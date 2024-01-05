import os
import json
import random
import re
import string
import tqdm
import argparse
import numpy as np
import pandas as pd
import time
from multiprocessing import Pool
from functools import partial
from rouge_score import rouge_scorer
from gpt_api import make_requests # gpt_api.py 

def encode_prompt(prompt_instructions):
    # 여러 프롬프트 지침을 단일 문자열로 인코딩
    prompt = open("./prompt.txt").read() + "\n"

    for idx, task_dict in enumerate(prompt_instructions):
        (instruction, input, output) = task_dict["instruction"], task_dict["input"], task_dict["output"]
        instruction = re.sub(r"\s+", " ", instruction).strip().rstrip(":")
        input = "<noinput>" if input.lower() == "" else input
        prompt += f"###\n"
        prompt += f"{idx + 1}. Instruction: {instruction}\n"
        prompt += f"{idx + 1}. Input: {input}\n"
        prompt += f"{idx + 1}. Output: {output}\n"
    prompt += f"###\n"
    prompt += f"{idx + 2}. Instruction: "
    return prompt

def post_process_gpt3_response(num_prompt_instructions, result):
    if result is None:
        return []

    
    instructions = []
    raw_instructoins = f"\n{num_prompt_instructions+1}. Instruction: " + result.message.content
    raw_instructoins = re.split("###", raw_instructoins)
    # 길이로 인해 디코딩이 중지되면 마지막 예제는 잘릴 가능성이 있으므로 삭제
    for idx, inst in enumerate(raw_instructoins):
        if idx == len(raw_instructoins) - 1 and result.finish_reason == "length":
            continue

        idx += num_prompt_instructions + 1
        splitted_data = re.split(f"{idx}\.\s+(Instruction|Input|Output):", inst)
        # 길이가 7이 인 될 경우
        if len(splitted_data) != 7:
            continue
        else:
            inst = splitted_data[2].strip()
            input = splitted_data[4].strip()
            input = "" if input.lower() == "<noinput>" else input
            output = splitted_data[6].strip()
        
        # instruction의 길이가 너무 짧거나 너무 길면 삭제
        if len(inst.split()) <= 3 or len(inst.split()) > 150:
            continue
        
        # 언어 모델에 적합하지 않은 키워드 필터링
        blacklist = [
            "사진",
            "사진을",
            "이미지",
            "이미지를",
            "그래프",
            "그래프를",
            "파일",
            "파일을",
            "맵",
            "맵을",
            "영상",
            "영상을",
            "동영상",
            "동영상을",
            "비디오",
            "비디오를",
            "음악",
            "음악을",
            "다이어그램",
            "다이어그램을"
        ]
        blacklist += []
        if any(find_word_in_string(word, inst) for word in blacklist):
            continue
        
        # 모델이 일부 기존 명령에 '프로그램 작성'을 추가하는 경향이 있어 다음과 같은 명령어 많이 생성
        # 모델이 프로그램을 작성해야 하는지 아니면 결과를 직접 출력해야 하는지 혼란
        # 여기서는 이를 필터링
        # 모든 프로그래밍 명령어에 대한 포괄적인 필터링이 아니라는 점 유의
        # 구두점으로 시작하는 항목 필터링
        if inst[0] in string.punctuation:
            continue

        # 영어가 아닌 문자로 시작하는 항목 필터링 -> 해당 프로젝트에서는 한글 사용
        # 즉 해당 코드 사용 x
        # if not inst[0].isascii():
        #     continue

        instructions.append({"instruction": inst, "input": input, "output": output})
    return instructions

def find_word_in_string(w, s):
    return re.compile(r"\b({0})\b".format(w), flags=re.IGNORECASE).search(s)

# parsing을 통해 format 형성
# terminal 작성 예시 : python 파일명.py --out_dir ""...
def parse_args():
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--out_dir", # 생성된 데이터 저장할 디렉토리 경로
        type=str,
        required=True,
        default="./",
        help="출력 저장 directory",
    )
    parser.add_argument(
        "--seed_tasks_path", # seed 작업에 사용될 jsonl 경로
        type=str,
        required=True,
        default="./seed_tasks.jsonl",
        help="사람이 작성한 데이터 경로",
    )
    parser.add_argument(
        "--num_instructions_to_generate", # 생성할 지시사항 개수 
        type=int,
        default=10,
        help="생성할 instruction 개수",
    )
    parser.add_argument(
        "--model",  # 사용할 모델
        type=str,
        default="gpt-3.5-turbo",
        help="사용할 모델(엔진)",
    )
    parser.add_argument(
        "--num_prompt_instructions", # 각 생성 요청에 포함할 프롬프트 지시사항 개수
        type=int,
        default=8,
        help="prompt에 사용할 instruction의 개수",
    )
    parser.add_argument(
        "--request_batch_size", # 병렬로 수행할 요청의 개수
        type=int,
        default=5,
        help="gpt3.5에게 한 번에 보낼 요청 수",
    )
    parser.add_argument(
        "--api_key", # openai api key 
        type=str,
        help="API키",
    )
    parser.add_argument(
        "--organization",
        type=str,
        help="The organization to use. If not specified, the default organization id will be used.",
    )
    return parser.parse_args()

if __name__ == "__main__":
    num_cpus = 4 # 병렬화에 사용할 CPU 코어 개수 
    num_to_include_machine_instruction = 2

    args = parse_args()

    # seed_task_path로 지정된 jsonl 파일 Open 후 Data load
    # seed_tasks 데이터에서 관련 정보 추출
    # seed_instruction_data 변수에 매핑된 seed_tasks 데이터 넣어줌
    seed_tasks = [json.loads(l) for l in open(args.seed_tasks_path, "r", encoding="utf-8")]

    # seed_tasks 데이터에서 instruction, input, output 정보 추출하여 seed_instruction_data에 저장
    seed_instructions = [
        {"instruction": t["instruction"], "input": t["instances"][0]["input"], "output": t["instances"][0]["output"]}
        for t in seed_tasks
    ]

    # 로드된 시드 데이터 지시사항 개수 출력(사용자가 작성한 시드 데이터)
    print(f"Loaded {len(seed_instructions)} human-written seed instructions")
    # print(seed_instructions)

    # machine_generated_instruction.jsonl 생성될 출력 디렉토리 생성
    os.makedirs(args.out_dir, exist_ok=True)

    # 요청을 생성하기 위한 인덱스 초기화
    request_idx = 0

    # 기계 생성 지시사항(machine_instruction_data)로드 
    machine_instructions = []
    # 이미 생성된 instruction이 있으면 불러오기
    if os.path.exists(os.path.join(args.out_dir, "machine_generated_instructions.jsonl")):
        with open(os.path.join(args.out_dir, "machine_generated_instructions.jsonl"), "r", encoding="utf-8") as fin:
            for l in fin:
                tmp = json.loads(l) 
                machine_instructions.append(tmp)
            print(f"Loaded {len(machine_instructions)} machine-generated instructions")

    # Rouge 스코어링 위한 scorer 객체 생성 -> 생성한 문장 유사도 체크
    # 유사도 = {}
    scorer = rouge_scorer.RougeScorer(["rougeL"], use_stemmer=False)
    
    # 새로운 지시사항 생성
    # num_instructions_to_generate 값에 따른 개수 생성
    progress_bar = tqdm.tqdm(total=args.num_instructions_to_generate)
    if machine_instructions:
        progress_bar.update(len(machine_instructions))


    # seed instruction과 기계 생성된 지시사항(machine_instruction_data)들을 토큰화 -> 유사도에 사용
    # all_instruction은 둘이 더해진 변수 
    # seed_instruction_data에서 seed instruction들의 텍스트 부분만 추출하여 all_instruction 리스트 저장
    # 이때 seed_instruction_data는 seed instruction 데이터의 리스트, 각각의 데이터는 기본 구조 띔
    all_instructions = [d["instruction"] for d in seed_instructions] + \
                       [d["instruction"] for d in machine_instructions]
    all_instruction_tokens = [scorer._tokenizer.tokenize(inst) for inst in all_instructions]


    # num_instructions_to_generate로 지정된 개수만큼 지시사항 생성하는 반복문
    # 요청 생성 및 결과 처리 과정은 반복문 내에서 수행
    # 이후 요청 생성 및 결과 처리, 유사도 계산, 생성된 지시사항 관리 등의 과정 이어짐
    # with open(os.path.join(args.batch_dir, "machine_generated_instructions.jsonl"), "a", encoding="utf-8") as fout:
    while len(machine_instructions) < args.num_instructions_to_generate:
        request_idx += 1

        # request_batch_size 개수에 따른 프롬프트 생성
        # 한 번의 요청(batch)에 포함될 입력 데이터 개수 나타내는 변수
        batch_inputs = []
        for _ in range(args.request_batch_size):
            if len(machine_instructions) > num_to_include_machine_instruction:
                prompt_instructions = random.sample(seed_instructions, args.num_prompt_instructions - num_to_include_machine_instruction)
                prompt_instructions += random.sample(machine_instructions, num_to_include_machine_instruction)
            else:
                prompt_instructions = random.sample(seed_instructions, args.num_prompt_instructions)
            prompt = encode_prompt(prompt_instructions)
            batch_inputs.append(prompt)

        print("요청 시작")
        request_start = time.time()
        results = make_requests(
            model=args.model,
            messages=batch_inputs, 
            max_tokens=2048, 
            temperature=1, 
            top_p=1,
            n=1, 
            stream=False,  
            frequency_penalty=0, 
            presence_penalty=0, 
            stop=["\n20", "20.", "20."], 
            logit_bias={"50256": -100},
            user=None,
            retries=3, 
            api_key=args.api_key, 
            organization=args.organization
        )
        request_duration = time.time() - request_start
        print("요청 끝")

        print("후처리 시작")
        process_start = time.time()
        instruction_data = []
        for result in results:
            new_instructions = post_process_gpt3_response(args.num_prompt_instructions, result)
            instruction_data += new_instructions
        
        total = len(instruction_data)
        keep = 0

        for instruction_data_entry in instruction_data:
            new_instruction_tokens = scorer._tokenizer.tokenize(instruction_data_entry["instruction"])
            with Pool(num_cpus) as p:
                rouge_scores = p.map(
                    partial(rouge_scorer._score_lcs, new_instruction_tokens),
                    all_instruction_tokens
                )
            rouge_scores = [score.fmeasure for score in rouge_scores]
            most_similar_instructions = {
                all_instructions[i]: rouge_scores[i] for i in np.argsort(rouge_scores)[-10:][::-1]
            }
            if max(rouge_scores) > 0.7:
                # print("유사도가 너무 높음")
                continue
            else:
                keep += 1

            # instruction_data_entry["most_similar_instructions"] = most_similar_instructions
            # instruction_data_entry["avg_similarity_score"] = float(np.mean(rouge_scores))
            machine_instructions.append(instruction_data_entry)
            all_instructions.append(instruction_data_entry["instruction"])
            all_instruction_tokens.append(new_instruction_tokens)
            with open(os.path.join(args.out_dir, "machine_generated_instructions.jsonl"), "a", encoding="utf-8") as fout:
                json.dump(instruction_data_entry, fout, ensure_ascii=False)
                fout.write("\n")
            progress_bar.update(1)
        process_duration = time.time() - process_start
        print("후처리 끝")
        print(f"Request {request_idx} took {request_duration:.2f}s, processing took {process_duration:.2f}s")
        print(f"Generated {total} instructions, kept {keep} instructions")
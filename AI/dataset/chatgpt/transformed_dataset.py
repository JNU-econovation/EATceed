import json

# 기존 JSONL 파일에서 데이터 읽기
with open('./machine_generated_instructions.jsonl', 'r', encoding='utf-8') as file:
    lines = file.readlines()

# 새로운 데이터 생성
new_data = []

for line in lines:
    json_data = json.loads(line)
    system_content = "You're good at understanding a person's current situation and emotions and making food recommendations accordingly."  # 업데이트된 system_content
    user_content = json_data.get("instruction", "")  # 기존의 instruction 값을 가져옴
    assistant_content = json_data.get("output", "")  # 기존의 output 값을 가져옴

    new_entry = {
        "messages": [
            {"role": "system", "content": system_content},
            {"role": "user", "content": user_content},
            {"role": "assistant", "content": assistant_content}
        ]
    }
    new_data.append(new_entry)

# 새로운 데이터를 JSONL 파일에 추가하여 저장
with open('chatgpt_fine_tuning_format.jsonl', 'w', encoding='utf-8') as new_file:
    for entry in new_data:
        json.dump(entry, new_file, ensure_ascii=False)
        new_file.write('\n')
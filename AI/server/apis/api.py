# 메인 로직 작성

# prompt를 불러오기
def read_prompt(filename):
    with open(filename, 'r', encoding='utf-8') as file:
        prompt = file.read().strip()
    return prompt
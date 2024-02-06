import subprocess
import urllib.parse
import shlex

query = input("Question: ")

# safe encode url string
encodedquery = urllib.parse.quote(query)

# join the curl command text
command = f"curl -X 'POST' \
  'http://127.0.0.1:8000/v1/chat/?user_input={encodedquery}' \
  -H 'accept: application/json' \
  -d ''"

args = shlex.split(command)
process = subprocess.Popen(args, shell=False, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
stdout, stderr = process.communicate()
print(stdout)

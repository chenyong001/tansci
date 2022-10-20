import re
from nltk.tokenize import word_tokenize
import json

import argparse
# file=open("./text_test01.txt",'r')
# text=file.read()
# file.close()

class TextAnalysis:
    def __init__(self,text):
        self.text_test=text

    # 获得词频的函数，注意：目前只能处理英文的对话内容
    def Get_wordfreq(self):
        container = re.findall(r"\"subtitle\"\:\".*?\"", self.text_test)
        # print(container)
        text01 = ""

        def find_content(str_input):
            index1 = []
            for i in range(len(str_input)):
                if str_input[i] == "\"":
                    index1.append(i)
            return str_input[index1[-2] + 1:index1[-1]]

        for part in container:
            text01 += find_content(part)
        # print(text01)
        # print("---------------")

        text02 = ""
        for i in range(len(text01)):
            if 97 <= ord(text01[i]) <= 122 or text01[i] == " ":
                text02 += text01[i]
            elif 65 <= ord(text01[i]) <= 122:
                text02 += chr(ord(text01[i]) + 32)
            else:
                text02 += " "
        # print(text02)
        word_list = word_tokenize(text02)
        # print(word_list)
        word_dict = {}
        for word in word_list:
            if word not in word_dict:
                word_dict[word] = 1
            else:
                word_dict[word] += 1
        # print(word_dict)
        word_cp = sorted(word_dict.items(), key=lambda x: x[1], reverse=True)
        # print(word_cp)
        word_dict = {}
        for a, b in word_cp:
            word_dict[a] = b
        word_json = json.dumps(word_dict)
        return word_json




parser = argparse.ArgumentParser(description='manual to this script')
parser.add_argument('--param1', type=str, default = None)
# parser.add_argument('--batch-size', type=int, default=32)
args = parser.parse_args()
# print 'hello'
# print(args.param1)
data=args.param1
data=data.replace('\'','\"')
# print(data)
# print(demjson.decode(args.param1))
# data=demjson.decode(args.param1)
# print(data[docId])
# data=json.loads(args.param1)
# solution=args.param1

# solution=TextAnalysis("请在这里输入你想处理的json数组样式的字符串")
# solution=TextAnalysis('[{"docId":"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2","subtitle":"Hello。 Hello。","property":"en","userId":"bc3ac26e69731b617eb80274453f6dba","timestamp":"2022-09-23 15:57:15"},{"docId":"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2","subtitle":"Create an acquisition task.","property":"en","userId":"bc3ac26e69731b617eb80274453f6dba","timestamp":"2022-09-23 15:57:27"}]')
solution=TextAnalysis(data)
print(solution.Get_wordfreq())#调用获得对话内容词频函数
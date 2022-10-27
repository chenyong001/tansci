import re
from nltk.tokenize import word_tokenize
import json
from nltk.corpus import stopwords
import argparse
import jieba
import jieba.posseg as pseg
import nltk
from nltk.text import Text

stopwords1=[line.strip() for line in open("/stopword.txt",encoding='utf-8').readlines()]
class TextAnalysis:
    def __init__(self,text):
        self.text_test=text
        self.text_test = re.sub("\n", " ", self.text_test)

    #预处理提取对话的内容
    def pre_processing_GetContent(self):
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
        return text01

    #提取英文关键词并生成字典
    def Get_English_dict(self):
        # print("pre_processing_GetContent()函数获取的是：",self.pre_processing_GetContent())
        text_test=self.pre_processing_GetContent()
        # print("text_test是：",text_test)
        text_English = "".join([word for word in re.findall(r'[\x00-\xff]+', text_test) if word.lower() not in stopwords1])
        text_English = re.sub("\,|\.|\?", " ", text_English)
        # print("text_English是",text_English)
        tokens = word_tokenize(text_English)
        tokens_tag = nltk.pos_tag(tokens)
        # print(tokens_tag)
        noun_container = [a for a, b in tokens_tag if b in ['NN', 'NNS', 'NNP', 'NNPS']]
        t = Text(tokens)
        dict_noun = {}
        for i in range(len(noun_container)):
            dict_noun[noun_container[i]] = t.count(noun_container[i])
        word_noun_cp = sorted(dict_noun.items(), key=lambda x: x[1], reverse=True)
        # print(word_cp)
        word_dict_english = {a: b for a, b in word_noun_cp}
        return word_dict_english

    # 提取中文关键词并生成字典
    def Get_Chinese_dict(self):
        text_test=self.pre_processing_GetContent()
        # print(text_test)
        text_Chinese = "".join(re.findall(r'[^\x00-\xff]+', text_test))
        # print(text_Chinese)
        tokens1 = jieba.lcut(re.sub("，|。|？|！|：", " ", text_Chinese))
        tokens1=[token for token in tokens1 if token!=' ']
        # print("tokens1是：",tokens1)
        t = Text(tokens1)
        tokens = pseg.cut(text_Chinese)
        tokens_tag = [(token.word, token.flag) for token in tokens if token.word not in stopwords1]
        dict_noun = {a: t.count(a) for a, b in tokens_tag if b in ['n', 'nr', 'ns', 'nt', 'nz']}
        word_noun_cp = sorted(dict_noun.items(), key=lambda x: x[1], reverse=True)
        # print(word_noun_cp)
        word_dict_chinese = {a: b for a, b in word_noun_cp}
        # print("切分后的字典为：",word_dict_chinese)
        # word_Chinese_json = json.dumps(dict_noun)
        # print("转换为json后：",word_Chinese_json)
        return word_dict_chinese

    # 提取中英文关键词并生成字典
    def Get_total_dict(self):
        text_test=self.pre_processing_GetContent()
        text_Chinese_part = "".join(re.findall(r'[^\x00-\xff]+', text_test))
        text_English_part = "".join(
            [word for word in re.findall(r'[\x00-\xff]+', text_test) if word not in stopwords.words('english')])
        # print(get_Chinese_dict(text_Chinese_part))
        # print(get_English_dict(text_English_part))
        dict_total = {**self.Get_Chinese_dict(), **self.Get_English_dict()}
        total_noun_cp = sorted(dict_total.items(), key=lambda x: x[1], reverse=True)
        word_total_dict = {a: b for a, b in total_noun_cp}
        return word_total_dict

    # 提取中文关键词并生成json
    def Get_Chinese_json(self):
        word_Chinese_json = json.dumps(self.Get_Chinese_dict())
        return word_Chinese_json

    # 提取英文关键词并生成json
    def Get_English_json(self):
        word_English_json = json.dumps(self.Get_English_dict())
        return word_English_json

    # 提取中英文关键词并生成json
    def Get_total_json(self):
        word_total_json = json.dumps(self.Get_total_dict())
        return word_total_json


    # 获得说话人时长的比例
    def Get_person_proportion(self):
        container1 = re.findall(r"\"userId\"\:\".*?\"", self.text_test)
        container1 = [element[11:len(element) - 1] for element in container1]
        # print(container1)
        container2 = re.findall(r"\"timestamp\"\:\".*?\"", self.text_test)
        container_time = []
        for timeslot in container2:
            container_time.append(timeslot[-20:-1])
        # print(container_time)
        userID_dict = {}

        def get_time(num):
            if num == len(container_time) - 1:
                return 10
            time1 = re.sub('-|:', ' ', container_time[num])
            time2 = re.sub('-|:', ' ', container_time[num + 1])
            time1 = time1.split(" ")
            time2 = time2.split(" ")
            unit = [31536000, 2592000, 86400, 3600, 60, 1]
            ans1, ans2 = 0, 0
            for i in range(len(time1)):
                ans1 += int(time1[i]) * unit[i]
                ans2 += int(time2[i]) * unit[i]
            return ans2 - ans1

        user_dict = {}
        for user in container1:
            if user not in user_dict:
                user_dict[user] = 0
        # print(user_dict)
        for i in range(len(container1)):
            user_dict[container1[i]] += get_time(i)
        # print(user_dict)

        time_span_all = sum(b for a, b in user_dict.items())
        # print(time_span_all)

        # user_talking_proportion={a:b/time_span_all} for a,b in user_dict.items()
        user_talking_proportion = {}
        for a, b in user_dict.items():
            user_talking_proportion[a] = (b / time_span_all)
        user_talking_proportion_json = json.dumps(user_talking_proportion)
        return user_talking_proportion_json




parser = argparse.ArgumentParser(description='manual to this script')
parser.add_argument('--fileName', type=str, default = None)
# parser.add_argument('--batch-size', type=int, default=32)
args = parser.parse_args()
# print 'hello'
# print(args.param1)
filePath="/temp/"+args.fileName
file=open(filePath,'r',encoding='utf-8')
text=file.read()
file.close()
# data=data.replace('\'','\"')
# print(data)
# print(demjson.decode(args.param1))
# data=demjson.decode(args.param1)
# print(data[docId])
# data=json.loads(args.param1)
# solution=args.param1

# solution=TextAnalysis("请在这里输入你想处理的json数组样式的字符串")
# solution=TextAnalysis('[{"docId":"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2","subtitle":"Hello。 Hello。","property":"en","userId":"bc3ac26e69731b617eb80274453f6dba","timestamp":"2022-09-23 15:57:15"},{"docId":"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2","subtitle":"Create an acquisition task.","property":"en","userId":"bc3ac26e69731b617eb80274453f6dba","timestamp":"2022-09-23 15:57:27"}]')
solution=TextAnalysis(text)
print(solution.Get_total_json())#调用获得对话内容词频函数
# print(solution.Get_person_proportion())#调用获得说话人的谈话时长比例
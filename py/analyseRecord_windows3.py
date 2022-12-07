import re
from nltk.tokenize import word_tokenize
import json
from nltk.corpus import stopwords
import argparse
import jieba
import jieba.posseg as pseg
import nltk
from nltk.text import Text
import pynlpir
import sys
import os
curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

pynlpir.open()

jieba.add_word("多模态")
# jieba.add_word("南洋理工大学")

stopwords1=[line.strip() for line in open("./stopword.txt",encoding='utf-8').readlines()]
with open('Userdict.txt', 'a', encoding="utf-8") as f0:
    f0.write("")

class TextAnalysis:
    def __init__(self,text):
        self.text_test=text
        self.text_test = re.sub("\n", " ", self.text_test)


    def Get_content(self):
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

    #预处理提取对话的中文内容
    def Get_Chinese_content(self):
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
        text_Chinese = "".join(re.findall(r'[^\x00-\xff]+', text01))
        return text_Chinese

    # 预处理提取对话的英文内容
    def Get_English_content(self):
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
        text_English = " ".join(
            [word for word in re.findall(r'[\x00-\xff]+', text01) if word.lower() not in stopwords1])
        text_English = re.sub("\,|\.|\?", " ", text_English)
        return text_English


    def Get_English_noun(self):
        # print("pre_processing_GetContent()函数获取的是：",self.pre_processing_GetContent())
        text_English=self.Get_English_content()
        # print("text_English是：",text_English)
        tokens = word_tokenize(text_English)
        tokens2=[token.lower() for token in tokens]
        # tokens2=[token[0].lower()+token[1:] if len(token)>1 and 65<=ord(token[0])<=90 else token for token in tokens ]
        # print(tokens2)
        tokens_tag = nltk.pos_tag(tokens2)
        dict_noun = {}
        noun_container = []
        for i in range(len(tokens_tag)):
            if tokens_tag[i][1] in ['NN', 'NNS', 'NNP', 'NNPS'] and tokens[i] not in noun_container:
                noun_container.append(tokens[i])
                dict_noun[tokens[i]]=1
            elif tokens_tag[i][1] in ['NN', 'NNS', 'NNP', 'NNPS'] and tokens[i] in noun_container:
                dict_noun[tokens[i]]+=1

            # [a for a, b in tokens_tag if b in ['NN', 'NNS', 'NNP', 'NNPS']]
        # print(noun_container)
        # t = Text(tokens)

        # for i in range(len(noun_container)):
        #     dict_noun[noun_container[i]] = t.count(noun_container[i])
        word_noun_cp = sorted(dict_noun.items(), key=lambda x: x[1], reverse=True)
        # print(word_cp)
        word_dict_english = {a: b for a, b in word_noun_cp}
        return word_dict_english

    def Get_English_verb(self):
        text_English=self.Get_English_content()
        tokens = word_tokenize(text_English)
        tokens2 = [token.lower() for token in tokens]
        tokens_tag = nltk.pos_tag(tokens2)
        # print(tokens_tag)

        dict_verb = {}
        verb_container = []
        for i in range(len(tokens_tag)):
            if tokens_tag[i][1] in ['VB','VBD','VBG','VBN','VBP','VBZ'] and tokens[i] not in verb_container:
                verb_container.append(tokens[i])
                dict_verb[tokens[i]]=1
            elif tokens_tag[i][1] in ['VB','VBD','VBG','VBN','VBP','VBZ'] and tokens[i] in verb_container:
                dict_verb[tokens[i]]+=1

        # print(verb_container)

        # t = Text(tokens)

        # for i in range(len(verb_container)):
        #     dict_verb[verb_container[i]] = t.count(verb_container[i])
        word_noun_cp = sorted(dict_verb.items(), key=lambda x: x[1], reverse=True)
        # print(word_cp)
        word_dict_english = {a: b for a, b in word_noun_cp}
        return word_dict_english

    def Get_English_adj(self):
        text_English=self.Get_English_content()
        tokens = word_tokenize(text_English)
        tokens2 = [token.lower() for token in tokens]
        tokens_tag = nltk.pos_tag(tokens2)
        # print(tokens_tag)

        dict_adj = {}
        adj_container = []
        for i in range(len(tokens_tag)):
            if tokens_tag[i][1] in ['JJ','JJR','JJS'] and tokens[i] not in adj_container:
                adj_container.append(tokens[i])
                dict_adj[tokens[i]]=1
            elif tokens_tag[i][1] in ['JJ','JJR','JJS'] and tokens[i] in adj_container:
                dict_adj[tokens[i]]+=1

        # print(verb_container)

        # t = Text(tokens)

        # for i in range(len(verb_container)):
        #     dict_verb[verb_container[i]] = t.count(verb_container[i])
        word_noun_cp = sorted(dict_adj.items(), key=lambda x: x[1], reverse=True)
        # print(word_cp)
        word_dict_english = {a: b for a, b in word_noun_cp}
        return word_dict_english


    #获取说话内容中的汉语名词
    def Get_Chinese_noun(self):
        text_test=self.Get_Chinese_content()
        # print(text_test)
        container=pynlpir.segment(text_test)
        container=[(a,b) for a,b in container if a not in stopwords1]
        dict_noun={}
        for item in container:
            if item[1]=='noun':
                if item[0] not in dict_noun:
                    dict_noun[item[0]]=1
                else:
                    dict_noun[item[0]]+=1
        word_noun_cp = sorted(dict_noun.items(), key=lambda x: x[1], reverse=True)
        # print(word_noun_cp)
        word_dict_chinese = {a: b for a, b in word_noun_cp}
        # print("切分后的字典为：",word_dict_chinese)
        # word_Chinese_json = json.dumps(dict_noun)
        # print("转换为json后：",word_Chinese_json)
        return word_dict_chinese

    # 获取说话内容中的汉语动词
    def Get_Chinese_verb(self):
        text_test=self.Get_Chinese_content()
        # print(text_test)
        container=pynlpir.segment(text_test)
        container = [(a, b) for a, b in container if a not in stopwords1]
        dict_noun={}
        for item in container:
            if item[1]=='verb':
                if item[0] not in dict_noun:
                    dict_noun[item[0]]=1
                else:
                    dict_noun[item[0]]+=1
        word_vb_cp = sorted(dict_noun.items(), key=lambda x: x[1], reverse=True)
        # print(word_noun_cp)
        word_dict_chinese = {a: b for a, b in word_vb_cp}
        # print("切分后的字典为：",word_dict_chinese)
        # word_Chinese_json = json.dumps(dict_noun)
        # print("转换为json后：",word_Chinese_json)
        return word_dict_chinese

    # 获取说话内容中的汉语形容词
    def Get_Chinese_adj(self):
        text_test=self.Get_Chinese_content()
        # print(text_test)
        container=pynlpir.segment(text_test)
        container = [(a, b) for a, b in container if a not in stopwords1]
        dict_adj={}
        for item in container:
            if item[1]=='adjective':
                if item[0] not in dict_adj:
                    dict_adj[item[0]]=1
                else:
                    dict_adj[item[0]]+=1
        word_adj_cp = sorted(dict_adj.items(), key=lambda x: x[1], reverse=True)
        # print(word_noun_cp)
        word_dict_chinese = {a: b for a, b in word_adj_cp}
        # print("切分后的字典为：",word_dict_chinese)
        # word_Chinese_json = json.dumps(dict_noun)
        # print("转换为json后：",word_Chinese_json)
        return word_dict_chinese

    # 获取说话内容中的汉语关键词
    def Get_keyword(self,n=30):
        text_test=self.Get_Chinese_content()
        return pynlpir.get_key_words(text_test)[:n]

    # 将dict格式转换为最后的json格式
    def Get_json(self,dict_word):
        json_word=json.dumps(dict_word)
        return json_word

    # 用户自定义添加关键词
    def Add_UserDict(self,word_list):
        with open('Userdict.txt', 'a', encoding="utf-8") as f:
            for word in word_list:
                f.write("\n" + word)

    def Del_UserDict(self,word_list):
        f1 = open('Userdict.txt', "r", encoding="utf-8")
        content = f1.read()
        for word in word_list:
            content = content.replace(word, "")
        with open('Userdict.txt', "w", encoding="utf-8") as f2:
            f2.write(content)

    # 用户自定义添加停用词
    def Add_StopWord(self,word_list):
        for word in word_list:
            stopwords1.append(word)

    def Del_StopWord(self,word_list):
        for word in word_list:
            stopwords1.remove(word)

    # 词性有：名词、形容词、动词、关键词、所有
    # 语言：中文、英文、所有
    # num:输出词汇的个数
    def Get_answer(self,language='all',cixing='all',num=30):
        pynlpir.nlpir.ImportUserDict(b'Userdict.txt')
        def generate_dict(dict_tartget,number):
            dict_res={}
            for key,val in dict_tartget.items():
                dict_res[key]=val
                number-=1
                if number==0:
                    break
            word_noun_cp = sorted(dict_res.items(), key=lambda x: x[1], reverse=True)
            dict_res = {a: b for a, b in word_noun_cp}
            return dict_res

        container_dict= {}
        text_test=""
        if cixing=="keywords":
            container_dict2 = self.Get_answer(num=1000)
            # print(len(container_dict2))
            # print("总的词汇表为：", container_dict2)
            container_list=[]
            if language=='Chinese':
                text_test = self.Get_Chinese_content()
            elif language == 'English':
                text_test = self.Get_English_content()
            elif language == 'all':
                text_test = self.Get_content()
            container_list = [word for word in pynlpir.get_key_words(text_test) if word not in stopwords1][:num]
            # for word in container_list:
            #     if word not in container_dict2:
            #         self.Add_UserDict(word)
            # print("关键词列表为：",container_list)
            # container_dict2 = self.Get_answer(num=1000)
            # print(container_dict2)
            for word in container_list:
                container_dict[word]=container_dict2[word]
            return container_dict

        if language=='English':
            text_test = self.Get_English_content()
            if cixing=="noun":
                container_dict=self.Get_English_noun()
            elif cixing=="verb":
                container_dict=self.Get_English_verb()
            elif cixing=="adj":
                container_dict=self.Get_English_adj()
            elif cixing=='all':
                container_dict = self.Get_English_noun()
                container_dict.update(self.Get_English_verb())
                container_dict.update(self.Get_English_adj())
                word_noun_cp = sorted(container_dict.items(), key=lambda x: x[1], reverse=True)
                container_dict = {a: b for a, b in word_noun_cp}
            return generate_dict(container_dict ,num)
        elif language=="Chinese":
            text_test = self.Get_Chinese_content()
            if cixing=="noun":
                container_dict=self.Get_Chinese_noun()
            elif cixing=="verb":
                container_dict=self.Get_Chinese_verb()
            elif cixing == "adj":
                container_dict=self.Get_Chinese_adj()
            elif cixing=='all':
                container_dict = self.Get_Chinese_noun()
                container_dict.update(self.Get_Chinese_verb())
                container_dict.update(self.Get_Chinese_adj())
                word_noun_cp = sorted(container_dict.items(), key=lambda x: x[1], reverse=True)
                container_dict = {a: b for a, b in word_noun_cp}
            return generate_dict(container_dict,num)
        elif language=='all':
            text_test = self.Get_content()
            if cixing=="noun":
                container_dict=self.Get_Chinese_noun()
                container_dict.update(self.Get_English_noun())
                # print(container_dict)
            elif cixing=="verb":
                container_dict = self.Get_Chinese_verb()
                container_dict.update(self.Get_English_verb())
            elif cixing == "adj":
                container_dict = self.Get_Chinese_adj()
                container_dict.update(self.Get_English_adj())
            elif cixing=='all':
                container_dict = self.Get_answer('Chinese','all',1000)
                container_dict.update(self.Get_answer('English','all',1000))
                # print(container_dict)
            word_noun_cp = sorted(container_dict.items(), key=lambda x: x[1], reverse=True)
            # print(word_noun_cp)
            word_dict = {a: b for a, b in word_noun_cp}
            return generate_dict(word_dict, num)


# 传参：filePath
parser = argparse.ArgumentParser(description='manual to this script')
parser.add_argument('--filePath', type=str, default = None)
parser.add_argument('--keywords-list', type=list, default = [])
parser.add_argument('--stopwords-list', type=list, default = [])
parser.add_argument('--num', type=int, default = 30)
parser.add_argument('--language-type', type=str, default = 'all')
parser.add_argument('--xxtype', type=str, default = 'all')
# parser.add_argument('--batch-size', type=int, default=32)
args = parser.parse_args()
# print 'hello'
# print(args.param1)
file=open(args.filePath,'r',encoding='utf-8')
text=file.read()
file.close()
keywords_list=args.keywords_list
stopwords_list=args.stopwords_list
num=args.num
language_type=args.language_type
xxtype=args.xxtype
# data=data.replace('\'','\"')
# print(data)
# print(demjson.decode(args.param1))
# data=demjson.decode(args.param1)
# print(data[docId])
# data=json.loads(args.param1)
# solution=args.param1

# 创建类与预处理
so=TextAnalysis(text)
text_container=so.Get_content()
container_list = [word for word in pynlpir.get_key_words(text_container) if word not in stopwords1]
container_dict2=so.Get_answer(num=1000)
after_delete=[]
for keyword in container_list:
    if keyword not in container_dict2:
        after_delete.append(keyword)
        so.Add_UserDict(keyword)#系统的关键词添加



# print("------------用户自定义添加关键词--------------")
so.Add_UserDict(keywords_list)
# print("------------用户自定义添加停用词-------------")
so.Add_StopWord(stopwords_list)
# print("-----------------调用得到结果---------------------")
print(so.Get_json(so.Get_answer(language_type,xxtype,num)))

# print("------------------后续清理----------------------")
so.Del_UserDict(after_delete)
so.Del_UserDict(keywords_list)





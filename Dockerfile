FROM com/my/python-java:v1.1
#FROM openjdk:8-jdk-alpine
#基础镜像
#工作目录
RUN mkdir /app
WORKDIR /app
# 修改时区
ENV TZ Asia/Shanghai
#加入jar包
ADD  target/*.jar /app/tansci.jar
#添加py脚本文件
ADD py/analyseRecord_linux.py /
ADD py/stopword.txt /
ADD py/Userdict.txt /
ADD py/NLPIR.user /usr/local/lib/python3.7/site-packages/pynlpir/Data
#用于存放py文件参数数据目录
RUN mkdir /temp
#创建日志目录
RUN mkdir /logs
#暴露端口
EXPOSE 8005
# 运行jar包
ENTRYPOINT ["nohup","java","-jar","/app/tansci.jar","&"]

#com/my/python-java 所执行的命令
#pip install nltk
#pip install argparse
#pip install jieba
#import nltk
#nltk.download('punkt')
#nltk.download('averaged_perceptron_tagger')
#nltk.download('stopwords')
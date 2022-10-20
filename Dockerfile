FROM com/my/python-java
#FROM openjdk:8-jdk-alpine
#基础镜像
#工作目录
RUN mkdir /app
WORKDIR /app
#加入jar包
ADD  target/*.jar /app/tansci.jar
#添加py脚本文件
ADD py/analyseRecord.py /
#创建日志目录
RUN mkdir /logs
#暴露端口
EXPOSE 8005
# 运行jar包
ENTRYPOINT ["nohup","java","-jar","/app/tansci.jar","&"]


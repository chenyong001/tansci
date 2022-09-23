FROM openjdk:8-jdk-alpine
#基础镜像
#工作目录
WORKDIR /app
#加入jar包
ADD  target/*.jar /app/tansci.jar
#创建日志目录
RUN mkdir /logs
#暴露端口
EXPOSE 8005
# 运行jar包
ENTRYPOINT ["nohup","java","-jar","/app/tansci.jar","&"]


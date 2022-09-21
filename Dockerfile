FROM openjdk:8-jdk-alpine
WORKDIR /app
ADD  target/*.jar /app/tansci.jar
EXPOSE 8005
# 运行jar包
ENTRYPOINT ["nohup","java","-jar","/app/tansci.jar","&"]

## 简介
sql：
2个表(record,record_data)

##服务器存放音频文件目录
/app/tempAudio
#前端部署
#首次部署
#tansci-view目录下新增Dockerfile、default.conf
#cd /home/ubuntu/tansci/tansci-view
#git pull
#docker build -t tansci-view:1 .
#docker run -p 8001:80 --name=tansci-view -d tansci-view:1
#更新部署
#cd /home/ubuntu/tansci/tansci-view
#git pull
#docker build -t tansci-view:v1 .
#docker stop tansci-view && docker rm tansci-view
#docker run -p 8001:80 --name=tansci-view -d tansci-view:v1

#后端部署
    #tansci根目录下新增Dockerfile
#cd /home/ubuntu/tansci
#git pull
#mvn clean package -Dmaven.test.skip=true
#docker build -t tansci:v1 .
#docker stop tansci && docker rm tansci
#docker run -d --name=tansci -p 8005:8005  --restart=always --privileged=true -v /app/tempAudio:/app/tempAudio tansci:v1
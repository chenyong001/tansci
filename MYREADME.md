## 简介
sql：
2个表(record,record_data)

#nlpir的license过期更换：
下载
https://github.com/NLPIR-team/NLPIR/blob/master/License/license%20for%20a%20month/NLPIR-ICTCLAS%E5%88%86%E8%AF%8D%E7%B3%BB%E7%BB%9F%E6%8E%88%E6%9D%83
下的nlpir.user文件替换,windows位置：C:\Users\50212\AppData\Roaming\Python\Python310\site-packages\pynlpir\Data
服务器位置：/usr/local/lib/python3.7/site-packages/pynlpir/Data
C:\\Users\\50212\\AppData\\Roaming\\Python\\Python310\\site-packages
##服务器存放音频文件目录
/app/tempAudio
#前端部署
#首次部署
#tansci-view目录下新增Dockerfile、default.conf
#cd /home/ubuntu/tansci/tansci-view
#git pull
#docker build -t tansci-view:v1 .
#docker run -p 8001:80 --name=tansci-view -d tansci-view:v1
#更新部署
#cd /home/ubuntu/tansci/tansci-view && git pull && docker ps
#docker build -t tansci-view:v2.5.1.0 .
#docker stop tansci-view && docker rm tansci-view
#docker run -p 8001:80 --name=tansci-view -d tansci-view:v2.5.1.0
#docker run -p 8080:8080 --name=xf -d xf:v1.0
#docker run -p 8002:80 --name=cv -d cv:v1.1

#后端部署
    #tansci根目录下新增Dockerfile
#cd /home/ubuntu/tansci && git pull
#mvn clean package -Dmaven.test.skip=true
#docker build -t tansci:v2.5.0.9 .
#docker stop tansci && docker rm tansci
#docker run -d --name=tansci -p 8005:8005  --restart=always --privileged=true \
-v /app/tempAudio:/app/tempAudio tansci:v2.5.0.9

1.proxy_pass代理地址端口后有目录(包括 / )，转发后地址：代理地址+访问URL目录部分去除location匹配目录 
2.proxy_pass代理地址端口后无任何，转发后地址：代理地址+访问URL目录部
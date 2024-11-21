# 

## Model
www.msaez.io/#/93813473/storming/theater1

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- reservation
- movie
- notification
- reservationlist


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- reservation
```
 http :8088/reservations id="id" userId="userId" userName="userName" movieId="movieId" count="count" reserveStatus="reserveStatus" date="date" 
```
- movie
```
 http :8088/movies id="id" movieName="movieName" qty="qty" description="description" 
```
- notification
```
 http :8088/notifications id="id" reserveId="reserveId" reserveStatus="reserveStatus" userId="userId" userName="userName" movieId="movieId" movieName="movieName" count="count" 
```
- reservationlist
```
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

# 영화예매 시스템
MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계에 걸쳐 클라우드 네이티브 애플리케이션을 개발했습니다.

# 서비스 시나리오
1. 영화사에서 영화 티켓을 올린다.
2. 고객이 영화를 예매한다.
3. 고객이 영화를 취소할 수 있다.
4. 고객이 티켓 매수 이상의 영화를 예매하면 자동 취소된다.
5. 고객이 영화 예매내역을 조회할 수 있다.
6. 고객이 영화를 예매하거나 취소하면 알림 메시지를 발송한다.


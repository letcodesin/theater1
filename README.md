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
MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 걸쳐 클라우드 네이티브 애플리케이션을 개발했습니다.

# 서비스 시나리오
1. 영화사에서 영화 티켓을 올린다.
2. 고객이 영화를 예매한다.
3. 고객이 영화를 취소할 수 있다.
4. 고객이 티켓 매수 이상의 영화를 예매하면 자동 취소된다.
5. 고객이 영화 예매내역을 조회할 수 있다.
6. 고객이 영화를 예매하거나 취소하면 알림 메시지를 발송한다.

# 클라우드 아키텍처 설계
![아키텍처설계01](https://github.com/user-attachments/assets/81a8d6a4-8938-49c7-9fd1-af4967302f98)

# 도메인분석 - 이벤트스토밍
![이벤트스토밍_theater1](https://github.com/user-attachments/assets/0b4ef8f8-887f-4cc5-927a-d5384914e0e7)

# 분산트랜잭션 - Saga
![saga01](https://github.com/user-attachments/assets/3e3be3dd-dcaa-47f1-9ea5-d69b361c1222)
- 영화사에서 영화 LALALand 티켓 100개를 올린다.
  
![saga02](https://github.com/user-attachments/assets/f0d1aaad-ed45-4e99-82de-eab99d4a4e52)
- 고객이 영화 티켓 45개를 예매한다.
  
![saga03](https://github.com/user-attachments/assets/04a2732f-c8b9-40ea-bfca-bbaee39ebf26)
- 예매가 정상적으로 완료된다.
  
![saga04](https://github.com/user-attachments/assets/de98fb19-1bea-43dc-bc73-8c29fa8caa17)
- 영화 티켓수가 감소한다.
  
![saga05](https://github.com/user-attachments/assets/ca1aa7e6-4f3e-4c53-ad74-2a97bf4911f9)
- 예약완료 알림 메시지를 발송한다.

# 보상처리 - Compensation 
![보상처리01](https://github.com/user-attachments/assets/349c0208-f7f5-4c61-bd38-6003be80eb52)
- 영화사에 영화 LALALand 티켓 55개를 있다.
  
![보상처리02](https://github.com/user-attachments/assets/824f324e-4c35-4cb5-a756-32ae01818981)
- 고객이 영화 티켓 재고 수량보다 많은 57개를 예매한다.
  
![보상처리03](https://github.com/user-attachments/assets/82139894-34bf-4feb-9bc1-cf6f6e39f7e0)
- 영화 티켓수가 감소하지 않는다.
  
![보상처리04](https://github.com/user-attachments/assets/c6b9dc2a-9143-4319-8cab-90ea917dfc9b)
- 예매가 취소된다.
  
![보상처리05](https://github.com/user-attachments/assets/8d9dea10-e247-4b3c-ba3d-d186f5d2ffb9)
- 예약취소 알림 메시지가 발송된다.

# 단일 진입점 - Gateway
![gateway01](https://github.com/user-attachments/assets/5261ddc1-f4fa-483b-a8ad-5dc61f6bf94e)
- 8083 포트를 사용하는 movie 서비스가 단일진입점 Gateway 포트인 8088포트로 올바르게 동작한다.
  
![gateway03](https://github.com/user-attachments/assets/67afd4b5-b3ff-4117-a9ce-3643d45c3bfe)
- 8083 포트를 사용하는 reservation 서비스가 단일진입점 Gateway 포트인 8088포트로 올바르게 동작한다.
  
![gateway05](https://github.com/user-attachments/assets/f4b4d371-a440-4549-aec3-a4c2ac119f1f)

# 분산 데이터 프로젝션 - CQRS 
![cqrs01](https://github.com/user-attachments/assets/8a3c330d-271e-4c23-b4a2-df6c194a7583)
- 예약이 완료되었을 때 예약목록에서 예약내역이 보여진다. 예약이 취소 되는 경우에는 예약목록에서 삭제된다.

# 클라우드 배포 - Container 운영
![클라우드배포_CI01](https://github.com/user-attachments/assets/23d91f57-7635-4384-aa50-74c085b29e03)
![클라우드배포_CI02](https://github.com/user-attachments/assets/69192e48-4406-4a69-93f7-f4b2773ef729)
- Azure DevOps를 통해 movie-CI가 정상적으로 배포된 것을 확인할 수 있다.
  
![클라우드배포_CD01](https://github.com/user-attachments/assets/0cf4cfaa-7ad7-4e49-89de-45cd02b6b15f)
![클라우드배포_CD02](https://github.com/user-attachments/assets/892a47ce-ce39-479d-98ea-aa1db8f97bf5)
- Azure DevOps를 통해 Movie-CD가 정상적으로 배포된 것을 확인할 수 있다.
  
![클라우드배포_CD03](https://github.com/user-attachments/assets/2f16a71b-0b84-4cbf-95db-0cd8099a8caf)
- Kubernetes에서 movie 서비스가 잘 배포된 것을 확인할 수 있다.

# 컨테이너 자동확장 - HPA 
![hpa01](https://github.com/user-attachments/assets/134c165a-953d-4d47-a224-ef3dfc2a198b)
- movie 서비스의 pod가 최소 1개, 최대 3개, cpu 사용률 40%로 autoscaling 룰을 설정한다.
  
![hpa02](https://github.com/user-attachments/assets/72762bd6-73e6-48df-a40e-50314ea05884)
- siege를 통해 movie 서비스에 부하를 넣는다.
  
![hpa03](https://github.com/user-attachments/assets/c0721e64-0c09-4d1a-9f63-257bf2ee8539)
- 기존 pod의 cpu 사용률이 높아짐에 따라 pod 갯수가 최대 3개로 증가하는 것을 볼 수 있다.
  
![hpa04](https://github.com/user-attachments/assets/f7743797-f1aa-4253-81f0-13bc94732d6d)

# 컨테이너로부터 환경분리 - CofigMap/Secret

# 클라우드 스토리지 활용 - PVC 
![pvc01](https://github.com/user-attachments/assets/a993d212-5ba2-412c-8390-c16fcffb365c)
![pvc02](https://github.com/user-attachments/assets/08fa93e5-807f-4700-bf7f-032633854149)
- azurefile이라는 클라우드 스토리지를 생성한다.
  
![pvc03](https://github.com/user-attachments/assets/f3d4fca8-14e4-4145-8b5a-33eeb1e08b59)
- azurefile이라는 volume을 사용하는 movie 서비스의 pod1을 배포한다.
  
![pvc04](https://github.com/user-attachments/assets/e3eafad6-b585-4a95-9291-74e21b50a2a5)
- 배포한 movie 서비스의 pod1에서 test.txt파일을 생성해 /mnt/data에 저장한다.
  
![pvc05](https://github.com/user-attachments/assets/8642ce6c-1221-4bac-9eb7-ead0ffb2d7bc)
![image](https://github.com/user-attachments/assets/a8341bb8-d6a9-44e8-acdd-d144011b5549)
- 새로 배포한 movie 서비스의 pod2에서도 동일한 test.txt파일을 확인할 수 있다.

# 셀프 힐링/ 무정지 배포 - Liveness/Rediness Probe
![readiness01](https://github.com/user-attachments/assets/05f2097e-53b8-4f6d-95aa-47833f4f31f0)
![readiness02](https://github.com/user-attachments/assets/32493f48-b131-4e12-bd39-8724a239f9d3)
- readiness 코드가 없는 상태에서 서비스 중인 movie 서비스의 image버전을 수정해 재배포하면 도중에 서비스가 끊기는 구간이 존재한다.
   
![readiness03](https://github.com/user-attachments/assets/2fa01f45-cf1b-42bc-a21d-7e270833d190)
- 다음과 같이 readiness코드를 추가한다.
  
![readiness04](https://github.com/user-attachments/assets/1d03d588-34fa-42e6-a09c-ac5deb919e04)
- 서비스 중인 movie 서비스의 image버전을 수정해 재배포하더라도 중단없이 서비스가 지속되며 무정지 배포가 가능하다.

# 서비스 메쉬 응용 - Mesh 
![istio01](https://github.com/user-attachments/assets/3ff7a857-d50f-4cbc-81c1-0bd077daf291)
![istio02](https://github.com/user-attachments/assets/9dc49a37-a45a-428e-97d4-15fc4cc7d1bd)
- istio 시스템을 설치한다.
  
![istio03](https://github.com/user-attachments/assets/84fc0a88-3647-44ad-b39a-611f837934b0)
- istio 시스템의 injection을 true로 설정한다.
  
![istio04](https://github.com/user-attachments/assets/c80ed68f-fb2b-4cd5-9f9e-4e322b403b7a)
- movie 서비스의 pod가 istio의 sidecar와 같이 2/2로 구동하는 것을 확인할 수 있다.

# 통합 모니터링 - Loggregation/Monitoring
![모니터링01](https://github.com/user-attachments/assets/39d0cefa-d792-4fdd-847f-c6020e6799a0)
![모니터링02](https://github.com/user-attachments/assets/3c731e61-270b-4c11-a7dd-6ba3e1457b47)
- 모니터링에 필요한 서비스들을 모두 띄운다.
  
![모니터링03](https://github.com/user-attachments/assets/c17cebe7-28d5-4477-ade1-b3066cae6fe6)
- seige를 통해 movie 서비스에 부하를 넣으면 Prometheus에서 그래프로 확인할 수 있다.
  
![모니터링04](https://github.com/user-attachments/assets/927b4689-59ee-4d6c-b4d3-f6cfdb59bab8)
- Grandfa에서도 동일하게 확인할 수 있다.



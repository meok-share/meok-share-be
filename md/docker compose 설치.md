# Docker Compose 설치 및 배포

```text

#Perform a quick update on your instance:
$ sudo yum update -y

#Install git in your EC2 instance
$ sudo yum install git -y

#Check git version
$ git version

```

# 도커, 도커 컴포즈 설치 및 시작
> docker client와 server(docker daemon)간 통신 방식은 기본적으로 unix domain socket(IPC socket)을 사용한다.
내부적으로 /var/run/docker.sock파일을 사용하여 통신한다.
따라서, 아래와 같이 설치 후 권한을 부여한다.

```text
//도커 설치  
$ sudo yum install docker
$ docker -v

// 도커 컴포즈 설치 
$ sudo curl -L https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose   

// 도커 시작하기     
$ sudo systemctl start docker

// 실행 권한 적용   
$ sudo chmod +x /usr/local/bin/docker-compose    
$ sudo chmod 666 /var/run/docker.sock
$ docker-compose -v
```

# JDK 설치 및 jar 파일 생성
```text
# aws coreetto 다운로드
$ sudo curl -L https://corretto.aws/downloads/latest/amazon-corretto-11-x64-linux-jdk.rpm -o jdk11.rpm

# jdk11 설치
$ sudo yum localinstall jdk11.rpm
```

# 테스트 케이스 제외하고, jar 파일 빌드만 진행
```text
$ ./gradlew clean build -x test
```

# Docker 이미지 받고 Docker Compose 실행
```text
$ docker-compose up --build
```

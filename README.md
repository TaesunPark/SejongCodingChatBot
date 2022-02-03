# SJ Coding Helper

세종대학교 학생들을 위한 코딩 도우미 플랫폼 “SJ Coding Helper

![Untitled](SJ%20Coding%20Helper%207f474c93a2da4ef6aeb0632413514224/Untitled.png)

## 💡 Background

- 코딩 수업을 처음 접하는 비전공자, 신입생 학생
- 학생 수에 비해 부족한 조교 학생 수
- 실시간 소통이 불가한 기존 시스템
- 조교 인권이 보장되지 않는 기존 시스템
- 학습 자료를 찾기 힘든 신입생

## **📚 Stack & Library**

- Spring-boot, Spring JPA, Flask, Mysql, Nginx, Docker, AWS EC2, AWS RDS, Tensorflow, React.js
- Java, Python, Javascript
- zt-exec

## 👩‍💻 Project Features

## 1. 코딩 Q&A 챗봇 기능

<aside>
☑️ 코딩 수업을 수강하는 학생들이 시간 제약을 받지 않고 실시간으로 챗봇에게 질문 가능

</aside>

<aside>
☑️ 사용자 질문 문장에서 불용어 제거 후 키워드 추출 → 딥러닝 모델을 통해 사용자 질문에서 의도, 개체명 인식 → 인식한 의도, 개체명 키워드를 이용하여 답변 검색

</aside>

<aside>
☑️ 사용자 질문 키워드 추출 → TF-IDF, 코사인 유사도를 통한 키워드 유사도 → 유서도 높은 질문 추천

</aside>

## 2. 코드 컴파일러 기능

<aside>
☑️ 편리한 웹 컴파일 환경 제공

</aside>

<aside>
☑️ zt-exec Library를 사용해 서버 gcc Compiler, python3 에 접근한 다음 결과 값 추출

</aside>

<aside>
☑️ 조교에게 코드 및 입 출력 결과 간편 전송 가능

</aside>

## 3. TA조교와 채팅기능

<aside>
☑️ 챗봇으로 해결되지 않은 문제를 TA조교와 1대 1 채팅 질문

</aside>

<aside>
☑️ 코드 컴파일러에서 오류 발생 시 TA조교에게 질문 가능

</aside>

## 🎞️ Service UI

## 사용자 서비스

## 1. 챗봇 채팅 페이지

![Untitled](SJ%20Coding%20Helper%207f474c93a2da4ef6aeb0632413514224/Untitled%201.png)

## 2. 웹 컴파일러 페이지

![Untitled](SJ%20Coding%20Helper%207f474c93a2da4ef6aeb0632413514224/Untitled%202.png)

![Untitled](SJ%20Coding%20Helper%207f474c93a2da4ef6aeb0632413514224/Untitled%203.png)

![Untitled](SJ%20Coding%20Helper%207f474c93a2da4ef6aeb0632413514224/Untitled%204.png)

## 3. TA조교 채팅 페이지

![Untitled](SJ%20Coding%20Helper%207f474c93a2da4ef6aeb0632413514224/Untitled%205.png)

## 🛠️ Architecture

![Untitled](SJ%20Coding%20Helper%207f474c93a2da4ef6aeb0632413514224/Untitled%206.png)

## 💭 I Learned

- 처음으로 Infra 구축을 해봤습니다. SSL 인증서 적용 뿐만 아니라 배포 플로우를 짤 줄 알게 되었습니다.
- 무중단 배포 서비스를 위해 Nginx에서 로드 밸런싱 설정을 해줬습니다. 각 웹 어플리케이션들을 도커 컨테이너로 관리해주었고, 포트 포워딩을 함으로 써 하나의 웹 서버로 관리할 수 있게 설정해주었습니다.
- Spring-boot에서 시스템에 있는 Gcc컴파일러나 Python3를 실행할 때 자원에 대한 보안과 타임아웃을 고려해 zt-exec 라이브러리를 사용하였습니다. docker 컨테이너 내부에서 유저 모드로 웹 어플리케이션을 실행하고 시스템에 접근하는 유저 모드 권한을 뺏음으로써 보안 강화를 하였습니다.
- JPA를 도입하고, 채팅 시스템에서 다대다, 일대다 환경을 고려해주어 무결성을 지켰습니다.
- 스키마 구성, 챗봇, 채팅, 컴파일러 REST API 구축 하였습니다.

## 📺 Service Video

[https://www.youtube.com/watch?v=2Y8-H26Ypds](https://www.youtube.com/watch?v=2Y8-H26Ypds)

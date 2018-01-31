# PHOLAR Lite.

## 기간

2017.09.05 ~ 2017.09.15

## 역할

Main, Gallery, Post Layout 및 기능 구현, Like, Notification 기능 구현

## 소개

Firebase 의 주요 기능 공부를 목적으로 NAVER APP 인 'PHOLAR' 를 카피하기로 했습니다.</br></br>
__PHOLAR Lite.__ 는 사진 공유 SNS 로 유저가 사진을 필수로 한 글을 작성하고, 그 글을 통해 사람들과 소통하는 APP 입니다.</br></br>
일주일이라는 짧은 기간 동안 개발을 통해 특수한 기능들을 빼고, 기존에 학습했던 기능들과
배워보고 싶고, 사용해보고 싶은 기능들을 위주로 구현하였습니다.</br>

## 개발 환경

- 개발 언어 : Java, JavaScript
- 개발 환경 : JDK 1.8, SDK(Min 16, Target 23)
- 데이터베이스 : Firebase Realtime DB
- 개발 도구 : Android Studio 3.0, Firebase Console

## 요약

![Skills&Library](https://github.com/Hooooong/Pholar/blob/master/img/skills%26Library.PNG)

## 화면

![screenshot1](https://github.com/Hooooong/Pholar/blob/master/img/screen1.PNG)
![screenshot2](https://github.com/Hooooong/Pholar/blob/master/img/screen2.PNG)
![screenshot3](https://github.com/Hooooong/Pholar/blob/master/img/screen3.PNG)
![screenshot4](https://github.com/Hooooong/Pholar/blob/master/img/screen4.PNG)

## 사용 Skills

1. Firebase Authentication

    - Google 계정 연동 로그인

2. Firebase Database & Storage

    - Firebase 에서 제공되는 Realtime DB 를 통해 Post 의 정보를 Create, Read

    - Firebase Storage 에 Post 에 관련된 사진 파일 업로드

3. Firebase Function & FCM ( Firebase Cloud Messaging )

    - Post 의 `좋아요` 와 `댓글` 이 달리면 Post 작성자에게 알림(Notification)

    - Function 에 Script 를 작성하여 Notification 활성화

4. RecyclerView

5. Permission

6. ETC

    - Image Loading Library ( Glide )

## 소스 코드

  - [전체 소스코드](https://github.com/Hooooong/Pholar/tree/master/app/src/main/java/com/hooooong/pholar)

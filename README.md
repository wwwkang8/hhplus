## 요구사항 분석
1. 특강 신청 API 구현
- 특강신청 성공여부 조회
  요청 : GET  /reservation/{userId}
  응답 : SUCCESS 또는 FAIL
  
- 특강신청
  요청 : POST /reservation/{userId}
  응답 : SUCCESS 또는 FAIL

 
## 데이터베이스
![image](https://github.com/wwwkang8/hhplus_architecture_2nd/assets/26863285/9a951e82-06c1-4db7-8426-a73991a96ed8)

- 추후 특강이 여러개가 될 것을 대비하여 확장 가능하도록 특강정보 테이블을 따로 분리하였습니다.
- 강의내역 테이블에 INSERT 되면 강의신청 성공으로 보기 때문에 따로 강의신청 상태 필드값은 추가하지 않았습니다.



## 아키텍쳐 구조
- 구조 : 레이어드 아키텍쳐 + 클린 아키텍쳐 혼합형태
- Controller <---> Service <---> Repository 구조
- 인터페이스1. 컨트롤러와 서비스 사이에 인터페이스 존재

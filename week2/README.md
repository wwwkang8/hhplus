## 요구사항 분석
1. 특강 신청 API 구현
- 특강신청 성공여부 조회
  - 요청 : GET  /reservation/{userId}/{courseId}
  - 응답 : SUCCESS 또는 FAIL
  
- 특강신청
  - 요청 : POST /reservation/
  - Body : {"userId": "2", "courseId": "1"}
  - 응답 : SUCCESS 또는 FAIL

 
## 데이터베이스
![image](https://github.com/wwwkang8/hhplus_architecture_2nd/assets/26863285/9a951e82-06c1-4db7-8426-a73991a96ed8)

- 추후 특강이 여러개가 될 것을 대비하여 확장 가능하도록 특강정보 테이블을 따로 분리하였습니다.
- 강의내역 테이블에 INSERT 되면 강의신청 성공으로 보기 때문에 따로 강의신청 상태 필드값은 추가하지 않았습니다.



## 아키텍쳐 구조
- 구조 : 레이어드 아키텍쳐 + 클린 아키텍쳐 혼합형태
- Controller <---> Service <---> Repository 구조
- 인터페이스1. 컨트롤러와 서비스 사이에 인터페이스 존재

## 개발하면서 궁금한 점
- 동시성 제어 테스트할 때 비관적락을 이용해서 userId, courseId에 대해서 락을 걸었다.
  그런데 막상 테스트를 하니 DeadLock이 발생하고, 30명의 학생이 아닌 2명의 학생 신청만 INSERT 되어 있었다.
- 특강신청 정보를 검증하는 Validation 로직에서 RuntimeException 오류가 발생하니까
  프로그램이 동작하다가 죽어버린다. 그렇다보니 클라이언트에 응답을 보내주지 못하는 현상 발생. 해결방안 확인하기

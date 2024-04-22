# 첫 번째 단계: 빌드 단계
FROM gradle:7.5-jdk17 AS builder

# 작업 디렉토리를 설정합니다.
WORKDIR /Users/jeonghokang/IdeaProjects/dockerfile-folder/build/libs/

# 호스트의 소스코드를 현재 Docker 이미지의 작업 디렉토리로 복사합니다.
COPY . .

# 애플리케이션을 빌드합니다.(임시로 테스트 스킵)
RUN gradle build -x test

# 두 번째 단계: 실행 단계
FROM openjdk:17-jdk-slim

# 작업 디렉토리를 설정합니다.
WORKDIR /Users/jeonghokang/IdeaProjects/dockerfile-folder

# 첫 번째 단계에서 빌드한 JAR 파일을 복사합니다.
COPY --from=builder /Users/jeonghokang/IdeaProjects/dockerfile-folder/build/libs/concert.jar ./

# Spring Boot 애플리케이션을 실행합니다.
CMD ["java", "-jar", "concert.jar"]
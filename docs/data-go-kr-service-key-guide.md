# 공공데이터포털 API Service Key 설정 가이드

## 1. 목적

본 문서는 Findex 프로젝트에서 공공데이터포털 API를 호출하기 위해 필요한 `DATA_GO_KR_SERVICE_KEY` 설정 방법을 정리한 문서입니다.

공공데이터포털의 인증키는 외부에 노출되면 안 되는 민감 정보입니다.
따라서 `application.yaml`에 직접 작성하지 않고, `application-local.yaml`에서 환경변수를 통해 주입하는 방식으로 관리합니다.

---

## 2. 설정 파일 분리 기준

현재 프로젝트의 설정 파일은 다음 기준으로 분리합니다.

```text
application.yaml
→ 팀원 모두에게 공통으로 적용되는 설정

application-local.yaml
→ 개인 로컬 환경에서만 사용하는 설정
```

---

## 3. `application.yaml` 공통 설정

`application.yaml`에는 팀원 모두에게 동일하게 적용되는 설정을 작성합니다.

현재 프로젝트의 `application.yaml` 예시는 다음과 같습니다.

```yaml
spring:
  application:
    name: findex

  jpa:
    hibernate:
      ddl-auto: validate

  flyway:
    enabled: true
    locations: classpath:db/migration

findex:
  api:
    base-url: https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService
    stock-market-endpoint: getStockMarketIndex
```

### 공통 설정에 포함되는 항목

| 설정                                 | 설명                         |
| ---------------------------------- | -------------------------- |
| `spring.application.name`          | 애플리케이션 이름                  |
| `spring.jpa.hibernate.ddl-auto`    | JPA DDL 자동 생성 정책           |
| `spring.flyway.enabled`            | Flyway 사용 여부               |
| `spring.flyway.locations`          | Flyway migration script 위치 |
| `findex.api.base-url`              | 공공데이터포털 API 기본 URL         |
| `findex.api.stock-market-endpoint` | 주식 시장 지수 정보 조회 endpoint    |

공공데이터 API의 기본 URL과 endpoint는 팀원 모두 동일하게 사용하므로 `application.yaml`에 둡니다.

---

## 4. `application-local.yaml` 로컬 설정

`application-local.yaml`에는 개인 로컬 환경마다 달라질 수 있는 설정을 작성합니다.

현재 프로젝트의 `application-local.yaml` 예시는 다음과 같습니다.

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5431/findex
    username: findex
    password: findex1234

  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true

findex:
  api:
    service-key: ${DATA_GO_KR_SERVICE_KEY}
```

### 로컬 설정에 포함되는 항목

| 설정                           | 설명                   |
| ---------------------------- | -------------------- |
| `server.port`                | 로컬 서버 실행 포트          |
| `spring.datasource.url`      | 로컬 PostgreSQL 접속 URL |
| `spring.datasource.username` | 로컬 DB 사용자명           |
| `spring.datasource.password` | 로컬 DB 비밀번호           |
| `spring.jpa.show-sql`        | SQL 로그 출력 여부         |
| `hibernate.format_sql`       | SQL 로그 포맷팅 여부        |
| `findex.api.service-key`     | 공공데이터포털 API 인증키 환경변수 |

DB 포트, DB 계정, SQL 로그 출력 여부, 인증키는 개인 로컬 환경마다 다를 수 있으므로 `application-local.yaml`에 둡니다.

---

## 5. Service Key 설정 위치

공공데이터포털 API 인증키는 `application-local.yaml`에서 아래와 같이 설정합니다.

```yaml
findex:
  api:
    service-key: ${DATA_GO_KR_SERVICE_KEY}
```

여기서 중요한 부분은 다음 설정입니다.

```yaml
service-key: ${DATA_GO_KR_SERVICE_KEY}
```

`DATA_GO_KR_SERVICE_KEY`는 실제 인증키 값을 직접 작성하는 것이 아니라, 실행 환경의 환경변수에서 값을 읽어오겠다는 의미입니다.

즉, 실제 인증키는 코드나 설정 파일에 직접 작성하지 않고, 각자의 실행 환경에 등록합니다.

---

## 6. 왜 환경변수로 관리하는가?

공공데이터포털 Service Key를 코드에 직접 작성하면 다음과 같은 문제가 발생할 수 있습니다.

* GitHub에 인증키가 노출될 수 있습니다.
* 팀원마다 다른 인증키를 사용하는 경우 관리가 어렵습니다.
* 운영 환경과 로컬 환경의 설정을 분리하기 어렵습니다.
* 인증키 재발급 시 코드 수정이 필요해집니다.

따라서 실제 인증키는 환경변수로 관리하고, 프로젝트 설정 파일에는 환경변수 이름만 작성합니다.

---

## 7. 설정값 설명

| 설정                       | 설명                             |
| ------------------------ | ------------------------------ |
| `base-url`               | 공공데이터포털 API의 기본 URL            |
| `stock-market-endpoint`  | 주식 시장 지수 정보를 조회하는 API endpoint |
| `service-key`            | 공공데이터포털에서 발급받은 인증키             |
| `DATA_GO_KR_SERVICE_KEY` | 로컬 또는 실행 환경에 등록할 환경변수 이름       |

---

## 8. Service Key 사용 기준

공공데이터포털에서 Service Key를 발급받으면 보통 아래 두 가지 형태의 인증키를 확인할 수 있습니다.

```text
Encoding 인증키
Decoding 인증키
```

본 프로젝트에서는 팀 내 기준을 통일하기 위해 **Decoding 인증키를 사용합니다.**

즉, 환경변수 `DATA_GO_KR_SERVICE_KEY`에는 **공공데이터포털에서 제공하는 Decoding 인증키 값**을 등록합니다.

```text
DATA_GO_KR_SERVICE_KEY=공공데이터포털에서_발급받은_Decoding_인증키
```

### Decoding 인증키를 사용하는 이유

API 호출 코드를 작성할 때 `WebClient`, `RestTemplate`, `UriComponentsBuilder` 등을 사용하면 query parameter가 자동으로 인코딩될 수 있습니다.

이때 이미 인코딩된 Encoding 인증키를 다시 인코딩하면 인증 오류가 발생할 수 있습니다.

따라서 본 프로젝트에서는 다음 기준을 사용합니다.

```text
팀 내 Service Key 사용 기준: Decoding 인증키 사용
```

---

## 9. IntelliJ 실행 기준 설정 방법

본 프로젝트는 로컬 실행 시 주로 IntelliJ의 실행 버튼을 사용합니다.

따라서 IntelliJ에서 애플리케이션을 실행하는 경우에는 터미널에 환경변수를 설정하지 않아도 됩니다.
IntelliJ의 Run Configuration에만 환경변수를 등록하면 됩니다.

### 설정 경로

```text
Run → Edit Configurations...
```

또는 우측 상단 실행 설정 드롭다운에서:

```text
Edit Configurations...
```

### 설정 방법

1. 실행할 Spring Boot Application 선택
2. `Active profiles`에 `local` 입력
3. `Environment variables` 항목에 아래 값 추가

```text
DATA_GO_KR_SERVICE_KEY=공공데이터포털에서_발급받은_Decoding_인증키
```

예시:

```text
DATA_GO_KR_SERVICE_KEY=abcde12345...
```

설정 후 IntelliJ의 실행 버튼으로 애플리케이션을 다시 실행합니다.

### IntelliJ로만 실행하는 경우

IntelliJ 실행 버튼으로만 애플리케이션을 실행한다면 아래 CLI 설정은 하지 않아도 됩니다.

```bash
export DATA_GO_KR_SERVICE_KEY="..."
```

```powershell
$env:DATA_GO_KR_SERVICE_KEY="..."
```

즉, IntelliJ에서 실행하는 경우에는 아래 두 가지만 설정하면 됩니다.

```text
Active profiles: local
Environment variables: DATA_GO_KR_SERVICE_KEY=Decoding 인증키
```

---

## 10. Spring Profile로 로컬 설정 실행하기

`application-local.yaml`을 사용하는 경우 `local` profile로 실행해야 합니다.

### IntelliJ 실행 버튼 사용 시

IntelliJ에서는 Run Configuration에서 아래 항목을 설정합니다.

```text
Active profiles: local
```

그리고 환경변수에 아래 값을 추가합니다.

```text
DATA_GO_KR_SERVICE_KEY=공공데이터포털에서_발급받은_Decoding_인증키
```

IntelliJ 실행 버튼으로 실행하는 경우에는 이 설정만으로 충분합니다.

---

## 11. CLI 실행 시 환경변수 설정 방법

터미널 또는 명령어로 애플리케이션을 실행하는 경우에는 아래 방법으로 환경변수를 설정합니다.

IntelliJ 실행 버튼만 사용하는 경우에는 이 섹션을 생략해도 됩니다.

---

### Mac / Linux / Git Bash

터미널에서 아래 명령어를 실행합니다.

```bash
export DATA_GO_KR_SERVICE_KEY="공공데이터포털에서_발급받은_Decoding_인증키"
```

설정 확인:

```bash
echo $DATA_GO_KR_SERVICE_KEY
```

단, 위 방식은 현재 터미널 세션에서만 유지됩니다.
터미널을 닫으면 다시 설정해야 합니다.

---

### Mac / Linux에서 영구 설정

사용 중인 shell 설정 파일에 환경변수를 추가합니다.

#### zsh 사용 시

```bash
echo 'export DATA_GO_KR_SERVICE_KEY="공공데이터포털에서_발급받은_Decoding_인증키"' >> ~/.zshrc
source ~/.zshrc
```

#### bash 사용 시

```bash
echo 'export DATA_GO_KR_SERVICE_KEY="공공데이터포털에서_발급받은_Decoding_인증키"' >> ~/.bashrc
source ~/.bashrc
```

설정 확인:

```bash
echo $DATA_GO_KR_SERVICE_KEY
```

---

### Windows PowerShell

PowerShell에서 아래 명령어를 실행합니다.

```powershell
$env:DATA_GO_KR_SERVICE_KEY="공공데이터포털에서_발급받은_Decoding_인증키"
```

설정 확인:

```powershell
echo $env:DATA_GO_KR_SERVICE_KEY
```

이 방식은 현재 PowerShell 세션에서만 유지됩니다.

---

### Windows PowerShell에서 영구 설정

Windows 사용자 환경변수로 등록하려면 아래 명령어를 사용합니다.

```powershell
[System.Environment]::SetEnvironmentVariable("DATA_GO_KR_SERVICE_KEY", "공공데이터포털에서_발급받은_Decoding_인증키", "User")
```

설정 후에는 IntelliJ, 터미널, PowerShell을 다시 실행해야 반영됩니다.

설정 확인:

```powershell
echo $env:DATA_GO_KR_SERVICE_KEY
```

---

### Windows CMD

CMD에서는 아래 명령어를 사용할 수 있습니다.

```cmd
set DATA_GO_KR_SERVICE_KEY=공공데이터포털에서_발급받은_Decoding_인증키
```

설정 확인:

```cmd
echo %DATA_GO_KR_SERVICE_KEY%
```

현재 CMD 창에서만 유지됩니다.

---

## 12. CLI로 애플리케이션 실행하기

CLI로 실행하는 경우에는 `local` profile과 `DATA_GO_KR_SERVICE_KEY` 환경변수가 모두 설정되어 있어야 합니다.

### Mac / Linux / Git Bash

```bash
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

### Windows PowerShell

```powershell
$env:SPRING_PROFILES_ACTIVE="local"
.\gradlew.bat bootRun
```

CLI로 실행하는 경우에는 터미널 환경에도 `DATA_GO_KR_SERVICE_KEY`가 설정되어 있어야 합니다.

---

## 13. 로컬 설정 파일 관리

개인마다 로컬 DB 포트, 계정, 인증키 설정이 다를 수 있으므로 로컬 설정은 별도 파일로 관리합니다.

실제 로컬 실행 파일:

```text
application-local.yaml
```

예시 파일로 제공하려면 다음과 같은 이름을 사용할 수 있습니다.

```text
application-local.yaml.txt
```

또는:

```text
application-local.example.yaml
```

예시 파일을 실제 사용할 때는 `.txt` 확장자를 제거하거나, 파일명을 `application-local.yaml`로 변경합니다.

---

## 14. 인증키가 없을 때 발생할 수 있는 오류

`DATA_GO_KR_SERVICE_KEY`가 설정되지 않은 상태에서 애플리케이션을 실행하면 다음과 같은 문제가 발생할 수 있습니다.

```text
Could not resolve placeholder 'DATA_GO_KR_SERVICE_KEY'
```

또는 API 호출 시 인증 오류가 발생할 수 있습니다.

```text
SERVICE_KEY_IS_NOT_REGISTERED_ERROR
INVALID_REQUEST_PARAMETER_ERROR
SERVICE_ACCESS_DENIED_ERROR
```

이 경우 아래 내용을 확인합니다.

```text
1. DATA_GO_KR_SERVICE_KEY 환경변수가 설정되어 있는지 확인
2. IntelliJ 실행 버튼으로 실행한다면 Run Configuration에 환경변수가 등록되어 있는지 확인
3. local profile이 활성화되어 있는지 확인
4. 공공데이터포털 인증키가 Decoding 인증키인지 확인
5. 공공데이터포털 API 활용 신청이 완료되었는지 확인
6. API 호출 시 Service Key가 중복 인코딩되지 않는지 확인
```

---

## 15. Service Key 인코딩 주의사항

본 프로젝트에서는 **Decoding 인증키 사용을 기준으로 합니다.**

API 호출 시 다음 사항을 주의합니다.

* Encoding 인증키가 아니라 Decoding 인증키를 사용합니다.
* 이미 URL 인코딩된 키를 다시 인코딩하면 인증 오류가 발생할 수 있습니다.
* `WebClient`, `RestTemplate`, `UriComponentsBuilder`를 사용할 때 serviceKey가 중복 인코딩되지 않도록 주의합니다.
* 팀 내에서는 Service Key 형식을 Decoding 인증키로 통일합니다.

권장 방식은 다음과 같습니다.

```text
공공데이터포털에서 Decoding 인증키를 복사한다.
DATA_GO_KR_SERVICE_KEY 환경변수에 Decoding 인증키를 등록한다.
API 호출 코드는 serviceKey가 한 번만 인코딩되도록 작성한다.
```

예를 들어 `UriComponentsBuilder`를 사용한다면 실제 요청 URL이 어떻게 만들어지는지 로그로 확인하는 것이 좋습니다.

---

## 16. Git 관리 주의사항

실제 인증키가 포함된 파일은 Git에 커밋하지 않습니다.

`.gitignore`에 아래 항목을 추가할 수 있습니다.

```gitignore
application-local.yaml
.env
```

단, 예시 파일은 커밋해도 됩니다.

```text
application-local.yaml.txt
application-local.example.yaml
.env.example
```

예시 파일에는 실제 인증키를 넣지 않습니다.

```yaml
findex:
  api:
    service-key: ${DATA_GO_KR_SERVICE_KEY}
```

또는 `.env.example`을 만든다면 다음과 같이 작성합니다.

```text
DATA_GO_KR_SERVICE_KEY=공공데이터포털_Decoding_인증키를_입력하세요
```

---

## 17. 팀원 설정 안내 문구

팀원에게는 아래와 같이 안내하면 됩니다.

```text
공공데이터포털 API 호출을 위해 DATA_GO_KR_SERVICE_KEY 환경변수 설정이 필요합니다.

1. 공공데이터포털에서 Service Key를 발급받습니다.
2. Encoding 인증키가 아니라 Decoding 인증키를 사용합니다.
3. application-local.yaml을 본인 로컬 환경에 맞게 수정합니다.
4. 본인 DB 환경에 맞게 datasource 정보를 수정합니다.
5. IntelliJ Run Configuration에서 Active profiles를 local로 설정합니다.
6. IntelliJ Run Configuration의 Environment variables에 DATA_GO_KR_SERVICE_KEY를 등록합니다.
7. IntelliJ 실행 버튼으로 애플리케이션을 실행합니다.

IntelliJ 실행 버튼으로만 실행한다면 터미널 export 설정은 하지 않아도 됩니다.
실제 Service Key는 GitHub에 커밋하지 않습니다.
```

---

## 18. 설정 확인 체크리스트

애플리케이션 실행 전 아래 항목을 확인합니다.

```text
1. 공공데이터포털 Decoding 인증키를 사용하고 있는가?
2. IntelliJ Run Configuration에 DATA_GO_KR_SERVICE_KEY를 추가했는가?
3. IntelliJ Run Configuration에서 Active profiles를 local로 설정했는가?
4. application-local.yaml에서 service-key가 ${DATA_GO_KR_SERVICE_KEY}로 되어 있는가?
5. 실제 인증키를 application.yaml 또는 application-local.yaml에 직접 작성하지 않았는가?
6. application-local.yaml 또는 .env 파일에 실제 인증키가 포함되어 Git에 커밋되지 않는가?
7. 공공데이터포털 API 활용 신청이 완료되었는가?
8. API 호출 시 Service Key가 중복 인코딩되지 않는가?
```

---

## 19. 최종 정리

`DATA_GO_KR_SERVICE_KEY`는 공공데이터포털 API 호출에 필요한 인증키입니다.

본 프로젝트에서는 **Decoding 인증키**를 사용합니다.

공통 API 설정은 `application.yaml`에서 관리합니다.

```yaml
findex:
  api:
    base-url: https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService
    stock-market-endpoint: getStockMarketIndex
```

개인별 인증키 설정은 `application-local.yaml`에서 환경변수로 주입합니다.

```yaml
findex:
  api:
    service-key: ${DATA_GO_KR_SERVICE_KEY}
```

IntelliJ 실행 버튼 사용 시:

```text
Run → Edit Configurations → Active profiles
local
```

```text
Run → Edit Configurations → Environment variables
DATA_GO_KR_SERVICE_KEY=공공데이터포털에서_발급받은_Decoding_인증키
```

CLI 실행 시 Mac / Linux / Git Bash:

```bash
export DATA_GO_KR_SERVICE_KEY="공공데이터포털에서_발급받은_Decoding_인증키"
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

CLI 실행 시 Windows PowerShell:

```powershell
$env:DATA_GO_KR_SERVICE_KEY="공공데이터포털에서_발급받은_Decoding_인증키"
$env:SPRING_PROFILES_ACTIVE="local"
.\gradlew.bat bootRun
```

IntelliJ 실행 버튼으로만 실행한다면 IntelliJ Run Configuration 설정만 하면 됩니다.

실제 인증키는 절대 GitHub에 커밋하지 않습니다.

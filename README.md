# Docker 설치 및 OnamNotifier 백엔드 시스템 구동 가이드

이 가이드는 Docker Desktop 설치부터 Spring Boot 백엔드 프로젝트(`OnamNotifier`)를 Docker 컨테이너로 빌드하고 구동하는 전체 과정을 다룬다.

---

## 1. 설치 준비 (다운로드)

1. GitHub 저장소(`https://github.com/Dwk0910/OnamNotifier`)에 접속한다.<br/>
   ![스크린샷 2026-08-08 221132.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221132.png)<br/>

2. 우측 상단의 `<> Code` 초록색 버튼을 누르고 **Download ZIP**을 클릭하여 프로젝트 압축 파일을 다운로드한다. (Git CLI가 편하다면 `git clone`을 사용해도 된다.)<br/>
   ![스크린샷 2026-08-08 221223.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221223.png)<br/>

3. [Docker 공식 홈페이지](https://www.docker.com/products/docker-desktop/)에 접속한다.<br/>

4. **Download for Windows - AMD64** 버튼을 클릭하여 설치 프로그램(`Docker Desktop Installer.exe`)을 다운로드한다.<br/>
   ![스크린샷 2026-08-08 221312.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221312.png)<br/>

---

## 2. 설치

![스크린샷 2026-08-08 221405.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221405.png)<br/>
(다운로드 완료 후 폴더 상태)<br/>

1. 다운로드한 `OnamNotifier-master.zip`의 압축을 푼다.<br/>
   ![스크린샷 2026-08-08 221427.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221427.png)<br/>

2. 환경 변수 설정
   - 보기 옵션 변경<br/>
     ![스크린샷 2026-08-08 221656.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221656.png)<br/>
     - 탐색기 상단 메뉴 **보기** 클릭 → **표시** 클릭<br/>
     - **파일 확장명** 체크<br/>
   - 확장명이 없는 `.env` 파일 생성<br/>
     ![스크린샷 2026-08-08 221711.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221711.png)<br/>
     ![스크린샷 2026-08-08 221727.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221727.png)<br/>
     (뒤에 `.txt`가 붙지 않도록 주의)
   - `.env` 파일 내용 작성<br/>
     ![스크린샷 2026-08-08 221843.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221843.png)<br/>
     - 제공받은 `.env` 파일 내용을 붙여넣고 저장
   
3. 다운로드한 `Docker Desktop Installer.exe`를 실행한다.

4. **Configuration** 설정 창이 뜨면 아래와 같이 옵션을 체크한다.
   - `Per-user installation (Recommended)` 선택
   - `Use WSL 2 instead of Hyper-V` 체크
   - `Add shortcut to desktop` 체크 (필요 시)
   - **OK** 버튼 클릭<br/>
   ![스크린샷 2026-08-08 221527.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221527.png)<br/>

5. 설치가 완료되면 **Installation succeeded** 메시지를 확인하고 **Close**를 눌러 설치 프로그램을 종료한다.<br/>
   ![스크린샷 2026-08-08 221630.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221630.png)<br/>

---

## 3. Docker 최초 실행

설치 후 바탕화면이나 시작 메뉴에서 **Docker Desktop**을 실행한다.<br/>
(로그인 화면이 뜨면 오른쪽 위의 `skip`버튼 누르거나 로그인 후 진행)

### 💡 혹시 이런 오류가 보이시나요?
![스크린샷 2026-08-08 221946.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221946.png)
> **`Virtualization support not found` 또는 `WSL 2 installation is incomplete`**

Docker 실행 중 가상화 관련 에러가 발생한 경우, 아래 절차대로 해결할 수 있다.


1. **Windows 기능 켜기/끄기 설정**
    - 단축키 'Win + R' -> `optionalfeatures` 입력 후 확인<br/>
   ![스크린샷 2026-08-08 222006.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222006.png)<br/>
    - 다음의 항목 체크:<br/>
      ![스크린샷 2026-08-08 222032.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222032.png)<br/>
      ![스크린샷 2026-08-08 222041.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222041.png)<br/>
      ![스크린샷 2026-08-08 222045.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222045.png)<br/>
        - `Windows용 Linux 하위 시스템 (Windows Subsystem for Linux)`
        - `가상 머신 플랫폼 (Virtual Machine Platform)`
        - `Hyper-V` 체크 (Home 버전의 경우 가상 머신 플랫폼만 켜도 됨)
    - 확인 클릭 후 PC 재부팅

2. **WSL** 설치
    - **Docker Desktop** 실행 후, WSL 설치 안내가 뜨면 ENTER 키를 눌러 설치 진행<br/>
      ![스크린샷 2026-08-08 221909.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20221909.png)<br/>
      ![스크린샷 2026-08-08 222321.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222321.png)<br/>
      ![스크린샷 2026-08-08 222331.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222331.png)<br/>
    - `Quit` 버튼을 누른 다음 Docker Desktop 종료<br/>
      ![스크린샷 2026-08-08 222417.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222417.png)<br/>
    - Docker Desktop을 다시 실행하면 정상적으로 구동됨<br/>

![스크린샷 2026-08-08 222535.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222535.png)
(로딩 약 1~3분 소요)

![스크린샷 2026-08-08 222607.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222607.png)<br/>
(이런 화면이 뜨면 정상)

<details>
<summary>(만약 이래도 안된다면)</summary>

**BIOS/UEFI 가상화(Virtualization) 활성화**
- 컴퓨터 재부팅 후 BIOS/UEFI 진입 (F2, Del 키 등)
- Intel CPU: `Intel Virtualization Technology (VT-x)` -> **Enabled**
- AMD CPU: `SVM Mode` / `AMD-V` -> **Enabled**
- 저장 후 부팅

</details>

---

## 4. Docker Compose를 통한 백엔드 시스템 구동

1. 압축을 풀고 `.env` 파일을 작성한 `OnamNotifier-master` 폴더를 연다.

2. 빈 곳을 우클릭한 다음 **`터미널에서 열기`** 를 클릭한다.<br/>
   ![스크린샷 2026-08-08 222232.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222232.png)<br/>

3. 터미널에 아래 명령어를 입력하여 Docker 이미지 빌드 및 컨테이너 실행을 진행한다.
   ```bash
   docker compose up -d --build
   ```
   <br/>

   ![스크린샷 2026-08-08 222249.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222249.png)<br/>
   ![스크린샷 2026-08-08 222913.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222913.png)<br/>

4. 컨테이너 생성이 완료되고 **Started** 상태가 되었는지 확인한다.

---

## 5. 백엔드 정상 구동 테스트

1. 웹 브라우저를 열고 주소창에 아래 URL을 입력한다.
   ```
   http://localhost:8080
   ```
   
2. 브라우저 화면에 `HTTP ERROR 401` (또는 인증 실패에 따른 `401 Unauthorized` 에러 페이지)가 뜨는지 확인한다.<br/>
   ![스크린샷 2026-08-08 222941.png](instructions_screenshots/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202026-08-08%20222941.png)<br/>

>💡 참고: Spring Security 인증이 적용되어 있어 기본 엔드포인트 접근 시 401 에러가 발생하는 것이 정상이며, 이는 백엔드 서버가 8080 포트에서 정상 작동 중임을 의미한다.
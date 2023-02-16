# coding style practice

사용 기술:<br/>
* Spring Web ✔<br/>
* Spring Data JPA ✔<br/>
* Spring Security ✔<br/>

서버 정보:<br/>
* server port : 17443<br/>
* DB : H2 임베디드 사용<br/>


구동 방법:<br/>
* gitGub Clone 후에 buile 완료 후 어플리케이션 구동<br/>
* Postman 같은 Rest api 테스트 툴 사용하여 요청<br/>

Postman(Rest api 툴) 사용 시 순서:<br/>
* /test/register/member <-url 호출 하면 init member 데이터 Init <br/>
<br/>
* /test/register/post <-url 호출 하면 init post 데이터 Init <br/>
<br/>
* /login <- accountId:임대인ID, password:임대인1234 를 form 형식으로 post url 호출 <br/>
login이 성공하면 Postman으로 cookie 값 및 header값이 내려오게 됩니다.<br/> 
해당 값을 가지고 /auth로 시작하는 url들은 검증 가능합니다.<br/><br/>
/auth로 시작하는 url 제외하고는 로그인 없이 호출 가능합니다 ex) /post

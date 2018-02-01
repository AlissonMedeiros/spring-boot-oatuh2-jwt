# spring-boot-oauth2-jwt

[![CircleCI](https://circleci.com/gh/AlissonMedeiros/spring-boot-oatuh2-jwt/tree/master.svg?style=svg)](https://circleci.com/gh/AlissonMedeiros/spring-boot-oatuh2-jwt/tree/master)

### OAuth2 Server

Generate JWT Key

```
$ keytool -genkeypair -alias jwt -keyalg RSA -dname "CN=jwt, L=Berlin, S=Berlin, C=DE" -keypass mySecretKey -keystore jwt.jks -storepass mySecretKey
```

Put the key in `src/main/resources/jwt.jks`

```
$ keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey
▒▒▒▒▒▒Կ▒▒▒▒▒:  mySecretKey
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoLnfiQCCqhXbrRHg/hhR
RIBffp/B/c5xyXqKJ3QYKU2s3iPo9eVFNvZu80KzKhQ6CsTgzRHfujxVp3IOB/CN
tKPfx2P6ulIS0R9sA4mDiXINYLao8Kpg7uK865QehYitB5voMNDTzi3sjUBoKlK5
ps46Pd8YmuXmM7TxonFGYjaGGtdt+w0RiC5ggF3mvzk6AHUR1KupCNPpcsGNMYGG
ek4FMcxZf2QBJEtvRN76blwQiUDX6R7xx4yeKsew2sVU86hE14h2NbuvVtdDIOKM
+F76o3+zGQzn4/Ijcs9faWoHbLUmigEmYU08B2zc3/6eDiaFsa0Lcm8QCWprVfpe
DQIDAQAB
-----END PUBLIC KEY-----
-----BEGIN CERTIFICATE-----
MIIDGTCCAgGgAwIBAgIEULSkdTANBgkqhkiG9w0BAQsFADA9MQswCQYDVQQGEwJE
RTEPMA0GA1UECBMGQmVybGluMQ8wDQYDVQQHEwZCZXJsaW4xDDAKBgNVBAMTA2p3
dDAeFw0xNjExMDUwNjAyNTFaFw0xNzAyMDMwNjAyNTFaMD0xCzAJBgNVBAYTAkRF
MQ8wDQYDVQQIEwZCZXJsaW4xDzANBgNVBAcTBkJlcmxpbjEMMAoGA1UEAxMDand0
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoLnfiQCCqhXbrRHg/hhR
RIBffp/B/c5xyXqKJ3QYKU2s3iPo9eVFNvZu80KzKhQ6CsTgzRHfujxVp3IOB/CN
tKPfx2P6ulIS0R9sA4mDiXINYLao8Kpg7uK865QehYitB5voMNDTzi3sjUBoKlK5
ps46Pd8YmuXmM7TxonFGYjaGGtdt+w0RiC5ggF3mvzk6AHUR1KupCNPpcsGNMYGG
ek4FMcxZf2QBJEtvRN76blwQiUDX6R7xx4yeKsew2sVU86hE14h2NbuvVtdDIOKM
+F76o3+zGQzn4/Ijcs9faWoHbLUmigEmYU08B2zc3/6eDiaFsa0Lcm8QCWprVfpe
DQIDAQABoyEwHzAdBgNVHQ4EFgQUaisB+TYSddByoWa9a9Xhpjx3+YQwDQYJKoZI
hvcNAQELBQADggEBAHyLIstXg+O63ETlsyovscyGVv4F1MrWx3nmZT++mlr7Ivbw
UoOzG71knOkaAINox/BrPCciDddBIRkKDdT6orolMg1HiPZGwCt+DJ2c7J/kFyJ3
kCUeQp6JefHIKrQUeEErPSDaQpm+afc0mq5I/FP5Kg0aSg6sUr1SJfqG6aEWf/pg
8V8I3/bGFG1QzHER3R/hX2f+09UElgIvIK8KTJoT1EnVRbzDyG0IEvvheIk/TXG+
ICaxFrDCkavbP2Swx7HuMNi9FQEIQ7lwPYtzX6cfeYYNHJ1CR70uN9YYkroTRWLx
4vsgVFhzRbGvnW5Ufv18lI0IThHsU395F/75aFw=
-----END CERTIFICATE-----
```

### OAuth2 Server

#### 1 - Password grant types

* User: `user`

```
$ curl "acme:acmesecret@localhost:8080/oauth/token" -d "grant_type=password&username=user&password=user"
return: {"access_token":"eyJ...
```

* User `admin`

```
$ curl "acme:acmesecret@localhost:8080/oauth/token" -d "grant_type=password&username=admin&password=admin"
return:  {"access_token":"eYj...
```

#### 2 - refresh_token

`password`authorization_code``accesstoken`，`refresh_token`，`/oauth/token -d grant_type=refresh_token -d refresh_token=$refresh_token``accesstoken`

* password

```
curl "acme:acmesecret@localhost:8080/oauth/token" -d "grant_type=password&username=user&password=user"
return: {"access_token":"eyJ...
```
`access_token` `refresh_token`，`refresh_token``/oauth/token -d grant_type=refresh_token -d refresh_token``accesstoken`

```
curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=refresh_token -d refresh_token=${TOKEN}
return: {"access_token":"eyJ...
```

* authorization_code

Access this url to get a Code (you need to get the code in the response):

`http://localhost:8080/oauth/authorize?response_type=code&client_id=acme&redirect_uri=https://www.google.com.br` 

The response is like this:

`https://www.google.com.br/?code=Axjz0f`

Replace the code:

```
curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=authorization_code -d client_id=acme -d redirect_uri=https://www.google.com.br -d code={NEW_CODE}
return: {"access_token":"eyJhb...
```

`/oauth/token -d grant_type=refresh_token -d refresh_token=$refresh_token``accesstoken`

```
curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=refresh_token -d refresh_token=$[TOKEN]
return: {"access_token":"eyJh...
```

#### 3 - client_credentials

grant_type=client_credentials

```
curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=client_credentials -d scope=openid
{"access_token":"eyJ..
```

#### 4 - authorization_code

`authorization_code``accesstoken`：

1 - `/oauth/authorize?response_type=code``code`

2 - `/oauth/token -d grant_type=authorization_code``accesstoken`

```
http://localhost:8080/oauth/authorize?response_type=code&client_id=acme&redirect_uri=https://www.google.com.br
```

`https://www.google.com.br/?code=8JsYwV`，`code`accesstoken

```
$ curl acme:acmesecret@localhost:8080/oauth/token -d grant_type=authorization_code -d client_id=acme -d redirect_uri=https://www.google.com.br -d code=8JsYwV
return: {"access_token":"eyJ...
```

#### 5 - implicit

`token``accesstoken`（/oauth/authorize?response_type=token）`accesstoken`

```
http://localhost:8080/oauth/authorize?response_type=token&client_id=acme&redirect_uri=https://www.google.com.br
```
The response is like this:

`https://www.google.com.br/#access_token=eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE0NzgzNzg4NjEsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUkVBRCIsIldSSVRFIl0sImp0aSI6ImEyMzE3NTlmLTZhMzktNGQ3YS1iMmMyLWIwOTdlMWZjZTEwMiIsImNsaWVudF9pZCI6ImFwcENsaWVudCIsInNjb3BlIjpbIm9wZW5pZCJdfQ.ASSLsAJa8CsgZDsv5vH8BqYTbmoCCeYKqSmv4m9jl2XpWc2edvauy89Vxvj8z6kKGr8QqDg786u6MMW7fX5CAjP34Mfs9XVI8gfg20Xk0sHoS3WPx0mseIXdJbaxhj0526X5947-eeMr_LDC5N_XlPQ3Qq_PcY3tmyh92IWUri2rRJMKEPHrmVqqWPcPcCSHoEaaMWNTq_gsdbsZiyX4jaW24LVQ0HZ4oYMnmUzbLCvIyPcKF7WR-KKEnOykYX5FJPjnbUz6EK5yG_icdkULsxmDr05JrEgkKR0n_JfL9_gOqpI8mpJFBkAUghM1y9No_fGvvhb22o-H8ar5wnGqYA&token_type=bearer&expires_in=43199&scope=openid&jti=a231759f-6a39-4d7a-b2c2-b097e1fce102`


### Based on:

https://github.com/ameizi/spring-boot-oauth2-example

http://callistaenterprise.se/blogg/teknik/2015/04/27/building-microservices-part-3-secure-APIs-with-OAuth/

http://docs.spring.io/spring-security/site/docs/4.2.2.RELEASE/reference/htmlsingle/#user-schema

https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql

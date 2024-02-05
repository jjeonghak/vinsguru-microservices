# [ 02-reactive-microservices-with-spring-webflux ]
> Spring Webflux 기초 학습을 위한 디렉토리  
> 간단한 마이크로서비스 적용 실습  

<br>

## 학습 내용
| **TITLE** | **DESCRIPTION** | **DIR** |
| ------- | ------- | ------- |
| **_Webflux_** | - _Spring Webflux_ <br> - _Functional Endpoints_ <br> - _RouterFunction, RouterHandler_ | [_webflux-demo-main_](./webflux-demo/src/main/java/com/vinsguru/webfluxdemo/) |
| **_Webflux Test_** | - _WebClient_ <br> - _WebTestClient_ | [_webflux-demo-test_](./webflux-demo/src/test/java/com/vinsguru/webfluxdemo/) |
| **_Simple Microservice_** | - _Reactive MongoDB_ <br> - _Data R2DBC_ <br> - _Server Sent Events_ | [_order-service_](./msa-http/order-service) <br> [_product-service_](./msa-http/product-service) <br> [_user-service_](./msa-http/user-service) |

<br>

## Simple Microservice - Structure
<img src="https://private-user-images.githubusercontent.com/77607258/302162646-c49aadc5-a0eb-4ea5-9003-11bb1947903e.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDcxMDIxOTYsIm5iZiI6MTcwNzEwMTg5NiwicGF0aCI6Ii83NzYwNzI1OC8zMDIxNjI2NDYtYzQ5YWFkYzUtYTBlYi00ZWE1LTkwMDMtMTFiYjE5NDc5MDNlLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjA1VDAyNTgxNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTdmMzRkOGI4MGVlNjRmZDBjZmViY2I3MmZkMjM5M2ZiODVhYmVjZjgzMzBhMGUxNDdjNWFhNjk4YTg5Mjc5ZjQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.tl45XyHSuKZr6D5FnDWKc-UT4-zqYKjDmLOjUVnpgis">

<br>


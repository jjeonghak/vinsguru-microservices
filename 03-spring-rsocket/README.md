# [ 03-spring-rsocket ]
> Spring RSocket 기초 학습을 위한 디렉토리  
> RSocket 동작 및 연결  
> 간단한 마이크로서비스 적용 실습  

<br>

## SUMMARY
| **TITLE** | **DESCRIPTION** | **DIR** |
| ------- | ------- | ------- |
| **RSocket** | - _RSocket_ <br> - _SocketAcceptor_ <br> - _RSocketServer_ <br> - _CloseableChannel_ | [_rsocket_](./rsocket) |
| **Spring RSocket** | - _Request & Response_ <br> - _Fire & Forget_ <br> - _Request Stream_ <br> - _Request Channel_ <br> - _SSL/TLS_ <br> - _Loadbalance_| [_spring-rsocket_](./spring-rsocket) |
| **MSA RSocket** | - _RSocketConnectorConfigurer_ <br> - _RSocketStrategiesCustomizer_ <br> - _MimeType_ | [_msa-rsocket_](./msa-rsocket) |


<br>

## RSocket
* RScocket is a binary protocol for client/server application  
* Peer-To-Peer messaging  
* Support Reactive-Streams
* Developed by Netflix
* Support other languages like js, go, python, kotlin etc

<br>

## Differences - WebFlux and RSocket
1. WebFlux
    * Non-blocking & Async communication
    * Request & Response
    * HTTP
    * Layer 7
    * JSON
    * Client has to initiate a request to receive response
    * Tools support

2. RSocket
    * Non-blocking & Async communication
    * Request & Response / Request stream / Bi-directional streaming / Fire & Forget
    * TCP / WebSocket
    * Layer 5 / 6
    * Binary
    * Pub / Sub
    * Stream resume
    * Backpressure
    * New protocl - Lcak of tools

<br>

## RSocket - Interaction Models
| **MODEL** | **PROTOCOL** | **RELATION** | **USAGE** |
| ------- | ------- | ------- | ------- |
| _Request & Response_ | _HTTP_ | _1:1_ | _Any typical request and response_ |
| _Fire & Forget_ | _TCP / WebSocket_ | _1:0_ | _Sign out_ |
| _Request Stream_ | _TCP / WebSocket_ | _1:N_ | _Uber Driver location uodates_ |
| _Request Channel_ | _TCP / WebSocket_ | _M:N_ | _Interactive communication like Game, Chat etc_ |

<br>

<img src = "https://private-user-images.githubusercontent.com/77607258/302181165-9f039455-dc5d-4758-81d0-0fe2de4c47c1.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MDcxMTAwMDYsIm5iZiI6MTcwNzEwOTcwNiwicGF0aCI6Ii83NzYwNzI1OC8zMDIxODExNjUtOWYwMzk0NTUtZGM1ZC00NzU4LTgxZDAtMGZlMmRlNGM0N2MxLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAyMDUlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMjA1VDA1MDgyNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTNjNDhkNGFkNzQ5MzQ4NzFhNWIzMjVlOGRjMDVkNTA1NGRjNWMyMzdmYWVhZDFiNDcxYjdiNDdhMjBiMDhlNTEmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.MZ3PqnNp8hxg1qWLxJnhBfge7ZxGUaWnShYHoaLtmZ0">

<br>

## Spring RSocket
* Spring auto configuration
* Serialization & Deserialization
* Dependency Injection
* Exception Handling
* SSL/TLS
* Routing

<br>

## Spring RSocket API Models
1. Request & Response
    * Mono&lt;T&gt; -> Mono&lt;R&gt;

2. Fire & Forget
    * Mono&lt;T&gt; -> Mono&lt;Void&gt;

3. Request Stream
    * Mono&lt;T&gt; -> Flux&lt;R&gt;

4. Request Channel
    * Flux&lt;T&gt; -> Flux&lt;R&gt;

<br>

## Validation / Error Handling
* RSocket treats app exceptions as an ApplicationErrorException(String message)
* Rasing error signal will end the response
* Prefer defaultIfEmpty / switchIfEmpty / onErrorReturn / onErrorResume
* Send exception / error details as item response
* Use @MessageExceptionHandler

<br>

## Spring RSocket - Connection / Retry / Resume
* @ConnectMapping
* @ConnectMapping("some.path") - setupRoute
* @ConnectMapping can accept any object - setupData
* Broken connection due to server restart / deployment will be taken care
* reconnect(retry) - to wait for the server to be up to send a requeest
* resume(retry) - for session resumption for streaming requests/responses

<br>

## Load Balancing
1. Server-Side
    * Use Nginx round robin

2. Clienet-Side
    * Use Flux&lt;List&lt;LoadbalanceTarget&gt;&gt;

<br>

## RSocket - SSL/TLS
* Create .p12 file
  * Command : keytool -genkeypair -alias rsocket -keyalg RSA -keysize 2048 -storetype PKCS12 -validity 3650 -keystore rsocket-server.p12 -storepass password
* Create .pem file
  * Command : keytool -exportcert -alias rsocket -keystore rsocket-server.p12 -storepass password -file cert.pem
* Create .truststore file
  * Command : keytool -importcert -alias rsocket -keystore client.truststore -storepass password -file cert.pem

<br>


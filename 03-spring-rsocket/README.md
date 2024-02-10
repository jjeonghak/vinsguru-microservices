# [ 03-spring-rsocket ]
> Spring RSocket 기초 학습을 위한 디렉토리  
> RSocket 동작 및 연결  
> 간단한 마이크로서비스 적용 실습  

<br>

## SUMMARY
| **TITLE** | **DESCRIPTION** | **DIR** |
| ------- | ------- | ------- |
| **_RSocket_** | - _RSocket_ <br> - _SocketAcceptor_ <br> - _RSocketServer_ <br> - _CloseableChannel_ | [_rsocket_](./rsocket) |
| **_Spring RSocket_** | - _Request & Response_ <br> - _Fire & Forget_ <br> - _Request Stream_ <br> - _Request Channel_ <br> - _SSL/TLS_ <br> - _Loadbalance_| [_spring-rsocket_](./spring-rsocket) |
| **_MSA RSocket_** | - _RSocketConnectorConfigurer_ <br> - _RSocketStrategiesCustomizer_ <br> - _MimeType_ | [_msa-rsocket_](./msa-rsocket) |

<br>

## MSA RSocket - Structure
<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/03-rsocket-msa.png" width="600" height="500">

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

<img src = "https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/03-rsocket-model.png" width="500" height="400">

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


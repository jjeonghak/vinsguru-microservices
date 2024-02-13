# [ 05-design-patterns-with-webflux ]
> Microservice 디자인 패턴 학습을 위한 디렉토리  
> Microservice 디자인 패턴 활용 실습  

<br>

## SUMMARY
| **TITLE** | **DESCRIPTION** | **DIR** |
| ------- | ------- | ------- |
| **_Integration Patterns_** | - _Gateway Aggregator Pattern_ <br> - _Scatter Gather Pattern_ <br> - _Orchestrator Pattern(parallel)_ <br> - _Orchestrator Pattern(sequential)_ <br> - _Splitter Pattern_ <br> | [_sec01_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec01/) <br> [_sec02_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec02/) <br> [_sec03_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec03/) <br> [_sec04_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec04/) <br> [_sec05_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec05/) |
| **_Resilient Patterns_** | - _Timeout Pattern_ <br> - _Retry Pattern_ <br> - _Circuit Breaker Pattern_ <br> - _Rate Limiter Pattern_ <br> - _Bulkhead Pattern_ <br> | [_sec06_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec06/) <br> [_sec07_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec07/) <br> [_sec08_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec08/) <br> [_sec09_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec09/) <br> [_sec10_](./webflux-patterns/src/main/java/com/vinsguru/webfluxpatterns/sec10/) |

<br>

## Gateway Aggregator Pattern
* Ask all our upstream services for a different part  
* And aggregator assemble them  
* Look like zip method  

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/05-gateway-aggregator-pattern.png" width="500" height="400">


<br>

## Scatter Gether Pattern
* Ask all our upstream services for a same type of messages
* And choose the best one or gather everything
* Look like merge method

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/05-scatter-gether-pattern.png" width="600" height="400">

<br>

## Orchestrator Pattern
* Aggregator + additional business logic to provide a workflow  

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/05-orchestrator-pattern.png" width="600" height="400">

<br>

## Orchestrator Pattern(Chained/Sequential workflow)
* Any service can assume the role of aggregation
* Increased latency
* Difficult to debug and to maintain change in requirement

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/05-orchestrator-sequential-pattern.png" width="700" height="300">

<br>

## Splitter Pattern
* Look like Scatter Gether Pattern
* But it has opposite flow(stream)

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/05-splitter-pattern.png" width="600" height="400">

<br>

## Timeout Pattern
* One of the simplest resilience patterns to implement
* Treat the request as failed if the response was not received within the given timeout

<br>

## Retry Pattern
* Recover from transient failure
* It might increase the overall response time
* Do not forget to set timeout
* Do not retry for 4xx error
  
<br>

## Circuit Breaker Pattern
* To allow the client service to operate normally when the upstream service is not healthy
* Resilience4j
  * Spring, Reactor suppots
  * Ratelimiter, Bulkhead etc

1. CLOSED
    * Dependent service is UP
    * All requests are sent

2. OPEN
    * Dependent service is DOWN
    * All requests are not sent

3. HALF OPEN
    * Dependent service is MIGHT BE UP
    * Only few requests are sent to check

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/05-circuit-breaker-pattern.png" width="400" height="300">

<br>

## Rate Limiter Pattern
1. Server Side Rate Limiter
    * To limit the number of requests being served by a server node
    * To protect system resiurces from overload
    * Very important CPU intensive tasks

2. Client Side Rate Limiter
    * To limit the number of requests sent by client to the server
    * To reduce the cost/respect the contract

<br>

## Bulkhead Pattern
* RateLimiter
  * Number of requests per time window
  * Reject other calls
* Bulkhead
  * Number of concurrent calls
  * Queue other calls

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/05-bulkhead-pattern.png" width="500" height="500">

<br>

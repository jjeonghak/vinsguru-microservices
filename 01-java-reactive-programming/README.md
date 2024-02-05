# [ 01-java-reactive-programming ]
> Spring Reactor 기초 학습을 위한 디렉토리  
> Mono, Flux 기본 활용 방법에 대해 기술

</br>

## SUMMARY 
| **TITLE** | **DESCRIPTION** | **DIR** |
| ------- | ------- | ------- |
| **_Mono Basic_** | - _stream, just, subscribe_ <br> - _empty, error_ <br> - _fromSupplier, fromFuture, fromRunnable_  | [_sec01_](./reactor/src/main/java/project/reactor/sec01) |
| **_Flux Basic 1_** | - _multiple subscribers_ <br> - _fromIterable, fromArray, fromStream, fromMono_ <br> - _range, interval_ <br> - _subscription_ <br> | [_sec02_](./reactor/src/main/java/project/reactor/sec02) |
| **_Flux Basic 2_** | - _create, generate_ <br> - _take, push_ <br> | [_sec03_](./reactor/src/main/java/project/reactor/sec03) |
| **_Basic Operator_** | - _handle_ <br> - _doCallbacks_ <br> - _limitRate, delayElements_ <br> - _onError, timeout_ <br> - _defaultIfEmpty, switchIfEmpty_ <br> - _transform_ <br> - _switchOnFirst_ <br> - _flatMap, concatMap_ | [_sec04_](./reactor/src/main/java/project/reactor/sec04) |
| **_Hot & Cold Publisher_** | - _cold & hot publisher_ <br> - _refCount, autoConnect_ <br> - _cache_ | [_sec05_](./reactor/src/main/java/project/reactor/sec05) |
| **_PublishOn & SubscribeOn_** | - _publishOn, subscribeOn_ <br> - _parallel, schedulers_ | [_sec06_](./reactor/src/main/java/project/reactor/sec06) |
| **_Overflow Strategy_** | - _onBackpressureDrop_ <br> - _onBackpressureLatest_ <br> - _onBackpressureError_ <br> - _onBackpressureBuffer_ | [_sec07_](./reactor/src/main/java/project/reactor/sec07) |
| **_Combine Operator_** | - _startWith, concatWith_ <br> - _merge, zip, conbineLatest_ | [_sec08_](./reactor/src/main/java/project/reactor/sec08) |
| **_Batching Operator_** | - _buffer, bufferTimeout_ <br> - _window, groupBy_ | [_sec09_](./reactor/src/main/java/project/reactor/sec09) |
| **_Repeat & Retry_** | - _repeat_ <br> - _retry, retryWhen_ | [_sec10_](./reactor/src/main/java/project/reactor/sec10) |
| **_Sink_** | - _Sinks.one_ <br> - _Sinks.many.unicast_ <br> - _Sinks.many.multicast_ <br> - _Sinks.many.replay_ | [_sec11_](./reactor/src/main/java/project/reactor/sec11) |
| **_Context_** | - _contxtWrite_ <br> - _deferContextual_ | [_sec12_](./reactor/src/main/java/project/reactor/sec12) |
| **_StepVerifier_** | - _expect, assert, verify_ <br> - _await, as_ | [_test_](./reactor/src/test/java/project/reactor/test) |

<br>

## Reactor Publisher
1. Mono&lt;T&gt;
    * Emit 0 or 1 item

2. Flux&lt;T&gt;
    * Emit 0 or N item
    * Like a list or stream

<br>

## Subscriber
* onNext - Consumer&lt;T&gt;
* onError - Consumer&lt;Throwable&gt;
* onComplete - Runnable

<br>

## Hot Publisher
| **METHOD** | **USAGE** |
| ------- | ------- |
| _publish().refCount(1)_ | _At least 1 subscriber._<br>_It will reconnect later when all the subscribers cancelled and some new subscriber appears._ |
| _publish().autoConnect(1)_ | _Same as above._<br>_But no resubscription._<br>_If the source emits, subscribers will receive item._ |
| _publish().autoConnect(0)_ | _Real hot publisher - no subscriber required_ |
| _cache()_ | _Cache the emitted item for late subscribers_ |

<br>

## Schesulers
* For upstream - subscribeOn
* For downstream - publishOn

<br>

| **METHOD** | **USAGE** |
| ------- | ------- |
| _boundedElastic_ | _Network / Time-consuming calls_ |
| _parallel_ | _CPU intensive tasks_ |
| _single_ | _A single dedicated thread for one-off tasks_ |
| _immediate_ | _Current thread_ |

<br>

* All the operations are always executed in sequential  
* Data is processed one by one via 1 thread in the ThreadPool for a Subscriber  
* Schesulers.parallel method is a thread pool for CPU tasks - Does not mean parallel execution  

<br>

## Backpressure / Overflow Strategy

| **STRATEGY** | **BEHAVIOR** |
| ------- | ------- |
| _buffer_ | _Keep in memory_ |
| _drop_ | _Once the queue is full, new items will be dropped_ |
| _latest_ | _Once the queue is full, keep 1 latest item as and when it arrives - drop old_ |
| _error_ | _Throw error th the downstream_ |

<br>

## Repeat / Retry
* Repeat - resubscribe after complete signal
* Retry - resubscribe after error signal

<br>

## Sinks
| **TYPE** | **BEHAVIOR** | **PUB:SUB** |
| ------- | ------- | ------- |
| _one_ | _Mono_ | _1:N_ |
| _many-unicast_ | _Flux_ | _1:1_ |
| _many-multicast_ | _Flux_ | _1:N_ |
| _many-replay_ | _Flux_ | _1:N(with replay of all values to late subscribers)_ |


<br>

## StepVerifier
1. Start
    * StepVerifier.create()

2. Next
    * expectNext(...)
    * expectNextCount(...)
    * thenConsumeWhile(...)

3. Verify
    * verifyComplete()
    * verify(Duration)
    * verifyError()

4. For slow publisher
    * StepVerifier.withVirtualTime(() -> getFlux())
    * thenAwait(Duration)

5. StepVerifierOptions
    * Context
    * Scenario name

<br>









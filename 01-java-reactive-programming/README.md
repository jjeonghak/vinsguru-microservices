# [ 01-java-reactive-programming ]
> Spring Reactor 기초 학습을 위한 디렉토리  
> Mono, Flux 기본 활용 방법에 대해 기술

</br>

## 학습 내용
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

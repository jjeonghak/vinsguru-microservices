# [07-reactive-kafka-from-scratch ]
> Kafka 및 Spring Webflux 기초 학습을 위한 디렉토리  
> Kafka 활용 실습  


<br>

## SUMMARY
| **TITLE** | **DESCRIPTION** | **DIR** |
| ------- | ------- | ------- |
| **_Kafka Playground_** | - _Shell Script_ <br> - _Docker Setup_ | [_kafka-playground_](./kafka-playground/) |
| **_Reactive Kafka Playground_** | - _Apache - KafkaConsumer, ConsumerRecord_ <br> - _Apache - KafkaProducer, ProducerRecord_ <br> - _Reactor - KafkaReceiver, ReceiverRecord_ <br> - _Reactor - KafkaSender, SenderRecord_ | [_sec01_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec01) <br> [_sec02_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec02) |
|  | - _Max In Flight_ | [_sec03_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec03) |
|  | - _RecordHeaders_ | [_sec04_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec04) |
|  | - _Partition, Partition Strategy_ <br> - _Consumer Group, Rebalancing_ | [_sec05_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec05) <br> [_sec06_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec06) |
|  | - _Seek Offset, AddAssignListener_ | [_sec07_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec07) |
|  | - _ReceiveAutoAck, Max Poll Records_ <br> - _ConcatMap, FlatMap, GroupBy_ | [_sec09_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec09) <br> [_sec10_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec10) <br> [_sec11_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec11) |
|  | - _Error Handling_ <br> - _Pipeline, Retry_ <br> - _Dead Letter_ <br> - _Poison Pill_ | [_sec12_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec12) <br> [_sec13_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec13) <br> [_sec14_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec14) |
|  | - _ReactiveKafkaConsumerTemplate_ <br> - _ReactiveKafkaProducerTemplate_ | [_sec16_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec16) <br> [_sec17_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec17) |
|  | - _SASL, PLAINTEXT, SSL_ <br> - _SaslConfigs, CommonClientConfigs, SslConfigs_ | [_sec18_](./reactive-kafka-playground/src/main/java/com/vinsguru/reactivekafkaplayground/sec18) |
|  | - _Embedded Kafka_ | [_test_](./reactive-kafka-playground/src/test/java/com/vinsguru/reactivekafkaplayground/) |
| **_MSA Kafka_** | - _ReactiveKafkaConsumerTemplate_ <br> - _ReactiveKafkaProducerTemplate_ <br> - _Persistable, ReactiveCrudRepository_ | [_msa-kafka_](./msa-kafka/) |

<br>

## Kafka
* Open source distributed event streaming platform 
* Capturing any events in real time and storing for later retrieval / processing events in real time
  * Low latency
  * High throughput
  * Horizontally scalable
  * Data is distributed
 
<br>

## Event
* Event is anything that happened in the business domain
  * someone liked a tweet(lkies-event)
  * someone placed an order(order-event)
* Also known as `Records`, `Messages`

<br>

## Topic
* It is a way of organizing data within a Kafka Cluster
* Can be divied into multiple partitions
* Offset is the position of the message within the partition

<br>

## Kafka Server
1. Broker(read + write)
2. Controller(managing the cluster)
3. Broker + Controller

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-kafka-structure.png" width="500" height="400">

<br>

## Bootstrap-Server
* A kafka cluster can have N number of servers
* A set of servers can act like a bootstrap-servers to provide initial metadata

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-kafka-flow.png" width="600" height="400">

<br>

## Producer
* Producer `collects` the messages and `delivers` them as batches to Kafka server
* How much time to collect
  * linger.ms - default 0
* How many bytes to collect
  * batch.size(bytes) - default 16KB

<br>

## Consumer
* `Pull` based, not Push based
* Consumer asks Kafka to give the messages(polling)
* How many messages you want to recive as part of a single poll request
  * max.poll.records

<br>

## Partition / Consumer Group / Scaling
* A new consumer joining or leaving the consumer group, do partition reassignment
* There is a problem that keys of existing consumers be mixed

* Messaage Ordering Issues
  >	1. Accept the message ordering issues for a short period of time  
  > Stop the producer  
  > Drain the existing partitions  
  > Start the producer
  >
  >	3. Create a new topic with 4 partitions  
  > Let the producer send messages to the new topic  
  > Let the consumers consume new topic once they have processed old topic messages  


<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-consumer-group.png" width="600" height="400">

<br>

````java
// producer side partition method
public static int partitionForKey(byte[] serializedKey, int numPartitions) {
    return Utils.toPositive(Utils.murmur2(serializedKey)) % numPartitions;
}
````

<br>

## Reset Offset

| **OPTIONS** | **DESCRIPTION** |
| ------- | ------- |
| _shift-by 5_ <br> _shift-by -5_ | _moves the offset by the given N_ |
| _by-duration PT5M_ | _moves the offset by 5 minutes_ |
| _to-datetime 2023-01-01T00:00:00.000_ | _moves the offset by date time_ |
| _to-earliest_ | _from beginning_ |
| _to-latest_ | _latest_ |

<br>

## Message

| **Attributes** | **DESCRIPTION** |
| ------- | ------- |
| _Key_ | _nullable_ |
| _Value_ | _nullable_ |
| _Timestamp_ |  |
| _Partition & Offset_ |  |
| _Headers_ | _key / value pairs_ <br> _like HTTP headers_ <br> _to provide additional metadata_ |
| _Compression Type_ | _none / gzip / snappy / lz4 / zstd_ |

<br>

## Kafka Cluster
* Topic is divided into partitions
* Each partition will have a leader
* A partition can be replicated in another broker(replication.factor)
* `Isr` - in sync replicas

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-cluster.png" width="600" height="400">

<br>

## Producer Acknowledge
	
| **ACKS** | **BEHAVIOR** |
| ------- | ------- |
| **-1** | _all(leader and follower)_ |
| **0** | _none(fire & forget)_ |
| **1** | _only leader_ |

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-producer-ack.png" width="400" height="500">

<br>

## Min In Sync Replicas

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-producer-min-insync-replica.png" width="400" height="400">

<br>

## Idempotent Producer
* When the acknowledgement did not make it to the client, client retry to send duplicate messages
* Kafka broker will directly send acknowledgement back to the client
* Kafka broker receives the messages and writes those messages into the partition
* But the sequence Ids are generated by client library and it send messages
* If client itself generate duplicate messages, then Kafka broker does not help

<br>

## Idempotent Consumer
* Let the producer set some unique message / event id
* Broker receives the messages 
* Check if they are present in table
* If they are present, duplicates - simply ack. skip processing
* If not, new messages - process, insert into db and then ack

<br>

## Compression
* It is easy for us to transfer the message
* Improve the latency


<br>

1. None
    * the compression is not enabled by default

2. Gzip
    * compression ratio is good
    * take more CPU

3. Snappy
    * snappy is not as good as gzip compression ratio
    * take less CPU

4. Lz4

<br>

## Setup
* Topics
  * Decide based on application requirments
* Partitions
  * Decide based on throughput(producer, kafka broker, consumer)
* Replication Factor
  * Decide beased on the number of brokers to go down at a time
  * min.insync.replicas = 2

<br>

## Reactive Kafka - Batch / Parallel Processing

| **METHOD** | **DESCRIPTION** |
| ------- | ------- |
| _concatMap_ | _Sequential batch processing_ |
| _flatMap_ | _Parallel batch processing_ |
| _groupBy & flatMap_ | _Parallel batch processing with message ordering_ |

<br>

## Error Handling
1. Retry

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-error-retry.png" width="400" height="400">

<br> 

2. Dead Letter Topic

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-error-dead-letter.png" width="600" height="400">

<br>

3. Poison Pill

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-error-poison-pill.png" width="600" height="400">

<br>

## Transactions
* read-process-write app
  * it consume events from one topic will do some processing
  * then it will produce output messages to another topic
* `read_committed`
  * can not receive aborted messages
* `read_uncommitted`
  * default mode
  * can receive any messages
  * received faster than read_committed mode
* `ProducerFencedException`
  * kafka broker and transaction coordinator not allow application to produce
  * have to be careful to use a unique transaction Id
  * have to committed or aborted the last 60 seconds

<br>

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/07-kafka-transaction.png" width="600" height="400">

<br>

````java
// kafka transaction pseudo code
try {
    receiverRecord = getOnRecordFromBroker();
    List<SenderRecord> senderRecords = processRecord(receiverRecord);
    sendAllRecords(senderRecords);
    receiverRecord.acknowledge();
    commit;
} catch(Exception e) {
    rollback;
}
````

<br>

## Delivery Semantics

| **DELIVERY** | **PROS** | **CONS** |
| ------- | ------- | ------- |
| _At Least Once_ | _we will not lose any message_ <br> _default delivery_ | _Consumer - messages might be redelivered_<br>_(we have to make our application idempotent)_ <br> _Producer - retry and throughput impact_ |
| _At Most Once_ | _Consumer - no duplicates_ <br> _Producer - low latency_ | _we might lose the messages_ |
| _Exactly Once_ | _exactly once_ | _performance impact_ |

<br>

## Spring Kafka
* ReactiveKafkaProducerTemplate&lt;K, V&gt; - KafkaSender&lt;K, V&gt;
* ReactiveKafkaConsumerTemplate&lt;K, V&gt; - KafkaReceiver&lt;K, V&gt;

<br>

````yml
spring:
  kafka:
    bootstrap-servers: localhost:9092

    # consumer side setup
    consumer:
      group-id: vinsguru-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
      properties:
        group.instance.id: "1"
        spring.json.trusted.packages: "com.vinsguru.reactivekafkaplayground.sec16.dto"
        spring.deserializer.value.delegate.class: org.springframework.kafka.support.serializer.JsonDeserializer

    # producer side setup
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer

````

<br>

## Integration Testing
* Reactor KafkaRecevier
* Reactor KafkaSender
* Kafka Server
  * EmbeddedKafka with Spring - `@EmbeddedKafka`
  * Testcontainers

<br>

## Kafka Security

* SASL - Simple Authentication and Security Layer
  > 1. PLAIN
  > 2. SSL
  >     * CA Certificate Authority  
  >       * has public key and private key  
  >     * CSR Certificate signing request  
  >       * ask CA to approve the request  
  >       * CA use a private key and give the certificate  
  >       * all client has public key   
  > 3. ...

* JAAS - Java Authentication and Authorization Service(API)

<br>




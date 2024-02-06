# [ 04-redis-with-spring-webflux ]
> Redis 및 Spring Webflux 기초 학습을 위한 디렉토리  
> Redis 및 Redisson 활용 실습  
> Redis 성능 비교(Apache JMeter)  

<br>

## SUMMARY
| **TITLE** | **DESCRIPTION** | **DIR** |
| ------- | ------- | ------- |
| **_Redisson Playground_** | - _RBucketReactive, RAtomicLongReactive_ <br> - _RMapReactive, RMapCacheReactive, RLocalCachedMap_ <br> - _RListReactive, RBlockingDequeReactive_ <br> - _RHyperLogLogReactive_ <br> - _RTopicReactive_ <br> - _RBatchReactive_ <br> - _RTransactionReactive_ <br> - _RScoredSortedSetReactive, PriorityQueue_ <br> - _RGeoReactive_ | [_redisson-playground_](./redisson-playground/src/test/java/com/vinsguru/redisson) |
| **_Redis Spring_** | - _RedissonReactiveClient_ <br> - _Cache Annotations_ | [_redis-spring_](./redis-spring) |
| **_Redis Performance_** | - _RMapReactive_ <br> - _RLocalCachedMap, LocalCachedMapOptions_ | [_redis-performance_](./redis-performance) |

<br>

## Performance - Apache JMeter Setup
1. READ - 90%
    * Endpoint: HTTP - GET /product/${_Random(1, 1000)} 
    * Number of Threads(users): 180
    * Ramp-up period(seconds): 30
    * Loop count: infinite
    * Check 'Same user on each iteration'
    * Check 'Delay Thread creation until needed'
    * Check 'Specify Thread lifetime'
    * Duration(seconds): 300

2. WRITE - 10%
    * Endpoint: HTTP - POST /product/${random}
    * Body: { "price": ${_Random(1, 100)}, "description": "product-${random}" } 
    * Number of Threads(users): 20
    * Ramp-up period(seconds): 30
    * Loop count: infinite
    * Check 'Same user on each iteration'
    * Check 'Delay Thread creation until needed'
    * Check 'Specify Thread lifetime'
    * Duration(seconds): 300

<br>

## Performance - Apache JMeter Result
1. No Redis
* Occur error because of database connection

| **Label** | **#Samples** | **Average** | **Median** | **90% Line** | **95% Line** | **99% Line** | **Min** | **Maximum** | **Error(%)** | **Througput** | **Received(KB/sec)** | **Sent(KB/sec)** |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| **GET** | 10126 | 806 | 449 | 1697 | 4778 | 6025 | 5 | 6862 | 6.09% | 167.4/sec | 21.14 | 20.74 |
| **PUT** | 1144 | 778 | 422 | 1700 | 3794 | 6102 | 7 | 6837 | 7.87% | 19.8/sec | 2.65 | 4.62 |
| **TOTAL** | 11270 | 803 | 446 | 1699 | 4724 | 6032 | 5 | 6862 | 6.27% | 186.3/sec | 23.66 | 25.15 |

<br>

2. Redis(Map)

| **Label** | **#Samples** | **Average** | **Median** | **90% Line** | **95% Line** | **99% Line** | **Min** | **Maximum** | **Error(%)** | **Througput** | **Received(KB/sec)** | **Sent(KB/sec)** |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| **GET** | 1486049 | 34 | 31 | 61 | 73 | 101 | 0 | 437 | 0.00% | 4955.7/sec | 622.89 | 628.62 |
| **PUT** | 79294 | 54 | 50 | 87 | 100 | 133 | 5 | 420 | 0.00% | 264.6/sec | 33.25 | 62.45 |
| **TOTAL** | 1565343 | 35 | 31 | 63 | 75 | 104 | 0 | 437 | 0.00% | 5219.9/sec | 656.10 | 691.02 |

<br>

3. Redis(Local Cache Map)

| **Label** | **#Samples** | **Average** | **Median** | **90% Line** | **95% Line** | **99% Line** | **Min** | **Maximum** | **Error(%)** | **Througput** | **Received(KB/sec)** | **Sent(KB/sec)** |
| ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
| **GET** | 1873344 | 27 | 25 | 44 | 53 | 77 | 0 | 416 | 0.00% | 6247.9/sec | 785.28 | 792.53 |
| **PUT** | 87507 | 52 | 49 | 75 | 86 | 115 | 5 | 557 | 0.00% | 292.0/sec | 36.70 | 68.93 |
| **TOTAL** | 1960851 | 28 | 26 | 47 | 57 | 82 | 0 | 557 | 0.00% | 6539.5/sec | 821.94 | 861.40 |

<br>

## Redis
* In-Memory Database / NoSQL
* Multi-purpose Data-Structure Server
* Open source -> redis.io
* Commercial cloud service -> redislabs.com
* Single threaded
* Any binary sequence can be the key(preder short, readable key)
* Max 2^32 keys

<br>

## Redis - Client / Server
* Client: redis-cli
* Server: redis-server
* Port: 6379

<br>

## Redis - Key / Value
* Expire the keys(game, session store)
* Redis can notify us when keys are expired or destroyed

<br>

## Redis - Hash
* A group of related key-value pairs stored as an object
* Represents 1 record in a relational Database table
* A document from Mongo collection
* We can not expire individual fields in hash
* But we can expire the hase

<br>

## Redis - List
* An ordered collection of items
* Can be used as Queue or Stack
* Can also be used as Message Queue for communication among Microservices

<br>

## Redis - Set
* An unordered collection of unique items
* O(1) constant time performance to check the existence of an item
* Intersection / Union / Diff

<br>

## Redis - Sorted Set
* An ordered collection of unique items
* Same as Set + with some scores for individual item
* It can be used to track top product by rating etc
* Can also be used as Priority Queue

<br>

## Redis - Geospatial
* Store information / an object with Latitude & Longitude
* Search for nearby objects
* Find distance between 2 objects

<br>

## Redis - Transaction
* Use WATCH, MULTI, EXRC, DISCARD
* To run a group of commands as a transaction

<br>

## Redis - Authorization
* By default no auth
* Default user has admin privileges
* No password is set for this user
* Redis version < 6
  * No username
  * Only password
  * Command: AUTH [password]
* Redis version >= 6
  * Username and password
  * ACL-users with specific privileges

<br>

## Redis - ACL

| **COMMAND** | **DESCRIPTION** |
| ------- | ------- |
| _acl list_ | - _get acl user list_ |
| _acl whoami_ | - _get acl user_ |
| _acl setuser [username] >[password]_ | - _create acl user_ |
| _acl deluser [username]_ | - _delete acl user_ |
| _auth [username] [pasword]_ | - _login acl user_ |
| _config set requirepass [password]_ | - _set default user password_ |

<br>

| **OPTION** | **DESCRIPTION** |
| ------- | ------- |
| _allcommands / +@all_ | - _access to all commands in redis_ |
| _-get, -set_ | - _no access to get, set commands_ |
| _+@set, +@hash, +@list_ | - _access set and hash related commands_ |
| _allkeys / ~*_ | - _access to all the keys in redis_ |
| _~numbers:*_ | - _access to keys starting with numbers_ |
| _-@dangerous_ | - _no access dangerous commands like flushdb, shutdown, config_ |

<br>

## Redis - Java Libraries
1. Jedis
    * Fast
    * Not thread safe
    * Not scalable

2. Letture(4K stars & actively maintained)
    * Scalable
    * Supports reactive-streams
    * Works with Spring Data Redis(not ReactiveCRUDRepository)
    * Low level API compared to Redisson

3. Redisson(16K stars & actively maintained)
    * Scalable
    * Supports reactive-streams
    * Excellent abstraction
    * A lot of cool features & excellent documentation
    * Works with Spring Data Redis(not ReactiveCRUDRepository)

<br>

## Redisson
* Redis Client Library for Java
* Much better abstraction compared to other libraries

<br>

| **REDIS** | **REDISSON** |
| ------- | ------- |
| _SET key value_ | _Bucket / AtomicLong_ |
| _Hash_ | _Map, MapCache, LocalCachedMap_ |
| _List_ | _List, Queue, Deque, MessageQueue_ |
| _Set_ | _Set_ |
| _Sorted Set_ | _SortedSet, PriorityQueue_ |

<br>

* Redis as
  * Message Queue
  * Priority Queue
* Batch
  * To save network round trip
  * Reactive objects are proxy objects
* Transaction
  * To make a set of commands atomic
  * Prefer LuaScript(requires knowledge on Lua)
* Pub / Sub
  * Message broadcasting
  * LocalCachedMap uses internally

<br>

## Redisson - Local Cache Map
1. Sunc Strategy
    * NONE
    * INVALIDATE
    * UPDATE

2. Reconnect Strategy
    * NONE
    * CLEAR
    
<br>

## Redisson - Cache Annotations
* Easy to use
* Limitations
  * No TTL support
  * Not a lot of flexibility
  * Does not work with publisher types

1. @Cacheable
    * Skip the method execution if key is present
    * Do the method execution only if the key is not present & store the result

2. @CacheEvit
    * Do the method execution always & evict the corresponding cache
    * Evit happens after method execution

3. @CachePut
    * Do the method execution always & update the corresponding cache

<br>



# [ 06-graphql-with-spring-webflux ]
> GraphQL 학습을 위한 디렉토리  
> GraphQL 활용 실습  
> REST 통신과 비교  
> 간단한 마이크로서비스 적용 실습  

<br>

## SUMMARY

| **TITLE** | **DESCRIPTION** | **DIR** |
| ------- | ------- | ------- |
| **_GraphQL Playground_** | - _QueryMapping_ <br> - _SchemaMapping_ <br> - _BatchMapping_ | [_sec01_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec01/) <br> [_sec02_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec02/) <br> [_sec03_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec03/) <br> [_sec04_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec04/) <br> [_sec05_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec05/) |
|  | - _GraphQL vs REST_ | [_sec06_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec06/) |
|  | - _DataFetchingFieldSelectionSet_ <br> - _DataFetchingEnvironment_ <br> - _DataFetcher_ | [_sec07_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec07/) |
|  | - _Field Glob pattern_ | [_sec08_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec08/) |
|  | - _GraphQL scalar type_ <br> - _ExtendedScalars_ | [_sec09_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec09/) |
|  | - _TypeResolver_ <br> - _ClassNameTypeResolver_ | [_sec10_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec10/) |
|  | - _Union_ | [_sec11_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec11/) |
|  | - _GraphQL work flow_ <br> - _GraphQlSourceBuilderCustomizer_ <br> - _PreparsedDocumentProvider, PreparsedDocumentEntry_ <br> - _ExecutionInput_ | [_sec12_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec12/) |
|  | - _MutationMapping_ <br> - _SubscriptionMapping_ | [_sec13_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec13/) <br> [_sec14_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec14/) |
|  | - _WebFilter_ <br> - _WebGraphQlInterceptor_ <br> - _DataFetcherExceptionResolver, GraphQLError_  | [_sec15_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec15/) |
|  | - _Client GraphQL_ <br> - _HttpGraphQlClient, ClientGraphQlResponse, ClientResponseField_ <br> - _WebSocketGraphQlClient, ReactorNettyWebSocketClient_ | [_sec16_](./graphql-playground/src/main/java/com/vinsguru/graphqlplayground/sec16/) |
|  | - _HttpGraphQlTester_ <br> - _WebSocketGraphQlTester_ | [_test_](./graphql-playground/src/test/java/com/vinsguru/graphqlplayground/) |
| **_MSA GraphQL_** | - _Schema design_ <br> - _DTO design_ <br> - _Error handling_ | [_msa-graphql_](./msa-graphql/) |

<br>

## MSA GraphQL - Structure

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/06-graphql-msa.png" width="600" height="500">

<br>

## GraphQL
* `Query Language` for APIs
* Strongly typed like Java
* Using the type system
  * the backend developers can expose the APIs and communicate how the data is structured
  * the clients have the flexibility to explore the API and request specific data
* Eliminates the Over-Fetching / Under-Fetching problems

<br>

## Basic Types

| TYPE | DESCRIPTION |
| ------- | ------- |
| Int | Integer |
| Float | Float |
| String | String |
| Boolean | Boolean |
| ID | Some unique ID(Serialized as String) <br> UUID / Long |
| [Object] | List&lt;Object&gt; |

<br>

## Custom / Object Types
````graphql
type Customer {
    # Customer ID is not null
    id: ID!
    name: String!
    age: Int
    products: [Product]
}

type Product {
    # Product ID is not null
    id: ID!
    description: String
    price: Int
}

enum Brand {
    HONDA
    BMW
    TOYOTA
}
````

<br>

## Special Types

| TYPE | REST/HTTP Equivalent |
| ------- | ------- |
|	Query | GET |
|	Mutation | POST / PUT / DELETE / PATCH |
|	Subscription | SSE / WebSocket |

<br>

## Schema
* Something like Database Schema
* Schema defines the types, fields and the relationship among them

<br>

## GraphiQL - Client
1. Field Aliases
    * Graphql server can execute multiple APIs / Fields at the same time
    * Graphql server can also execute same API multiple times for different arguments

2. Fragments
    * To reuse a set of fields in the query

3. Operation Name
    * Good practise is to given an Operation Name for debugging / monitoring purposes

4. Directives
    * @include
    * @skip
    * @deprecated

5. Varialbes

<br>

````graphql
# Operation Name
query CustomersByAgeRange(
      $kids: AgeRangeFilter!,
      $adults: AgeRangeFilter!,
      $seniors: AgeRangeFilter!,

      # It is not null - default is false
      $includeKids: Boolean! = false,
      $includeAdults: Boolean! = false,
      $skipSeniors: Boolean! = true
  ) {
    # Field Aliases
    kids: customersByAgeRange(filter: $kids) @include(if: $includeKids)
    {
        ... CustomerDetails
    }
    adults: customersByAgeRange(filter: $adults) @include(if: $includeAdults)
    {
        ... CustomerDetails
    }
    seniors: customersByAgeRange(filter: $seniors) @skip(if: $skipSeniors)
    {
        ... CustomerDetails
    }
}

# Fragment
fragment CustomerDetails on Customer {
    id
    name
    age
    city
}
````

````json
{
    "_comment": "Variables"
    
    "includeKids": true,
    "kids": {
        "min": 1,
        "max": 20
    },
  
    "includeAdults": true,
    "adults": {
        "min": 21,
        "max": 50
    },
  
    "skipSeniors": false,
    "seniors": {
        "min": 51,
        "max": 100
    }
}
````

<br>

## Difference - REST and GraphQL

<img src="https://github.com/jjeonghak/vinsguru-microservices/blob/main/md-images/06-graphql-rest-diff.png" width="800" height="400">

<br>

```java
// GraphQL works like a zip method 

Mono<R> endpoint1();
Mono<R> endpoint2();
Mono<R> endpoint3();

return Mono.zip(
    endpoint1(),
    endpoint2(),
    endpoint3()
);
```

<br>

## Operation Caching
* Graphql Query Parse / Validation
* Preder / encourage the clients to use variables
* Use `Caffeine` cache / LRU cache
  * Keep recently used
  * Evict the old

<br>

## CRUD Application with GraphQL
1. `@QueryMapping` - GET
2. `@MutationMapping` - POST / PUT / DELETE
3. `@SubscriptionMapping` - STREAM

<br>

* We can use null to indicate if the requested resource not found
* An Operation can contain multiple mutations - they will be executed sequentially
* We can not combine Query and Mutation in a single operation
* As per GraphQL spec
  * type Query - mandatory
  * type Mutation - optional
  * type Subscription - optional
  * type Query must be present in the Schema file

<br>

## Error Handling / Validation
* There are no [2xx / 3xx / 4xx / 5xx] success or error codes in GraphQL
  * When GraphQL uses HTTP as transport, we might see these HTTP codes in the response
  * But it should not be considered as success/error codes from GraphQL
* DataFetcherExceptionResolver to collect the application specific errors or any unhandled exceptions to build GraphQLError
* Instread of throwing exception, we can also use Union
* WebGraphQLInterceptor to collect HTTP headers / cookies and use GraphQLContext to pass it to the controller
* Spring Security / WebFilter / Bean validation work just fine with GraphQL

<br>

## Integration Test
* HttpGraphQlTester
* Method - document / documentName
* We can pass the variables / operation name etc as we did for HttpGraphQlClient
* Use Json Path to access the response and verify

<br>





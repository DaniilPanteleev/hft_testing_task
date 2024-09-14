# todo-autotests

## Libraries

### Infrastructure

|                                Naming                                | Description                                         |
|:--------------------------------------------------------------------:|:----------------------------------------------------|
|         [Jackson](https://github.com/FasterXML/jackson-docs)         | Serialization/Deserialization of JSON               |
|            [Lombok](https://projectlombok.org/features/)             | Downgrage of boilerplate                            |
| [Spring Boot](https://docs.spring.io/spring-boot/documentation.html) | Simplify services creation, use Application Context |

### Testing

|                            Naming                            | Description                |
|:------------------------------------------------------------:|:---------------------------|
| [Junit 5](https://junit.org/junit5/docs/current/user-guide/) | Test Runner Engine         |
|          [AssertJ](https://assertj.github.io/doc/)           | Asserts, softasserts       |
|           [Awaitility](http://www.awaitility.org/)           | Conditional waiting        |
|      [Testcontainers](https://java.testcontainers.org/)      | Work with docker from code |
|           [Rest Assured](https://rest-assured.io/)           | API testing framework      |

### Reporting

|                  Naming                  | Description                     |
|:----------------------------------------:|:--------------------------------|
| [Allure](https://allurereport.org/docs/) | Gathering and report generation |

## Architecture

1. [api-tests](api-tests) - api tests package
2. [performance-tests](performance-tests) - performance tests package

## How to run?

**NOTE:** docker is required!

```bash
./run_tests.sh
```

In opened menu select what you want to run

## Performance

In opened report you have some useful tabs:
- Global - Common information about perf run
- Details - Detailed statistic of POST /todo endpoint perf
- Screenshots - Screenshots of app utilized resources under perf test
- Summary - Conclusion of performance results



# ReZoom API Server

[![Build Status](https://travis-ci.org/Nexters/rezoom-backend.svg?branch=master)](https://travis-ci.org/Nexters/rezoom-backend) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)




## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Usage](#usage)
* [License](#license)


## About The Project

<img src="https://github.com/momentjin/study/blob/master/resource/image/rezoom-screenshot1.png" width="400px"> <img src="https://github.com/momentjin/study/blob/master/resource/image/rezoom-screenshot3.png" width="400px">

자기소개서 관리 어플리케이션입니다.

- 자기소개서 CRUD
- 해시태그를 이용한 문항 검색
- 서류 마감 N일전 알림 (현재는 Email 알림 기능만 지원)
- .txt파일로 작성된 자기소개서 마이그레이션 (내용 형식 일치할 때만 가능)

### Built With
- Java 8
- Spring Boot 2
- JPA (Hibernate)
- JUnit5
- Maven
 
## Getting Started

### Prerequisites

- jdk 1.8 ~
- maven
 
### Installation

1. Clone the repository
```sh
~/$ git clone https://github.com/Nexters/rezoom-backend.git
```
2. Create your database configuration info in application-dev.yml
```sh
~/$ vim rezoom-backend/src/main/resources/application-dev.yml

<example>
# database
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/[input datatabase name]?useSSL=false&characterEncoding=UTF-8
    username: [input username]
    password: [input password]
    driver-class-name: com.mysql.jdbc.Driver
    initialization-mode: always
  jpa:
    database: mysql
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect
    generate-ddl: true
```
3. execute
```sh
~/rezoom-backend$ mvn spring-boot:run
```
## Usage

If you want to change the properties file to activate, edit your pom.xml
```xml
...
<profiles>
  <profile>
      <id>dev</id>
      <properties>
          <activatedProperties>dev</activatedProperties>
      </properties>
      <activation>
          <activeByDefault>true</activeByDefault>
      </activation>
  </profile>
  <profile>
      <id>ci</id>
      <properties>
          <activatedProperties>ci</activatedProperties>
      </properties>
  </profile>
</profiles>
```

If you want to call an API except the login API, you need a token. Use login API to get a token. Then, put the issued token in the header and call API.
```sh
curl -X POST -H "Content-Type: application/json" -d '{"id":"[your id", "password":"your password"}' http://localhost:8080/login
curl -X GET -H "Authorization: [your token]" localhost:8080/coverletters 
```

## License

Copyright 2019 momentjin.

Licensed under the MIT license:

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

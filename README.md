
## Spring Boot
```ssh

```
## RabbitMQ
```ssh
docker pull rabbitmq
docker run -d --name rabbitmq-server -p 5672:5672 -p 15672:15672 rabbitmq:management\n
docker exec -it rabbitmq-container-name rabbitmqctl list_queues\n```
```

### Ref:
- https://www.baeldung.com/spring-boot-start
- https://spring.io/blog/2020/03/26/spring-boot-2-2-6-available-now
- https://www.baeldung.com/spring-amqp
- https://www.baeldung.com/spring-boot-logging
- https://www.baeldung.com/mdc-in-log4j-2-logback
- https://medium.com/@d.lopez.j/spring-boot-setting-a-unique-id-per-request-dd648efef2b
- https://stegard.net/2021/02/spring-boot-http-access-logging-in-three-steps/
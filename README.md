# ConsumeService

Here i have implemented circuit breaker using resilience4j
I have consumed NotificationService and Email Service and implemented circuit breaker

we can implement circuit breakerusing spring cloud netflix starter but now its deeprecated so now implemented using resilience4j
for this 3 starter dependecies needs to be added .
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
		<dependency>
			<groupId>io.github.resilience4j</groupId>
			<artifactId>resilience4j-spring-boot2</artifactId>
			<version>1.5.0</version>
		</dependency>
    
    
    we need to add annotation 	@CircuitBreaker(name = "EmailNotificationService",fallbackMethod = "dummyconsumeservice") 
    here under fallbackmethod we need to give method name as dummy method when service got down and in that time this dummy method will gets called
    and under name u can give any name and in my case i have used service which am calling from this consume api
    
    management.health.circuitbreakers.enabled=true
management.endpoint.health.show-details=always
management.endpoints.web.exposure.include=health

resilience4j.circuitbreaker.instances.EmailNotificationService.base-config=default
resilience4j.circuitbreaker.configs.default.register-health-indicator=true
resilience4j.circuitbreaker.configs.default.event-consumer-buffer-size=10
resilience4j.circuitbreaker.configs.default.failure-rate-threshold=50
resilience4j.circuitbreaker.configs.default.minimum-number-of-calls=5

resilience4j.circuitbreaker.configs.default.automatic-transition-from-open-to-half-open-enabled=true
resilience4j.circuitbreaker.configs.default.wait-duration-in-open-state=5s
resilience4j.circuitbreaker.configs.default.permitted-number-of-calls-in-half-open-state=3
resilience4j.circuitbreaker.configs.default.sliding-window-size=10
resilience4j.circuitbreaker.configs.default.sliding-window-type=COUNT_BASED

above are properties needs to be added for resilience4j 

when services are up and running perfectly 
![image](https://user-images.githubusercontent.com/115841974/207441357-61a74903-e631-405e-a8c2-810b7db82aac.png)

![image](https://user-images.githubusercontent.com/115841974/207441794-1401d6e4-8b6a-4da5-9490-b8e09d9e02f2.png)


here i am consuming 2 services and when service went down 
![image](https://user-images.githubusercontent.com/115841974/207441881-adf4675e-bdcc-4f2c-9918-a11787ee0b75.png)

![image](https://user-images.githubusercontent.com/115841974/207441987-2ee0651f-4d7c-47d3-89ad-7de58dab8f01.png)
![image](https://user-images.githubusercontent.com/115841974/207442139-fe70f7d1-764d-4c9c-b534-1316b907a5ae.png)
resilience4j.circuitbreaker.configs.default.minimum-number-of-calls=5
 according to above stmt when it reaches threshold percentage and if exceeded min no of calls state gets open after 5 sec state will gets half open 
 ![image](https://user-images.githubusercontent.com/115841974/207442418-5f96c865-e207-44a3-ba3b-aae1458c03d0.png)

![image](https://user-images.githubusercontent.com/115841974/207442490-d655bc75-a621-4b9b-9e98-f461cd957c70.png)
resilience4j.circuitbreaker.configs.default.permitted-number-of-calls-in-half-open-state=3
a/c to above stmt if no of calls exceed in hal open state and still service gets failure again state gets into open state and if service gets success state gets into closed state 
here service gets failure so it went to open state
![image](https://user-images.githubusercontent.com/115841974/207442819-4e0275ce-73f0-4d3c-a856-836b3f1a571d.png)

![image](https://user-images.githubusercontent.com/115841974/207443001-adcdf2c1-b688-4b6d-9977-38aed72ea9c2.png)
now above screenshot reprsents if service gets success

Retry mechanism using resilience4j
![image](https://user-images.githubusercontent.com/115841974/207457845-a993aeab-af84-477c-9092-03f0cf931475.png)

![image](https://user-images.githubusercontent.com/115841974/207457919-be41b637-7d71-4800-99dc-4e2281fbeb5a.png)



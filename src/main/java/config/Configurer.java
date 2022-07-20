package config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class Configurer {
    /*
    - git: https://github.com/koushikkothagal/spring-boot-microservices-workshop
    - restTemplate or webclient
    - one server ie service discovery and other clients including the one that is sending request to other services
    - use eureka server-client, add dependency and cloud dependency management in server and client application
    - in service that makes claim to other service ie, the application.property file contains: eureka.client.register-with-eureka=false,
    eureka.client.fetch-registry=false and add @LoadBalanced above RestTemplate (bean class)
    - load balance default port is 8761 so for ui: http://localhost:8761
    - in client services, the application.property contains: spring.application.name = services' name
    - require annotation of EnableEurekaServer in server and enableEurekaClient in client (main application file)
    - To access other web services like khalti, use keys ie private and public in apis
    - fault tolerance and resilience
    - for fault tolerance use timestamp, circuit breaker(priority)
    - circuit breaker library (Hystrix)
        -add dependency netflix-hystrix
        -add annotation of EnableCircuitBreaker in application class of service that is calling other service
        -add annotation of hystrixCommand to methods that require circuit breaker
        -provide parameter in annotation hystrixCommand like fallBackMethod= "specify other method with same
        parameter as bellow method with different name"
        -if there is more than one api calling from the method then, you can extract those apis in different methods with fallback method in different class file
        - circuit breaker require logic to perform its operation like
            -Example:
                -last n request to consider for the decision: 5
                -how many of those should failed: 3
                -timeout duration: 2s
                -how long to wait (sleep window): 10s
            -And there is a fallback to perform after circuit breaker perform its operation
            -Types of fallback:
                - error (not recommended)
                - fallback "default" response
                - use previous response (cache)
            -automatic recovery

    -for hystrix dashboard add hystrix-dashboard and actuator dependency, and add EnableHystrixDashboard annotation in application class file,
    add management.endpoints.web.exposure.include = hystrix.stream
     */


    /*
    for microservice configuration
    - use value annotation to get the values of key from application.property
        - for eg: @Value("${key-name : default value (if required)}")
        - Or, can also pass key and value in commandline
    -Or, make a class with the same key name and datatype in application.properties
        - use @configuration and @EnableConfigurationProperty annotation
        - autowired that class in service or controller file and use it.
    - for external property file so that, jar can be executed repeatedly without build when property file is updated
        - put the application.property file in target folder alongside with jar file
        - Or, pass application.property file in commandline while running jar file ie java -jar .....
    - use yaml (.yml) file instead of (.property) when there is lots of same package name
        ie app.name = bijen, app.string = shrestha, app.int = 09
            -It can be written as app:
                                    .name: bijen
                                    .string: shrestha
                                    .int: 09
            -Use space instead of tabs for indentation. (tabs not allowed)
            -use colon (:) instead of equals to (=)

    - for different config files, use profiles (groups of configuration)
        -property file name: application-profileName.yml
        -default profile is implemented ie configuration file: application.yml
        -to switch profile: write below code in application.yml file
            spring.profiles.active: profileName

    - add actuator dependency to know the condition of application including environment properties

     */

    /*
    Spring cloud config
    for spring cloud config server
    - add spring-cloud-config-server dependency
    - add @EnableConfigServer to main application file
    - add spring.cloud.config.server.uri = "url of git or local repo(file: application.yml)" in application.properties
    - uri for local repo: ${HOME}/folderLocation
    - http://localhost:port/<property-file-name>/<profile>

    for spring cloud config client
    - add spring-cloud-starter-config dependency and dependency management
    - add pring.cloud.config.server.uri = url of server
    - for different client to access different server config file,
     make different configuration property file with its client microservice name(spring.application.name)
     for eg: bijen.property for bijen app, shrestha.yml for shrestha app


    for automatic refreshing the configuration properties
    - add actuator dependency
    - add @RefreshScope annotation on configuration class, instance field
    - call post request to localhost:clientPort/actuator/refresh in postman
     by putting Headers ("ContentType": application/json, "ContentType": application/json)
     */


    @Bean
    public RestTemplate getRestTemplate() {
//        HttpComponentsClientHttpRequestFactory
        return new RestTemplate();
    }


}

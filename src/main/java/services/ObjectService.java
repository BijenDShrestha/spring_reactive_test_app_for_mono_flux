package services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ObjectService {

    @Autowired
    RestTemplate restTemplate;



    @HystrixCommand(fallbackMethod = "fallbackPostObject",
    commandProperties = {
//            timeout duration: 5s
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
//            how many of those should fail: 5
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//            threshold for error
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//          how long to wait (sleep window): 10s
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
    })
    private void postObject() {
        Object object = new Object();
        RestTemplate restTemplate = new RestTemplate();
        Object newObject = restTemplate.postForObject("http:localhost:8080/api/demo", object, Object.class);
    }

    private void fallbackPostObject() {
        Object fallbackObject = restTemplate.getForObject("http:localhost:8080/api/demo", Object.class);
    }



    //bulkHead pattern using in hystrix
    @HystrixCommand(fallbackMethod = "fallbackPostObject",
    threadPoolKey = "getObjectPool",
    threadPoolProperties = {
            //numbers of threads in a bulk
            @HystrixProperty(name = "coreSize", value = "50"),
            @HystrixProperty(name = "maxQueueSize", value = "20")
    })
    private void getObject() {
        Object object = new Object();
        RestTemplate restTemplate = new RestTemplate();
        Object newObject = restTemplate.getForObject("http:localhost:8080/api/demo", Object.class);
    }

}

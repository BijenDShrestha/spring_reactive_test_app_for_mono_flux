package demoTest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FluxTest {

    @Test
    public void createAFlux_just() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

        fruitFlux.subscribe(
                f -> System.out.println("here is a fruit " + f)
        );

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void createAFlux_fromArray() {
        String[] fruits = new String[]{
                "Apple", "Orange", "Grape", "Banana", "Strawberry"
        };

        Flux<String> fruitFlux = Flux.fromArray(fruits);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void createAFlux_fromIteration() {
        List<String> fruitList = new ArrayList<>();
        fruitList.add("Apple");
        fruitList.add("Orange");
        fruitList.add("Grape");
        fruitList.add("Banana");
        fruitList.add("Strawberry");

        Flux<String> fruitFlux = Flux.fromIterable(fruitList);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void createAFlux_fromStream() {
        Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");

        Flux<String> fruitFlux = Flux.fromStream(fruitStream);

        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    public void createAFlux_range() {

        Flux<Integer> rangeFlux = Flux.range(1, 5);

        StepVerifier.create(rangeFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }


    @Test
    public void createAFlux_interval() {

        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5L);

        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }


    @Test
    public void mergeFlux() {
        Flux<String> firstName = Flux.just("Bijen", "Mike", "Rita")
                .delayElements(Duration.ofSeconds(5));

        Flux<String> lastName = Flux.just("Shrestha", "Tyson", "Sharma")
                .delayElements(Duration.ofSeconds(5))
                .delaySubscription(Duration.ofSeconds(3));

        Flux<String> mergeFlux = firstName.mergeWith(lastName);

        StepVerifier.create(mergeFlux)
                .expectNext("Bijen")
                .expectNext("Shrestha")
                .expectNext("Mike")
                .expectNext("Tyson")
                .expectNext("Rita")
                .expectNext("Sharma")
                .verifyComplete();
    }


    @Test
    public void zipFlux() {
        Flux<String> firstName = Flux.just("Bijen", "Mike", "Rita");
        Flux<String> lastName = Flux.just("Shrestha", "Tyson", "Sharma");

        Flux<Tuple2<String, String>> zipFlux = Flux.zip(firstName, lastName);

        StepVerifier.create(zipFlux)
                .expectNextMatches(p ->
                        p.getT1().equalsIgnoreCase("Bijen") &&
                                p.getT2().equalsIgnoreCase("Shrestha"))
                .expectNextMatches(p ->
                        p.getT1().equalsIgnoreCase("Mike") &&
                                p.getT2().equalsIgnoreCase("Tyson"))
                .expectNextMatches(p ->
                        p.getT1().equalsIgnoreCase("Rita") &&
                                p.getT2().equalsIgnoreCase("Sharma"))
                .verifyComplete();

    }


    @Test
    public void zipFluxToObject() {
        Flux<String> firstName = Flux.just("Bijen", "Mike", "Rita");
        Flux<String> lastName = Flux.just("Shrestha", "Tyson", "Sharma");

        Flux<String> zipFlux = Flux.zip(firstName, lastName, (f,l) -> f + " " + l);

        StepVerifier.create(zipFlux)
                .expectNext("Bijen Shrestha")
                .expectNext("Mike Tyson")
                .expectNext("Rita Sharma")
                .verifyComplete();

    }

    @Test
    public void firstFlux() {
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100));

        Flux<String> fastFlux = Flux.just("cheetah", "horse", "squirrel");

        Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);

        StepVerifier.create(firstFlux)
                .expectNext("cheetah")
                .expectNext("horse")
                .expectNext("squirrel")
                .verifyComplete();

    }

    @Test
    public void filterFlux() {
        Flux<String> flux = Flux.just("Bijen Shrestha", "BijenShrestha", "BijShrestha", "Bij Shrestha")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500))
                .filter(name -> name.contains(" "));

        StepVerifier.create(flux)
                .expectNext("Bijen Shrestha")
                .verifyComplete();
    }

    @Test
    public void map() {
        Flux<List<String>> flux = Flux.just("Bijen Shrestha", "B Shrestha", "Bij Shrestha")
                .map(name ->
                {
                    String[] split = name.split("\\s");
                    List<String> splitString = new ArrayList<>();
                    splitString.add(split[0]);
                    splitString.add(split[1]);
                    return splitString;
                });

        StepVerifier.create(flux)
                .expectNext(Arrays.asList("Bijen", "Shrestha"))
                .expectNext(Arrays.asList("B", "Shrestha"))
                .expectNext(Arrays.asList("Bij", "Shrestha"))
                .verifyComplete();
    }


    @Test
    public void any() {
        Flux<String> flux = Flux.just("Bijen", "B Shrestha", "Bij Shrestha");

        Mono<Boolean> hasBMono = flux.any(b -> b.contains("S"));
//        Mono<Boolean> hasBMono = flux.all(b -> b.contains("B"));

        StepVerifier.create(hasBMono)
                .expectNext(true)
                .verifyComplete();
    }


}

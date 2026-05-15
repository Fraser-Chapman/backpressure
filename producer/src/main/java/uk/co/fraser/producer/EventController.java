package uk.co.fraser.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @GetMapping
    public Flux<String> getEvents() {
        return Flux.interval(Duration.ofMillis(1))
                .map(Object::toString);
    }
}

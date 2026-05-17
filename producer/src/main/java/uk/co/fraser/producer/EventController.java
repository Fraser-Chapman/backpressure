package uk.co.fraser.producer;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import uk.co.fraser.shared.model.Event;

import java.time.Duration;

@RestController
@RequestMapping("/api/event")
public class EventController {

    /**
     * @return a Reactive Stream that produces 100 messages 100ms apart.
     */
    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Event> getEvents() {
        System.out.println("======== Producing events =======");
        return Flux.range(1, 100)
                .delayElements(Duration.ofMillis(100))
                .map(value -> new Event(value.toString()))
                .map(event -> {
                    System.out.println("Producing event: " + event);
                    return event;
                })
                .doOnComplete(() -> System.out.println("====== Finished producing! ======"));
    }
}

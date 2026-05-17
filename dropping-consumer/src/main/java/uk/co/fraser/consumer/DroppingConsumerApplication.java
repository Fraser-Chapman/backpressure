package uk.co.fraser.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.BufferOverflowStrategy;
import uk.co.fraser.shared.model.Event;

import java.time.Duration;

/**
 * This application consumes messages and keeps a buffer of 10 messages. In the event of the buffer overflowing,
 * the oldest message is dropped. This results in messages from the producer being skipped.
 */
@SpringBootApplication
public class DroppingConsumerApplication implements CommandLineRunner {

    private final WebClient.Builder webClientBuilder;

    public DroppingConsumerApplication(final WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    static void main(String[] args) {
        SpringApplication.run(DroppingConsumerApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        System.out.println("======== Starting consumption ========");
        final WebClient webClient = webClientBuilder.build();
        webClient.get()
                .uri("http://localhost:8080/api/event")
                .exchangeToFlux(response -> response.bodyToFlux(Event.class))
                .onBackpressureBuffer(10, BufferOverflowStrategy.DROP_OLDEST)
                .delayElements(Duration.ofMillis(500))
                .subscribe(event -> System.out.println("Consumed event: " + event));
    }
}


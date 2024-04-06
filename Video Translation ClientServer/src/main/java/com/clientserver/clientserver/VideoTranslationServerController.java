package com.clientserver.clientserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class VideoTranslationServerController {

    private static final Logger logger = LoggerFactory.getLogger(VideoTranslationServerController.class);

    public static void main(String[] args) {
        SpringApplication.run(VideoTranslationServerController.class, args);
        logger.info("Server started");
    }
}

@RestController
class TranslationController {

    private final Random random = new Random();
    private final long maxDelayMs = 5000; // Maximum delay of 5 seconds

    private static final Logger logger = LoggerFactory.getLogger(TranslationController.class);

    @GetMapping("/status")
    public Status getStatus() throws InterruptedException {
        int randomDelay = random.nextInt((int) maxDelayMs);

        // Key feature: Simulate random delay for status response
        logger.info("Incoming status request. Random delay: {} ms", randomDelay);
        Thread.sleep(randomDelay);

        // Key feature: Determine status based on random delay
        Status status = new Status(randomDelay < maxDelayMs * 0.8 ? Status.Result.COMPLETED : Status.Result.PENDING);
        logger.info("Status checked: {}", status.getResult());
        return status;
    }
}

class Status {
    enum Result {PENDING, COMPLETED, ERROR}

    private final Result result;

    public Status(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}

package com.clientserver.clientserver;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VideoTranslationClient {

    private final String serverUrl;
    private final RestTemplate restTemplate;
    private final ScheduledExecutorService executorService;

    private final int initialRetryDelay = 1000; // Initial delay in milliseconds
    private final int maxRetries = 5; // Maximum number of retries
    private final double backoffMultiplier = 2.0; // Multiplier to apply for each retry

    private static final Logger logger = LoggerFactory.getLogger(VideoTranslationClient.class);

    public VideoTranslationClient(String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        this.executorService = Executors.newScheduledThreadPool(1);
    }

    // Key feature: Asynchronous status checking with retry mechanism
    public void checkStatus(CompletionHandler handler) {
        checkStatusWithRetry(handler, 0, initialRetryDelay);
    }

    private void checkStatusWithRetry(CompletionHandler handler, int retryCount, int delay) {
        Runnable task = () -> {
            try {
                logger.info("Checking status...");
                Status status = restTemplate.getForObject(serverUrl + "/status", Status.class);
                logger.info("Received status: {}", status.getResult());

                if (status.getResult() == Status.Result.COMPLETED) {
                    handler.onComplete(status.getResult());
                } else if (status.getResult() == Status.Result.ERROR || retryCount >= maxRetries) {
                    // Key Feature: Handle error or maximum retries reached
                    handler.onError(new Exception("Maximum retries reached or encountered an error"));
                } else {
                    // Key Feature: Handle retry for PENDING status
                    int nextDelay = (int) (delay * backoffMultiplier);
                    logger.info("Status is pending. Retrying in {} ms", nextDelay);
                    checkStatusWithRetry(handler, retryCount + 1, nextDelay);
                }
            } catch (Exception e) {
                logger.error("Error occurred while checking status: {}", e.getMessage());
                handler.onError(e);
            }
        };

        logger.info("Scheduling status check task with delay: {} ms", delay);
        executorService.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    // Key feature: Graceful shutdown of executor service
    public void shutdown() {
        logger.info("Shutting down executor service");
        executorService.shutdown();
    }

    // Callback interface for status checking completion
    interface CompletionHandler {
        void onComplete(Status.Result result);

        void onError(Exception e);
    }
}

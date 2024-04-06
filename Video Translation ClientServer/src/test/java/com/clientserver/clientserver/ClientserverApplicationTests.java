package com.clientserver.clientserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
class ClientserverApplicationTests {
    private static ConfigurableApplicationContext serverContext;
    private static VideoTranslationClient client;
    private static BufferedWriter writer;

    @BeforeAll
    public static void setUp() {
        try {
            writer = new BufferedWriter(new FileWriter("test_logs.txt", true));
            log("Starting test setup...");
            serverContext = SpringApplication.run(VideoTranslationServerController.class);
            client = new VideoTranslationClient("http://localhost:8080");
            log("Test setup completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void tearDown() {
        try {
            log("Starting test teardown...");
            serverContext.close();
            client.shutdown();
            log("Test teardown completed.");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testVideoTranslation() {
        log("Starting test execution...");
        // Start the translation process
        client.checkStatus(new VideoTranslationClient.CompletionHandler() {
            @Override
            public void onComplete(Status.Result result) {
                assertEquals(Status.Result.COMPLETED, result);
                log("API endpoint hit result: " + result);
                log("Test completed successfully!");
            }

            @Override
            public void onError(Exception e) {
                // Handle error
                log("Error occurred during test execution: " + e.getMessage());
            }
        });
        log("Test execution completed.");
    }

    private static void log(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

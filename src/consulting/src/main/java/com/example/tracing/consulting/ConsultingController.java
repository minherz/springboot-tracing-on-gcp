package com.example.tracing.consulting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
public class ConsultingController {
    private static final Logger logger = LoggerFactory.getLogger(ConsultingController.class);

    @PostConstruct
    public void init() {
        // ...
    }

    @GetMapping("/healthz")
    public String home() {
        logger.info("health check completed");
        return "health is OK";
    }

    @GetMapping("/consult")
    public String consult() {
        String result = Utilities.getProphecy();
        logger.debug("consulted with the quote: {}", result);
        return result;
    }
}

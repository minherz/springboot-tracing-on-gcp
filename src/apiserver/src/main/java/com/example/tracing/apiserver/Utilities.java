package com.example.tracing.apiserver;

import java.lang.Thread;
import java.lang.InterruptedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {
    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

    public static String makeWork(long ms) {
        logger.info("Simulate work with delay of {} milliseconds", Long.valueOf(ms));
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            logger.warn("Simulate work sleep was interrupted: {}",
                    ex.getMessage());
            return String.format("Simulated work was interrupted with error: \"%s\"",
                    ex.getMessage());
        }
        return String.format("Simulated work completed after %.2f seconds\n", (float) ms / 1000);
    }
}

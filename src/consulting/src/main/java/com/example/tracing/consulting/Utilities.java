package com.example.tracing.consulting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Utilities {
    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);
    private static final List<String> prophecies = new ArrayList<>();

    public static String getProphecy() {
        if (prophecies.size() == 0) {
            logger.debug("Loading prophecies...");
            try {
                Resource resource = new ClassPathResource("prophecies.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                while (reader.ready()) {
                    String line = reader.readLine();
                    prophecies.add(line);
                }
            } catch (IOException e) {
                logger.info("No prophecies were provided.");
                logger.warn(e.getMessage());
            }
            if (prophecies.size() == 0) {
                logger.debug("Fallback to predefined prophecies...");
                prophecies.add("To be or not to be is out of the question");
                prophecies.add("Who wakes up first, gets to wear sleepers");
                prophecies.add("Eenie meenie minie mo, catch a baby by the toe");
                prophecies.add("Born to those who have thrice defied him");
            }
        }

        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(prophecies.size());
        return prophecies.get(index);
    }
}

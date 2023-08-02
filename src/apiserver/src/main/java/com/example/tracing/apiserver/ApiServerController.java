package com.example.tracing.apiserver;

import java.net.URI;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ApiServerController {
    private static final Logger logger = LoggerFactory.getLogger(ApiServerController.class);

    @GetMapping("/")
    public String home() {
        logger.info("Home method is called. Will work a second to take care of it.");
        String s = Utilities.makeWork(1000);
        return String.format("Nobody home!\n%s", s);
    }

    @GetMapping("/work")
    public String doWork(@RequestParam(defaultValue = "local") String type) {
        if (type.equals("local")) {
            return processLocal();
        } else if (type.equals("public")) {
            return processPublic();
        } else if (type.equals("mixed")) {
            String s1 = processLocal();
            String s2 = processPublic();
            return String.format("%s<br>\n%s", s1, s2);
        } else {
            return "Not supported";
        }
    }

    private String processLocal() {
        Random random = new Random(System.currentTimeMillis());
        // random from 0 to 30 seconds
        int seconds = random.nextInt(10) + 1;
        logger.debug("Process internal call");
        logger.info("Redirecting to internal to work for {} seconds", seconds);

        String uri = String.format("http://localhost:%d/internal?wait=%d", getPort(), seconds);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        logger.debug("Internal call result: {}", result);
        return result;
    }

    private String processPublic() {
        logger.debug("Process public call");

        String consultingURI = getConsultingURI();
        if (consultingURI == null) {
            return "{error}";
        }
        logger.info("Call consulting service at {}", consultingURI);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(consultingURI, String.class);
        logger.debug("Process call result: {}", result);
        return result;
    }

    private String consultingServiceURI = null;

    private String getConsultingURI() {
        if (consultingServiceURI == null) {
            String s = System.getenv("CONSULTING_SERVICE_URL");
            if (s == null) {
                logger.error("CONSULTING_SERVICE_URL environment variable is not set.");
                return null;
            }
            URI serviceURI;
            try {
                serviceURI = URI.create(s);
            } catch (IllegalArgumentException ex) {
                logger.error("Failed to parse consulting URI ({}): {}", s, ex.getMessage());
                return null;
            }
            consultingServiceURI = StringUtils.removeEnd(serviceURI.toString(), "/");
        }
        return consultingServiceURI + "/consult";
    }

    @Autowired
    private Environment environment;

    private int getPort() {
        String port = environment.getProperty("local.server.port");
        return port != null ? Integer.parseInt(port) : 8080;
    }
}

package com.example.tracing.apiserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class InternalController {
    private static final Logger logger = LoggerFactory.getLogger(InternalController.class);

    @GetMapping("/internal")
    @ResponseBody
    public String doWork(@RequestParam(name = "wait", defaultValue = "1") String seconds) {
        logger.info("Internal work is called for {} seconds", seconds);
        String work = Utilities.makeWork(Integer.parseInt(seconds) * 1000);
        return String.format("Internal work ran. %s", work);
    }

}

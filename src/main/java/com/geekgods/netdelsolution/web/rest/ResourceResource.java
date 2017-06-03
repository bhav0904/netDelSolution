package com.geekgods.netdelsolution.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.geekgods.netdelsolution.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ResourceResource {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/resources")
    @Timed
    public ResponseEntity<List<Map<String, String>>> getResources(@RequestParam String category,
                                                                  @RequestParam(required = false) String city,
                                                                  @RequestParam(required = false) String trainingType) {
        switch (category) {
            case "Medical":
                return ResponseEntity.ok(restTemplate.getForObject("https://data.delaware.gov/resource/dhqa-h9is.json?profession_id=Medical Practice",
                        List.class));
            case "Legal":
                return ResponseEntity.ok(BotService.getLawyers(city == null ? "Wilmington" : city));
            case "Training":
                return ResponseEntity.ok(BotService.getresources(trainingType == null ? "Computer" : trainingType));
        }
        return ResponseEntity.ok(null);
    }
}

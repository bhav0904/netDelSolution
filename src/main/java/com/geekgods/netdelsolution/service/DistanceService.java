package com.geekgods.netdelsolution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class DistanceService {

    @Autowired
    private RestTemplate restTemplate;

    public double calculateDistanceInMilesBetween(String address1, String address2) {
        Map<String, Object> distanceMap = this.restTemplate.getForObject(
                "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + address1 +
                        "&destinations="+ address2 +"&mode=bicycling&language=en-EN&units=imperial", Map.class);

        Integer distanceInFeet = (Integer) ((Map<String, Object>) ((Map<String, Object>) ((List<Object>)
                ((Map<String, Object>) ((List<Object>) distanceMap.get("rows")).get(0)).get("elements")).get(0)).get("distance")).get("value");

        return Math.floor(distanceInFeet * 0.000189394);
    }
}

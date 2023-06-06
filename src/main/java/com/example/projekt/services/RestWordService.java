package com.example.projekt.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Getter
public class RestWordService {
    @Autowired
    RestTemplate restTemplate;

    String url="http://localhost:8090";

    public boolean contains(String word){
        Boolean responseBody = restTemplate.getForObject(url+"/in-dictionary/"+word,Boolean.class);
        return Boolean.TRUE.equals(responseBody.booleanValue());
    }
}

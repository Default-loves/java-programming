package com.junyi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * @time: 2021/6/18 15:18
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Main {
    @Test
    public void test() throws JsonProcessingException {
        String str = "{\"id\": 3,\"type\": \"xiaobai\",\"phone\": \"12345678\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = objectMapper.readValue(str, Person.class);
        System.out.println(person);
    }
}

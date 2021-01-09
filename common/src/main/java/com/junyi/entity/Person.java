package com.junyi.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @time: 2021/1/9 8:10
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Person extends Base {
    private String name;


    @Test
    public void func() {
        Person person = Person.builder()
                .id(1)
                .createDateTime(LocalDateTime.now())
                .name("junyi")
                .build();
        System.out.println(person.toString());
    }
}

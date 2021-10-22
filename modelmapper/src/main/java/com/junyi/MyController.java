package com.junyi;

import com.alibaba.fastjson.JSON;
import com.junyi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @time: 2021/10/21 16:11
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
@Slf4j
public class MyController {


    @GetMapping("get")
    public void get() {
        Order order = getOrder();
        log.info("before: {}", JSON.toJSONString(order));

        ModelMapper modelMapper = new ModelMapper();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        log.info("after: {}", JSON.toJSONString(orderDTO));
    }

    public Order getOrder() {
        Address address = new Address("wuyi", "shenzhen");
        Name name = new Name("a", "b");
        Order order = new Order();
        order.setBillingAddress(address);
        order.setCustomer(new Customer(name));
        return order;
    }
}

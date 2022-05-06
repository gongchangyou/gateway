package com.braindata.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/5/6 11:01
 */
@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping("hi")
    String hi() {
        return "hi";
    }

    @RequestMapping("hiflux")
    Flux<String> hiList() {

        //单个元素
        Flux.just("just").subscribe(System.out::println);
        //多个元素 没有 subscribe 不会执行reduce
        Flux.just("just", "just1", "just2").reduce((x,y)-> {
                        System.out.println(x + y);
                return x+y;
        }).subscribe(System.out::println);

        return Flux.just("xxx", "yyy");
    }

    @RequestMapping("himono")
    Mono<String> hiMono() {
        return Mono.just("zzz");
    }


}

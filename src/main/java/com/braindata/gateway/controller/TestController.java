package com.braindata.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/5/6 11:01
 */
@Slf4j
@RequestMapping("test")
@RestController
public class TestController extends BaseController{

    @RequestMapping("hi")
    String hi() {
        return "hi";
    }

    @RequestMapping("hiflux")
    Flux<Object> hiList() {

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

    static class User {
        public Date date;
        public String str;
    }

    /**
     * 这个字符串前后空格会被删除 (trim)
     * @param str
     * @return
     */
    @RequestMapping("str")
    Object str(String str) {
        log.info("str={} ",str);
        return str;
    }

    /**
     * 注意入参date 传入必须形如 2022-02-12 12:00:00
     * @param date
     * @return
     */
    @RequestMapping(value = "date")
    Object date(Date date) {
        log.info("date={}", date);
        return date;
    }

    /**
     * 这个函数进不来了, 因为Time是Date的子类，会被initBinder format，会抛异常
     * @param time
     * @return
     */
    @RequestMapping(value = "time")
    Object time(Time time) {
        log.info("time={}", time);
        return time;
    }


}

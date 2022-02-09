package com.braindata.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/2/9 9:43 上午
 */
@Slf4j
@Configuration
public class Route implements ApplicationEventPublisherAware {
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    private ApplicationEventPublisher publisher;

    @PostConstruct
    void init() {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId("id");
        routeDefinition.setOrder(3);
        routeDefinition.setUri(URI.create("http://127.0.0.1:7823"));
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("Path");
        predicateDefinition.setArgs(new HashMap<>(){{
            put("pattern", "/pay/toPay");
        }});
        routeDefinition.setPredicates(new ArrayList<>(){{
            add(predicateDefinition);
        }});
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
//        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        var list = routeDefinitionLocator.getRouteDefinitions();
        log.info(list.toString());
    }

    /**
     * Set the ApplicationEventPublisher that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param applicationEventPublisher event publisher to be used by this object
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}

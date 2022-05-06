package com.braindata.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootTest
class GatewayApplicationTests {

	/**
	 * 冷流 n个订阅者 发布者发布n次 1-4跑2次
	 * @return
	 */
	@Test
	void cold() {
		log.info("cold");
		Flux<Integer> numbers = Flux
				.just(1, 2, 3, 4)
				.log();
		numbers
				.reduce(Integer::sum)
				.subscribe(sum -> log.info("Sum is: {}", sum));
		numbers
				.reduce((a, b) -> a * b)
				.subscribe(product -> log.info("Product is: {}", product));
	}

	@Test
	void hot() {
		log.info("hot");
		Flux<Integer> numbers = Flux
				.just(1, 2, 3, 4)
				.log();
		numbers
				.reduce(Integer::sum)
				.subscribe(sum -> log.info("Sum is: {}", sum));
		numbers
				.reduce((a, b) -> {
					log.info("a={} b={}",a,b);
					return a * b;})
				.subscribe(product -> log.info("Product is: {}", product));
	}

}

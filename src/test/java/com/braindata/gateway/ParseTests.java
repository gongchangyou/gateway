package com.braindata.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;

@Slf4j
@SpringBootTest
class ParseTests {
	@Data
	static class B {
		String b;
	}

	@Data
	static class A {
		String a;
		B b;
	}

	/**
	 * 冷流 n个订阅者 发布者发布n次 1-4跑2次
	 * @return
	 */
	@Test
	void parse() {
		val mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			val result = mapper.readValue("{\"a\":\"c\",\"d\":\"bbb\",\"b\":{\"b\":\"zzz\"}}", A.class);

			log.info("result = {} b={}", result,result.getB().getB());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}


}

package com.techstack.contract;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class LearnSpringCloudContractApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test_should_return_all_employee_name() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/employees", String.class);

		String json = "[\"Pascal\", \"Thomas\"]";

		BDDAssertions.then(response.getStatusCode()).isEqualTo(201);
		BDDAssertions.then(response.getBody()).isEqualTo(json);
	}
}

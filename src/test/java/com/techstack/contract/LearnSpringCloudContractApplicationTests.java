package com.techstack.contract;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.impl.conn.Wire;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@AutoConfigureWireMock(port = 8081)
@SpringBootTest
class LearnSpringCloudContractApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test_should_return_all_employee_name() {

		String json = "[\"Pascal\", \"Thomas\"]";

		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/employees"))
				.willReturn(WireMock.aResponse().withBody(json).withStatus(201)));

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8081/employees", String.class);

		BDDAssertions.then(response.getStatusCode().value()).isEqualTo(201);
		BDDAssertions.then(response.getBody()).isEqualTo(json);
	}
}

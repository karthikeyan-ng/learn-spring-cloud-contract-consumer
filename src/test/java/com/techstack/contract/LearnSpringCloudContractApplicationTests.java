package com.techstack.contract;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
		ids = "com.techstack:learn-spring-cloud-contract-provider:+:stubs:8083")
@AutoConfigureWireMock(port = 8081)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
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

    @Test
    public void test_should_return_all_employee_name_integration_by_calling_actual_service() {

        String json = "[\"Pascal\",\"Thomas\"]";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8082/employees", String.class);

        BDDAssertions.then(response.getStatusCode().value()).isEqualTo(201);
        BDDAssertions.then(response.getBody()).isEqualTo(json);
    }

	@Test
	public void test_should_return_all_employee_name_integration_by_calling_stub_with_fixed_port() {

		String json = "[\"Pascal\",\"Thomas\"]";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8083/employees", String.class);

		BDDAssertions.then(response.getStatusCode().value()).isEqualTo(201);
		BDDAssertions.then(response.getBody()).isEqualTo(json);
	}

}

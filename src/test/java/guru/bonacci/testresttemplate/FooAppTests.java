package guru.bonacci.testresttemplate;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FooAppTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;

	@Test
	public void testAddEmployeeSuccess() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + randomServerPort + "/foos/";
		URI uri = new URI(baseUrl);
		var foo = new Foo("a", "b");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<Foo> request = new HttpEntity<>(foo, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		// Verify request succeed
		assertThat(result.getStatusCodeValue()).isEqualTo(201);
	}
}
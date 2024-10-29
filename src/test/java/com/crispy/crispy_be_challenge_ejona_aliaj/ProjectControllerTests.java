package com.crispy.crispy_be_challenge_ejona_aliaj;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.ProjectRequest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProjectControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    //@Test
    //@DirtiesContext
    void shouldCreateANewProject() {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setTitle("TODO list");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/1.0/projects", projectRequest, Void.class);
        Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewProject = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewProject, String.class);
        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        String title = documentContext.read("$.title");
        Assertions.assertThat(title).isNotNull();
        Assertions.assertThat(title).isEqualTo("TODO list");
    }

}

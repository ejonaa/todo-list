package com.crispy.crispy_be_challenge_ejona_aliaj;

import com.crispy.crispy_be_challenge_ejona_aliaj.controller.request.security.NewUserRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateANewUser() {
        NewUserRequest newUserMock = createMockUser("test1");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/api/create-account", newUserMock, Void.class);
        Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldNotCreateUserWithSameUsername() {
        NewUserRequest newUserMock = createMockUser("test2");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/api/create-account", newUserMock, Void.class);
        Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Void> retryCreateResponse = restTemplate.postForEntity("/api/create-account", newUserMock, Void.class);
        Assertions.assertThat(retryCreateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    private static NewUserRequest createMockUser(String userName) {
        NewUserRequest newUserMock = new NewUserRequest();
        newUserMock.setFirstName("Test");
        newUserMock.setLastName("Test");
        newUserMock.setLogin(userName);
        newUserMock.setPassword("test");
        return newUserMock;
    }
}

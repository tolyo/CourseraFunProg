package com.tel.resource;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tel.model.PhoneValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneResourceTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    PhoneValidator phoneValidator;

    @Before
    public void setup() {
        phoneValidator = new PhoneValidator();
    }

    @Test
    public void testEmptyRequest() {
        // when empty request
        ResponseEntity<ObjectNode> responseEntity = testRestTemplate.postForEntity("/phones", phoneValidator, ObjectNode.class);

        // then should return error
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void testInvalidPhone() {
        // when empty request
        phoneValidator.setNumber("+418379123847");
        ResponseEntity<ObjectNode> responseEntity = testRestTemplate.postForEntity("/phones", phoneValidator, ObjectNode.class);

        // then should return error
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void testValidPhone() {
        // given a request with a valid phone
        phoneValidator.setNumber("+15005550006");
        ResponseEntity<ObjectNode> responseEntity =
                testRestTemplate.postForEntity("/phones", phoneValidator, ObjectNode.class);

        // then should be OK
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

    }
}

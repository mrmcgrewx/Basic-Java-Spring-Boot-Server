/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.parser.JSONParser;
import models.Client;
import models.Credentials;
import models.Login;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class UserRequestTests {

    private Random rand;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateUser() throws Exception {
        rand = new Random();
        int n = rand.nextInt(5000) + 1;
        Client client = new Client();
        client.setUsername("testUser" + n);
        client.setPassword("123456~");
        client.setFirstname("Jon");
        client.setLastname("Doe");
        client.setEmail("jonDoe@gmail.com");

        String expected = "OK";

        postClientCreate(client,expected,HttpStatus.OK);
    }

    @Test
    public void testCheckForExistingUser() throws Exception {
        Client client = new Client();
        client.setUsername("testUser");
        client.setPassword("123456~");
        client.setFirstname("Jon");
        client.setLastname("Doe");
        client.setEmail("jonDoe@gmail.com");

        String expected = "User Exists";

        postClientCreate(client,expected,HttpStatus.BAD_REQUEST);

    }

    public void postClientCreate(Client client,String expected, HttpStatus httpStatus) {
        ResponseEntity<String> entity = restTemplate
                .postForEntity("http://localhost:" + this.port + "/client/create",client,String.class);
        assertEquals(httpStatus, entity.getStatusCode());
        assertEquals(expected,entity.getBody());
    }

    @Test
    public void testLoginFlow() {
        Credentials credentials = new Credentials();
        credentials.setUsername("testUser");
        credentials.setPassword("123456~");

        ResponseEntity<String> entity = restTemplate
                .postForEntity("http://localhost:" + this.port + "/client/login",credentials,String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        //String token = entity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Login user = null;
        try {
            user = mapper.readValue(entity.getBody(), Login.class);
        } catch (IOException e) {
            System.out.println("error getting body");
        }

        String token = user.getToken();


        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization",token);
                    return execution.execute(request, body);
                }));


        ResponseEntity<String> entity2 = restTemplate
                .getForEntity("http://localhost:" + this.port + "/client/verify",String.class);

        assertEquals(HttpStatus.OK,entity2.getStatusCode());
    }

}

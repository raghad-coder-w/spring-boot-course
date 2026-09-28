package com.example.demo.user;

import com.example.demo.components.user.*;
import com.example.demo.components.user.entity.Address;
import com.example.demo.components.user.entity.Company;
import com.example.demo.components.user.entity.Geo;
import com.example.demo.components.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(UserRestClient.class)
class UserRestClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    UserRestClient client;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldFindAllUsers() throws Exception {
        // given
        User user1 = new User(1,
                "Leanne",
                "lgraham",
                "lgraham@gmail.com",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", new Geo("-37.3159", "81.1496")),
                "1-770-736-8031 x56442",
                "hildegard.org",
                new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"));

        List<User> users = List.of(user1);

        // when
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(users), MediaType.APPLICATION_JSON));

        // then
        List<User> allUsers = client.findAll();
        assertEquals(users, allUsers);
    }

    @Test
    void shouldFindUserById() throws Exception {
        // given
        User user = new User(1,
                "Leanne",
                "lgraham",
                "lgraham@gmail.com",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", new Geo("-37.3159", "81.1496")),
                "1-770-736-8031 x56442",
                "hildegard.org",
                new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"));

        // when
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/users/1"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(user), MediaType.APPLICATION_JSON));

        // then
        User leanne = client.findById(1);
        assertEquals(user.name(), "Leanne", "User name should be Leanne");
        assertEquals(user.username(), "lgraham", "User username should be lgraham");
        assertEquals(user.email(), "lgraham@gmail.com");
        assertAll("Address",
                () -> assertEquals(user.address().street(), "Kulas Light"),
                () -> assertEquals(user.address().suite(), "Apt. 556"),
                () -> assertEquals(user.address().city(), "Gwenborough"),
                () -> assertEquals(user.address().zipcode(), "92998-3874"),
                () -> assertEquals(user.address().geo().lat(), "-37.3159"),
                () -> assertEquals(user.address().geo().lng(), "81.1496")
        );
        assertEquals(user.phone(), "1-770-736-8031 x56442");
        assertEquals(user.website(), "hildegard.org");
        assertAll("Company",
                () -> assertEquals(user.company().name(), "Romaguera-Crona"),
                () -> assertEquals(user.company().catchPhrase(), "Multi-layered client-server neural-net"),
                () -> assertEquals(user.company().bs(), "harness real-time e-markets"));
    }

}
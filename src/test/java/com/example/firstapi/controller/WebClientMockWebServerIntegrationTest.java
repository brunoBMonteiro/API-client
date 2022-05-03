package com.example.firstapi.controller;

import com.example.firstapi.service.ClienteService;
import com.example.firstapi.util.ClienteCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WebClientMockWebServerIntegrationTest {

    @Autowired
    private ClienteService clienteService;

    @Mock
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    public void givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_thenReturnsMockedObject() throws URISyntaxException, JsonProcessingException {
        var expected = ClienteCreator.createValidClient();

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://localhost:8080/clientes/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(expected))
                );


        var ss = clienteService.findById(expected.getId());
        Assertions.assertThat(expected).isEqualTo(ss);


    }

}
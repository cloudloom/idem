package com.tracebucket.idem.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracebucket.idem.IdemStarter;
import com.tracebucket.idem.rest.resource.TenantResource;
import com.tracebucket.idem.test.config.AccessTokenReceiverConfig;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Vishwajit on 03-08-2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IdemStarter.class, AccessTokenReceiverConfig.class})
@WebIntegrationTest
public class TenantControllerTest {


    private static final Logger log = LoggerFactory.getLogger(GroupControllerTest.class);

    private RestTemplate restTemplate = null;

    @Value("http://localhost:${server.port}${server.contextPath}")
    private String basePath;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccessTokenReceiverConfig accessTokenReceiver;

    private String accessToken = null;

    private TenantResource group;
}

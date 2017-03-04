package com.uran;

import com.uran.domain.Horse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class TestThisApp {
    private static final Logger log = LoggerFactory.getLogger(TestThisApp.class);
    
    public static void main(String args[]) {
       
        RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<Horse> horseResponseEntity = restTemplate.exchange(
                "http://localhost:8080/api/horses/1",
                HttpMethod.GET, new HttpEntity<Horse>(createHeaders("admin@gmail.com", "admin")),
                Horse.class
        );
        log.info(horseResponseEntity.toString());
    }
    
    private static HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}

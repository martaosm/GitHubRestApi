package com.example.GitHubRestApi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

/***
 * config class for GitHub access, without a token there is limited rate for accessing GitHub repositories,
 * but with personal access token the limit is taken off, the personal access token can be generated on GitHub account
 * Setting -> Developer Settings -> Personal Access Token
 * this class is responsible for including generated token in rest template
 * config class is commented for easier use of application, in case of reaching rate limit uncomment all lines
 */
//@Configuration
public class GitHubConfig {
//    @Value("${github.api.token}")
//    private String githubApiToken;
//
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
//            request.getHeaders().set("Authorization", "Bearer " + githubApiToken);
//            return execution.execute(request, body);
//        };
//        restTemplate.setInterceptors(Collections.singletonList(interceptor));
//        return restTemplate;
//    }
}

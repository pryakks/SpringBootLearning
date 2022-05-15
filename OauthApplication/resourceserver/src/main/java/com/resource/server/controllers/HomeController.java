package com.resource.server.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @Value("${oauth2AuthorizeUrl}")
    private String oauth2AuthorizeUrl;
    @Value("${clientId}")
    private String clientId;
    @Value("${redirectUri}")
    private String redirectUri;
    @Value("${scope}")
    private String scope;
    @Value("${responseType}")
    private String responseType;

    @Value("${tokenUrl}")
    private String tokenUrl;

    @Value("${grantType}")
    private String grantType;

    @Value("${clientSecret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    public String getDashboardPage(){
        return "index";
    }

    @GetMapping("/callAuthorizedUrl")
    public ModelAndView callAuthorizedUrl() {
        String resourceUrl = oauth2AuthorizeUrl + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                +"&scope="+scope
                +"&response_type="+responseType;
        return new ModelAndView("redirect:" + resourceUrl);
    }

    @GetMapping("/authorized")
    public String getAuthorizedCode(@RequestParam String code, Model model){
        model.addAttribute("code", code);
        return "index";
    }

    @GetMapping("/getAccessToken")
    public void getAccessToken(@RequestParam("code") String code, Model model) throws JsonProcessingException, OAuthProblemException, OAuthSystemException {
        model.addAttribute("code", code);
        code = "5SLGk5hQ4lgF92YvlzG_P_4BY_wtHiIQ-DTeqocbioAee4d6cMaDf-yFEsMta7E4tklfStYH4RCA8QffvwJDlDMuvA8dD58Mvx1gRftSZ1hEDw5kqaLW17jBqVTq3Ob2";
         OAuth2ClientCall(code);
//        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//        restTemplate = restTemplateBuilder.basicAuthentication(clientId, clientSecret).build();
//        BasicAuthClientCall(code);

//        return response;
    }

    private void BasicAuthClientCall(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> bodyParamMap = new HashMap<String, String>();
        bodyParamMap.put("grant_type", grantType);
        bodyParamMap.put("code",code);
        bodyParamMap.put("redirect_uri", redirectUri);

        String reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);

        HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData,headers);

//        ResponseEntity<Object> response = restTemplate.postForEntity(tokenUrl, requestEnty, Object.class);


        ResponseEntity<Object> response =
                restTemplate.exchange(tokenUrl,
                        HttpMethod.POST,
                        requestEnty,
                        Object.class);
        System.out.println("response : "+ response.getBody());
    }

    private void OAuth2ClientCall(String code) throws OAuthSystemException, OAuthProblemException {
        String encodedValue = getBase64Encoded(clientId, clientSecret);
        OAuthClient client =new OAuthClient(new URLConnectionClient());
        OAuthClientRequest request = OAuthClientRequest.tokenLocation(tokenUrl)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setCode(code)
                .buildBodyMessage();
        request.addHeader("Authorization", "Basic " + encodedValue);
        request.addHeader(OAuth.HeaderType.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_FORM_URLENCODED));
        OAuthJSONAccessTokenResponse oAuthResponse = client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class);
        String finalToken = oAuthResponse.getAccessToken();
        System.out.println(finalToken);
    }

    private String getBase64Encoded(String clientId, String clientSecret) {
         return Base64.getEncoder().encodeToString((clientId+":"+clientSecret).getBytes(StandardCharsets.UTF_8));
    }
}


package de.szut.lf8_project.services;

import de.szut.lf8_project.dtos.GetEmployeeDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
  //  private Token jwtToken;
    private final String URL = "https://employee.szut.dev/employees";
    private final String TOKEN_URL = "https://keycloak.szut.dev/auth/realms/szut/protocol/openid-connect/token";
    private static EmployeeService instance;

    public EmployeeService() {
        restTemplate = new RestTemplate();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

/*
    // Instanz
    public static EmployeeService getInstance() {
        if (instance == null) {
            instance = new EmployeeService();
        }
        return instance;
    }

    public String getjwtToken() {
        if (jwtToken == null || jwtToken.isExpired()) {
            buildJwtToken();
        }
        return jwtToken.getToken();
    }
    private void buildJwtToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> bodyParamMap = new LinkedMultiValueMap<>();
        bodyParamMap.add("grant_type", "password");
        bodyParamMap.add("client_id", "employee-management-service");
        bodyParamMap.add("username", "user");
        bodyParamMap.add("password", "test");

        HttpEntity<MultiValueMap<String, String>> entity =  new HttpEntity<>(bodyParamMap, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(TOKEN_URL, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK){
            String response = responseEntity.getBody();
            JSONObject tokenAsJson = new JSONObject(response);

            jwtToken  = new Token(tokenAsJson.get("access_token").toString(), LocalDateTime.now(), Long.parseLong(tokenAsJson.get("expires_in").toString()) - 2L);
        }else {
            throw new RuntimeException("Error while getting JWT-Token");
        }
    }

 */

    public <T> T redirectGet(String uri, String token, Class<? extends T> clazz) {
        headers.set("Authorization", token);
        RequestEntity<Void> request = RequestEntity.get(toUrl(uri)).headers(headers).build();
        return restTemplate.exchange(request, clazz).getBody();
    }

    public <T, B> T redirectPost(String uri, String token, Class<? extends T> clazz, B body) {
        headers.set("Authorization", token);
        RequestEntity<B> request = RequestEntity.post(toUrl(uri)).headers(headers).body(body);
        return restTemplate.exchange(request, clazz).getBody();
    }

    public <T, B> T redirectPut(String uri, String token, Class<? extends T> clazz, B body) {
        headers.set("Authorization", token);
        RequestEntity<B> request = RequestEntity.put(toUrl(uri)).headers(headers).body(body);
        return restTemplate.exchange(request, clazz).getBody();
    }

    public <T> T redirectDelete(String uri, String token, Class<? extends T> clazz) {
        headers.set("Authorization", token);
        RequestEntity<Void> request = RequestEntity.delete(toUrl(uri)).headers(headers).build();
        return restTemplate.exchange(request, clazz).getBody();
    }

    private String toUrl(String uri) {
        return "https://employee.szut.dev" + uri;
    }

    public GetEmployeeDTO findById(Long id, String token) {
        return redirectGet("/employees/" + id, token, GetEmployeeDTO.class);
    }
}

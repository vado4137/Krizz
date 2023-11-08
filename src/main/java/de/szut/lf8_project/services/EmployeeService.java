package de.szut.lf8_project.services;

import de.szut.lf8_project.dtos.GetEmployeeDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    public EmployeeService() {
        restTemplate = new RestTemplate();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

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

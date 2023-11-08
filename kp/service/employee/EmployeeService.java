package de.szut.lf8_project.service.employee;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class EmployeeService {

    public static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE2OTg4MzgxNDYsImlhdCI6MTY5ODgzNDU0NiwianRpIjoiOTM1NTFjOTMtZWY2OC00YWQ3LWFhNjEtNTliMDkzYTM4NDc2IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiI0MmQ2ZTE3Yi0yYzMzLTRkYzQtOGU2NC1lYjIyYWM0ZjljMWQiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.OlD91BqzqkPHNqNAnGrtuDF0VVuYIbEc0pLDlamVHd7VMgePO4gwkEZYUBm9524VAo9i26fpQOMZF6aRgpHabkKig_sA0JPrPaaNmZhWkwfykZXHBW56fvjia0GZyeGS2Qa9Yhx3F9mJxI0yDcMKxpPOL2XOs9g46ELpidSJu03CclUXSuNEZa1ydb2iDtB2q6gfMj3o-fCRFjBDhUSTptbQnF44I89hA8cVfg-eRQknLMg1OAuf6cPDTMbx12G_b4_v2N9C0mYOG_lL0fwIEQ7VJ-DOyVnFUMpy81AwayOkNgR3QunixbMeFsBCW--SoLHwSS1-poEn8sVeEoV9Sg";

    private final RestTemplate restTemplate;
    private String url = "https://employee.szut.dev/";

    private String auth = "access_key=" + TOKEN;

    private String tokenUrl = "https://keycloak.szut.dev";
    private String tokenDir = "/auth/realms/szut/protocol/openid-connect/token";

    public EmployeeService() {
        this.restTemplate = new RestTemplate();
    }

    public void create(EmployeeEntity entity) {
        restTemplate.postForEntity(url + "employees", entity, EmployeeEntity.class);
    }

    public EmployeeEntity getById(String token, Long employeeId) {
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> e = new HttpEntity<>(headers);
        return restTemplate.getForObject(url+"employees/" + employeeId, EmployeeEntity.class, e);
    }
}

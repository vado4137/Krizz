package de.szut.lf8_project.tests;
import de.szut.lf8_project.dtos.CreateProjectDTO;
import de.szut.lf8_project.dtos.GetProjectDTO;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.*;


public class IntegrationsTests  {

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    private String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE2OTk0NDg0NDEsImlhdCI6MTY5OTQ0NDg0MSwianRpIjoiM2M5MGUxOWEtNzg1Yi00ZTU0LThiNDAtNTRjZGU1Njg3NTU5IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiI5NDFiMjgwYi0yNGY1LTRhZTQtOWMzNC1mNDg5ODhkY2YzM2UiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbInByb2R1Y3Rfb3duZXIiLCJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtc3p1dCIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlciJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyIn0.HgX3CnUZ_mrkJ9n2PfV3LuMkI0yLun0Ax71CpguTtqwo0QseJXr_bOvzIA7WQi1Wj1v0iWpcidiPw5DXJUGqAa0zYxY6wKUssVTpPLGcXUZeJ5tYBR0t4U3LT0GlgOBLdS6jw8ZDzHgQ1dIqP39PJVRWuSX0zgrJww9GVVGPnuNgJA3_a7iE7SyukdqDjG59u1b0bMmPtJPSugtLLWfiY_Z_8UpGSr6yhXHdoLT9vIPzMLvCaa1l3jubRT97XNoK39SUU8hxbdIjt1m1iz5riVf9YR07nMg0fqIZnltP2rjaXUPnxrEVe0m96eTbUQgkaEhdMAXuVUIOGhK88EG73g";


    {
        restTemplate = new RestTemplate();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    // Try to get all Projects without token
    @Test
    public void puttAllProjects() {
        headers.set("Authorization", token);
        String uri = "https://project.szut.dev/projects";
        CreateProjectDTO project = new CreateProjectDTO();
        project.setDescription("Test");
        project.setContactName("mama");
        project.setClientId(1L);
        RequestEntity<CreateProjectDTO> request = RequestEntity.post(uri).headers(headers).body(project);
        GetProjectDTO response = restTemplate.exchange(request, GetProjectDTO.class).getBody();
        GetProjectDTO expect = new GetProjectDTO();
        expect.setDescription("Test");
        expect.setContactName("mama");
        expect.setClientId(1L);
        assertEquals(response, expect);
    }
}

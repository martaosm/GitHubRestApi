package com.example.GitHubRestApi.service;

import com.example.GitHubRestApi.error.UserNotFoundError;
import com.example.GitHubRestApi.model.Branch;
import com.example.GitHubRestApi.response.RepoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitHubService {
    private final String apiUrl = "https://api.github.com/";

//    UNCOMMENT LINES BELOW IN CASE OF REACHING RATE LIMIT
//    private final RestTemplate restTemplate;
//    @Autowired
//    public GitHubService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    //return list of all repositories that belong to the user, in case of not founding the user method returns custom error message,
    //after shooting GitHub api, method writes retrieved objects into json string and later converts them into JSONObjects for easy access to their attributes
    //method performs check if repository is a fork, required attributes are written into response object and list of them is returned
    public ResponseEntity<?> getUserRepositories(String username){
        List<RepoResponse> repoResponseList = new ArrayList<>();
        try{
            String url = apiUrl.concat("users/").concat(username).concat("/repos");
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object[]> objects = restTemplate.getForEntity(url, Object[].class);
            //UNCOMMENT LINE BELOW IN CASE OF REACHING RATE LIMIT AND COMMENT TWO LINES ABOVE
            //ResponseEntity<Object[]> objects = this.restTemplate.getForEntity(url, Object[].class);
            List<String> jsons = new ArrayList<>();
            writeToJson(objects.getBody(), jsons);
            for (String json : jsons) {
                JSONObject repoObject = new JSONObject(json);
                if (!(Boolean) repoObject.get("fork")) {
                    repoResponseList.add(new RepoResponse(repoObject.getString("name"), repoObject.getJSONObject("owner").getString("login"),
                            getBranches(username, repoObject.getString("name"))));
                }
            }
        }catch(HttpClientErrorException e){
            UserNotFoundError err = new UserNotFoundError();
            if(e.getStatusCode().equals(HttpStatusCode.valueOf(404))){
                err.setStatus(HttpStatus.NOT_FOUND.value());
                err.setMessage("User not found");
            }else if(e.getStatusCode().equals(HttpStatusCode.valueOf(403))){
                err.setStatus(HttpStatus.FORBIDDEN.value());
                err.setMessage("Rate limit reached");
            }else{
                err.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                err.setMessage("Something went wrong");
            }
            return new ResponseEntity<Object>(err, new HttpHeaders(), err.getStatus());
        }
        return ResponseEntity.ok(repoResponseList);
    }


    //gets all branches from a repository and converts them into JSONObjects for easy access to their attributes
    public List<Branch> getBranches(String username, String repoName){
        List<Branch> result = new ArrayList<>();
        String url = apiUrl.concat("repos/").concat(username).concat("/").concat(repoName).concat("/branches");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> objects = restTemplate.getForEntity(url, Object[].class);
        //UNCOMMENT LINE BELOW IN CASE OF REACHING RATE LIMIT AND COMMENT TWO LINES ABOVE
        //ResponseEntity<Object[]> objects = this.restTemplate.getForEntity(url, Object[].class);
        List<String> branchesObjects = new ArrayList<>();
        writeToJson(objects.getBody(), branchesObjects);
        for(String branchJson : branchesObjects){
            JSONObject branchObject = new JSONObject(branchJson);
            result.add(new Branch(branchObject.getString("name"), branchObject.getJSONObject("commit").getString("sha")));
        }
        return result;
    }


    //method writes provided objects into json string and adds converted object to the list of strings
    public static void writeToJson(Object[] objects, List<String> jsons) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Arrays.stream(objects).forEach(o -> {
            try {
                jsons.add(ow.writeValueAsString(o));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

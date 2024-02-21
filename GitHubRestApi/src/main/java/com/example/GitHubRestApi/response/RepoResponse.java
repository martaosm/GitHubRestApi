package com.example.GitHubRestApi.response;

import com.example.GitHubRestApi.model.Branch;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/***
 * model for a response, attributes consist of repository name,
 * login of an owner and list of branch objects (more information about an object in Branch class)
 */
public class RepoResponse implements Serializable {

    @JsonProperty("repositoryName")
    String repositoryName;
    @JsonProperty("ownerLogin")
    String ownerLogin;
    @JsonProperty("branches")
    List<Branch> branches;

    public RepoResponse() {
    }

    public RepoResponse(String repositoryName, String ownerLogin, List<Branch> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }
}

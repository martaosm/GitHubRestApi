package com.example.GitHubRestApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/***
 * model for a branch object, it consists of branch name and sha of a last commit
 */
public class Branch implements Serializable {
    @JsonProperty("branchName")
    String name;
    @JsonProperty("lastCommitSha")
    String lastCommitSha;

    public Branch() {
    }

    public Branch(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }
}

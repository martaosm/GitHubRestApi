package com.example.GitHubRestApi.error;

import lombok.Getter;

/***
 * error class for returning http status code of an error along with a message what happened
 */
@Getter
public class UserNotFoundError{
    public int status;
    public String message;

    public UserNotFoundError(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
}

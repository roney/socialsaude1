package com.socialsaude.api.response;

import com.socialsaude.socialsaudecommons.model.User;

import java.util.List;

/**
 * Created by reis on 16/05/16.
 */
public class UsersResponse {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private List<User> body;

    public List<User> getBody() {
        return body;
    }

    public void setBody(List<User> body) {
        this.body = body;
    }
}

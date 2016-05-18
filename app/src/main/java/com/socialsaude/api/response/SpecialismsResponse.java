package com.socialsaude.api.response;

import com.socialsaude.socialsaudecommons.model.Specialism;
import com.socialsaude.socialsaudecommons.model.User;

import java.util.List;

/**
 * Created by reis on 16/05/16.
 */
public class SpecialismsResponse {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private List<Specialism> body;

    public List<Specialism> getBody() {
        return body;
    }

    public void setBody(List<Specialism> body) {
        this.body = body;
    }
}

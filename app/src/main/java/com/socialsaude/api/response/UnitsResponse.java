package com.socialsaude.api.response;

import com.socialsaude.socialsaudecommons.model.HealthUnit;
import com.socialsaude.socialsaudecommons.model.User;

import java.util.List;

/**
 * Created by reis on 16/05/16.
 */
public class UnitsResponse {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private List<HealthUnit> body;

    public List<HealthUnit> getBody() {
        return body;
    }

    public void setBody(List<HealthUnit> body) {
        this.body = body;
    }
}

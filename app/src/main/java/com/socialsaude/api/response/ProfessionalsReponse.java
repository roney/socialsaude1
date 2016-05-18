package com.socialsaude.api.response;

import com.socialsaude.socialsaudecommons.model.HealthProfessional;
import com.socialsaude.socialsaudecommons.model.User;

import java.util.List;

/**
 * Created by reis on 16/05/16.
 */
public class ProfessionalsReponse {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private List<HealthProfessional> body;

    public List<HealthProfessional> getBody() {
        return body;
    }

    public void setBody(List<HealthProfessional> body) {
        this.body = body;
    }
}

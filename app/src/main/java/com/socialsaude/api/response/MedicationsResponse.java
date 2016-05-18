package com.socialsaude.api.response;

import com.socialsaude.socialsaudecommons.model.Medication;
import com.socialsaude.socialsaudecommons.model.User;

import java.util.List;

/**
 * Created by reis on 16/05/16.
 */
public class MedicationsResponse {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private List<Medication> body;

    public List<Medication> getBody() {
        return body;
    }

    public void setBody(List<Medication> body) {
        this.body = body;
    }
}
package com.socialsaude.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.socialsaude.socialsaudecommons.model.HealthProfessional;
import com.socialsaude.socialsaudecommons.model.Specialism;
import com.socialsaude.socialsaudecommons.model.User;

import java.util.List;

/**
 * Created by reis on 16/05/16.
 */
public class UsersResponse {


    @SerializedName("health_professional")
    private HealthProfessional healthProfessional;

    private User user;

    private Specialism specialism;

    public HealthProfessional getHealthProfessional() {
        return healthProfessional;
    }

    public void setHealthProfessional(HealthProfessional healthProfessional) {
        this.healthProfessional = healthProfessional;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialism getSpecialism() {
        return specialism;
    }

    public void setSpecialism(Specialism specialism) {
        this.specialism = specialism;
    }
}

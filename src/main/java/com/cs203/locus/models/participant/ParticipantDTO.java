package com.cs203.locus.models.participant;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ParticipantDTO implements Serializable {
    
    @NotBlank
    private boolean vaxStatus;

    private String vaxAwsUrl;

    public void setVaxAwsUrl(String vaxAwsUrl) {
        this.vaxAwsUrl = vaxAwsUrl;
    }

    public String getVaxAwsUrl(){
        return vaxAwsUrl;
    }

    public void setVaxStatus(boolean vaxStatus){
        this.vaxStatus = vaxStatus;
    }

    public boolean getVaxStatus(){
        return vaxStatus;
    }
}
package com.cs203.locus.models.organiser;

import java.io.Serializable;

public class OrganiserDTO implements Serializable {

    private static final long serialVersionUID = -57651050556269804L;

    private String companyName;

    private String companyAcra;

    private String companySector;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAcra() {
        return companyAcra;
    }

    public void setCompanyAcra(String companyAcra) {
        this.companyAcra = companyAcra;
    }

    public String getCompanySector() {
        return companySector;
    }

    public void setCompanySector(String companySector) {
        this.companySector = companySector;
    }

}

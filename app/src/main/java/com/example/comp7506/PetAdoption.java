package com.example.comp7506;

import java.io.Serializable;

public class PetAdoption implements Serializable {
    String organization;
    String type;
    String gender;
    String name;
    String adoptionNumber;
    String source;
    String announceDate;

    PetAdoption(String organization, String type, String gender, String name, String adoptionNumber, String source, String announceDate) {
        this.organization = organization;
        this.type = type;
        this.gender = gender;
        this.name = name;
        this.adoptionNumber = adoptionNumber;
        this.source = source;
        this.announceDate = announceDate;
    }

    public String toString() {
        String result = "";
        result = result + "[" + organization + "] ";
        result = result + "[" +  type + "] ";
        result = result + "[" +  gender + "] ";
        result = result + "[" +  name + "] ";
        result = result + "[" +  adoptionNumber + "] ";
        result = result + "[" +  source + "] ";
        result = result + "[" +  announceDate + "] ";

        return result;
    }
}

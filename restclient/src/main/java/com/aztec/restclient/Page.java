package com.aztec.restclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This is a simple Java class with a handful of properties and matching getter methods. It's annotated 
 * with @JsonIgnoreProperties from the Jackson JSON processing library to indicate that any properties 
 * not bound in this type should be ignored.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    private String name;
    private String about;
    private String phone;
    private String website;

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

}

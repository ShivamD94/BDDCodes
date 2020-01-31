package com.hilti.ta.utils;

import io.cucumber.datatable.dependency.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO representing banner.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BannerData {

    @JsonProperty
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

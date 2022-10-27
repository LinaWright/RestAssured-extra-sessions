package com.cydeo.officehours.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Place {
    @JsonProperty("place name")
    public String placeName;

    public String longitude;

    public String state;
    @JsonProperty("state abbreviation")
    public String stateAbbreviation;

    public String latitude;
}

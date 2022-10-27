package com.cydeo.officehours.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class StateCity {

        @JsonProperty("country abbreviation")
        public String countryAbbreviation;
        @JsonProperty("places")
        public List<StateCityPlaces> places = null;
        @JsonProperty("country")
        public String country;
        @JsonProperty("place name")
        public String placeName;
        @JsonProperty("state")
        public String state;
        @JsonProperty("state abbreviation")
        public String stateAbbreviation;


}

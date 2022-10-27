package com.cydeo.officehours.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StateCityPlaces {


        @JsonProperty("place name")
        public String placeName;
        @JsonProperty("longitude")
        public String longitude;
        @JsonProperty("post code")
        public String postCode;
        @JsonProperty("latitude")
        public String latitude;


}

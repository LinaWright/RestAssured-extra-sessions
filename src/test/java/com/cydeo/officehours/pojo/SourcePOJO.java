package com.cydeo.officehours.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SourcePOJO {
    @JsonProperty
    String source;
    String name;
}

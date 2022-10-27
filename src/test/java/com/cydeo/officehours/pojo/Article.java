package com.cydeo.officehours.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Article {
    @JsonProperty
    SourcePOJO source;
    String author;
    String title;
}

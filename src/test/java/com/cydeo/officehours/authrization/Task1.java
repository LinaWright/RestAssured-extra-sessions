package com.cydeo.officehours.authrization;

import com.cydeo.utils.BookItAPITestBase;
import com.cydeo.utils.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Task1 extends BookItAPITestBase {
    @Test
    public void duckTest(){
        String accessToken = getAccessToken(ConfigurationReader.getProperty("teacher_email"),ConfigurationReader.getProperty("teacher_password"));
        System.out.println("accessToken = " + accessToken);

        Map<String,?> roomMap = given().accept(ContentType.JSON)
                .and().header("Authorization", accessToken)
                .when().get("/api/rooms/duke")
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .log().all().extract().as(Map.class);

        assertThat(roomMap.get("id"),is(116));
        assertThat(roomMap.get("name"),is("duke"));
        assertThat(roomMap.get("capacity"),is(4));
        assertThat(roomMap.get("withTV"),is(true));
        assertThat(roomMap.get("withWhiteBoard"),is(false));
    }

}

package com.cydeo.utils;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

public abstract class BookItAPITestBase {

    protected static RequestSpecification teacherReqSpec;

    protected static ResponseSpecification responseSpec;

    @BeforeAll
    public static void setup(){

        baseURI = ConfigurationReader.getProperty("bookit.base.url");

        String teacherToken = getAccessToken(ConfigurationReader.getProperty("teacher_email"), ConfigurationReader.getProperty("teacher_password"));

        teacherReqSpec = given().accept(ContentType.JSON)
                .and().header("Authorization" , teacherToken)
                .log().all();

        responseSpec = expect().statusCode(200)
                .and().contentType(ContentType.JSON).logDetail(LogDetail.ALL);
    }
    public static String getAccessToken(String email, String password){
        String accessToken = given().accept(ContentType.JSON)
                .and().queryParam("email", email)
                .and().queryParam("password", password)
                .when().get("/sign").
                then().assertThat().statusCode(200)
                .and().extract().path("accessToken");
        assertThat("accessToken is empty or null",accessToken, not(emptyOrNullString()));
        Assertions.assertTrue(!accessToken.isEmpty() && accessToken != null);

        return "Bearer " + accessToken;

    }


}

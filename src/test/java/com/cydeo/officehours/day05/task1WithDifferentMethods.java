package com.cydeo.officehours.day05;

import com.cydeo.utils.HrApiTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class task1WithDifferentMethods extends HrApiTestBase {
    static int regionId;

    @Test //Post region
    public void Task1() {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("region_id", 2199);
        requestBody.put("region_name", "Test Region");
        //Post region
        regionId = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().body()
                .when().post("/regions/").prettyPeek()
                .then().statusCode(201)
                .contentType(ContentType.JSON)
                .body("region_id", is(2199))
                .body("region_name", is("Test Region"))
                .extract().response().jsonPath().getInt("region_id");
    }

    @Test //Get region
    public void Task2() {
        given().accept(ContentType.JSON)
                .pathParam("region_id", regionId)
                .when().get("regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().body("region_id", is(regionId),
                        "region_name", is("Test Region"));
    }

    @Test //delete
    public void Task3() {
        given().accept(ContentType.JSON)
                .pathParam("region_id", regionId)
                .when().delete("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .body("rowsDeleted", is(1));
    }

}

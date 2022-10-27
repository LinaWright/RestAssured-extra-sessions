package com.cydeo.officehours.day05;

import com.cydeo.officehours.pojo.PostRegion;
import com.cydeo.utils.HrApiTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class APITask4 extends HrApiTestBase {
    /**
     * 1) POST a region then do validations. Please use Map or POJO class, or JsonPath
     * given accept is json
     * and content type is json
     * When I send post request to "/regions/"
     * With json:
     * {
     * "region_id":100,
     * "region_name":"Test Region"
     * }
     * Then status code is 201
     * content type is json
     * region_id is 100
     * region_name is Test Region
     */

    @Test
    public void task1() {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("region_id", 2198);
        requestBody.put("region_name", "Test Region");
        //Post region
        int regionId = given().accept(ContentType.JSON).contentType(ContentType.JSON).body(requestBody).log().body().when().post("/regions/").prettyPeek().then().statusCode(201).contentType(ContentType.JSON).body("region_id", is(2198)).body("region_name", is("Test Region")).extract().response().jsonPath().getInt("region_id");

        /**
         * given accept is json
         * When I send GET request to "/regions/100"
         * Then status code is 200
         * content type is json
         * region_id is 100
         * region_name is Test Region
         */
        //Get region
        given().accept(ContentType.JSON).pathParam("region_id", regionId).when().get("regions/{region_id}").prettyPeek().then().statusCode(200).and().contentType(ContentType.JSON).and().body("region_id", is(regionId), "region_name", is("Test Region"));

        //delete
        given().accept(ContentType.JSON).pathParam("region_id", regionId).when().delete("/regions/{region_id}").prettyPeek().then().statusCode(200).body("rowsDeleted", is(1));



    }
        /**
         * Given accept type is Json
         * And content type is json
         * When i send PUT request to /regions/100
         * With json body:
         *    {
         *      "region_id": 100,
         *      "region_name": "Wooden Region"
         *    }
         * Then status code is 200
         * And content type is json
         * region_id is 100
         * region_name is Wooden Region
         */

        /**
         * Given accept type is Json
         * When i send DELETE request to /regions/100
         * Then status code is 200
         */

    @Test
        public void task2 () {
        int region_id = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("region_id", 82345)
                .body(new PostRegion(82345, "whatever"))//serialization POJO->Json
                .when().put("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("region_id", is(82345))
                .body("region_name", is("whatever"))
                .extract().jsonPath().getInt("region_id");

       // delete same region
        given().accept(ContentType.JSON)
                .pathParam("region_id",region_id)
                .when().delete("/regions/{region_id}")
                .then().statusCode(200)
                .body("rowsDeleted",is(1));

        }

}

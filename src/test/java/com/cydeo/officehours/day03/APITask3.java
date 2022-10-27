package com.cydeo.officehours.day03;

import com.cydeo.officehours.pojo.StateCity;
import com.cydeo.officehours.pojo.StateCityPlaces;
import com.cydeo.officehours.pojo.ZipInfo;
import com.cydeo.utils.ZipCodeTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class APITask3 extends ZipCodeTestBase {
    @Test
    public void task1() {
        //Given Accept application/json
        //And path zipcode is 22031
        //When I send a GET request to /us endpoint
        //Then status code must be 200
        //And content type must be application/json
        //And Server header is cloudflare
        //And Report-To header exists
        //And body should contains following information
        //    post code is 22031
        //    country  is United States
        //    country abbreviation is US
        //    place name is Fairfax
        //    state is Virginia
        //    latitude is 38.8604

        Response response = given().accept(ContentType.JSON)
                .pathParam("postal-code", 22031)
                .when().get("/us/{postal-code}").prettyPeek();
        //Then status code must be 200
        assertEquals(200, response.statusCode());

        //And content type must be application/json
        assertEquals(ContentType.JSON.toString(), response.contentType());

        //And Server header is cloudflare
        assertEquals("cloudflare", response.getHeader("Server"));

        //And Report-To header exists
        assertFalse(response.getHeader("Report-To").isEmpty());
        assertTrue(response.getHeaders().hasHeaderWithName("Report-To"));

        //OPTION1 --> response as
        ZipInfo zipInfo = response.as(ZipInfo.class);

        //    post code is 22031
        assertEquals("22031", zipInfo.getPostCode());
        //    country  is United States
        assertEquals("United States", zipInfo.getCountry());
        //    country abbreviation is US
        assertEquals("US", zipInfo.getCountryAbbreviation());
        //    place name is Fairfax
        assertEquals("Fairfax", zipInfo.getPlaces().get(0).getPlaceName());
        //    state is Virginia
        assertEquals("Virginia", zipInfo.getPlaces().get(0).getState());
        //    latitude is 38.8604
        assertEquals("38.8604", zipInfo.getPlaces().get(0).getLatitude());


        System.out.println("=======JSONPATH GETOBJECT=======");
        //OPTION2 -->jsonpath
        ZipInfo zipInfoJsonPath = response.jsonPath().getObject("", zipInfo.getClass());
        //    post code is 22031
        assertEquals("22031", zipInfoJsonPath.getPostCode());
        //    country  is United States
        assertEquals("United States", zipInfoJsonPath.getCountry());
        //    country abbreviation is US
        assertEquals("US", zipInfoJsonPath.getCountryAbbreviation());
        //    place name is Fairfax
        assertEquals("Fairfax", zipInfoJsonPath.getPlaces().get(0).getPlaceName());
        //    state is Virginia
        assertEquals("Virginia", zipInfoJsonPath.getPlaces().get(0).getState());
        //    latitude is 38.8604
        assertEquals("38.8604", zipInfoJsonPath.getPlaces().get(0).getLatitude());
    }

    @Test
    public void task3jsonPath() {
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().pathParam("state", "VA")
                .and().pathParam("place name", "Fairfax")
                .when().get("/us/{state}/{place name}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON).extract().jsonPath();
/*
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("state", "VA")
                .and().pathParam("place name", "Fairfax")
                .when().get("/us/{state}/{place name}");
        assertEquals(200,response.statusCode());
        assertEquals("application/json",response.contentType());
        response.prettyPrint();

 */

        assertEquals("US", jsonPath.getString("'country abbreviation'"));
        assertEquals("United States", jsonPath.getString("country"));
        assertEquals("Fairfax", jsonPath.getString("'place name'"));

        List<String> places = jsonPath.getList("places.'place name'");
        for (String each : places) {
            assertTrue(each.contains("Fairfax"));
        }

        List<String> postCodes = jsonPath.getList("places.'post code'");
        for (String each : postCodes) {
            assertTrue(each.startsWith("22"));
        }

    }

    @Test
    public void task3POJO() {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("state", "VA");
        pathParams.put("place name", "Fairfax");

        Response response = given().accept(ContentType.JSON)
                .and().pathParams(pathParams)
                .and().get("/us/{state}/{place name}");

        assertEquals(HttpStatus.SC_OK,response.statusCode());
        //assertEquals("application/json",response.contentType());
        assertEquals(ContentType.JSON.toString(),response.contentType());

        //deserialize json to pojo
        StateCity stateCity = response.as(StateCity.class);
        assertEquals("VA",stateCity.getStateAbbreviation());
        assertEquals("United States",stateCity.getCountry());
        assertEquals("fairfax",stateCity.getPlaceName());

        List<StateCityPlaces> places = stateCity.getPlaces();

        places.get(places.size()-1).getPlaceName();
        for (int i = 0; i < places.size(); i++) {
            assertTrue(places.get(i).getPlaceName().contains("fairfax"));
            assertTrue(places.get(i).postCode.startsWith("22"));
        }

        assertTrue(places.stream().allMatch(each -> each.getPlaceName().contains("Fairfax")));
        assertTrue(places.stream().allMatch(each -> each.getPostCode().startsWith("22")));
    }

}

package com.cydeo.officehours.day02;

import com.cydeo.utils.HrApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

public class APITask2 extends HrApiTestBase {
    /**
     * Homework: HR ORDS API : Jsonpath | Path()
     * Feel free to use any project and class for automation.
     * <p>
     * Please use jsonPath approach to extract values from response.
     * <p>
     * ORDS API:
     * Q1:
     * - Given accept type is Json
     * - Path param value- US
     * - When users sends request to /countries
     * - Then status code is 200
     * - And Content - Type is Json
     * - And country_id is US
     * - And Country_name is United States of America
     * - And Region_id is 2
     */
    @Test
    public void task() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("country_id", "US")
                .when().get("/countries/{country_id}");


        assertEquals(200,response.getStatusCode());
        response.prettyPrint();

        assertEquals(ContentType.JSON.toString(),response.getContentType());


        //convert response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        assertEquals("US",jsonPath.getString("country_id"));
        assertEquals("United States of America",jsonPath.getString("country_name"));
        assertEquals(2,jsonPath.getInt("region_id"));

    }

    /**
     * Q2:
     * - Given accept type is Json
     * - Query param value - q={"department_id":80}
     * - When users sends request to /employees
     * - Then status code is 200
     * - And Content - Type is Json
     * - And all job_ids start with 'SA'
     * - And all department_ids are 80
     * - Count is 25
     *
     */
    @Test
    public void task02(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"department_id\":80}").
                when().get("/employees");

        response.prettyPrint();
        //- And all job_ids start with 'SA'

        JsonPath jsonPath = response.jsonPath();
        //OPTION 1
        List<String> list = jsonPath.getList("items.job_id");
        System.out.println(list);
        List<String> afterFilter = list.stream().filter(each -> each.startsWith("SA")).collect(Collectors.toList());
        assertEquals(afterFilter.size(),list.size());

        //OPTION 2
        assertTrue(list.stream().allMatch(each->each.startsWith("SA")));
        //- Count is 25
        int count = jsonPath.getInt("count");
        System.out.println(count);
        assertEquals(25,count);

        //all department_id is 80
        List<Integer>departmentId = jsonPath.getList("item.department_id");
        assertTrue(departmentId.stream().allMatch(each->each==80));
    }

    /**
     * Q3:
     * - Given accept type is Json
     * -Query param value q= region_id 3
     * - When users sends request to /countries
     * - Then status code is 200
     * - And all regions_id is 3
     * - And count is 6
     * - And hasMore is false
     * - And Country_name are;
     * Australia,China,India,Japan,Malaysia,Singapore
     */
    @Test
    public void test3(){

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":3}").
                when().get("/countries").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("items.country_name",hasItems("Australia","China","India","Japan","Malaysia","Singapore"))
                .extract().response();

        //prettyPeek --> returns response and prints response into screen
        //it helps us to continue chaining as a response

        //prettyPrint--> returns String and prints response into screen

        //- And all regions_id is 3
        JsonPath jsonPath = response.jsonPath();
        List<Integer> regionIDs = jsonPath.getList("items.region_id");

        assertTrue(regionIDs.stream().allMatch(each->each==3));

        //- And count is 6
        int count = jsonPath.getInt("count");
        System.out.println(count);
        assertEquals(6,count);

        //- And hasMore is false
        assertFalse(jsonPath.getBoolean("hasMore"));

    }
}

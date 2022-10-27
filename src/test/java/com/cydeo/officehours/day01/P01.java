package com.cydeo.officehours.day01;

import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.TypiCodeTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;


import javax.management.Query;
import java.nio.file.Path;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P01 extends TypiCodeTestBase {
    /**
     * - Given accept type is Json
     * - When user sends request to v
     * <p>
     * -Then print response body
     * <p>
     * - And status code is 200
     * - And Content - Type is Json
     */

    @Test
    public void verifyResponds() {
        Response response = given().log().all().accept(ContentType.JSON)
                .when().get("/posts");
        response.prettyPrint();
        System.out.println(response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());
    }

    /**
     * Q2:
     * - Given accept type is Json
     * - Path param "id" value is 1
     * - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
     * - Then status code is 200
     * And json body contains "repellat provident"
     * And header Content - Type is Json
     * And header "X-Powered-By" value is "Express"
     * And header "X-Ratelimit-Limit" value is 1000
     * And header "Age" value is more than 100
     * And header "NEL" value contains "success_fraction"
     */

    @Test
    public void respondsBody() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 1)
                .when().get("posts/{id}");

        assertEquals(200, response.statusCode());
        assertTrue(response.asString().contains("repellat provident"));
        assertEquals("application/json; charset=utf-8", response.contentType());
        assertEquals("Express", response.headers().getValue("X-Powered-By"));
        assertEquals("1000", response.headers().getValue("X-Ratelimit-Limit"));

        //How can we verify Date？
        System.out.println(response.getHeader("Date"));
        assertTrue(response.getHeaders().hasHeaderWithName("Date"));

        //And header "Age" value is more than 100
        String age = response.getHeader("Age");
        assertTrue(Integer.parseInt(age) >= 100);

        Integer ageValueOf = Integer.valueOf(response.getHeader("Age"));
        assertTrue(response.getHeader("NEL").contains("success_fraction"));

    }

    /**
     * Q3:
     * <p>
     * - Given accept type is Json
     * - Path param "id" value is 12345
     * - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
     * - Then status code is 404
     * <p>
     * - And json body contains "{}"
     */
    @Test
    public void postWithInvalidID() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 12345)
                .when().get("posts/{id}");
        assertEquals(404, response.statusCode());
        assertEquals("{}", response.asString());
        assertTrue(response.asString().contains("{}"));
    }

    /**
     * Q4:
     * - Given accept type is Json
     * - Path param "id" value is 2
     * - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}/comments
     * - Then status code is 200
     * <p>
     * - And header Content - Type is Json
     * - And json body contains "Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"
     */
    @Test
    public void Task4() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 2)
                .when().get("posts/{id}/comments");
        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());
        assertTrue(response.asString().contains("Presley.Mueller@myrl.com"));
        assertTrue(response.asString().contains("Dallas@ole.me"));
        assertTrue(response.asString().contains("Mallory_Kunze@marie.org"));

        //response.path
        String email = response.path("[0].email");
        System.out.println(email);

        //Jsonpath
        JsonPath jsonPath = response.jsonPath();
        System.out.println(jsonPath.getString("[0].email"));

    }
        /**
         * Q5:
         *
         * - Given accept type is Json
         * - Query Param "postId" value is 1
         * - When user sends request to  https://jsonplaceholder.typicode.com/comments
         * - Then status code is 200
         *
         * - And header Content - Type is Json
         *
         * - And header "Connection" value is "keep-alive"
         * - And json body contains "Lew@alysha.tv"
         */
        @Test
        public void Task5() {
            Response response = given().accept(ContentType.JSON)
                    .queryParam("postId", 1)
                    .when().get("/comments");
            assertEquals(200, response.statusCode());
            response.prettyPrint();
            assertEquals("keep-alive",response.getHeader("Connection"));
            assertTrue(response.asString().contains("Lew@alysha.tv"));
        }
         /**
         * Q6:
         *
         * - Given accept type is Json
         * - Query Param "postId" value is 333
         * - When user sends request to  https://jsonplaceholder.typicode.com/comments
         *
         * - And header Content - Type is Json
         *
         * - And header "Content-Length" value is 2
         * - And json body contains "[]"
         */

}
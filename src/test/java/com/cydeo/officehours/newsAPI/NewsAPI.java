package com.cydeo.officehours.newsAPI;

import com.cydeo.officehours.pojo.Article;
import com.cydeo.utils.NewsAPITestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import java.util.List;

import static io.restassured.RestAssured.*;

public class NewsAPI extends NewsAPITestBase {

    @Test
    public void queryParams() {
        given().accept(ContentType.JSON)
                .queryParams("q", "bitcoin")
                .queryParams("apiKey", "baeb0ecdd475447491722afdf41690ea")
                .get("/everything").prettyPeek()
                .then().statusCode(200);
    }

    @Test
    public void headerAuth(){
        given().accept(ContentType.JSON)
                .queryParams("q", "bitcoin")
                .header("x-api-key","baeb0ecdd475447491722afdf41690ea")
                .get("/everything").prettyPeek()
                .then().statusCode(200);
    }

    @Test
    public void headerAuthorization(){
        given().accept(ContentType.JSON)
                .queryParams("q", "bitcoin")
                .header("Authorization","baeb0ecdd475447491722afdf41690ea")
                .get("/everything").prettyPeek()
                .then().statusCode(200);
    }

    @Test
    public void GETEverything(){
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .queryParams("q", "bitcoin")
                .header("Authorization", "baeb0ecdd475447491722afdf41690ea")
                .get("/everything").prettyPeek()
                .then().statusCode(200)
                .extract().jsonPath();
        List<Article> articles = jsonPath.getList("articles");
        System.out.println("articles = " + articles);
    }

}

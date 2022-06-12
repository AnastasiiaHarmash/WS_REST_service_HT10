package com.epam.lab;

import com.epam.lab.pojo.Author;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class AuthorPostPutDeleteTests {

    private static final String getAllAuthors = "/api/library/authors";
    private static final String getAuthor = "/api/library/author/";
    private static final String getAuthorsSearch = "/api/library/authors/search/?query=";
    private static final String CONTENT_TYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final int OK = 200;

    @BeforeMethod
    public void preConditionCreateAuthor()
    {
        long id = 1;
        String firstName = "Vasya";
        String secondName = "Cat";
        String nationality = "multinational";
        String date = "2000-11-11";
        String country = "CatLand";
        String city = "Capital";
        String authorDescription = "He writes one book every few years because he is lazy and constantly sleeps.";

        Author newAuthor = new Author(id, new Author.Name(firstName, secondName), nationality, new Author.Birth(date, country, city), authorDescription);
        Response response = given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .and()
                .body(newAuthor)
                .when()
                .post(getAuthor)
                .then()
                .extract().response();
    }

    @AfterMethod
    public void postConditionDeleteAuthor() {
        long id = 1;
        Response response = get(getAuthor + id);
        Response response1 = given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .when()
                .delete(getAuthor + id)
                .then()
                .extract().response();
    }

    @Test(description = "POST create a new author")
    public void createNewAuthor() {

        postConditionDeleteAuthor();

        long id = 1;
        String firstName = "Vasya";
        String secondName = "Cat";
        String nationality = "multinational";
        String date = "2000-11-11";
        String country = "CatLand";
        String city = "Capital";
        String authorDescription = "He writes one book every few years because he is lazy and constantly sleeps.";

        Author newAuthor = new Author(id, new Author.Name(firstName, secondName), nationality, new Author.Birth(date, country, city), authorDescription);
        Response response = given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .and()
                .body(newAuthor)
                .when()
                .post(getAuthor)
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(String.valueOf(id), response.jsonPath().getString("authorId"));
        Assert.assertEquals(firstName, response.jsonPath().getString("authorName.first"));
        Assert.assertEquals(secondName, response.jsonPath().getString("authorName.second"));
        Assert.assertEquals(nationality, response.jsonPath().getString("nationality"));
        Assert.assertEquals(date, response.jsonPath().getString("birth.date"));
        Assert.assertEquals(country, response.jsonPath().getString("birth.country"));
        Assert.assertEquals(city, response.jsonPath().getString("birth.city"));
        Assert.assertEquals(authorDescription, response.jsonPath().getString("authorDescription"));

    }

    @Test(description = "PUT update an existed author")
    public void updateExistedAuthor() {

        long id = 1;
        String firstName = "UpdateVasya";
        String secondName = "UpdateCat";
        String nationality = "multinational";
        String date = "2000-11-11";
        String country = "CatLand";
        String city = "Capital";
        String authorDescription = "Wake up when September ends.";

        Author newAuthor = new Author(id, new Author.Name(firstName, secondName), nationality, new Author.Birth(date, country, city), authorDescription);
        Response response = given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .and()
                .body(newAuthor)
                .when()
                .put(getAuthor)
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), OK);
        Assert.assertEquals(String.valueOf(id), response.jsonPath().getString("authorId"));
        Assert.assertEquals(firstName, response.jsonPath().getString("authorName.first"));
        Assert.assertEquals(secondName, response.jsonPath().getString("authorName.second"));
        Assert.assertEquals(nationality, response.jsonPath().getString("nationality"));
        Assert.assertEquals(date, response.jsonPath().getString("birth.date"));
        Assert.assertEquals(country, response.jsonPath().getString("birth.country"));
        Assert.assertEquals(city, response.jsonPath().getString("birth.city"));
        Assert.assertEquals(authorDescription, response.jsonPath().getString("authorDescription"));

    }

    @Test(description = "DELETE an existed author")
    public void deleteExistedAuthor() {

        long id = 1;

        Response response = get(getAuthor + id);
        Response response1 = given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .when()
                .delete(getAuthor + id)
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), OK);
    }
}

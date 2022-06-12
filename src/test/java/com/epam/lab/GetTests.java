package com.epam.lab;

import com.epam.lab.pojo.Author;
import com.epam.lab.pojo.Genre;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.*;

public class GetTests {

    private static final String getAllAuthors = "/api/library/authors";
    private static final String getAuthor = "/api/library/author/";
    private static final String getAuthorsSearch = "/api/library/authors/search/?query=";
    private static final String CONTENT_TYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final int OK = 200;

    //AUTHOR
    //GET
    @Test(description = "GET all authors")
    public void getAllAuthors()
    {
        Response response = get(getAllAuthors);
        List<Author> authors = response.jsonPath().getList("$", Author.class);
        //authors.forEach(System.out::println);
        Assert.assertEquals(response.getStatusCode(), OK);
        Assert.assertEquals(authors.size(), 10);
    }

    @Test(description = "GET author by ID")
    public void getAuthorById()
    {
        Response response = get(getAllAuthors);
        List<Author> authors = response.jsonPath().getList("$", Author.class);
        long id = authors.get(0).getAuthorId();
        Response response1 = get(getAuthor + id);
        Assert.assertEquals(response1.getStatusCode(), OK);
    }

    @Test(description = "GET search by keyword")
    public void getSearchByKeyWord()
    {
        String searchWord = "Zella";

        List<Author> authors = given()
                .when()
                .contentType(ContentType.JSON)
                .get(getAuthorsSearch + searchWord)
                .then().log().all()
                .extract().body().jsonPath().getList("$", Author.class);
        for (Author author : authors) {
            Assert.assertTrue(author.getAuthorName().getFirst().equals(searchWord) || author.getAuthorName().getSecond().equals(searchWord));
        }
    }

    //api/library/book/{bookId}/author get Author of special Book
    //api/library/genre/{genreId}/authors get all Authors in special Genre

    @Test(description = "GET author by ID. Negative test.")
    public void getAuthorByIdNegative()
    {
        long id = -1;
        Response response = get(getAuthor + id);
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test(description = "GET genre of special author")
    public void getAllGenresOfSpecialAuthor() {

        Response response = get(getAllAuthors);
        List<Author> authors = response.jsonPath().getList("$", Author.class);
        long id = authors.get(0).getAuthorId();
        response = get("/api/library/author/" + id + "/genres");
        Assert.assertEquals(response.getStatusCode(), OK);
        List<Genre> genres = response.jsonPath().getList("$", Genre.class);
        genres.forEach(System.out::println);
    }

}

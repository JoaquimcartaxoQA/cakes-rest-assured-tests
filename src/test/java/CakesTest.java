import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CakesTest {

    private static RequestSpecification requestSpec;
    private final List<Integer> createdCakeIds = new ArrayList<>();

    @BeforeAll
    static void setup() {
        baseURI = "http://localhost:8081";
        requestSpec = TestConfig.getRequestSpecification();
    }

    @AfterEach
    void cleanup() {
        for (int id : createdCakeIds){
            given(requestSpec)
            .when()
                    .delete("/cakes/" + id);
        }
        createdCakeIds.clear();
    }

    @Test
    void shouldReturn200WhenGettingCakes() {

        given(requestSpec)
        .when()
               .get("/cakes")
        .then()
               .log()
               .all()
               .statusCode(200);
    }

    @Test
    void shouldReturn200WhenGettingCakeById() {

        given(requestSpec)
        .when()
               .get("/cakes/1")
        .then()
               .log()
               .all()
               .statusCode(200);
    }

    @Test
    void shouldReturn404WhenGettingCakeById() {

        given(requestSpec)
        .when()
               .get("/cakes/999999")
        .then()
               .log()
               .all()
               .statusCode(404);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
    void shouldReturn200WhenGettingCakeById(int id) {
        given(requestSpec)
        .when()
               .get("/cakes/" + id)
        .then()
               .log()
               .all()
               .statusCode(200);
    }

    @Test
    void shouldReturn201WhenCreatingCake() {

        given(requestSpec)
                .contentType("application/json")
                .body(CakesTestData.validCake())
        .when()
                .post("/cakes")
        .then()
                .log()
                .all()
                .statusCode(201)
                .body("title", equalTo("Chocolate Cake"))
                .body("id", notNullValue());
    }

   /* //Verificar regra de negócio, titulo null com permissão para crear, mas quebra metodo GETall.
    @Test
    void shouldReturn201WhenCreatingCakeWithoutTitle() {

        given(requestSpec)
                .contentType("application/json")
                .body(CakesTestData.cakeWithoutTitle())
        .when()
                .post("/cakes")
        .then()
                .log()
                .all()
                .statusCode(201)
                .body("title", nullValue())
                .body("description", equalTo("Cake sem título"))
                .body("id", notNullValue());
    } */

    @Test
    void shouldReturn201WhenCreatingCakeWithEmptyTitle() {

        given(requestSpec)
                .contentType("application/json")
                .body(CakesTestData.cakeWithEmptyTitle())
        .when()
                .post("/cakes")
        .then()
                .log()
                .all()
                .statusCode(201)
                .body("title", equalTo(""))
                .body("description", equalTo("Cake com título vazio"))
                .body("id", notNullValue());;
    }
}
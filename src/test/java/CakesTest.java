import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CakesTest {

    private static RequestSpecification requestSpec;

    @BeforeAll
    static void setup() {
        baseURI = "http://localhost:8081";
        requestSpec = TestConfig.getRequestSpecification();
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

        Map<String, String> newCake = Map.of(
                "title", "Chocolate Cake",
                "description", "Delicious chocolate cake with ganache"
        );
        given(requestSpec)
                .contentType("application/json")
                .body(newCake)
        .when()
                .post("/cakes")
        .then()
                .log()
                .all()
                .statusCode(201)
                .body("title", equalTo("Chocolate Cake"))
                .body("id", notNullValue());
    }
}
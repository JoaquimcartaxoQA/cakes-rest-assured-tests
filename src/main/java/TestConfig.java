import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.authentication.PreemptiveAuthProvider;

public class TestConfig {

    public static RequestSpecification getRequestSpecification() {

        return new RequestSpecBuilder()
                .setAuth(
                        new PreemptiveAuthProvider()
                                .basic("cake-user", "cake123")
                )
                .build();
    }
}

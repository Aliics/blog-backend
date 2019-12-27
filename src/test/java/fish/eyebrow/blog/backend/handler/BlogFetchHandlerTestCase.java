package fish.eyebrow.blog.backend.handler;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fish.eyebrow.blog.backend.TestUtil;
import fish.eyebrow.blog.backend.guice.BlogBackendModule;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlogFetchHandlerTestCase {

    public static final String PATH = "http://localhost:8080/blog";

    private Vertx vertx;


    @BeforeEach
    void setUp() {
        Injector injector = Guice.createInjector(new BlogBackendModule());
        vertx = injector.getInstance(Vertx.class);
    }


    @AfterEach
    void tearDown() {
        vertx.close();
    }


    @Test
    void shouldRespondWithEmptyJsonWhenNoPostsExist() {
        when()
                .get(PATH)
                .then()
                .body(equalTo(TestUtil.readFile("empty_response.json")));
    }


    @Test
    void shouldRespondWith400WhenGivenInverseRange() {
        given()
                .param("start", 0)
                .param("end", -1)
                .when()
                .get(PATH)
                .then()
                .statusCode(400)
                .body(emptyString());
    }


    @Test
    void shouldRespondWith400WhenGivenBadStart() {
        given()
                .param("start", "bad")
                .param("end", 0)
                .when()
                .get(PATH)
                .then()
                .statusCode(400)
                .body(emptyString());
    }
}

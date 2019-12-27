package fish.eyebrow.blog.backend.handler;

import static io.restassured.RestAssured.when;
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
                .get("http://localhost:8080/blog")
                .then()
                .body(equalTo(TestUtil.readFile("empty_response.json")));
    }
}

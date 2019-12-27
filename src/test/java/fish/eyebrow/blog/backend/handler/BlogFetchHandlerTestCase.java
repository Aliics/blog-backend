package fish.eyebrow.blog.backend.handler;

import static io.restassured.RestAssured.when;
import static io.vertx.core.Vertx.vertx;
import static org.hamcrest.Matchers.equalTo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fish.eyebrow.blog.backend.TestUtil;
import fish.eyebrow.blog.backend.guice.BlogBackendModule;
import fish.eyebrow.blog.backend.verticle.BlogBackendVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
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

package fish.eyebrow.blog.backend.handler;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import fish.eyebrow.blog.backend.dao.BlogDao;
import fish.eyebrow.blog.backend.util.FileUtil;
import fish.eyebrow.blog.backend.guice.BlogBackendModule;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlogFetchHandlerTestCase {

    public static final String PATH = "http://localhost:8080/blog";

    @Mock
    private BlogDao blogDao;

    private Vertx vertx;


    @BeforeEach
    void setUp() {
        Module testModule = Modules.override(new BlogBackendModule()).with(new AbstractModule() {
            @Override
            protected void configure() {
                bind(BlogDao.class).toInstance(blogDao);
            }
        });

        Injector injector = Guice.createInjector(testModule);
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
                .body(equalTo(FileUtil.readFile("empty_response.json")));

        verify(blogDao, times(1))
                .getPostsInRange(Long.MIN_VALUE, Long.MAX_VALUE);
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

        verify(blogDao, times(0))
                .getPostsInRange(anyLong(), anyLong());
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

        verify(blogDao, times(0))
                .getPostsInRange(anyLong(), anyLong());
    }
}

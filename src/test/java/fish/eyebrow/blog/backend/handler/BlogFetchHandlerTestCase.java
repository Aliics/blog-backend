package fish.eyebrow.blog.backend.handler;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import fish.eyebrow.blog.backend.dao.BlogDao;
import fish.eyebrow.blog.backend.guice.BlogBackendModule;
import fish.eyebrow.blog.backend.model.Post;
import fish.eyebrow.blog.backend.util.FileUtil;
import io.restassured.RestAssured;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BlogFetchHandlerTestCase {

    public static final String PATH = "http://localhost:8080/blog";

    @Mock
    private BlogDao blogDao;

    private Vertx vertx;


    @BeforeEach
    void setUp() {
        Module testModule = Modules
                .override(new BlogBackendModule())
                .with(new AbstractModule() {
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
        RestAssured
                .when()
                .get(PATH)
                .then()
                .body(equalTo(FileUtil.readFile("response/empty_response.json")));

        verify(blogDao, times(1))
                .getPostsInRange(Long.MIN_VALUE, Long.MAX_VALUE);
    }


    @Test
    void shouldRespondWithBlogPostsInJsonWhenPostsExist() {
        Post post = new Post();
        post.setId(1);
        post.setTitle("B U I L D W A L L");

        when(blogDao.getPostsInRange(anyLong(), anyLong())).thenReturn(List.of(post));

        RestAssured
                .when()
                .get(PATH)
                .then()
                .body(equalTo(FileUtil.readFile("response/single_post_response.json")));

        verify(blogDao, times(1))
                .getPostsInRange(Long.MIN_VALUE, Long.MAX_VALUE);
    }


    @Test
    void shouldRespondWith400WhenGivenInverseRange() {
        RestAssured
                .given()
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
        RestAssured
                .given()
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

package fish.eyebrow.blog.backend.handler;

import static org.hamcrest.Matchers.emptyString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlogCreationHandlerTestCase {

    public static final String PATH = "http://localhost:8080/blog";

    @Mock
    private BlogDao blogDao;

    private Vertx vertx;

    private ArgumentCaptor<Post> postCaptor;


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
        postCaptor = ArgumentCaptor.forClass(Post.class);
    }


    @AfterEach
    void tearDown() {
        vertx.close();
    }


    @Test
    void shouldRespondWith200AndInvokeBlogDaoInsertion() {
        when(blogDao.insertPost(any(Post.class))).thenReturn(true);

        Post expected = new Post();
        expected.setTitle("posterino");

        RestAssured
                .given()
                .body(FileUtil.readFile("request/new_post_request.json"))
                .when()
                .post(PATH)
                .then()
                .statusCode(200)
                .body(emptyString());

        verify(blogDao, times(1)).insertPost(postCaptor.capture());

        Post post = postCaptor.getValue();
        assertEquals(expected.getTitle(), post.getTitle());
    }


    @Test
    void shouldRespondWith400WhenPostCannotDeserialize() {
        RestAssured
                .given()
                .body(FileUtil.readFile("request/bad_post_request.json"))
                .when()
                .post(PATH)
                .then()
                .statusCode(400)
                .body(emptyString());

        verify(blogDao, times(0)).insertPost(any(Post.class));
    }
}

package fish.eyebrow.blog.backend.guice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fish.eyebrow.blog.backend.dao.BlogDao;
import fish.eyebrow.blog.backend.verticle.BlogBackendVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class BlogBackendModuleTestCase {

    private Injector injector;


    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new BlogBackendModule());
    }


    @Test
    void shouldCreateConfiguration() {
        JsonObject expected = new JsonObject()
                .put("port", 8080)
                .put("db_url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        JsonObject actual = injector.getInstance(JsonObject.class);

        assertEquals(expected, actual);
    }


    @Test
    void shouldCreateBlogBackendVerticle() {
        Verticle verticle = injector.getInstance(BlogBackendVerticle.class);

        assertNotNull(verticle);
    }


    @Test
    void shouldCreateJdbcConnection() {
        Connection connection = injector.getInstance(Connection.class);

        assertNotNull(connection);
    }


    @Test
    void shouldCreateBlogDao() {
        BlogDao blogDao = injector.getInstance(BlogDao.class);

        assertNotNull(blogDao);
    }


    @Test
    void shouldCreateVertxAndDeployVerticles() {
        Vertx vertx = injector.getInstance(Vertx.class);

        assertNotNull(vertx);

        vertx.close();
    }
}

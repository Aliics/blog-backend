package fish.eyebrow.blog.backend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fish.eyebrow.blog.backend.guice.BlogBackendModule;
import fish.eyebrow.blog.backend.model.Post;
import fish.eyebrow.blog.backend.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class BlogDaoTestCase {

    private BlogDao blogDao;

    private Connection connection;


    @BeforeEach
    void setUp() {
        Injector injector = Guice.createInjector(new BlogBackendModule());
        blogDao = injector.getInstance(BlogDao.class);
        connection = injector.getInstance(Connection.class);

        executeUpdateSql("sql/create_posts_table.sql");
    }


    @Test
    void shouldReturnTwoPostsWhenQueryingAllPosts() {
        executeUpdateSql("sql/insert_two_posts.sql");

        List<Post> actual = blogDao.getPostsInRange(Long.MIN_VALUE, Long.MAX_VALUE);
        Post post0 = actual.get(0);
        Post post1 = actual.get(1);

        assertEquals(2, actual.size());

        assertEquals(1, post0.getId());
        assertEquals("a cool post", post0.getTitle());

        assertEquals(2, post1.getId());
        assertEquals("another cool post", post1.getTitle());
    }


    private void executeUpdateSql(String fileName) {
        try {
            connection.prepareCall(FileUtil.readFile(fileName)).execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package fish.eyebrow.blog.backend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fish.eyebrow.blog.backend.guice.BlogBackendModule;
import fish.eyebrow.blog.backend.model.Post;
import fish.eyebrow.blog.backend.util.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
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


    @AfterEach
    void tearDown() {
        executeUpdateSql("sql/drop_posts_table.sql");
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


    @Test
    void shouldReturnOneOfTwoPostsWhenOneIsOutOfRange() {
        executeUpdateSql("sql/insert_two_posts.sql");

        List<Post> actual = blogDao.getPostsInRange(2, Long.MAX_VALUE);
        Post post = actual.get(0);

        assertEquals(1, actual.size());

        assertEquals(2, post.getId());
        assertEquals("another cool post", post.getTitle());
    }


    @Test
    void shouldCreateNewPostIntoEmptyPostTable() throws SQLException {
        Post post = new Post();
        post.setTitle("creative title for a post");

        boolean inserted = blogDao.insertPost(post);
        ResultSet result = executeQuerySql("sql/select_all_posts.sql");

        assertTrue(inserted);
        assertNotNull(result);

        result.next();
        assertEquals(1, result.getLong("id"));
        assertEquals("creative title for a post", result.getString("title"));
    }


    private void executeUpdateSql(String fileName) {
        try {
            connection.prepareCall(FileUtil.readFile(fileName)).execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private ResultSet executeQuerySql(String fileName) {
        try {
            return connection.prepareCall(FileUtil.readFile(fileName)).executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

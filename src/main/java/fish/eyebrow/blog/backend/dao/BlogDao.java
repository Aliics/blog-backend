package fish.eyebrow.blog.backend.dao;

import com.google.inject.Inject;
import fish.eyebrow.blog.backend.model.Post;
import fish.eyebrow.blog.backend.util.FileUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlogDao {

    private static String SELECT_POSTS_WITH_RANGE = FileUtil
            .readFile("sql/select_posts_with_range.sql");

    private Connection connection;


    @Inject
    public BlogDao(Connection connection) {
        this.connection = connection;
    }


    public List<Post> getPostsInRange(long start, long end) {
        try {
            PreparedStatement selectStatement = connection.prepareStatement(SELECT_POSTS_WITH_RANGE);
            selectStatement.setLong(1, start);
            selectStatement.setLong(2, end);

            ResultSet result = selectStatement.executeQuery();

            List<Post> posts = new ArrayList<>();
            while (result.next()) {
                Post post = Post.of(result);
                if (Objects.nonNull(post)) {
                    posts.add(post);
                }
            }

            return posts;
        }
        catch (SQLException e) {
            return List.of();
        }
    }
}

package fish.eyebrow.blog.backend.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Post {

    private static Logger LOGGER = LoggerFactory.getLogger(Post.class);

    private long id;

    private String title;


    public static Post of(ResultSet result) {
        Post post = new Post();

        try {
            post.setId(result.getLong("id"));
            post.setTitle(result.getString("title"));

            return post;
        }
        catch (SQLException e) {
            LOGGER.error("Could not map result to Post");
            return null;
        }
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }
}

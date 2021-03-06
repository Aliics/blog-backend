package fish.eyebrow.blog.backend.model;

import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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


    public static Post of(JsonObject jsonObject) {
        Post post = new Post();
        post.setTitle(jsonObject.getString("title"));

        if (Objects.isNull(post.title)) {
            return null;
        }

        return post;
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

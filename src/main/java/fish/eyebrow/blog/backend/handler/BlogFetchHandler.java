package fish.eyebrow.blog.backend.handler;

import com.google.inject.Inject;
import fish.eyebrow.blog.backend.dao.BlogDao;
import fish.eyebrow.blog.backend.model.Post;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class BlogFetchHandler implements Handler<RoutingContext> {

    private static Logger LOGGER = LoggerFactory.getLogger(BlogFetchHandler.class);

    private BlogDao blogDao;


    @Inject
    public BlogFetchHandler(BlogDao blogDao) {
        this.blogDao = blogDao;
    }


    @Override
    public void handle(RoutingContext event) {
        MultiMap params = event.queryParams();

        try {
            long start = parseIntNonNullOrElse(params.get("start"), Long.MIN_VALUE);
            long end = parseIntNonNullOrElse(params.get("end"), Long.MAX_VALUE);

            if (end < start) {
                event.response().setStatusCode(400).end();
                return;
            }

            List<Post> posts = blogDao.getPostsInRange(start, end);
            JsonObject postsJson = new JsonObject().put("posts", posts);

            event.response().end(postsJson.encode());
        }
        catch (NumberFormatException e) {
            LOGGER.error("Could not parse start and end params as integers");
            event.response().setStatusCode(400).end();
        }
    }


    private long parseIntNonNullOrElse(String param, long other) {
        return Objects.nonNull(param) ? Long.parseLong(param) : other;
    }
}

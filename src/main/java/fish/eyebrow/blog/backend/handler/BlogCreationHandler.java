package fish.eyebrow.blog.backend.handler;

import com.google.inject.Inject;
import fish.eyebrow.blog.backend.dao.BlogDao;
import fish.eyebrow.blog.backend.model.Post;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class BlogCreationHandler implements Handler<RoutingContext> {

    private BlogDao blogDao;


    @Inject
    public BlogCreationHandler(BlogDao blogDao) {
        this.blogDao = blogDao;
    }


    @Override
    public void handle(RoutingContext event) {
        Post post = Post.of(event.getBodyAsJson());

        if (!blogDao.insertPost(post)) {
            event.response().setStatusCode(400).end();
            return;
        }

        event.response().setStatusCode(200).end();
    }
}

package fish.eyebrow.blog.backend.verticle;

import com.google.inject.Inject;
import fish.eyebrow.blog.backend.handler.BlogCreationHandler;
import fish.eyebrow.blog.backend.handler.BlogFetchHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BlogBackendVerticle extends AbstractVerticle {

    private BlogFetchHandler blogFetchHandler;

    private BlogCreationHandler blogCreationHandler;


    @Inject
    public BlogBackendVerticle(BlogFetchHandler blogFetchHandler, BlogCreationHandler blogCreationHandler) {
        this.blogFetchHandler = blogFetchHandler;
        this.blogCreationHandler = blogCreationHandler;
    }


    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/blog").handler(blogFetchHandler);
        router
                .post("/blog")
                .handler(BodyHandler.create())
                .handler(blogCreationHandler);

        vertx
                .createHttpServer()
                .requestHandler(router)
                .listen(config().getInteger("port"));
    }
}

package fish.eyebrow.blog.backend.verticle;

import com.google.inject.Inject;
import fish.eyebrow.blog.backend.handler.BlogFetchHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class BlogBackendVerticle extends AbstractVerticle {

    private BlogFetchHandler blogFetchHandler;


    @Inject
    public BlogBackendVerticle(BlogFetchHandler blogFetchHandler) {
        this.blogFetchHandler = blogFetchHandler;
    }


    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.get("/blog").handler(blogFetchHandler);

        vertx
                .createHttpServer()
                .requestHandler(router)
                .listen(config().getInteger("port"));
    }
}

package fish.eyebrow.blog.backend.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class BlogFetchHandler implements Handler<RoutingContext> {

    @Override
    public void handle(final RoutingContext event) {
        event.response().end("{}");
    }
}

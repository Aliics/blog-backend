package fish.eyebrow.blog.backend.handler;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class BlogFetchHandler implements Handler<RoutingContext> {

    private static Logger LOGGER = LoggerFactory.getLogger(BlogFetchHandler.class);


    @Override
    public void handle(RoutingContext event) {
        MultiMap params = event.queryParams();

        try {
            int start = parseIntNonNullOrElse(params.get("start"), Integer.MIN_VALUE);
            int end = parseIntNonNullOrElse(params.get("end"), Integer.MAX_VALUE);

            if (end < start) {
                event.response().setStatusCode(400).end();
                return;
            }

            event.response().end("{}");
        }
        catch (NumberFormatException e) {
            LOGGER.error("Could not parse start and end params as integers");
            event.response().setStatusCode(400).end();
        }
    }


    private int parseIntNonNullOrElse(String param, int other) {
        return Objects.nonNull(param) ? Integer.parseInt(param) : other;
    }
}

package fish.eyebrow.blog.backend.verticle;

import com.google.inject.Inject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class BlogBackendVerticle extends AbstractVerticle {

    private JsonObject configuration;


    @Inject
    public BlogBackendVerticle(final JsonObject configuration) {
        this.configuration = configuration;
    }
}

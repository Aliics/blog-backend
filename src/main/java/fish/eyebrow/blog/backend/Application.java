package fish.eyebrow.blog.backend;

import static io.vertx.core.Vertx.vertx;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fish.eyebrow.blog.backend.guice.BlogBackendModule;
import fish.eyebrow.blog.backend.verticle.BlogBackendVerticle;
import io.vertx.core.Verticle;

public class Application {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BlogBackendModule());
        Verticle verticle = injector.getInstance(BlogBackendVerticle.class);
        vertx().deployVerticle(verticle);
    }
}

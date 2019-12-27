package fish.eyebrow.blog.backend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fish.eyebrow.blog.backend.guice.BlogBackendModule;
import io.vertx.core.Vertx;

public class Application {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BlogBackendModule());
        injector.getInstance(Vertx.class);
    }
}

package fish.eyebrow.blog.backend.guice;

import static io.vertx.core.Vertx.vertx;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import fish.eyebrow.blog.backend.handler.BlogFetchHandler;
import fish.eyebrow.blog.backend.verticle.BlogBackendVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BlogBackendModule extends AbstractModule {

    private static Logger LOGGER = LoggerFactory.getLogger(BlogBackendModule.class);


    @Provides
    @Singleton
    JsonObject createConfiguration() {
        Path path = Paths.get(ClassLoader.getSystemResource("configuration.json").getPath());
        try {
            return new JsonObject(Files.readString(path));
        }
        catch (Exception e) {
            LOGGER.error("Could not create configuration from configuration.json");
            return null;
        }
    }


    @Inject
    @Provides
    @Singleton
    Vertx deployVertxVerticles(
            BlogBackendVerticle blogBackendVerticle,
            JsonObject configuration
    ) {
        Vertx vertx = vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(configuration);

        vertx.deployVerticle(blogBackendVerticle, deploymentOptions);

        return vertx;
    }
}

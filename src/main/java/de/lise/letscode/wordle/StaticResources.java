package de.lise.letscode.wordle;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class StaticResources {
    public void init(@Observes Router router) {
        router.get("/*").handler(StaticHandler.create("src/main/resources/public"));
    }
}

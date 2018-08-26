package io.luxcore;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.luxcore.di.MainModule;
import io.luxcore.events.ShutdownEvent;
import io.luxcore.fx.LoginController;
import io.luxcore.fx.StatusCheckTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Main application class
 * Starting with password input
 */
public class LCApplication extends Application {

    private static final Logger logger = LoggerFactory.getLogger(LCApplication.class);

    @Inject
    private StatusCheckTask statusCheck;
    @Inject
    private EventBus eventBus;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new MainModule());
        injector.injectMembers(this);
        eventBus.register(this);
        URL resource = LoginController.class.getResource("login.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        logger.info("Loading fxml {}", resource);


        loader.setControllerFactory(type -> {
            Object instance = injector.getInstance(type);
            eventBus.register(instance);
            return instance;
        });

        Parent root = loader.load();
        statusCheck.start();
        Scene scene = new Scene(root);

        primaryStage.setTitle("LuxGateJ");
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> {
            //Object controller = loader.getController();
            eventBus.post(new ShutdownEvent());
            Platform.exit();
        });
        primaryStage.show();
    }

    @Subscribe
    public void logDeadEvent(DeadEvent event) {
        logger.warn("Caught dead event {}", event);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
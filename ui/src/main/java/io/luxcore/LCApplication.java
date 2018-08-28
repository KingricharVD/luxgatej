package io.luxcore;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.luxcore.di.MainModule;
import io.luxcore.events.ShutdownEvent;
import io.luxcore.events.TermsOfUseAgreedEvent;
import io.luxcore.fx.LoginController;
import io.luxcore.fx.StatusCheckTask;
import io.luxcore.fx.TermsOfUseController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private Scene scene;
    private ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
    private Future<Parent> loginViewLoadFuture;
    private Parent termsOfUseView;
    private FadeTransition fadeIn;
    private FadeTransition fadeOut;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new MainModule());
        injector.injectMembers(this);
        eventBus.register(this);

        URL tosResource = TermsOfUseController.class.getResource("terms-of-use.fxml");
        FXMLLoader loader = new FXMLLoader(tosResource);

        loader.setControllerFactory(type -> {
            Object instance = injector.getInstance(type);
            eventBus.register(instance);
            return instance;
        });

        termsOfUseView = loader.load();
        loadFonts();
        scene = new Scene(termsOfUseView);
        scene.setFill(Color.rgb(50,50,50, 1));
        primaryStage.setTitle("LuxGateJ");
        primaryStage.setScene(scene);
        primaryStage.setOnHidden(e -> {
            eventBus.post(new ShutdownEvent());
            threadExecutor.shutdown();
            Platform.exit();
        });
        primaryStage.show();

        // loading next view in background while user "reads" terms of use
        loginViewLoadFuture = threadExecutor.submit(() -> {
            createTransitions();
            URL loginResource = LoginController.class.getResource("login.fxml");
            FXMLLoader asyncLoader = new FXMLLoader(loginResource);
            asyncLoader.setControllerFactory(type -> {
                Object instance = injector.getInstance(type);
                eventBus.register(instance);
                return instance;
            });
            Parent load = asyncLoader.load();
            logger.debug("Async load of login page complete");
            return load;
        });
    }

    private void loadFonts() {
        Font.loadFont(
                getClass().getResource("/fonts/Roboto-Bold.ttf").toExternalForm(), -1);
        Font.loadFont(
                getClass().getResource("/fonts/Roboto-Regular.ttf").toExternalForm(), -1);
        Font.loadFont(
                getClass().getResource("/fonts/Roboto-Medium.ttf").toExternalForm(), -1);
        Font.loadFont(
                getClass().getResource("/fonts/Roboto-Light.ttf").toExternalForm(), -1);
    }

    private void createTransitions() {

        fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.millis(50));
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);

        fadeOut = new FadeTransition();
        fadeOut.setDuration(Duration.millis(50));
        fadeOut.setFromValue(0);
        fadeOut.setToValue(1);
    }

    @Subscribe
    public void logDeadEvent(DeadEvent event) {
        logger.warn("Caught dead event {}", event);
    }

    @Subscribe
    public void onTermsOfUseAgreed(TermsOfUseAgreedEvent event) throws ExecutionException, InterruptedException {
        fadeIn.setNode(termsOfUseView);
        Parent parent = loginViewLoadFuture.get();
        fadeOut.setNode(parent);
        fadeIn.setOnFinished(a -> {
            scene.setRoot(parent);
            fadeOut.play();
        });
        fadeIn.play();

        statusCheck.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
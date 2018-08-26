package io.luxcore.fx;

import com.adr.fonticon.FontAwesome;
import com.adr.fonticon.Icon;
import com.adr.fonticon.IconBuilder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import io.luxcore.LuxgateInstance;
import io.luxcore.LuxgateService;
import io.luxcore.Shutdown;
import io.luxcore.dto.rs.StatusResponse;
import io.luxcore.events.LuxgateStatusEvent;
import io.luxcore.events.ShutdownEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Login dialog controller.
 * Requesting password (actually passphrase) for future requests.
 * It trying to check local daemon accessibility at background.
 */
public class LoginController implements Initializable {

    final private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML public PasswordField passwordField;
    @FXML public Text statusMark;
    @FXML public AnchorPane anchorPane;
    @FXML public Group iconGroup;

    @Inject
    private LuxgateInstance instance;
    @Inject
    private LuxgateService service;
    @Inject
    private EventBus eventBus;

    private Icon statusOnlineIcon = IconBuilder
            .create(FontAwesome.FA_CIRCLE)
            .color(Color.web("#64DD17"))
            .build();

    private Icon statusFailedIcon = IconBuilder
            .create(FontAwesome.FA_TIMES_CIRCLE)
            .color(Color.web("#E65100"))
            .build();

    private Icon statusUnknownIcon = IconBuilder
            .create(FontAwesome.FA_DOT_CIRCLE_O)
            .color(Color.web("#FFD600"))
            .build();

    public void initialize(URL location, ResourceBundle resources) {
        setStatusIcon(statusUnknownIcon);
    }

    public void onPasswordEnter(ActionEvent event) {

    }

    @Subscribe
    public void onStatusChange(LuxgateStatusEvent event) {
        logger.info("Received event {}", event);
        Platform.runLater(() -> {
            setStatusIcon(event.isOnline() ? statusOnlineIcon : statusFailedIcon);
        });
    }

    private void setStatusIcon(Icon icon) {
        iconGroup.getChildren().replaceAll(node -> icon);
    }

    @Subscribe
    public void shutdown(ShutdownEvent event) {
        logger.info("Shutting down");
        //scheduler.shutdown();
    }

}

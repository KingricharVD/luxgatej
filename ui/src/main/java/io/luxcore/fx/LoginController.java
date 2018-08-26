package io.luxcore.fx;

import com.adr.fonticon.FontAwesome;
import com.adr.fonticon.Icon;
import com.adr.fonticon.IconBuilder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.hash.Hashing;
import com.google.inject.Inject;
import io.luxcore.LuxgateInstance;
import io.luxcore.LuxgateService;
import io.luxcore.dto.rs.Asset;
import io.luxcore.events.LuxgateStatusEvent;
import io.luxcore.events.ShutdownEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML public ImageView logo;
    @FXML public Label invalidTryLabel;

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

    private Bloom bloomEffect;

    public void initialize(URL location, ResourceBundle resources) {
        setStatusIcon(statusUnknownIcon);
        bloomEffect = new Bloom();
        bloomEffect.setThreshold(0.1);
    }

    public void onPasswordEnter(ActionEvent event) {

        String password = passwordField.textProperty().get();
        logger.info("Passphrase: {}", password);
        instance.setPassword(password);
        logger.info("Password is valid");
        logo.setEffect(bloomEffect);
        invalidTryLabel.setVisible(false);

        // TODO there should be luxgate executable instead of echo

        try {
            String sha256hex = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();

            Process process = Runtime.getRuntime()
                    .exec(new String[] { "/bin/echo", "password.(" + sha256hex + ")"});
            InputStream inputStream = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String readLine;
            while (((readLine = br.readLine()) != null)) {
                logger.info("DAEMON>{}", readLine);
                if (readLine.startsWith("password.")) {
                    int start = readLine.indexOf('(');
                    int end = readLine.indexOf(')');
                    if (start > 0 && end > 0) {
                        String passwd = readLine.substring(start + 1, end);
                        logger.info("Password: \"{}\"", passwd);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("WIP");
                        alert.setHeaderText("Password from luxgate daemon (fake)");
                        alert.setContentText(passwd);
                        alert.showAndWait();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error launching luxgate", e);
        }
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

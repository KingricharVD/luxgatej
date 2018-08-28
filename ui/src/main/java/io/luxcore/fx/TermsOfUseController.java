package io.luxcore.fx;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import io.luxcore.events.TermsOfUseAgreedEvent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class TermsOfUseController implements Initializable {
    public WebView webView;
    public CheckBox agree;
    public Button next;

    @Inject
    private EventBus eventBus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webView.getEngine()
                .setUserStyleSheetLocation(getClass().getResource("/terms-of-use/style.css").toExternalForm());

        // no other document locales so use only US for now
        webView.getEngine()
                .load(getClass().getResource("/terms-of-use/en-US.html").toExternalForm());
        next.disableProperty().bind(agree.selectedProperty().not());
    }

    public void termsAgreed(ActionEvent event) {
        eventBus.post(new TermsOfUseAgreedEvent());
    }
}

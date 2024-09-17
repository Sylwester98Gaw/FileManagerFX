package script.helpers;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public class ShowAlerts {
    public void Alert(Alert.AlertType alertType, String Header, String Content, String Title) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.setTitle(Title);
        alert.showAndWait();
    }

    public void Alert(Alert.AlertType alertType, String Header, String Content, String Title, ImageView imageView) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.setTitle(Title);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }
}

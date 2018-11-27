
package threadingjava;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private ImageView image;

    @FXML
    private void handleButtonSync(ActionEvent event) {
        NewtonSolver ns = new NewtonSolver();
        ns.startTimeMeasurement();
        ns.solve();
        ns.stopTimeMeasurement();

        image.setImage(ns.getImageFX());
        label.setText(Double.toString(ns.getDuration()));
    }

    @FXML
    private void handleButtonCompletableFuture(ActionEvent event) {
        // ...
        // TODO: solve problem using Thread class (thread for each image row)
        // TODO: solve problem using Thread class (thread for each processor)
        // TODO: solve problem using thread pool
        // TODO: solve problem using completable futures (try different ways to do it)
        
        // IMPORTANT: be aware of synchronization issues and blocking of UI thread
    }

    @FXML
    private void handleButtonCompletableFutureRows(ActionEvent event) {
        // ...
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}

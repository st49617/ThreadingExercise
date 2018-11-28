package threadingjava;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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

        Thread thread = new Thread(() -> {
            NewtonSolver ns = new NewtonSolver();

            ns.startTimeMeasurement();

            List<Thread> threads = new ArrayList<>();

            int threadNumber = 4;

            for (int i = 0; i < threadNumber; i++) {
                int k = i;
                Thread t = new Thread(() -> {
                    ns.solvePart(k, threadNumber);
                });
                t.start();
                threads.add(t);
            }

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            ns.stopTimeMeasurement();

            Platform.runLater(() -> {
                image.setImage(ns.getImageFX());
                label.setText(Double.toString(ns.getDuration()));
            });
        });

        thread.start();

    }

    @FXML
    private void handleButtonCompletableFutureRows(ActionEvent event) {
        NewtonSolver ns = new NewtonSolver();
        ns.startTimeMeasurement();

        CompletableFuture.runAsync(() -> {
            ns.solve();
        }).thenRunAsync(() -> {
            ns.stopTimeMeasurement();
            Platform.runLater(() -> {
                image.setImage(ns.getImageFX());
                label.setText(Double.toString(ns.getDuration()));
            });
        });

//        try {
//            CompletableFuture<void> completableFuture = ns.asyncSolve();
////            String result = completableFuture.get();
////            System.out.println(result);
//            CompletableFuture<Void> future = completableFuture.thenAccept(s -> {
//                Platform.runLater(() -> {
//                    image.setImage(ns.getImageFX());
//                    label.setText(Double.toString(ns.getDuration()));
//                });
//            });
//        } catch (InterruptedException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //CompletableFuture<String> future = new CompletableFuture<>();
//        Executors.newCachedThreadPool().submit(() -> {
//            ns.solve();
//            ns.stopTimeMeasurement();
//            Platform.runLater(() -> {
//                image.setImage(ns.getImageFX());
//                label.setText(Double.toString(ns.getDuration()));
//            });
//            //future.complete("Hello");
//            return null;
//        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}

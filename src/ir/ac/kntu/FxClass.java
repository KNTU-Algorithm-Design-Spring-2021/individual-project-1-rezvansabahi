package ir.ac.kntu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FxClass extends Application {

    BoundingBoxView boxView = new BoundingBoxView();

    @Override
    public void start(Stage stage) {

        BorderPane pane = new BorderPane();
        TextField tf = new TextField();
        tf.setPrefColumnCount(3);
        tf.setAlignment(Pos.BASELINE_RIGHT);
        Button numberV = new Button("number of vertices");
        numberV.setOnAction(e -> {
            int key = Integer.parseInt(tf.getText());
            boxView.display(key);
        });


        HBox hBox = new HBox(4);
        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(new Label("Enter number: "), tf, numberV);
        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);

        pane.setStyle("-fx-background-color: #000000;");

        pane.setCenter(boxView);

        hBox.setStyle("-fx-background-color: #FFFFFF;");

        //Creating a scene object
        Scene scene = new Scene(pane, 600, 300);

        //Adding scene to the stage
        stage.setScene(scene);
        stage.setTitle("Minimal Bounding Box");

        //Displaying the contents of the stage
        stage.show();

    }

    public static void showResult (String[] args) {
        launch(args);
    }

}

package AlertBoxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Antonivar on 2016-11-30.
 */
public class ConfirmBox {

    private static boolean answer;
    private static Button yesButton;
    private static Button noButton;


    public static boolean display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        window.setMinWidth(250);
        window.setMinHeight(150);

        Label label1 = new Label();
        label1.setText(message);

        //Create two buttons and set actions
        yesButton = new Button("Yes");
        noButton = new Button("No");

        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label1,yesButton,noButton);


        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        //scene.getStylesheets().add("ken.css");
        window.setScene(scene);
        window.showAndWait();


        return answer;

    }
}
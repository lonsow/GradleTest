package GUI;

import Server.ObjectPasser;
import Server.Rating;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerShopRateBuyer implements Initializable {
    public TextField bewertungsfeld;
    public Slider zahlenrating;
    public Button bewertenbutton;
    public Label BuyerLabel;
    public Label Productname;
    public Button AbbrechenB;
    ObjectPasser o;

    public void bewerten(ActionEvent actionEvent) throws IOException {
        System.out.println(Double.toString(zahlenrating.getValue()));
        if(zahlenrating.getValue()!=0) {
            o.getI().getBuyingUser().getUserRatings().add(new Rating((int) zahlenrating.getValue(), bewertungsfeld.getText()));
            switchLayout("Shop_angeboteneProdukte_Layout.fxml", bewertenbutton);
        } else System.out.println("bitte geben sie eine Bewertung ab");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        o=ObjectPasser.getInstance();
        BuyerLabel.setText(o.getI().getBuyingUser().getUsername());
        Productname.setText(o.getI().getItemname());
    }
    public  void switchLayout(String layoutName, Button b ) throws IOException {
        Stage s = new Stage();
        Stage cs =(Stage)b.getScene().getWindow();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource(layoutName));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
        cs.close();

    }

    public void abbrechen(ActionEvent actionEvent) throws IOException {
        switchLayout("Shop_angeboteneProdukte_Layout.fxml", bewertenbutton);
    }
}

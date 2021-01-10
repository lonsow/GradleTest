package GUI;

import Client.SEPClient;
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

public class ControllerBestellungenStornieren implements Initializable {
    public Button JaButton;
    public Button AbbrechenButton;
    public Label Produktname;
    public Label Produktpreis;
    public TextField bewertungsfeld;
    public Slider zahlenrating;
    public Button bewertenbutton;
    ObjectPasser o;
    SEPClient sepc;
    public void Bestaetigen(ActionEvent actionEvent) throws IOException {
        sepc=SEPClient.getInstance();
        sepc.currentsession.getWallet().setWalletmoney(sepc.currentsession.getWallet().getWalletmoney()+o.getI().getItemprize());
        System.out.println("ihr geld ist:"+ Double.toString(sepc.currentsession.getWallet().getWalletmoney()));

        for (int i=0;i<o.itemdummy.length;i++){
            if(o.itemdummy[i]==o.getI()) {
                o.itemdummy[i] = null;
            }
        }
        switchLayout("Shop_Bestellungen_Layout.fxml", AbbrechenButton);
    }

    public void Abbrechen(ActionEvent actionEvent) throws IOException {
        switchLayout("Shop_Bestellungen_Layout.fxml", AbbrechenButton);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        o= ObjectPasser.getInstance();
        Produktname.setText(o.getI().getItemname());
        Produktpreis.setText(Double.toString(o.getI().getItemprize()));
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

    public void bewerten(ActionEvent actionEvent) {
        System.out.println(Double.toString(zahlenrating.getValue()));
        o.getI().getOfferingUser().getUserRatings().add(new Rating((int)zahlenrating.getValue(),bewertungsfeld.getText()));
    }
}

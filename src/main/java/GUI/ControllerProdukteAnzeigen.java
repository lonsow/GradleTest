package GUI;

import Client.SEPClient;
import Server.ObjectPasser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerProdukteAnzeigen implements Initializable{
    public Label Tx_Produktname;
    public TextField Tx_PreisAlt;
    public TextField Tx_PreisNeu;
    public TextField Tx_Prozente;
    public ListView Li_Produktname;
    public Button Bt_Kaufen;
    public Button AbbrechenButton;
    public Button Bt_uebernehmen;
    public SEPClient sepc;
    ObjectPasser o;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        o = ObjectPasser.getInstance();
        sepc = SEPClient.getInstance();
        Tx_Produktname.setText(o.getI().getItemname());
        Tx_PreisAlt.setText(Double.toString(o.getI().getItemprize()));
        Tx_PreisAlt.setDisable(true);
        Tx_Prozente.setDisable(true);
        Li_Produktname.setDisable(true);

        if(sepc.currentsession.isBusiness()==true){
            Bt_uebernehmen.setVisible(true);
            Bt_Kaufen.setVisible(false);
            Tx_PreisNeu.setDisable(false);
            Tx_PreisNeu.setVisible(true);
            Tx_Prozente.setVisible(true);
        }

        if(sepc.currentsession.isBusiness()==false){
            Bt_uebernehmen.setVisible(false);
            Bt_Kaufen.setVisible(true);
            Tx_PreisNeu.setDisable(true);
            if(Tx_PreisNeu.getText()==null || Tx_PreisNeu.getText()==""){
                Tx_PreisNeu.setVisible(false);
                Tx_Prozente.setVisible(false);
            }
        }
    }
    public void uebernehmen(ActionEvent actionEvent)throws IOException{
        int preisAlt;
        int preisNeu;
        double prozente;

        if(Tx_PreisNeu.getText()==null || Tx_PreisNeu.getText()=="" || Tx_PreisNeu.getText().equals(Tx_PreisAlt.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);                                                      //https://code.makery.ch/blog/javafx-dialogs-official/
            alert.setTitle("Keine Änderungen");
            alert.setHeaderText(null);
            alert.setContentText("Es wurden keine Änderungen vorgenommen, geben Sie einen neuen Preis ein.");
            alert.showAndWait();
        }
        else{
            preisAlt = Integer.parseInt(Tx_PreisAlt.getText());
            preisNeu = Integer.parseInt(Tx_PreisNeu.getText());
            if(preisNeu < preisAlt){
                prozente = (preisAlt-preisNeu)/preisAlt*100;
                Tx_Prozente.setText(Double.toString(prozente));
                Tx_Prozente.setVisible(true);
                Tx_PreisNeu.setDisable(true);
            }
            else{
                Tx_PreisAlt.setText(Tx_PreisNeu.getText());
            }
        }
    }

    public void kaufen(ActionEvent actionEvent) {
    }


    public void Abbrechen(ActionEvent actionEvent) throws IOException {
        switchLayout("Shop_Suche_Layout.fxml",Bt_Kaufen);
        Stage cs =(Stage)AbbrechenButton.getScene().getWindow();
        cs.close();
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
}

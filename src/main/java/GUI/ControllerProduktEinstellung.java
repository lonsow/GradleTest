package GUI;

import Client.SEPClient;
import Server.Item;
import Server.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerProduktEinstellung implements Initializable {
    public Button HomeButton;
    public Button AbbrechenButton;
    public Button AnbietenButton;
    public TextField GNameField;
    public TextField AnzahlField;
    public TextField KategorieField;
    public TextField PreisField;
    public TextField PBeschreibungField;
    public TextField PNameField;
    public Button BellungsButton;
    public Button ChatButton;
    @FXML
    private Button id_suche;
    @FXML
    private Button id_profil;
    @FXML
    private Button id_wallet;

    SEPClient sepc;

    User user ;

    Alert a = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sepc = SEPClient.getInstance();
        user = (User) sepc.currentsession;
        if (user.isBusiness() == true){
            id_wallet.setDisable(true);
        }
        // Ueberpruefung auf GK-Konto
        if (user.isBusiness() == true) {
            AnbietenButton.setDisable(false);
            GNameField.setText(user.getBusinessname());
        } else {
            a.setContentText("Sie sind kein Gewerbekunde. Bitte zurückkehren!");
            a.showAndWait();
        }
    }
    @FXML
    public void searchButtonHandleAction(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Suche_Layout.fxml", this.id_suche);
    }
    @FXML
    public void profilButtonHandleAction(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Profil_Layout.fxml",this.id_profil );
    }
    @FXML
    public void walletButtonHandleAction(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Wallet_Layout.fxml", this.id_wallet);
    }

    public void HomeClick(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Layout.fxml", this.HomeButton);
    }

   /* public void showMessage(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Test message box");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();

    }*/

    public  void switchLayout(String layoutName, Button b ) throws IOException{
        Stage s = new Stage();
        Stage cs =(Stage)b.getScene().getWindow();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource(layoutName));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
        cs.close();

    }


    public void AbbrechenClick(ActionEvent actionEvent) throws IOException {
        // Zur Suche zurückkehren
        this.switchLayout("Shop_Suche_Layout.fxml", this.id_suche);
    }

    public void AnbietenClick(ActionEvent actionEvent) {
        sepc = SEPClient.getInstance();
        String name = PNameField.getText();
        String beschreibung = PBeschreibungField.getText();
        double preis = Double.parseDouble(PreisField.getText());
        String kategorie = KategorieField.getText();
        int anzahl = Integer.parseInt(AnzahlField.getText());
        int id = user.getId();
        Item newProduct = new Item(name,beschreibung, preis, user , kategorie, anzahl); //Erstellen des Items
        sepc.requestObjectOperation(1, newProduct, null ,null,null); //An Server senden
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Produkt erfolgreich eingepflegt!");
        a.showAndWait();
    }

    public void Bestellungslink(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Bestellungen_Layout.fxml", this.BellungsButton);
    }

    public void ChatLink(ActionEvent actionEvent) {
    }
}

package GUI;

import Client.SEPClient;
import Server.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWallet implements Initializable {

    public Button HomeButton;
    public Button WalletGuthabenAufladen;
    public TextField KontostandField;
    public Button BellungsButton;
    public Button ChatButton;
    @FXML
    private Button id_suche;
    @FXML
    private Button id_profil;
    @FXML
    private Button id_wallet;

    SEPClient sepc;
    User u;
    
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

    public void WalletGuthabenLadenButton(ActionEvent actionEvent) throws IOException {
        //Mock kunde bis datenbank anbindung
        Stage s = new Stage();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Wallet_Aufladen.fxml"));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sepc = SEPClient.getInstance();
        u = (User) sepc.currentsession;
        KontostandField.setText(Double.toString(u.getWallet().getWalletmoney()));
    }

    public void Bestellungslink(ActionEvent actionEvent) {
    }

    public void ChatLink(ActionEvent actionEvent) {
    }
}

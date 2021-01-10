package GUI;

import Client.SEPClient;
import Server.User;
import Server.Wallet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WalletAufladen {
    public Button AbbrechenButton;
    public Button AufladenButton;
    public TextField BetragFeld;
    SEPClient sepc;
    User u;
    Alert a = new Alert (Alert.AlertType.INFORMATION);

    public void Abbrechen(ActionEvent actionEvent) {
        Stage cs =(Stage)AbbrechenButton.getScene().getWindow();
        cs.close();
    }

    public void Aufladen(ActionEvent actionEvent) {
        Double aufladung = Double.parseDouble(BetragFeld.getText());
        sepc = SEPClient.getInstance();
        u = (User) sepc.currentsession;
        Wallet w = u.getWallet();
        Double kontostand = w.getWalletmoney();
        kontostand += aufladung;
        w.setWalletmoney(kontostand);
        u.setWallet(w);
        a.setContentText("Aufladung erfolgreich!");
        a.show();
    }

}

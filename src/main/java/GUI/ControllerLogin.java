package GUI;

import Client.SEPClient;
import Server.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    public Button regButton;
    public Button Einloggen;
    public PasswordField Passwort;
    public TextField Username;
    SEPClient sepc;
    Alert error = new Alert(Alert.AlertType.ERROR);

    User user = new User();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    //Findet Heraus, ob der Nutzername Valide ist
    public void EinloggenButton(ActionEvent actionEvent) throws IOException {

        sepc = SEPClient.getInstance();

        //Hol den Nutzernamen aus dem TextFeld und gleiche mit der DB am

        /**sepc.requestfetch(user, Username.getText());

        //Der User ist auf den gesuchten Server gestellt
        try{
            if(sepc.getServerResult() != null)
            {
                user = (User) sepc.getServerResult();
                if(user.getUserpasswort() == Passwort.getText())
                {
                    sepc.currentsession = user;
                    Stage s = new Stage();
                    Stage cs =(Stage)Einloggen.getScene().getWindow();
                    Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_Layout.fxml"));
                    Scene sc = new Scene(searchLayout);
                    s.setScene(sc);
                    s.show();
                    cs.close();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }**/


        // *** EINLOGGABLAUF ***
        // Username abfangen, als Kundenobjekt mit alles null außer username erzeugen und an Server senden

        // Object zurückerhalten und als Kunde typecasten

        // Kundenobjekt.Passwort mit eingegebenem Passwort vergleichen --> Login oder Error melden


        sepc.requestfetch(user, Username.getText());

        if(sepc.getServerResult()!=null) {
            User testkunde = (User) sepc.getServerResult();

            //Testen der eingegebenen Daten
            if ( testkunde.getUserpasswort().equals(Passwort.getText())) {
                // ***CLIENT SETZEN***
                // Currentsession auf das Kundenobjekt setzen, sodass der Client eindeutig ist
                sepc.currentsession = testkunde;

                //Bei erfolgreichem Login Weiterleitung zur Startseite
                error.setAlertType(Alert.AlertType.INFORMATION);
                error.setContentText("Login erfolgreich!");
                error.showAndWait();


                Stage s = new Stage();
                Stage cs = (Stage) Einloggen.getScene().getWindow();
                Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_Layout.fxml"));
                Scene sc = new Scene(searchLayout);
                s.setScene(sc);
                s.show();
                cs.close();
            } else {
                error.setContentText("Falsches Passwort!");
                error.show();
            }
        } else{
            error.setContentText("Es gibt kein Profil zu diesem Namen!");
            error.show();}
    }


    public void regButtonAction(ActionEvent actionEvent) throws IOException {
        //Weiterleitung zur RegSeite!
        Stage s = new Stage();
        Stage cs =(Stage)regButton.getScene().getWindow();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_Registrierung_Layout.fxml"));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
        cs.close();
    }



}

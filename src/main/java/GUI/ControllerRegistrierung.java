package GUI;

import Client.SEPClient;
import Server.Adress;
import Server.User;
import Server.Wallet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class ControllerRegistrierung {

    public CheckBox GKButton;
    public TextField PWField;
    public TextField EmailField;
    public TextField gNameField;
    public TextField uNameField;
    public TextField StrasseField;
    public TextField PLZField;
    public Button okReg;
    public Button abbrReg;
    public ImageView bildview;
    public TextField HausnummerField;
    public TextField StadtField;
    public TextField LandField;
    private Image pb;
    private byte[] image;

    SEPClient sepc;

    User u;

    Alert error = new Alert(Alert.AlertType.ERROR);

    public void GKAction(ActionEvent actionEvent) {
        if (GKButton.isSelected()){
            gNameField.setDisable(false);
        }
        else gNameField.setDisable(true);
    }


    public void okAction(ActionEvent actionEvent) throws IOException {
        sepc =SEPClient.getInstance();
        u = sepc.currentsession;
        //if abfrage für checkbox gk kunde
        if (PWField.getText().isEmpty() || EmailField.getText().isEmpty() || uNameField.getText().isEmpty() || StrasseField.getText().isEmpty() || PLZField.getText().isEmpty() || HausnummerField.getText().isEmpty() || StadtField.getText().isEmpty() || StadtField.getText().isEmpty() || pb == null){
            error.setContentText("Bitte alle Felder ausfüllen!");
            error.show();
        }
        else {
            //Nutzername unique Pruefung
            if (!uNameField.getText().isEmpty()){
                User user = new User (uNameField.getText(),null , null ,false ,null ,null );
                sepc.requestObjectOperation(1 , user, null,null,null);
                if (sepc.getServerResult() != null){
                    error.setContentText("Benutzername schon vergeben! Bitte anderen Nutzernamen wählen!");
                    error.show();
                }
            }

            Adress a = new Adress(LandField.getText(), StadtField.getText(),
                    StrasseField.getText(), Integer.parseInt(HausnummerField.getText()),Integer.parseInt(PLZField.getText()) );

            Wallet w = new Wallet (); //???????
            //Wenn Checkbox ausgewaehlt --> GK eröffnen
            if (GKButton.isSelected()) {
                if (gNameField.getText().isEmpty()) {
                    error.setContentText("Bitte Geschaeftsnamen eingeben!");
                    error.show();
                }
                // *** GK ERSTELLEN UND ALS KUNDENOBJEKT AN SERVER SENDEN ***
                User newGK = new User (uNameField.getText(),null , null ,true , gNameField.getText() ,null );
                sepc.requestObjectOperation (1, newGK, null ,null,null);
                sepc.currentsession = newGK;
            } else //Ansonsten PK eröffnen
            {
                // *** PK ERSTELLEN UND ALS KUNDENOBJEKT AN SERVER SENDEN ***
                User newPK = new User (uNameField.getText(),null , null ,false ,null ,null );
                sepc.requestObjectOperation (1, newPK, null,null,null);
                sepc.currentsession = newPK;
            }
            error.setAlertType(Alert.AlertType.INFORMATION);
            error.setContentText("Registrierung erfolgreich!");
            error.showAndWait();
            //Bei erfolgreicher Registrierung zur Startseite weiterleiten!
            Stage s = new Stage();
            Stage cs = (Stage) okReg.getScene().getWindow();
            Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_Layout.fxml"));
            Scene sc = new Scene(searchLayout);
            s.setScene(sc);
            s.show();
            cs.close();
        }
    }

    public void abbrAction(ActionEvent actionEvent) throws IOException {
        //Zum Login-View zurückkehren!
        Stage s = new Stage();
        Stage cs =(Stage)abbrReg.getScene().getWindow();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_Login.fxml"));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
        cs.close();
    }


    public void bildClick(MouseEvent mouseEvent) throws IOException {
        FileChooser BildExplorer = new FileChooser();
        BildExplorer.setTitle("Bitte Profilbild auswaehlen");
        File f = BildExplorer.showOpenDialog(null);
        image = FileUtils.readFileToByteArray(f); // Bild zu ByteArray
        if (f != null) {
            Image bild = new Image(f.toURI().toString());
            bildview.setImage(bild);
            pb = bildview.getImage();
        }
    }
}

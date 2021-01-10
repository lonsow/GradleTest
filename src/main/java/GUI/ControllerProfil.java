package GUI;

import Client.SEPClient;
import Server.Adress;
import Server.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerProfil implements Initializable {
    public Button Profil_Aenderungen_Button;
    public Button Profil_Abbrechen_Button;

    public TextField BenutzernameField;
    public TextField PasswortField;
    public TextField EmailField;
    public TextField PLZField;
    public TextField GNameField;
    public ImageView Profilbild;
    public TextField NRField;
    public TextField StadtField;
    public TextField LandField;
    public TextField StrasseField;
    public Button BellungsButton;
    public Button ChatButton;
    private Image pb;
    private Image bild;
    private byte[] image;

    public Button HomeButton;
    @FXML
    private Button id_suche;
    @FXML
    private Button id_profil;
    @FXML
    private Button id_wallet;

    SEPClient sepc;
    User user ;

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

    public void ProfilAbbrechenFunktion(ActionEvent actionEvent) throws IOException {
        //  *** ZUR STARTSEITE ZURÜCKKEHREN ***
        this.switchLayout("Shop_Layout.fxml", this.Profil_Abbrechen_Button);
    }

    public void ProfilAenderungenFunktion(ActionEvent actionEvent) {
        sepc = SEPClient.getInstance();
        user = (User) sepc.currentsession;
        // *** EINGEGEBENE TEXTE SPEICHERN ***
        // Kundenobjekt mit Settern ändern
        // aktualisiertes Kundenobjekt zurückschicken
        user.setUsername(BenutzernameField.getText());
        user.setUserpasswort(PasswortField.getText());
        user.setUsermail(EmailField.getText());
        user.setUseradressid(0); //ANSCHRIFTSID??

        Adress a = new Adress(LandField.getText(), StadtField.getText(),
                StrasseField.getText(), Integer.parseInt(NRField.getText()),Integer.parseInt(PLZField.getText()) );

        user.setAdress(a);
        user.setImage(image);
        if (GNameField.getText() != null){
            user.setBusinessname(GNameField.getText());
        }
        sepc.requestObjectOperation (5,user,null,null,null);
    }

    public void ProfilbildClick(MouseEvent mouseEvent) {
        // *** HOCHGELADENES PB SPEICHERN ***
        // Filechooser öffnen und Bild hochladen lassen
        FileChooser BildExplorer = new FileChooser();
        BildExplorer.setTitle("Bitte Profilbild auswählen");
        File f = BildExplorer.showOpenDialog(null);
        if (f != null) {
            Image bild = new Image(f.toURI().toString());
            Profilbild.setImage(bild);
            pb = Profilbild.getImage();
        }
        //image = FileUtils.readFileToByteArray(pb);
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
        user = (User) sepc.currentsession;
        if (user.isBusiness()){
            id_wallet.setDisable(true);
        }
        // *** KUNDENOBJEKT AUSLESEN ***
        // Felder durch Getter ausfüllen lassen
        BenutzernameField.setText(user.getUsername());
        PasswortField.setText(user.getUserpasswort());
        EmailField.setText(user.getUsermail());
        Adress anschrift =user.getAdress();
        StrasseField.setText(anschrift.getStreet());
        PLZField.setText(Integer.toString(anschrift.getPostalcode()));
        NRField.setText(Integer.toString(anschrift.getStreetNumber()));
        StadtField.setText(anschrift.getCity());
        LandField.setText(anschrift.getCountry());
        // ByteArray zu Bilddatei konvertieren
        //byte[] bytes = "frontbackend.com".getBytes(StandardCharsets.UTF_8);             //https://frontbackend.com/java/convert-byte-array-to-file-in-java
        //image = sepc.currentsession.getImage();
        //bild = FileUtils.writeByteArrayToFile(image, bytes);
        //Profilbild.setImage(bild);
        if (user.getBusinessname() != null){
            GNameField.setText(user.getBusinessname());
        } else{
            GNameField.setDisable(true);
        }
    }


    public void Bestellungslink(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Bestellungen_Layout.fxml", this.BellungsButton);
    }

    public void ChatLink(ActionEvent actionEvent) {
    }
}

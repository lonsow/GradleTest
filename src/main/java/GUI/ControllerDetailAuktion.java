package GUI;

import Client.SEPClient;
import Server.Auction;
import Server.ObjectPasser;
import Server.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerDetailAuktion implements Initializable {
    public Button bt_zurueck;
    public Button bt_Bieten;
    public ImageView iv_bild;
    public TextField tf_Name;
    public TextField tf_Beschreibung;
    public TextField tf_Startpreis;
    public TextField tf_Mindestgebot;
    public TextField tf_Versandart;
    public TextField tf_Gebot;
    public TextField tf_endzeitpunkt;

    SEPClient sepc;
    User u;
    ObjectPasser o;
    Auction a;

    Alert error = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sepc = SEPClient.getInstance();
        u = (User) sepc.currentsession;
        o = ObjectPasser.getInstance();
        a = o.getA();
        // ByteArray zu Bilddatei konvertieren
        //byte[] bytes = "frontbackend.com".getBytes(StandardCharsets.UTF_8);             //https://frontbackend.com/java/convert-byte-array-to-file-in-java
        //Byte[] i  = a.getAuctionimage();
        //Image bild = FileUtils.writeByteArrayToFile(image, bytes);
        //iv_bild.setImage(image);
        tf_Name.setText(a.getauctionName());
        tf_Beschreibung.setText(a.getAuctionDescription());
        Double mindestgebot = a.getMinadd()+a.getStartpreis();
        tf_Mindestgebot.setText(Double.toString(mindestgebot));
        tf_Startpreis.setText(Double.toString(a.getStartpreis()));
        tf_Versandart.setText(a.getVersandart());
        //Date zu String formartieren
        Date zeitpunkt = a.getTimeForAuction();
        DateFormat dateFormat = new SimpleDateFormat("DD.MM.YYYY hh:mm"); /**wieso ein Jahr zu viel?**/
        String stringzeit = dateFormat.format(zeitpunkt);
        tf_endzeitpunkt.setText(stringzeit);
    }
    public void Zurueck_click(ActionEvent actionEvent) throws IOException {
        //Zurueck zur Auktionskatalogseite
        Stage s = new Stage();
        Stage cs =(Stage)bt_zurueck.getScene().getWindow();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_SucheAuktionen_Layout.fxml"));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
        cs.close();
    }

    private void aktualisieren() throws IOException{
        Stage s = new Stage();
        Stage cs =(Stage)bt_zurueck.getScene().getWindow();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_DetailAuktion_Layout.fxml"));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
        cs.close();
    }

    public void Bieten_click(ActionEvent actionEvent) throws IOException {
        double gebot = Double.parseDouble(tf_Gebot.getText());
        if (gebot < (a.getStartpreis()+a.getMinadd())){
            error.setAlertType(Alert.AlertType.ERROR);
            error.setContentText("Ihr Gebot ist zu niedrig. Bitte bieten Sie mindestens: "+(a.getStartpreis()+a.getMinadd()) + " SEP-Euro!");
            error.showAndWait();
        }
        else{
            error.setAlertType(Alert.AlertType.INFORMATION);
            error.setHeaderText("Hoechstbietender");
            error.setContentText("Herzlichen Glueckwunsch! Sie sind aktuell Hoechstbietender!");
            error.showAndWait();
            a.setStartpreis(Double.parseDouble(tf_Gebot.getText()));
            a.setUserBidded(u);
            o.setA(a);
            aktualisieren();
        }
    }


}

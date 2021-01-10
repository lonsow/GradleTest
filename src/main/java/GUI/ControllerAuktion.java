package GUI;

import Client.SEPClient;
import Server.Auction;
import Server.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerAuktion implements Initializable {
    public Button bt_zurueck;
    public Button bt_Einstellen;
    public ImageView iv_bild;
    public TextField tf_Name;
    public TextField tf_Beschreibung;
    public TextField tf_Startpreis;
    public TextField tf_Mindestgebot;
    public TextField tf_Versandart;
    public DatePicker dp_Endzeitpunkt;
    public TextField tf_Uhrzeit_Std;
    public TextField tf_Uhrzeit_Min;
    private Image prb;
    private Byte[] image;
    private Date datum;

    SEPClient sepc;
    User u;

    Alert error = new Alert(Alert.AlertType.ERROR);

    //  Aktuelles Datum aus System lesen --> https://stackoverflow.com/questions/31899692/how-to-set-default-value-for-javafx-datepicker-in-fxml
    public static final LocalDate NOW_LOCAL_DATE (){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }

    private void Datum_Rechner (){
        int std = Integer.parseInt(tf_Uhrzeit_Std.getText());
        int min = Integer.parseInt(tf_Uhrzeit_Min.getText());
        LocalDate d = dp_Endzeitpunkt.getValue();
        //  https://stackoverflow.com/questions/5165428/how-to-set-time-to-a-date-object-in-java
        Calendar Kalender = Calendar.getInstance();
        Kalender.set(d.getYear()-1, d.getMonthValue(), d.getDayOfMonth(), std, min, 00);
        datum = Kalender.getTime();
    }

    public void Zurueck_click(ActionEvent actionEvent) throws IOException {
        //Zurueck zum Katalog (Suchseite-Auktionen) gelangen
        Stage s = new Stage();
        Stage cs = (Stage) bt_zurueck.getScene().getWindow();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_SucheAuktionen_Layout.fxml"));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
        cs.close();
    }

    public void Einstellen_click(ActionEvent actionEvent) {
        sepc =SEPClient.getInstance();
        u = sepc.currentsession;
        //Pruefung ob alle Felder ausgefuellt sind
        if (tf_Name.getText().isEmpty() || tf_Beschreibung.getText().isEmpty() || dp_Endzeitpunkt.getValue() == null || tf_Mindestgebot.getText().isEmpty() || tf_Startpreis.getText().isEmpty() || tf_Versandart.getText().isEmpty() || prb == null){
            error.setContentText("Bitte alle Felder ausfuellen!");
            error.showAndWait();
        }
        else {
            Datum_Rechner();
            //auktionen brauchen wieder Bild
            Auction a = new Auction(tf_Name.getText(), tf_Beschreibung.getText(),  Double.parseDouble(tf_Startpreis.getText()), Double.parseDouble(tf_Mindestgebot.getText()), datum , tf_Versandart.getText());
            //sepc.requestsave(a);                              //Auktion an Server senden              IN ARBEIT!!!!!!!!!!
            //Bei erfolgreichem Einstellen eine Meldung anzeigen
            error.setAlertType(Alert.AlertType.CONFIRMATION);
            error.setContentText("Auktion erfolreich eingestellt!");
            error.showAndWait();
            System.out.println(datum);
        }
    }

    public void Bild_click(MouseEvent mouseEvent) throws IOException {
        byte[] image;
        //Produktbild mit FileChooser auswählen
        FileChooser ProduktBildExplorer = new FileChooser();
        ProduktBildExplorer.setTitle("Bitte Produktbild auswaehlen");
        File f = ProduktBildExplorer.showOpenDialog(null);
        //ByteArray erzeugen und byte to Byte konvertieren
        image = FileUtils.readFileToByteArray(f);
        int counter = 0;
        for(byte b: image){
            this.image[counter++] = b;
        }
        //Bild in FXML darstellen
        if (f != null) {
            Image bild = new Image(f.toURI().toString());
            iv_bild.setImage(bild);
            prb = iv_bild.getImage();
        }
    }

    public void dp_click(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //aktuelles Datum auswaehlen
        dp_Endzeitpunkt.setValue(NOW_LOCAL_DATE());
    }
}

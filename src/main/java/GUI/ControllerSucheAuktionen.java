package GUI;

import Client.SEPClient;
import Server.Auction;
import Server.ObjectPasser;
import Server.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSucheAuktionen implements Initializable {

    public Tab tab_Produkte;
    public Tab tab_Auktionen;
    public Button bt_prdukte;
    public Button BellungsButton;
    public Button ChatButton;
    Auction[] produkte;
    Auction[] ausgabe;
    String selectedItemName;
    double selectedItemPreis;
    int selectedItem;
    SEPClient sepc;
    User u;
    ObjectPasser o;
    public TextField Tx_Suche;
    public Button Bt_Suche;
    public ListView Li_Produkte;
    public ListView Li_Preis;
    public ListView Li_Datum;
    public CheckBox Ch_aufsteigend;
    public CheckBox Ch_absteigend;

    public Button HomeButton;
    public Button EinstellenButton;
    @FXML
    private Button id_suche;
    @FXML
    private Button id_profil;
    @FXML
    private Button id_wallet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sepc = SEPClient.getInstance();
        u = (User) sepc.currentsession;
        if (u.isBusiness() == true){
            id_wallet.setDisable(true);
        }
        o = ObjectPasser.getInstance();

        gesamterKatalog();
        aufsteigend();
        absteigend();
    }

    public Auction[] gesamterKatalog() {

        ausgabe = o.auctiondummy;

        for(int i=0; i<o.auctiondummy.length; i++) {
            Li_Produkte.getItems().add(o.auctiondummy[i].getauctionName());
            Li_Preis.getItems().add(o.auctiondummy[i].getStartpreis());
            Li_Datum.getItems().add(o.auctiondummy[i].getTimeForAuction());
        }
        return o.auctiondummy;
    }

    public void aufsteigend(){                                        //setzt Button absteigend auf false wenn aufsteigend ausgewählt wird
        Ch_aufsteigend.setOnAction((event) -> {                      //https://code.makery.ch/blog/javafx-8-event-handling-examples/
            Ch_absteigend.setSelected(false);
        });
    }

    public void absteigend(){                                 //setzt Button aufsteigend auf false wenn absteigend ausgewählt wird
        Ch_absteigend.setOnAction((event) ->  {
            Ch_aufsteigend.setSelected(false);
        });
    }


    public void leereListboxen() {
        Li_Produkte.getItems().clear();
        Li_Preis.getItems().clear();
        Li_Datum.getItems().clear();
    }

    public void suche(ActionEvent actionEvent) {
        suche();                                            //filtert suche
        sortiere(ausgabe);                                  //sortiert auf- oder absteigend
    }

    public void suche(){
        leereListboxen();

        int count = 0;

        if(!Tx_Suche.getText().isEmpty()) {                                     //Möglichkeit 1, Suchfeld nicht leer
            for (int i = 0; i < o.auctiondummy.length; i++) {
                if (Tx_Suche.getText().toLowerCase() == o.auctiondummy[i].getauctionName().toLowerCase() || o.auctiondummy[i].getauctionName().toLowerCase().contains(Tx_Suche.getText().toLowerCase())) {
                    count++;
                    System.out.println("Suche 1");
                }
            }
            Auction[] a = new Auction[count];
            for (int i = 0, j=0; i < o.auctiondummy.length; i++) {
                if (Tx_Suche.getText().toLowerCase() == o.auctiondummy[i].getauctionName().toLowerCase() || o.auctiondummy[i].getauctionName().toLowerCase().contains(Tx_Suche.getText().toLowerCase())) {
                    a[j] = new Auction(o.auctiondummy[i].getauctionName(), o.auctiondummy[i].getAuctionDescription(),o.auctiondummy[i].getStartpreis(),o.auctiondummy[i].getMinadd(),o.auctiondummy[i].getTimeForAuction(),o.auctiondummy[i].getVersandart());
                    Li_Produkte.getItems().add(o.auctiondummy[i].getauctionName());
                    Li_Preis.getItems().add(o.auctiondummy[i].getStartpreis());
                    Li_Datum.getItems().add(o.auctiondummy[i].getTimeForAuction());
                    j++;
                }
            }
            ausgabe = a;
        }
        else if (Tx_Suche.getText().isEmpty()) {                                                                   //Möglichkeit 2, Suchfeld leer
            System.out.println("Suche 2");
            ausgabe = gesamterKatalog();
        }

        if(Li_Produkte.getItems().isEmpty()){                                                                      //Möglichkeit 3, keine Produkte zur Suche gefunden
            Alert alert = new Alert(Alert.AlertType.WARNING);                                                      //https://code.makery.ch/blog/javafx-dialogs-official/
            alert.setTitle("Keine Ergebnisse zu dieser Suche");
            alert.setHeaderText(null);
            alert.setContentText("Keine Ergebnisse gefunden");
            alert.showAndWait();
        }
    }

    public void sortiere(Auction[] ausgabe) {

        if (ausgabe != null) {
            if (Ch_absteigend.isSelected()) {                                                        //sortiere Einträge nach Preis aufsteigend
                leereListboxen();
                Auction temp;                                                                          //https://www.java-programmieren.com/bubblesort-java.php
                for (int i = 1; i < ausgabe.length; i++) {
                    for (int j = 0; j < ausgabe.length - i; j++) {
                        if (ausgabe[j + 1].getStartpreis() > ausgabe[j].getStartpreis()) {
                            temp = ausgabe[j];
                            ausgabe[j] = ausgabe[j + 1];
                            ausgabe[j + 1] = temp;
                        }
                    }
                }
                for (int i = 0; i < ausgabe.length; i++) {                                               //füge sortiertes Array in ListBox ein
                    Li_Produkte.getItems().add(ausgabe[i].getauctionName());
                    Li_Preis.getItems().add(ausgabe[i].getStartpreis());
                    Li_Datum.getItems().add(ausgabe[i].getTimeForAuction());
                }
            }

            if (Ch_aufsteigend.isSelected()) {                                                         //sortiere Einträge nach Preis aufsteigend
                leereListboxen();
                Auction temp;                                                                          //https://www.java-programmieren.com/bubblesort-java.php
                for (int i = 1; i < ausgabe.length; i++) {
                    for (int j = 0; j < ausgabe.length - i; j++) {
                        if (ausgabe[j + 1].getStartpreis() < ausgabe[j].getStartpreis()); {
                            temp = ausgabe[j];
                            ausgabe[j] = ausgabe[j + 1];
                            ausgabe[j + 1] = temp;
                        }
                    }
                }
                for (int i = 0; i < ausgabe.length; i++) {                                               //füge sortiertes Array in ListBox ein
                    Li_Produkte.getItems().add(ausgabe[i].getauctionName());
                    Li_Preis.getItems().add(ausgabe[i].getStartpreis());
                    Li_Datum.getItems().add(ausgabe[i].getTimeForAuction());
                }
            }
        }
    }

    public void auktionenAnzeige() throws IOException{        //bei Auswahl von Eintrag in Li_Produkte soll ein neues Fenster mit ausgewähltem Produkt geöffnet werden

        for (int i=0; i<o.auctiondummy.length;i++){
            if(o.auctiondummy[i]!=null) {
                if (o.auctiondummy[i].getauctionName().equals((String) Li_Produkte.getSelectionModel().getSelectedItem())) {
                    o.setA(o.auctiondummy[i]);
                }
            }
        }
        switchLayout("Shop_DetailAuktion_Layout.fxml", id_suche);
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

    public  void switchLayout(String layoutName, Button b ) throws IOException {
        Stage s = new Stage();
        Stage cs =(Stage)b.getScene().getWindow();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource(layoutName));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();
        cs.close();

    }

    public void tab_pr_click(Event event) throws IOException {
        //this.switchLayout("Shop_Suche_Layout.fxml", this.EinstellenButton);
    }

    public void tab_au_click(Event event) {

    }

    public void bt_produkte_click(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Suche_Layout.fxml", this.EinstellenButton);
    }

    public void ChatLink(ActionEvent actionEvent) {
    }

    public void Bestellungslink(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Bestellungen_Layout.fxml", this.BellungsButton);
    }

    public void EinstellenClick(ActionEvent actionEvent) throws IOException{
        this.switchLayout("Shop_Auktion_Layout.fxml", this.BellungsButton);
    }
}
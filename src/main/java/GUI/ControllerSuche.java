package GUI;

import Client.CSVReader;
import Client.SEPClient;
import Server.Item;
import Server.ObjectPasser;
import Server.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerSuche implements Initializable {

    public Tab tab_Produkte;
    public Tab tab_Auktionen;
    public Button bt_auktionen;
    public Button BellungsButton;
    public Button ChatButton;
    public Button csvscannen;
    public CheckBox bookcheckbox;
    Item[] produkte;
    Item[] ausgabe;
    String selectedItemName;
    double selectedItemPreis;
    int selectedItem;
    SEPClient sepc;
    User u;
    ObjectPasser o;
    public TextField Tx_Suche;
    public ChoiceBox ComKategorie;
    public Button Bt_Suche;
    public ListView Li_Produkte;
    public ListView Li_Kategorie;
    public ListView Li_Preis;
    public ListView Li_Beschreibung;
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
            EinstellenButton.setDisable(false);
            csvscannen.setDisable(false);
            id_wallet.setDisable(true);
        }                                                           //füllt Kategorie am Anfang
        o = ObjectPasser.getInstance();

        List<String> list = new ArrayList<String>();                    //https://stackoverflow.com/questions/18361195/javafx-how-to-load-populate-values-at-start-up
        list.add(null);
        list.add("Bücher");
        list.add("Elektronik");
        list.add("Kleidung");
        ObservableList obList = FXCollections.observableList(list);
        ComKategorie.getItems().clear();
        ComKategorie.setItems(obList);

        gesamterKatalog();
        absteigend();
        aufsteigend();

    }
    public Item[] gesamterKatalog() {
        Item[] produkte = new Item[5];
        produkte[0] = new Item("Faust", "beschreibung", 9.99, null, "Bücher",2);
        produkte[1] = new Item("Die Verwandlung","Beschreibung",14.99,null, "Bücher" ,3);
        produkte[2] = new Item("Batterien","" , 5.95, null, "Elektronik", 5);
        produkte[3] = new Item("Powerbank", "be",22, null, "Elektronik",1);
        produkte[4] = new Item("Kaschmir pullover","beschr" ,29.99 ,null, "Kleidung",2);

        ausgabe = produkte;

        for(int i=0; i<produkte.length; i++) {
            Li_Produkte.getItems().add(produkte[i].getItemname());
            Li_Kategorie.getItems().add(produkte[i].getItemcategory());
            Li_Preis.getItems().add(produkte[i].getItemprize());
            Li_Beschreibung.getItems().add(produkte[i].getItemdescription());
        }
        return ausgabe;
    }

    public void aufsteigend(){                                  //setzt Button absteigend auf false wenn aufsteigend ausgewählt wird
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
        Li_Kategorie.getItems().clear();
        Li_Preis.getItems().clear();
        Li_Beschreibung.getItems().clear();
    }

    public void suche(ActionEvent actionEvent) {
        suche();                                            //filtert suche
        sortiere(ausgabe);                                  //sortiert auf- oder absteigend
    }

    public void suche(){
        leereListboxen();

        int count = 0;
        Item[] produkte = new Item[5];
        produkte[0] = new Item("Faust", "beschreibung", 9.99, null, "Bücher",2);
        produkte[1] = new Item("Die Verwandlung","Beschreibung",14.99,null, "Bücher" ,3);
        produkte[2] = new Item("Batterien","" , 5.95, null, "Elektronik", 5);
        produkte[3] = new Item("Powerbank", "be",22, null, "Elektronik",1);
        produkte[4] = new Item("Kaschmir pullover","beschr" ,29.99 ,null, "Kleidung",2);

        if(!Tx_Suche.getText().isEmpty() && ComKategorie.getSelectionModel().getSelectedItem()==null) {              //Möglichkeit 1, Suchfeld nicht leer & keine Kategorie ausgewählt
            for (int i = 0; i < produkte.length; i++) {
                if (Tx_Suche.getText().toLowerCase() == produkte[i].getItemname().toLowerCase() || produkte[i].getItemname().toLowerCase().contains(Tx_Suche.getText().toLowerCase())) {
                    count++;
                    System.out.println("Suche 1");
                }
            }
            Item a[] = new Item[count];
            for (int i = 0, j=0; i < produkte.length; i++) {
                if (Tx_Suche.getText().toLowerCase() == produkte[i].getItemname().toLowerCase() || produkte[i].getItemname().toLowerCase().contains(Tx_Suche.getText().toLowerCase())) {
                    a[j] = new Item(produkte[i].getItemname(),produkte[i].getItemdescription(), produkte[i].getItemprize(), null, produkte[i].getItemcategory(), 5);
                    Li_Produkte.getItems().add(produkte[i].getItemname());
                    Li_Kategorie.getItems().add(produkte[i].getItemcategory());
                    Li_Preis.getItems().add(produkte[i].getItemprize());
                    Li_Beschreibung.getItems().add(produkte[i].getItemdescription());
                    j++;
                }
            }
            ausgabe = a;
        }
        else if(Tx_Suche.getText().isEmpty() && ComKategorie.getSelectionModel().getSelectedItem()!=null){        //Möglichkeit 2, Suchfeld leer & Kategorie ausgewählt
            for (int i = 0; i < produkte.length; i++) {
                if (ComKategorie.getSelectionModel().getSelectedItem().equals(produkte[i].getItemcategory())) {
                    count++;
                    System.out.println("Suche 2");
                }
            }
            Item a[] = new Item[count];
            for (int i = 0, j=0; i < produkte.length; i++) {
                if (ComKategorie.getSelectionModel().getSelectedItem().equals(produkte[i].getItemcategory())) {
                    a[j] = new Item(produkte[i].getItemname(),produkte[i].getItemdescription(), produkte[i].getItemprize(), null, produkte[i].getItemcategory(), 5);
                    Li_Produkte.getItems().add(produkte[i].getItemname());
                    Li_Kategorie.getItems().add(produkte[i].getItemcategory());
                    Li_Preis.getItems().add(produkte[i].getItemprize());
                    Li_Beschreibung.getItems().add(produkte[i].getItemdescription());
                    j++;
                }
            }
            ausgabe = a;
        }
        else if (!Tx_Suche.getText().isEmpty() && ComKategorie.getSelectionModel().getSelectedItem()!=null) {      //Möglichkeit 3, Suchfeld nicht leer & Kategorie ausgewählt
            for (int i = 0; i < produkte.length; i++) {
                if (Tx_Suche.getText().toLowerCase() == produkte[i].getItemname().toLowerCase() || produkte[i].getItemname().toLowerCase().contains(Tx_Suche.getText().toLowerCase())) {
                    if (ComKategorie.getSelectionModel().getSelectedItem().equals(produkte[i].getItemcategory())) {
                        count++;
                        System.out.println("Suche 3");
                    }
                }
            }
            Item a[] = new Item[count];
            for (int i = 0, j=0; i < produkte.length; i++) {
                if (Tx_Suche.getText().toLowerCase() == produkte[i].getItemname().toLowerCase() || produkte[i].getItemname().toLowerCase().contains(Tx_Suche.getText().toLowerCase())) {
                    if (ComKategorie.getSelectionModel().getSelectedItem().equals(produkte[i].getItemcategory())) {
                        a[j] = new Item(produkte[i].getItemname(), produkte[i].getItemdescription(), produkte[i].getItemprize(), null, produkte[i].getItemcategory(), 5);
                        Li_Produkte.getItems().add(produkte[i].getItemname());
                        Li_Kategorie.getItems().add(produkte[i].getItemcategory());
                        Li_Preis.getItems().add(produkte[i].getItemprize());
                        Li_Beschreibung.getItems().add(produkte[i].getItemdescription());
                        j++;
                    }
                }
            }
            ausgabe = a;
        }
        else if (Tx_Suche.getText().isEmpty() && ComKategorie.getSelectionModel().getSelectedItem()==null) {       //Möglichkeit 4, Suchfeld leer & keine Kategorie ausgewählt
            System.out.println("Suche 4");
            ausgabe = gesamterKatalog();
        }

        if(Li_Produkte.getItems().isEmpty()){                                                                      //Möglichkeit 5, keine Produkte zur Suche gefunden
            Alert alert = new Alert(Alert.AlertType.WARNING);                                                      //https://code.makery.ch/blog/javafx-dialogs-official/
            alert.setTitle("Keine Ergebnisse zu dieser Suche");
            alert.setHeaderText(null);
            alert.setContentText("Keine Ergebnisse gefunden");
            alert.showAndWait();
        }
    }


    public void sortiere(Item[] ausgabe) {

        if (ausgabe != null) {
            if (Ch_absteigend.isSelected()) {                                                        //sortiere Einträge nach Preis aufsteigend
                leereListboxen();
                Item temp;                                                                          //https://www.java-programmieren.com/bubblesort-java.php
                for (int i = 1; i < ausgabe.length; i++) {
                    for (int j = 0; j < ausgabe.length - i; j++) {
                        if (ausgabe[j + 1].getItemprize() > ausgabe[j].getItemprize()) {
                            temp = ausgabe[j];
                            ausgabe[j] = ausgabe[j + 1];
                            ausgabe[j + 1] = temp;
                        }
                    }
                }
                for (int i = 0; i < ausgabe.length; i++) {                                               //füge sortiertes Array in ListBox ein
                    Li_Produkte.getItems().add(ausgabe[i].getItemname());
                    Li_Kategorie.getItems().add(ausgabe[i].getItemcategory());
                    Li_Preis.getItems().add(ausgabe[i].getItemprize());
                    Li_Beschreibung.getItems().add(ausgabe[i].getItemdescription());
                }
            }

            if (Ch_aufsteigend.isSelected()) {                                                         //sortiere Einträge nach Preis aufsteigend
                leereListboxen();
                Item temp;                                                                          //https://www.java-programmieren.com/bubblesort-java.php
                for (int i = 1; i < ausgabe.length; i++) {
                    for (int j = 0; j < ausgabe.length - i; j++) {
                        if (ausgabe[j + 1].getItemprize() < ausgabe[j].getItemprize()) {
                            temp = ausgabe[j];
                            ausgabe[j] = ausgabe[j + 1];
                            ausgabe[j + 1] = temp;
                        }
                    }
                }
                for (int i = 0; i < ausgabe.length; i++) {                                               //füge sortiertes Array in ListBox ein
                    Li_Produkte.getItems().add(ausgabe[i].getItemname());
                    Li_Kategorie.getItems().add(ausgabe[i].getItemcategory());
                    Li_Preis.getItems().add(ausgabe[i].getItemprize());
                    Li_Beschreibung.getItems().add(ausgabe[i].getItemdescription());
                }
            }
        }
    }

    public void produktAnzeige() throws IOException{        //bei Auswahl von Eintrag in Li_Produkte soll ein neues Fenster mit ausgewähltem Produkt geöffnet werden
        /*selectedItemName = (String) Li_Produkte.getSelectionModel().getSelectedItem();
        selectedItem = Li_Produkte.getSelectionModel().getSelectedIndex();
        selectedItemPreis = (double) Li_Preis.getItems().get(selectedItem);
        System.out.println(ausgabe[selectedItem].getItemname());
        sepc.produkt = ausgabe[selectedItem];

        Stage s = new Stage();
        Parent searchLayout = FXMLLoader.load(getClass().getClassLoader().getResource("Shop_Katalog_Layout.fxml"));
        Scene sc = new Scene(searchLayout);
        s.setScene(sc);
        s.show();*/

        for (int i=0; i<o.itemdummy.length;i++){
            if(o.itemdummy[i]!=null) {
                if (o.itemdummy[i].getItemname().equals((String) Li_Produkte.getSelectionModel().getSelectedItem())) {
                    o.setI(o.itemdummy[i]);
                }
            }
        }
        switchLayout("Shop_Katalog_Layout.fxml", id_suche);

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

    /*public void KaufenClick(ActionEvent actionEvent) {
        //Kunde muss sich Produkt aussuchen, welches er kaufen will
        //Preis dann aus Wallet abziehen

        String name = (String)Li_Produkte.getSelectionModel().getSelectedItem();
        for (int i = 0; i < produkte.length; i++){
            if (name==produkte[i].getItemname()){
                if(u.getWallet().getWalletmoney()-produkte[i].getItemprize()>=0) {
                    u.getWallet().setWalletmoney(u.getWallet().getWalletmoney() - produkte[i].getItemprize()); //guthaben vom wallet abziehen
                    //sepc.requestObjectOperation(PackageOperation.DELETE, produkte[i]); //aus verfügbare Artikeltabelle
                    //sepc.requestObjectOperation(PackageOperation.SAVE, produkte[i]); // in gekaufte Artikeltabelle
                    Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                    a.setContentText(produkte[i].getItemname() + " erfolgreich gekauft!!!");
                    a.show();
                } else {
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setContentText("nicht genug Guthaben");
                    a.show();
                }
            }
        }
    }*/

    public void EinstellenClick(ActionEvent actionEvent) throws IOException {
       //Weiter zur ProduktErstellungsSeite
        this.switchLayout("Shop_ProduktEinstellung_Layout.fxml", this.EinstellenButton);
    }

    public void tab_pr_click(Event event) {

    }

    public void tab_au_click(Event event) throws IOException {
        //this.switchLayout("Shop_SucheAuktionen_Layout.fxml", this.EinstellenButton);
    }

    public void bt_auktionen_click(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_SucheAuktionen_Layout.fxml", this.bt_auktionen);
    }

    public void Bestellungslink(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Bestellungen_Layout.fxml", this.BellungsButton);
    }

    public void ChatLink(ActionEvent actionEvent) {
    }

    public void scannen(ActionEvent actionEvent) {
        FileChooser csvfinder = new FileChooser();
        csvfinder.setTitle("Bitte Profilbild auswählen");
        File f = csvfinder.showOpenDialog(null);
        if (f != null) {
            CSVReader readit= new CSVReader();
            System.out.println(f.toURI().toString().substring(6));
            readit.read(f.toURI().toString().substring(6),bookcheckbox.isSelected(),sepc.currentsession);
        }
    }
}
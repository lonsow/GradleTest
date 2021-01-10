package GUI;

import Client.SEPClient;
import Server.Auction;
import Server.Item;
import Server.ObjectPasser;
import Server.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerBestellungen implements Initializable {

    public Button HomeButton;
    public Button BellungsButton;
    public Button ChatButton;
    public ListView ANameList;
    public ListView AShippingDate;
    public ListView AShippingType;
    public ListView BNameList;
    public ListView BDescription;
    public ListView BPrice;
    public Button yourproducts;
    @FXML
    private Button id_suche;
    @FXML
    private Button id_profil;
    @FXML
    private Button id_wallet;

    SEPClient sepc;
    ObjectPasser o;
    Auction yourauctions[];
    Item youritems[];

    User user ;

    Alert a = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Auction dummyauction=new Auction();
        Item dummyitem=new Item();
        dummyitem.setBuyingUser(sepc.currentsession);
        dummyauction.setWinningUser(sepc.currentsession);
        sepc = SEPClient.getInstance();
        user =  sepc.currentsession;
        if (user.isBusiness() == true){
            id_wallet.setDisable(true);
        }

        o=ObjectPasser.getInstance();
        //sepc.requestfetch(dummyitem, dummyitem.getBuyingUser)                                  IN ARBEIT!!!!!! MEHRERE ITEMS FETCHEN
        //youritems=(Item[])sepc.getServerResult();
        //sepc.requestfetch(dummyauction, dummyauction.getWinningUser)                                  IN ARBEIT!!!!!! MEHRERE ITEMS FETCHEN
        //yourauctions=(Auction[])sepc.getServerResult();
        yourauctions=o.auctiondummy;
        youritems=o.itemdummy;
        showAuctionHistory(yourauctions);
        showProductHistory(youritems);
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


    public void Bestellungslink(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Bestellungen_Layout.fxml", this.BellungsButton);
    }

    public void ChatLink(ActionEvent actionEvent) {
    }

    
    private void showAuctionHistory(Auction auctions[]){
        for (int i=0; i<auctions.length; i++){
            if(auctions[i]!=null) {
                ANameList.getItems().add(auctions[i].getauctionName());
                AShippingType.getItems().add(auctions[i].getVersandart());
                //AShippingDate.getItems().add(auctions[i].) Hier noch ankunfts/abholdatum einfügen
            }
        }

    }
    
    
    private void showProductHistory(Item products[]){
        for (int i=0; i<products.length; i++){
            if(products[i]!=null) {
                BNameList.getItems().add(products[i].getItemname());
                BDescription.getItems().add(products[i].getItemdescription());
                BPrice.getItems().add(Double.toString(products[i].getItemprize()));
            }
        }
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


    public void StornierenAnfrage(MouseEvent mouseEvent) throws IOException {
        for (int i=0; i<o.itemdummy.length;i++){
            if(o.itemdummy[i]!=null) {
                if (o.itemdummy[i].getItemname().equals((String) BNameList.getSelectionModel().getSelectedItem())) {
                    o.setI(o.itemdummy[i]);
                }
            }
        }
        switchLayout("Shop_Bestellungen_Stornieren.fxml", id_suche);
    }

    public void gotoproducts(ActionEvent actionEvent) throws IOException {
        switchLayout("Shop_angeboteneProdukte_Layout.fxml", id_suche);
    }
}

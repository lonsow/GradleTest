package GUI;

import Client.SEPClient;
import Server.Item;
import Server.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStartseite implements Initializable {
    public Button HomeButton;
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

    //@FXML
    //private TableView<Item> item_list;
    @FXML
    private TableColumn<Item, String> name;
    @FXML
    private TableColumn<Item, Double> price;
    @FXML
    private TableColumn<Item,String> description;
    @FXML
    private TableColumn<Item,String> category;
    @FXML
    private ListView<Item> items_list;
    @FXML
    private ListView <Item> gebote_listview ;
    @FXML
    private ListView <Item> eigenauktion_Listview;
    @FXML
    private ListView <Item> gemerkteAuction_Listview;



    ObservableList<Item> itemObservableList ;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sepc = SEPClient.getInstance();
        u = (User)  sepc.currentsession;
        if (u.isBusiness() == true){
            id_wallet.setDisable(true);
        }

        /*name.setCellValueFactory(new PropertyValueFactory<Item, String>("itemname"));
        price.setCellValueFactory(new PropertyValueFactory<Item, Double>("itemprize"));
        description.setCellValueFactory(new PropertyValueFactory<Item, String>("itemdescription"));
        category.setCellValueFactory(new PropertyValueFactory<Item, String>("itemcategory"));*/

        Item item = new Item();

        /*sepc.requestObjectOperation(4, null, null,null,null );
        sepc.getServerResult();
        List<Item> arrayList = (List<Item>) sepc.getServerResult();
        itemObservableList = FXCollections.observableArrayList(arrayList);
        item_list.setItems(itemObservableList);*/
        ObservableList <Item> itemList = FXCollections.observableArrayList();
        itemList.clear();
        //These data should be request from the server, in the meantime are simulate by following lines
        itemList.add(new Item("Lampe","weiß",15.0, (User)sepc.currentsession, "Elektronik", 5, "https://icons.iconarchive.com/icons/rokey/the-last-order/48/default-document-icon.png"));
        itemList.add(new Item("Rucksack","braun",30.0, (User)sepc.currentsession, "Schulausrüstung", 15, "https://icons.iconarchive.com/icons/rokey/the-last-order/48/default-document-icon.png"));

        items_list.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {



            @Override
            public ListCell<Item> call(ListView<Item> itemListView) {
                ListCell<Item> cell = new ListCell<Item>(){
                    protected void updateItem(Item product, boolean empty){
                        super.updateItem(product, empty);
                        if(product != null){
                            ImageView imageView = new ImageView(new Image(product.getImagePath()));
                            setGraphic(imageView);
                            setText(product.getItemname() + "\n" + product.getItemprize()+ "€" + "\n"+ product.getItemdescription() +"\n"+ product.getItemcategory());

                            //setText(product.getDescription());

                        }
                    }
                };
                return cell;
            }
        });
        ObservableList<Item> gebotList = FXCollections.observableArrayList();
        gebotList.clear();
        gebotList.add(new Item("Guitare" ," Geile Gitarre", 250, (User)sepc.currentsession,"Elektronik", 10, "https://icons.iconarchive.com/icons/wackypixel/lesters-electric-guitar/128/BlackBeauty-Guitar-icon.png"));
        gebotList.add(new Item("Guitare" ," Schönes Auto mit allem Komfort", 1000, (User)sepc.currentsession,"Auto", 22, "https://icons.iconarchive.com/icons/artdesigner/urban-stories/128/Car-icon.png"));

        gebote_listview.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> imageItemListView) {
                ListCell<Item> cell = new ListCell<Item>(){
                    @Override
                    protected void updateItem(Item imageItem, boolean empty){
                        super.updateItem(imageItem, empty);
                        if(imageItem != null){
                            ImageView imageView = new ImageView(new Image(imageItem.getImagePath()));
                            //setGraphic(imageView);
                            //setText(imageItem.getDescription());
                            //setGraphic();
                            HBox hBox = new HBox();
                            Button button = new Button("merken");
                            Pane pane = new Pane();

                            hBox.getChildren().addAll(imageView,new Label(imageItem.getItemdescription()),pane,button);
                            button.setOnAction(e -> showMessage("Bitte gehe in gemerkte Liste"));

                            hBox.setHgrow(pane, Priority.ALWAYS);

                            setGraphic(hBox);
                        }
                    }
                };

                return cell;
            }
        });


        ObservableList<Item> eigenAuktionList= FXCollections.observableArrayList();
        eigenAuktionList.clear();
        eigenAuktionList.add(new Item("Fußball","Schwarz-weiß", 35, (User)sepc.currentsession, "Sport", 5, "https://icons.iconarchive.com/icons/martin-berube/sport/64/Football-icon.png"));
        eigenAuktionList.add(new Item("Brotbox","Sehr schön", 35, (User)sepc.currentsession, "Küchengerät", 15, "https://icons.iconarchive.com/icons/greg-barnes/vintage-kitchen/64/Breadbox-icon.png"));

        eigenauktion_Listview.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> eigenquktionListview) {
                ListCell<Item>cell = new ListCell<Item>(){
                    protected void updateItem(Item imageItem, boolean empty) {
                        super.updateItem(item, empty);
                        if(imageItem != null){
                            ImageView imageView = new ImageView(new Image(imageItem.getImagePath()));
                            setGraphic(imageView);
                            setText(imageItem.getItemname() + "\n" + imageItem.getItemprize()+ "€" + "\n"+ imageItem.getItemdescription() +"\n"+ imageItem.getItemcategory());

                        }
                    }
                };
                return cell;
            }


        });

        ObservableList gemerkteAuctionList = FXCollections.observableArrayList();
        gemerkteAuctionList.clear();
        gemerkteAuctionList.add(new Item("Gitarre","Hat Jimi Hendrick gehört", 500, (User)sepc.currentsession, "Musik", 1, "https://icons.iconarchive.com/icons/wackypixel/lesters-electric-guitar/128/BlackBeauty-Guitar-icon.png"));
        gemerkteAuctionList.add(new Item("Audi","Automatik", 10000, (User)sepc.currentsession, "Auto", 4, "https://icons.iconarchive.com/icons/artdesigner/urban-stories/128/Car-icon.png"));
        gemerkteAuction_Listview.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> param) {
                ListCell<Item> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null){
                            ImageView imageView = new ImageView(new Image(item.getImagePath()));
                            setGraphic(imageView);
                            setText(item.getItemname() + "\n" + item.getItemprize()+ "€" + "\n"+ item.getItemdescription() +"\n"+ item.getItemcategory());
                        }
                    }
                };
                return cell;
            }
        });

        items_list.setItems(itemList);
        gebote_listview.setItems(gebotList);
        eigenauktion_Listview.setItems(eigenAuktionList);
        gemerkteAuction_Listview.setItems(gemerkteAuctionList);




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
    public void showMessage(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Test message box");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();

    }


    public void Bestellungslink(ActionEvent actionEvent) throws IOException {
        this.switchLayout("Shop_Bestellungen_Layout.fxml", this.BellungsButton);
    }

    public void ChatLink(ActionEvent actionEvent) {
    }
}



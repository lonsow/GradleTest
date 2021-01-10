package GUI;

import Client.SEPClient;
import Server.Item;
import Server.ObjectPasser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAngeboteneProdukte implements Initializable {
    public ListView PName;
    public ListView PPrice;
    public ListView PBuyingUser;
    Item youritems[];
    public Button Backbutton;
    ObjectPasser o;
    SEPClient sepc;

    public void back(ActionEvent actionEvent) throws IOException {
        switchLayout("Shop_Bestellungen_Layout.fxml",Backbutton);
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
        Item dummyitem=new Item();
        sepc = SEPClient.getInstance();
        //dummyitem.setOfferingUser(sepc.currentsession);
        //sepc.requestfetch(dummyitem,dummyitem.getOfferingUser());             MEHRERE ITEMS AUF EINMAL AUS DB ENTNEHMEN????
        //youritems= (Item[]) sepc.getServerResult();
        o= ObjectPasser.getInstance();
        youritems=o.itemdummy;
        showyourproducts(youritems);
    }

    private void showyourproducts(Item products[]){
        for (int i=0; i<products.length; i++){
            if(products[i]!=null) {
                PName.getItems().add(products[i].getItemname());
                if (products[i].getBuyingUser()!=null) {
                    PBuyingUser.getItems().add(products[i].getBuyingUser().getUsername());
                } else {PBuyingUser.getItems().add("not sold yet");}
                PPrice.getItems().add(Double.toString(products[i].getItemprize()));
            }
        }
    }

    public void rateBuyer(MouseEvent mouseEvent) throws IOException {
        if(PBuyingUser.getSelectionModel().getSelectedItem()!="not sold yet") {
            o.setI(youritems[PBuyingUser.getSelectionModel().getSelectedIndex()]);
            switchLayout("Shop_RateBuyer_Layout.fxml",Backbutton);
        }
    }
}

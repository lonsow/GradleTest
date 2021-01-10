package Client;

import Server.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    static SEPClient sepc = SEPClient.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root =  FXMLLoader.load(getClass().getClassLoader().getResource("Shop_Layout.fxml"));
        primaryStage.setTitle("SEP");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main (String[]args)
    {

        User dummy = new User( "username",  "usermail",   "userpasswort",  true,  "businessname",   null);
        sepc.currentsession = dummy;

      //  testmethodeUser();
        new Thread(sepc).start();
        launch(args);
    }

    public static void testmethodeUser(){
        User user = new User("ChristosStinkt", "uni.christos.ath@mail.com",
                "Passwort123", true,
                "vollständigerbusinessname", null );


        double walletdouble = 8.12 ;
        Wallet wallet = new Wallet();
        wallet.setWalletmoney(walletdouble);
        wallet.setUser(user);



        //Auction (String auctionName, String auctionDescription,  double startpreis, double minadd, Date timeForAuction, String versandart)
        Auction auction = new Auction("auktionsname", "auktionsBeschreibung", 1.78 , 0.5, null, "Verschiffen" );
        //Items String itemname,
        //                 String itemdescription, double itemprize,
        //                 User user, String itemcategory,int itemsum)
        Item item = new Item("ItemName1","ItemBeschreibung1",10.0, user, "Buch", 7 );
        Item item1 = new Item("ItemName2", "ItemBeschreibung2", 20.0,user,"Elektro",10);

        //Rating
        Rating rating = new Rating(5, "Das Item ist schaisze");
        //rating.setUserThatRated(user);
        // rating.setUserRated(user);

        //AdressString country, String city, String street, int streetNumber, int postalcode, User user
        Adress adress = new Adress("Deutschland", "Essen", "Limbecker", 12, 45355);
        //
        //auction.setProvidingUser(user);

        //user.addObjectToAuctionsOpened(auction);
        // user.getAuctionsOpened().add(auction);  //Referenziert das auf das gleiche Objekt wie  user.addObjectToAuctionsOpened(auction);

        // 1 zu 1
        user.setWallet(wallet);
        user.setAdress(adress);


        // 1 zu n
        user.addItemsOfferedByUser(item);

        user.addItemsBought(item);

        user.getAuctionsBidd().add(auction);
        user.addAuctionsBidd(auction);
        //user.getAuctionsOpened().add(auction);
        user.addToAuctionsOpened(auction);
        //user.getRatingsOfUser().add(rating);
        user.addRatingsOfUser(rating);
        //user.getUserRatings().add(rating);
        user.addUserRatings(rating);
        // n zu 1
        item.setOfferingUser(user);
        item1.setOfferingUser(user);

        item.setBuyingUser(user);
        item1.setBuyingUser(user);


        auction.setProvidingUser(user);
        auction.setWinningUser(user);
        auction.setUserBidded(user);

        rating.setUserRated(user);
        rating.setUserThatRated(user);

        // n zu n
        user.getItemList().add(item);
        user.addItemList(item);
        user.addItemList(item1);


        item.getUsers().add(user);
        item.addUsers(user);
        item1.addUsers(user);

        sepc.requestsave(user);
    }
}

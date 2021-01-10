package Server;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id//sagt aus das id ein Primary KEy ist
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String username ;

    private String usermail ;

    private int useradressid;

    private String userpasswort;
    @Lob
    private byte[] image;
    /**SOLLTE mit Apache Commons.
     *  FileUtils.readFileToByteArray(File input) gespeichert und übergeben werden
     **/
    private boolean isBusiness ;
    private String businessname;

    //Jeder user hat eine Wallet
    
    @OneToOne(cascade = {CascadeType.ALL})
    private Wallet wallet;//Jeder nutzer kann eine Wallet haben

    //Jeder user hat eine Adresse
    @OneToOne(cascade = {CascadeType.ALL})
    private Adress adress;

    //Item das ein Businessuser hinzugefügt hat
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "offeringUser", fetch = FetchType.EAGER)
    private List<Item> itemsOfferedByUser=new ArrayList<>();

    @OneToMany(mappedBy = "buyingUser")
    private List<Item> itemsBought=new ArrayList<>();

    @OneToMany(mappedBy = "userThatRated")
    private List<Rating> ratingsOfUser=new ArrayList<>();

    @OneToMany(mappedBy = "userRated")
    private List<Rating> userRatings = new ArrayList<>();
    //Liste mit gebotenen Auktionen
    @OneToMany(mappedBy = "userBidded")
    private List<Auction> auctionsBidd=new ArrayList<>();

    //Jeder User kann mehrere Auktionen einstellen
    @OneToMany(mappedBy = "providingUser", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Auction> auctionsOpened = new ArrayList<>();

    //Jeder User kann mehrere Items angesehen haben
    @ManyToMany(mappedBy = "users")
    private List <Item> itemList = new ArrayList<Item>() ;

    //Konstruktor

    public User() {}
    //Wurde angemackert
    public User ( String username, String usermail,
                  String userpasswort,
                  boolean isBusiness, String businessname,
                  byte[] image) //int userwalletid ,
    {
        this.username = username;
        this.usermail = usermail;
        this.userpasswort = userpasswort;
        this.image = image;
        //this.image = image;
        wallet = new Wallet(); //wallet.setWalletid(userwalletid);
        adress = new Adress();
        this.isBusiness =isBusiness;
        this.businessname = businessname;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public int getUseradressid() {
        return useradressid;
    }

    public void setUseradressid(int useradressid) {
        this.useradressid = useradressid;
    }

    public String getUserpasswort() {
        return userpasswort;
    }

    public void setUserpasswort(String userpasswort) {
        this.userpasswort = userpasswort;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void addItemList(Item item){
        this.getItemList().add(item);
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public List<Auction> getAuctionsBidd() {
        return auctionsBidd;
    }

    public void setAuctionsBidd(List<Auction> auctionsBidd) {
        this.auctionsBidd = auctionsBidd;
    }

    public void addAuctionsBidd(Auction auction){
        this.getAuctionsBidd().add(auction);

    }
    public List<Rating> getRatingsOfUser() {
        return ratingsOfUser;
    }

    public void setRatingsOfUser(List<Rating> ratingsOfUser) {
        this.ratingsOfUser = ratingsOfUser;
    }

    public void addRatingsOfUser(Rating rating){
        this.getRatingsOfUser().add(rating);
    }
    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    public void addUserRatings(Rating rating){
        this.getUserRatings().add(rating);
    }
    public List<Item> getItemsBought() {
        return itemsBought;
    }

    public void setItemsBought(List<Item> itemsBought) {
        this.itemsBought = itemsBought;
    }

    public void addItemsBought(Item item){
        this.getItemsBought().add(item);
    }
    public List<Auction> getAuctionsOpened() {
        return auctionsOpened;
    }
    public void setAuctionsOpened(List<Auction> auctionsOpened) {
        this.auctionsOpened = auctionsOpened;
    }
    public void addToAuctionsOpened(Auction auction)
    {
        this.getAuctionsOpened().add(auction);
    }

    public List<Item> getItemsOfferedByUser() {
        return itemsOfferedByUser;
    }

    public void setItemsOfferedByUser(List<Item> itemsOfferedByUser) {
        this.itemsOfferedByUser = itemsOfferedByUser;
    }
    public void addItemsOfferedByUser(Item item){
        itemsOfferedByUser.add(item);
    }
}

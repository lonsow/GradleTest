package Server;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int itemid;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<User> users = new ArrayList<>();

    private String itemname;

    //Jedes Item hat einen VErkäufer
    @ManyToOne(cascade = {CascadeType.ALL})
    private User offeringUser;

    //jedes Item hat einen Käufer
    @ManyToOne(cascade = {CascadeType.ALL})
    private User buyingUser;


    private int itemsum;

    private String itemdescription;
    private double itemprize;
    private String itemcategory;
    private String imagePath;

    @Lob
    private byte[] image ;




    public Item() {}
    //Konstruktor
    public Item( String itemname,
                 String itemdescription, double itemprize,
                 User user, String itemcategory,int itemsum){ //,

        this.itemname =itemname;
        this.itemdescription = itemdescription;
        this.itemprize = itemprize;
        this.offeringUser =user;
        this.itemcategory =itemcategory;
        this.itemsum = itemsum;

    }
    public Item( String itemname,
                 String itemdescription, double itemprize,
                 User user, String itemcategory,int itemsum, String imagePath){ //,

        this.itemname =itemname;
        this.itemdescription = itemdescription;
        this.itemprize = itemprize;
        this.offeringUser =user;
        this.itemcategory =itemcategory;
        this.itemsum = itemsum;
        this.imagePath = imagePath;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUsers(User user){
        this.getUsers().add(user);
    }
    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public User getOfferingUser() {
        return offeringUser;
    }

    public void setOfferingUser(User user) {
        this.offeringUser = user;
    }

    public int getItemsum() {
        return itemsum;
    }

    public void setItemsum(int itemsum) {
        this.itemsum = itemsum;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public double getItemprize() {
        return itemprize;
    }

    public void setItemprize(double itemprize) {
        this.itemprize = itemprize;
    }

    public String getItemcategory() {
        return itemcategory;
    }

    public void setItemcategory(String itemcategory) {
        this.itemcategory = itemcategory;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImagePath() {
        return this.imagePath;
    }


    public User getBuyingUser() {
        return buyingUser;
    }

    public void setBuyingUser(User buyingUser) {
        this.buyingUser = buyingUser;
    }

}

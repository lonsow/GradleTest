package Server;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

//braucht bild im konstruktor?

@Entity
public class Auction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int auctionId;

    private String auctionName;
    private String auctionDescription;

    @Lob
    private byte[] auctionimage;


    private double startpreis;
    private double minadd;
    private Date timeForAuction;
    private String versandart;

    @ManyToOne
    private User providingUser;

    @OneToOne(cascade = {CascadeType.ALL})
    private User winningUser;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<User> saved;

    @ManyToOne(cascade = {CascadeType.ALL})
    private User userBidded;

    public Auction() {}

    public Auction(String auctionName, String auctionDescription,  double startpreis, double minadd, Date timeForAuction, String versandart) {
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.startpreis = startpreis;
        this.minadd = minadd;
        this.timeForAuction = timeForAuction;
        this.versandart = versandart;
    }

    public String getauctionName() {
        return this.auctionName;
    }

    public void setAuctionname(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getAuctionDescription() {
        return this.auctionDescription;
    }

    public void setAuctionDescription(String auctionDescription) {
        this.auctionDescription = auctionDescription;
    }

    public byte[] getAuctionimage() {
        return this.auctionimage;
    }

    public void setAuctionimage(byte[] auctionimage) {
        this.auctionimage = auctionimage;
    }

    public double getStartpreis() {
        return this.startpreis;
    }

    public void setStartpreis(double startpreis) {
        this.startpreis = startpreis;
    }

    public double getMinadd() {
        return this.minadd;
    }

    public void setMinadd(double minadd) {
        this.minadd = minadd;
    }

    public Date getTimeForAuction() {
        return this.timeForAuction;
    }

    public void setTimeForAuction(Date timeForAuction) {
        this.timeForAuction = timeForAuction;
    }

    public String getVersandart() {
        return this.versandart;
    }

    public void setVersandart(String versandart) {
        this.versandart = versandart;
    }

    public User getProvidingUser() {
        return this.providingUser;
    }

    public void setProvidingUser(User providingUser) {
        this.providingUser = providingUser;
    }

    public int getAuctionId() {
        return this.auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public List<User> getSaved() {
        return this.saved;
    }

    public void setSaved(List<User> saved) {
        this.saved = saved;
    }

    public User getUserBidded() {
        return userBidded;
    }

    public void setUserBidded(User userBidded) {
        this.userBidded = userBidded;
    }

    public User getWinningUser() {
        return winningUser;
    }

    public void setWinningUser(User winningUser) {
        this.winningUser = winningUser;
    }
}

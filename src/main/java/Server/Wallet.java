package Server;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Wallet implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int walletid;
    private double walletmoney;

    @OneToOne(cascade = {CascadeType.ALL})
    private User user;//jede Wallet hat einen Nutzer

    public Wallet(){}

    public int getWalletid() {
        return walletid;
    }

    public void setWalletid(int walletid) {
        this.walletid = walletid;
    }

    public double getWalletmoney() {
        return walletmoney;
    }

    public void setWalletmoney(double walletmoney) {
        this.walletmoney = walletmoney;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

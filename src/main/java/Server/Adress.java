package Server;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Adress implements Serializable
{
    //Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int adressid;
    //Land
    private String country;
    //Stadt
    private String city;
    //Straße
    private  String street;
    //Straßennummer
    private int streetNumber;
    //Postleitzahl
    private int postalcode;

    @OneToOne(cascade = {CascadeType.ALL})
    private User user;

    //Konstruktor
    public Adress() {
    }

    public Adress(String country, String city, String street, int streetNumber, int postalcode)
    {
        this.country = country;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalcode = postalcode;
    }


    public int getAdressid() {
        return adressid;
    }

    public void setAdressid(int adressid) {
        this.adressid = adressid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        postalcode = postalcode;
    }
}

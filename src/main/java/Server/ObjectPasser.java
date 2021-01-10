package Server;


// KLASSE ZUM ÜBERTRAGEN VON INFORMATIONEN ZWISCHEN CONTROLLERN; ENTHÄLT ZUR ZEIT NOCH DUMMYS DIE DIE DATENBANKANBINDUNG SIMULIEREN SOLLEN

import java.util.Calendar;
import java.util.Date;

public class ObjectPasser {
    private User u;
    private Item i;
    private Auction a;
    public Item itemdummy[];
    public Auction auctiondummy[];


    private static ObjectPasser op;
    private ObjectPasser(){
        // ERSTELLUNG DER DUMMIES
        Calendar Kalender = Calendar.getInstance();
        Kalender.set(2020, 12,31,23,59,59);
        Date datum = Kalender.getTime();
        User dummyuser = new User("Till","till@Tmail.de","123",true,"Laden",null);
        User dummykaufer= new User("Max","Max@Mmail.de","123",false,"",null);
        itemdummy= new Item[5];
        auctiondummy=new Auction[5];
        itemdummy[0] = new Item("Faust", "beschreibung", 9.99, dummyuser, "Bücher",2);
        itemdummy[1] = new Item("Die Verwandlung","Beschreibung",14.99,dummyuser, "Bücher" ,3);
        itemdummy[2] = new Item("Batterien","sehr energetisch" , 5.95, dummyuser, "Elektronik", 5);
        itemdummy[3] = new Item("Powerbank", "Saft for days",22, dummyuser, "Elektronik",1);
        itemdummy[4] = new Item("Kaschmir pullover","beschr" ,29.99 ,dummyuser, "Kleidung",2);
        auctiondummy[0] = new Auction();
        auctiondummy[1] = new Auction();
        auctiondummy[2] = new Auction();
        auctiondummy[3] = new Auction();
        auctiondummy[4] = new Auction();
        auctiondummy[0].setAuctionname("test1");
        auctiondummy[1].setAuctionname("test2");
        auctiondummy[2].setAuctionname("test3");
        auctiondummy[3].setAuctionname("test4");
        auctiondummy[4].setAuctionname("test5");
        auctiondummy[0].setVersandart("selbstabholer");
        auctiondummy[1].setVersandart("Expresslieferung");
        auctiondummy[2].setVersandart("selbstabholer");
        auctiondummy[3].setVersandart("Schneckenexpress");
        auctiondummy[4].setVersandart("selbstabholer");
        auctiondummy[0].setStartpreis(5);
        auctiondummy[1].setStartpreis(20);
        auctiondummy[2].setStartpreis(15);
        auctiondummy[3].setStartpreis(3);
        auctiondummy[4].setStartpreis(1.99);
        auctiondummy[0].setTimeForAuction(datum);
        auctiondummy[1].setTimeForAuction(datum);
        auctiondummy[2].setTimeForAuction(datum);
        auctiondummy[3].setTimeForAuction(datum);
        auctiondummy[4].setTimeForAuction(datum);
        auctiondummy[0].setMinadd(0.5);
        auctiondummy[1].setMinadd(1.5);
        auctiondummy[2].setMinadd(1.0);
        auctiondummy[3].setMinadd(2.2);
        auctiondummy[4].setMinadd(3.6);
        auctiondummy[0].setAuctionDescription("Testbeschreibung 1");
        auctiondummy[1].setAuctionDescription("Testbeschreibung 2");
        auctiondummy[2].setAuctionDescription("Testbeschreibung 3");
        auctiondummy[3].setAuctionDescription("Testbeschreibung 4");
        auctiondummy[4].setAuctionDescription("Testbeschreibung 5");
        itemdummy[1].setBuyingUser(dummykaufer);
        itemdummy[3].setBuyingUser(dummykaufer);
        itemdummy[4].setBuyingUser(dummykaufer);
    }


    public static ObjectPasser getInstance(){
        if(op==null){
            op= new ObjectPasser();
        }
        return op;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public Item getI() {
        return i;
    }

    public void setI(Item i) {
        this.i = i;
    }

    public Auction getA() {
        return a;
    }

    public void setA(Auction a) {
        this.a = a;
    }


}

package Server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientHandler extends Thread {

    private Package object;

    private Socket CHS = null;

    //1---Konstruktor
        //Die Verbindung wird durch die Server Klasse aufgebaut

    public ClientHandler(Socket s) throws IOException {

        //Verbindung zum Socket
        this.CHS = s;
    }


    //2---run Methode um mit start des Threads umzugehen
    public void run()
    {

        try {
            ObjectInputStream in;
            ObjectOutputStream out;
            Package tosend = new Package(0, null, null, null , "Antwort vom server");
            Package received;

            while(true)
            {
                System.out.println("Server läuft");
                int caseint = 0;
                //Test Sysout
                System.out.print("ha");
                in = new ObjectInputStream(CHS.getInputStream());
                //hier muss noch abgefragt werden und die Variable in caseint geschrieben werden
                received=(Package)in.readObject();
                System.out.println("kunde sagt:" + received.packagenumber);
                caseint = received.packagenumber;

                switch(caseint)
                {
                    //speicher das Objekt
                    case 1:
                        System.out.println("Writing/Safe  operation on SQL Database ready!");
                        if(received.objectforpackage != null){
                            System.out.println("Objekt für Package ist nicht null");
                            saveObject(received.getObjectoforpackage());
                        }
                        else
                        {
                            System.out.println("Objectforpackage war null");
                        }
                        break;
                    //lese das objekt
                    case 2:
                        System.out.println("Fetching an Object from SQL Database");
                        if(received.objectforpackage != null)
                        {
                            System.out.println("Objekt für Package is nicht null");
                            fetchObject(received.getObjectoforpackage() , received.searchparameter);

                        }
                        break;


                    //Update ein Objekt
                    case 3:
                        System.out.println("Updating an Object on SQL Database");
                        if(received.objectforpackage != null)
                        {
                            System.out.println("Object für package ist nicht null");
                            //Auf int gecasted um an die ID zu kommen.
                            updateObject(received, (int) received.searchparameter);
                        }
                        break;
                    //Versende die E-Mail an einen Nutzer
                    case 4:
                        System.out.println("Versede E-Mail an Nutzer");
                        if(received.objectforpackage != null)
                        {
                            sendMailToUsers((User) received.objectforpackage);
                        }
                        break;
                    }
                out = new ObjectOutputStream(CHS.getOutputStream());
                out.writeObject(tosend);
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            CHS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    HibernateDatenbankobjekt Konfigurieren und Updaten beim Hinzufügen eines neuen Datensatzes
    diese Methode kann aufgerufen werden, nachdem ein Objekt übergeben wurde um dieses Objekt zu speichern
    **/
    public void saveObject(Object o)
    {
        Configuration c;  //Konfigurationsobjekt erstellen Hier wird die Verbindung zur SQL Datenbank aufgebaut
        c = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(o.getClass());

        SessionFactory sf = c.buildSessionFactory();//Über Konfigurationsobjekt eine Sf Bauen
        Session sesch = sf.openSession();   //session über sf öffnen
        Transaction tx = sesch.beginTransaction();
        sesch.save(o); //objekt speichern
        //transaktion abgeben
        tx.commit();
        sesch.close();
        System.out.println("UPDATED: " + o.toString());
    }

    public static Object fetchObject(Object o, Object searchfor) throws InterruptedException {

        Object fetched;
        //Konfigurationsobjekt erstellen Hier wird die Verbindung zur SQL Datenbank aufgebaut
        Configuration f= new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(o.getClass());
        //Über Konfigurationsobjekt eine Sf Bauen
        SessionFactory sf = f.buildSessionFactory();
        //session über sf öffnen
        Session sesch = sf.openSession();

        Transaction tx = sesch.beginTransaction();
        //schreibt ein Objekt auf
        //id muss als identifier gegeben sein, in der ersten Iteration kann bspw. bei User der Nutzername alsIdentifier gegeben sein
        fetched = sesch.byNaturalId((String) searchfor);
                //get(o.getClass().getName(), searchfor);
        //transaktion abgeben
        tx.commit();
        System.out.println("UPDATED: " + o.toString());
        sesch.close();
        return fetched;
    }

    public void updateObject(Object object, int id )
    {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();
        object = session.load(object.getClass(), id);
        session.update(object);
        tx.commit();
        session.close();
    }

    public static void sendMailToUsers(User user)
    {
        String username = "sepgruppeo@gmail.com";
        String password = "SEPGruppeOWS2020!";

        MailSender sender = new MailSender();
        sender.login("smtp.gmail.com", "465", username, password);

        System.out.println("DAS IST DIE USERMAIL and die gesendet wird FFQ: " + user.getUsermail());
        String recieverAdress = user.getUsermail();

        try {

            sender.send("sepgruppeo@gmail.com", "GruppeO", recieverAdress, "Infos zur Bestellung",
                    "Überall dieselbe alte Leier.\r\n\r\nDas Layout ist fertig, der Text lässt auf sich warten. "
                            + "Damit das Layout nun nicht nackt im Raume steht und sich klein und leer vorkommt, "
                            + "springe ich ein: der Blindtext. Genau zu diesem Zwecke erschaffen, immer im Schatten "
                            + "meines großen Bruders »Lorem Ipsum«, freue ich mich jedes Mal, wenn Sie ein paar Zeilen "
                            + "lesen. Denn esse est percipi - Sein ist wahrgenommen werden.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    //test
    public Object getPackage() {
        return object;
    }
    //test
    public void setPackage(Package pack) {
        this.object = pack;
    }

}

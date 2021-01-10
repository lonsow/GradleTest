package Client;

import Server.Item;
import Server.User;
import Server.Package;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//SER MUSS STATIC IN DER MAIN DEKLARIERT WERDEN UND ALS THREAD
public class SEPClient implements Runnable {
    public static User currentsession;
    public static Item produkt;             //Hilfsvariable zum Produkte anzeigen
    private static SEPClient client;
    private volatile Package tosend;
    private Package output;


    private SEPClient(){
    }

    public static SEPClient getInstance(){
        if(client==null){
            client= new SEPClient();
        }
        return client;
    }






    public void run(){
        String HostName = "127.0.0.1";
        int pN=1234;
        Socket S;

        //sollte anforderung fürs Login sein
        
        ObjectOutputStream out;
        ObjectInputStream in;
        try

        {
            S = new Socket(HostName, pN);
            while(true)
            {
                //damit das package nur einmal versendet wird
                if(tosend!=null) {
                 out = new ObjectOutputStream(S.getOutputStream());
                    out.writeObject(tosend);
                    tosend = null;
                    in = new ObjectInputStream(S.getInputStream());
                    Package received = (Package) in.readObject();
                    output=received;
                    System.out.println(received.Text);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Alarm!!!!!");
        }
    }


    public Object getServerResult(){return output.getObjectoforpackage();}
    public void requestObjectOperation(int packagenumber, Object objectoforpackage ,Object searchparameter, Object objecttobeadded ,String text)
    {
            tosend=new Package(packagenumber, objectoforpackage , searchparameter, objecttobeadded , text);
    }

    public void requestsave(Object o){
        tosend=new Package(1,o,null,null,"sende speicheranfrage");
    }

    public void requestfetch(Object targetedtable, Object searchparameter){
        tosend=new Package(2,targetedtable,searchparameter,null,"sende suchanfrage");
    }

    public void requestupdate(Object tobeupdated, int id){
        tosend=new Package(3,tobeupdated,id,null,"sende Updateanfrage");
    }
    public void requestemail(User receiver){
        tosend= new Package(4,receiver,null,null,"sende emailanfrage");
    }
}

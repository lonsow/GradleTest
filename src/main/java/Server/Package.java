package Server;

import java.io.Serializable;

public class Package implements Serializable {

    //Für die Behandlung des Objektes in den switch Cases des Clienthandlers
    int packagenumber ;
    //Objekt, das in der Dat#enbank verarbeitet werden soll
    Object objectforpackage;
    //(Optional ansonsten mit Null)kann auch mit null instanziert werden
    Object objecttobeadded;
    public String Text;
    //parameter nachdem du entitäten in der DB suchst
    public Object searchparameter ;
    //klassenname des content objektes von dem package entspricht bei null "empty"
    public Package( int packagenumber, Object objectoforpackage ,Object searchparameter, Object objecttobeadded ,String text ){
        this.packagenumber = packagenumber;
        this.Text=text;
        this.searchparameter = searchparameter;
        this.objecttobeadded = objecttobeadded;
        this.objectforpackage =objectoforpackage;
    }
    public int getPackagenumber() {
        return packagenumber;
    }
    public void setPackagenumber(int packagenumber) {
        this.packagenumber = packagenumber;
    }
    public Object getObjectoforpackage() {
        return objectforpackage;
    }
    public void setObjectoforpackage(Object objectoforpackage) {
        this.objectforpackage = objectoforpackage;
    }
}


package Client;

import Server.Item;
import Server.User;

import java.io.BufferedReader;
import java.io.FileReader;

public class CSVReader {
    public Item[] read(String filepath, boolean isbook, User einsteller){
        try{
            BufferedReader reader= new BufferedReader(new FileReader(filepath));
            Item[] result= new Item[this.csvdateilaenge(filepath)-1];
            System.out.println(Integer.toString(this.csvdateilaenge(filepath)-1));
            reader.readLine();
            String line;
            int counter=0;
            while ((line = reader.readLine())!=null){

                String[] data =line.split(";");

                if(isbook==false) {
                    result[counter] = new Item(
                            data[0],
                            data[3],
                            Double.parseDouble(data[2]),
                            einsteller,
                            data[1],
                            1,
                            null);
                }else{
                    if(data.length>4)data[0]=data[0]+", "+data[4];
                    result[counter] = new Item(

                            data[0],
                            data[3],
                            Double.parseDouble(data[2]),
                            einsteller,
                            data[1],
                            1,
                            null);
                }
                System.out.println(result[counter].getItemname()+"    "+ String.valueOf(result[counter].getItemprize()));
                counter++;
            }
            return result;


        } catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }

    public int csvdateilaenge(String filepath){
        try {
            int result = 0;
            BufferedReader reader= new BufferedReader(new FileReader(filepath));
            while (reader.readLine()!=null){
            result++;
            }
            return result;
        }catch (Exception e){
            System.out.println("keine gültige Datei");
        }
        return 0;
    }

}


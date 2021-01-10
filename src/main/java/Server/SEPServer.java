package Server;
// A Java program for a Server
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SEPServer {
   int pN = 1234;
   ServerSocket SS = null;


   public void runServer() {
      try{
         SS=new ServerSocket(pN);
      } catch (IOException e) {
         e.printStackTrace();
      }
      while(true){
         try {
            Socket CS = SS.accept();
            new Thread(new ClientHandler(CS)).start();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}

package controladores;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLEsperaController implements Initializable {

  /**
   * Initializes the controller class.
   */
  private int id;
  private Socket socket;
  private String room;
  @Override
  public void initialize(URL url, ResourceBundle rb) {
   try {
      System.out.println("Intento");
      socket = IO.socket("http://localhost:7001");
       socket.connect();
      System.out.println("Conectado");
      socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

  @Override
  public void call(Object... args) {}

}).on("setRoom", new Emitter.Listener() {
        @Override
        public void call(Object... os) {
          Platform.runLater(()->{
          setRoom((String) os[0]);
          });
        }
      });
    } catch (URISyntaxException ex) {
      Logger.getLogger(FXMLEsperaController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }  

  public void setId(int id) {
    this.id = id;
  }
  
  public void setRoom(String room){
    System.out.println("Room: "+room);
    this.room=room;
  }
  
}

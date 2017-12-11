package controladores;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logica.Context;

/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLJuegoPrincipianteController implements Initializable {
@FXML
ImageView imgCampo00, imgCampo01, imgCampo02, imgCampo03, imgCampo04, imgCampo05, imgCampo06, imgCampo07;
/**
@FXML
ImageView imgCampo10, imgCampo11, imgCampo12, imgCampo13, imgCampo14, imgCampo15, imgCampo16, imgCampo17;
@FXML
ImageView imgCampo20, imgCampo21, imgCampo22, imgCampo23, imgCampo24, imgCampo25, imgCampo26, imgCampo27;
@FXML
ImageView imgCampo30, imgCampo31, imgCampo32, imgCampo33, imgCampo34, imgCampo35, imgCampo36, imgCampo37;
@FXML
ImageView imgCampo40, imgCampo41, imgCampo42, imgCampo43, imgCampo44, imgCampo45, imgCampo46, imgCampo47;
@FXML
ImageView imgCampo50, imgCampo51, imgCampo52, imgCampo53, imgCampo54, imgCampo55, imgCampo56, imgCampo57;
@FXML
ImageView imgCampo60, imgCampo61, imgCampo62, imgCampo63, imgCampo64, imgCampo65, imgCampo66, imgCampo67;
@FXML
ImageView imgCampo70, imgCampo71, imgCampo72, imgCampo73, imgCampo74, imgCampo75, imgCampo76, imgCampo77;
*/
@FXML
Rectangle rcaJuego;
private int id;
private Socket socket;
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
  id=Context.getInstance().currentPlayer().getIdJugador();
  socket=Context.getInstance().currentSocket();
  Image sumido=new Image("/resource/BS.png");
  Image alzado=new Image("/resource/AMORBA.png");
  Image uno=new Image("/resource/AMOR1.png");
  imgCampo00.setImage(alzado);imgCampo01.setImage(alzado);imgCampo02.setImage(alzado);imgCampo03.setImage(alzado);imgCampo04.setImage(alzado);imgCampo05.setImage(alzado);imgCampo06.setImage(alzado);imgCampo07.setImage(alzado);
  
  socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

  @Override
  public void call(Object... args) {
  System.out.println("Conectado");
  
  }

}).on("prueba", new Emitter.Listener() {
    @Override
    public void call(Object... os) {
      System.out.println("Prueba correcta"+os[0]);
    }
  });
  socket.emit("damePrueba","ya");
  }  
  
}

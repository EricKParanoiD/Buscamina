package controladores;
import controller.ConfiguracionesJpaController;
import controller.JugadorJpaController;
import entidades.Configuraciones;
import entidades.Jugador;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logica.Context;

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
  @FXML
  ImageView imgCargando;
  @FXML
  Button btnCancelar;
  @FXML
  Label lblEspera;
  @FXML
  Label lblEmpezando;
  @FXML
  ListView lsvJugadores;
  private ObservableList<String> nombres=FXCollections.observableArrayList();
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    lsvJugadores.setItems(nombres);
    id=Context.getInstance().currentPlayer().getIdJugador();
    lblEmpezando.setVisible(false);
    Image cargando=new Image("/resource/cargando.gif");
    imgCargando.setImage(cargando);
   try {
      System.out.println("Intento");
      socket = IO.socket("http://localhost:7001");
      socket.connect();
      socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

  @Override
  public void call(Object... args) {
  System.out.println("Conectado");
  envioInfo();
  }

}).on("setRoom", new Emitter.Listener() {
        @Override
        public void call(Object... os) {
          Platform.runLater(()->{
          setRoom((String) os[0]);
          });
        }
      }).on("actualizaNombre", new Emitter.Listener() {
        @Override
        public void call(Object... os) {
          Platform.runLater(()->{
            for (int i = 0; i < os.length; i++) {
              String nuevo =(String) os[i];
              nombres.add(nuevo);
            }
            
          });
        }
      });
    } catch (URISyntaxException ex) {
      Logger.getLogger(FXMLEsperaController.class.getName()).log(Level.SEVERE, null, ex);
    }
   

  }  

  public void envioInfo(){
  JugadorJpaController jugadorjpa=new JugadorJpaController();
  Jugador jugador=jugadorjpa.findJugador(id);
  ConfiguracionesJpaController configuracionesjpa=new ConfiguracionesJpaController(); //Controlador de configuraciones jpa
  Configuraciones configuracion=configuracionesjpa.findConfiguraciones(id); //busca las configuraciones por id
  String nombre=jugador.getNombreJugador();
  int dificultad=configuracion.getDificultad();
  socket.emit("infoJugador", id, nombre, dificultad);
  nombres.add(nombre);
  }
  public void setId(int id) {
    this.id = id;
  }
  
  public void setRoom(String room){
    System.out.println("Room: "+room);
    this.room=room;
  }
  
}

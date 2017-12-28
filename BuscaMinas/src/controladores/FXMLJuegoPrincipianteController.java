package controladores;

import controller.ConfiguracionesJpaController;
import controller.JugadorJpaController;
import entidades.Configuraciones;
import entidades.Jugador;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logica.Context;
import logica.Tablero;

/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLJuegoPrincipianteController implements Initializable {

  @FXML
  ImageView imgCampo00, imgCampo01, imgCampo02, imgCampo03, imgCampo04, imgCampo05, imgCampo06, imgCampo07;
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

  @FXML
  Rectangle rcaJuego, rcaJugador;
  @FXML
  Label lblJugador;
  @FXML
  ListView lsvJugadores;
  @FXML
  GridPane grpCampo;
  private int id;
  private String room;
  private Socket socket;
  private final ObservableList<String> nombres = FXCollections.observableArrayList();
  private String turno="1";
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    lsvJugadores.setItems(nombres);
    id = Context.getInstance().currentPlayer().getIdJugador();
    room = Context.getInstance().currentRoom();
    socket = Context.getInstance().currentSocket();
    Image sumido = new Image("/resource/BS.png");
    Image alzado = new Image("/resource/AMORBA.png");
    Image uno = new Image("/resource/AMOR1.png");
    Tablero tab = new Tablero(10, 8);
    inicializaCampos(alzado);
    //grpCampo.seton;
    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

      @Override
      public void call(Object... args) {
        System.out.println("Conectado");

      }

    }).on("actualizaNombre", new Emitter.Listener() {
      @Override
      public void call(Object... os) {
        Platform.runLater(() -> {
          System.out.println("Actualizare nombre");
          String nuevo = (String) os[0];
          System.out.println("Nombre: " + nuevo);
          if (!nombres.contains(nuevo)) {
            nombres.add(nuevo);
          }

        });
      }
    }).on("setColor", new Emitter.Listener() {
      @Override
      public void call(Object... os) {
        Platform.runLater(() -> {
          System.out.println("SetColor");
          rcaJuego.setFill(cadenaAColor((String) os[0]));
        });

      }
    }).on("iniciarColor", new Emitter.Listener() {
      @Override
      public void call(Object... os) {
        Platform.runLater(() -> {
          System.out.println("Iniciar Color");
          rcaJugador.setFill(cadenaAColor((String) os[0]));
        });
      }
    });
    
    ConfiguracionesJpaController configuracionesjpa = new ConfiguracionesJpaController(); //Controlador de configuraciones jpa
    Configuraciones configuracion = configuracionesjpa.findConfiguraciones(id); //busca las configuraciones por id
    int dificultad = configuracion.getDificultad(); //Dificultad obtenida
    JugadorJpaController jugadorjpa = new JugadorJpaController();
    Jugador jugador = jugadorjpa.findJugador(id);
    String nombreJugador = jugador.getNombreJugador();
    lblJugador.setText(nombreJugador);
    socket.emit("solicitaInfo", room, dificultad, nombreJugador);
  }

  private void inicializaCampos(Image alzado) {
    imgCampo00.setImage(alzado);
    imgCampo01.setImage(alzado);
    imgCampo02.setImage(alzado);
    imgCampo03.setImage(alzado);
    imgCampo04.setImage(alzado);
    imgCampo05.setImage(alzado);
    imgCampo06.setImage(alzado);
    imgCampo07.setImage(alzado);
    imgCampo10.setImage(alzado);
    imgCampo11.setImage(alzado);
    imgCampo12.setImage(alzado);
    imgCampo13.setImage(alzado);
    imgCampo14.setImage(alzado);
    imgCampo15.setImage(alzado);
    imgCampo16.setImage(alzado);
    imgCampo17.setImage(alzado);
    imgCampo20.setImage(alzado);
    imgCampo21.setImage(alzado);
    imgCampo22.setImage(alzado);
    imgCampo23.setImage(alzado);
    imgCampo24.setImage(alzado);
    imgCampo25.setImage(alzado);
    imgCampo26.setImage(alzado);
    imgCampo27.setImage(alzado);
    imgCampo30.setImage(alzado);
    imgCampo31.setImage(alzado);
    imgCampo32.setImage(alzado);
    imgCampo33.setImage(alzado);
    imgCampo34.setImage(alzado);
    imgCampo35.setImage(alzado);
    imgCampo36.setImage(alzado);
    imgCampo37.setImage(alzado);
    imgCampo40.setImage(alzado);
    imgCampo41.setImage(alzado);
    imgCampo42.setImage(alzado);
    imgCampo43.setImage(alzado);
    imgCampo44.setImage(alzado);
    imgCampo45.setImage(alzado);
    imgCampo46.setImage(alzado);
    imgCampo47.setImage(alzado);
    imgCampo50.setImage(alzado);
    imgCampo51.setImage(alzado);
    imgCampo52.setImage(alzado);
    imgCampo53.setImage(alzado);
    imgCampo54.setImage(alzado);
    imgCampo55.setImage(alzado);
    imgCampo56.setImage(alzado);
    imgCampo57.setImage(alzado);
    imgCampo60.setImage(alzado);
    imgCampo61.setImage(alzado);
    imgCampo62.setImage(alzado);
    imgCampo63.setImage(alzado);
    imgCampo64.setImage(alzado);
    imgCampo65.setImage(alzado);
    imgCampo66.setImage(alzado);
    imgCampo67.setImage(alzado);
    imgCampo70.setImage(alzado);
    imgCampo71.setImage(alzado);
    imgCampo72.setImage(alzado);
    imgCampo73.setImage(alzado);
    imgCampo74.setImage(alzado);
    imgCampo75.setImage(alzado);
    imgCampo76.setImage(alzado);
    imgCampo77.setImage(alzado);
  }

  public Color cadenaAColor(String color) {
    switch (color) {
      case "Rosa":
        return Color.DEEPPINK;
      case "Rojo":
        return Color.RED;
      case "Naranja":
        return Color.ORANGE;
      case "Amarillo":
        return Color.YELLOW;
      case "Verde":
        return Color.GREEN;
      case "Morado":
        return Color.PURPLE;
      case "Azul":
        return Color.BLUE;
      case "Gris":
        return Color.GREY;
      default:
        return Color.CYAN;
    }
  }

  //private static class EventHandler<javafx.scene.input.MouseEvent> implements EventHandler<MouseEvent> {

    //public EventHandler<javafx.scene.input.MouseEvent>() {
    //}

    //@Override
    //public void handle(MouseEvent event) {
    //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
  //}
}

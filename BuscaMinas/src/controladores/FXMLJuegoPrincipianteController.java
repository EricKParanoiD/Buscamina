package controladores;

import controller.ConfiguracionesJpaController;
import controller.JugadorJpaController;
import entidades.Configuraciones;
import entidades.Jugador;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logica.Campo;
import logica.Context;
import logica.Coordenadas;
import logica.Tablero;

/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLJuegoPrincipianteController implements Initializable {


  @FXML
  Rectangle rcaJuego, rcaJugador;
  @FXML
  Label lblJugador,lblPuntaje,lblMinas,lblMaximo;
  @FXML
  ListView lsvJugadores;
  @FXML
  GridPane grpCampo;
  private String nombreJugador;
  private int id;
  private String room;
  private Socket socket;
  private String turnoActual="nadie";
  private final ObservableList<String> nombres = FXCollections.observableArrayList();
  private Campo[][] campos=new Campo[8][8];
  private Tablero tab = new Tablero(10, 8);
  private Image sumido = new Image("/resource/BS.png");
  private Image alzado = new Image("/resource/AMORBA.png");
  private Image uno = new Image("/resource/AMOR1.png");
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    lsvJugadores.setItems(nombres);
    //id = Context.getInstance().currentPlayer().getIdJugador();
    //room = Context.getInstance().currentRoom();
    socket = Context.getInstance().currentSocket();
    
    
    inicializaCampos(alzado);
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
    
    
/**    
    ConfiguracionesJpaController configuracionesjpa = new ConfiguracionesJpaController(); //Controlador de configuraciones jpa
    Configuraciones configuracion = configuracionesjpa.findConfiguraciones(id); //busca las configuraciones por id
    int dificultad = configuracion.getDificultad(); //Dificultad obtenida
    JugadorJpaController jugadorjpa = new JugadorJpaController();
    Jugador jugador = jugadorjpa.findJugador(id);
    String nombreJugador = jugador.getNombreJugador();
    lblJugador.setText(nombreJugador);
    socket.emit("solicitaInfo", room, dificultad, nombreJugador);
  */
    
  }
  public void clicCampo(int x, int y){
    
    int arrAux[][]=tab.getArrCasilla();
    if(arrAux[x][y]!=9){
      lblPuntaje.setText(Integer.toString(Integer.valueOf(lblPuntaje.getText())+10));
    }
    ArrayList<Coordenadas> arrAlrededor=new ArrayList<>();
    tab.limpiarVaciosAlrededor(arrAlrededor, x, y);
    for (int i = 0; i < arrAlrededor.size(); i++) {
      int arrX=arrAlrededor.get(i).getCoordenadaX();
      int arrY=arrAlrededor.get(i).getCoordenadaY();
      campos[arrX][arrY].setDisable(true);
      if(arrAux[arrX][arrY]!=9){
        if(arrAux[arrX][arrY]!=0){
          campos[arrX][arrY].setText(Integer.toString(arrAux[arrX][arrY]));
        }
      }else{
        campos[arrX][arrY].setText("*");
      }
      
    }
  }
  private void inicializaCampos(Image alzado) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        campos[i][j]=new Campo(new Coordenadas(i, j));
        campos[i][j].setPrefSize(25, 25);
        ImageView aux=new ImageView(alzado);
        aux.setFitHeight(24);
        aux.setFitWidth(24);
        //campos[i][j].setGraphic(aux);
        grpCampo.setMaxSize(200, 200);
        grpCampo.add(campos[i][j], i, j);
        final int auxX=i;
        final int auxY=j;
        campos[i][j].setOnAction(e -> clicCampo(auxX, auxY));
                } 
    }    
  }

  public void setTurnoActual(String turnoActual) {
    this.turnoActual = turnoActual;
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

}

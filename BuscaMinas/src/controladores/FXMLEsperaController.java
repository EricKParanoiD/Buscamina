package controladores;

import controller.ConfiguracionesJpaController;
import controller.JugadorJpaController;
import entidades.Configuraciones;
import entidades.Jugador;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
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
  @FXML
  ProgressBar pgbRegresiva;
  private ObservableList<String> nombres = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    lsvJugadores.setItems(nombres);
    pgbRegresiva.setVisible(false);
    id = Context.getInstance().currentPlayer().getIdJugador();
    lblEmpezando.setVisible(false);
    Image cargando = new Image("/resource/cargando.gif");
    imgCargando.setImage(cargando);
    Stage planillaStage = (Stage) btnCancelar.getScene().getWindow();
    planillaStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        JugadorJpaController jugadorjpa = new JugadorJpaController();
        Jugador jugador = jugadorjpa.findJugador(id);
        ConfiguracionesJpaController configuracionesjpa = new ConfiguracionesJpaController(); //Controlador de configuraciones jpa
        Configuraciones configuracion = configuracionesjpa.findConfiguraciones(id); //busca las configuraciones por id
        String nombre = jugador.getNombreJugador();
        int dificultad = configuracion.getDificultad();
        socket.emit("cerrar", room, nombre,dificultad);
        socket.close();
      }
    });
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
          Platform.runLater(() -> {
            setRoom((String) os[0]);
          });
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
      }).on("enUso", new Emitter.Listener() {
        @Override
        public void call(Object... os) {
          Platform.runLater(() -> {
            Alerta alerta = new Alerta();
            alerta.alertaInfo("Cuenta en uso", "La cuenta esta en uso", "No puedes iniciar juego debido a que tu cuenta ya esta en uso por otro equipo");
            socket.close();
            menu();
          });
        }
      }).on("comenzarPartida", new Emitter.Listener() {
        @Override
        public void call(Object... os) {
          Platform.runLater(() -> {
            imgCargando.setVisible(false);
            pgbRegresiva.setVisible(true);
            pgbRegresiva.setDisable(true);
            ResourceBundle rb = ResourceBundle.getBundle("resource.Bundle");
            String cadena = rb.getString("starting");
            lblEspera.setText(cadena);
          });

        }
      }).on("inicioPartida", new Emitter.Listener() {
        @Override
        public void call(Object... os) {
          Context.getInstance().setSocket(socket);
          juegoPrincipiante();

        }
      });
    } catch (URISyntaxException ex) {
      Logger.getLogger(FXMLEsperaController.class.getName()).log(Level.SEVERE, null, ex);
    }

    btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        socket.close();
        menu();
      }
    });
  }

  public void envioInfo() {
    JugadorJpaController jugadorjpa = new JugadorJpaController();
    Jugador jugador = jugadorjpa.findJugador(id);
    ConfiguracionesJpaController configuracionesjpa = new ConfiguracionesJpaController(); //Controlador de configuraciones jpa
    Configuraciones configuracion = configuracionesjpa.findConfiguraciones(id); //busca las configuraciones por id
    String nombre = jugador.getNombreJugador();
    int dificultad = configuracion.getDificultad();
    socket.emit("infoJugador", id, nombre, dificultad);
  }

  /**
   * Metodo para la invocacion del menu
   */
  private void menu() {
    try {
      Stage planillaStage = (Stage) btnCancelar.getScene().getWindow();  //Se obtiene el stage del boton crear
      ResourceBundle rb = ResourceBundle.getBundle("resource.Bundle");
      Parent root = FXMLLoader.load(getClass().getResource("/pantallas/FXMLMen.fxml"), rb); //Se obtiene el recurso FXML y se abre su stream      
      planillaStage.setScene(new Scene(root)); //Se pone la escena en el stage
      planillaStage.show(); //Se muestra
    } catch (IOException ex) {
      Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  private void juegoPrincipiante() {
    try {
      Stage planillaStage = (Stage) btnCancelar.getScene().getWindow();  //Se obtiene el stage del boton crear
      ResourceBundle rb = ResourceBundle.getBundle("resource.Bundle");
      Parent root = FXMLLoader.load(getClass().getResource("/pantallas/FXMLJuegoPrincipiante.fxml"), rb); //Se obtiene el recurso FXML y se abre su stream      
      planillaStage.setScene(new Scene(root)); //Se pone la escena en el stage
      planillaStage.show(); //Se muestra
    } catch (IOException ex) {
      Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public void setId(int id) {
    this.id = id;
  }

  public void setRoom(String room) {
    System.out.println("Room: " + room);
    this.room = room;
  }

}

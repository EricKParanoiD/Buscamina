package controladores;

import controller.JugadorJpaController;
import entidades.Jugador;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logica.Encriptar;

/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLInicioSesionController implements Initializable {
//Inicializacion de variables con FXML

  @FXML
  Button btnIniciar;
  @FXML
  Button btnCrear;
  @FXML
  TextField txfUsuario;
  @FXML
  PasswordField psfContrasena;
  JugadorJpaController jugadorjpa = new JugadorJpaController();//Variable de controlador JPA
  List<Jugador> jugadores = jugadorjpa.findJugadorEntities(); //Lista para guardar entidades de la base de datos
  Encriptar encript = new Encriptar(); //Variable para encriptacion
  Alerta alerta = new Alerta(); //Variable para la creacion de alertas
  private int id;
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    /**
     * Acciones a ejecutar cuando sea pulsado el boton Iniciar (Iniciar sesion) dependiendo el
     * resultado del metodo verificar
     */
    btnIniciar.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //switch dependiendo de la verificacion
        switch (verificar()) {
          case 0:
            menu(); //0 es para inicio de sesion correcto 
            break;
          case 1:
            //alerta de informacion de contraseña incorrecta
            alerta.alertaInfo("Acceso denegado", "Contraseña incorrecta", "Tu contraseña es incorrecta, vuelve a intentar");
            break;
          case 2:
            //alerta de usuario no encontrado
            alerta.alertaInfo("Acceso denegado", "Usuario no encontrado", "El usuario con el que intentas ingresar no existe");
            break;
          default:
            //Comportamiento por default
            alerta.alertaInfo("Acceso denegado", "Revisa los campos", "Ha habido algun error, revisa los campos");
        }
      }
    });

    /**
     * Accion del boton Crear que lleva a crear un nuevo usuario
     */
    btnCrear.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        crearCuenta();
      }
    });
  }

  /**
   * Metodo para verificar los datos introducidos en los campos de jugador y contraseña
   *
   * @return regresa un int que indica la comprobacion de los campos
   */
  public int verificar() {
    jugadores = jugadorjpa.findJugadorEntities(); //Actualizacion de entidades en la base de datos
    //for para comprobar uno a uno los datos de las entidades
    for (int i = 0; i < jugadores.size(); i++) {
      String nombre = jugadores.get(i).getNombreJugador(); //variable auxiliar para el nombre del jugador
      if (nombre.equals(txfUsuario.getText())) {
        String contrasena = jugadores.get(i).getContrasena(); //variable auxiliar para la contraseña
        String contrasenaUsuario = encript.convertirSHA256(psfContrasena.getText()); //contraseña introducida hasheada
        if (contrasena.equals(contrasenaUsuario)) {
          setId(jugadores.get(i).getIdJugador()); //Id del jugador guardado para enviarlo posteriormente
          return 0; //0 para nombre y contraseña correctas
        } else {
          return 1; //1 para contraseña incorrecta
        }
      }

    }
    return 2; //2 si el nombre de jugador no es encontrado
  }

  /**
   * Metodo para la invocacion del menu
   */
  private void menu() {
    try {
      Stage planillaStage = (Stage) btnCrear.getScene().getWindow();  //Se obtiene el stage del boton crear
      FXMLLoader loader = new FXMLLoader(); //instancia de loader
      AnchorPane root = (AnchorPane) loader.load(getClass().getResource("/pantallas/FXMLMenu.fxml").openStream()); //Se obtiene el recurso FXML y se abre su stream
      FXMLMenuController cMenu = (FXMLMenuController) loader.getController(); //Se obtiene el controlador con el loader y se castea
      //Se usa el setId para pasar el parametro
      cMenu.setId(id);
      planillaStage.setScene(new Scene(root)); //Se pone la escena en el stage
      planillaStage.show(); //Se muestra
    } catch (IOException ex) {
      Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  /**
   * Metodo para ir a la escena de crear cuenta
   */
  public void crearCuenta() {
    try {
      Stage stage = (Stage) btnIniciar.getScene().getWindow();
      stage.close();

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pantallas/FXMLCrearCuenta.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (IOException ex) {
      Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public void setId(int id) {
    this.id = id;
  }

}

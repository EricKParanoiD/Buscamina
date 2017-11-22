package controladores;

import controller.JugadorJpaController;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import entidades.Jugador;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.ConfiguracionesJpaController;
import controller.PartidasganadasJpaController;
import controller.exceptions.PreexistingEntityException;
import entidades.Configuraciones;
import entidades.Partidasganadas;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLCrearCuentaController implements Initializable {

  /**
   * Initializes the controller class.
   */
  @FXML
  TextField txfNombreJugador;
  @FXML
  PasswordField psfContrasena;
  @FXML
  PasswordField psfRepita;
  @FXML
  ComboBox cbbGenero;
  @FXML
  DatePicker dtpFecha;
  @FXML
  Button btnRegistrarse;
  @FXML
  Button btnRegresar;
  Alerta alerta = new Alerta(); //Alerta para uso de alertas en la clase
  JugadorJpaController jugadorjpa = new JugadorJpaController(); //Controlador de entidades de jugador

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    dtpFecha.setValue(LocalDate.now()); //Inicializacion de fecha en hoy
    ArrayList<String> listaGenero = new ArrayList<>(); //ArrayList para guardar las opciones de genero
    listaGenero.add("Masculino");
    listaGenero.add("Femenino");
    ObservableList<String> listaObserv = FXCollections.observableArrayList(listaGenero); //Cambio de Arraylist a ObservableList para visualizarlo
    cbbGenero.setItems(listaObserv); //Items de comboBox colocados
    cbbGenero.getSelectionModel().select(0); //Item seleccionado por defecto
    btnRegistrarse.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //If que checa si los campos cumplen con los requisitos para la creacion
        if (verificarCampos()) {
          Date fecha = java.sql.Date.valueOf(dtpFecha.getValue()); //Variable para pasar la fecha a tipo compatible
          Jugador jugador = new Jugador(3, txfNombreJugador.getText(), psfContrasena.getText(), getGenero(), fecha); //Objeto a crear en la base de datos
          jugadorjpa.create(jugador); //Creacion de objeto en la base de datos
          int id=buscarId(txfNombreJugador.getText()); //Id del jugador creado
          if (id!=-1) {
            //Creacion de entidades por defecto en la base de datos
            ConfiguracionesJpaController configuracionesjpa=new ConfiguracionesJpaController();
            Configuraciones configuraciones=new Configuraciones(id, 1, 100, 7001, "localhost");
            PartidasganadasJpaController partidasjpa=new PartidasganadasJpaController();
            Partidasganadas partidas=new Partidasganadas(id, 0, 0, 0);
            try {
              partidasjpa.create(partidas);
              configuracionesjpa.create(configuraciones);
            } catch (PreexistingEntityException ex) {
              Logger.getLogger(FXMLCrearCuentaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
              Logger.getLogger(FXMLCrearCuentaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Alerta de jugador creado
            alerta.alertaOk("Usuario creado", null, "El usuario ha sido registrado con exito");
            inicioSesion(); //metodo hacia el inicio de sesion
          }

        }
      }
    });
  
  btnRegresar.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        inicioSesion();
      }
    });
  }
/**
 * Metodo hacia el inicio de sesion
 */
  public void inicioSesion() {
    try {
      //Recupera el stage de el boton btnRegistrarse
      Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
      //Cierra el stage
      stage.close();
      
      //Carga la nueva interfaz
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pantallas/FXMLInicioSesion.fxml"));
      //Lo carga en la escena
      Parent root1 = (Parent) fxmlLoader.load();
      //Pone la escena en el stage y lo muestra
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (IOException ex) {
      Logger.getLogger(FXMLCrearCuentaController.class.getName()).log(Level.SEVERE, null, ex);
    }
    

  }
/**
 * Verificar todos los campos para asegurar que sean datos validos, muestra alertas si no lo son
 * @return regresa si son validos o no, un boolean
 */
  public boolean verificarCampos() {
    boolean nombre = !txfNombreJugador.getText().isEmpty(); //verifica que el campo no este vacio
    boolean contrasena = !psfContrasena.getText().isEmpty(); //verifica que el campo no este vacio
    boolean repita = !psfRepita.getText().isEmpty();  //verifica que el campo no este vacio
    boolean genero = !cbbGenero.getValue().toString().isEmpty(); //verifica que el campo no este vacio
    boolean fecha = dtpFecha.getValue().isBefore(LocalDate.now()); //verifica que la fecha del campo sea anterior al mismo dia
    boolean coinciden = psfContrasena.getText().equals(psfRepita.getText()); //verifica que las contraseñas coincidan
    //If de campos vacios, alerta de campos vacios si no
    if (nombre && contrasena && repita && genero) {
      if(comprobarNombre(txfNombreJugador.getText())){
     //If de fecha anterior a dia actual, alerta de fecha si no
      if (fecha) {
        //If de contraseñas coinciden, alerta de contraseña si no
        if (coinciden) {
          return true; //True si todo esta bien
        } else {
          alerta.alertaInfo("Contraseña", null, "Revisa tus contraseñas, no coinciden");
        }
      } else {
        alerta.alertaInfo("Fecha invalida", "Fecha de nacimiento invalida", "Tu fecha de nacimiento no puede ser hoy o despues");
      }
    }else{
        alerta.alertaInfo("Jugador invalido", "Nombre de jugador ocupado", "El nombre de jugador ya esta ocupado");
      }
    }else {
      alerta.alertaInfo("Campos vacios", "Revisa los campos", "Algunos campos estan vacios, llenalos e intenta de nuevo");
    }
      
    return false; //False si algo esta mal
  }

  /**
   * Metodo que busca por nombre en la base de datos
   * @param nom nombre del jugador a buscar
   * @return regresa el id del jugador o -1 si no lo encuentra
   */
  public int buscarId(String nom) {
    List<Jugador> jugadores = jugadorjpa.findJugadorEntities(); //Actualizacion de entidades en la base de datos
    //for para comprobar uno a uno los datos de las entidades
    for (int i = 0; i < jugadores.size(); i++) {
      String nombre = jugadores.get(i).getNombreJugador(); //variable auxiliar para el nombre del jugador
      if (nombre.equals(nom)) {
        return jugadores.get(i).getIdJugador(); //Regresa el id del jugador cuando lo encuentra
      }

    }
    return -1; //-1 si el nombre de jugador no es encontrado
  }

  /**
   * Convierte valor de cbbGenero a booleano
   * @return True para masculino, false para femenino
   */
  public boolean getGenero() {
    String genero = cbbGenero.getValue().toString();
    return genero.equals("Masculino");
  }
  
  /**
   * Comprueba si el nombre ya existe o no
   * @param nom nombre del jugador
   * @return true si esta disponible, false si no
   */
  public boolean comprobarNombre(String nom){
    List<Jugador> jugadores = jugadorjpa.findJugadorEntities(); //Actualizacion de entidades en la base de datos
    //for para comprobar uno a uno los datos de las entidades
    for (int i = 0; i < jugadores.size(); i++) {
      String nombre = jugadores.get(i).getNombreJugador(); //variable auxiliar para el nombre del jugador
      if (nombre.equals(nom)) {
        return false; //Regresa false si encuentra el nombre del jugador
      }
    }
    return true;
  }

}

package controladores;

import controller.ConfiguracionesJpaController;
import controller.exceptions.PreexistingEntityException;
import entidades.Configuraciones;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logica.Context;

/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLOpcionesController implements Initializable {

  /**
   * Initializes the controller class.
   */
  @FXML
  ComboBox cbbDificultad;
  @FXML
  Slider sldVolumen;
  @FXML
  TextField txfPuerto;
  @FXML
  TextField txfIp;
  @FXML
  Button btnGuardar;
  @FXML
  Button btnSalir;
  
  private int id; //variable para id del jugador
  Alerta alerta=new Alerta(); //alerta para la creacion de alertas en esta clase
  ConfiguracionesJpaController configuracionesjpa=new ConfiguracionesJpaController(); //Controlador para configuraciones
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ArrayList<String> listaDificultad = new ArrayList<>(); //ArrayList para guardar las opciones de Dificultad
    listaDificultad.add("Principiante");
    listaDificultad.add("Intermedio");
    listaDificultad.add("Dificil");
    ObservableList<String> listaObserv = FXCollections.observableArrayList(listaDificultad); //Cambio de Arraylist a ObservableList para visualizarlo
    cbbDificultad.setItems(listaObserv); //Items de comboBox colocados
    cargarOpciones();
    /**
     * Accion para le boton de salir
     */
    btnSalir.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //If con confirmacion de salida sin guardar cambios
        if(alerta.alertaConfirmacion("Salir sin guardar", "Sus cambios no serán guardados", "¿Desea salir sin guardar?")){
        menu(); //Si se desea salir sin cambios se sale, si no, no se hace nada
        }
      }
    });
  /**
   * accion para el boton de guardar
   */
    btnGuardar.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        guardar(); //Guardar cambios
        alerta.alertaOk("Cambios guardados", null, "Tus cambios han sido guardados"); //Alerta de cambios guardados
        menu(); //regreso al menu principal
      }
    });
  }  

 /**
   * Metodo para la invocacion del menu
   */
  private void menu() {
    try {
      Stage planillaStage = (Stage) btnSalir.getScene().getWindow();  //Se obtiene el stage del boton crear
      ResourceBundle rb=ResourceBundle.getBundle("resource.Bundle");
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/pantallas/FXMLMen.fxml"), rb); //Se obtiene el recurso FXML y se abre su stream
      planillaStage.setScene(new Scene(root)); //Se pone la escena en el stage
      planillaStage.show(); //Se muestra
    } catch (IOException ex) {
      Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
  /**
   * Metodo para cargar las opciones del jugador
   */
  public void cargarOpciones(){
    id=Context.getInstance().currentPlayer().getIdJugador();
    Configuraciones configuracion=configuracionesjpa.findConfiguraciones(id); //busca las configuraciones por id
    cbbDificultad.getSelectionModel().select(configuracion.getDificultad()-1); //selecciona la dificultad (-1 por el indice)
    sldVolumen.setValue(configuracion.getVolumen()); //Se pone el valor del volumen
    txfPuerto.setText(String.valueOf(configuracion.getPuerto())); //se pone el texto de la base de datos casteado a String
    txfIp.setText(configuracion.getIp()); //Igual con la ip
  }
  /**
   * Metodo para guardar las configuraciones
   */
  public void guardar(){
    int dificultad=aDificultad((String) cbbDificultad.getValue()); //Obtencion de dificultad con metodo
    int volumen=(int) sldVolumen.getValue(); //Volumen de double a int
    int puerto=Integer.parseInt(txfPuerto.getText()); //Puerto de String a int
    String ip=txfIp.getText(); //obtencion de ip
    Configuraciones configuraciones=new Configuraciones(id,dificultad , volumen, puerto, ip); //Creacion de entidad
    try {
      configuracionesjpa.edit(configuraciones); //Edicion de entidad en base de datos
    } catch (PreexistingEntityException ex) {
      Logger.getLogger(FXMLOpcionesController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(FXMLOpcionesController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  /**
   * Metodo que convierte de dificultad en String a un int
   * @param dificultad dificultad en texto
   * @return dificultad en int
   */
  public int aDificultad(String dificultad){
    //Switch con dificultades
    switch(dificultad){
      case "Principiante":
        return 1;
      case "Intermedio":
        return 2;
      case "Dificil":
        return 3;
      default:
        return 2; //Dos por si hay errores se quede en medio para el jugador
    }
  }
  
  /**
   * Metodo para pasar parametro de id
   * @param id id a settear
   */
  public void setId(int id) {
    this.id = id;
  } 
}
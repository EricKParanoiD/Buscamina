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
  private int id;
  Alerta alerta=new Alerta();
  ConfiguracionesJpaController configuracionesjpa=new ConfiguracionesJpaController();
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ArrayList<String> listaDificultad = new ArrayList<>(); //ArrayList para guardar las opciones de Dificultad
    listaDificultad.add("Principiante");
    listaDificultad.add("Intermedio");
    listaDificultad.add("Dificil");
    ObservableList<String> listaObserv = FXCollections.observableArrayList(listaDificultad); //Cambio de Arraylist a ObservableList para visualizarlo
    cbbDificultad.setItems(listaObserv); //Items de comboBox colocados
    
    btnSalir.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if(alerta.alertaConfirmacion("Salir sin guardar", "Sus cambios no serán guardados", "¿Desea salir sin guardar?")){
        menu();
        }
      }
    });
  
    btnGuardar.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        guardar();
        menu();
      }
    });
  }  

 /**
   * Metodo para la invocacion del menu
   */
  private void menu() {
    try {
      Stage planillaStage = (Stage) btnSalir.getScene().getWindow();  //Se obtiene el stage del boton crear
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
  
  public void cargarOpciones(){
    Configuraciones configuracion=configuracionesjpa.findConfiguraciones(id);
    cbbDificultad.getSelectionModel().select(configuracion.getDificultad()-1);
    sldVolumen.setValue(configuracion.getVolumen());
    txfPuerto.setText(String.valueOf(configuracion.getPuerto()));
    txfIp.setText(configuracion.getIp());
  }
  
  public void guardar(){
    int dificultad=aDificultad((String) cbbDificultad.getValue());
    int volumen=(int) sldVolumen.getValue();
    int puerto=Integer.parseInt(txfPuerto.getText());
    String ip=txfIp.getText();
    Configuraciones configuraciones=new Configuraciones(id,dificultad , volumen, puerto, ip);
    try {
      configuracionesjpa.create(configuraciones);
    } catch (PreexistingEntityException ex) {
      Logger.getLogger(FXMLOpcionesController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(FXMLOpcionesController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public int aDificultad(String dificultad){
    switch(dificultad){
      case "Principiante":
        return 1;
      case "Intermedio":
        return 2;
      case "Dificil":
        return 3;
      default:
        return 2;
    }
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  
  
}

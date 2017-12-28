package controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logica.Context;

/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLMenuController implements Initializable {

  /**
   * Initializes the controller class.
   */
  @FXML
  Button btnSalir;
  @FXML
  Button btnJugar;
  @FXML
  Button btnMejores;
  @FXML
  Button btnOpciones;
  private int id;
  


  @Override
  public void initialize(URL url, ResourceBundle rb) {
    
    
    
    id=Context.getInstance().currentPlayer().getIdJugador();
    btnJugar.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        jugar();
      }
    });
    btnSalir.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        inicioSesion();
      }
    });
    
    btnOpciones.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        opciones();
      }
    });
  }

/**
 * Metodo hacia el inicio de sesion
 */
  public void inicioSesion() {
    try {
      //Recupera el stage de el boton btnRegistrarse
      Stage stage = (Stage) btnSalir.getScene().getWindow();
      //Cierra el stage
      stage.close();
      
      //Carga la nueva interfaz
      ResourceBundle rb=ResourceBundle.getBundle("resource.Bundle");
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pantallas/FXMLInicioSesion.fxml"),rb);
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
   * Metodo para ir a la interfaz opciones
   */
  public void opciones(){
    try {
      Stage planillaStage = (Stage) btnSalir.getScene().getWindow();  //Se obtiene el stage del boton salir
      ResourceBundle rb=ResourceBundle.getBundle("resource.Bundle");
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/pantallas/FXMLOpciones.fxml"), rb);  //Se obtiene el recurso FXML y se abre su stream
      planillaStage.setScene(new Scene(root)); //Se pone la escena en el stage
      planillaStage.show(); //se muestra
    } catch (IOException ex) {
      Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  /**
   * Metodo para ir a la interfaz jugar
   */
  public void jugar(){
    try {
      Stage planillaStage = (Stage) btnSalir.getScene().getWindow();  //Se obtiene el stage del boton salir
      ResourceBundle rb=ResourceBundle.getBundle("resource.Bundle");
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/pantallas/FXMLEspera.fxml"), rb);  //Se obtiene el recurso FXML y se abre su stream
      planillaStage.setScene(new Scene(root)); //Se pone la escena en el stage
      planillaStage.show(); //se muestra
    } catch (IOException ex) {
      Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  public void setId(int id) {
    this.id = id;
  }
  
  
}

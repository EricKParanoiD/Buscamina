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

  public void inicioSesion() {
    try {
      Stage stage = (Stage) btnSalir.getScene().getWindow();
      stage.close();
      
      
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pantallas/FXMLInicioSesion.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (IOException ex) {
      Logger.getLogger(FXMLMenuController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void opciones(){
    try {
      Stage planillaStage = (Stage) btnSalir.getScene().getWindow();
      FXMLLoader loader = new FXMLLoader();
      AnchorPane root = (AnchorPane) loader.load(getClass().getResource("/pantallas/FXMLOpciones.fxml").openStream());
      FXMLOpcionesController cOpciones = (FXMLOpcionesController) loader.getController();
      cOpciones.setId(id);
      cOpciones.cargarOpciones();
      planillaStage.setScene(new Scene(root));
      planillaStage.show();
    } catch (IOException ex) {
      Logger.getLogger(FXMLInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  
}

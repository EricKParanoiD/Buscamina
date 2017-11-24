/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
/**
 *
 * @author EricK
 */
public class Buscaminas extends Application {
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    ResourceBundle rb=ResourceBundle.getBundle("resource.Bundle"); 
  Parent root=FXMLLoader.load(getClass().getResource("/pantallas/FXMLInicioSesion.fxml"),rb);  
    
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
  
}

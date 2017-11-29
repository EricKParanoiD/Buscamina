/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author EricK
 */
public class Prueba extends Application {
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    ResourceBundle rb=ResourceBundle.getBundle("resource.Bundle"); 
  Parent root=FXMLLoader.load(getClass().getResource("/pantallas/FXMLJuegoPrincipiante.fxml"),rb);  
    
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  public static void main(String[] args) {
    launch(args); 
  }
}

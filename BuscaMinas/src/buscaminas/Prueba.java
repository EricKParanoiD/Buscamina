/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import io.socket.client.IO;
import io.socket.client.Socket;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logica.Context;

/**
 *
 * @author EricK
 */
public class Prueba extends Application {
  private static Socket socket;
  @Override
  public void start(Stage primaryStage) throws IOException {
    ResourceBundle rb=ResourceBundle.getBundle("resource.Bundle"); 
  Parent root=FXMLLoader.load(getClass().getResource("/pantallas/FXMLJuegoPrincipiante.fxml"),rb);  
    
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  public static void main(String[] args) {
    try {
      socket = IO.socket("http://localhost:7001"); //Se inicia el socket con puerto y host
      socket.connect();
      Context.getInstance().setSocket(socket);
      launch(args); 
    } catch (URISyntaxException ex) {
      Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}

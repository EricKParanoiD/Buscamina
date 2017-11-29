package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author EricK
 */
public class FXMLJuegoPrincipianteController implements Initializable {
@FXML
ImageView imgCampo00, imgCampo01, imgCampo02;
@FXML
Rectangle rcaJuego;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
  rcaJuego.setFill(Color.GREEN);
  Image sumido=new Image("/resource/BS.png");
  Image alzado=new Image("/resource/AMORBA.png");
  Image uno=new Image("/resource/AMOR1.png");
  imgCampo00.setImage(uno);
  imgCampo01.setImage(sumido);
  imgCampo02.setImage(alzado);
  }  
  
}

package controladores;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author EricK
 */
public class Alerta {


public void alertaInfo(String titulo, String cabeza, String mensaje){
  Alert alert = new Alert(AlertType.WARNING);
alert.setTitle(titulo);
alert.setHeaderText(cabeza);
alert.setContentText(mensaje);

alert.showAndWait();
}

public void alertaOk(String titulo, String cabeza, String mensaje){
  Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle(titulo);
alert.setHeaderText(cabeza);
alert.setContentText(mensaje);

alert.showAndWait();
}

public boolean alertaConfirmacion(String titulo, String cabeza, String mensaje){
  Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle(titulo);
alert.setHeaderText(cabeza);
alert.setContentText(mensaje);

Optional<ButtonType> result = alert.showAndWait();
  return result.get() == ButtonType.OK;
}
}

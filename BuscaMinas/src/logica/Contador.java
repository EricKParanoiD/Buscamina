/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author EricK
 */
public class Contador extends Thread {

  private int segundos;
  private final Object cambiar;
  private int i;
  @Override
  public void run() {   

    for ( i = segundos; i > 0; i--) {
        esperarSegundo();
        Platform.runLater(new Runnable() {
    @Override
    public void run() {
        if (cambiar instanceof ProgressBar) {
          double progreso=0.033*i;
          ((ProgressBar) cambiar).setProgress(progreso);
        }
        if (cambiar instanceof Label) {
          ((Label) cambiar).setText(Integer.toString(i));          
        }
    }
});
           
  
      }
   

  }

  /**
   * Constructor
   *
   * @param segundos numero de segundos
   * @param cambiar objecto a cambiar
   */
  public Contador(int segundos, Object cambiar) {
    this.segundos = segundos;
    this.cambiar = cambiar;
  }

  /**
   * Metodo para esperar un segundo
   */
  public void esperarSegundo() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      System.out.println("Excepcion");
      Logger.getLogger(Contador.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
//Setters y getters

  public int getSegundos() {
    return segundos;
  }

  public void setSegundos(int segundos) {
    this.segundos = segundos;
  }

}

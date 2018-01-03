/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javafx.scene.control.Button;

/**
 *
 * @author EricK
 */
public class Campo extends Button{
  private Coordenadas coordenadas;

  public Campo(Coordenadas coordenadas) {
    super();
    this.coordenadas = coordenadas;
  }

  public Campo() {
  }
  
  
}

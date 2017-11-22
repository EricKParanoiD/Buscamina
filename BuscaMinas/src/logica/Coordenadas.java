/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author EricK
 */
public class Coordenadas {
  private int coordenadaX; //atributo de coordenada X
  private int coordenadaY; //atributo de coordenada Y

  /**
   * Constructor de coordenadas
   * @param coordenadaX coordenada X
   * @param coordenadaY coordenada Y
   */
  public Coordenadas(int coordenadaX, int coordenadaY) {
    this.coordenadaX = coordenadaX;
    this.coordenadaY = coordenadaY;
  }
  
  
  @Override
public boolean equals(Object o){
  
if(( o instanceof Coordenadas)){
  Coordenadas oCoor=(Coordenadas) o;
  return oCoor.coordenadaX==this.coordenadaX && oCoor.coordenadaY==this.coordenadaY;
}else{
  return false;
}  

}
  //Setters y getters
/**
 * Metodo hashcode generado
 * @return el hashcode
 */
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 37 * hash + this.coordenadaX;
    hash = 37 * hash + this.coordenadaY;
    return hash;
  }

  public int getCoordenadaX() {
    return coordenadaX;
  }

  public void setCoordenadaX(int coordenadaX) {
    this.coordenadaX = coordenadaX;
  }

  public int getCoordenadaY() {
    return coordenadaY;
  }

  public void setCoordenadaY(int coordenadaY) {
    this.coordenadaY = coordenadaY;
  }
  
}

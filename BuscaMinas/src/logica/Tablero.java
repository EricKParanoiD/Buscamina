/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;

/**
 *
 * @author EricK
 */
public final class Tablero {

  private int arrCasilla[][]; //Arreglo de casillas con numero de minas (0 sin minas alrededor, 1 una mina alrededor, 9 mina, etc)
  private int numMinas; //numero de minas en el campo
  private int tamano; //tamaño del cuadrante del tablero

  /**
   * Constructor de tablero
   *
   * @param numMinas numero de minas a colocar en el tablero
   * @param tamano tamaño del cuadrado de tablero
   */
  public Tablero(int numMinas, int tamano) {
    this.numMinas = numMinas;
    this.tamano = tamano;
    this.arrCasilla = new int[tamano][tamano];
    inicializar();  //inicializacion de tablero
    sortear();  //Sorteo de minas
    contarAlrededor();  //conteo alrededor de minas
  }
  /**
   * Constructor vacio
   */
  public Tablero() {
  }

  /**
   * Metodo para sortear las minas
   */
  public void sortear() {
    for (int i = 0; i < numMinas; i++) {
      int xCor = (int) (Math.random() * (tamano - 1)); //xCor variable auxiliar de coordenada X
      int yCor = (int) (Math.random() * (tamano - 1)); //yCor variable auxiliar de coordenada Y
      //Condicion que verifica que no se repita una mina en el mismo lugar
      if (arrCasilla[xCor][yCor] != 9) {
        arrCasilla[xCor][yCor] = 9;
      } else {
        i--;
      }

    }
  }

  /**
   * Metodo para inicializar el arreglo en 0
   */
  public void inicializar() {
    for (int i = 0; i < tamano; i++) {
      for (int j = 0; j < tamano; j++) {
        arrCasilla[i][j] = 0;
      }

    }
  }

  /**
   * Metodo para poner en el arreglo los numeros de cuantas minas tiene la casilla alrededor
   */
  public void contarAlrededor() {
    //Ciclo para repasar todo el arreglo
    for (int i = 0; i < arrCasilla.length; i++) {
      for (int j = 0; j < arrCasilla.length; j++) {
        //Ciclo que repasa las casillas alrededor de la casilla actual
        int contadorMinas = 0;
        if (arrCasilla[i][j] != 9) {
          for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
              int corX = i + (k - 1);
              int corY = j + (l - 1);
              //Condicion que verifica que no se repase a si misma la casilla, y las oordenadas no se salgan del arreglo
              if ((i != corX || j != corY) && (corX >= 0 && corX < arrCasilla.length) && (corY >= 0 && corY < arrCasilla.length)) {
                // && !(corY<=0 || corY>arrCasilla.length-1)
                //COndicion que verifica si hay mina
                if (arrCasilla[corX][corY] == 9) {
                  contadorMinas++;
                }
              }
            }
          }
          arrCasilla[i][j] = contadorMinas; //Al final del conteo se le asigna el numero
        }

      }
    }
  }

  /**
   * metodo de impresion de matriz para comprobacion visual
   */
  public void imprimirMatriz() {
    for (int i = 0; i < arrCasilla.length; i++) {
      for (int j = 0; j < arrCasilla.length; j++) {
        System.out.print(arrCasilla[j][i] + " ");
      }
      System.out.println("");
    }
  }
  /**
   * Metodo que pone en el array dado la lista de elementos vacios alrededor
   * @param arrCoordenadas  arreglo al que ponerle las coordenadas
   * @param actualX coordenada x de inicio
   * @param actualY coordenada y de inicio
   */
  public void limpiarVaciosAlrededor(ArrayList<Coordenadas> arrCoordenadas, int actualX, int actualY) {

    arrCoordenadas.add(new Coordenadas(actualX, actualY));
    for (int k = 0; k < 3; k++) {
      for (int l = 0; l < 3; l++) {
        int corX = actualX + (k - 1);
        int corY = actualY + (l - 1);
        System.out.println("Comprobando el " + corX + ", " + corY + "Con " + actualX + ", " + actualY);
        //Condicion que verifica que no se repase a si misma la casilla, y las oordenadas no se salgan del arreglo
        if ((actualX != corX || actualY != corY) && (corX >= 0 && corX < arrCasilla.length) && (corY >= 0 && corY < arrCasilla.length) && !(arrCoordenadas.contains(new Coordenadas(corX, corY)))) {
          System.out.println("Entrando a limpiar " + corX + "," + corY);
          if (arrCasilla[corX][corY] == 0) {

            limpiarVaciosAlrededor(arrCoordenadas, corX, corY);
          } else if (!arrCoordenadas.contains(new Coordenadas(corX, corY))) {
            arrCoordenadas.add(new Coordenadas(corX, corY));
          }
        }
      }
    }

  }
  //Setters y Getters

  public int[][] getArrCasilla() {
    return arrCasilla;
  }

  public void setArrCasilla(int[][] arrCasilla) {
    this.arrCasilla = arrCasilla;
  }

  public int getNumMinas() {
    return numMinas;
  }

  public void setNumMinas(int numMinas) {
    this.numMinas = numMinas;
  }

  public int getTamano() {
    return tamano;
  }

  public void setTamano(int tamano) {
    this.tamano = tamano;
  }

}

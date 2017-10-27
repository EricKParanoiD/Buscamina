package PruebasUnitarias;

import java.util.ArrayList;
import logica.Coordenadas;
import logica.Tablero;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author EricK
 */
public class TestTablero {
  //Variables para uso global Clase Tablero y Arreglo para auxiliar
  Tablero tab;
  int [][] arrPrueba;
  
  public TestTablero() {
  }
  /**
   * Inicializacion del Tablero
   */  
  @Before
  public void before() {
    System.out.println("Before");
    tab=new Tablero();
  }
  
  @After
  public void after() {
    System.out.println("After");
  }
  /**
   * Prueba del metodo inicializar
   */
  @Test
  public void TestInicializar(){
    tab.setTamano(8); //Se le pone el tamaño
    tab.setArrCasilla(new int [10][10]);  // Se inicializa el arreglo
    arrPrueba=tab.getArrCasilla();  //Se vacia el arreglo para usar los numeros mas facilmente
    System.out.println("Test Inicializar");
  int contadorNum=0; //Contador para ver si hay otros numeros ademas de 0 
  tab.inicializar(); //Uso de metodo Inicializar
    for (int i = 0; i < arrPrueba.length; i++) {
      for (int j = 0; j < arrPrueba.length; j++) {
        if(arrPrueba[i][j]!=0){
          contadorNum++;
        }
       }      
    }
    tab.imprimirMatriz();
    Assert.assertEquals(0, contadorNum);
  }
  
  /**
   * Prueba del metodo sortear
   */
  @Test
  public void testSortear(){
    System.out.println("Test Sortear");
    tab.setNumMinas(10); //se le pone el numero de minas
    tab.setTamano(8); //Se le pone el tamaño
    tab.setArrCasilla(new int [10][10]); //se inicializa el arreglo
    tab.inicializar(); //uso del metodo inicializar
    tab.sortear(); //Uso del metodo sortear
    arrPrueba=tab.getArrCasilla(); //Se pasa el arreglo a uno nuevo para verificar
    int contadorMinas=0; //Contador para minas en el arreglo
    /**
     * Ciclo para checar uno por uno cada elemento del arreglo
     */
    for (int i = 0; i < arrPrueba.length; i++) {
      for (int j = 0; j < arrPrueba.length; j++) {
        if(arrPrueba[i][j]==9){
          contadorMinas++;
        }
      }
     }
    tab.imprimirMatriz();
    Assert.assertEquals(10, contadorMinas);
    
  }
  /**
   * Prueba del metodo contarAlrededor
   */
  @Test
  public void testContarAlrededor(){
    System.out.println("Test ContarAlrededor");
    tab.setArrCasilla(new int [10][10]); //Declaracion de Tablero
    tab.inicializar(); //Uso de metodo inicializar
    arrPrueba=tab.getArrCasilla(); //Se obtiene del Tablero el arreglo
    arrPrueba [1][1]=9; //Se le colocan dos minas al arreglo
    arrPrueba [5][4]=9;
    tab.setArrCasilla(arrPrueba); //se le pone arreglo al Tablero
    tab.contarAlrededor(); //uso del metodo contarAlrededor
    tab.imprimirMatriz(); //Se imprime para verificar
    
    Assert.assertEquals(1, arrPrueba[4][3]);
    Assert.assertEquals(1, arrPrueba[5][3]);
  }
  
  /**
   * 
  */ 
  @Test
  public void testLimpiarVaciosAlrededor(){
    System.out.println("Test LimpiarVaciosAlrededor");
    tab.setArrCasilla(new int [10][10]); //Declaracion de Tablero
    tab.inicializar(); //Uso de metodo inicializar
    arrPrueba=tab.getArrCasilla(); //Se obtiene del Tablero el arreglo
    arrPrueba [3][2]=9; //Se le colocan dos minas al arreglo
    arrPrueba [2][2]=9;
    arrPrueba [4][5]=9;
    arrPrueba [0][2]=9;
    arrPrueba [1][7]=9;
    tab.setArrCasilla(arrPrueba); //se le pone arreglo al Tablero
    tab.contarAlrededor(); //uso del metodo contarAlrededor
    tab.imprimirMatriz(); //Se imprime para verificar
    ArrayList<Coordenadas> arrLimpios=tab.limpiarVaciosAlrededor(0, 0);
    for (int i = 0; i < arrLimpios.size(); i++) {
   System.out.println("X:"+arrLimpios.get(i).getCoordenadaX()+"Y:"+arrLimpios.get(i).getCoordenadaY());      
    }
    
    System.out.println("Despues de limpiar vacios");
    tab.imprimirMatriz();

  }
  
}

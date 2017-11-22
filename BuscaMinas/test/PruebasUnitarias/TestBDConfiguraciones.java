
package PruebasUnitarias;

import controller.ConfiguracionesJpaController;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entidades.Configuraciones;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author EricK
 */

public class TestBDConfiguraciones {
  //Variables de uso global
  ConfiguracionesJpaController configuracionjpa=new ConfiguracionesJpaController();
  List<Configuraciones> configuraciones=configuracionjpa.findConfiguracionesEntities();
  
  /**
   * before que actualiza la lista de configuraciones con la base de datos
   */
  @Before
  public void before(){
    System.out.println("Before");
    configuraciones=configuracionjpa.findConfiguracionesEntities();
  }
  
  /**
   * Test para lectura de configuraciones
   */
  @Test
  public void testBDLeer(){
    System.out.println("Leer");
    imprimirConfiguraciones(); //impresion de todas las configuraciones en la base de datos
    //Asserts con comparaciones 
    Assert.assertEquals(1, configuraciones.get(0).getDificultad());
    Assert.assertEquals(10, configuraciones.get(0).getVolumen());
    Assert.assertEquals(7001, configuraciones.get(0).getPuerto());
    Assert.assertEquals("localhost", configuraciones.get(0).getIp());
  }
  /**
   * Test para la edicion en base de datos
   */
  @Test
  public void testBDEditar(){
    System.out.println("Editar");
    imprimirConfiguraciones(); //impresion para comprobacion visual
    Configuraciones editC=new Configuraciones(1, 2, 20, 7001, "localhost"); //Declaracion de objeto para editar
    //Try con edicion
    try {
      configuracionjpa.edit(editC);
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    }
    //Actualizacion de entidades en base de datos
    configuraciones=configuracionjpa.findConfiguracionesEntities();
    System.out.println("Editado");
    imprimirConfiguraciones(); //Impresion para comprobacion visual
    //Asserts para comprobacion
    Assert.assertEquals(2, configuraciones.get(0).getDificultad());
    Assert.assertEquals(20, configuraciones.get(0).getVolumen());
    Assert.assertEquals(7001, configuraciones.get(0).getPuerto());
    Assert.assertEquals("localhost", configuraciones.get(0).getIp());
    //Regreso a variable a normalidad despues de comprobacion de la edicion
    Configuraciones returnC=new Configuraciones(1, 1, 10, 7001, "localhost");
    try {
      configuracionjpa.edit(returnC);
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  /**
   * Test de creacion y eliminacion (juntos para dejar todo igual)
   */
  @Test
  public void testDBCrearEliminar(){
    System.out.println("Crear");
    imprimirConfiguraciones(); //Impresion para comprobacion visual
    Configuraciones crearC=new Configuraciones(2, 3, 40, 7001, "localhost"); //Creacion de objeto para creacion
    //Try con creacion de objeto
    try {
      configuracionjpa.create(crearC);
    } catch (PreexistingEntityException ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    System.out.println("Creado");
    configuraciones=configuracionjpa.findConfiguracionesEntities(); //Actualizacion de lista con entidades de la base
    //Asserts para comprobacion de creacion
    Assert.assertEquals(3, configuraciones.get(configuraciones.size()-1).getDificultad());
    Assert.assertEquals(40, configuraciones.get(configuraciones.size()-1).getVolumen());
    Assert.assertEquals(7001, configuraciones.get(configuraciones.size()-1).getPuerto());
    Assert.assertEquals("localhost", configuraciones.get(configuraciones.size()-1).getIp());
    imprimirConfiguraciones(); //Impresion para comprobacion visual
    //Assert de numero de objetos en la base
    Assert.assertEquals(2, configuraciones.size());
    //Try con eliminacion
    System.out.println("Eliminar");
    try {
      configuracionjpa.destroy(configuraciones.get(configuraciones.size()-1).getJugadoridJugador());
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    }
    System.out.println("Eliminado");
    configuraciones=configuracionjpa.findConfiguracionesEntities(); //Actualizacion de lista con base de datos
    //Assert con comprobacion de numero de entidades
    Assert.assertEquals(1, configuraciones.size());
  }
  
  /**
   * Metodo de impresion de lista para facilitar comprobacion visual
   */
  public void imprimirConfiguraciones(){
    for (int i = 0; i < configuraciones.size(); i++) {
      System.out.println("Configuraciones ID: "+configuraciones.get(i).getJugadoridJugador());
      System.out.println("Configuraciones Dificultad: "+configuraciones.get(i).getDificultad());
      System.out.println("Configuraciones Volumen: "+configuraciones.get(i).getVolumen());      
    }
  }
  
}

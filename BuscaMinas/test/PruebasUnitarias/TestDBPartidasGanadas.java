/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PruebasUnitarias;

import controller.PartidasganadasJpaController;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entidades.Partidasganadas;
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
public class TestDBPartidasGanadas {
  PartidasganadasJpaController partidajpa=new PartidasganadasJpaController();
  List<Partidasganadas> partidas=partidajpa.findPartidasganadasEntities();
  
 /**
  * before con actualizacion de partidas en base de datos
  */
  @Before
  public void before(){
    System.out.println("Before");
    partidas=partidajpa.findPartidasganadasEntities();
  }
  /**
   * test de leer partidas ganadas en la base de datos
   */
  @Test
  public void testBDLeer(){
    System.out.println("Leer");
    imprimirPartidas(); //impresion de partidas para comprobacion visual
    //Asserts de partidas
    Assert.assertEquals(5, partidas.get(0).getFacil(), 0); 
    Assert.assertEquals(3, partidas.get(0).getIntermedio(), 0);
    Assert.assertEquals(2, partidas.get(0).getDificil(), 0);
  }
  /**
   * test de edicion
   */
  @Test
  public void testBDEditar(){
    System.out.println("Editar");
    imprimirPartidas(); //impresion para comprobacion visual
    Partidasganadas editP=new Partidasganadas(1, 4, 2, 1);  //creacion de entidad para edicion
    try {
      partidajpa.edit(editP); //edicion
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(TestDBPartidasGanadas.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(TestDBPartidasGanadas.class.getName()).log(Level.SEVERE, null, ex);
    }
    partidas=partidajpa.findPartidasganadasEntities(); //actualizacion de lista de entidades
    imprimirPartidas(); //impresion para comprobacion visual
    //Asserts para comprobacion
    Assert.assertEquals(4, partidas.get(0).getFacil(), 0);
    Assert.assertEquals(2, partidas.get(0).getIntermedio(), 0);
    Assert.assertEquals(1, partidas.get(0).getDificil(), 0);
    //Regreso a la normalidad de entidad
    Partidasganadas returnP=new Partidasganadas(1, 5, 3, 2);
    try {
      partidajpa.edit(returnP);
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(TestDBPartidasGanadas.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(TestDBPartidasGanadas.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  /**
   * test de eliminacion y creacion
   */
  @Test
  public void testDBCrearEliminar(){
    System.out.println("Crear");
    imprimirPartidas(); //impresion para comprobacion visual
    Partidasganadas crearP=new Partidasganadas(2, 6, 5, 1); //entidad a crear
    try {
      partidajpa.create(crearP); //creacion
    } catch (PreexistingEntityException ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    System.out.println("Creado");
    partidas=partidajpa.findPartidasganadasEntities(); //actualizacion de entidades
    //Asserts de comprobacion de creacion
    Assert.assertEquals(6, partidas.get(partidas.size()-1).getFacil(), 0);
    Assert.assertEquals(5, partidas.get(partidas.size()-1).getIntermedio(), 0);
    Assert.assertEquals(1, partidas.get(partidas.size()-1).getDificil(), 0);
    Assert.assertEquals(2, partidas.size());
    imprimirPartidas(); //impresion para comprobacion visual
    System.out.println("Eliminar");
    try {
      partidajpa.destroy(partidas.get(partidas.size()-1).getJugadoridJugador()); //eliminacion de entidad creada
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(TestBDConfiguraciones.class.getName()).log(Level.SEVERE, null, ex);
    }
    System.out.println("Eliminado");
    imprimirPartidas(); //impresion para comprobacion visual
    partidas=partidajpa.findPartidasganadasEntities(); //actualizacion de lista de entidades
    Assert.assertEquals(1, partidas.size()); //assert de tamaño
  }
  
  /**
   * metodo de impresion para comprobacion visual
   */
  public void imprimirPartidas(){
    for (int i = 0; i < partidas.size(); i++) {
       System.out.println("Partidas ID: "+partidas.get(i).getJugadoridJugador());
       System.out.println("Partidas Facil: "+partidas.get(i).getFacil());
       System.out.println("Partidas Intermedio: "+partidas.get(i).getIntermedio());
       System.out.println("Partidas Dificil: "+partidas.get(i).getDificil()+"\n");
    }
  }
}

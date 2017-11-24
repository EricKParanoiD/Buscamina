/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.Jugador;

public class Context {
    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private final Jugador jugador;

  public Context() {
    this.jugador = new Jugador();
  }

    public Jugador currentPlayer() {
        return jugador;
    }
}
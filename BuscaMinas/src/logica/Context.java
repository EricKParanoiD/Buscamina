package logica;

import entidades.Jugador;
import io.socket.client.Socket;

public class Context {
    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private final Jugador jugador;
    private Socket socket;
    
  public Context() {
    this.jugador = new Jugador();
  }

    public Socket currentSocket(){
      return socket;
    }
    
    public void setSocket(Socket socket){
      this.socket=socket;
    }
    public Jugador currentPlayer() {
        return jugador;
    }
}
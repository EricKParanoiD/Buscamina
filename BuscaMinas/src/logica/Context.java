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
    private String room;
  public Context() {
    this.jugador = new Jugador();
  }
  
  public String currentRoom(){
    return room;
  }
  
  public void setRoom(String room){
    this.room=room;
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
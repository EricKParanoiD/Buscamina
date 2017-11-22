package logica;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Encriptar {
  /**
   * Convierte una cadena a un sha256
   * @param password cadena a convertir
   * @return cadena convertida
   */
  public String convertirSHA256(String password) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      Logger.getLogger(Contador.class.getName()).log(Level.SEVERE, null, e);
      return null;
    }

    byte[] hash = md.digest(password.getBytes());
    StringBuilder sb = new StringBuilder();

    for (byte b : hash) {
      sb.append(String.format("%02x", b));
    }

    return sb.toString();
  }
}

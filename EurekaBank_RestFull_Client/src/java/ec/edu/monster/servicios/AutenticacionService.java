/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.servicios;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Usuario
 */
public class AutenticacionService {
    
     private static final String USUARIO_CORRECTO = "Monster";
    private final String hashContraseñaCorrecta;

    public AutenticacionService() {
        String contraseñaOriginal = "Monster9";
        this.hashContraseñaCorrecta = generarHashSegura(contraseñaOriginal);        
    }

    // Método para autenticar al usuario
    public boolean autenticar(String username, String password) {
        String hashPassword = generarHashSegura(password);
        return USUARIO_CORRECTO.equals(username) && hashContraseñaCorrecta.equals(hashPassword);
    }
   
    public String generarHashSegura(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método auxiliar para convertir bytes a hexadecimal
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }    
}

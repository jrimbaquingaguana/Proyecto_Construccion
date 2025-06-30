/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.controlador;

import ec.edu.monster.servicios.AutenticacionService;

/**
 *
 * @author Usuario
 */
public class LoginControlador {
    private AutenticacionService autenService;
    
    public LoginControlador() {
        this.autenService = new AutenticacionService();
    }
    
    public boolean login(String username, String password) {
        return autenService.autenticar(username, password);
    }
    
}

package br.com.itb.miniprojetospring.service;

public interface EmailService {
    String enviarEmailRecuperacao(String email_usuario);

    String enviarEmailRecuperacao(String email_usuario, String token);

    boolean validarToken(String token);
}

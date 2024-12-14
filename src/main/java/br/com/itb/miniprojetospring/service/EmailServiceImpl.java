package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public String enviarEmailRecuperacao(String email_usuario) {
        return "";
    }

    @Override
    public String enviarEmailRecuperacao(String email_usuario, String token) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

            messageHelper.setSubject("Recuperação de senha");
            messageHelper.setTo(email_usuario);
            messageHelper.setText("Codigo para a restauracao da senha ," + token, true);

            javaMailSender.send(mimeMessage);
            return token; // Retorna o token gerado
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar o e-mail de recuperacao de senha", e);
        }
    }

    @Override
    public boolean validarToken(String token) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByToken(token);

        if (optionalUsuario.isPresent() && optionalUsuario.get().isTokenValido()) {
            return true; // Token válido
        } else {
            return false; // Token inválido ou expirado
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString().substring(0, 9); // Gera um token de 9 caracteres
    }
}


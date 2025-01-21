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
    public String enviarEmailRecuperacao(String email_usuario, String token) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

            messageHelper.setSubject("Recuperação de senha");
            messageHelper.setTo(email_usuario);
            messageHelper.setText("<p>Olá "+ email_usuario+"!</p>"+
                    "<p>Recebemos sua solicitação para um código de uso-único para <strong> redefinição de senha </strong>.</p>"+

                    "<p>Seu código para a Restauração da senha: <strong>" + token + "</strong></p>." +
                    "<p>Caso não temha solicitado esse código, apenas ignore esta mensagem </p>"+
                    "</br>" +
                    "<p>Agradecemos pela sua atenção,</p>"+
                    "<p>Safe Solutions</p>",true);


            javaMailSender.send(mimeMessage);
            return token; // Retorna o token gerado
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar o e-mail de recuperacao de senha", e);
        }
    }

}


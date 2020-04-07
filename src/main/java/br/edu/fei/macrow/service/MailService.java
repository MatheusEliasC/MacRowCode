package br.edu.fei.macrow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {

	@Autowired
    private JavaMailSender javaMailSender;
	
	public void sendConfirmationEmail(String email, String dominio,String url) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email+"@"+dominio);

        msg.setSubject("Confirmação de conta via email");
        String text = "Olá usuário. \n Para confirmar sua conta por favor acesse o link abaixo.\n"+url;
        msg.setText(text);

        javaMailSender.send(msg);

    }
	
}

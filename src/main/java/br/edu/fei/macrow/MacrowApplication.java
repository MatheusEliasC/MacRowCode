package br.edu.fei.macrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Para testar o sistema de email retire os comentarios e adicione na frente de "MacrowApplication" a implementação: " implements CommandLineRunner"
@SpringBootApplication
public class MacrowApplication{

	public static void main(String[] args) {
		SpringApplication.run(MacrowApplication.class, args);
	}
	
//	@Autowired
//    private JavaMailSender javaMailSender;
	
//	@Override
//    public void run(String... args) {
//
//        System.out.println("Sending Email...");
//
//        try {
//		
//            sendEmail();
//            //sendEmailWithAttachment();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } 
//
//        System.out.println("Done");
//
//    }
	
	/**
	 * Função para testar o sistema de envio de email
	 */
//    void sendEmail() {
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("matheuseliascruz@gmail.com");
//
//        msg.setSubject("Testing from Spring Boot");
//        msg.setText("Hello World \n Spring Boot Email");
//
//        javaMailSender.send(msg);
//
//    }

}

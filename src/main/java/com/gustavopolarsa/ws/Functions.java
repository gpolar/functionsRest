package com.gustavopolarsa.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

import com.gustavopolarsa.to.EmailTO;

@Component
@Path("/email")
public class Functions {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response enviarEmail(EmailTO emailTO) {
		  Properties props = new Properties();
		  
          props.put("mail.smtp.host", "smtp.sendgrid.net");
          props.put("mail.smtp.starttls.enable", "true");
          /*props.put("mail.smtp.socketFactory.port", "465");
          props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");*/
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.port", "587");

          Session session = Session.getDefaultInstance(props,
                      new javax.mail.Authenticator() {
                           protected PasswordAuthentication getPasswordAuthentication()
                           {
                                 return new PasswordAuthentication("gpolar", "Sevchenko1");
                           }
                      });

          /** Ativa Debug para sessão */
          session.setDebug(true);

          try {
        	  System.out.println(emailTO.getAssunto()+" - " + emailTO.getMensagem() +" - " + emailTO.getDestinatario());
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("fiapcloud@gustavopolarsa.com")); //Remetente

                Address[] toUser = InternetAddress //Destinatário(s)
                           .parse(emailTO.getDestinatario());  

                message.setRecipients(Message.RecipientType.TO, toUser);
                message.setSubject(emailTO.getAssunto());//Assunto
                message.setText(emailTO.getMensagem());
                /**Método para enviar a mensagem criada*/
                Transport.send(message);

                System.out.println("Feito!!!");

           } catch (MessagingException e) {
                throw new RuntimeException(e);
          }
		return Response.noContent().build();
	}
}
package br.com.alura.forum.service.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.alura.forum.infra.NewReplyMailFactory;
import br.com.alura.forum.model.Answer;
import br.com.alura.forum.model.topic.domain.Topic;

@Service
public class ForumMailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private NewReplyMailFactory newReplyMailFactory;
	
	@Async
	public void sendNewReplyMail(Answer answer) throws MailServiceException {
		Topic answeredTopic = answer.getTopic();
		
		MimeMessagePreparator messagePreparator = (mimeMessage) -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			
			messageHelper.setTo(answeredTopic.getOwnerEmail());
			messageHelper.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());
			
			String messageContent = this.newReplyMailFactory.generateNewReplyMailContent(answer);
			messageHelper.setText(messageContent, true);
		};
		
		try {
			mailSender.send(messagePreparator);
		}catch(MailException e) {
			throw new MailServiceException("Não foi possivel enviar o email.", e);
		}
		
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(answeredTopic.getOwnerEmail());
//		message.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());
//		message.setText("Olá " + answeredTopic.getOwnerEmail() + " Há uma nova mensagem no fórum! " + answer.getOwnerName() + " comentou no tópico " + answeredTopic.getShortDescription());
//		try {
//			mailSender.send(message);
//		} catch (MailException e) {
//			throw new MailServiceException("Não foi possível enviar email.", e);
//		}
	}
	
}

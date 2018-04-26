package com.pankaj.mail;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.pankaj.mail.util.CSVToArrayList;

public class SendBulkMail {
	public static void main(String[] args) {

		final String from = "pankajkumar.01869@gmail.com";
		final DataSource source = new FileDataSource("/home/pkp/Desktop/Resume-PankajKumar.pdf");

		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("your gmail address", "your gmail password");
			}
		});

		for (String to : CSVToArrayList.readEmailsFromCSV("/home/pkp/Desktop/email.csv"))
			try {
				// Create a default MimeMessage object.
				Message message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(from));

				// Set To: header field of the header.

				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

				// Set Subject: header field
				message.setSubject("Resume for Java/J2EE Profile - 1.4+ years of Experience");

				// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();

				// Now set the actual message
				messageBodyPart.setText("Phone: +918010215963");

				// Create a multipar message
				Multipart multipart = new MimeMultipart();

				// Set text message part
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("Resume-PankajKumar.pdf");
				multipart.addBodyPart(messageBodyPart);

				// Send the complete message parts
				message.setContent(multipart);

				// Send message
				Transport.send(message);

				System.out.println("Sent message successfully to:- " + to);

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}

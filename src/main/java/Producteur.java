import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producteur {

	// Url du broker  (ici l'url par défaut de l'installation d'apacheMQ
	private static String	url		= "tcp://lincsnt:61616";

	// Nom de la Queue de destination des messages
	private static String	subject	= "TestQueue";

	public static void main(String[] args) throws JMSException {
		// On récupere une connexion JMS et on la démarre

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		// La session va servir à envoyer et recevoir des messages JMS.
		// le premier parametre indique si nous voulons un contexte transactionnel ou pas.
		// ici c'est une session non transactionnelle

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Ici création de la queue de destination (MyQueue).
		// Si la Queue existe déja , elle n'est pas recréée.
		Destination destination = session.createQueue(subject);

		// création du producteur de messages
		MessageProducer producer = session.createProducer(destination);

		// Création de 1000 messages texte à destination de la Queue "MyQueue"
		TextMessage message = null;

		for (int i = 0; i < 1000; i++) {
			message = session.createTextMessage("Message de test numéro ==>" + i + " à destination de "+subject);
			// Envoi du message
			producer.send(message);
			System.out.println("Message envoyé '" + message.getText() + "'");
		}
		connection.close();
	}
}
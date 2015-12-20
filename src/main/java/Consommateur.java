import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consommateur {

	// Url du broker (ici l'url par défaut de l'installation d'apacheMQ)
	private static String	url		= "tcp://lincsnt:61616";

	// Nom de la Queue
	private static String	subject	= "MyQueue";

	public static void main(String[] args) throws JMSException {
		// Getting JMS connection from the server
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		// Creating session for seding messages
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createQueue(subject);

		// on créer le consomateur de la Queue
		MessageConsumer consumer = session.createConsumer(destination);

		// réception des messages
		TextMessage textMessage;
		while (true) {
			Message m = consumer.receive(1);
			if (m != null) {
				if (m instanceof TextMessage) {
					textMessage = (TextMessage) m;
					System.out.println("Message reçu : " + textMessage.getText());
				} else {
					break;
				}
			}
		}

		connection.close();
	}
}
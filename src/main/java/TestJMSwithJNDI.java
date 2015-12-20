import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class TestJMSwithJNDI {

	public static void main(final String[] args) {
		Context context = null;
		ConnectionFactory factory = null;
		Connection connection = null;
		Destination destination = null;
		Session session = null;
		MessageProducer sender = null;
		try {
			context = new InitialContext();
			factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			destination = (Destination) context.lookup("MyQueue");
			//			destination = (Destination) context.lookup("queue/MyQueue"); // does not work
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createProducer(destination);
			connection.start();

			final TextMessage message = session.createTextMessage();
			message.setText("Mon message");
			sender.send(message);
			System.out.println("Message envoye= " + message.getText());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (context != null) {
				try {
					context.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

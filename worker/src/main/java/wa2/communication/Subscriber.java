package wa2.communication;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);

    private static final String NO_MESSAGE = "empty";

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;

    public void create(String clientId, String topicName) throws JMSException {
        this.clientId = clientId;

        // create a Connection Factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_BROKER_URL);

        // create a Connection
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);

        // create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // create the Topic from which messages will be received
        Destination destination = session.createQueue(topicName);

        // create a MessageConsumer for receiving messages
        messageConsumer = session.createConsumer(destination);

        // start the connection in order to receive messages
        connection.start();
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

    public String getMsg(int timeout) throws JMSException {

        String msg = NO_MESSAGE;

        // read a message from the topic destination
        Message message = messageConsumer.receive(timeout);

        // check if a message was received
        if (message != null) {
            // cast the message to the correct type
            TextMessage textMessage = (TextMessage) message;

            // retrieve the message content
            String text = textMessage.getText();
            LOGGER.debug(clientId + ": received message with text='{}'", text);
            //System.out.println(clientId + ": received message with text=" + text);

            msg = text;
        } else {
            // LOGGER.debug(clientId + ": no message received");
            // System.out.println(clientId + ": no message received");
        }

        //  LOGGER.info("greeting={}", greeting);
        //  System.out.println("greeting=" + greeting);
        return msg;
    }
}
package wa2.communication;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.hibernate.boot.jaxb.SourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Publisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Publisher.class);

    private String clientId;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public Publisher() throws JMSException {
        UUID id = UUID.randomUUID();
        create(String.valueOf(id),"bank.t");
    }

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

        // create the Topic to which messages will be sent
        Destination destination = session.createQueue(topicName);

        // create a MessageProducer for sending messages
        messageProducer = session.createProducer(destination);
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

    public void sendCalculation(String uuid, String values) throws JMSException {
        String text = uuid + ";" + values;

        // create a JMS TextMessage
        TextMessage textMessage = session.createTextMessage(text);

        // send the message to the topic destination
        messageProducer.send(textMessage);

        LOGGER.debug(clientId + ": sent message with text='{}'", text);
        System.out.println(clientId + ": sent message with text=" + text);
    }
}
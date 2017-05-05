package com.dipen.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.soap.Text;
import java.util.Properties;

/**
 * Created by dipen on 5/5/2017.
 */
public class MySender {

    public static void main(String[] args) {
        Properties env = new Properties( );
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.fscontext.RefFSContextFactory");
       /* env.put(Context.PROVIDER_URL,
                "ts://localhost:4848");*/
        try {
            InitialContext initialContext = new InitialContext();

            QueueConnectionFactory  queueConnectionFactory= (QueueConnectionFactory) initialContext.lookup("java:comp/DefaultJMSConnectionFactory");

            QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();

            queueConnection.start();

            QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = (Queue) initialContext.lookup("myQueue");

            QueueSender queueSender = queueSession.createSender(queue);

            TextMessage textMessage = queueSession.createTextMessage();

            textMessage.setText("Hello Dipen! This is JMS Testing");

            queueSender.send(textMessage);

            queueConnection.close();

        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }

    }
}
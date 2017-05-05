package com.dipen.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by dipen on 5/5/2017.
 */
public class MyReceiver {
    public static void main(String[] args) {
        try {
            InitialContext initialContext = new InitialContext();

            QueueConnectionFactory queueConnectionFactory= (QueueConnectionFactory) initialContext.lookup("java:comp/DefaultJMSConnectionFactory");

            QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();

            queueConnection.start();

            QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = (Queue) initialContext.lookup("myQueue");

            QueueReceiver queueReceiver = queueSession.createReceiver(queue);
            final TextMessage[] textMessage = new TextMessage[1];
            queueReceiver.setMessageListener(     new MessageListener(){
                @Override
                public void onMessage(Message message) {
                    System.out.print("Inside Message Receiver" + message);
                    textMessage[0] = (TextMessage) message;
                }
            });

            System.out.print(textMessage[0]);

            queueConnection.close();

        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }
}


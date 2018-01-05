package com.poc.sample;

import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Bala on 05/01/2018.
 */
@Service
public class CadsSolaceService {

    private static final Logger logger = LoggerFactory.getLogger(CadsSolaceService.class);

    @Autowired
    private CadsConfig cadsConfig;

    public void subscribeSolaceTopic() throws Exception {

        final CountDownLatch latch = new CountDownLatch(1);

        logger.info("TopicSubscriber is connecting to Solace messaging at %s...%n", cadsConfig.getSolaceHost());

        SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();
        connectionFactory.setHost(cadsConfig.getSolaceHost());
        connectionFactory.setVPN(cadsConfig.getSolaceVPN());
        connectionFactory.setUsername(cadsConfig.getSolaceUser());
        connectionFactory.setPassword(cadsConfig.getSolacePassword());
        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        logger.info("Connected to Solace Message VPN '%s' with client username '%s'.%n", cadsConfig.getSolaceVPN(),
                cadsConfig.getSolaceUser());

        Topic topic = session.createTopic(cadsConfig.getSolaceTopic());

        MessageConsumer messageConsumer = session.createConsumer(topic);

        try {
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        byte[] messageContent = SolJmsUtility.toByteArray(message);
                        Path pathToFile = Paths.get(cadsConfig.getTargetDirectory());
                        Files.write(pathToFile, messageContent);
                        logger.info("Message received.");
                        latch.countDown();
                    } catch (JMSException ex) {
                        logger.error("Error processing incoming message.", ex);
                    } catch (IOException ex) {
                        logger.error("Error writing incoming message.", ex);
                    }
                }
            });

            connection.start();
            logger.info("Awaiting message...");
            latch.await();

            connection.stop();
        } catch (Exception ex) {
            logger.error("Error writing incoming message.", ex);
        } finally {
            messageConsumer.close();
            session.close();
            connection.close();
        }
    }

}

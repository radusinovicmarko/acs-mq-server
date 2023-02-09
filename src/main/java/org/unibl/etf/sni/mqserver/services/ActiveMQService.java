package org.unibl.etf.sni.mqserver.services;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
public class ActiveMQService {
    private final JmsTemplate jmsTemplate;
    @Value("${mq.queue.receive}")
    private String queueReceiveName;

    @Autowired
    public ActiveMQService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "${mq.queue.send}")
    public void receiveMessage(TextMessage message) {
        try {
            sendMessage(message.getText());
        } catch (JMSException ignored) {
        }
    }

    private void sendMessage(String message) {
        try {
            jmsTemplate.convertAndSend(new ActiveMQQueue(queueReceiveName), message);
        } catch (Exception ignored) {
        }
    }
}

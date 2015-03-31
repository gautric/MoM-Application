/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.a.g.jee.mom.ejb;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.a.g.jee.mom.service.MoMSender;

import org.slf4j.Logger;

/**
 * 
 * Class MoMSenderBean
 * 
 * @author gautric <gautric __at__redhat __dot__ com>
 *
 */
@Stateless
public class MoMSenderBean implements MoMSender {

	@Inject
	private Logger LOGGER;

	@Resource(name = "jms/connectionFactory")
	ConnectionFactory factory;

	@Resource(name = "jms/queue/Queue")
	Queue queue;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.a.g.jee.mom.mdb.MoMSender#sendMessage(java.lang.String)
	 */
	@Override
	public void sendMessage(String message) throws JMSException {
		LOGGER.trace("Factory : {}", factory);
		LOGGER.trace("Queue : {}", queue);

		String messageToSend = message;
		Connection connection = factory.createConnection();
		LOGGER.trace("Recuperation d'une connexion {}", connection);

		Session session = connection.createSession(false, AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(queue);
		LOGGER.trace("Creation du sender {} sur la queue {}", producer, queue);

		TextMessage textMessage = session.createTextMessage(messageToSend);
		textMessage.setJMSCorrelationID(UUID.randomUUID().toString());
		producer.send(textMessage);
		producer.close();
		session.close();
		connection.close();
	}
}

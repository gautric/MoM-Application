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
package net.a.g.jee.mom.ws;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.a.g.jee.mom.cdi.EventMessage;
import net.a.g.jee.mom.service.MoMSender;

import org.slf4j.Logger;

/**
 * 
 * Class MoMWebsocket
 * 
 * @author gautric <gautric __at__redhat __dot__ com>
 *
 */
@ServerEndpoint("/websocket")
public class MoMWebsocket implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6063001539204682520L;

	@Inject
	private Logger LOGGER;

	@Inject
	private MoMSender senderBean;

	@Inject
	@EventMessage
	Event<String> event;

	private static final Set<Session> sessions = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(final Session session) {
		try {
			session.getBasicRemote().sendText("session opened");
			session.setMaxIdleTimeout(-1);
			sessions.add(session);
			if (senderBean == null) {
				LOGGER.info("senderBean is null");
			}
		} catch (Exception ex) {
			LOGGER.error("", ex);
		}
	}

	@OnMessage
	public void onMessage(final String message, final Session client)
			throws JMSException {
		try {
			client.getBasicRemote().sendText(
					"sending message to SessionBean...");
		} catch (IOException ex) {
			LOGGER.error("", ex);
		}
		if (senderBean != null) {
			senderBean.sendMessage(message);
			// event.fire(message);
		}
	}

	@OnClose
	public void onClose(final Session session) {
		LOGGER.error("onClose()" + session);
		try {
			sessions.remove(session);
		} catch (Exception ex) {
			LOGGER.error("", ex);
		}
	}

	@Asynchronous
	public void onMoMMessage(@Observes @EventMessage String msg)
			throws IOException {
		LOGGER.info("Got Event Message to send WebSocket!" + msg);
		for (Session session : sessions) {
			try {
				if (session.isOpen()) {
					session.getBasicRemote().sendText(
							"message from JMS: " + msg);
				}
			} catch (IllegalStateException e) {
				LOGGER.error("", e);
				sessions.remove(session);
			}

		}
	}
}
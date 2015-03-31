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
package net.a.g.jee.mom.mdb;


import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.a.g.jee.mom.cdi.EventMessage;

import org.slf4j.Logger;

@Named
@Singleton
public class MoMMDB implements MessageListener {

	@Inject
	private Logger LOGGER;


	@Inject
	@EventMessage
	Event<String> event;

	public void onMessage(Message message) {
		LOGGER.info("Received Message from queue: {}" + (TextMessage) message);

		assert (event != null);

		try {
			event.fire(((TextMessage) message).getText().toString()
					+ "--> Fired");
		} catch (JMSException e) {
			
			e.printStackTrace();
		}

	}
}

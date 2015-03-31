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
package net.a.g.jee.mom.rs;

import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.NamingException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import net.a.g.jee.mom.service.MoMSender;

import org.slf4j.Logger;

/**
 * 
 * Class MoMSenderResource
 * 
 * @author gautric <gautric __at__redhat __dot__ com>
 *
 */
@Path("/")
@Stateless
@TransactionAttribute(NOT_SUPPORTED)
public class MoMSenderResource {

	@Inject
	private Logger LOGGER;

	@Resource(name = "jms/connectionFactory")
	ConnectionFactory factory;

	@Resource(name = "jms/queue/Queue")
	Queue queue;
	
	@Inject
	private MoMSender senderBean;

	@GET
	@Path("/send")
	public Response sendService(
			@QueryParam("message") @DefaultValue("Hello") String message)
			throws JMSException, NamingException {
		senderBean.sendMessage(message);
		LOGGER.debug("Message sent {}", message);
		return Response.status(200).entity(message).build();
	}
}

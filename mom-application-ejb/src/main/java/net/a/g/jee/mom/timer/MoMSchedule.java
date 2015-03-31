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
package net.a.g.jee.mom.timer;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;
import javax.jms.JMSException;

import net.a.g.jee.mom.service.MoMSender;

import org.slf4j.Logger;

@Stateless
public class MoMSchedule {

	@Inject
	private Logger LOGGER;
	
	@Inject
	private MoMSender senderBean;

	@SuppressWarnings("unused")
	@Schedule(second = "0,30", minute = "*", hour = "*", dayOfWeek = "*", dayOfMonth = "*", month = "*", year = "*", info = "MyTimer")
	public void scheduled(final Timer t) throws JMSException {
		LOGGER.debug("@Schedule called at: " + new java.util.Date());
		senderBean.sendMessage("@Schedule called at: " + new java.util.Date());
	}
}
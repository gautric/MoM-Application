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

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.slf4j.Logger;

@Singleton
@Remote(net.a.g.jee.mom.service.MoMTimeout.class)
public class MoMTimeout implements net.a.g.jee.mom.service.MoMTimeout {

	@Inject
	private Logger LOGGER;
	
	@Resource
	TimerService timerService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.a.g.jee.mom.mdb.MoMTimeout#setTimer(long)
	 */
	@Override
	public void setTimer(long intervalDuration) {
		LOGGER.info("Setting a programmatic timeout for " + intervalDuration
				+ " milliseconds from now.");
		Timer timer = timerService.createTimer(intervalDuration,
				"Created new programmatic timer");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.a.g.jee.mom.mdb.MoMTimeout#timeout(javax.ejb.Timer)
	 */
	@Override
	@Timeout
	public void timeout(Timer timer) {
		LOGGER.info("TimerBean: timeout occurred");
	}

}

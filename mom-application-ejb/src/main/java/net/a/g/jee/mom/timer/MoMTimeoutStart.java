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

import java.io.FileInputStream;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

import net.a.g.jee.mom.service.MoMTimeout;

public class MoMTimeoutStart {

	public static void main(String[] args) throws Exception {

		Properties prop = new Properties();
		prop.load(new FileInputStream(
				"src/main/resources/jndi-client.properties"));

		InitialContext ctx = new InitialContext(prop);
		NamingEnumeration<NameClassPair> list = ctx
				.list("mom-application-ear/mom-application-ejb-0.0.1-SNAPSHOT/");
		while (list.hasMore()) {
			System.out.println(list.next().getName());
		}

		MoMTimeout timer = (MoMTimeout) ctx
				.lookup("mom-application-ear/mom-application-ejb-0.0.1-SNAPSHOT/MoMTimeout!net.a.g.jee.mom.service.MoMTimeout");

		timer.setTimer(5000);
	}

}

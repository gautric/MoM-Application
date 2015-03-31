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
package net.a.g.jee.mom.util;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoMUtil {

	private static final Logger LOG = LoggerFactory.getLogger(MoMUtil.class);

	/**
	 * 
	 * jndiTreeView
	 * 
	 * adaptation from http://denistek.blogspot.fr/2008/08/list-jndi-names.html
	 * 
	 * @author gautric
	 *
	 */
	public static final void jndiTreeView(String name) {
		Context ctx;
		try {
			ctx = (Context) new InitialContext().lookup(name);
			listContext(ctx, "");
		} catch (NamingException e) {
			LOG.info("JNDI failure: ", e);
		}

	};

	/**
	 * Recursively exhaust the JNDI tree
	 * 
	 * @throws NamingException
	 */
	private static final void listContext(Context ctx, String indent)
			throws NamingException {

		NamingEnumeration list = ctx.listBindings("");
		while (list.hasMore()) {
			Binding item = (Binding) list.next();
			String className = item.getClassName();
			String name = item.getName();
			LOG.info(indent + className + " " + name);
			Object o = item.getObject();
			if (o instanceof javax.naming.Context) {
				listContext((Context) o, indent + " ");
			}
		}
	}
}

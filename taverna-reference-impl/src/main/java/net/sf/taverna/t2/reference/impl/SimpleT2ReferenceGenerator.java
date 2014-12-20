/*******************************************************************************
 * Copyright (C) 2007 The University of Manchester   
 * 
 *  Modifications to the initial code base are copyright of their
 *  respective authors, or their employers as appropriate.
 * 
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *    
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *    
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 ******************************************************************************/
package net.sf.taverna.t2.reference.impl;

import net.sf.taverna.t2.reference.T2ReferenceGenerator;

/**
 * Simple implementation of T2ReferenceGenerator intended to be injected into
 * the service layers for integration testing. Exposes a namespace property
 * which can be configured through spring and allocates local parts based on an
 * integer counter - this is only guaranteed to be unique within a single
 * instance of this object so isn't suitable for real production use. For proper
 * usage use an implementation tied to the backing store you're putting t2
 * reference objects into.
 * 
 * @author Tom Oinn
 */
public class SimpleT2ReferenceGenerator extends AbstractT2ReferenceGenerator implements T2ReferenceGenerator {
	private String namespace = "testNS";
	private String localPrefix = "test";
	private int counter = 0;

	/**
	 * Set the namespace for identifiers generated by this class as a string
	 * 
	 * @param newNamespace
	 *            the namespace to use
	 */
	public void setNamespace(String newNamespace) {
		this.namespace = newNamespace;
	}

	/**
	 * Get the namespace for identifiers generated by this class
	 */
	@Override
	public String getNamespace() {
		return namespace;
	}

	@Override
	protected synchronized String getNextLocalPart() {
		return localPrefix + (counter++);
	}
}

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
package org.apache.taverna.provenance.lineageservice.types;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


/**
 * 
 * @author Paolo Missier
 * 
 */
public class ProcessType implements ProvenanceEventType {
	private ProcessorType[] processor;
	private String dataflowID; // attribute
	private String facadeID; // attribute

	public ProcessType() {
	}

	public ProcessType(ProcessorType[] processor, String dataflowID,
			String facadeID) {
		this.processor = processor;
		this.dataflowID = dataflowID;
		this.facadeID = facadeID;
	}

	/**
	 * Gets the processor value for this ProcessType.
	 * 
	 * @return processor
	 */
	public ProcessorType[] getProcessor() {
		return processor;
	}

	/**
	 * Sets the processor value for this ProcessType.
	 * 
	 * @param processor
	 */
	public void setProcessor(ProcessorType[] processor) {
		this.processor = processor;
	}

	public ProcessorType getProcessor(int i) {
		return this.processor[i];
	}

	public void setProcessor(int i, ProcessorType _value) {
		this.processor[i] = _value;
	}

	/**
	 * Gets the dataflowID value for this ProcessType.
	 * 
	 * @return dataflowID
	 */
	public String getDataflowID() {
		return dataflowID;
	}

	/**
	 * Sets the dataflowID value for this ProcessType.
	 * 
	 * @param dataflowID
	 */
	public void setDataflowID(String dataflowID) {
		this.dataflowID = dataflowID;
	}

	/**
	 * Gets the facadeID value for this ProcessType.
	 * 
	 * @return facadeID
	 */
	public String getFacadeID() {
		return facadeID;
	}

	/**
	 * Sets the facadeID value for this ProcessType.
	 * 
	 * @param facadeID
	 */
	public void setFacadeID(String facadeID) {
		this.facadeID = facadeID;
	}

}

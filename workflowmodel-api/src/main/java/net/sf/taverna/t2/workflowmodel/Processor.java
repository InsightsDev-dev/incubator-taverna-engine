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
package net.sf.taverna.t2.workflowmodel;

import static net.sf.taverna.t2.annotation.HierarchyRole.CHILD;

import java.util.List;

import net.sf.taverna.t2.annotation.Annotated;
import net.sf.taverna.t2.annotation.HierarchyTraversal;
import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.lang.observer.Observable;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import net.sf.taverna.t2.workflowmodel.processor.dispatch.DispatchStack;
import net.sf.taverna.t2.workflowmodel.processor.iteration.IterationStrategyStack;

/**
 * A single node within the dataflow digraph, the Processor is the basic
 * functional unit within a Taverna workflow. It should also notify interested
 * observers when it has finished execution (including all iterations of the processor).
 * 
 * @author Tom Oinn
 * @author Alex Nenadic
 * 
 */
@ControlBoundary
public interface Processor extends TokenProcessingEntity, Annotated<Processor>, Observable<ProcessorFinishedEvent>, WorkflowItem  {

	/**
	 * The iteration strategy is responsible for combining input data events
	 * into jobs which are then queued for execution through the dispatch stack
	 * 
	 * @return IterationStrategyStack containing one or more IterationStrategy
	 *         objects. In most cases this will only contain a single
	 *         IterationStrategy but there are particular scenarios where
	 *         staging partial iteration strategies together is the only way to
	 *         get the desired combination of inputs
	 */
	@HierarchyTraversal(hierarchies = { "workflowStructure" }, role = { CHILD })
	public IterationStrategyStack getIterationStrategy();

	/**
	 * Each processor has a list of zero or more input ports. These are uniquely
	 * named within the list. Any input port may have a default value associated
	 * with it and may be attached to exactly one upstream output port. Where it
	 * is necessary to connect a single input port to multiple output ports a
	 * Merge object is used. Ordering within the list is not meaningful but we
	 * use List rather than Set to preserve the ordering across serialisation
	 * operations.
	 * <p>
	 * Processor inputs are instances of FilteringInputPort - they must have the
	 * filter depth set before any data events arrive at the Processor. In
	 * addition they assume that a full collection will be supplied, i.e. that
	 * there will be exactly one event at the end of the list of events for a
	 * given process ID with an index array of length zero.
	 * 
	 * @return List of named input ports
	 */
	@HierarchyTraversal(hierarchies = { "workflowStructure" }, role = { CHILD })
	public List<? extends ProcessorInputPort> getInputPorts();

	/**
	 * Each processor has a list of zero or more output ports. Output ports are
	 * uniquely named within the list and may be connected to arbitrarily many
	 * downstream input ports or Merge objects. Ordering within the list is not
	 * meaningful but we use List rather than Set to preserve the ordering
	 * across serialisation operations.
	 * 
	 * @return List of named output ports
	 */
	@HierarchyTraversal(hierarchies = { "workflowStructure" }, role = { CHILD })
	public List<? extends ProcessorOutputPort> getOutputPorts();

	/**
	 * The dispatch stack pulls jobs from the queue generated by the iteration
	 * system and handles the dispatch of these jobs to appropriate activity
	 * workers
	 * 
	 * @return the DispatchStackImpl for this processor
	 */
	@HierarchyTraversal(hierarchies = { "workflowStructure" }, role = { CHILD })
	public DispatchStack getDispatchStack();

	/**
	 * A processor contains zero or more activities in an ordered list. To be
	 * any use in a workflow the processor should contain at least one activity
	 * but it's technically valid to have none! Activities may be abstract or
	 * concrete where an abstract activity is one with no invocation mechanism,
	 * in these cases additional logic must be added to the dispatch stack of
	 * the containing processor to convert these to concrete invokable
	 * activities during the workflow invocation.
	 * 
	 * @return list of Activity instances
	 */
	@HierarchyTraversal(hierarchies = { "workflowStructure" }, role = { CHILD })
	public List<? extends Activity<?>> getActivityList();

	/**
	 * A processor with no inputs cannot be driven by the supply of data tokens
	 * as it has nowhere to receive such tokens. This method allows a processor
	 * to fire on an empty input set, in this case the owning process identifier
	 * must be passed explicitly to the processor. Internally this pushes a
	 * single empty job event into the dispatch queue, bypassing the iteration
	 * logic (which is entirely predicated on the existence of input ports).
	 * Callers must ensure that an appropriate process identifier is specified,
	 * the behaviour on missing or duplicate process identifiers is not defined.
	 */
	public void fire(String owningProcess, InvocationContext context);

	/**
	 * A processor has zero or more preconditions explicitly declared. All such
	 * preconditions must be satisfied before any jobs are passed into the
	 * dispatch stack. These preconditions replace and generalise the
	 * coordination constraints from Taverna 1.
	 * 
	 * @return a List of Condition objects defining constraints on this
	 *         processor's execution
	 */
	@HierarchyTraversal(hierarchies = { "workflowStructure" }, role = { CHILD })
	public List<? extends Condition> getPreconditionList();

	/**
	 * A processor may control zero or more other processors within the same
	 * level of the workflow through preconditions.
	 * 
	 * @return a List of Condition objects for which this is the controlling
	 *         processor
	 */
	public List<? extends Condition> getControlledPreconditionList();

}

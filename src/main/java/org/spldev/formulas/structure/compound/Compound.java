package org.spldev.formulas.structure.compound;

import java.util.*;

import org.spldev.formulas.structure.*;

/**
 * A logical connector that is {@code true} iff all of its children are
 * {@code true}.
 *
 * @author Sebastian Krieter
 */
public abstract class Compound extends NonTerminal implements Formula {

	public Compound(Collection<Formula> nodes) {
		super(nodes);
	}

	public Compound(Formula... nodes) {
		super(nodes);
	}

	protected Compound() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Formula> getChildren() {
		return (List<Formula>) super.getChildren();
	}

}

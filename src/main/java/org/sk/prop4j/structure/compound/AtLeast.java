package org.sk.prop4j.structure.compound;

import java.util.List;

import org.sk.prop4j.structure.Formula;

/**
 * A logical connector that is {@code true} iff at least a given number of its
 * children are {@code true}.
 *
 * @author Sebastian Krieter
 */
public class AtLeast extends Cardinal {

	public AtLeast(List<Formula> nodes, int min) {
		super(nodes, min, Integer.MAX_VALUE);
	}

	private AtLeast(AtLeast oldNode) {
		super(oldNode);
	}

	@Override
	public AtLeast clone() {
		return new AtLeast(this);
	}

	@Override
	public String getName() {
		return "atLeast-" + min;
	}

	@Override
	public int getMin() {
		return super.getMin();
	}

	@Override
	public void setMin(int min) {
		super.setMin(min);
	}

}

/*
 * Copyright (C) 2023 FeatJAR-Development-Team
 *
 * This file is part of FeatJAR-formula.
 *
 * formula is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * formula is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with formula. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatJAR> for further information.
 */
package de.featjar.formula.analysis.bool;

import de.featjar.base.computation.IComputation;
import de.featjar.base.data.Result;
import de.featjar.formula.analysis.IClause;
import de.featjar.formula.analysis.ISolver;
import de.featjar.formula.analysis.VariableMap;
import de.featjar.formula.analysis.value.ValueClause;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * A Boolean clause; that is, a disjunction of literals. Implemented as a sorted
 * list of indices. Often used as input to a SAT {@link ISolver}. Indices are
 * ordered naturally; that is, in ascending order, so negative indices come
 * before positive indices. The same index may occur multiple times, but no
 * element may be 0.
 *
 * @author Sebastian Krieter
 * @author Elias Kuiter
 */
public class BooleanClause extends ABooleanAssignment implements IClause<Integer, Boolean> {

    public BooleanClause(int... integers) {
        this(integers, true);
        assert Arrays.stream(integers).noneMatch(a -> a == 0) : "contains zero: " + Arrays.toString(integers);
    }

    public BooleanClause(int[] integers, boolean sort) {
        super(integers);
        assert Arrays.stream(integers).noneMatch(a -> a == 0) : "contains zero: " + Arrays.toString(integers);
        assert sort
                        || Arrays.stream(integers)
                                        .reduce((a, b) -> a != 0 && a < b ? b : 0)
                                        .orElse(1)
                                != 0
                : "unsorted: " + Arrays.toString(integers);
        if (sort) sort();
    }

    public BooleanClause(Collection<Integer> integers) {
        super(integers);
        assert integers.stream().noneMatch(a -> a == 0) : "contains zero: " + integers.toString();
        sort();
    }

    public BooleanClause(BooleanClause booleanClause) {
        super(booleanClause);
        // TODO implement as test
        assert Objects.equals(this, booleanClause) : this + " != " + booleanClause;
    }

    protected void sort() {
        hashCodeValid = false;
        Arrays.sort(elements);
    }

    @Override
    public int[] getNonZeroValues() {
        assert Arrays.stream(elements).noneMatch(a -> a == 0) : "contains zero: " + Arrays.toString(elements);
        return copy();
    }

    @Override
    public int countNonZero() {
        assert Arrays.stream(elements).noneMatch(a -> a == 0) : "contains zero: " + Arrays.toString(elements);
        return size();
    }

    @Override
    public int countNegatives() {
        return -(Arrays.binarySearch(elements, 0) + 1);
    }

    @Override
    public int countPositives() {
        return elements.length + (Arrays.binarySearch(elements, 0) + 1);
    }

    @Override
    public int[] getPositiveValues() {
        return Arrays.copyOfRange(elements, elements.length - countPositives(), elements.length);
    }

    @Override
    public int[] getNegativeValues() {
        return Arrays.copyOfRange(elements, 0, countNegatives());
    }

    @Override
    public int indexOf(int integer) {
        return Arrays.binarySearch(elements, integer);
    }

    @Override
    public int[] indicesOf(int integer) {
        return new int[] {Arrays.binarySearch(elements, integer)};
    }

    @Override
    public BooleanClause inverse() {
        final int[] inverse = new int[elements.length];
        final int highestIndex = inverse.length - 1;
        for (int i = 0; i < inverse.length; i++) {
            inverse[highestIndex - i] = -elements[i];
        }
        return new BooleanClause(inverse, false);
    }

    @Override
    public Result<ValueClause> toValue(VariableMap variableMap) {
        return variableMap.toValue(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IComputation<ValueClause> toValue(IComputation<VariableMap> variableMap) {
        return (IComputation<ValueClause>) super.toValue(variableMap);
    }

    public String print() {
        return VariableMap.toAnonymousValue(this).get().print();
    }

    @Override
    public String toString() {
        return String.format("BooleanClause[%s]", print());
    }

    @Override
    public BooleanClause toClause() {
        return this;
    }

    @Override
    public BooleanClause addAll(ABooleanAssignment integers) {
        return new BooleanClause(addAll(integers.get()));
    }

    @Override
    public BooleanClause retainAll(ABooleanAssignment integers) {
        return new BooleanClause(retainAll(integers.get()));
    }

    @Override
    public BooleanClause retainAllVariables(ABooleanAssignment integers) {
        return new BooleanClause(retainAllVariables(integers.get()));
    }

    @Override
    public BooleanClause removeAll(ABooleanAssignment integers) {
        return new BooleanClause(removeAll(integers.get()));
    }

    @Override
    public BooleanClause removeAllVariables(ABooleanAssignment integers) {
        return new BooleanClause(removeAllVariables(integers.get()));
    }
}

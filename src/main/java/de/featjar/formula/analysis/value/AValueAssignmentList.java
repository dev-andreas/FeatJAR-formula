/*
 * Copyright (C) 2023 Sebastian Krieter, Elias Kuiter
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
 * See <https://github.com/FeatureIDE/FeatJAR-formula> for further information.
 */
package de.featjar.formula.analysis.value;

import de.featjar.base.data.Sets;
import de.featjar.base.io.IO;
import de.featjar.formula.analysis.IAssignmentList;
import de.featjar.formula.io.value.ValueAssignmentListFormat;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A list of value assignments.
 *
 * @param <T> the type of the literal list
 * @author Elias Kuiter
 */
public abstract class AValueAssignmentList<T extends AValueAssignment> implements IAssignmentList<T>, IValueRepresentation {
    protected final List<T> literalLists;

    public AValueAssignmentList() {
        literalLists = new ArrayList<>();
    }

    public AValueAssignmentList(int size) {
        literalLists = new ArrayList<>(size);
    }

    public AValueAssignmentList(Collection<? extends T> literalLists) {
        this.literalLists = new ArrayList<>(literalLists);
    }

    public AValueAssignmentList(AValueAssignmentList<T> other) {
        this(other.getAll());
    }

    @Override
    public List<T> getAll() {
        return literalLists;
    }

    @Override
    public ValueAssignmentList toAssignmentList() {
        return new ValueAssignmentList(
                literalLists.stream().map(AValueAssignment::toAssignment).collect(Collectors.toList()));
    }

    @Override
    public ValueClauseList toClauseList() {
        return new ValueClauseList(
                literalLists.stream().map(AValueAssignment::toClause).collect(Collectors.toList()));
    }

    @Override
    public ValueSolutionList toSolutionList() {
        return new ValueSolutionList(
                literalLists.stream().map(AValueAssignment::toSolution).collect(Collectors.toList()));
    }

    @Override
    public LinkedHashSet<String> getVariableNames() {
        return literalLists.stream()
                .map(AValueAssignment::getVariableNames)
                .flatMap(LinkedHashSet::stream)
                .collect(Sets.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AValueAssignmentList<?> that = (AValueAssignmentList<?>) o;
        return Objects.equals(literalLists, that.literalLists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(literalLists);
    }

    abstract public String print();
}

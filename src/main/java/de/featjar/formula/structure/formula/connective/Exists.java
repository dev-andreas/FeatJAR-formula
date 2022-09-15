/*
 * Copyright (C) 2022 Sebastian Krieter, Elias Kuiter
 *
 * This file is part of formula.
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
package de.featjar.formula.structure.formula.connective;

import de.featjar.formula.structure.Expression;
import de.featjar.formula.structure.term.value.Variable;

/**
 * Expresses "exists X such that A" constraints (i.e., existential quantification).
 * Evaluates to {@code true} iff there is a value of the bound variable such that the formula evaluates to {@code true}.
 *
 * @author Sebastian Krieter
 */
public class Exists extends Quantifier {
    //todo: rewrite CNFSlicer to work on a QBF
    protected Exists(Exists exists) {
        super(exists);
    }

    public Exists(Variable boundVariable, Expression expression) {
        super(boundVariable, expression);
    }

    @Override
    public String getName() {
        return "exists-" + boundVariable;
    }

    @Override
    public Exists cloneNode() {
        return new Exists(this);
    }
}
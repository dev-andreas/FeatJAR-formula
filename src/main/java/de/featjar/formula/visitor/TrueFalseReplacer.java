package de.featjar.formula.visitor;

import de.featjar.base.data.Result;
import de.featjar.base.data.Void;
import de.featjar.base.tree.visitor.ITreeVisitor;
import de.featjar.formula.structure.IFormula;
import de.featjar.formula.structure.connective.IConnective;
import de.featjar.formula.structure.predicate.False;
import de.featjar.formula.structure.predicate.IPredicate;
import de.featjar.formula.structure.predicate.Literal;
import de.featjar.formula.structure.predicate.True;

import java.util.List;
import java.util.Map;

/**
 * Replaces literals with true or false.
 *
 * @author Andreas Gerasimow
 */
public class TrueFalseReplacer implements ITreeVisitor<IFormula, Void> {

    Map<Literal, Boolean> literalValueList;

    public TrueFalseReplacer(Map<Literal, Boolean> literalValueList) {
        this.literalValueList = literalValueList;
    }

    @Override
    public TraversalAction firstVisit(List<IFormula> path) {
        final IFormula formula = ITreeVisitor.getCurrentNode(path);
        if (formula instanceof IPredicate) {
            return TraversalAction.SKIP_CHILDREN;
        } else if (formula instanceof IConnective) {
            return TraversalAction.CONTINUE;
        } else {
            return TraversalAction.FAIL;
        }
    }

    @Override
    public TraversalAction lastVisit(List<IFormula> path) {
        final IFormula formula = ITreeVisitor.getCurrentNode(path);
        formula.replaceChildren(c -> {
            if (c instanceof Literal && literalValueList.containsKey(c)) {
                return literalValueList.get(c) ? True.INSTANCE : False.INSTANCE;
            }
            return c;
        });
        return TraversalAction.CONTINUE;
    }

    @Override
    public Result<Void> getResult() {
        return Result.ofVoid();
    }
}

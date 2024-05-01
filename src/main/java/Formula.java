import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

public class Formula {
    private String formula;
    private boolean isEquality;

    private String rightPeace;
    private String leftPeace;

    public Formula(String formula) {
        formula = removeSpaces(formula);
        this.formula = formula;
        isEquality = formula.contains("=");
        if (isEquality) {
            rearrangeEquation();
        }
    }

    public String getFormula() {
        return formula;
    }

    public String removeSpaces(String text) {
        return text.replace(" ", "");
    }

    public void rearrangeEquation() {
        String[] parts = formula.split("=");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Error, it has more two peaces");
        }

        leftPeace = parts[0];
        rightPeace = parts[1];
    }

    public void simplifyFormula() {
        if (!isEquality) {
            formula = simplifyExpression(formula);
        } else {
            rightPeace = simplifyExpression(rightPeace);
            leftPeace = simplifyExpression(leftPeace);

            formula = leftPeace + "=" + leftPeace;
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static String simplifyExpression(String expression) {
        ExprEvaluator evaluator = new ExprEvaluator();
        IExpr result = evaluator.eval(F.ExpandAll(F.Simplify(evaluator.parse(expression))));

        return result.toString();
    }
}

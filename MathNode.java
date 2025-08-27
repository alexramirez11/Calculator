public class MathNode {

    private double firstOperand, secondOperand;
    private Operations op;
    private MathNode prev, next;
    private int priority = 0;
    private boolean isEvaluated;
    private String singleExp;
    
    public MathNode(String singleExp) {
        this.singleExp = singleExp;
        this.op = findOperator(this.singleExp);
        String[] split = splitForOperands(this.singleExp, op);
        this.firstOperand = toDouble(split[0]);
        this.secondOperand = toDouble(split[1]);
        prev = next = null;
        isEvaluated = false;
    }

    public double evaluate() {
        switch (this.op) {
            case PLUS:
                isEvaluated = true;
                return this.firstOperand + this.secondOperand;
            case MINUS:
                isEvaluated = true;
                return this.firstOperand - this.secondOperand;
            case MULTIPLY:
                isEvaluated = true;
                return this.firstOperand * this.secondOperand;
            case DIVIDE:
                if (this.secondOperand == 0) {
                    throw new ZeroException("divide");
                }
                isEvaluated = true;
                return this.firstOperand / this.secondOperand;
            case MODULO:
                if (this.secondOperand == 0) {
                    throw new ZeroException("mod");
                }
                isEvaluated = true;
                return this.firstOperand % this.secondOperand;
            case EXPONENT:
                isEvaluated = true;
                return Math.pow(this.firstOperand, this.secondOperand);
            default:
                throw new NoOperationException("expression: " + this.singleExp);
        }
    }

    private double toDouble(String numStr) {
        if (numStr.contains("N")) {
            return 0 - Double.parseDouble(numStr.replace("N", ""));
        } else {
            return Double.parseDouble(numStr);
        }
    }

    public boolean isIdentical(MathNode toCompare) {
        if ((this.firstOperand == toCompare.getFirstOperand()) && (this.secondOperand == toCompare.getSecondOperand()) && (this.op == toCompare.getOperation())) {
            return true;
        }
        return false;
    }

    public boolean isChainedByOperand(MathNode toCompare) {
        return this.secondOperand == toCompare.firstOperand;
    }

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public boolean hasPrevious() {
        return prev != null;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean isPrevious(MathNode toCompare) {
        return this.prev.isIdentical(toCompare);
    }

    public boolean isNext(MathNode toCompare) {
        return this.next.isIdentical(toCompare);
    }

    private Operations findOperator(String exp) {
        String[] find = exp.split("");
        for (int i = 0; i < find.length; i++) {
            String op = getBaseOperator(find[i]);
            if (op != null) {
                return translateOperation(op);
            }
        }
        return null;
    }

    private String getBaseOperator(String val) {
        if (val.compareTo("+") == 0 || val.compareTo("-") == 0 || val.compareTo("*") == 0 || val.compareTo("/") == 0 || val.compareTo("%") == 0 || val.compareTo("^") == 0) {
            return val;
        }
        return null;
    }

    private String[] splitForOperands(String exp, Operations operation) {
        String[] split;
        switch (operation) {
            case PLUS:
                split = exp.split("\\+");
                return split;
            case MINUS:
                split = exp.split("-");
                return split;
            case MULTIPLY:
                split = exp.split("\\*");
                return split;
            case DIVIDE:
                split = exp.split("/");
                return split;
            case MODULO:
                split = exp.split("%");
                return split;
            case EXPONENT:
                split = exp.split("^");
                return split;
            default:
                return null;
        }
    }

    private Operations translateOperation(String opToken) {
        if (opToken.compareTo("+") == 0) {
            priority = 2;
            return Operations.PLUS;
        } else if (opToken.compareTo("-") == 0) {
            priority = 2;
            return Operations.MINUS;
        } else if (opToken.compareTo("*") == 0) {
            priority = 1;
            return Operations.MULTIPLY;
        } else if (opToken.compareTo("/") == 0) {
            priority = 1;
            return Operations.DIVIDE;
        } else if (opToken.compareTo("%") == 0) {
            priority = 1;
            return Operations.MODULO;
        } else if (opToken.compareTo("^") == 0) {
            priority = 0;
            return Operations.EXPONENT;
        } else {
            priority = 99999;
            return Operations.NONE;
        }
    }

    public int getPriority() {
        return priority;
    }

    public double getFirstOperand() {
        return this.firstOperand;
    }

    public double getSecondOperand() {
        return this.secondOperand;
    }

    public Operations getOperation() {
        return this.op;
    }

    public void setFirstOperand(double num) {
        this.firstOperand = num;
    }

    public void setSecondOperand(double num) {
        this.secondOperand = num;
    }

    public void setOperation(Operations op) {
        this.op = op;
    }

    public void setNext(MathNode newNode) {
        next = newNode;
    }

    public MathNode getNext() {
        return next;
    }

    public void setPrevious(MathNode node) {
        prev = node;
    }

    public MathNode getPrevious() {
        return prev;
    }

    public String toString() {
        String str = "first operand: " + firstOperand + "\n";
        str += "operation: " + op + "\n";
        str += "second operand: " + secondOperand + "\n";
        return str; 
    }
}

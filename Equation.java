import java.util.LinkedList;

/**
 * This class represents a mathmatical equation, containing methods to evaluate the equation.
 * @author Alex Ramirez
 */
public class Equation {

    private String[] usable;
    private String expression;
    private MathList list = new MathList();
    private MathNode[] pemdas;
    
    /**
     * This method builds an equation object based off the given string.
     * @param expression - A string containing a mathmatical equation to be evaluated
     */
    public Equation(String expression) {
        this.expression = expression;
        usable = breakToChunks(breakDown(expression));
        for (int i = 0; i < usable.length; i++) {
            list.add(new MathNode(usable[i]));
        }
        pemdas = list.sortToPemdas();
    }

    /**
     * This method breaks down the equation into individual numbers and operators.
     * @param expression - The expression that needs to be broken down
     * @return A string array
     * @throws NoOperationException if there is operator found in the resulting array
     */
    private String[] breakDown(String expression) {
        String[] temp = sanitize(expression.split(""));
        String[] toUse = new String[temp.length];
        int blockCount = 0, opCount = 0;
        String str = "";
        for (int i = 0; i < temp.length; i++) {
            if (isBaseOperator(temp[i])) {
                opCount++;
                toUse[blockCount] = str;
                blockCount++;
                toUse[blockCount] = temp[i];
                blockCount++;
                str = "";
            } else {
                if (temp[i].compareTo("N") == 0) {
                    str = "N";
                } else {
                    str += temp[i];
                    if (i == (temp.length - 1)) {
                        toUse[blockCount] = str;
                    }
                }
            }
        }
        if (opCount < 1) {
            throw new NoOperationException(expression);
        }

        return trimArray(toUse);
    }

    /**
     * This method evaluates the equation given to the Equation object's constructor.
     * @return A double represeting the answer to the equation
     */
    public double evaluate() {
        return evalExponent(pemdas, list)[0].evaluate();
    }

    private String[] breakToChunks(String[] arr) {
        String[] res = new String[arr.length];
        String endStr = arr[0];
        LinkedList<String> list = new LinkedList<>();

        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }

        res[0] = list.removeFirst() + list.removeFirst();
        endStr = list.removeFirst();
        res[0] += endStr;
        int blocks = 1;
        while (!list.isEmpty()) {
            res[blocks] = endStr + list.removeFirst();
            endStr = list.removeFirst();
            res[blocks] += endStr;
            blocks++;
        }

        for (String s : res) {
            if (s != null) {
                System.out.println(s);
            }
        }

        return trimArray(res);
    }

    private MathNode[] evalExponent(MathNode[] arr, MathList list) {
        if (arr.length == 1) {
            return arr;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].hasNext() && arr[i].getPriority() == 0 && (arr[i].getNext().getPriority() == arr[i].getPriority())) {
                if (!arr[i].isEvaluated()) {
                    double res = arr[i].evaluate();
                    arr[i].getNext().setFirstOperand(res);
                    if (arr[i].hasPrevious()) {
                        arr[i].getPrevious().setSecondOperand(res);
                    }
                    list.remove(arr[i]);
                }
            } else if (arr[i].hasNext() && arr[i].getPriority() == 0 && (arr[i].getNext().getPriority() > arr[i].getPriority())) {
                if (!arr[i].isEvaluated()) {
                    double res = arr[i].evaluate();
                    arr[i].getNext().setFirstOperand(res);
                    if (arr[i].hasPrevious()) {
                        arr[i].getPrevious().setSecondOperand(res);
                    }
                    list.remove(arr[i]);
                }
            } else if (!arr[i].hasNext() && arr[i].hasPrevious() && arr[i].getPriority() == 0 && (arr[i].getPrevious().getPriority() > arr[i].getPriority())) {
                if (!arr[i].isEvaluated()) {
                    double result = arr[i].evaluate();
                    arr[i].getPrevious().setSecondOperand(result);
                    list.remove(arr[i]);
                }
            }
        }

        return evalMultDivMod(list.sortToPemdas(), list);
    }

    private MathNode[] evalMultDivMod(MathNode[] arr, MathList list) {
        if (arr.length == 1) {
            return arr;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].hasNext() && arr[i].getPriority() == 1 && (arr[i].getNext().getPriority() == arr[i].getPriority())) {
                if (!arr[i].isEvaluated()) {
                    double result = 0.0;
                    result = arr[i].evaluate();
                    arr[i].getNext().setFirstOperand(result);
                    if (arr[i].hasPrevious()) {
                        arr[i].getPrevious().setSecondOperand(result);
                    }
                    list.remove(arr[i]);
                }
            } else if (arr[i].hasNext() && arr[i].getPriority() == 1 && (arr[i].getNext().getPriority() > arr[i].getPriority())) {
                if (!arr[i].isEvaluated()) {
                    double result = 0.0;
                    result = arr[i].evaluate();
                    arr[i].getNext().setFirstOperand(result);
                    if (arr[i].hasPrevious()) {
                        arr[i].getPrevious().setSecondOperand(result);
                    }
                    list.remove(arr[i]);
                }
            } else if (!arr[i].hasNext() && arr[i].hasPrevious() && arr[i].getPriority() == 1 && (arr[i].getPrevious().getPriority() > arr[i].getPriority())) {
                if (!arr[i].isEvaluated()) {
                    double result = 0.0;
                    result = arr[i].evaluate();
                    arr[i].getPrevious().setSecondOperand(result);
                    list.remove(arr[i]);
                }
            }
        }
        return evalPlusMinus(list.sortToPemdas(), list);
    }

    private MathNode[] evalPlusMinus(MathNode[] arr, MathList list) {
        if (arr.length == 1) {
            return arr;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].hasNext() && arr[i].getPriority() == 2 && (arr[i].isChainedByOperand(arr[i].getNext()))) {
                double result = arr[i].evaluate();
                arr[i].getNext().setFirstOperand(result);
                list.remove(arr[i]);
            }
        }

        return list.sortToPemdas();
    }

    private String[] trimArray(String[] expArray) {
        int count = 0;
        for (String s : expArray) {
            if (s != null) {
                count++;
            }
        }
        String[] returned = new String[count];
        for (int i = 0; i < count; i++) {
            returned[i] = expArray[i];
        }
        return returned;
    }

    private String[] sanitize(String[] toClean) {
        String[] clean = new String[toClean.length];
        if (isBaseOperator(toClean[0]) && toClean[0].compareTo("-") != 0) {
            throw new NumberFormatException();
        }
        if (toClean[0].compareTo("-") == 0) {
            toClean[0] = "N";
        }
        for (int i = 0; i < toClean.length; i++) {
            if (isBaseOperator(toClean[i]) && isBaseOperator(toClean[i - 1]) && toClean[i].compareTo("-") == 0) {
                clean[i] = "N";
            } else if (isBaseOperator(toClean[i]) && isBaseOperator(toClean[i - 1]) && toClean[i].compareTo("-") != 0) {
                throw new NumberFormatException();
            } else {
                clean[i] = toClean[i].trim();
            }
        }
        
        return clean;
    }

    private boolean isBaseOperator(String val) {
        if (val.compareTo("+") == 0 || val.compareTo("-") == 0 || val.compareTo("*") == 0 || val.compareTo("/") == 0 || val.compareTo("%") == 0 || val.compareTo("^") == 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.expression;
    }
}

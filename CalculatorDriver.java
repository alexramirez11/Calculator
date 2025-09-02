import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author Alex Ramirez
 */

public class CalculatorDriver extends Application {

    private Label resultLabel, prevResult, infoLabel;
    private Button one, two, three, four, five, six, seven, eight, nine, zero, decimal, clearAll, clear, plus, minus, multiply, divide, operation, prev, sqrt, squared, nextMenu;
    private Button ln, log, factorial, modulo, sin, cos, tan, csc, data, median, swapMenuBack, pi, comma, mean, undo, info, back, sd, exponent;
    private VBox biggerBox, infoMenu;
    private HBox firstRow, secondRow, thirdRow, fourthRow, fifthRow, topBar, prevBar, sixthRow, page2FirstRow, page2SecondRow, page2ThirdRow, page2FourthRow;
    private String resultText = "", tempString = "", pattern = "#.#####", prevString = "", infoString = "";
    private List<String> stringList = new LinkedList<String>();
    private String[] dataStringArray;
    private double[] dataArray;
    private double tempResult = 0;
    private DecimalFormat forDecimal;
    private boolean isAllCleared = true;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage calcStage) throws Exception {

        forDecimal = new DecimalFormat(pattern);
        
        resultLabel = new Label(resultText);
        resultLabel.setId("resultLabel");
        resultLabel.setFont(new Font(48));
        resultLabel.setPrefHeight(100);
        resultLabel.setPrefWidth(500);
        resultLabel.setAlignment(Pos.CENTER);

        one = new Button("1");
        one.setOnAction(this::processOne);
        two = new Button("2");
        two.setOnAction(this::processTwo);
        three = new Button("3");
        three.setOnAction(this::processThree);
        four = new Button("4");
        four.setOnAction(this::processFour);
        five = new Button("5");
        five.setOnAction(this::processFive);
        six = new Button("6");
        six.setOnAction(this::processSix);
        seven = new Button("7");
        seven.setOnAction(this::processSeven);
        eight = new Button("8");
        eight.setOnAction(this::processEight);
        nine = new Button("9");
        nine.setOnAction(this::processNine);
        zero = new Button("0");
        zero.setOnAction(this::processZero);
        decimal = new Button(".");
        decimal.setOnAction(this::processDecimal);
        pi = new Button("π");
        pi.setOnAction(this::processPi);

        plus = new Button("+");
        plus.setId("opButton");
        plus.setOnAction(this::processPlus);
        minus = new Button("-");
        minus.setId("opButton");
        minus.setOnAction(this::processMinus);
        multiply = new Button("*");
        multiply.setId("opButton");
        multiply.setOnAction(this::processMultiply);
        divide = new Button("/");
        divide.setId("opButton");
        divide.setOnAction(this::processDivide);

        clear = new Button("C");
        clear.setId("clearButton");
        clear.setOnAction(this::processClear);
        clearAll = new Button("AC");
        clearAll.setId("clearButton");
        clearAll.setOnAction(this::processClearAll);

        operation = new Button("=");
        operation.setId("equalsButton");
        operation.setOnAction(this::processEquals);

        prev = new Button("Prev");
        prev.setOnAction(this::processPrev);

        sqrt = new Button("sqrt");
        sqrt.setOnAction(this::processSqrt);

        squared = new Button("x²");
        squared.setOnAction(this::processSquared);

        exponent = new Button("^");
        exponent.setOnAction(this::processExponent);

        nextMenu = new Button("->");
        nextMenu.setOnAction(this::processNextMenu);

        swapMenuBack = new Button("<-");
        swapMenuBack.setOnAction(this::processSwapMenuBack);

        ln = new Button("ln");
        ln.setOnAction(this::processNaturalLog);

        log = new Button("Log");
        log.setOnAction(this::processLog);

        factorial = new Button("!");
        factorial.setOnAction(this::processFactorial);

        modulo = new Button("%");
        modulo.setId("opButton");
        modulo.setOnAction(this::processModulo);

        sin = new Button("sin");
        sin.setId("trigOp");
        sin.setOnAction(this::processSin);

        cos = new Button("cos");
        cos.setId("trigOp");
        cos.setOnAction(this::processCosine);

        tan = new Button("tan");
        tan.setId("trigOp");
        tan.setOnAction(this::processTangent);

        csc = new Button("csc");
        csc.setId("trigOp");
        csc.setOnAction(this::processCosecant);

        data = new Button("data");
        data.setOnAction(this::processData);
        data.setId("statOp");

        comma = new Button(",");
        comma.setOnAction(this::processComma);

        mean = new Button("Mean");
        mean.setOnAction(this::processMean);
        mean.setId("statOp");

        median = new Button("Median");
        median.setOnAction(this::processMedian);
        median.setId("statOp");

        sd = new Button("sd");
        sd.setOnAction(this::processStandardDeviation);
        sd.setId("statOp");

        undo = new Button("Undo");
        undo.setOnAction(this::processUndo);

        info = new Button("Info");
        info.setOnAction(this::processInfo);

        back = new Button("Back");
        back.setOnAction(this::processBack);

        prevResult = new Label("---");
        prevResult.setId("prevLabel");
        prevBar = new HBox(prevResult);
        prevBar.setId("box");
        prevBar.setPrefHeight(75);
        prevBar.setPrefWidth(400);
        prevBar.setAlignment(Pos.CENTER);

        topBar = new HBox(resultLabel);
        topBar.setId("box");
        topBar.setPrefHeight(100);
        topBar.setPrefWidth(400);
        topBar.setAlignment(Pos.CENTER);

        infoString = "-This calculator is only in radians.\n-Input -- is to make a number negative.\n" + 
                            "-The sqrt calculates the square root.\n-The ln calculates the natural log.\n" + 
                            "-The log calculates the logarithm in base 10\n-The ! calculates the factorial of a number.\n" + 
                            "-The Mean finds the average of numbers in a comma\nseparated list.\n" + 
                            "-The Undo button deletes the last entered character in the\nequation.\n" + 
                            "-The only operations that require the click of the equals\nbutton are addition, subtraction, mulitplication, division,\nand modulo.\n" + 
                            "-All other operations require that only one number is in the\nequation, before clicking on the operation's button.";

        infoLabel = new Label(infoString);
        infoLabel.setFont(new Font(18));

        firstRow = new HBox(one, two, three, plus);
        firstRow.setId("buttonbox");
        firstRow.setAlignment(Pos.CENTER);
        secondRow = new HBox(four, five, six, minus);
        secondRow.setId("buttonbox");
        secondRow.setAlignment(Pos.CENTER);
        thirdRow = new HBox(seven, eight, nine, multiply);
        thirdRow.setId("buttonbox");
        thirdRow.setAlignment(Pos.CENTER);
        fourthRow = new HBox(zero, decimal, undo, divide);
        fourthRow.setId("buttonbox");
        fourthRow.setAlignment(Pos.CENTER);
        fifthRow = new HBox(prev, clearAll, clear, operation);
        fifthRow.setId("buttonbox");
        fifthRow.setAlignment(Pos.CENTER);
        sixthRow = new HBox(swapMenuBack, info, comma, nextMenu);
        sixthRow.setId("buttonbox");
        sixthRow.setAlignment(Pos.CENTER);

        page2FirstRow = new HBox(pi, squared, sqrt, modulo);
        page2FirstRow.setId("buttonbox");
        page2FirstRow.setAlignment(Pos.CENTER);
        page2SecondRow = new HBox(ln, log, factorial, exponent);
        page2SecondRow.setId("buttonbox");
        page2SecondRow.setAlignment(Pos.CENTER);
        page2ThirdRow = new HBox(sin, cos, tan, csc);
        page2ThirdRow.setId("buttonbox");
        page2ThirdRow.setAlignment(Pos.CENTER);
        page2FourthRow = new HBox(median, sd, mean, data);
        page2FourthRow.setId("buttonbox");
        page2FourthRow.setAlignment(Pos.CENTER);

        infoMenu = new VBox(infoLabel, back);

        biggerBox = new VBox(prevBar, topBar, firstRow, secondRow, thirdRow, fourthRow, fifthRow, sixthRow);
        biggerBox.setId("biggerbox");
        biggerBox.setAlignment(Pos.CENTER);
        biggerBox.setPrefWidth(650); // old param: 500
        biggerBox.setPrefHeight(500); // old param: 470

        operation.setDisable(true);
        swapMenuBack.setDisable(true);

        Group root = new Group(biggerBox);
        Scene scene = new Scene(root, 650, 500, Color.GRAY); // old parameters: root, 500, 470, Color.GRAY
        scene.getStylesheets().add("stylesheet.css");

        calcStage.setTitle("Alex's Calculator");
        calcStage.setScene(scene);
        calcStage.setResizable(false);
        calcStage.show();
    }

    // *******************Button Processes*******************
    private void processOne(ActionEvent e) {
        tempString = "1";
        resultText += "1";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processTwo(ActionEvent e) {
        tempString = "2";
        resultText += "2";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processThree(ActionEvent e) {
        tempString = "3";
        resultText += "3";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processFour(ActionEvent e) {
        tempString = "4";
        resultText += "4";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processFive(ActionEvent e) {
        tempString = "5";
        resultText += "5";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processSix(ActionEvent e) {
        tempString = "6";
        resultText += "6";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processSeven(ActionEvent e) {
        tempString = "7";
        resultText += "7";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processEight(ActionEvent e) {
        tempString = "8";
        resultText += "8";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processNine(ActionEvent e) {
        tempString = "9";
        resultText += "9";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processZero(ActionEvent e) {
        tempString = "0";
        resultText += "0";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processPi(ActionEvent e) {
        tempString = "" + Math.PI;
        resultText += "π";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processExponent(ActionEvent event) {
        tempString = "^";
        resultText += "^";
        resultLabel.setText(resultText);
        stringList.add(tempString);
        operation.setDisable(false);
    }
    private void processPlus(ActionEvent event) {
        tempString = "+";
        resultText += "+";
        resultLabel.setText(resultText);
        stringList.add(tempString);
        operation.setDisable(false);
    }
    private void processMinus(ActionEvent event) {
        tempString = "-";
        resultText += "-";
        resultLabel.setText(resultText);
        stringList.add(tempString);
        operation.setDisable(false);
    }
    private void processMultiply(ActionEvent event) {
        tempString = "*";
        resultText += "*";
        resultLabel.setText(resultText);
        stringList.add(tempString);
        operation.setDisable(false);
    }
    private void processDivide(ActionEvent event) {
        tempString = "/";
        resultText += "/";
        resultLabel.setText(resultText);
        stringList.add(tempString);
        operation.setDisable(false);
    }
    private void processModulo(ActionEvent event) {
        tempString = "%";
        resultText += "%";
        resultLabel.setText(resultText);
        stringList.add(tempString);
        operation.setDisable(false);
    }
    private void processDecimal(ActionEvent event) {
        tempString = ".";
        resultText += ".";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processComma(ActionEvent event) {
        tempString = ",";
        resultText += ",";
        resultLabel.setText(resultText);
        stringList.add(tempString);
    }
    private void processClear(ActionEvent e) {
        clear();
    }
    private void processClearAll(ActionEvent e) {
        clearAll();
    }
    private void processPrev(ActionEvent e) {
        if (isAllCleared && tempResult == 0) {
            clearAll();
            resultLabel.setText("No previous value found.");
            resultLabel.setFont(new Font(20));
        } else {
        tempString = "" + tempResult;
        resultText += "" + forDecimal.format(tempResult);
        resultLabel.setText(resultText);
        stringList.add(tempString);
        }
    }

    private void processUndo(ActionEvent event) {
        try {
            processUndo();
        } catch (StringIndexOutOfBoundsException e) {
            clearAll();
            resultLabel.setText("No input found. Hit the\nAC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }
    private void processUndo() {
        StringBuilder str = new StringBuilder(resultText);
        str.deleteCharAt(resultText.length() - 1);
        resultText = str.toString();
        resultLabel.setText(resultText);
        stringList.remove(stringList.size() - 1);
    }

    private void processMean(ActionEvent event) {
        try {
            processMean();
        } catch (NullPointerException e) {
            clearAll();
            resultLabel.setText("No data found. Hit the\nAC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }

    private void processData(ActionEvent event) {
        try {
            processData();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type numbers in a comma separated list and then\nhit the data button. Hit the AC button to start over.");
            resultLabel.setFont(new Font(18));
        } catch (NullPointerException e) {
            clearAll();
            resultLabel.setText("No numbers found. Hit the\nAC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }

    private void processData() {
        prevString = "data = [" + resultText + "]";
        dataStringArray = resultText.split(",");
        if (dataStringArray.length == 0) {
            throw new NullPointerException();
        }
        dataArray = new double[dataStringArray.length];
        for (int i = 0; i < dataStringArray.length; i++) {
            if (dataStringArray[i].equals("π")) {
                dataArray[i] = Math.PI;
            } else {
                dataArray[i] = Double.parseDouble(dataStringArray[i]);
            }
        }

        resultLabel.setText("Data: [" + resultText + "]");
        clearStringList();
    }

    private void processMean() {
        prevString = "mean(data)";
        double avg = 0;
        for (int i = 0; i < dataArray.length; i++) {
            avg += dataArray[i];
        }
        avg = avg / dataArray.length;
        resultLabel.setText("Mean(data): " + forDecimal.format(avg));
        tempResult = avg;
        clearStringList();
    }

    private void processMedian(ActionEvent event) {
        try {
            processMedian();
        } catch (NullPointerException e) {
            clearAll();
            resultLabel.setText("No data found. Hit the\nAC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }

    private void processMedian() {
        prevString = "median(data)";
        Arrays.sort(dataArray);
        int mid = dataArray.length / 2;
        double med = dataArray[mid];
        if (dataArray.length % 2 == 0) {
            med = dataArray[mid - 1] + dataArray[mid];
            med = med / 2;
        }
        resultLabel.setText("Median(data): " + med);
        tempResult = med;
        clearStringList();
    }

    private void processStandardDeviation(ActionEvent event) {
        try {
            processStandardDeviation();
        } catch (NullPointerException e) {
            clearAll();
            resultLabel.setText("No data found. Hit the\nAC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }

    private void processStandardDeviation() {
        prevString = "Standard Deviation(data)";
        double mu = 0;
        for (int i = 0; i < dataArray.length; i++) {
            mu += dataArray[i];
        }
        mu = mu / dataArray.length;
        double variance = 0;
        for (int i = 0; i < dataArray.length; i++) {
            variance += Math.pow(dataArray[i] - mu, 2);
        }
        variance = variance / dataArray.length;
        double sd = Math.sqrt(variance);
        resultLabel.setText("sd(data): " + forDecimal.format(sd));
        tempResult = sd;
        clearStringList();
    }

    private void processSqrt(ActionEvent event) {
        try {
            processSqrt();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the sqrt button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }
    private void processSquared(ActionEvent event) {
        try {
            processSquared();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the x² button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }

    private void processNaturalLog(ActionEvent event) {
        try {
            processLN();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the ln button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }
    private void processLN() {
        prevString = "ln(" + resultText + ")";
        double num;
        if (resultText.equals("π")) {
            num = Math.log(Math.PI);
        } else {
            num = Math.log(Double.parseDouble(resultText));
        } 
        resultLabel.setText("" + forDecimal.format(num));
        tempResult = num;
        clearStringList();
    }
 
    private void processSqrt() {
        prevString = "sqrt(" + resultText + ")";
        double sqrtNum;
        if (resultText.equals("π")) {
            sqrtNum = Math.sqrt(Math.PI);
            resultLabel.setText("" + forDecimal.format(sqrtNum));
            tempResult = sqrtNum;
        clearStringList();
        } else if (resultText.contains("-")) {
            clearAll();
            resultLabel.setText("Cannot calculate the square root\nof a negative number.");
            resultLabel.setFont(new Font(20));
        } else {
            sqrtNum = Math.sqrt(Double.parseDouble(resultText));
            resultLabel.setText("" + forDecimal.format(sqrtNum));
            tempResult = sqrtNum;
            clearStringList();
        }
    }

    private void processSquared() {
        prevString = "(" + resultText + ")²";
        double squaredNum;
        if (resultText.equals("π")) {
            squaredNum = Math.pow(Math.PI, 2);
        } else {
            squaredNum = Math.pow(Double.parseDouble(resultText), 2);
        }
        resultLabel.setText("" + forDecimal.format(squaredNum));
        tempResult = squaredNum;
        clearStringList();
    }

    private void processLog(ActionEvent event) {
        try {
            processLog();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the log button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }
    private void processLog() {
        prevString = "log(" + resultText + ")";
        double num;
        if (resultText.equals("π")) {
            num = Math.log10(Math.PI);
        } else {
            num = Math.log10(Double.parseDouble(resultText));
        }
        resultLabel.setText("" + forDecimal.format(num));
        tempResult = num;
        clearStringList();
    }

    private void processSin(ActionEvent event) {
        try {
            processSin();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the sin button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        } 
    }
    private void processSin() {
        prevString = "sin(" + resultText + ")";
        double num;
        if (resultText.equals("π")) {
            num = Math.sin(Math.PI);
        } else {
            num = Math.sin(Double.parseDouble(resultText));
        }
        resultLabel.setText("" + forDecimal.format(num));
        tempResult = num;
        clearStringList();
    }

    private void processCosine(ActionEvent event) {
        try {
            processCosine();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the cos button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        } 
    }
    private void processCosine() {
        prevString = "cos(" + resultText + ")";
        double num;
        if (resultText.equals("π")) {
            num = Math.cos(Math.PI);
        } else {
            num = Math.cos(Double.parseDouble(resultText));
        }
        resultLabel.setText("" + forDecimal.format(num));
        tempResult = num;
        clearStringList();
    }

    private void processTangent(ActionEvent event) {
        try {
            processTangent();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the tan button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        } 
    }
    private void processTangent() {
        prevString = "tan(" + resultText + ")";
        double num;
        if (resultText.equals("π")) {
            num = Math.tan(Math.PI);
        } else {
            num = Math.tan(Double.parseDouble(resultText));
        }
        resultLabel.setText("" + forDecimal.format(num));
        tempResult = num;
        clearStringList();
    }

    private void processCosecant(ActionEvent event) {
        try {
            processCosecant();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the csc button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        } 
    }
    private void processCosecant() {
        prevString = "csc(" + resultText + ")";
        double num;
        if (resultText.equals("π")) {
            clearAll();
            resultLabel.setText("The cosecant of π is undefined.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        } else {
            num = (1 / Math.sin(Double.parseDouble(resultText)));
            resultLabel.setText("" + forDecimal.format(num));
            tempResult = num;
            clearStringList();
        }
    }

    private void processFactorial(ActionEvent event) {
        try {
            processFactorial();
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please type a number and then hit the ! button.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        }
    }
    private void processFactorial() {
        prevString = "(" + resultText + ")!";
        if (resultText.equals("π")) {
            clearAll();
            resultLabel.setText("Cannot perform this action. The factorial of π \nis irrational. Hit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        } else if (resultText.contains(".") || resultText.contains("-")) {
            clearAll();
            resultLabel.setText("Cannot perform this action. The factorial can only \nbe found for a positive rational integer.");
            resultLabel.setFont(new Font(20));
        } else {
            double num = (Double.parseDouble(resultText));
            num = factorial(num);
            resultLabel.setText("" + forDecimal.format(num));
            tempResult = num;
            clearStringList();
        }
    }
    private double factorial(double num) {
        if (num == 1) {return 1;}
        return num * factorial(num - 1);
    }

    // *******************Menu Swapping Methods*******************
    private void processNextMenu(ActionEvent e) {
        nextMenu.setDisable(true);
        swapMenuBack.setDisable(false);
        info.setDisable(true);

        biggerBox.getChildren().remove(2);
        biggerBox.getChildren().add(2, page2FirstRow);
        biggerBox.getChildren().remove(3);
        biggerBox.getChildren().add(3, page2SecondRow);
        biggerBox.getChildren().remove(4);
        biggerBox.getChildren().add(4, page2ThirdRow);
        biggerBox.getChildren().remove(5);
        biggerBox.getChildren().add(5, page2FourthRow);
    }

    private void processSwapMenuBack(ActionEvent e) {
        swapMenuBack.setDisable(true);
        nextMenu.setDisable(false);
        info.setDisable(false);

        biggerBox.getChildren().remove(2);
        biggerBox.getChildren().add(2, firstRow);
        biggerBox.getChildren().remove(3);
        biggerBox.getChildren().add(3, secondRow);
        biggerBox.getChildren().remove(4);
        biggerBox.getChildren().add(4, thirdRow);   
        biggerBox.getChildren().remove(5);
        biggerBox.getChildren().add(5, fourthRow);
    }

    private void processInfo(ActionEvent e) {
        nextMenu.setDisable(true);
        swapMenuBack.setDisable(true);
        info.setDisable(true);

        biggerBox.getChildren().removeAll(prevBar, topBar, firstRow, secondRow, thirdRow, fourthRow, fifthRow, sixthRow);
        biggerBox.getChildren().add(infoMenu);
    }

    private void processBack(ActionEvent e) {
        info.setDisable(false);
        nextMenu.setDisable(false);
        swapMenuBack.setDisable(true);

        biggerBox.getChildren().remove(infoMenu);
        biggerBox.getChildren().addAll(prevBar, topBar, firstRow, secondRow, thirdRow, fourthRow, fifthRow, sixthRow);
    }

    // *******************Clearing Methods*******************
    private void clear() {
        resultText = "";
        resultLabel.setText(resultText);
        resultLabel.setFont(new Font(48));

        prevResult.setText("Previous Operation: " + prevString + "\nPrevious answer: " + forDecimal.format(tempResult));

        clearStringList();
    }
    private void clearAll() {
        clear();
        prevString = "---";
        prevResult.setText("---");
        tempResult = 0;
        dataArray = null;
        isAllCleared = true;
    }
    private void clearStringList() {
        Iterator<String> removeIter = stringList.iterator();
        while (removeIter.hasNext()) {
            removeIter.next();
            removeIter.remove();
        }
    }

    // *******************Operation Calculation Methods*******************
    private void processEquals(ActionEvent event) {
        try {
            Equation eq = new Equation(resultText);
            prevString = resultText;
            operation.setDisable(true);
            double result = eq.evaluate();
            tempResult = result;
            resultLabel.setText(forDecimal.format(result));
        clearStringList();
        }  catch (NoOperationException e) {
            clearAll();
            resultLabel.setText("No operator found. Please hit the\nAC button to start over.");
            resultLabel.setFont(new Font(20));
        } catch (NumberFormatException e) {
            clearAll();
            resultLabel.setText("Please only perform one operation at a time.\nHit the AC button to start over.");
            resultLabel.setFont(new Font(20));
        } catch (ZeroException e) {
            clearAll();
            resultLabel.setText(e.getMessage());
            resultLabel.setFont(new Font(20));
        }
    }
}
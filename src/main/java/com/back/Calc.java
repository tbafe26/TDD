package com.back;

public class Calc {
    public static int run(String expression) {
        String[] tokens = expression.split("\\s+");
        int firstNum = Integer.parseInt(tokens[0]);

        return calc(tokens, 1, firstNum);
    }

    private static int calc(String[] tokens, int index, int result) {
        if (index >= tokens.length) return result;

        String operator = tokens[index];
        int num = Integer.parseInt(tokens[index + 1]);

        if (operator.equals("+")) result += num;
        else if (operator.equals("-")) result -= num;
        else if (operator.equals("*")) result *= num;

        return calc(tokens, index + 2, result);
    }
}

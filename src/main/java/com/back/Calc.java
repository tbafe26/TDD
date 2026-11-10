package com.back;

public class Calc {
    public static int run(String expression) {
        // 괄호를 독립 토큰으로 분리
        String[] tokens = expression
                .replace("(", " ( ")
                .replace(")", " ) ")
                .trim()
                .split("\\s+");

        return expr(tokens, 0)[0];
    }

    // 덧셈/뺄셈 식: {값, 다음 인덱스} 반환
    private static int[] expr(String[] tokens, int index) {
        int[] first = term(tokens, index);
        return exprRest(tokens, first[1], first[0]);
    }

    private static int[] exprRest(String[] tokens, int index, int result) {
        if (index >= tokens.length || tokens[index].equals(")")) return new int[]{result, index};

        String operator = tokens[index];
        int[] next = term(tokens, index + 1);

        if (operator.equals("+")) result += next[0];
        else if (operator.equals("-")) result -= next[0];

        return exprRest(tokens, next[1], result);
    }

    // 곱셈 묶음: {값, 다음 인덱스} 반환
    private static int[] term(String[] tokens, int index) {
        int[] first = factor(tokens, index);
        return termRest(tokens, first[1], first[0]);
    }

    private static int[] termRest(String[] tokens, int index, int value) {
        if (index >= tokens.length || !tokens[index].equals("*")) return new int[]{value, index};

        int[] next = factor(tokens, index + 1);
        return termRest(tokens, next[1], value * next[0]);
    }

    // 숫자, 괄호 식, 단항 음수: {값, 다음 인덱스} 반환
    private static int[] factor(String[] tokens, int index) {
        if (tokens[index].equals("-")) {
            // 단항 음수: 뒤따르는 항을 재귀로 계산한 뒤 부호 반전
            int[] operand = factor(tokens, index + 1);
            return new int[]{-operand[0], operand[1]};
        }

        if (tokens[index].equals("(")) {
            // 괄호 안을 재귀로 계산하고 닫는 괄호 다음으로 이동
            int[] inner = expr(tokens, index + 1);
            return new int[]{inner[0], inner[1] + 1};
        }

        return new int[]{Integer.parseInt(tokens[index]), index + 1};
    }
}

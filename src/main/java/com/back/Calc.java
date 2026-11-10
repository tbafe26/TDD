package com.back;

public class Calc {
    public static int run(String expression) {
        String[] tokens = expression.split("\\s+");

        // 첫 항(곱셈 묶음)을 계산한 뒤 나머지 덧셈/뺄셈을 이어서 처리
        int[] first = term(tokens, 0);
        return expr(tokens, first[1], first[0]);
    }

    // 덧셈/뺄셈: 곱셈 묶음(term) 단위로 누적
    private static int expr(String[] tokens, int index, int result) {
        if (index >= tokens.length) return result;

        String operator = tokens[index];
        int[] next = term(tokens, index + 1);

        if (operator.equals("+")) result += next[0];
        else if (operator.equals("-")) result -= next[0];

        return expr(tokens, next[1], result);
    }

    // 곱셈 묶음: {값, 다음 인덱스} 반환
    private static int[] term(String[] tokens, int index) {
        int value = Integer.parseInt(tokens[index]);
        return termRest(tokens, index + 1, value);
    }

    private static int[] termRest(String[] tokens, int index, int value) {
        if (index >= tokens.length || !tokens[index].equals("*")) return new int[]{value, index};

        int num = Integer.parseInt(tokens[index + 1]);
        return termRest(tokens, index + 2, value * num);
    }
}

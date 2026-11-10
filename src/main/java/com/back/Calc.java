package com.back;

public class Calc {
    // 부분 계산 결과: 계산된 값과 다음에 읽을 토큰 위치
    private record Result(int value, int next) {
    }

    public static int run(String expression) {
        // 괄호를 독립 토큰으로 분리
        String[] tokens = expression
                .replace("(", " ( ")
                .replace(")", " ) ")
                .trim()
                .split("\\s+");

        return expr(tokens, 0).value();
    }

    // 덧셈/뺄셈 식: 곱셈 묶음(term)을 차례로 더하고 뺀다
    private static Result expr(String[] tokens, int index) {
        Result first = term(tokens, index);
        return exprRest(tokens, first.next(), first.value());
    }

    private static Result exprRest(String[] tokens, int index, int acc) {
        if (index >= tokens.length || tokens[index].equals(")")) return new Result(acc, index);

        String operator = tokens[index];
        Result next = term(tokens, index + 1);
        int value = operator.equals("+") ? acc + next.value() : acc - next.value();

        return exprRest(tokens, next.next(), value);
    }

    // 곱셈 묶음: 덧셈/뺄셈보다 먼저 계산
    private static Result term(String[] tokens, int index) {
        Result first = factor(tokens, index);
        return termRest(tokens, first.next(), first.value());
    }

    private static Result termRest(String[] tokens, int index, int acc) {
        if (index >= tokens.length || !tokens[index].equals("*")) return new Result(acc, index);

        Result next = factor(tokens, index + 1);
        return termRest(tokens, next.next(), acc * next.value());
    }

    // 숫자, 괄호 식, 단항 음수
    private static Result factor(String[] tokens, int index) {
        String token = tokens[index];

        if (token.equals("-")) {
            // 단항 음수: 뒤따르는 항을 재귀로 계산한 뒤 부호 반전
            Result operand = factor(tokens, index + 1);
            return new Result(-operand.value(), operand.next());
        }

        if (token.equals("(")) {
            // 괄호 안을 재귀로 계산하고 닫는 괄호 다음으로 이동
            Result inner = expr(tokens, index + 1);
            return new Result(inner.value(), inner.next() + 1);
        }

        return new Result(Integer.parseInt(token), index + 1);
    }
}

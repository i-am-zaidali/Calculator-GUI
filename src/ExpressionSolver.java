
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author zimp
 */

class ExpressionSolver {

    private final List<Float> numbers;

    public ExpressionSolver(String expression) {
        Stack<Character> operators = new Stack<>();
        this.numbers = new ArrayList<>();

        StringBuilder numberBuilder = new StringBuilder();
        for (char c : expression.replace(" ", "").toCharArray()) {
            if (Character.isDigit(c) || c == '.' || (c == '-' && numberBuilder.isEmpty())) {
                numberBuilder.append(c);
            } else if (c == '+' || c == '-' || c == 'x' || c == '÷') {
                if (!numberBuilder.isEmpty()) {
                    numbers.add(Float.valueOf(numberBuilder.toString()));
                    numberBuilder.setLength(0);
                }
                while (!operators.isEmpty() && hasHigherPrecedence(operators.peek(), c)) {
                    char op = operators.pop();
                    float operand2 = numbers.removeLast();
                    float operand1 = numbers.removeLast();
                    float result = calculate(operand1, operand2, op);
                    numbers.add(result);
                }
                operators.push(c);
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }

        // Add the last number if applicable
        if (!numberBuilder.isEmpty()) {
            numbers.add(Float.valueOf(numberBuilder.toString()));
        }

        // Process remaining operators
        while (!operators.isEmpty()) {
            char op = operators.pop();
            float operand2 = numbers.removeLast();
            float operand1 = numbers.removeLast();
            float result = calculate(operand1, operand2, op);
            numbers.add(result);
        }
    }

    private boolean hasHigherPrecedence(char op1, char op2) {
        return op1 == 'x' || op1 == '÷';
    }

    private float calculate(float operand1, float operand2, char op) {
        return switch (op) {
            case '+' -> operand1 + operand2;
            case '-' -> operand1 - operand2;
            case 'x' -> operand1 * operand2;
            case '÷' -> {
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield operand1 / operand2;
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + op);
        };
    }
    public float solve() {
        if (numbers.size() != 1) {
            throw new IllegalStateException("Invalid expression format. Expected single result.");
        }
        return numbers.getFirst();
    }
}
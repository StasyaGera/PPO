package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.text.ParseException;
import java.util.List;
import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private Stack<Integer> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getValue());
    }

    @Override
    public void visit(Brace token) {
    }

    @Override
    public void visit(Operation token) {
        int n2 = stack.pop();
        int n1 = stack.pop();
        switch (token.getType()) {
            case ADD:
                stack.push(n1 + n2);
                break;
            case SUB:
                stack.push(n1 - n2);
                break;
            case MUL:
                stack.push(n1 * n2);
                break;
            case DIV:
                stack.push(n1 / n2);
                break;
        }
    }

    public int calc(List<Token> tokens) throws ParseException {
        for (Token t: tokens) {
            t.accept(this);
        }
        return stack.pop();
    }
}

package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private Stack<Token> stack = new Stack<>();
    private List<Token> result = new ArrayList<>();

    @Override
    public void visit(NumberToken token) {
        result.add(token);
    }

    @Override
    public void visit(Brace token) throws ParseException {
        if (token.getType().equals(Brace.Type.LEFT)) {
            stack.push(token);
        } else {
            while (!stack.isEmpty() && stack.peek().getType() != Token.Type.LEFT) {
                result.add(stack.pop());
            }
            if (stack.isEmpty()) {
                throw new ParseException("Expected left parenthesis", 0);
            }
            stack.pop();
        }
    }

    @Override
    public void visit(Operation token) {
        while (!stack.isEmpty()
                && (token.getType() == Token.Type.ADD || token.getType() == Token.Type.SUB)
                && (stack.peek().getType() == Token.Type.ADD || stack.peek().getType() == Token.Type.SUB
                || stack.peek().getType() == Token.Type.MUL || stack.peek().getType() == Token.Type.DIV)) {
            result.add(stack.pop());
        }
        stack.add(token);
    }

    public List<Token> parse(List<Token> tokens) throws ParseException {
        for (Token t: tokens) {
            t.accept(this);
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        for (Token t : result) {
            if (t.getType() == Token.Type.LEFT) {
                throw new ParseException("Unclosed left parenthesis", 0);
            }
        }
        return result;
    }
}

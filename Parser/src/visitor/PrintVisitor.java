package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.text.ParseException;
import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private StringBuilder result = new StringBuilder();

    @Override
    public void visit(NumberToken token) {
        result.append(token.toString()).append(' ');
    }

    @Override
    public void visit(Brace token) {
        result.append(token.toString()).append(' ');
    }

    @Override
    public void visit(Operation token) {
        result.append(token.toString()).append(' ');
    }

    public String print(List<Token> tokens) throws ParseException {
        for (Token t: tokens) {
            t.accept(this);
        }
        return result.toString();
    }
}

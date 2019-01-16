package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;

import java.text.ParseException;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token) throws ParseException;
    void visit(Operation token);
}

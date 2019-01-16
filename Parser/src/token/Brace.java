package token;

import visitor.TokenVisitor;

import java.text.ParseException;

public class Brace implements Token {
    private Type type;

    Brace(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void accept(TokenVisitor visitor) throws ParseException {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return type.toString();
    }
}

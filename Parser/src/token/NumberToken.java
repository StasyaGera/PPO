package token;

import visitor.TokenVisitor;

public class NumberToken implements Token {
    private int value;

    public NumberToken(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getType().toString() + "(" + Integer.toString(value) + ")";
    }
}

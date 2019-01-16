package token;

import visitor.TokenVisitor;

public class Operation implements Token {
    private Type type;

    public Operation(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return type.toString();
    }
}

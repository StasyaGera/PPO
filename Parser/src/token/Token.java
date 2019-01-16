package token;

import visitor.TokenVisitor;

import java.text.ParseException;

public interface Token {
    void accept(TokenVisitor visitor) throws ParseException;
    Type getType();

    enum Type {
        ADD,
        SUB,
        MUL,
        DIV,
        LEFT,
        RIGHT,
        NUMBER
    }
}

import token.Token;
import token.Tokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Token> tokens;
        try {
            tokens = new Tokenizer().tokenize(System.in);
            List<Token> reversedPolishNotation = new ParserVisitor().parse(tokens);
            System.out.println(new PrintVisitor().print(reversedPolishNotation));
            System.out.println(new CalcVisitor().calc(reversedPolishNotation));
        } catch (ParseException e) {
            if (e.getErrorOffset() == 0) {
                System.err.println(e.getMessage());
            } else {
                System.err.println(e.getMessage() + " at position " + e.getErrorOffset());
            }
        }
    }
}

package token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;

public class Tokenizer {
    public List<Token> tokenize(String input) throws ParseException {
        List<Token> result = new ArrayList<>();
        for (int pos = 0; pos < input.length(); pos++) {
            char curr = input.charAt(pos);

            if (isWhitespace(curr)) continue;

            if (isDigit(curr)) {
                int num = 0;
                while (pos < input.length() && isDigit(input.charAt(pos))) {
                    num *= 10;
                    num += Character.getNumericValue(input.charAt(pos));
                    pos++;
                }
                result.add(new NumberToken(num));
                pos--;
                continue;
            }

            switch (curr) {
                case '(':
                    result.add(new Brace(Token.Type.LEFT));
                    break;
                case ')':
                    result.add(new Brace(Token.Type.RIGHT));
                    break;
                case '+':
                    result.add(new Operation(Token.Type.ADD));
                    break;
                case '-':
                    result.add(new Operation(Token.Type.SUB));
                    break;
                case '*':
                    result.add(new Operation(Token.Type.MUL));
                    break;
                case '/':
                    result.add(new Operation(Token.Type.DIV));
                    break;
                default:
                    throw new ParseException("Unknown character", pos + 1);
            }
        }
        return result;
    }

    public List<Token> tokenize(InputStream inputStream) throws ParseException {
        List<Token> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.addAll(tokenize(line));
            }
        } catch (IOException e) {
            System.err.println("Error while closing inputStream");
            System.exit(1);
        }
        return result;
    }
}

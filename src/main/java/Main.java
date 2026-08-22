import tokens.Token;
import tokens.Tokenizer;

import java.util.ArrayList;

public class Main {
    public void main() {
        String expression = "((2+5*12)+5)";

        ArrayList<Token> tokenList = Tokenizer.convertString(expression);

        for(Token token : tokenList){
            System.out.println(token.getData());
        }
    }

}

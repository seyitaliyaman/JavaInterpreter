package com.edu.aydin;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//Every non-whitespace character is a token in this language
//all identifiers are single letter tokens and they can only be variables
public class Tester {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //open the file with the program text and read it
        //into a String variable for further processing
        Parser parser = new Parser(new Scanner("program1.txt"));
        parser.parse();

    }

}


//Another name for a scanner is tokenizer
//According to our grammar every single non-white space
//is a token!!!!
//Scanner will read the program line by line and scan
//every line from left to right returning tokens
class Scanner {
    private String progText;
    private int curPos = 0;
    private TokenFactory tokenFactory;
    //private String fileName;

    Scanner(String fileName){
        //Scanners know about the programming language (they do not know anything about
        //the grammar). But they know about token types
        try {
            //1st way
            byte [] allBytes = Files.readAllBytes(Paths.get(fileName));
            progText = new String(allBytes);
            this.tokenFactory = new TokenFactory();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //parser will ask the scanner for the next token. Parser knows about
    //the grammar
    Token nextToken() {
        if(curPos == progText.length())
            return tokenFactory.getEOF();
        char curChar;
        //we skip white space characters
        while(curPos < progText.length() && Character.isWhitespace(progText.charAt(curPos)))
            curPos++;

        if(curPos == progText.length())
            return tokenFactory.getEOF();

        //we are at the first non-whitespace character
        curChar = progText.charAt(curPos);
        curPos++;
        return tokenFactory.getToken(curChar);
        //change this by including
        //the proper remaining token types
        //return new ErrorToken("NotRecognizedToken");
    }
}
class TokenFactory{

    public Token getToken(char tokenChar){
        if(Character.isDigit(tokenChar)){
            return new NumberToken(String.valueOf(tokenChar));
        }else if(Character.isAlphabetic(tokenChar)){
            return new IdentifierToken(String.valueOf(tokenChar));
        } else{
            switch (tokenChar){
                case '{':
                    return new WhileBegToken(String.valueOf(tokenChar));
                case '}':
                    return new WhileEndToken(String.valueOf(tokenChar));
                case '[':
                    return new IfBeginToken(String.valueOf(tokenChar));
                case ']':
                    return new IfEndToken(String.valueOf(tokenChar));
                case '=':
                    return new EqualToken(String.valueOf(tokenChar));
                case '-':
                    return new HyphenToken(String.valueOf(tokenChar));
                case '+':
                    return new PlusToken(String.valueOf(tokenChar));
                case '*':
                    return new StarToken(String.valueOf(tokenChar));
                case '/':
                    return new SlashToken(String.valueOf(tokenChar));
                case '>':
                    return new GreaterThanToken(String.valueOf(tokenChar));
                case '<':
                    return new LessThanToken(String.valueOf(tokenChar));
                case ';':
                    return new SemicolonToken(String.valueOf(tokenChar));
                case '?':
                    return new QuestionMarkToken(String.valueOf(tokenChar));
                case '%':
                    return new RemainderToken(String.valueOf(tokenChar));
                case '^':
                    return new ExponentToken(String.valueOf(tokenChar));
                default:
                    return new ErrorToken(String.valueOf(tokenChar));
            }
        }
    }

    public Token getEOF(){
        return new EOFToken();
    }
}



class Parser{
    private Scanner scanner;
    Parser(Scanner scanner){
        this.scanner = scanner;
    }
    void parse() {
        Token token = scanner.nextToken();
        while(!token.getType().equals(TokenType.END_OF_FILE)) {
            //print all token texts and their types here for
            //for example, program1.txt
            System.out.println("Token : "+token.text+" Token Type : "+token.getType());
            token = scanner.nextToken();
        }
    }
}

//read the whole program text into a String variable and use it for
//extracting tokens



class Token{
    protected String text;
    protected TokenType tokenType;
    Token(String text){
        this.text = text;
    }
    TokenType getType() {
        return tokenType;
    }

}
class EOFToken extends Token{
    EOFToken(){
        super("EOF");
        this.tokenType = TokenType.END_OF_FILE;
    }
}
class NumberToken extends Token{
    NumberToken(String text){
        super(text);
        this.tokenType = TokenType.NUMBER;
    }
}
class IdentifierToken extends Token{
    IdentifierToken(String text){
        super(text);
        this.tokenType = TokenType.IDENTIFIER;
    }
}
class WhileBegToken extends Token{
    WhileBegToken(String text){
        super(text);
        this.tokenType = TokenType.WHILE_BEG;
    }
}

class WhileEndToken extends Token{
    WhileEndToken(String text){
        super(text);
        this.tokenType = TokenType.WHILE_END;
    }
}

class IfBeginToken extends Token{
    IfBeginToken(String text){
        super(text);
        this.tokenType = TokenType.IF_BEG;
    }
}

class IfEndToken extends Token{
    IfEndToken(String text){
        super(text);
        this.tokenType = TokenType.IF_END;
    }
}

class ErrorToken extends Token{
    ErrorToken(String text){
        super(text);
        this.tokenType = TokenType.NRT_ERROR;
    }
}

class EqualToken extends Token{
    EqualToken(String text){
        super(text);
        this.tokenType = TokenType.EQUAL;
    }
}

class SemicolonToken extends Token{
    SemicolonToken(String text){
        super(text);
        this.tokenType = TokenType.SEMICOLON;
    }
}

class HyphenToken extends Token{
    HyphenToken(String text){
        super(text);
        this.tokenType = TokenType.HYPHEN;
    }
}

class PlusToken extends Token{
    PlusToken(String text){
        super(text);
        this.tokenType = TokenType.PLUS;
    }
}

class StarToken extends Token{
    StarToken(String text){
        super(text);
        this.tokenType = TokenType.STAR;
    }
}

class SlashToken extends Token{
    SlashToken(String text){
        super(text);
        this.tokenType = TokenType.SLASH;
    }
}

class LessThanToken extends Token{
    LessThanToken(String text){
        super(text);
        this.tokenType = TokenType.LESS_THAN;
    }
}

class GreaterThanToken extends Token{
    GreaterThanToken(String text){
        super(text);
        this.tokenType = TokenType.GREATER_THAN;
    }
}

class QuestionMarkToken extends Token{
    QuestionMarkToken(String text){
        super(text);
        this.tokenType = TokenType.QUESTION_MARK;
    }
}

class RemainderToken extends Token{
    RemainderToken(String text){
        super(text);
        this.tokenType = TokenType.REMAINDER;
    }
}

class ExponentToken extends Token{
    ExponentToken(String text){
        super(text);
        this.tokenType = TokenType.EXPONENT;
    }
}






enum TokenType{
    IF_BEG("["), IF_END("]"), WHILE_BEG("{"), WHILE_END("}"), EQUAL("="), SEMICOLON(";"), EXPONENT("^"),
    HYPHEN("-"),PLUS("+"),STAR("*"),SLASH("/"),QUESTION_MARK("?"),GREATER_THAN(">"),LESS_THAN("<"), REMAINDER("%"),
    NUMBER,IDENTIFIER,NRT_ERROR, END_OF_FILE;
    String text;
    TokenType(){
        this.text = this.toString();
    }
    TokenType(String text){
        this.text = text;
    }

}
package com.edu.aydin;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

//Every non-whitespace character is a token in this language
//all identifiers are single letter tokens and they can only be variables
public class Tester {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //open the file with the program text and read it
        //into a String variable for further processing
        Tree tree = new Tree();
        Parser parser = new Parser(new Scanner("program1.txt"), tree);
        try {
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                case ':':
                    return new ColonToken(String.valueOf(tokenChar));
                case '(':
                    return new ParenthesesBeg(String.valueOf(tokenChar));
                case ')':
                    return new ParenthesesEnd(String.valueOf(tokenChar));
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
    private final Scanner scanner;
    private final Tree tree;
    Parser(Scanner scanner, Tree tree){
        this.scanner = scanner;
        this.tree = tree;
    }
    void parse() throws Exception {
        tree.root.check(scanner);
        /*Token token = scanner.nextToken();
        while(!token.getType().equals(TokenType.END_OF_FILE)) {
            //print all token texts and their types here for
            //for example, program1.txt

            System.out.println("Token : "+token.text+" Token Type : "+token.getType());

            token = scanner.nextToken();
        }*/
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
    boolean isTerminal(){
        return (this.tokenType.equals(TokenType.IF_END) || this.tokenType.equals(TokenType.WHILE_END) ||
                this.tokenType.equals(TokenType.SEMICOLON) || this.tokenType.equals(TokenType.END_OF_FILE));
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

class ColonToken extends Token{
    ColonToken(String text){
        super(text);
        this.tokenType = TokenType.COLON;
    }
}

class ParenthesesBeg extends Token{
    ParenthesesBeg(String text){
        super(text);
        this.tokenType = TokenType.PARENTHESES_BEG;
    }
}

class ParenthesesEnd extends Token{
    ParenthesesEnd(String text){
        super(text);
        this.tokenType = TokenType.PARENTHESES_END;
    }
}

enum TokenType{
    IF_BEG("["), IF_END("]"), WHILE_BEG("{"), WHILE_END("}"), EQUAL("="),
    SEMICOLON(";"), EXPONENT("^"), HYPHEN("-"), PLUS("+"), STAR("*"), SLASH("/"),
    QUESTION_MARK("?"), GREATER_THAN(">"), LESS_THAN("<"), REMAINDER("%"), COLON(":"),
    PARENTHESES_BEG("("), PARENTHESES_END(")"), NUMBER, IDENTIFIER, NRT_ERROR, END_OF_FILE;
    String text;
    TokenType(){
        this.text = this.toString();
    }
    TokenType(String text){
        this.text = text;
    }

}

class Tree {

    TreeNode root;

    Tree(){
        this.root = new StatementNode(null);
    }

}

abstract class TreeNode {

    TreeNode parent;
    ArrayList<TreeNode> children;

    TreeNode(TreeNode parent){
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    abstract void apply();
    abstract Token check(Scanner scanner) throws Exception;

}

class StatementNode extends TreeNode{

    StatementNode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        Token token = scanner.nextToken();
        while (!token.isTerminal()){
            TreeNode nextNode = null;
            switch (token.tokenType){
                case IF_BEG:
                    nextNode = new ConditionNode(this);
                    break;
                case WHILE_BEG:
                    nextNode = new WhileNode(this);
                    break;
                case IDENTIFIER:
                    nextNode = new AssignmentNode(this, token.text);
                    break;
                case LESS_THAN:
                    nextNode = new OutputNode(this);
                    break;
                case GREATER_THAN:
                    nextNode = new InputNode(this);
                    break;
            }
            if (nextNode == null){
                throw new Exception("Invalid Token, [ or { or letter or < or > expected. Found: "+token.text);
            }else{
                nextNode.check(scanner);
            }
            token = scanner.nextToken();
        }
        if (this.parent != null){
            this.parent.children.add(this);
        }
        return token;
    }

}

class ConditionNode extends TreeNode{

    ConditionNode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        ENode eNode = new ENode(this);
        Token token = eNode.check(scanner);
        if (token.tokenType.equals(TokenType.QUESTION_MARK)){
            StatementNode statementNode = new StatementNode(this);
            token = statementNode.check(scanner);
            if (token.tokenType.equals(TokenType.COLON)){
                StatementNode sNode = new StatementNode(this);
                token = sNode.check(scanner);
            }else if (!token.tokenType.equals(TokenType.IF_END)){
                throw new Exception("Invalid token, ] is expected");
            }
        }else {
            throw new Exception("Invalid token, ? is expected");
        }
        this.parent.children.add(this);
        return token;
    }
}

class WhileNode extends TreeNode{

    WhileNode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        ENode eNode = new ENode(this);
        Token token = eNode.check(scanner);
        if (token.tokenType.equals(TokenType.QUESTION_MARK)){
            StatementNode statementNode = new StatementNode(this);
            statementNode.check(scanner);
        }else {
            throw new Exception("Invalid token, ? is expected. Found: "+token.text);
        }
        this.parent.children.add(this);
        return token;
    }
}

class AssignmentNode extends TreeNode{

    AssignmentNode(TreeNode parent, String letter) {
        super(parent);
        this.children.add(new LetterNode(this, letter));
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        Token token = scanner.nextToken();
        if (token.tokenType.equals(TokenType.EQUAL)){
            ENode eNode = new ENode(this);
            token = eNode.check(scanner);
            if (token.tokenType.equals(TokenType.PARENTHESES_END)){
                token = scanner.nextToken();
            }
            if (!token.tokenType.equals(TokenType.SEMICOLON)){
                throw new Exception("Invalid Token ; is expected");
            }
        }else{
            throw new Exception("Invalid Token = is expected");
        }
        this.parent.children.add(this);
        return token;
    }
}

class OutputNode extends TreeNode{

    OutputNode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        ENode eNode = new ENode(this);
        Token token = eNode.check(scanner);
        if (!token.tokenType.equals(TokenType.SEMICOLON)){
            throw new Exception("Invalid Token ; is expected");
        }
        this.parent.children.add(this);
        return token;
    }
}

class InputNode extends TreeNode{

    InputNode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        ENode eNode = new ENode(this);
        Token token = eNode.check(scanner);
        if (!token.tokenType.equals(TokenType.SEMICOLON)){
            throw new Exception("Invalid Token ; is expected");
        }
        this.parent.children.add(this);
        return token;
    }
}

class ENode extends TreeNode{

    ENode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        TNode tNode = new TNode(this);
        Token token = tNode.check(scanner);
        while (!(token.tokenType.equals(TokenType.IF_END) || token.tokenType.equals(TokenType.WHILE_END) ||
                token.tokenType.equals(TokenType.SEMICOLON) || token.tokenType.equals(TokenType.END_OF_FILE) ||
                token.tokenType.equals(TokenType.QUESTION_MARK) || token.tokenType.equals(TokenType.PARENTHESES_END))){

            if (token.tokenType.equals(TokenType.PLUS) || token.tokenType.equals(TokenType.HYPHEN)){
                TNode secondTNode = new TNode(this);

                token = secondTNode.check(scanner);
            }else {
                throw new Exception("Invalid token, + or - expected. Found: "+token.text);
            }

        }
        this.parent.children.add(this);
        return token;
    }
}

class TNode extends TreeNode{

    TNode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        UNode uNode = new UNode(this);
        Token token = uNode.check(scanner);
        while (!(token.tokenType.equals(TokenType.IF_END) || token.tokenType.equals(TokenType.WHILE_END) ||
                token.tokenType.equals(TokenType.SEMICOLON) || token.tokenType.equals(TokenType.END_OF_FILE) ||
                token.tokenType.equals(TokenType.QUESTION_MARK) || token.tokenType.equals(TokenType.PLUS) ||
                token.tokenType.equals(TokenType.HYPHEN) || token.tokenType.equals(TokenType.PARENTHESES_END))){

            if (token.tokenType.equals(TokenType.STAR) || token.tokenType.equals(TokenType.SLASH) ||
                    token.tokenType.equals(TokenType.REMAINDER)){
                UNode secondUNode = new UNode(this);
                token = secondUNode.check(scanner);
            }else {
                throw new Exception("Invalid token, * or / or % expected. Found: "+token.text);
            }
        }
        this.parent.children.add(this);
        return token;
    }
}

class UNode extends TreeNode{

    UNode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        FNode fNode = new FNode(this);
        Token token = fNode.check(scanner);
        if (token.tokenType.equals(TokenType.EXPONENT)){
            UNode uNode = new UNode(this);
            token = uNode.check(scanner);
        }else if (!(token.tokenType.equals(TokenType.IF_END) || token.tokenType.equals(TokenType.WHILE_END) ||
                token.tokenType.equals(TokenType.SEMICOLON) || token.tokenType.equals(TokenType.END_OF_FILE) ||
                token.tokenType.equals(TokenType.QUESTION_MARK) || token.tokenType.equals(TokenType.PLUS) ||
                token.tokenType.equals(TokenType.HYPHEN) || token.tokenType.equals(TokenType.STAR) ||
                token.tokenType.equals(TokenType.SLASH) || token.tokenType.equals(TokenType.REMAINDER) ||
                token.tokenType.equals(TokenType.PARENTHESES_END))){
            throw new Exception("Invalid Token, ^ or ; or ? expected. Found: "+token.text);
        }
        this.parent.children.add(this);
        return token;
    }
}

class FNode extends TreeNode{

    FNode(TreeNode parent) {
        super(parent);
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) throws Exception {
        Token token = scanner.nextToken();
        if (token.tokenType.equals(TokenType.PARENTHESES_BEG)){
            ENode eNode = new ENode(this);
            token = eNode.check(scanner);
            if (!token.tokenType.equals(TokenType.PARENTHESES_END)){
                throw new Exception("Invalid Token, ) or letter or digit expected");
            }
        }else if (token.tokenType.equals(TokenType.IDENTIFIER)){
            LetterNode letterNode = new LetterNode(this, token.text);
            token = letterNode.check(scanner);
        }else if (token.tokenType.equals(TokenType.NUMBER)){
            DigitNode digitNode = new DigitNode(this, Integer.parseInt(token.text));
            token = digitNode.check(scanner);
        }else {
            throw new Exception("Invalid Token, ( or letter or digit expected. Found: "+token.text);
        }
        this.parent.children.add(this);
        return token;
    }
}

class LetterNode extends TreeNode{

    String letter;

    LetterNode(TreeNode parent, String letter) {
        super(parent);
        this.letter = letter;
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) {
        this.parent.children.add(this);
        return scanner.nextToken();
    }
}

class DigitNode extends TreeNode{

    int digit;

    DigitNode(TreeNode parent, int digit) {
        super(parent);
        this.digit = digit;
    }

    @Override
    void apply() {

    }

    @Override
    Token check(Scanner scanner) {
        this.parent.children.add(this);
        return scanner.nextToken();
    }
}
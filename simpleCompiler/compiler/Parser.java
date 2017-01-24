import java.util.ArrayList;
import java.util.List;

/**
 * Created by scvalencia606 on 1/21/17.
 */
public class Parser {

    private final static List<ASTNodePrecedence> PRECEDENCE_TABLE = new ArrayList<ASTNodePrecedence>() {{

        add(new ASTNodePrecedence("EOI",                false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence("*",                  false,  true,   false,  13,     ASTNodeType.MULTIPLICATION));
        add(new ASTNodePrecedence("/",                  false,  true,   false,  13,     ASTNodeType.DIVISION));
        add(new ASTNodePrecedence("%",                  false,  true,   false,  13,     ASTNodeType.MODULO));
        add(new ASTNodePrecedence("+",                  false,  true,   false,  12,     ASTNodeType.ADDITION));
        add(new ASTNodePrecedence("-",                  false,  true,   false,  12,     ASTNodeType.SUBTRACTION));
        add(new ASTNodePrecedence("-",                  false,  false,  true,   14,     ASTNodeType.NEGATE));
        add(new ASTNodePrecedence("!",                  false,  false,  true,   14,     ASTNodeType.NOT));
        add(new ASTNodePrecedence("<",                  false,  true,   false,  10,     ASTNodeType.LESS));
        add(new ASTNodePrecedence("<=",                 false,  true,   false,  10,     ASTNodeType.LESS_EQUAL));
        add(new ASTNodePrecedence(">",                  false,  true,   false,  10,     ASTNodeType.GREATER));
        add(new ASTNodePrecedence(">=",                 false,  true,   false,  10,     ASTNodeType.GREATER_EQUAL));
        add(new ASTNodePrecedence("==",                 false,  true,   false,   9,     ASTNodeType.EQUAL));
        add(new ASTNodePrecedence("!=",                 false,  true,   false,   9,     ASTNodeType.NOT_EQUAL));
        add(new ASTNodePrecedence("=",                  false,  false,  false,  -1,     ASTNodeType.ASSIGN));
        add(new ASTNodePrecedence("&&",                 false,  true,   false,   5,     ASTNodeType.AND));
        add(new ASTNodePrecedence("||",                 false,  true,   false,   4,     ASTNodeType.OR));
        add(new ASTNodePrecedence("if",                 false,  false,  false,  -1,     ASTNodeType.IF));
        add(new ASTNodePrecedence("else",               false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence("while",              false,  false,  false,  -1,     ASTNodeType.WHILE));
        add(new ASTNodePrecedence("print",              false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence("putc",               false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence("(",                  false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence(")",                  false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence("{",                  false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence("}",                  false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence(";",                  false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence(",",                  false,  false,  false,  -1,     ASTNodeType.NONE));
        add(new ASTNodePrecedence("Ident",              false,  false,  false,  -1,     ASTNodeType.IDENTIFIER));
        add(new ASTNodePrecedence("Integer literal",    false,  false,  false,  -1,     ASTNodeType.INTEGER));
        add(new ASTNodePrecedence("String literal",     false,  false,  false, -1,      ASTNodeType.STRING));

    }};

    private int currentTokenIndex;

    private List<Token> tokens;

    private Token currentToken;

    private String sourceCodeFileName;

    public Parser(List<Token> tokens, String sourceCodeFileName) {

        this.tokens = new ArrayList<>(tokens);

        this.currentTokenIndex = 0;
        this.currentToken = this.tokens.get(this.currentTokenIndex);
        this.sourceCodeFileName = sourceCodeFileName;

    }

    private void error(int currentLine, int currentColumn, String message) {
        String errorMessage = String.format("%s:%d:%d ast-error: %s\n", sourceCodeFileName, currentLine, currentColumn,
            message);

        System.out.printf(errorMessage);
        System.exit(0);
    }

    private void expect(String message, TokenType tokenType) {
        if(currentToken.getTokenType() == tokenType)
            getNextToken();
        else {
            String errorMessage = String.format("%s: Expecting '%s', found '%s'",
                message,
                PRECEDENCE_TABLE.get(tokenType.ordinal()).getName(),
                PRECEDENCE_TABLE.get(currentToken.getTokenType().ordinal()).getName());

            error(currentToken.getLine(), currentToken.getColumn(), errorMessage);
        }
    }

    private void getNextToken() {
        currentTokenIndex++;
        this.currentToken = this.tokens.get(this.currentTokenIndex);
    }

    private ASTNode makeASTNode(ASTNodeType operator, ASTNode left, ASTNode right) {
        return new ASTNode(operator, left, right, null);
    }

    private ASTNode makeASTLeaf(ASTNodeType operator, String value) {
        return new ASTNode(operator, null, null, value);
    }

    private ASTNode parenthesizedExpression() {
        expect("paren_expr", TokenType.LEFT_PARENTHESIS);
        ASTNode abstractSyntaxTreeRoot = expr(0);
        expect("paren_expr", TokenType.RIGHT_PARENTHESIS);
        return abstractSyntaxTreeRoot;
    }

    private ASTNode expr(int p) {
        ASTNode abstractSyntaxTreeRoot = null;

        if(currentToken.getTokenType() == TokenType.LEFT_PARENTHESIS)
            abstractSyntaxTreeRoot = parenthesizedExpression();
        else if(currentToken.getTokenType() == TokenType.ADDITION ||
            currentToken.getTokenType() == TokenType.SUBTRACTION) {

            TokenType operator = (currentToken.getTokenType() == TokenType.SUBTRACTION) ?
                TokenType.NEGATE : TokenType.ADDITION;

            getNextToken();

            ASTNode intermediateNode = expr(PRECEDENCE_TABLE.get(TokenType.NEGATE.ordinal()).getPrecedence());
            abstractSyntaxTreeRoot = (operator == TokenType.NEGATE) ?
                makeASTNode(ASTNodeType.NEGATE, intermediateNode, null) : intermediateNode;
        } else if(currentToken.getTokenType() == TokenType.NOT) {
            getNextToken();
            abstractSyntaxTreeRoot = makeASTNode(ASTNodeType.NOT,
                expr(PRECEDENCE_TABLE.get(TokenType.NOT.ordinal()).getPrecedence()), null);
        } else if(currentToken.getTokenType() == TokenType.IDENTIFIER) {
            abstractSyntaxTreeRoot = makeASTLeaf(ASTNodeType.IDENTIFIER, currentToken.getValue());
            getNextToken();
        } else if(currentToken.getTokenType() == TokenType.INTEGER) {
            abstractSyntaxTreeRoot = makeASTLeaf(ASTNodeType.INTEGER, currentToken.getValue());
            getNextToken();
        } else {
            String errorMessage = String.format("Expecting a primary, found: %s",
                PRECEDENCE_TABLE.get(currentToken.getTokenType().ordinal()).getName());

            error(currentToken.getLine(), currentToken.getColumn(), errorMessage);
        }

        while(PRECEDENCE_TABLE.get(currentToken.getTokenType().ordinal()).isBinary() &&
            (PRECEDENCE_TABLE.get(currentToken.getTokenType().ordinal()).getPrecedence() >= p)) {
            TokenType operator = currentToken.getTokenType();
            getNextToken();
            int q = PRECEDENCE_TABLE.get(operator.ordinal()).getPrecedence();

            if(!PRECEDENCE_TABLE.get(operator.ordinal()).isRightAssociative())
                q++;

            ASTNode intermediateNode = expr(q);
            abstractSyntaxTreeRoot = makeASTNode(PRECEDENCE_TABLE.get(operator.ordinal()).getNodeType(),
                abstractSyntaxTreeRoot, intermediateNode);
        }

        return abstractSyntaxTreeRoot;
    }

    private ASTNode statement() {
        ASTNode abstractSyntaxTreeRoot = null;

        ASTNode expression;
        ASTNode statementLeft;
        ASTNode statementRight = null;

        if(currentToken.getTokenType() == TokenType.IF) {
            getNextToken();

            expression = parenthesizedExpression();
            statementLeft = statement();

            if(currentToken.getTokenType() == TokenType.ELSE) {
                getNextToken();
                statementRight = statement();
            }

            abstractSyntaxTreeRoot = makeASTNode(ASTNodeType.IF, expression,
                makeASTNode(ASTNodeType.IF, statementLeft, statementRight));

        } else if(currentToken.getTokenType() == TokenType.PUTC) {
            getNextToken();

            expression = parenthesizedExpression();
            abstractSyntaxTreeRoot = makeASTNode(ASTNodeType.PRT_CHAR, expression, null);
            expect("putc", TokenType.SEMICOLON);

        } else if(currentToken.getTokenType() == TokenType.PRINT) {
            getNextToken();

            expect("print", TokenType.LEFT_PARENTHESIS);

            for(;;) {
                if(currentToken.getTokenType() == TokenType.STRING) {
                    expression = makeASTNode(ASTNodeType.PRT_STRING,
                        makeASTLeaf(ASTNodeType.STRING, currentToken.getValue()), null);

                    getNextToken();
                } else
                    expression = makeASTNode(ASTNodeType.PRT_IDENTIFIER, expr(0), null);

                abstractSyntaxTreeRoot = makeASTNode(ASTNodeType.SEQUENCE, abstractSyntaxTreeRoot, expression);

                if(currentToken.getTokenType() != TokenType.COMMA)
                    break;

                getNextToken();

            }

            expect("print", TokenType.RIGHT_PARENTHESIS);
            expect("print", TokenType.SEMICOLON);

        } else if(currentToken.getTokenType() == TokenType.SEMICOLON) {
            getNextToken();
        } else if(currentToken.getTokenType() == TokenType.IDENTIFIER) {
            ASTNode value = makeASTLeaf(ASTNodeType.IDENTIFIER, currentToken.getValue());
            getNextToken();
            expect("assign", TokenType.ASSIGN);
            expression = expr(0);

            abstractSyntaxTreeRoot = makeASTNode(ASTNodeType.ASSIGN, value, expression);

            expect("assign", TokenType.SEMICOLON);

        } else if(currentToken.getTokenType() == TokenType.WHILE) {
            getNextToken();

            expression = parenthesizedExpression();
            statementLeft = statement();
            abstractSyntaxTreeRoot = makeASTNode(ASTNodeType.WHILE, expression, statementLeft);

        } else if(currentToken.getTokenType() == TokenType.LEFT_CURLY) {
            getNextToken();

            while(currentToken.getTokenType() != TokenType.RIGHT_CURLY &&
                currentToken.getTokenType() != TokenType.EOI)

                abstractSyntaxTreeRoot = makeASTNode(ASTNodeType.SEQUENCE, abstractSyntaxTreeRoot, statement());

            expect("lbrace", TokenType.RIGHT_CURLY);

        } else if(currentToken.getTokenType() == TokenType.EOI) {
            return abstractSyntaxTreeRoot;
        } else {
            String message = String.format("Expecting start of statement, found: %s",
                PRECEDENCE_TABLE.get(currentToken.getTokenType().ordinal()).getName());

            error(currentToken.getLine(), currentToken.getColumn(), message);
        }


        return abstractSyntaxTreeRoot;
    }

    public ASTNode parse() {
        ASTNode abstractSyntaxTreeRoot = null;


        while(currentToken.getTokenType() != TokenType.EOI)
            abstractSyntaxTreeRoot = makeASTNode(ASTNodeType.SEQUENCE, abstractSyntaxTreeRoot, statement());

        return abstractSyntaxTreeRoot;
    }

}

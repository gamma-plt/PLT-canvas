/**
 * Created by scvalencia606 on 1/20/17.
 */
public enum TokenType {

    /* End of Input Token */
    EOI,

	/* Arithmetic operators */
	MULTIPLICATION, DIVISION, MODULO, ADDITION, SUBTRACTION,

    /* Relational operators */
    NEGATE, NOT, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL, EQUAL, NOT_EQUAL, ASSIGN,

	/* Boolean operators */
	AND, OR,

	/* Keywords */
	IF, ELSE, WHILE, PRINT, PUTC, 

	/* Separators */
	LEFT_PARENTHESIS, RIGHT_PARENTHESIS, LEFT_CURLY, RIGHT_CURLY, SEMICOLON, COMMA,

	/* Valued Tokens */
	IDENTIFIER, INTEGER, STRING
	
}
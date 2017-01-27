/**
 * TokenType is an enum with constants for the types or categories
 * that a Token object may have. Those categories are exclusive,
 * and are used to identify the type for the compiler of a character
 * stream. This is what distinguishes a token from another token.
 * This is also the main input for parsing. The categories are:
 *
 * <li> {@link #EOI} </li>
 * <li> {@link #MULTIPLICATION} </li>
 * <li> {@link #DIVISION} </li>
 * <li> {@link #MODULO} </li>
 * <li> {@link #ADDITION} </li>
 * <li> {@link #SUBTRACTION} </li>
 * <li> {@link #NEGATE} </li>
 * <li> {@link #EQUAL} </li>
 * <li> {@link #NOT} </li>
 * <li> {@link #NOT_EQUAL} </li>
 * <li> {@link #LESS} </li>
 * <li> {@link #LESS_EQUAL} </li>
 * <li> {@link #GREATER} </li>
 * <li> {@link #GREATER_EQUAL} </li>
 * <li> {@link #ASSIGN} </li>
 * <li> {@link #AND} </li>
 * <li> {@link #OR} </li>
 * <li> {@link #IF} </li>
 * <li> {@link #ELSE} </li>
 * <li> {@link #WHILE} </li>
 * <li> {@link #PRINT} </li>
 * <li> {@link #PUTC} </li>
 * <li> {@link #LEFT_PARENTHESIS} </li>
 * <li> {@link #RIGHT_PARENTHESIS} </li>
 * <li> {@link #LEFT_CURLY} </li>
 * <li> {@link #RIGHT_CURLY} </li>
 * <li> {@link #SEMICOLON} </li>
 * <li> {@link #COMMA} </li>
 * <li> {@link #IDENTIFIER} </li>
 * <li> {@link #INTEGER} </li>
 * <li> {@link #STRING} </li>
 *
 * @author      Sebastián Valencia (sc.valencia606@gmail.com)
 */
public enum TokenType {

    /* End of Input Token */
    EOI,

	/* Arithmetic operators */
	MULTIPLICATION, DIVISION, MODULO, ADDITION, SUBTRACTION,

    /* Relational operators */
    NEGATE, EQUAL, NOT, NOT_EQUAL, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL, ASSIGN,

	/* Boolean operators */
	AND, OR,

	/* Keywords */
	IF, ELSE, WHILE, PRINT, PUTC, 

	/* Separators */
	LEFT_PARENTHESIS, RIGHT_PARENTHESIS, LEFT_CURLY, RIGHT_CURLY, SEMICOLON, COMMA,

	/* Valued Tokens */
	IDENTIFIER, INTEGER, STRING
	
}
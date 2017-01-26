/**
 * Token is a class to model a single token or lexical token
 * that represents the category (in case that the lexer succeeds)
 * of a program construct. The lexical analysis part, converts a
 * sequence of characters (the source code), into a sequence of
 * tokens, that is, strings with identified meaning. This class,
 * represents valued tokens, besides the category, a Token object
 * holds information on it's position within the source code, and
 * a value if needed. The information of every Token object is
 * encapsulated in this attributes:
 *
 * <ul>
 * <li>The line of the processed Token object
 * <li>The column of the processed Token object
 * <li>The type of the Token, that is, it's category
 * <li>The value of the Token, this is used only if needed, otherwise,
 * it should be null
 * </ul>
 *
 * @author      Sebastián Valencia (sc.valencia606@gmail.com)
 */
public class Token {

    /**
     * Represents the line which the Token is located in
     */
    private int line;

    /**
     * Represents the column of the line which the Token is located in
     */
    private int column;

    /**
     * The class of the Token, it depends upon the definition of the language
     */
    private TokenType tokenType;

    /**
     * This field represents the value of a Token, if it's a valued Token, otherwise,
     * it should be set to null
     */
    private String value;


    /**
     * Constructor for a Token object. This is the constructor for the most generic
     * Token we can imagine. It still applies to several languages, the main elements
     * of a Token object, are highlighted in the fields of this class
     *
     * @param line The line where the Token object is located in. It helps to the
     *             programmer to debug her/his own code, this helps to provide the
     *             information when an error must be displayed.
     *
     * @param column It provides the same features as line. Both line and columns,
     *               set the position of the initial character of the Token object.
     *
     * @param tokenType Specifies the type of the Token object, it is one of the enum
     *                  constants provided by TokenType.
     *
     * @param value Is the value of the Token object if it needs one, only few cases
     *              actually need this field to be set different to null.
     */
	public Token(int line, int column, TokenType tokenType, String value) {
		this.line = line;
		this.column = column;
		this.tokenType = tokenType;
		this.value = value;
	}

    /**
     * {@link Token#line}
     */
    public int getLine() {
        return line;
    }

    /**
     * {@link Token#column}
     */
    public int getColumn() {
        return column;
    }

    /**
     * {@link Token#tokenType}
     */
    public TokenType getTokenType() {
        return this.tokenType;
    }

    /**
     * {@link Token#value}
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Gives a String representation of a Token object, it should be used for
     * Lexer debugging
     *
     * @return String representation of the Token.
     */
    public String toString() {

		return String.format("%5d  %5d   %-18s   %s", this.line, this.column, this.tokenType,
            (this.value == null) ? "" : (this.tokenType == TokenType.STRING) ? "\"" + this.value + "\"" : this.value);

	}
}
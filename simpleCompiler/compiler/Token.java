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

	private int line;

    private int column;

    private TokenType tokenType;

    private String value;

	public Token(int line, int column, TokenType tokenType, String value) {
		this.line = line;
		this.column = column;
		this.tokenType = tokenType;
		this.value = value;
	}

    public TokenType getTokenType() {
        return this.tokenType;
    }

    public String getValue() {
        return this.value;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String toString() {

		return String.format("%5d  %5d   %-18s   %s", this.line, this.column, this.tokenType,
            (this.value == null) ? "" : (this.tokenType == TokenType.STRING) ? "\"" + this.value + "\"" : this.value);

	}
}
/**
 * Created by scvalencia606 on 1/20/17.
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
            (this.value == null) ? "" : this.value);

	}
}
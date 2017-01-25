/**
* Represents a Token
*/
public class Token {

	TokenType tokenType;

	String value;

	public Token(TokenType tokenType, String value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	public String toString() {
		return String.format("Token(%s, %s)", this.tokenType, this.value);
	}
}
import java.math.*;

public class Interpreter {

	// The expression to parse and calculate
	String expression;

	// Position in the expression
	int position;

	// Current Token being processed
	Token currentToken;

	// Current character being processed
	Character currentChar;

	public Interpreter(String expression) {
		this.expression = expression;
		this.position = 0;
		this.currentToken = null;
		this.currentChar = this.expression.charAt(this.position);
	}

	private void error(int position) {
		System.out.println("Error parsing input expression at " + position);
		System.exit(1);
	}

	private void advance() {
		this.position++;

		if(this.position > this.expression.length() - 1)
			this.currentChar = null;
		else
			this.currentChar = this.expression.charAt(this.position);
	}

	private void skipWhitespaces() {
		while(this.currentChar != null && this.currentChar == ' ')
			this.advance();
	}

	private String integer() {
		String value = "";

		while(this.currentChar != null && Character.isDigit(this.currentChar)) {
			value += this.currentChar;
			this.advance();
		}

		return value;
	}

	// The scanner
	private Token getNextToken() {

		Token nextToken = new Token(TokenType.EOF, null);

		while(this.currentChar != null) {

			if(this.currentChar == ' ') {
				this.skipWhitespaces();
				continue;
			}

			else if(Character.isDigit(this.currentChar)) {
				nextToken = new Token(TokenType.INTEGER, this.integer());
				break;
			}
	
			else if(this.currentChar == '+') {
				this.advance();
				nextToken = new Token(TokenType.PLUS, "+");
				break;
			} 

			else if(this.currentChar == '-') {
				this.advance();
				nextToken = new Token(TokenType.MINUS, "-");
				break;
			}

			else if(this.currentChar == '*') {
				this.advance();
				nextToken = new Token(TokenType.PROD, "*");
				break;
			}

			else if(this.currentChar == '/') {
				this.advance();
				nextToken = new Token(TokenType.DIVISION, "/");
				break;
			}
			
			else
				this.error(this.position);
		}

		return nextToken;

	}

	/**
	* Compare the current token type with the passed token type and if they 
	* match then accept the current token and assign the next token to the 
	* this.currentToken token. Otherwise, report an error.
	*/
	private void accept(TokenType expectedTokenType) {
		if(this.currentToken.tokenType == expectedTokenType)
			this.currentToken = this.getNextToken();
		else
			this.error(this.position);
	}

	// The parser and AST interpreter
	// expr -> INTEGER PLUS INTEGER
	public BigInteger expr() {

		// Initialize the variable with the first token
		this.currentToken = this.getNextToken();

		// According to the grammar, we're looking for a digit
		Token left = this.currentToken;
		this.accept(TokenType.INTEGER);

		// According to the grammar, we're looking for a PLUS token
		Token operator = this.currentToken;

		if(operator.tokenType == TokenType.PLUS) {
			this.accept(TokenType.PLUS);
		} else if(operator.tokenType == TokenType.MINUS) {
			this.accept(TokenType.MINUS);
		} else if(operator.tokenType == TokenType.PROD) {
			this.accept(TokenType.PROD);
		} else if(operator.tokenType == TokenType.DIVISION) {
			this.accept(TokenType.DIVISION);
		}

		// According to the grammar, we're looking for a digit
		Token right = this.currentToken;
		this.accept(TokenType.INTEGER);

		// Fetch the data from both tokens, convert them to real integers
		BigInteger leftValue = new BigInteger(left.value);
		BigInteger rightValue = new BigInteger(right.value);
		BigInteger result = BigInteger.ZERO;

		switch (operator.tokenType) {
			case PLUS:
				result = leftValue.add(rightValue);
				break;

			case MINUS:
				result = leftValue.subtract(rightValue);
				break;

			case PROD:
				result = leftValue.multiply(rightValue);
				break;

			case DIVISION:
				result = leftValue.divide(rightValue);
				break;
		}

		return result; 

	}

	public BigInteger interpret() {
		return expr();
	}
}
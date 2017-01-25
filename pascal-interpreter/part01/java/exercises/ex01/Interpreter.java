public class Interpreter {

	// The expression to parse and calculate
	String expression;

	// Position in the expression
	int position;

	// Current Token being processed
	Token currentToken;

	public Interpreter(String expression) {
		this.expression = expression;
		this.position = 0;
		this.currentToken = null;
	}

	private void error() {
		System.out.println("Error parsing input expression");
		System.exit(1);
	}

	// The scanner
	private Token getNextToken() {

		Token nextToken = null;

		// End of Input
		if(this.position >= this.expression.length())
			nextToken = new Token(TokenType.EOF, null);

		else {
			/**
			* Get a character at the position this.position and decide
            * what token to create based on the single character
			*/
			char currentChar = this.expression.charAt(this.position);

			/**
			* If the character is a digit then converit to an integer, and
			* return an INTEGER token. Increment this.position.
			*/

			if(Character.isDigit(currentChar)) {

				String value = "";

				while(this.position < this.expression.length() && Character.isDigit(this.expression.charAt(this.position))) {
					value += this.expression.charAt(this.position);
					this.position++;

				}

				nextToken = new Token(TokenType.INTEGER, Integer.parseInt(value) + "");
				//this.position++;
			} 

			/**
			* Otherwise, it is the operator. Create the token and 
			* Increment this.position.
			*/

			else if(currentChar == '+') {
				nextToken = new Token(TokenType.PLUS, currentChar + "");
				this.position++;
			} else
				this.error();
		}

		currentToken = nextToken;
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
			this.error();
	}

	// The parser and AST interpreter
	// expr -> INTEGER PLUS INTEGER
	public int interpret() {

		// Initialize the variable with the first token
		this.currentToken = this.getNextToken();

		// According to the grammar, we're looking for a digit
		Token left = this.currentToken;
		this.accept(TokenType.INTEGER);

		// According to the grammar, we're looking for a PLUS token
		Token operator = this.currentToken;
		this.accept(TokenType.PLUS);

		// According to the grammar, we're looking for a digit
		Token right = this.currentToken;
		this.accept(TokenType.INTEGER);

		// Fetch the data from both tokens, convert them to real integers
		int leftValue = Integer.valueOf(left.value);
		int rightValue = Integer.valueOf(right.value);

		// Return the sum of those values
		return leftValue + rightValue;

	}


}
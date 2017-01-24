import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by scvalencia606 on 1/20/17.
 */
public class Tokenizer {

	private String sourceCodeFileName;
	private BufferedReader fileReader;
	private int currentCharacterCode;
	private char currentCharacter;
	private int currentLine;
	private int currentColumn;

	private static final HashMap<Character, TokenType> symbols = new HashMap<Character, TokenType>() {{
		put('{', TokenType.LEFT_CURLY);
		put('}', TokenType.RIGHT_CURLY);
		put('(', TokenType.LEFT_PARENTHESIS);
		put(')', TokenType.RIGHT_PARENTHESIS);
		put('+', TokenType.ADDITION);
		put('-', TokenType.SUBTRACTION);
		put('*', TokenType.MULTIPLICATION);
		put('%', TokenType.MODULO);
		put(';', TokenType.SEMICOLON);
		put(',', TokenType.COMMA);
	}};

	private static final HashMap<String, TokenType> keyWords = new HashMap<String, TokenType>() {{
		put("if", TokenType.IF);
		put("while", TokenType.WHILE);
		put("else", TokenType.ELSE);
		put("print", TokenType.PRINT);
		put("putc", TokenType.PUTC);
	}};

    public Tokenizer(File sourceCodeFileObject) {
        this.sourceCodeFileName = sourceCodeFileObject.getName();
        this.currentCharacterCode = 0;
        this.currentCharacter = ' ';
        this.currentLine = 1;
        this.currentColumn = 0;

        try {
            this.fileReader = new BufferedReader(new FileReader(sourceCodeFileObject));
        } catch(FileNotFoundException e) {
            System.out.println("simplec: file not found: " + sourceCodeFileName);
            System.out.println("Usage: simplec <source file>");
            System.exit(0);
        }
    }

    private char advanceCurrentCharacter() throws IOException {
		currentCharacterCode = fileReader.read();

		if(currentCharacterCode == -1)
            return '\0';

		currentCharacter = (char) currentCharacterCode;
		currentColumn++;

		if(currentCharacter == '\n') {
			currentLine++;
			currentColumn = 0;
			advanceCurrentCharacter();
		}

		return currentCharacter;
	}

	private void error(int currentLine, int currentColumn, String message) {
		System.out.printf("%s:%d:%d tok-error: %s\n", sourceCodeFileName, currentLine, currentColumn, message);
		System.exit(0);
	}

	private Token divisionOrCommentToken(int currentLine, int currentColumn) throws IOException {
		if(advanceCurrentCharacter() != '*')
			return new Token(currentLine, currentColumn, TokenType.DIVISION, null);

		advanceCurrentCharacter();
		while(true) {
			if(currentCharacter == '*') {

				if(advanceCurrentCharacter() == '/') {
					advanceCurrentCharacter();
					return getNextToken();
				}
			}

			else if(currentCharacterCode == -1)
				error(currentLine, currentColumn, "Found EOF while parsing comment");

			else
				advanceCurrentCharacter();
		}
	}

	private Token charConstant(int currentLine, int currentColumn) throws IOException {
		int ord = (int) advanceCurrentCharacter();

		switch(currentCharacter) {

			case '\'':
				error(currentLine, currentColumn, "Empty character constant");
				break;

			case '\\':
				advanceCurrentCharacter();

				switch(currentCharacter) {

					case 'n':
						ord = (int) '\n';
						break;

					case '\\':
						ord = (int) '\\';
						break;

					default:
						error(currentLine, currentColumn, "Unknown escape sequence \\" + currentCharacter);
						break;
				}

				break;
		}

		if(advanceCurrentCharacter() != '\'')
			error(currentLine, currentColumn, "Multi-character constant");

		advanceCurrentCharacter();
		return new Token(currentLine, currentColumn, TokenType.INTEGER, ord + "");
	}

	private Token stringLiteral(char currentChar, int currentLine, int currentColumn) throws IOException {
		String value = "";

		while(advanceCurrentCharacter() != currentChar) {
			if(currentCharacterCode == -1 || currentCharacter == '\n')
				error(currentLine, currentColumn, "EOF while scanning string literal");
			value += currentCharacter;
		}

		advanceCurrentCharacter();
		return new Token(currentLine, currentColumn, TokenType.STRING, value);
	}

	private Token identifierOrInteger(int currentLine, int currentColumn) throws IOException {
		boolean isNumber = true;
		String text = "";


		while(Character.isLetterOrDigit(currentCharacter) || currentCharacter == '_') {

			text += currentCharacter;

			if(!Character.isDigit(currentCharacter))
				isNumber = false;

			advanceCurrentCharacter();
		}


		if(text.length() == 0)
			error(currentLine, currentColumn, "Unrecognized character " + currentCharacter);

		if(Character.isDigit(text.charAt(0))) {
			if(!isNumber)
				error(currentLine, currentColumn, "Invalid number " + text);

			int number = Integer.valueOf(text);
			return new Token(currentLine, currentColumn, TokenType.INTEGER, number + "");
		}



		if(keyWords.containsKey(text))
			return new Token(currentLine, currentColumn, keyWords.get(text), null);

		return new Token(currentLine, currentColumn, TokenType.IDENTIFIER, text);
	}

	private Token follow(char expect, TokenType possibility1, TokenType possibility2, int currentLine,
								int currentColumn) throws IOException {

		if(advanceCurrentCharacter() == expect) {
			advanceCurrentCharacter();
			return new Token(currentLine, currentColumn, possibility1, null);
		}

		if(possibility2 == TokenType.EOI)
			error(currentLine, currentColumn, "Unrecognized character " + currentCharacter);

		return new Token(currentLine, currentColumn, possibility2, null);
	}

	private Token getNextToken() throws IOException {

		while(currentCharacter == ' ')
			advanceCurrentCharacter();

		int errorLine, errorColumn;

		errorLine = currentLine;
		errorColumn = currentColumn;

		if(currentCharacterCode == -1)
			return new Token(errorLine, errorColumn, TokenType.EOI, null);

		else if(currentCharacter == '/')
			return divisionOrCommentToken(errorLine, errorColumn);

		else if(currentCharacter == '\'')
			return charConstant(errorLine, errorColumn);

		else if(currentCharacter == '<')
			return follow('=', TokenType.LESS_EQUAL, TokenType.LESS, errorLine, errorColumn);

		else if(currentCharacter == '>')
			return follow('=', TokenType.GREATER_EQUAL, TokenType.GREATER, errorLine, errorColumn);

		else if(currentCharacter == '=')
			return follow('=', TokenType.EQUAL, TokenType.ASSIGN, errorLine, errorColumn);

		else if(currentCharacter == '!')
			return follow('=', TokenType.NOT_EQUAL, TokenType.NOT, errorLine, errorColumn);

		else if(currentCharacter == '&')
			return follow('&', TokenType.AND, TokenType.EOI, errorLine, errorColumn);

		else if(currentCharacter == '|')
			return follow('|', TokenType.OR, TokenType.EOI, errorLine, errorColumn);

		else if(currentCharacter == '"')
			return stringLiteral(currentCharacter, errorLine, errorColumn);

		else if(symbols.containsKey(currentCharacter)) {
			TokenType symbol = symbols.get(currentCharacter);
			advanceCurrentCharacter();
			return new Token(errorLine, errorColumn, symbol, null);
		}

		else
			return identifierOrInteger(currentLine, currentColumn);
	}

    public List<Token> tokenize() throws IOException {
        List<Token> tokens = new ArrayList<>();

        while(true) {
            Token token = getNextToken();
            tokens.add(token);

            if(token.getTokenType() == TokenType.EOI)
                break;
        }

        return tokens;
    }

}
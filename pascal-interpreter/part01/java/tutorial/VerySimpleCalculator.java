import java.io.*;

public class VerySimpleCalculator {

	public static void main(String[] args) {

		Console console = System.console();

		while(true) {
			System.out.print("calc >> ");
			String input = console.readLine();
			Interpreter interpreter = new Interpreter(input);
			int value = interpreter.interpret();
			System.out.println(value);
		}	
		
	}
}
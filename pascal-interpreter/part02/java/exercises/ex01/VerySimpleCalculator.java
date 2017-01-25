import java.io.*;
import java.math.*;

public class VerySimpleCalculator {

	public static void main(String[] args) {

		Console console = System.console();

		while(true) {
			System.out.print("calc >> ");
			String input = console.readLine();
			Interpreter interpreter = new Interpreter(input);
			BigInteger value = interpreter.interpret();
			System.out.println(value);
		}	
		
	}
}
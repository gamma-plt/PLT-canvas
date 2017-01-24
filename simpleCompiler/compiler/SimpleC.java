import java.io.*;
import java.util.List;

/**
 * Created by scvalencia606 on 1/20/17.
 * Usage: javac SimpleC.java && java SimpleC ../examples/test/tokenizer/example6.scc && rm *.class
 */
public class SimpleC {

    static final boolean DEBUGGING = true;

    static String sourceCodeFileName;

    static File sourceCodeFileObject;

    static List<Token> tokens;

    static ASTNode abstractSyntaxTree;

    public static void main(String[] args) throws IOException {

        if(args.length != 1) {
            System.out.println("Usage: simplec <source file>");
            System.exit(0);
        }

        sourceCodeFileName = args[0];
        sourceCodeFileObject = new File(sourceCodeFileName);

        Tokenizer tokenizer = new Tokenizer(sourceCodeFileObject);
        tokens = tokenizer.tokenize();

        Parser parser = new Parser(tokens, sourceCodeFileName);
        abstractSyntaxTree = parser.parse();


        if(DEBUGGING)
            writeDebuggingFiles();

    }

    public static void writeDebuggingFiles() {

        String[] path = sourceCodeFileName.split(File.separator);
        String filename = path[path.length - 1];
        filename = filename.substring(0, filename.indexOf('.'));

        String mainFolder = sourceCodeFileObject.getParent();

        String lexerOutputFileName = mainFolder + File.separator + filename + '.' + "tok" + '.' + "scc";
        String parserOutputFileName = mainFolder + File.separator + filename + '.' + "ast" + '.' + "scc";

        File lexerOutputFile = new File(lexerOutputFileName);
        File parserOutputFile = new File(parserOutputFileName);

        try {
            lexerOutputFile.createNewFile();
            parserOutputFile.createNewFile();


        } catch(IOException e) {
            e.printStackTrace();
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(lexerOutputFile), "utf-8"))) {

            for(Token token : tokens)
                writer.write(token.toString() + "\n");

        } catch(IOException e) {
            e.printStackTrace();
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(parserOutputFile), "utf-8"))) {

            if(abstractSyntaxTree != null)
                writer.write(ASTNode.displayFlattRepresentation(abstractSyntaxTree));

        } catch(IOException e) {
            e.printStackTrace();
        }

    }
}

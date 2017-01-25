import java.util.ArrayList;
import java.util.List;

/**
 * Created by scvalencia606 on 1/21/17.
 */
public class ASTNode {

    private static final List<String> DISPLAY_NODES = new ArrayList<String>() {{
        add("IDENTIFIER");
        add("STRING");
        add("INTEGER");
        add("SEQUENCE");
        add("IF");
        add("PRTC");
        add("PRTS");
        add("PRTI");
        add("WHILE");
        add("ASSIGN");
        add("NEGATE");
        add("NOT");
        add("MULTIPLY");
        add("DIVIDE");
        add("MOD");
        add("ADD");
        add("SUBSTRACT");
        add("LESS");
        add("LESS_EQUAL");
        add("GREATER");
        add("GREATER_EQUAL");
        add("EQUAL");
        add("NOT_EQUAL");
        add("AND");
        add("OR");
    }};

    private ASTNodeType nodeType;

    private ASTNode left;

    private ASTNode right;

    private String value;

    public ASTNode(ASTNodeType nodeType, ASTNode left, ASTNode right, String value) {
        this.nodeType = nodeType;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public ASTNodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(ASTNodeType nodeType) {
        this.nodeType = nodeType;
    }

    public ASTNode getLeft() {
        return left;
    }

    public void setLeft(ASTNode left) {
        this.left = left;
    }

    public ASTNode getRight() {
        return right;
    }

    public void setRight(ASTNode right) {
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String displayFlattRepresentation(ASTNode astNode) {
        String ans = "";

        if(astNode == null)
            ans = ";\n";
        else {
            ans += String.format("%-14s", DISPLAY_NODES.get(astNode.nodeType.ordinal()));

            if(astNode.nodeType == ASTNodeType.IDENTIFIER || astNode.nodeType == ASTNodeType.INTEGER)
                ans += String.format("%s\n", astNode.value);
            else if(astNode.nodeType == ASTNodeType.STRING)
                ans += String.format("\"%s\"\n", astNode.value);
            else {
                ans += "\n";
                ans += displayFlattRepresentation(astNode.left);
                ans += displayFlattRepresentation(astNode.right);
            }
        }

        return ans;
    }
}

/**
 * Created by scvalencia606 on 1/21/17.
 */
public class ASTNodePrecedence {

    private String name;
    private boolean rightAssociative;
    private boolean binary;
    private boolean unary;
    private int precedence;
    private ASTNodeType nodeType;

    public ASTNodePrecedence(String name, boolean rightAssociative, boolean binary, boolean unary, int precedence,
                             ASTNodeType nodeType) {
        this.name = name;
        this.rightAssociative = rightAssociative;
        this.binary = binary;
        this.unary = unary;
        this.precedence = precedence;
        this.nodeType = nodeType;
    }

    public String getName() {
        return name;
    }

    public boolean isRightAssociative() {
        return rightAssociative;
    }

    public boolean isBinary() {
        return binary;
    }

    public boolean isUnary() {
        return unary;
    }

    public int getPrecedence() {
        return precedence;
    }

    public ASTNodeType getNodeType() {
        return nodeType;
    }
}

public class NoOperationException extends RuntimeException {

    public NoOperationException(String exp) {
        super("No operators found:\n\t\"+\" \"-\" \"*\" \"/\"\n\tIn expression: " + exp);
    }
}

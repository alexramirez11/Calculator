public class ZeroException extends RuntimeException {
    
    public ZeroException(String op) {
        super("Cannot " + op + " by zero");
    }
}

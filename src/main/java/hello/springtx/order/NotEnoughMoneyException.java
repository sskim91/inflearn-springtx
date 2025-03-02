package hello.springtx.order;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(final String message) {
        super(message);
    }

}

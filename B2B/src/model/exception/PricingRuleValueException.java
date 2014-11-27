package model.exception;

public class PricingRuleValueException extends AppException {
    public PricingRuleValueException() { }
    public PricingRuleValueException(String msg, Exception e) {
        super(msg, e);
    }
    public PricingRuleValueException(String msg) {
        super(msg);
    }
}

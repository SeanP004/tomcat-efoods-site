package model.pricing;

public interface PriceOverride {

	boolean override(Cost cost, PriceManager singleton);
}

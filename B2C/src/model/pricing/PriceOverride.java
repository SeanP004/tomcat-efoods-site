package model.pricing;

/**
 * 
 * interface that will pass function though as a price override in filter
 * for cross selling
 *
 */
public interface PriceOverride {

	boolean override(Cost cost, PriceManager singleton);
}

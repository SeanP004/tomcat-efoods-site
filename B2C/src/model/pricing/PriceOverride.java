package model.pricing;

import model.cart.*;

public interface PriceOverride {

	boolean override(Cost cost, PriceManager singleton);
}

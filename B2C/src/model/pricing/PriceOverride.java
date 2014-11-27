package model.pricing;

import model.cart.*;

interface PriceOverride {

	boolean override(Cost cost, PriceManager singleton);
}

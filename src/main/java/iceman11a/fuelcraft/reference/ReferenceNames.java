package iceman11a.fuelcraft.reference;


public class ReferenceNames
{
    public static final String NAME_FLUID_DIESEL                    = "diesel";
    public static final String NAME_FLUID_OIL                       = "oil";
    public static final String NAME_FLUID_TAPOIL                    = "tapoil";

    // Block registration names
    public static final String NAME_TILE_ORES                       = "ore";
    public static final String NAME_TILE_MACHINE                    = "machine";

    // Individual block/item (unlocalized) names
    public static final String NAME_ORE_CORBAMITE                   = "corbamite";
    public static final String NAME_ORE_CORCOAL                     = "corcoal";
    public static final String NAME_ORE_REDCOR                      = "redcor";
    public static final String NAME_ORE_BLACKDIAMOND                = "blackdiamond";

    public static final String NAME_TILE_CART_PAINTER               = "cartpainter";
    public static final String NAME_TILE_CART_FILTER                = "cartfilter";
    public static final String NAME_TILE_DIESEL_PRODUCER            = "dieselproducer";
    public static final String NAME_TILE_OIL_PRODUCER               = "oilproducer";
    public static final String NAME_TILE_TAPOIL_PRODUCER            = "tapoilproducer";
    public static final String NAME_TILE_TOKEN_CONTROLLER           = "tokencontroller";
    public static final String NAME_TILE_CROSSING_GATE              = "crossinggate";

    // Items
    public static final String NAME_ITEM_DIESEL_BUCKET              = "dieselbucket";
    public static final String NAME_ITEM_ORE_RESOURCE               = "resource";
    public static final String NAME_ITEM_TAPOIL_BUCKET              = "tapoilbucket";
    public static final String NAME_ITEM_TOKEN                      = "token";

    public static final String NAME_ENTITY_DIESEL_TRAIN_ENGINE      = "dieseltrainengine";

    public static final String FORESTBIOMENAME                      = "Blue light Forest";
    public static final String NAME_BLOCK_TOKEN_TRACK               = "tokentrack";

    public static String getPrefixedName(String name)
    {
        return Reference.MOD_ID + "." + name;
    }
}

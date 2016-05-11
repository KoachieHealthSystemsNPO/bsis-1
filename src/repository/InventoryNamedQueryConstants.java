package repository;

public class InventoryNamedQueryConstants {

  public static final String NAME_FIND_STOCK_LEVELS_FOR_LOCATION = "Component.findStockLevelsForLocation";
  public static final String QUERY_FIND_STOCK_LEVELS_FOR_LOCATION =
      "SELECT NEW dto.StockLevelDTO(c.location, c.componentType, c.donation.bloodAbo, c.donation.bloodRh, COUNT(c)) " +
      "FROM Component c " +
      "WHERE c.location = :location AND c.inventoryStatus = :inventoryStatus AND c.isDeleted = :deleted " +
      "GROUP BY c.location, c.componentType, c.donation.bloodAbo, c.donation.bloodRh " +
      "ORDER BY c.location, c.componentType, c.donation.bloodAbo, c.donation.bloodRh";
}

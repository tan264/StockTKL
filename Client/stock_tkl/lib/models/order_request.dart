class OrderRequest {
  final String symbol;
  final String orderType;
  final String direction;
  final int quantity;
  final double price;

  OrderRequest(
      {required this.symbol,
      required this.orderType,
      required this.direction,
      required this.quantity,
      required this.price});
}

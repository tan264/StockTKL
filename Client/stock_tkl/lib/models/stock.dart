class Stock {
  final int stockId;
  final String symbol;
  final String companyName;
  final String industry;
  final String sector;
  final int quantity;

  Stock(
      {required this.stockId,
      required this.symbol,
      required this.companyName,
      required this.industry,
      required this.sector,
      required this.quantity});

  factory Stock.fromJson(Map<String, dynamic> json) => Stock(
        stockId: json['stockId'],
        symbol: json['symbol'],
        companyName: json['companyName'],
        industry: json['industry'],
        sector: json['sector'],
        quantity: json['quantity'],
      );
}

class Stock {
  final int stockId;
  final String symbol;
  final String companyName;
  final String industry;
  final String sector;

  Stock(
      {required this.stockId,
      required this.symbol,
      required this.companyName,
      required this.industry,
      required this.sector});

  factory Stock.fromJson(Map<String, dynamic> json) => Stock(
        stockId: json['stockId'],
        symbol: json['symbol'],
        companyName: json['companyName'],
        industry: json['industry'],
        sector: json['sector'],
      );
}

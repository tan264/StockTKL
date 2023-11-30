class RealtimeQuote {
  final String symbol;
  final String companyName;
  final String industry;
  final String sector;
  final double price;
  final double changeValue;
  final double percentChange;
  final int volume;

  RealtimeQuote(
      {required this.symbol,
      required this.companyName,
      required this.industry,
      required this.sector,
      required this.price,
      required this.changeValue,
      required this.percentChange,
      required this.volume});

  factory RealtimeQuote.fromJson(Map<String, dynamic> json) => RealtimeQuote(
      symbol: json['symbol'],
      companyName: json['companyName'],
      industry: json['industry'],
      sector: json['sector'],
      price: json['price'],
      changeValue: json['changeValue'],
      percentChange: json['percentChange'],
      volume: json['volume']);

  static List<RealtimeQuote> listFromJson(list) =>
      List<RealtimeQuote>.from(list.map((x) => RealtimeQuote.fromJson(x)));
}

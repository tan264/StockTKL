// enum EOrderDirection {
//     buy("BUY"),
//     sell("SELL");

//     const EOrderDirection(this.name);
//     final String name;
// }

// enum EOrderStatus {
//     pending("PENDING"),
//     completed("COMPLETED"),
//     canceled("CANCELED");

//     const EOrderStatus(this.name);
//     final String name;
// }

// enum EOrderType {
//     market("MARKET"),
//     limit("LIMIT");

//     const EOrderType(this.name);
//     final String name;
// }

class Order {
  final String symbol;
  final String orderType;
  final String direction;
  final int quantity;
  final double price;
  final String status;
  final DateTime orderDate;
  final String companyName;

  Order(
      {required this.symbol,
      required this.orderType,
      required this.direction,
      required this.quantity,
      required this.price,
      required this.status,
      required this.orderDate,
      required this.companyName});

  // factory Order.fromJson(Map<String, dynamic> json) => Order(direction: json[''])
  factory Order.fromJson(Map<String, dynamic> json) => Order(
        symbol: json['symbol'],
        orderType: json['orderType'],
        direction: json['direction'],
        quantity: json['quantity'],
        price: json['price'],
        status: json['status'],
        orderDate: DateTime(
            json['orderDate'][0],
            json['orderDate'][1],
            json['orderDate'][2],
            json['orderDate'][3],
            json['orderDate'][4],
            json['orderDate'][5]),
        companyName: json['companyName'],
      );
}

import 'dart:ffi';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/main.dart';
import 'package:stock_tkl/models/realtime_quote.dart';
import 'package:stock_tkl/models/stock_data.dart';
import 'package:stock_tkl/pages/home/controller.dart';
import 'package:stock_tkl/pages/home/market/stock_market/detail.dart';

class StockMarketPage extends GetView<HomeController> {
  StockMarketPage({super.key});

  // final TextEditingController _controller = TextEditingController();
  final List<String> _indexes = ['VN30', 'HOSE', 'HNX', 'UPCOM'];
  final String _selectedIndex = '';

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: DropdownButton<String>(
                  icon: const Icon(Icons.arrow_downward),
                  borderRadius: const BorderRadius.all(Radius.circular(5)),
                  hint: const Text('Select Index'),
                  value: _selectedIndex.isEmpty ? null : _selectedIndex,
                  items: _indexes.map<DropdownMenuItem<String>>((String value) {
                    return DropdownMenuItem<String>(
                      value: value,
                      child: Text(value),
                    );
                  }).toList(),
                  onChanged: (String? selectedItem) {
                    // setState(() {
                    //   _selectedIndex = selectedItem ?? '';
                    // });
                  }),
            )
          ],
        ),
        Expanded(
          child: SingleChildScrollView(
            scrollDirection: Axis.horizontal,
            child: SingleChildScrollView(
              scrollDirection: Axis.vertical,
              child: Obx(
                () {
                  if (controller.realtimeQuotes.isEmpty) {
                    return const Center(child: CircularProgressIndicator());
                  } else {
                    // logger.i("refresh");
                    return DataTable(
                      showCheckboxColumn: false,
                      dividerThickness: 1.0, // Độ dày của đường phân cách
                      columns: const [
                        DataColumn(label: Text('Symbol')),
                        DataColumn(label: Text('Price')),
                        DataColumn(label: Text('+/-')),
                        DataColumn(label: Text('+/-(%)')),
                        DataColumn(label: Text('TotalVol')),
                      ],
                      rows: controller.realtimeQuotes
                          .map(
                            (e) => DataRow(
                              cells: [
                                DataCell(Text(e.symbol)),
                                customDataCell(e.price.toString(),
                                    isChange: controller
                                        .trackTheChange[e.symbol]!["price"]!),
                                customDataCell(e.changeValue.toString(),
                                    isChange: controller.trackTheChange[
                                        e.symbol]!["changeValue"]!),
                                customDataCell(e.percentChange.toString(),
                                    isChange: controller.trackTheChange[
                                        e.symbol]!["percentChange"]!),
                                customDataCell(e.volume.toString(),
                                    isChange: controller
                                        .trackTheChange[e.symbol]!["volume"]!),
                              ],
                              onSelectChanged: (value) {
                                _showBottomSheet(context, e);
                              },
                            ),
                          )
                          .toList(),
                    );
                  }
                },
              ),
            ),
          ),
        )
      ],
    );
  }

  DataCell customDataCell(String value, {int isChange = 0}) {
    TextStyle? style;
    if (controller.isFirstSecond.value && isChange == 1) {
      style = const TextStyle(color: Colors.green);
    } else if (controller.isFirstSecond.value && isChange == -1) {
      style = const TextStyle(color: Colors.red);
    }
    return DataCell(
      SizedBox.expand(
        child: Container(
          padding: const EdgeInsets.symmetric(vertical: 2),
          child: Align(
            alignment: AlignmentDirectional.centerStart,
            child: Text(
              value,
              style: style,
            ),
          ),
        ),
      ),
    );
  }

  _showBottomSheet(
    BuildContext context,
    RealtimeQuote realtimeQuote,
  ) {
    String symbol = realtimeQuote.symbol;
    double price = realtimeQuote.price;
    int quantity = 0;

    showModalBottomSheet(
        context: context,
        isScrollControlled: true,
        builder: (BuildContext context) {
          return Padding(
            padding: EdgeInsets.only(
                bottom: MediaQuery.of(context).viewInsets.bottom),
            child: Container(
              padding: const EdgeInsets.all(20.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                mainAxisSize: MainAxisSize.min,
                children: <Widget>[
                  Text(realtimeQuote.companyName,
                      style: const TextStyle(
                          fontSize: 24, fontWeight: FontWeight.bold)),
                  Text(
                    'Stock Symbol: ${realtimeQuote.symbol}',
                    style: const TextStyle(fontSize: 18),
                  ),
                  Text('Price: \$${realtimeQuote.price}',
                      style: const TextStyle(fontSize: 18)),
                  Text('Change: \$${realtimeQuote.changeValue}',
                      style: const TextStyle(fontSize: 18)),
                  Text('Change (%): ${realtimeQuote.percentChange}%',
                      style: const TextStyle(fontSize: 18)),
                  const SizedBox(height: 20.0),
                  Obx(
                    () => Row(
                      mainAxisSize: MainAxisSize.max,
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        DropdownButton<String>(
                          value: controller.selectedOrderType.value,
                          onChanged: (value) {
                            if (value != null) {
                              controller.updateOrderType(value);
                            }
                          },
                          items: <String>['LIMIT', 'MARKET']
                              .map<DropdownMenuItem<String>>(
                                (String value) => DropdownMenuItem<String>(
                                  value: value,
                                  child: Text(value),
                                ),
                              )
                              .toList(),
                        ),
                        const Spacer(),
                        Expanded(
                          child: TextField(
                            textDirection: TextDirection.rtl,
                            enabled:
                                controller.selectedOrderType.value == "LIMIT",
                            onChanged: (value) {
                              price = double.parse(value);
                            },
                            decoration: const InputDecoration(
                              suffixIcon: Icon(Icons.attach_money),
                              border: InputBorder.none,
                              hintTextDirection: TextDirection.rtl,
                              hintText: 'Enter price',
                              // labelText: 'price',
                              // border: OutlineInputBorder(),
                            ),
                            keyboardType: TextInputType.number,
                          ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(
                    height: 5,
                  ),
                  TextField(
                    onChanged: (value) {
                      quantity = int.parse(value);
                    },
                    decoration: const InputDecoration(
                      hintText: 'Enter quantity',
                      // labelText: 'Quantity',
                      border: InputBorder.none,
                    ),
                    keyboardType: TextInputType.number,
                  ),
                  const SizedBox(height: 20.0),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      ElevatedButton(
                        onPressed: () {
                          // Logic for BUY button
                          controller.sendOrderRequest(
                              symbol, "BUY", quantity, price);
                          // Navigator.pop(context);
                          // Perform action when BUY button is pressed
                        },
                        child: const Text('BUY'),
                      ),
                      ElevatedButton(
                        onPressed: () {
                          // Logic for SELL button
                          controller.sendOrderRequest(
                              symbol, "SELL", quantity, price);
                          // Navigator.pop(context);
                          // Perform action when SELL button is pressed
                        },
                        child: const Text('SELL'),
                      ),
                      Obx(() {
                        if (!controller.stocksFavorite
                            .contains(realtimeQuote.symbol)) {
                          return ElevatedButton.icon(
                              onPressed: () {
                                controller.addToWatchList(realtimeQuote.symbol);
                              },
                              icon: const Icon(Icons.favorite_border),
                              label: const Text("Add to favorite"));
                        } else {
                          return ElevatedButton.icon(
                              onPressed: () {
                                controller
                                    .deleteFromWatchList(realtimeQuote.symbol);
                              },
                              icon: const Icon(Icons.favorite),
                              label: const Text("Delete from watchlist"));
                        }
                      }),
                    ],
                  ),
                ],
              ),
            ),
          );
        });
  }
}

// List<StockData> generateStockData() {
//   return List.generate(7, (index) {
//     final date = DateTime.now().subtract(Duration(days: index));
//     final double price = (Random().nextDouble() * 100).clamp(50, 150);
//     return StockData(date, price);
//   }).reversed.toList();
// }

import 'dart:math';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/models/realtime_quote.dart';
import 'package:stock_tkl/models/stock_data.dart';
import 'package:stock_tkl/pages/home/controller.dart';
import 'package:stock_tkl/pages/home/market/industry/detail.dart';

class IndustryPage extends GetView<HomeController> {
  IndustryPage({super.key});

  final List<String> _industries = [
    "Dairy and dairy products",
    "Real estate development",
    "Food retailing",
    "Commercial banking",
    "Information technology",
    "Mechanical manufacturing",
    "Investment securities",
    "Securities investment fund",
    "Educational system"
  ];

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: Obx(
                () => DropdownButton<String>(
                    icon: const Icon(Icons.arrow_downward),
                    hint: const Text('Select Industry'),
                    borderRadius: const BorderRadius.all(Radius.circular(5)),
                    value: controller.selectedIndustry.value.isEmpty
                        ? null
                        : controller.selectedIndustry.value,
                    items: _industries
                        .map<DropdownMenuItem<String>>((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                    onChanged: (String? selectedItem) {
                      if (selectedItem != null) {
                        controller.selectedIndustry.value = selectedItem;
                      }
                    }),
              ),
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
                    return DataTable(
                      showCheckboxColumn: false,
                      dividerThickness: 1.0, // Độ dày của đường phân cách
                      columns: const [
                        DataColumn(label: Text('Symbol')),
                        DataColumn(label: Text('Price')),
                        DataColumn(label: Text('+/-')),
                        DataColumn(label: Text('+/- %')),
                        DataColumn(label: Text('TotalVol')),
                      ],
                      rows: controller.realtimeQuotes
                          .where((element) =>
                              element.industry ==
                              controller.selectedIndustry.value)
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
                  Text(realtimeQuote.companyName),
                  Text('Stock Symbol: ${realtimeQuote.symbol}'),
                  Text('Price: \$${realtimeQuote.price}'),
                  Text('Change: \$${realtimeQuote.changeValue}'),
                  Text('Change (%): ${realtimeQuote.percentChange}%'),
                  const SizedBox(height: 20.0),
                  Obx(
                    () => Row(
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
                        Expanded(
                          child: TextField(
                            enabled:
                                controller.selectedOrderType.value == "LIMIT",
                            onChanged: (value) {
                              price = double.parse(value);
                            },
                            decoration: const InputDecoration(
                              hintText: 'Enter price',
                              labelText: 'price',
                              border: OutlineInputBorder(),
                            ),
                            keyboardType: TextInputType.number,
                          ),
                        ),
                      ],
                    ),
                  ),
                  TextField(
                    onChanged: (value) {
                      quantity = int.parse(value);
                    },
                    decoration: const InputDecoration(
                      hintText: 'Enter quantity',
                      labelText: 'Quantity',
                      border: OutlineInputBorder(),
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
                          Navigator.pop(context);
                          // Perform action when BUY button is pressed
                        },
                        child: const Text('BUY'),
                      ),
                      ElevatedButton(
                        onPressed: () {
                          // Logic for SELL button
                          controller.sendOrderRequest(
                              symbol, "SELL", quantity, price);
                          Navigator.pop(context);
                          // Perform action when SELL button is pressed
                        },
                        child: const Text('SELL'),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          );
        });
  }
}

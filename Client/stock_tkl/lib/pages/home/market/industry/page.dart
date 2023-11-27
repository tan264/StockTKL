import 'dart:math';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/models/stock_data.dart';
import 'package:stock_tkl/pages/home/controller.dart';
import 'package:stock_tkl/pages/home/market/industry/detail.dart';

class IndustryPage extends GetView<HomeController> {
  IndustryPage({super.key});

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
              // child: Obx(
              //   () {
              //     if (controller.realtimeQuotes.isEmpty) {
              //       return const Center(child: CircularProgressIndicator());
              //     } else {
              //       logger.i("refresh");
              //       return DataTable(
              //         showCheckboxColumn: false,
              //         dividerThickness: 1.0, // Độ dày của đường phân cách
              //         columns: const [
              //           DataColumn(label: Text('Symbol')),
              //           DataColumn(label: Text('Price')),
              //           DataColumn(label: Text('+/-')),
              //           DataColumn(label: Text('+/-(%)')),
              //           DataColumn(label: Text('TotalVol')),
              //         ],
              //         rows: controller.realtimeQuotes
              //             .map((e) => DataRow(cells: [
              //                   DataCell(Text(e.symbol)),
              //                   customDataCell(e.price.toString(),
              //                       isChange: controller
              //                           .trackTheChange[e.symbol]!["price"]!),
              //                   customDataCell(e.changeValue.toString(),
              //                       isChange: controller.trackTheChange[
              //                           e.symbol]!["changeValue"]!),
              //                   customDataCell(e.percentChange.toString(),
              //                       isChange: controller.trackTheChange[
              //                           e.symbol]!["percentChange"]!),
              //                   customDataCell(e.volume.toString(),
              //                       isChange: controller
              //                           .trackTheChange[e.symbol]!["volume"]!),
              //                 ]))
              //             .toList(),
              //       );
              //     }
              //   },
              // ),
              child: DataTable(
                showCheckboxColumn: false,
                dividerThickness: 1.0,
                columns: const [
                  DataColumn(label: Text('Symbol')),
                  DataColumn(label: Text('Price')),
                  DataColumn(label: Text('+/-')),
                  DataColumn(label: Text('+/-(%)')),
                  DataColumn(label: Text('TotalVol')),
                ],
                rows: [
                  DataRow(
                      onSelectChanged: (bool? selected) {
                        if (selected == true) {
                          final stockData = generateStockData();
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => StockDetailPage(
                                stockSymbol: 'ABC',
                                stockPrice: 123.1,
                                stockData: stockData,
                              ),
                            ),
                          );
                        }
                      },
                      cells: const [
                        DataCell(Text('ABC')),
                        DataCell(Text('123.1')),
                        DataCell(Text('12.3')),
                        DataCell(Text('11.0')),
                        DataCell(Text('12345')),
                      ]),
                  DataRow(
                      onSelectChanged: (bool? selected) {
                        if (selected == true) {
                          final stockData = generateStockData();
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => StockDetailPage(
                                stockSymbol: 'ZZ',
                                stockPrice: 123.1,
                                stockData: stockData,
                              ),
                            ),
                          );
                        }
                      },
                      cells: const [
                        DataCell(Text('ZZ')),
                        DataCell(Text('123.1')),
                        DataCell(Text('12.3')),
                        DataCell(Text('11.0')),
                        DataCell(Text('12345')),
                      ]),
                  DataRow(
                      onSelectChanged: (bool? selected) {
                        if (selected == true) {
                          final stockData = generateStockData();

                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => StockDetailPage(
                                stockSymbol: 'XYZ',
                                stockPrice: 123.1,
                                stockData: stockData,
                              ),
                            ),
                          );
                        }
                      },
                      cells: const [
                        DataCell(Text('XYZ')),
                        DataCell(Text('123.1')),
                        DataCell(Text('12.3')),
                        DataCell(Text('11.0')),
                        DataCell(Text('12345')),
                      ]),
                ],
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
}

List<StockData> generateStockData() {
  return List.generate(7, (index) {
    final date = DateTime.now().subtract(Duration(days: index));
    final double price = (Random().nextDouble() * 100).clamp(50, 150);
    return StockData(date, price);
  }).reversed.toList();
}

import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/main.dart';
import 'package:stock_tkl/pages/home/controller.dart';

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
                    logger.i("refresh");
                    return DataTable(
                      dividerThickness: 1.0, // Độ dày của đường phân cách
                      columns: const [
                        DataColumn(label: Text('Symbol')),
                        DataColumn(label: Text('Price')),
                        DataColumn(label: Text('+/-')),
                        DataColumn(label: Text('+/- %')),
                        DataColumn(label: Text('TotalVol')),
                      ],
                      rows: controller.realtimeQuotes
                          .map((e) => DataRow(cells: [
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
                              ]))
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
    Color? color;
    if (controller.isFirstSecond.value && isChange == 1) {
      color = Colors.green;
    } else if (controller.isFirstSecond.value && isChange == -1) {
      color = Colors.red;
    }
    return DataCell(
      SizedBox.expand(
        child: Container(
          padding: const EdgeInsets.symmetric(vertical: 2),
          color: color,
          child: Align(
            alignment: AlignmentDirectional.centerStart,
            child: Text(value),
          ),
        ),
      ),
    );
  }
}

import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/models/stock.dart';
import 'package:stock_tkl/pages/my_stocks/controller.dart';

class MyStocksPage extends GetView<MyStocksController> {
  const MyStocksPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('My Stocks'),
      ),
      body: Obx(() {
        if (controller.isLoading.isTrue) {
          return const Center(child: CircularProgressIndicator());
        }

        if (controller.ownedStocks.isEmpty) {
          return const Center(child: Text('You currently have no stocks.'));
        }

        return ListView.builder(
          itemCount: controller.ownedStocks.length,
          itemBuilder: (context, index) {
            Stock stock = controller.ownedStocks[index];
            return ListTile(
              title: Text(stock.companyName),
              subtitle: Text('Symbol: ${stock.symbol}\nSector: ${stock.sector}\nIndustry: ${stock.industry}\nQuantity: ${stock.quantity}'),
            );
          },
        );
      }),
    );
  }
}

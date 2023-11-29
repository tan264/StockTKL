import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/models/order.dart';
import 'package:stock_tkl/pages/order_history/controller.dart';

class OrderHistoryPage extends GetView<OrderHistoryController> {
  const OrderHistoryPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Order History'),
      ),
      body: Obx(() {
        if (controller.isLoading.value) {
          return const Center(child: CircularProgressIndicator());
        }
        return ListView.builder(
          itemCount: controller.orders.length,
          itemBuilder: (context, index) {
            Order order = controller.orders[index];
            return ListTile(
              title: Text(order.symbol),
              subtitle: Text('Quantity: ${order.quantity}, Price: ${order.price}, Status: ${order.status}'),
              trailing: Text('${order.orderDate}'),
            );
          },
        );
      }),
    );
  }
}

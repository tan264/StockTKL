import 'package:get/get.dart';
import 'package:stock_tkl/pages/order_history/controller.dart';

class OrderHistoryBinding extends Bindings {
  @override
  void dependencies() {
    Get.put<OrderHistoryController>(OrderHistoryController());
  }
}

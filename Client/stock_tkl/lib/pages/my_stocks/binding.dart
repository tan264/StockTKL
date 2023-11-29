import 'package:get/get.dart';
import 'package:stock_tkl/pages/my_stocks/controller.dart';

class MyStocksBinding extends Bindings {
  @override
  void dependencies() {
    Get.put<MyStocksController>(MyStocksController());
  }
}

import 'package:get/get.dart';
import 'package:stock_tkl/pages/auth/controller.dart';

class AuthBinding extends Bindings {
  @override
  void dependencies() {
    Get.put<AuthController>(AuthController());
  }
}

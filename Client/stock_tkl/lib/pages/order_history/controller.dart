import 'package:get/get.dart';
import 'package:stock_tkl/models/order.dart';
import 'package:stock_tkl/services/api_service.dart';

class OrderHistoryController extends GetxController {
  final RxList<Order> orders = <Order>[].obs;
  final ApiService apiService = Get.find<ApiService>();
  final isLoading = false.obs;
  final String token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZHRydW5nIiwiaWF0IjoxNzAxMjA1MTc4LCJleHAiOjE3MDEyOTE1Nzh9.lF88UJfdWKKTpP0RzgjWhfyhQEMLnn0ffOYj84vMFI8';

  @override
  void onInit() {
    super.onInit();
    fetchOrderHistory();
  }

  void fetchOrderHistory() async {
    isLoading(true);
    try {
      var fetchedOrders = await apiService.getOrderHistory(token);
      orders.assignAll(fetchedOrders);
    } catch (e) {
      Get.snackbar("Error", "Unable to fetch order history");
    } finally {
      isLoading(false);
    }
  }
}

import 'package:get/get.dart';
import 'package:stock_tkl/main.dart';
import 'package:stock_tkl/models/stock.dart';
import 'package:stock_tkl/services/api_service.dart';
import 'package:stock_tkl/services/auth_service.dart';

class MyStocksController extends GetxController {
  final RxList<Stock> ownedStocks = <Stock>[].obs;
  final RxBool isLoading = false.obs;
  final ApiService apiService = Get.find<ApiService>();
  final AuthService authService = Get.find<AuthService>();
  // final token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cnVuZzIiLCJpYXQiOjE3MDEyMDg4OTEsImV4cCI6MTcwMTI5NTI5MX0.DHJWtVhd9g1w5SF5AHgiAwH656psEbL5mZYxM5bhLdU";

  @override
  void onInit() {
    super.onInit();
    fetchOwnedStocks();
  }

  void fetchOwnedStocks() async {
    isLoading(true);
    try {
      // final token = authService.token;
      //final token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cnVuZzIiLCJpYXQiOjE3MDEyMDg4OTEsImV4cCI6MTcwMTI5NTI5MX0.DHJWtVhd9g1w5SF5AHgiAwH656psEbL5mZYxM5bhLdU";
      // if (token == null) {
      //   throw Exception('Token is null');
      // }
      var result = await apiService.getOwnedStocks(authService.token!);
      ownedStocks.assignAll(result);
    } catch (e) {
      logger.e("Error fetching owned stocks: $e");
      Get.snackbar('Error', 'Unable to fetch owned stocks');
    } finally {
      isLoading(false);
    }
  }
}

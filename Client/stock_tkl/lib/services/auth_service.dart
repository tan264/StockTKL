import 'package:get/get.dart';
import 'package:stock_tkl/services/api_service.dart';

class AuthService extends GetxService {
  String? token;
  final isLogged = false.obs;
  IApiService apiService = Get.find<ApiService>();

  void login(
    String username,
    String password, {
    required void Function() onDone,
    required Function(String) onError,
    required Function() onSuccess,
  }) async {
    token = await apiService.login(username, password, onError);
    if (token != null) {
      isLogged.value = true;
      onSuccess();
    }
    onDone();
  }
}

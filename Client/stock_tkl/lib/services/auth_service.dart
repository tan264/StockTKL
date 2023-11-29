import 'package:get/get.dart';
import 'package:stock_tkl/services/api_service.dart';

class AuthService extends GetxService {
  String? token;
  final isLogged = false.obs;
  IApiService apiService = Get.find<ApiService>();

  // String? get token => _token;

  // @override
  // void onInit() {
  //   super.onInit();
  //   loadToken();
  // }

  // Future<void> saveToken(String token) async {
  //   final prefs = await SharedPreferences.getInstance();
  //   await prefs.setString('token', token);
  //   _token = token;
  //   isLogged.value = true;
  // }

  // Future<void> loadToken() async {
  //   final prefs = await SharedPreferences.getInstance();
  //   _token = prefs.getString('token');
  //   isLogged.value = _token != null;
  // }

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

  void register(
    String username,
    String password,
    String fullName,
    String email, {
    required void Function() onDone,
    required Function(String) onError,
    required Function() onSuccess,
  }) async {
    token =
        await apiService.register(username, password, fullName, email, onError);
    if (token != null) {
      isLogged.value = true;
      onSuccess();
    }
    onDone();
  }
}

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/routes/app_routes.dart';
import 'package:stock_tkl/services/auth_service.dart';

class AuthController extends GetxController {
  final _authService = Get.find<AuthService>();
  final isLogging = false.obs;

  void login(String username, String password) {
    isLogging.value = true;
    _authService.login(
      username,
      password,
      onDone: () {
        isLogging.value = false;
      },
      onError: (p0) {
        Get.snackbar("Error", p0, backgroundColor: Colors.white);
      },
      onSuccess: () {
        Get.toNamed(AppRoutes.home);
      },
    );
  }
}

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/routes/app_routes.dart';
import 'package:stock_tkl/services/auth_service.dart';

class AuthController extends GetxController {
  final _authService = Get.find<AuthService>();
  final isLoading = false.obs;

  void login(String username, String password) {
    isLoading.value = true;
    _authService.login(
      username,
      password,
      onDone: () {
        isLoading.value = false;
      },
      onError: (p0) {
        Get.snackbar("Error", p0, backgroundColor: Colors.white);
      },
      onSuccess: () {
        Get.toNamed(AppRoutes.home);
      },
    );
  }

  void register(String username, String password, String fullName,
      String confirmPassword, String email) {
    if (confirmPassword != password) {
      Get.snackbar("Error", "Confirmed password not match");
    }
    isLoading.value = true;
    _authService.register(
      username,
      password,
      fullName,
      email,
      onDone: () {
        isLoading.value = false;
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

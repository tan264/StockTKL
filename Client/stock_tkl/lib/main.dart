import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_navigation/src/root/get_material_app.dart';
import 'package:logger/logger.dart';
import 'package:stock_tkl/pages/home/binding.dart';
import 'package:stock_tkl/pages/home/page.dart';
import 'package:stock_tkl/routes/app_pages.dart';
import 'package:stock_tkl/services/api_service.dart';
import 'package:stock_tkl/services/auth_service.dart';

final logger = Logger();
void main() {
  initService();

  runApp(const MyApp());
}

initService() {
  Get.put<ApiService>(ApiService());
  Get.put<AuthService>(AuthService());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'StockTKL',
      theme: ThemeData(primaryColor: Colors.blue),
      initialBinding: HomeBinding(),
      home: HomePage(),
      getPages: AppPages.pages,
    );
  }
}

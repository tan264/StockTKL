import 'package:get/get.dart';
import 'package:stock_tkl/pages/home/binding.dart';
import 'package:stock_tkl/pages/home/page.dart';
import 'package:stock_tkl/routes/app_routes.dart';

class AppPages {
  static final List<GetPage> pages = [
    GetPage(
      name: AppRoutes.home,
      page: () => HomePage(),
      binding: HomeBinding(),
    ),
  ];
}

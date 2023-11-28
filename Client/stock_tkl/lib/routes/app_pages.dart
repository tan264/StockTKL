import 'package:get/get.dart';
import 'package:stock_tkl/pages/auth/binding.dart';
import 'package:stock_tkl/pages/auth/signin.dart';
import 'package:stock_tkl/pages/auth/signup.dart';
import 'package:stock_tkl/pages/home/binding.dart';
import 'package:stock_tkl/pages/home/page.dart';
import 'package:stock_tkl/pages/my_stocks/binding.dart';
import 'package:stock_tkl/pages/my_stocks/page.dart';
import 'package:stock_tkl/pages/order_history/binding.dart';
import 'package:stock_tkl/pages/order_history/page.dart';
import 'package:stock_tkl/routes/app_routes.dart';

class AppPages {
  static final List<GetPage> pages = [
    GetPage(
      name: AppRoutes.home,
      page: () => HomePage(),
      binding: HomeBinding(),
    ),
    GetPage(
      name: AppRoutes.signin,
      page: () => const SignInPage(),
      binding: AuthBinding(),
    ),
    GetPage(
      name: AppRoutes.signup,
      page: () => const SignUpPage(),
      binding: AuthBinding(),
    ),
    GetPage(
      name: AppRoutes.signin,
      page: () => const SignInPage(),
      binding: AuthBinding(),
    ),
    GetPage(
      name: AppRoutes.orderHistory,
      page: () => const OrderHistoryPage(),
      binding: OrderHistoryBinding(),
    ),
    GetPage(
      name: AppRoutes.myStocks,
      page: () => const MyStocksPage(),
      binding: MyStocksBinding(),
    ),
  ];
}

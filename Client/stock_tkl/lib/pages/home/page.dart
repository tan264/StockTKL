import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/pages/home/controller.dart';
import 'package:stock_tkl/pages/home/market/page.dart';
import 'package:stock_tkl/pages/home/watchlist/page.dart';
import 'package:stock_tkl/pages/user/profile.dart';
import 'package:stock_tkl/routes/app_routes.dart';

class HomePage extends GetView<HomeController> {
  HomePage({super.key});

  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  final List<Widget> _screens = [
    MarketPage(),
    const WatchListPage(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        key: _scaffoldKey,
        drawer: Drawer(
          child: ListView(
            children: <Widget>[
              DrawerHeader(
                decoration:
                    const BoxDecoration(color: Color.fromRGBO(27, 31, 35, 1)),
                child: Padding(
                  padding: const EdgeInsets.all(1.0),
                  child: Row(
                    children: [
                      SizedBox(
                        width: 100,
                        height: 100,
                        child: Image.asset('assets/images/logo.png',
                            fit: BoxFit.contain),
                      ),
                      const SizedBox(width: 16),
                      const Expanded(
                        child: Text(
                          'Welcome to StockTKL',
                          style: TextStyle(
                            color: Color.fromRGBO(65, 148, 138, 1),
                            fontSize: 24,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              if (!controller.isLogged())
                Row(
                  children: [
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.symmetric(
                            vertical: 10.0, horizontal: 10.0),
                        child: ElevatedButton(
                          onPressed: () {
                            Get.toNamed(AppRoutes.signin);
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.blue,
                            foregroundColor: Colors.white,
                          ),
                          child: const Text('Sign In'),
                        ),
                      ),
                    ),
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.symmetric(
                            vertical: 10.0, horizontal: 10.0),
                        child: ElevatedButton(
                          onPressed: () {
                            Get.toNamed(AppRoutes.signup);
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: Colors.white,
                            foregroundColor: Colors.blue,
                          ),
                          child: const Text('Sign Up'),
                        ),
                      ),
                    ),
                  ],
                ),
              ListTile(
                leading: const Icon(Icons.account_circle),
                title: const Text('Account'),
                onTap: () {
                  Navigator.of(context).push(MaterialPageRoute(
                      builder: (context) => const AccountPage()));
                },
              ),
              ListTile(
                leading: const Icon(Icons.show_chart),
                title: const Text('My Stocks'),
                onTap: () {
                  // Navigate to the My Stocks page
                  Get.toNamed(AppRoutes.myStocks);
                },
              ),
              ListTile(
                leading: const Icon(Icons.history),
                title: const Text('Order History'),
                onTap: () {
                  // Navigate to the Order History page
                  Get.toNamed(AppRoutes.orderHistory);
                },
              ),
            ],
          ),
        ),
        body: Column(
          children: [
            SafeArea(
              child: Padding(
                padding: const EdgeInsets.all(10),
                child: Row(
                  children: [
                    IconButton(
                        onPressed: () {
                          //Open drawer
                          _scaffoldKey.currentState?.openDrawer();
                        },
                        icon: const Icon(Icons.menu)),
                    Expanded(
                        child: TextField(
                      decoration: InputDecoration(
                          hintText: 'Search something',
                          prefixIcon: const Icon(Icons.search),
                          border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(8)),
                          contentPadding:
                              const EdgeInsets.symmetric(vertical: 0.0)),
                    )),
                    IconButton(
                      icon: const Icon(Icons.notifications),
                      onPressed: () {
                        // Handle bell icon button press
                      },
                    ),
                  ],
                ),
              ),
            ),
            Obx(() => _screens[controller.indexBottomNavigation.value]),
          ],
        ),
        bottomNavigationBar: Obx(
          () => BottomNavigationBar(
            backgroundColor: Colors.grey.shade300,
            selectedItemColor: Colors.green,
            unselectedItemColor: Colors.grey.shade600,
            currentIndex: controller.indexBottomNavigation.value,
            onTap: (index) {
              controller.indexBottomNavigation.value = index;
            },
            items: const [
              BottomNavigationBarItem(
                icon: Icon(Icons.pie_chart),
                label: 'Market',
              ),
              BottomNavigationBarItem(
                icon: Icon(Icons.list_sharp),
                label: 'Watchlist',
              ),
            ],
          ),
        ));
  }
}

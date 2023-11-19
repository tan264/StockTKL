import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/pages/home/controller.dart';
import 'package:stock_tkl/pages/home/market/page.dart';
import 'package:stock_tkl/pages/home/watchlist/page.dart';

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
      // drawer: LeftDrawer(),
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
      bottomNavigationBar: BottomNavigationBar(
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
    );
  }
}

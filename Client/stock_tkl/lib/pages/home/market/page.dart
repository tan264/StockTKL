import 'package:contained_tab_bar_view/contained_tab_bar_view.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/pages/home/controller.dart';
import 'package:stock_tkl/pages/home/market/industry/page.dart';
import 'package:stock_tkl/pages/home/market/stock_market/page.dart';

class MarketPage extends GetView<HomeController> {
  MarketPage({super.key});

  final List<String> categories = [
    'Stock Market',
    'Industry',
  ];

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: ContainedTabBarView(
        tabs: const [
          Text(
            "Stock Market",
            style: TextStyle(color: Colors.black),
          ),
          Text(
            "Industry",
            style: TextStyle(color: Colors.black),
          ),
        ],
        views: [
          StockMarketPage(),
          IndustryPage(),
        ],
      ),
    );
  }
}

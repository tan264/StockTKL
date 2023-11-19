import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:infinite_scroll_tab_view/infinite_scroll_tab_view.dart';
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
      child: InfiniteScrollTabView(
        contentLength: categories.length,
        onTabTap: (index) {
          debugPrint('You tapped: $index');
        },
        tabBuilder: (index, isSelected) => Text(
          categories[index],
          style: TextStyle(
            color: isSelected ? Colors.pink : Colors.black54,
            fontWeight: FontWeight.bold,
            fontSize: 18,
          ),
        ),
        pageBuilder: (context, index, _) {
          if (index == 0) {
            return StockMarketPage();
          } else {
            return const IndustryPage();
          }
        },
      ),
    );
  }
}

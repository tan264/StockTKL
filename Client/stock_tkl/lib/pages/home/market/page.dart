// import 'package:flutter/material.dart';
// import 'package:get/get.dart';
// import 'package:infinite_scroll_tab_view/infinite_scroll_tab_view.dart';
// import 'package:stock_tkl/pages/home/controller.dart';
// import 'package:stock_tkl/pages/home/market/industry/page.dart';
// import 'package:stock_tkl/pages/home/market/stock_market/page.dart';

// class MarketPage extends GetView<HomeController> {
//   MarketPage({super.key});

//   final List<String> categories = [
//     'Stock Market',
//     'Industry',
//   ];

//   @override
//   Widget build(BuildContext context) {
//     return Expanded(
//       child: DefaultTabController(
//         length: categories.length,
//         // onTabTap: (index) {
//         //   debugPrint('You tapped: $index');
//         // },
//         // tabBuilder: (index, isSelected) => Text(
//         //   categories[index],
//         //   style: TextStyle(
//         //     color: isSelected ? Colors.pink : Colors.black54,
//         //     fontWeight: FontWeight.bold,
//         //     fontSize: 18,
//         //   ),
//         // ),
//         // pageBuilder: (context, index, _) {
//         //   if (index == 0) {
//         //     return StockMarketPage();
//         //   } else {
//         //     return const IndustryPage();
//         //   }
//         // },
//         child: Scaffold(
//           appBar: AppBar(
//             backgroundColor: Colors.white,
//             bottom: PreferredSize(
//               preferredSize: const Size.fromHeight(48.0),
//               child: Container(
//                 color: Colors.white,
//                 child: const TabBar(
//                   labelColor: Colors.pink,
//                   unselectedLabelColor: Colors.black54,
//                   labelStyle: TextStyle(
//                     fontWeight: FontWeight.bold,
//                     fontSize: 18,
//                   ),
//                   tabs: [
//                     Tab(text: 'Stock Market'),
//                     Tab(text: 'Industry'),
//                   ],
//                   indicatorSize: TabBarIndicatorSize.label,
//                   indicatorColor: Colors.pink,
//                 ),
//               ),
//             ),
//           ),
//           body: TabBarView(
//             children: [
//               StockMarketPage(),
//               IndustryPage(),
//             ],
//           ),
//         //   body: Column(
//         //   children: [
//         //     const Material(
//         //       color: Colors.white,
//         //       child: TabBar(
//         //         labelColor: Colors.pink,
//         //         unselectedLabelColor: Colors.black54,
//         //         labelStyle: TextStyle(
//         //           fontWeight: FontWeight.bold,
//         //           fontSize: 18,
//         //         ),
//         //         tabs: [
//         //           Tab(text: 'Stock Market'),
//         //           Tab(text: 'Industry'),
//         //         ],
//         //         indicatorSize: TabBarIndicatorSize.label,
//         //         indicatorColor: Colors.pink,
//         //       ),
//         //     ),
//         //     Expanded(
//         //       child: TabBarView(
//         //         children: [
//         //           StockMarketPage(),
//         //           IndustryPage(),
//         //         ],
//         //       ),
//         //     ),
//         //   ],
//         // ),
//         ),
//       ),
//     );
//   }
// }

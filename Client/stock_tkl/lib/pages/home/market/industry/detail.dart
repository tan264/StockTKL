import 'dart:math';

import 'package:flutter/material.dart';
import 'package:fl_chart/fl_chart.dart';
import 'package:stock_tkl/models/stock_data.dart';

class StockDetailPage extends StatefulWidget {
  final String stockSymbol;
  final double stockPrice;
  final List<StockData> stockData;

  const StockDetailPage({
    Key? key,
    required this.stockSymbol,
    required this.stockPrice,
    required this.stockData,
  }) : super(key: key);

  @override
  _StockDetailPageState createState() => _StockDetailPageState();
}

class _StockDetailPageState extends State<StockDetailPage> {
  int stockAmount = 1;

  void _showSnackbar(String message) {
    final snackBar = SnackBar(content: Text(message));
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.stockSymbol),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            SizedBox(
              height: 200,
              child: StockChart(stockData: widget.stockData),
            ),
            // Price section
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text('Price: \$${widget.stockPrice.toStringAsFixed(2)}',
                  style: const TextStyle(fontSize: 24)),
            ),
            // Amount section
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  IconButton(
                    icon: const Icon(Icons.remove),
                    onPressed: () {
                      if (stockAmount > 1) {
                        setState(() {
                          stockAmount--;
                          _showSnackbar('Removed one item');
                        });
                      }
                    },
                  ),
                  Text('Amount: $stockAmount'),
                  IconButton(
                    icon: const Icon(Icons.add),
                    onPressed: () {
                      setState(() {
                        stockAmount++;
                        _showSnackbar('Added one item');
                      });
                    },
                  ),
                ],
              ),
            ),
            // Total value section
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text(
                'Total Value: \$${(widget.stockPrice * stockAmount).toStringAsFixed(2)}',
                style: const TextStyle(fontSize: 20),
              ),
            ),
          ],
        ),
      ),
      bottomNavigationBar: BottomAppBar(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton(
              onPressed: () {
                final String message =
                    'You buy $stockAmount with \$${(widget.stockPrice * stockAmount).toStringAsFixed(2)}';
                _showSnackbar(message);
              },
              style: ElevatedButton.styleFrom(backgroundColor: Colors.green),
              child: const Text('Buy'),
            ),
            ElevatedButton(
              onPressed: () {
                final String message =
                    'You sell $stockAmount with \$${(widget.stockPrice * stockAmount).toStringAsFixed(2)}';
                _showSnackbar(message);
              },
              style: ElevatedButton.styleFrom(backgroundColor: Colors.red),
              child: const Text('Sell'),
            ),
          ],
        ),
      ),
    );
  }
}

class StockChart extends StatelessWidget {
  final List<StockData> stockData;

  const StockChart({Key? key, required this.stockData}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    List<FlSpot> spots = stockData
        .map((data) =>
            FlSpot(data.date.millisecondsSinceEpoch.toDouble(), data.price))
        .toList();

    return Padding(
      padding: const EdgeInsets.only(top: 16.0), // Adjust the padding as needed
      child: LineChart(
        LineChartData(
          gridData: const FlGridData(show: false),
          titlesData: const FlTitlesData(show: false),
          borderData: FlBorderData(show: false),
          minX: stockData.first.date.millisecondsSinceEpoch.toDouble(),
          maxX: stockData.last.date.millisecondsSinceEpoch.toDouble(),
          minY: stockData.map((data) => data.price).reduce(min),
          maxY: stockData.map((data) => data.price).reduce(max),
          lineBarsData: [
            LineChartBarData(
              spots: spots,
              isCurved: true,
              color: Colors.red,
              barWidth: 2,
              isStrokeCapRound: true,
              dotData: const FlDotData(show: false),
              belowBarData: BarAreaData(show: false),
            ),
          ],
        ),
      ),
    );
  }
}

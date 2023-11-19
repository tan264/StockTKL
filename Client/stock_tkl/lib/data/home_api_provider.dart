import 'dart:convert';

import 'package:get/get.dart';
import 'package:stock_tkl/main.dart';
import 'package:stock_tkl/models/realtime_quote.dart';

abstract class IHomeApiProvider {
  Future<List<RealtimeQuote>> getRealtimeQuotes();
}

class HomeApiProvider extends GetConnect implements IHomeApiProvider {
  @override
  void onInit() {
    httpClient.baseUrl = "http://10.0.2.2:8080"; // for emulator
    super.onInit();
  }

  @override
  Future<List<RealtimeQuote>> getRealtimeQuotes() async {
    var result = List<RealtimeQuote>.empty();
    final response = await httpClient.get("/api/quote/get-realtime-quotes");

    if (response.isOk) {
      try {
        Map<String, dynamic> jsonBody = jsonDecode(response.bodyString!);
        result = RealtimeQuote.listFromJson(jsonBody['data']);
      } catch (e) {
        logger.d(e);
      }
    }

    return result;
  }
}

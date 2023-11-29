import 'dart:convert';

import 'package:get/get.dart';
import 'package:stock_tkl/main.dart';
import 'package:stock_tkl/models/order.dart';
import 'package:stock_tkl/models/realtime_quote.dart';
import 'package:stock_tkl/models/stock.dart';

abstract class IApiService {
  Future<String?> login(
      String username, String password, void Function(String) onError);

  Future<String?> register(String username, String password, String fullName,
      String email, void Function(String) onError);

  Future<Set<String>> watchList(String token);

  Future<bool> sendOrder(
      String token,
      String stockSymbol,
      String orderType,
      String direction,
      int quantity,
      double price,
      void Function(String) onError);

  Future<List<RealtimeQuote>> getRealtimeQuotes();

  Future addToWatchList(String token, String symbol,
      void Function(String) onError, void Function() onSuccess);

  Future deleteFromWatchList(String token, String symbol,
      void Function(String) onError, void Function() onSuccess);
  Future<List<Stock>> getOwnedStocks(String token);

  Future<List<Order>> getOrderHistory(String token);
}

class ApiService extends GetConnect implements IApiService {
  @override
  void onInit() {
    httpClient.baseUrl = "http://10.0.2.2:8080"; // for emulator
    // httpClient.baseUrl = "http://34.126.116.150";
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

  @override
  Future<String?> login(
      String username, String password, void Function(String) onError) async {
    final data = {
      "username": username,
      "password": password,
    };
    final response = await httpClient.post(
      "/api/auth/signin",
      body: data,
    );
    if (response.isOk) {
      try {
        Map<String, dynamic> jsonBody = jsonDecode(response.bodyString!);
        logger.d(jsonBody);
        return jsonBody['token'];
      } catch (e) {
        logger.d(e);
      }
    } else {
      logger.d(response.bodyString);
      onError("${response.statusCode} - ${response.statusText}");
    }
    return null;
  }

  @override
  Future<String?> register(String username, String password, String fullName,
      String email, void Function(String) onError) async {
    final data = {
      "username": username,
      "password": password,
      "email": email,
      "fullName": fullName,
    };
    final response = await httpClient.post(
      "/api/auth/signup",
      body: data,
    );
    if (response.isOk) {
      try {
        Map<String, dynamic> jsonBody = jsonDecode(response.bodyString!);
        logger.d(jsonBody);
        return jsonBody['token'];
      } catch (e) {
        logger.d(e);
      }
    } else {
      logger.d(response.bodyString);
      onError("${response.statusCode} - ${response.statusText}");
    }
    return null;
  }

  @override
  Future<Set<String>> watchList(String token) async {
    final headers = {'Authorization': 'Bearer $token'};
    final response =
        await httpClient.get("/api/user/watchlist", headers: headers);
    if (response.isOk) {
      try {
        List<dynamic> jsonBody = jsonDecode(response.bodyString!);
        Set<String> symbols = <String>{};
        for (var i in jsonBody) {
          symbols.add(i['symbol']);
        }
        logger.d(jsonBody);
        return symbols;
      } catch (e) {
        logger.d(e);
      }
    } else {
      logger.d(response.bodyString);
      logger.d("${response.statusCode} - ${response.statusText}");
    }
    return <String>{};
  }

  @override
  Future<bool> sendOrder(
      String token,
      String stockSymbol,
      String orderType,
      String direction,
      int quantity,
      double price,
      void Function(String) onError) async {
    final data = {
      "stockSymbol": stockSymbol,
      "orderType": orderType,
      "direction": direction,
      "quantity": quantity,
      "price": price,
    };
    final headers = {'Authorization': 'Bearer $token'};
    final response = await httpClient.post("/api/order/send-order",
        body: data, headers: headers);
    if (response.isOk) {
      try {
        Map<String, dynamic> jsonBody = jsonDecode(response.bodyString!);
        logger.d(jsonBody);
        return true;
      } catch (e) {
        logger.d(e);
      }
    } else {
      logger.d(response.bodyString);
      onError("${response.statusCode} - ${response.statusText}");
    }
    return false;
  }

  @override
  Future addToWatchList(String token, String symbol,
      void Function(String p1) onError, void Function() onSuccess) async {
    final headers = {'Authorization': 'Bearer $token'};
    final params = {
      'symbol': symbol,
    };
    final response = await httpClient.post("/api/user/watchlist/add",
        headers: headers, query: params);
    if (response.isOk) {
      try {
        Map<String, dynamic> jsonBody = jsonDecode(response.bodyString!);
        Get.snackbar("Success", jsonBody['message']);
        onSuccess();
        logger.d(jsonBody);
      } catch (e) {
        logger.d(e);
      }
    } else {
      logger.d(response.bodyString);
      onError("${response.statusCode} - ${response.statusText}");
    }
  }

  @override
  Future deleteFromWatchList(String token, String symbol,
      void Function(String p1) onError, void Function() onSuccess) async {
    final headers = {'Authorization': 'Bearer $token'};
    final params = {
      'symbol': symbol,
    };
    final response = await httpClient.delete("/api/user/watchlist/remove",
        headers: headers, query: params);
    if (response.isOk) {
      try {
        Map<String, dynamic> jsonBody = jsonDecode(response.bodyString!);
        logger.d(jsonBody);
        onSuccess();
      } catch (e) {
        logger.d(e);
      }
    } else {
      logger.d(response.bodyString);
      logger.d("${response.statusCode} - ${response.statusText}");
    }
  }

  @override
  Future<List<Stock>> getOwnedStocks(String token) async {
    final headers = {'Authorization': 'Bearer $token'};
    final response =
        await httpClient.get("/api/user/owned-stocks", headers: headers);

    if (response.isOk) {
      try {
        List<dynamic> jsonList = jsonDecode(response.bodyString!);
        return jsonList.map((data) => Stock.fromJson(data)).toList();
      } catch (e) {
        logger.d(e);
        throw Exception('Failed to parse owned stocks');
      }
    } else {
      logger.d(response.bodyString);
      throw Exception(
          'Failed to fetch owned stocks: ${response.statusCode} - ${response.statusText}');
    }
  }

  @override
  Future<List<Order>> getOrderHistory(String token) async {
    final headers = {'Authorization': 'Bearer $token'};
    final response = await get("/api/user/orders", headers: headers);

    if (response.isOk) {
      try {
        List<dynamic> jsonData = response.body;
        return jsonData.map((data) => Order.fromJson(data)).toList();
      } catch (e) {
        logger.e("Error parsing orders: $e");
        throw Exception('Failed to parse order history');
      }
    } else {
      logger.e(
          "Error fetching order history: ${response.statusCode} - ${response.statusText}");
      throw Exception(
          'Failed to fetch order history: ${response.statusCode} - ${response.statusText}');
    }
  }
}

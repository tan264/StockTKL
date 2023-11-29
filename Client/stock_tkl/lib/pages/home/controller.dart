import 'dart:async';
import 'dart:convert';

import 'package:get/get.dart';
import 'package:stock_tkl/main.dart';
import 'package:stock_tkl/models/order_request.dart';
import 'package:stock_tkl/models/realtime_quote.dart';
import 'package:stock_tkl/routes/app_routes.dart';
import 'package:stock_tkl/services/api_service.dart';
import 'package:stock_tkl/services/auth_service.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';

class HomeController extends GetxController {
  final wsUrl = 'http://10.0.2.2:8080/ws'; // for emulator
  // final wsUrl = 'http://34.126.116.150/ws';
  late StompClient client;

  final IApiService _apiProvider = Get.find<ApiService>();
  final AuthService _authService = Get.find<AuthService>();

  final List<RealtimeQuote> realtimeQuotes = <RealtimeQuote>[].obs;
  final Map<String, Map<String, int>> trackTheChange = {};
  final Set<String> stocksFavorite = <String>{}.obs;

  final indexBottomNavigation = 0.obs;
  final isFirstSecond = false.obs;
  final selectedOrderType = "MARKET".obs;
  final selectedIndustry = "".obs;

  @override
  void onInit() async {
    super.onInit();
    realtimeQuotes.assignAll(await _apiProvider.getRealtimeQuotes());
    for (var e in realtimeQuotes) {
      trackTheChange[e.symbol] = {
        "price": 0,
        "changeValue": 0,
        "percentChange": 0,
        "volume": 0
      };
    }
    if (_authService.isLogged.value) {
      logger.d("logged");
      final token = _authService.token;
      if (token != null) {
        stocksFavorite.assignAll(await _apiProvider.watchList(token));
        logger.d(stocksFavorite);
      }
    }
    // logger.i(trackTheChange.toString());
    initClient();
  }

  void updateOrderType(String newValue) {
    selectedOrderType.value = newValue;
  }

  void sendOrderRequest(
      String stockSymbol, String direction, int quantity, double price) {
    if (_authService.isLogged.value) {
      _apiProvider.sendOrder(_authService.token!, stockSymbol,
          selectedOrderType.value, direction, quantity, price, (p0) {
        Get.snackbar("Error", "$p0");
      });
      Get.back();
    } else {
      Get.toNamed(AppRoutes.signin);
    }
  }

  @override
  void onClose() {
    super.onClose();

    client.deactivate();
  }

  void initClient() {
    client = StompClient(
      config: StompConfig.sockJS(
        url: wsUrl,
        onConnect: onConnectCallback,
        onDisconnect: (p0) {
          if (p0.body != null) {
            printInfo(info: p0.body!);
          } else {
            printInfo(info: "Disconected");
          }
        },
        onDebugMessage: (p0) => printInfo(info: p0),
        onStompError: (p0) => printInfo(info: p0.body ?? ""),
        onWebSocketError: (p0) => printInfo(info: p0),
      ),
    );

    client.activate();
  }

  void onConnectCallback(StompFrame connectFrame) {
    // client is connected and ready
    printInfo(info: "Connected to websocket");
    client.subscribe(
      destination: '/topic/muaxuandautien',
      headers: {},
      callback: (frame) {
        // Received a frame for this subscription
        if (frame.body != null) {
          // printInfo(info: frame.body!);
          // realtimeQuotes.assignAll(decodeRealtimeQuotesJSON(frame.body!));
          List<RealtimeQuote> newRealtimeQuotes =
              RealtimeQuote.listFromJson(jsonDecode(frame.body!));
          handleChange(newRealtimeQuotes);
          realtimeQuotes.assignAll(newRealtimeQuotes);
          // logger.i("new data");
          isFirstSecond.value = true;
          // printInfo(info: "real-time quotes:\n ${realtimeQuotes.toString()}");
          Timer(const Duration(seconds: 1), () {
            isFirstSecond.value = false;
          });
        } else {
          printInfo(info: "frame.body is null");
        }
      },
    );

    // client.send(
    //     destination: '/app/chat.addUser',
    //     body: jsonEncode(ChatMessage.joinMessage("tan264").toJson()),
    //     headers: {});
    // Get.closeAllSnackbars();
    // Get.snackbar("Connected!", "Enter username to join with us",
    //     snackPosition: SnackPosition.BOTTOM,
    //     backgroundColor: Get.theme.colorScheme.primaryContainer,
    //     colorText: Get.theme.colorScheme.onPrimaryContainer);
  }

  void addToWatchList(String symbol) {
    if (_authService.isLogged.value) {
      _apiProvider.addToWatchList(
        _authService.token!,
        symbol,
        (p0) {
          Get.snackbar("Error", p0);
        },
        () {
          stocksFavorite.add(symbol);
        },
      );
    } else {
      Get.toNamed(AppRoutes.signin);
    }
  }

  void deleteFromWatchList(String symbol) {
    if (_authService.isLogged.value) {
      _apiProvider.deleteFromWatchList(
        _authService.token!,
        symbol,
        (p0) {
          Get.snackbar("Error", p0);
        },
        () {
          stocksFavorite.remove(symbol);
        },
      );
    }
  }

  void handleChange(List<RealtimeQuote> newRealtimeQuotes) {
    for (int i = 0; i < realtimeQuotes.length; i++) {
      RealtimeQuote oldRealtimeQuote = realtimeQuotes[i];
      RealtimeQuote newRealtimeQuote = newRealtimeQuotes[i];

      if (newRealtimeQuote.price > oldRealtimeQuote.price) {
        trackTheChange[newRealtimeQuote.symbol]?["price"] = 1;
      } else if (newRealtimeQuote.price < oldRealtimeQuote.price) {
        trackTheChange[newRealtimeQuote.symbol]?["price"] = -1;
      } else {
        trackTheChange[newRealtimeQuote.symbol]?["price"] = 0;
      }

      if (newRealtimeQuote.changeValue > oldRealtimeQuote.changeValue) {
        trackTheChange[newRealtimeQuote.symbol]?["changeValue"] = 1;
      } else if (newRealtimeQuote.changeValue < oldRealtimeQuote.changeValue) {
        trackTheChange[newRealtimeQuote.symbol]?["changeValue"] = -1;
      } else {
        trackTheChange[newRealtimeQuote.symbol]?["changeValue"] = 0;
      }

      if (newRealtimeQuote.percentChange > oldRealtimeQuote.percentChange) {
        trackTheChange[newRealtimeQuote.symbol]?["percentChange"] = 1;
      } else if (newRealtimeQuote.percentChange <
          oldRealtimeQuote.percentChange) {
        trackTheChange[newRealtimeQuote.symbol]?["percentChange"] = -1;
      } else {
        trackTheChange[newRealtimeQuote.symbol]?["percentChange"] = 0;
      }

      if (newRealtimeQuote.volume > oldRealtimeQuote.volume) {
        trackTheChange[newRealtimeQuote.symbol]?["volume"] = 1;
      } else if (newRealtimeQuote.volume < oldRealtimeQuote.volume) {
        trackTheChange[newRealtimeQuote.symbol]?["volume"] = -1;
      } else {
        trackTheChange[newRealtimeQuote.symbol]?["volume"] = 0;
      }
    }
  }

  bool isLogged() {
    return _authService.isLogged.value;
  }

  // void handleData(String jsonString) {
  //   List<RealtimeQuote> data = decodeRealtimeQuotesJSON(jsonString);
  //   if (realtimeQuotes.isNotEmpty) {
  //     data.asMap().keys.forEach((element) { })
  //   }
  // }
}

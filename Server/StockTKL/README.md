# api get watchlist/orders/owned stocks of current user

api get curent user watchlist
URL: <http://localhost:8080/api/user/watchlist>
Method: GET
Request:None
Respone:
```JSON
[
  {
    "stockId": 2,
    "symbol": "VTP",
    "companyName": "Vạn Thịnh Phát",
    "industry": "Real Steal",
    "sector": "BDS"
  },
  {
    "stockId": 1,
    "symbol": "BMG",
    "companyName": "Ban Mai Group",
    "industry": "Education",
    "sector": "Education"
  }
]
```

api get curent user ownded stocks
URL: <http://localhost:8080/api/user/owned-stocks>
Method: GET
Request:None
Respone:
```JSON
[
  {
    "stockId": 1,
    "symbol": "BMG",
    "companyName": "Ban Mai Group",
    "industry": "Education",
    "sector": "Education"
  }
]
```

api get current user orders
URL: <http://localhost:8080/api/user/orders>
Method: GET
Request:None
Respone:
```JSON
[
  {
    "orderId": 1,
    "userId": 1,
    "stockId": 1,
    "orderType": "MARKET",
    "direction": "BUY",
    "quantity": 1000,
    "price": 10000,
    "status": "COMPLETED",
    "orderDate": [
      2023,
      11,
      27,
      17,
      48,
      58
    ]
  },
  {
    "orderId": 2,
    "userId": 1,
    "stockId": 1,
    "orderType": "MARKET",
    "direction": "BUY",
    "quantity": 1000,
    "price": 10000,
    "status": "COMPLETED",
    "orderDate": [
      2023,
      11,
      27,
      17,
      53,
      47
    ]
  },
  {
    "orderId": 3,
    "userId": 1,
    "stockId": 1,
    "orderType": "MARKET",
    "direction": "SELL",
    "quantity": 1000,
    "price": 10000,
    "status": "COMPLETED",
    "orderDate": [
      2023,
      11,
      27,
      17,
      54,
      8
    ]
  },
  {
    "orderId": 6,
    "userId": 1,
    "stockId": 1,
    "orderType": "MARKET",
    "direction": "SELL",
    "quantity": 1000,
    "price": 10000,
    "status": "COMPLETED",
    "orderDate": [
      2023,
      11,
      27,
      18,
      4,
      31
    ]
  },
  {
    "orderId": 8,
    "userId": 1,
    "stockId": 1,
    "orderType": "LIMIT",
    "direction": "BUY",
    "quantity": 100,
    "price": 10000,
    "status": "COMPLETED",
    "orderDate": [
      2023,
      11,
      27,
      18,
      10,
      41
    ]
  },
  {
    "orderId": 9,
    "userId": 1,
    "stockId": 2,
    "orderType": "LIMIT",
    "direction": "BUY",
    "quantity": 1000,
    "price": 10000,
    "status": "PENDING",
    "orderDate": [
      2023,
      11,
      27,
      18,
      15,
      1
    ]
  },
  {
    "orderId": 12,
    "userId": 1,
    "stockId": 2,
    "orderType": "MARKET",
    "direction": "BUY",
    "quantity": 1000,
    "price": 20000,
    "status": "COMPLETED",
    "orderDate": [
      2023,
      11,
      27,
      18,
      17,
      7
    ]
  },
  {
    "orderId": 13,
    "userId": 1,
    "stockId": 2,
    "orderType": "LIMIT",
    "direction": "SELL",
    "quantity": 500,
    "price": 8000,
    "status": "COMPLETED",
    "orderDate": [
      2023,
      11,
      27,
      18,
      18,
      34
    ]
  },
  {
    "orderId": 16,
    "userId": 1,
    "stockId": 2,
    "orderType": "MARKET",
    "direction": "BUY",
    "quantity": 100,
    "price": null,
    "status": "PENDING",
    "orderDate": [
      2023,
      11,
      27,
      19,
      24,
      37
    ]
  },
  {
    "orderId": 17,
    "userId": 1,
    "stockId": 2,
    "orderType": "MARKET",
    "direction": "BUY",
    "quantity": 1000,
    "price": null,
    "status": "PENDING",
    "orderDate": [
      2023,
      11,
      27,
      19,
      25,
      35
    ]
  },
  {
    "orderId": 19,
    "userId": 1,
    "stockId": 2,
    "orderType": "MARKET",
    "direction": "BUY",
    "quantity": 1000,
    "price": null,
    "status": "PENDING",
    "orderDate": [
      2023,
      11,
      27,
      19,
      40,
      37
    ]
  },
  {
    "orderId": 21,
    "userId": 1,
    "stockId": 2,
    "orderType": "LIMIT",
    "direction": "BUY",
    "quantity": 100,
    "price": 8000,
    "status": "PENDING",
    "orderDate": [
      2023,
      11,
      27,
      19,
      45,
      48
    ]
  },
  {
    "orderId": 22,
    "userId": 1,
    "stockId": 2,
    "orderType": "LIMIT",
    "direction": "BUY",
    "quantity": 100,
    "price": 8000,
    "status": "PENDING",
    "orderDate": [
      2023,
      11,
      27,
      20,
      3,
      23
    ]
  },
  {
    "orderId": 23,
    "userId": 1,
    "stockId": 2,
    "orderType": "LIMIT",
    "direction": "BUY",
    "quantity": 100,
    "price": 8000,
    "status": "PENDING",
    "orderDate": [
      2023,
      11,
      27,
      20,
      4,
      5
    ]
  },
  {
    "orderId": 25,
    "userId": 1,
    "stockId": 2,
    "orderType": "LIMIT",
    "direction": "SELL",
    "quantity": 500,
    "price": 8000,
    "status": "COMPLETED",
    "orderDate": [
      2023,
      11,
      27,
      20,
      26,
      58
    ]
  }
]
```

api send request  
Sample url: http://localhost:8080/api/order/send-order  
Sample request
```JSON
{
  "userId" : 3,
  "stockSymbol": "TKL",
  "orderType": "LIMIT",
  "direction": "SELL",
  "quantity": 10,
  "price": 40
}
```
Sample response
```JSON
{
  "statusCode": 200,
  "message": "Send order successfully",
  "data": {
    "orderId": 13,
    "userId": 3,
    "stockId": 1,
    "orderType": "LIMIT",
    "direction": "SELL",
    "quantity": 10,
    "price": 40,
    "status": "PENDING",
    "orderDate": [
      2023,
      11,
      26,
      16,
      43,
      15,
      766398000
    ]
  }
}
```
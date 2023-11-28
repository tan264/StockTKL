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
    "sector": "Education",
    "quantity": 200,
    "purchaseDate": [
      2023,
      11,
      28,
      11,
      22,
      59
    ],
    "price": 5500
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
    "symbol": "BMG",
    "companyName": "Ban Mai Group",
    "industry": "Education",
    "sector": "Education",
    "price": 10000,
    "quantity": 1000,
    "orderType": "MARKET",
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
    "symbol": "BMG",
    "companyName": "Ban Mai Group",
    "industry": "Education",
    "sector": "Education",
    "price": 10000,
    "quantity": 1000,
    "orderType": "MARKET",
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
    "symbol": "BMG",
    "companyName": "Ban Mai Group",
    "industry": "Education",
    "sector": "Education",
    "price": 10000,
    "quantity": 1000,
    "orderType": "MARKET",
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
    "symbol": "BMG",
    "companyName": "Ban Mai Group",
    "industry": "Education",
    "sector": "Education",
    "price": 10000,
    "quantity": 1000,
    "orderType": "MARKET",
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
    "symbol": "BMG",
    "companyName": "Ban Mai Group",
    "industry": "Education",
    "sector": "Education",
    "price": 10000,
    "quantity": 100,
    "orderType": "LIMIT",
    "orderDate": [
      2023,
      11,
      27,
      18,
      10,
      41
    ]
  }
]
```

api add stocks to watchlist
URL: <http://localhost:8080/api/user/watchlist/add>
Method: POST
Sample request
`symbol=BMG`
Sample respone
```JSON
{
    "statusCode": 200,
    "message": "Stock added to watchlist successfully"
}
```

api remove stocks from watchlist
URL: <http://localhost:8080/api/user/watchlist/remove>
Method: DELETE
Sample request
`symbol=BMG`
Sample respone
```JSON
{
    "statusCode": 200,
    "message": "Stock removed from watchlist successfully"
}
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
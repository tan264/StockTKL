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
        "symbol": "b",
        "companyName": "x",
        "industry": "xyz",
        "sector": "abc"
    },
    {
        "stockId": 1,
        "symbol": "a",
        "companyName": "w",
        "industry": "xyz",
        "sector": "abc"
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
        "stockId": 3,
        "symbol": "c",
        "companyName": "y",
        "industry": "xyz",
        "sector": "abc"
    },
    {
        "stockId": 4,
        "symbol": "d",
        "companyName": "z",
        "industry": "xyz",
        "sector": "abc"
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
        "orderType": null,
        "quantity": 2,
        "price": 100.00,
        "status": null,
        "orderDate": null
    },
    {
        "orderId": 2,
        "orderType": null,
        "quantity": 3,
        "price": 200.00,
        "status": null,
        "orderDate": null
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
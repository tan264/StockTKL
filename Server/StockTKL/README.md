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
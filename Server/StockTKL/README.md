# api get watchlist/orders/owned stocks of current user

api get curent user watchlist
URL: <http://localhost:8080/api/user/watchlist>
Method: GET
Request:None
Respone:
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

api get curent user ownded stocks
URL: <http://localhost:8080/api/user/owned-stocks>
Method: GET
Request:None
Respone:
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

api get current user orders
URL: <http://localhost:8080/api/user/orders>
Method: GET
Request:None
Respone:
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

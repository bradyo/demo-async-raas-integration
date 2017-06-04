# Demo Async RaaS API Integration

This application demonstrates a bulletproof asynchronous [Tango Card RaaS API](https://api.tangocard.com/raas/v2/) 
integration.

In this model, we create an order transaction in the database and queue up the order for 
processing by an asynchronous worker. 

This model is suitable for integrations with the constraints:

- Reward response data is not needed immediately. For example: the reward data is emailed
  after order is placed.
- Need to have low latency when placing orders. For example: the order is placed on a website
  or mobile app and needs a quick response time. 
  
This model is not suitable for integrations with the following constraints:

- Reward response data is needed immediately. For example: the reward data is displayed
  immediately to the user or needs to be passed to a downstream synchronous process.
  

## Running Example App

```
mvn spring-boot:run
```

### Request

```
curl -X POST localhost:8080/orders \
-H 'Content-type: application/json' -d '{
  "amount": "100.00"
}'
```

### Response

```
200 OK
```

```
{
  "referenceNumber": "0001-8743-019357"
}
```
// Order management controller

var orderService = require('../services/order_service') // get an instance of order service

//////////////////////////////////////////////////////////////////
// NORMAL USER ORDER
module.exports.getOrderList = async function(req, res) { 
  // get list of order
  var NID = '001'
  console.log(req.query.NID)
  var orderList = await orderService.getOrderList(req.query.NID) 
  res.send(JSON.stringify(orderList))
}

module.exports.placeOrder = async function(req, res) { 
  // place order
  var order = {
    orderId: null,
    orderAddress: '37 xyz',
    orderStatus: 'Pending',
    NID: '001'
  }
  var response = {
    ack: null
  }
  response.ack = await orderService.placeOrder(req.body) 
  res.send(JSON.stringify(response))
}

module.exports.cancelOrder = async function(req, res) { 
  // update order status
  var request = {
    NID: '001',
    OID: '003'
  }
  var response = {
    ack: null
  }
  console.log(req.body)
  response.ack = await orderService.cancelOrder(req.body.NID, req.body.OID) 
  res.send(JSON.stringify(response))
}

// //////////////////////////////////////////////////////////////////
// // SELLER ORDER
module.exports.getSellerOrderList = async function(req, res) { 
  // get list of order
  var SID = '001'
  var orderList = await orderService.getSellerOrderList(SID) 
  res.send(JSON.stringify(orderList))
}

module.exports.acceptOrderSeller = async function(req, res) { 
  // update order status
  var request = {
    SID: '001',
    OID: '001'
  }
  var response = {
    ack: null
  }
  response.ack = await orderService.acceptOrderSeller(request.SID, request.OID) 
  res.send(JSON.stringify(response))
}

module.exports.cancelOrderSeller = async function(req, res) { 
  // update order status
  var request = {
    SID: '001',
    OID: '002'
  }
  var response = {
    ack: null
  }
  response.ack = await orderService.cancelOrder(request.SID, request.OID) 
  res.send(JSON.stringify(response))
}
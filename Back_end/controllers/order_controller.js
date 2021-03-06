// Order management controller

var orderService = require('../services/order_service') // get an instance of order service

//////////////////////////////////////////////////////////////////
// NORMAL USER ORDER
module.exports.getOrderList = async function(req, res) { 
  // get list of order
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
  var response = {
    ack: null
  }
  response.ack = await orderService.cancelOrder(req.body.NID, req.body.OID) 
  res.send(JSON.stringify(response))
}

// //////////////////////////////////////////////////////////////////
// // SELLER ORDER
module.exports.getSellerOrderList = async function(req, res) { 
  // get list of order
  var orderList = await orderService.getSellerOrderList(req.body.accountID) 
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
  response.ack = await orderService.acceptOrderSeller(req.body.SID, req.body.OID) 
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
  response.ack = await orderService.cancelOrder(req.body.SID, req.body.OID) 
  res.send(JSON.stringify(response))
}
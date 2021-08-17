// Order management controller

var orderService = require('../services/order_service') // get an instance of order service

//////////////////////////////////////////////////////////////////
// NORMAL USER ORDER
module.exports.getOrderList = async function(req, res) { 
  // get list of order
  var NID = '001'
  var orderList = await orderService.getOrderList(NID) 
  res.send(JSON.stringify(orderList))
}

module.exports.placeOrder = async function(req, res) { 
  // place order
  var order = {
    orderId: null,
    orderAddress: '37 abc',
    orderedProducts: [['001', '002'], ['001', '003']], // [SID, PID]
    orderStatus: 'Pending',
    customerName: 'Daph Duck'
  }
  var response = {
    ack: null
  }
  response.ack = await orderService.placeOrder(order) 
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
  response.ack = await orderService.cancelOrder(request.NID, request.OID) 
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
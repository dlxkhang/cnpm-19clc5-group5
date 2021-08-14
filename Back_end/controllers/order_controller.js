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
  var ack = await orderService.placeOrder(order) 
  res.send(JSON.stringify(ack))
}

module.exports.cancelOrder = async function(req, res) { 
  // update order status
  var request = {
    NID: '001',
    OID: '003'
  }
  var ack = await orderService.cancelOrder(request.NID, request.OID) 
  res.send(JSON.stringify(ack))
}

// //////////////////////////////////////////////////////////////////
// // SELLER ORDER
// module.exports.getSellerOrderList = async function(req, res) { 
//   // get list of order
//   var orderList = await orderService.getOrderList() 
//   res.send(JSON.stringify(orderList))
// }

// module.exports.updateSellerOrderStatus = async function(req, res) { 
//   // update order status
//   var ack = orderService.updateSellerOrderStatus() 
//   res.send(JSON.stringify(ack))
// }
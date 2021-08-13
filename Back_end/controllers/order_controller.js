// Order management controller

var orderService = require('../services/order_service') // get an instance of order service
module.exports.getOrderList = function(req, res) { 
  // get list of order
  orderList = orderService.getOrderList() 
  res.send(stringify(orderList))
}

module.exports.updateOrderStatus = function(req, res) { 
  // get list of order
  orderList = orderService.getOrderList() 
  res.send('Updated successfully')
}
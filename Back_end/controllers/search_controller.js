// search controller

var searchService = require('../services/search_service') // get an instance of search service


module.exports.getOrderList = async function(req, res) { 
  // get list of order
  var NID = '001'
  var orderList = await orderService.getOrderList(NID) 
  res.send(JSON.stringify(orderList))
}

// Order management service
// get and process raw data from database


var db = require('../database/database') // get an instance of database

module.exports.getOrderList = { 
    // get list of order
    orderList = db.getOrderList()
}

module.exports.updateOrderStatus = { 
    // get list of order
    orderList = orderService.getOrderList() 
}
  
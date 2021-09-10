// account controller

var accountService = require('../services/account_service') // get an instance of account service


module.exports.checkLogin = async function(req, res) { 
  // get list of order
  var response = await accountService.checkLogin(req.body) 
  res.send(JSON.stringify(response))
}

module.exports.getNormalUserInfo = async function(req, res) { 
    // get list of order
    var normalUserInfo = await accountService.getNormalUserInfo(req.body.accountID) 
    res.send(JSON.stringify(normalUserInfo))
}

module.exports.getSellerInfo = async function(req, res) { 
    // get list of order
    var SID = '001'
    var sellerInfo = await accountService.getSellerInfo(req.body.accountID) 
    res.send(JSON.stringify(sellerInfo))
}
  
module.exports.addNormalAccount = async function(req, res) { 
    var response = await accountService.addNormalAccount(req.body) 
    res.send(JSON.stringify(response))
}

module.exports.addSellerAccount = async function(req, res) { 
    
    var response = await accountService.addSellerAccount(req.body) 
    res.send(JSON.stringify(response))
}

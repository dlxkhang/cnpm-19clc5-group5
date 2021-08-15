// account controller

var accountService = require('../services/account_service') // get an instance of account service


module.exports.checkLogin = async function(req, res) { 
  // get list of order
  var account = {
      username: 'buenas',
      password: '123456'
  }
  var ack = await accountService.checkLogin(account) 
  res.send(JSON.stringify(ack))
}

module.exports.addNormalAccount = async function(req, res) { 
    // get list of order
    var account = {
        accountId: null,
        fullname: 'Daph Duck',
        username: 'buenas',
        phoneNumber: '0909888999',
        password: '123456'
    }
    var ack = await accountService.addNormalAccount(account) 
    res.send(JSON.stringify(ack))
}

module.exports.addSellerAccount = async function(req, res) { 
    // get list of order
    var account = {
        accountId: null,
        storeName: 'Test Store 1',
        storeAddress: '341 Khuong Viet',
        username: 'hola',
        phoneNumber: '0909233222',
        password: '123456'
    }
    var ack = await accountService.addSellerAccount(account) 
    res.send(JSON.stringify(ack))
}

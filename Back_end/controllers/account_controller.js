// account controller

var accountService = require('../services/account_service') // get an instance of account service


module.exports.checkLogin = async function(req, res) { 
  // get list of order
  var account = {
      username: 'buenas',
      password: '123456'
  }
  var response = await accountService.checkLogin(account) 
  res.send(JSON.stringify(response))
}

module.exports.addNormalAccount = async function(req, res) { 
    // get list of order
    var account = {
        accountId: null,
        fullname: 'khang',
        username: 'testacc',
        phoneNumber: '0909888999',
        password: '123456'
    }
    var response = await accountService.addNormalAccount(account) 
    res.send(JSON.stringify(response))
}

module.exports.addSellerAccount = async function(req, res) { 
    // get list of order
    var account = {
        accountId: null,
        storeName: 'Test Store 3',
        storeAddress: '341 Khuong Viet',
        username: 'hola2',
        phoneNumber: '0909233222',
        password: '123456'
    }
    var response = await accountService.addSellerAccount(account) 
    res.send(JSON.stringify(response))
}

// search controller

var searchService = require('../services/search_service') // get an instance of search service


module.exports.searchNewProductByName = async function(req, res) { 
  var key = 'nord'
  // get list of product
  var productList = await searchService.getNewProductListByName(req.query.key)
  res.send(JSON.stringify(productList))
}

module.exports.searchOldProductByName = async function(req, res) { 
  var key = 'acou'
  // get list of product
  var productList = await searchService.getOldProductListByName(key)
  res.send(JSON.stringify(productList))
}

module.exports.searchNewProductByCategory = async function(req, res) { 
  var key = 'Guitar'
  // get list of product
  console.log(req.query.key)
  var productList = await searchService.getNewProductListByCategory(req.query.key)
  res.send(JSON.stringify(productList))
}

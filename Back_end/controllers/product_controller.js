// Product management controller

var productService = require('../services/product_service') // get an instance of product service


//////////////////////////////////////////////////////////////////
// NEW PRODUCT
module.exports.getProductList = async function(req, res) { 
  // get list of product
  var productList = await productService.getProductList()
  res.send(JSON.stringify(productList))
}

module.exports.getProductListSeller = async function(req, res) { 
  // get list of product for seller
  var productList = await productService.getProductListSeller(req.body.SID)
  console.log(productList)
  res.send(JSON.stringify(productList))
}

module.exports.addNewProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  // var product = {
  //   productId: null,
  //   productCategory: '006',
  //   productName: 'test123',
  //   productPrice: 150.5,
  //   SID: '001',
  //   imageURI: null,
  //   productDescription: 'testdescription',
  //   stock:12
  // }
  
  var response = {
    ack: null
  }
  response.ack = await productService.addNewProduct(req.body)
  console.log(JSON.stringify(response))
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.editProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  
  var response = {
    ack: null
  }
  response.ack = await productService.editProduct(req.body)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.deleteProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)

  var response = {
    ack: null
  }
  response.ack = await productService.deleteProduct(req.body.SID)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.addToCart = async function(req, res) { 
  var request = {
    NID: '001',
    SID: '001',
    PID: '002'
  }
  var response = {
    ack: null
  }
  response.ack = await productService.addToCart(req.body)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.deleteProductFromCart = async function(req, res) { 
  // var req = {
  //   productId: '001',
  //   NID: '001'
  // }
  var response = {
    ack: null
  }
  console.log(req.body)
  response.ack = await productService.deleteProductFromCart(req.body)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.getProductsInCart = async function(req, res) { 
  // get list of product in cart
  var NID = '001'
  response = await productService.getProductsInCart(req.query.NID)
  res.send(JSON.stringify(response)) // send acknowledge message
}
//////////////////////////////////////////////////////////////////
// OLD PRODUCT
module.exports.getOldProductList = async function(req, res) { 
  // get list of product
  var oldProductList = await productService.getOldProductList()
  res.send(JSON.stringify(oldProductList))
}

module.exports.addOldProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  var product = {
    productId: null,
    productCategory: '005',
    productName: 'Guitar Bass Yamaha ALO',
    NID: '001',
    imageURI: null,
    productDescription: null,
    condition: 8
  }
  var response = {
    ack: null
  }
  response.ack = await productService.addOldProduct(req.body)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.editOldProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  var product = {
    productId: '002',
    productCategory: '005',
    productName: 'Guitar BA DON',
    NID: '001',
    imageURI: null,
    productDescription: null,
    condition: 6
  }
  var response = {
    ack: null
  }
  response.ack = await productService.editOldProduct(req.body)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.deleteOldProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  var productId = '002'
  var response = {
    ack: null
  }
  response.ack = await productService.deleteOldProduct(req.body.productId)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.getUserProducts= async function(req, res) { 
  // get list of product in cart
  var NID = '001'
  var listOfUserProducts = await productService.getUserProducts(req.query.NID)
  res.send(JSON.stringify(listOfUserProducts))
}
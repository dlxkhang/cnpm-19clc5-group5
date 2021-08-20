// Product management controller

var productService = require('../services/product_service') // get an instance of product service


//////////////////////////////////////////////////////////////////
// NEW PRODUCT
module.exports.getProductList = async function(req, res) { 
  // get list of product
  var productList = await productService.getProductList()
  res.send(JSON.stringify(productList))
}

module.exports.addNewProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  // var product = {
  //   productId: null,
  //   productCategory: null,
  //   productName: 'test',
  //   productPrice: 1303,
  //   sellerName: 'Test Store',
  //   imageURI: null,
  //   productDescription: 'description'
  // }
  var product = {
    productId: null,
    productCategory: '005',
    productName: 'test guitar',
    productPrice: 229,
    sellerName: 'Khang Music',
    imageURI: null,
    productDescription: null,
    stock: 20
  }
  var response = {
    ack: null
  }
  response.ack = await productService.addNewProduct(product)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.editProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  var product = {
    productId: '010',
    productCategory: '005',
    productName: 'Guitar BA DON',
    productPrice: 366,
    sellerName: 'Test Store',
    imageURI: null,
    productDescription: null,
    stock: 100
  }
  var response = {
    ack: null
  }
  response.ack = await productService.editProduct(product)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.deleteProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  var productId = '003'

  var response = {
    ack: null
  }
  response.ack = await productService.deleteProduct(productId)
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
  response.ack = await productService.addToCart(request)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.deleteProductFromCart = async function(req, res) { 
  var req = {
    productId: '001',
    NID: '001'
  }
  var response = {
    ack: null
  }
  response.ack = await productService.deleteProductFromCart(req)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.getProductsInCart = async function(req, res) { 
  // get list of product in cart
  var NID = '001'
  response = await productService.getProductsInCart(NID)
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
    sellerName: 'Daph Duck',
    imageURI: null,
    productDescription: null,
    condition: 8
  }
  var response = {
    ack: null
  }
  response.ack = await productService.addOldProduct(product)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.editOldProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  var product = {
    productId: '002',
    productCategory: '005',
    productName: 'Guitar BA DON',
    sellerName: 'Daph Duck',
    imageURI: null,
    productDescription: null,
    condition: 6
  }
  var response = {
    ack: null
  }
  response.ack = await productService.editOldProduct(product)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.deleteOldProduct = async function(req, res) { 
  // parse JSON to object
  //var product = JSON.parse(req.body)
  var productId = '002'
  var response = {
    ack: null
  }
  response.ack = await productService.deleteOldProduct(productId)
  res.send(JSON.stringify(response)) // send acknowledge message
}

module.exports.getUserProducts= async function(req, res) { 
  // get list of product in cart
  var NID = '001'
  var listOfUserProducts = await productService.getUserProducts(NID)
  res.send(JSON.stringify(listOfUserProducts))
}
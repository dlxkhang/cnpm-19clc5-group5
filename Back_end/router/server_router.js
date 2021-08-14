var express = require('express');
var router = express.Router();
var productController = require('../controllers/product_controller')
var orderController = require('../controllers/order_controller')

// NEW PRODUCT
router.get('/api/product', productController.getProductList) // get list of product
router.get('/api/product/add', productController.addNewProduct) // add new product
router.get('/api/product/edit', productController.editProduct) // edit product
router.get('/api/product/delete', productController.deleteProduct) // delete product
router.get('/api/product/add_to_cart', productController.addToCart) // add product to cart
router.get('/api/cart', productController.getProductsInCart) // get list of product in cart

// OLD PRODUCT
router.get('/api/old_product', productController.getOldProductList) // get list of old product
router.get('/api/old_product/add', productController.addOldProduct) // add old product
router.get('/api/old_product/edit', productController.editOldProduct) // edit old product
router.get('/api/old_product/delete', productController.deleteOldProduct) // delete old product

// NORMAL USER ORDER
router.get('/api/order', orderController.getOrderList) // get order list
router.get('/api/order/place_order', orderController.placeOrder) // place order
router.get('/api/order/cancel', orderController.cancelOrder) // update order status
// SELLER ORDER
module.exports = router
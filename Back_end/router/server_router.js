var express = require('express');
var router = express.Router();
var productController = require('../controllers/product_controller')
var orderController = require('../controllers/order_controller')
var accountController = require('../controllers/account_controller')
var searchController = require('../controllers/search_controller')

// ACCOUNT
router.post('/api/account/login', accountController.checkLogin) // check validity of account
router.post('/api/account/normal_user/info', accountController.getNormalUserInfo) // get normal user info
router.post('/api/account/seller/info', accountController.getSellerInfo) // get normal user info
router.post('/api/account/signup/normal_user', accountController.addNormalAccount) // add normal user account
router.post('/api/account/signup/seller', accountController.addSellerAccount) // add seller account

// NEW PRODUCT
router.get('/api/product', productController.getProductList) // get list of product
router.get('/api/product/add', productController.addNewProduct) // add new product
router.post('/api/product/edit', productController.editProduct) // edit product
router.post('/api/product/delete', productController.deleteProduct) // delete product
router.post('/api/product/add_to_cart', productController.addToCart) // add product to cart
router.post('/api/cart/delete', productController.deleteProductFromCart) // delete product from cart
router.get('/api/cart', productController.getProductsInCart) // get list of product in cart

// OLD PRODUCT
router.get('/api/old_product', productController.getOldProductList) // get list of old product
router.post('/api/old_product/add', productController.addOldProduct) // add old product
router.post('/api/old_product/edit', productController.editOldProduct) // edit old product
router.post('/api/old_product/delete', productController.deleteOldProduct) // delete old product
router.get('/api/old_product/user_product', productController.getUserProducts) // get user's product

// NORMAL USER ORDER
router.get('/api/order', orderController.getOrderList) // get order list
router.post('/api/order/place_order', orderController.placeOrder) // place order
router.post('/api/order/cancel', orderController.cancelOrder) // update order status

// SELLER ORDER
router.get('/api/order_seller', orderController.getSellerOrderList) // get seller order list
router.post('/api/order_seller/accept', orderController.acceptOrderSeller) // accept seller order
router.post('/api/order_seller/cancel', orderController.cancelOrderSeller) // cancel seller order

// SEARCH ENGINE
router.get('/api/search/product', searchController.searchNewProductByName) // search new product by name
router.get('/api/search/old_product', searchController.searchOldProductByName) // search old product by name
router.get('/api/search/product/category', searchController.searchNewProductByCategory) // search new product by category

module.exports = router
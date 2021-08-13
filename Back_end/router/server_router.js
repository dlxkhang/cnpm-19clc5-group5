var express = require('express');
var router = express.Router();
var productController = require('../controllers/product_controller')

router.get('/api/product', productController.getProductList) // get list of product
router.get('/api/product/add', productController.addNewProduct) // add new product
router.get('/api/product/edit', productController.editProduct) // edit product
router.get('/api/product/delete', productController.deleteProduct) // delete product
module.exports = router
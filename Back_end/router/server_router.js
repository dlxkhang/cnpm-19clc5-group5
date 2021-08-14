var express = require('express');
var router = express.Router();
var productController = require('../controllers/product_controller')

router.get('/api/product', productController.getProductList) // get list of product
router.get('/api/product/add', productController.addNewProduct) // add new product
router.get('/api/product/edit', productController.editProduct) // edit product
router.get('/api/product/delete', productController.deleteProduct) // delete product

router.get('/api/old_product', productController.getOldProductList) // get list of product
router.get('/api/old_product/add', productController.addOldProduct) // add new product
router.get('/api/old_product/edit', productController.editOldProduct) // edit product
router.get('/api/old_product/delete', productController.deleteOldProduct) // delete product
module.exports = router
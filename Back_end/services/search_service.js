// Search engine service
// get and process raw data from database


var db = require('../database/database') // get an instance of database


module.exports.getNewProductListByName = (key) => {
    return new Promise(function(resolve, reject) {
        key = '%' + key + '%'
        var query = "SELECT distinct p.PID as productId, p.CATEGORY as productCategory, p.PRODUCT_NAME as productName, w.UNIT_PRICE as productPrice, s.STORE_NAME as sellerName, p.IMAGE_URL as imageURI, p.DESCRIPTION as productDescription, w.STOCK as stock FROM Product p JOIN Warehouse w ON(p.PID = w.PID) JOIN Seller s ON(w.SID = s.SID) WHERE w.stock > 0 AND p.PRODUCT_NAME LIKE ?"
        db.all(query, key, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            resolve(allRows)
        })
    })
}

module.exports.getOldProductListByName = (key) => {
    return new Promise(function(resolve, reject) {
        key = '%' + key + '%'
        var query = "SELECT p.PID as productId, p.CATEGORY as productCategory, p.PRODUCT_NAME as productName, n.FULL_NAME as sellerName, p.IMAGE_URL as imageURI, p.DESCRIPTION as productDescription, w.CONDITION as condition FROM OldProduct p JOIN UserWarehouse w ON(p.PID = w.PID) JOIN NormalUser n ON(w.NID = n.NID) WHERE p.PRODUCT_NAME LIKE ?"
        db.all(query, key, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            resolve(allRows)
        })
    })
}

module.exports.getNewProductListByCategory = (key) => {
    return new Promise(function(resolve, reject) {
        key = '%' + key + '%'
        var query = "SELECT p.PID as productId, c.CATEGORY_NAME as productCategory, p.PRODUCT_NAME as productName, w.UNIT_PRICE as productPrice, s.STORE_NAME as sellerName, p.IMAGE_URL as imageURI, p.DESCRIPTION as productDescription, w.STOCK as stock FROM Product p JOIN Warehouse w ON(p.PID = w.PID) JOIN Seller s ON(w.SID = s.SID) JOIN Category c ON(c.CID = p.CATEGORY) WHERE w.stock > 0 AND c.CATEGORY_NAME LIKE ?"
        db.all(query, key, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            resolve(allRows)
        })
    })
}
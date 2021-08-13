// product management service
// get and process raw data from database


var db = require('../database/database') // get an instance of database
// var productCategory: Category,
//     var productName:String,
//     var productPrice:Double,
//     var sellerName:String,
//     var imageURI: String? = null,
//     var productDescription:String? = null): Parcelable

module.exports.getProductList = () => {
    return new Promise(function(resolve, reject) {
        var query = "SELECT p.PID as productId, p.CATEGORY as productCategory, p.PRODUCT_NAME as productName, w.UNIT_PRICE as productPrice, s.STORE_NAME as sellerName, p.IMAGE_URL as imageURI, p.DESCRIPTION as productDescription, w.STOCK as stock FROM Product p JOIN Warehouse w ON(p.PID = w.PID) JOIN Seller s ON(w.SID = s.SID) WHERE w.stock > 0"
        db.all(query, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            resolve(allRows)
        })
    })
}

async function checkProductExist(product) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT p.PRODUCT_NAME as productName, s.STORE_NAME as sellerName FROM Product p JOIN Warehouse w ON(p.PID = w.PID) JOIN Seller s ON(w.SID = s.SID) WHERE p.PRODUCT_NAME = ? AND s.STORE_NAME = ?"
        var params = [product.productName, product.sellerName]
        db.all(query, params, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            if(allRows.length == 0) {
                resolve(false)
                return
            }
            resolve(true)
        })  
    })
}

async function generateNewId(product) {
    return new Promise(async function(resolve, reject) {
        // generate new ID
        var newProductId = 0
        var query = "SELECT MAX(PID) as MAX_PID FROM Product"
        db.each(query, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            if(row.length == 0)
                newProductId = 0
            else {
                newProductId = parseInt(`${row.MAX_PID}`) + 1
                product.productId = newProductId.toString().padStart(3, '0')
                resolve()
            }     
        })
    })
}

async function getStoreId(product) {
    return new Promise(async function(resolve, reject) {
        // get store ID based on store name
        var storeId
        var query = "SELECT SID FROM Seller WHERE STORE_NAME = ?"
        db.each(query, [product.sellerName], function(err, row) {
            if(err) {
                reject(err)
                return
            }
            storeId = parseInt(`${row.SID}`)
            resolve(storeId.toString().padStart(3, '0'))
        })
    })
}

module.exports.addNewProduct = async (product) => {
    return new Promise(async function(resolve, reject) {
        // check if product already exists
        var productExist = await checkProductExist(product)
        if(productExist) {
            resolve('product_exist')
            return
        }
        
        // generate new ID
        await generateNewId(product)


        db.serialize(() => {
            // add to Product table
            var query = "INSERT INTO Product VALUES (?, ?, ?, ?, ?)"
            var params = [product.productId, product.productName, product.productCategory, product.imageURI, product.productDescription]
            db.run(query, params, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // get store ID based on store name
                var storeId = await getStoreId(product)
                
                // add product to warehouse
                console.log(product)
                var query = "INSERT INTO Warehouse VALUES (?, ?, ?, ?)"
                var params = [storeId, product.productId, product.stock, product.productPrice]
                db.run(query, params, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                    resolve("add_product_success")
                })
            });
        });
    })
}

async function checkProductExistById(PID) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT PID FROM Product WHERE PID = ?"
        db.all(query, [PID], function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            if(allRows.length == 0) {
                resolve(false)
                return
            }
            resolve(true)
        })  
    })
}

module.exports.editProduct = async (product) => {
    return new Promise(async function(resolve, reject) {
        // check if product exist by ID
        var productExist = await checkProductExistById(product.productId)
        if(!productExist) {
            resolve('product_not_exist')
            return
        }

        db.serialize(() => {
            // update in Product table
            var query = "UPDATE Product SET PRODUCT_NAME = ?, CATEGORY = ?, IMAGE_URL = ?, DESCRIPTION = ? WHERE PID = ?"
            var params = [product.productName, product.productCategory, product.imageURI, product.productDescription, product.productId]
            db.run(query, params, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // update product in Warehouse table
                console.log(product)
                var query = "UPDATE Warehouse SET STOCK = ?, UNIT_PRICE = ? WHERE PID = ?"
                var params = [product.stock, product.productPrice, product.productId]
                db.run(query, params, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                    resolve("edit_product_success")
                })
            });
        });
    })
}

module.exports.deleteProduct = async (productId) => {
    return new Promise(async function(resolve, reject) {
        // check if product exist by ID
        var productExist = await checkProductExistById(productId)
        if(!productExist) {
            resolve('product_not_exist')
            return
        }

        db.serialize(() => {
            // delete in Product table
            var query = "DELETE FROM Product WHERE PID = ?"
            db.run(query, productId, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // delete product in Warehouse table
                var query = "DELETE FROM Warehouse WHERE PID = ?"
                db.run(query, productId, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                    resolve("delete_product_success")
                })
            });
        });
    })
}
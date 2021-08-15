// product management service
// get and process raw data from database


var db = require('../database/database') // get an instance of database
// var productCategory: Category,
//     var productName:String,
//     var productPrice:Double,
//     var sellerName:String,
//     var imageURI: String? = null,
//     var productDescription:String? = null): Parcelable


//////////////////////////////////////////////////////////////////
// NEW PRODUCT
module.exports.getProductList = () => {
    return new Promise(function(resolve, reject) {
        var query = "SELECT p.PID as productId, c.CATEGORY_NAME as productCategory, p.PRODUCT_NAME as productName, w.UNIT_PRICE as productPrice, s.STORE_NAME as sellerName, p.IMAGE_URL as imageURI, p.DESCRIPTION as productDescription, w.STOCK as stock FROM Product p JOIN Warehouse w ON(p.PID = w.PID) JOIN Seller s ON(w.SID = s.SID) JOIN Category c ON(c.CID = p.CATEGORY) WHERE w.stock > 0"
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

async function queryProductPrice(SID, PID) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT UNIT_PRICE FROM Warehouse WHERE SID = ? AND PID = ?"
        db.each(query, SID, PID, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            resolve(row.UNIT_PRICE)
        })
    })
}

module.exports.addToCart = async (request) => {
    return new Promise(async function(resolve, reject) {
        db.serialize(async () => {
            // query Price of product
            var productPrice = await queryProductPrice(request.SID, request.PID)
            
            db.serialize(() => {
                // add to ShoppingCart table
                var query = "INSERT INTO ShoppingCart VALUES (?, ?, ?, ?, ?)"
                var params = [request.NID, request.SID, request.PID, 1, productPrice]
                db.run(query, params, function(err) {
                    if (err) {
                        reject(err)
                        return
                    }
                    resolve('add_to_cart_success')
                })
            })
        })
    })
}

module.exports.getProductsInCart = (NID) => {
    return new Promise(function(resolve, reject) {
        var query = "SELECT p.PRODUCT_NAME as productName, sc.TOTAL_PRICE as productPrice FROM ShoppingCart sc JOIN Product p ON(sc.PID = p.PID) WHERE sc.NID = ?"
        db.all(query, NID, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            resolve(allRows)
        })
    })
}
//////////////////////////////////////////////////////////////////
// OLD PRODUCT
module.exports.getOldProductList = () => {
    return new Promise(function(resolve, reject) {
        var query = "SELECT p.PID as productId, c.CATEGORY_NAME as productCategory, p.PRODUCT_NAME as productName, n.FULL_NAME as sellerName, p.IMAGE_URL as imageURI, p.DESCRIPTION as productDescription, w.CONDITION as condition FROM OldProduct p JOIN UserWarehouse w ON(p.PID = w.PID) JOIN NormalUser n ON(w.NID = n.NID) JOIN Category c ON(c.CID = p.CATEGORY)"
        db.all(query, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            resolve(allRows)
        })
    })
}

async function checkOldProductExist(product) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT p.PRODUCT_NAME as productName, n.FULL_NAME as sellerName FROM OldProduct p JOIN UserWarehouse w ON(p.PID = w.PID) JOIN NormalUser n ON(w.NID = n.NID) WHERE p.PRODUCT_NAME = ? AND n.FULL_NAME = ?"
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

async function generateNewIdForOldProduct(product) {
    return new Promise(async function(resolve, reject) {
        // generate new ID
        var newProductId = 0
        var query = "SELECT MAX(PID) as MAX_PID FROM OldProduct"
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

async function getSellerId(product) {
    return new Promise(async function(resolve, reject) {
        // get seller ID based on seller name
        var sellerId
        var query = "SELECT NID FROM NormalUser WHERE FULL_NAME = ?"
        db.each(query, [product.sellerName], function(err, row) {
            if(err) {
                reject(err)
                return
            }
            sellerId = parseInt(`${row.NID}`)
            resolve(sellerId.toString().padStart(3, '0'))
        })
    })
}

module.exports.addOldProduct = async (product) => {
    return new Promise(async function(resolve, reject) {
        // check if product already exists
        var productExist = await checkOldProductExist(product)
        if(productExist) {
            resolve('old_product_exist')
            return
        }
        
        // generate new ID
        await generateNewIdForOldProduct(product)


        db.serialize(() => {
            // add to Product table
            var query = "INSERT INTO OldProduct VALUES (?, ?, ?, ?, ?)"
            var params = [product.productId, product.productName, product.productCategory, product.imageURI, product.productDescription]
            db.run(query, params, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // get seller ID based on seller name
                var sellerId = await getSellerId(product)
                
                // add product to user warehouse
                console.log(product)
                var query = "INSERT INTO UserWarehouse VALUES (?, ?, ?)"
                var params = [sellerId, product.productId, product.condition]
                db.run(query, params, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                    resolve("add_old_product_success")
                })
            });
        });
    })
}

async function checkOldProductExistById(PID) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT PID FROM OldProduct WHERE PID = ?"
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

module.exports.editOldProduct = async (product) => {
    return new Promise(async function(resolve, reject) {
        // check if product exist by ID
        var productExist = await checkOldProductExistById(product.productId)
        if(!productExist) {
            resolve('old_product_not_exist')
            return
        }

        db.serialize(() => {
            // update in Product table
            var query = "UPDATE OldProduct SET PRODUCT_NAME = ?, CATEGORY = ?, IMAGE_URL = ?, DESCRIPTION = ? WHERE PID = ?"
            var params = [product.productName, product.productCategory, product.imageURI, product.productDescription, product.productId]
            db.run(query, params, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // update product in UserWarehouse table
                console.log(product)
                var query = "UPDATE UserWarehouse SET CONDITION = ? WHERE PID = ?"
                var params = [product.condition, product.productId]
                db.run(query, params, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                    resolve("edit_old_product_success")
                })
            });
        });
    })
}

module.exports.deleteOldProduct = async (productId) => {
    return new Promise(async function(resolve, reject) {
        // check if product exist by ID
        var productExist = await checkOldProductExistById(productId)
        if(!productExist) {
            resolve('old_product_not_exist')
            return
        }

        db.serialize(() => {
            // delete in OldProduct table
            var query = "DELETE FROM OldProduct WHERE PID = ?"
            db.run(query, productId, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // delete product in UserWarehouse table
                var query = "DELETE FROM UserWarehouse WHERE PID = ?"
                db.run(query, productId, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                    resolve("delete_old_product_success")
                })
            });
        });
    })
}
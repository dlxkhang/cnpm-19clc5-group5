// Order management service
// get and process raw data from database


var db = require('../database/database') // get an instance of database
const deliveryChargesByProduct = 2

// NORMAL USER ORDER    
function Order(orderId, orderAddress, orderedProducts, itemTotal, deliveryCharges, orderStatus, customerName) {
    this.orderId = orderId
    this.orderAddress = orderAddress
    this.orderedProducts = orderedProducts
    this.itemTotal = itemTotal
    this.deliveryCharges = deliveryCharges
    this.orderStatus = orderStatus
    this.customerName = customerName
}

async function getListOID(NID) {
    return new Promise(function(resolve, reject) {
        var query = "SELECT distinct o.OID FROM [Order] o JOIN OrderOf of ON(o.OID = of.OID) WHERE of.NID = ?"
        var listOID = []
        db.all(query, NID, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            allRows.forEach((item) => {
                listOID.push(item.OID)
            })
            resolve(listOID)
        })
    })
}

async function getListSID(OID) {
    return new Promise(function(resolve, reject) {
        var query = "SELECT SID FROM OrderDetail WHERE OID = ?"
        var listSID = []
        db.all(query, OID, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            allRows.forEach((item) => {
                if(!listSID.includes(item.SID))
                    listSID.push(item.SID)
            })
            resolve(listSID)
        })
    })
}

async function getOrderedProducts(OID, listSID) {
    return new Promise(async function(resolve, reject) {
        var orderedProducts = []
        for(const SID of listSID) {
            // get ordered product
            var query = "SELECT distinct p.PID as productId, p.CATEGORY as productCategory, p.PRODUCT_NAME as productName, w.UNIT_PRICE as productPrice, s.STORE_NAME as sellerName, p.IMAGE_URL as imageURI, p.DESCRIPTION as productDescription, w.STOCK as stock FROM Product p JOIN Warehouse w ON(p.PID = w.PID) JOIN OrderDetail od ON(od.PID = p.PID) JOIN Seller s ON(od.SID = s.SID) WHERE od.OID = ? AND w.SID = ? AND od.SID = ?"
            db.all(query, OID, SID, SID, async function(err, allRows) {
                if(err) {
                    reject(err)
                    return
                }
                allRows.forEach((row) => {
                    orderedProducts.push(row)
                })
            })
        }
        resolve(orderedProducts)
    })
}

async function calcItemTotal(orderedProducts) {
    return new Promise(function(resolve, reject) {
        var itemTotal = 0
        orderedProducts.forEach((product) => {
            itemTotal += product.productPrice
        })
        resolve(itemTotal)
    })
}

async function getOrderAddress(OID) {
    return new Promise(function(resolve, reject) {
        query = "SELECT ADDRESS as orderAddress FROM [Order] WHERE OID = ?"
        db.each(query, OID, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            resolve(`${row.orderAddress}`)
        })
    })
}

async function getOrderStatus(OID) {
    return new Promise(function(resolve, reject) {
        query = "SELECT Status as orderStatus FROM OrderOf WHERE OID = ?"
        db.each(query, OID, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            resolve(`${row.orderStatus}`)
        })
    })
}

async function getCustomerName(NID) {
    return new Promise(function(resolve, reject) {
        query = "SELECT FULL_NAME as customerName FROM NormalUser WHERE NID = ?"
        db.each(query, NID, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            resolve(`${row.customerName}`)
        })
    })
}

async function initOrder(OID, orderAddress, orderedProducts, itemTotal, deliveryCharges, orderStatus,customerName) {
    return new Promise(function(resolve, reject) {
        var order = new Order(OID, orderAddress, orderedProducts, itemTotal, deliveryCharges, orderStatus,customerName)
        resolve(new Order(OID, orderAddress, orderedProducts, itemTotal, deliveryCharges, orderStatus,customerName))
    })
}

module.exports.getOrderList = (NID) => { 
    return new Promise(async function(resolve, reject) {
        // var order = {
        //     val orderId: '001',
        //     val orderAddress: '341 Khuong Viet Phu Trung',
        //     val orderedProducts: List<Product>,
        //     val itemTotal: Double,
        //     val deliveryCharges: Double,
        //     var orderStatus: String
        //     var storeName: string
        // }
        var listOfOrder = []
        // get orders id
        var listOID = await getListOID(NID)

        // loop through each OID to get detail info
        for(const OID of listOID) {
            // get list of stores of each OID
            var listSID = await getListSID(OID)

            // get list of ordered products
            var orderedProducts = await getOrderedProducts(OID, listSID)

            // calculate items total price
            var itemTotal = await calcItemTotal(orderedProducts)

            // calculate delivery charges
            var deliveryCharges = deliveryChargesByProduct * orderedProducts.length

            // get order address of current OID
            var orderAddress = await getOrderAddress(OID)

            // get order status of current OID
            var orderStatus = await getOrderStatus(OID)
        
            // get customer name of current OID
            var customerName = await getCustomerName(NID)
            
            var order = await initOrder(OID, orderAddress, orderedProducts, itemTotal, deliveryCharges, orderStatus,customerName)
            listOfOrder.push(order)
        }
        resolve(listOfOrder)
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

async function findNIDByFullName(fullName) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT NID FROM NormalUser WHERE FULL_NAME = ?"
        db.each(query, fullName, function(err, row) {
            if (err) {
                reject(err)
                return
            }
            resolve(row.NID)
        })
    })
}

async function generateNewId(order) {
    return new Promise(async function(resolve, reject) {
        // generate new ID
        var newOrderId = 0
        var query = "SELECT MAX(OID) as MAX_OID FROM [Order]"
        db.each(query, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            if(row.length == 0)
                newOrderId = 0
            else {
                newOrderId = parseInt(`${row.MAX_OID}`) + 1
                order.orderId = newOrderId.toString().padStart(3, '0')
                resolve()
            }     
        })
    })
}

module.exports.placeOrder = (order) => {
    return new Promise(async function(resolve, reject) {
        db.serialize(async () => {
            // insert into Order table
            var query = "INSERT INTO [Order] VALUES (?, ?)"

            // generate order ID
            await generateNewId(order)

            db.run(query, order.orderId, order.orderAddress, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // insert into OrderDetail table
                for(const item of order.orderedProducts) {
                    var query = "INSERT INTO OrderDetail VALUES (?, ?, ?, ?, ?)"
                    var productPrice = await queryProductPrice(item[0], item[1])
                    var params = [order.orderId, item[0], item[1], 1, productPrice]
                    db.run(query, params, function(err) {
                        if (err) {
                            reject(err)
                            return
                        }
                    })

                    // decrease stock in Warehouse table
                    db.serialize(() => {
                        query = "UPDATE Warehouse SET STOCK = STOCK - 1 WHERE SID = ? AND PID = ?"
                        db.run(query, item[0], item[1], function(err) {
                            if (err) {
                                reject(err)
                                return
                            }
                        })
                    })  
                }
            })

            db.serialize(async () => {
                // insert into OrderOf table
                var query = "INSERT INTO OrderOf VALUES (?, ?, ?)"
                var NID = await findNIDByFullName(order.customerName)
                db.run(query, order.orderId, NID, order.orderStatus, function(err) {
                    if (err) {
                        reject(err)
                        return
                    }
                })

                // delete ordered product in Shopping Cart table
                db.serialize(async () => {
                    var query = "DELETE FROM ShoppingCart WHERE NID = ?"
                    db.run(query, NID, function(err) {
                        if (err) {
                            reject(err)
                            return
                        }
                    })
                })

            })
            resolve("place_order_success")
        })
    })
}

async function checkOrderStatus(NID, OID) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT Status FROM OrderOf WHERE NID = ? AND OID = ?"
        db.each(query, NID, OID, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            resolve(row.Status)
        })  
    })
}

module.exports.cancelOrder = (NID, OID) => {
    return new Promise(async function(resolve, reject) {
        // check order status
        var orderStatus = await checkOrderStatus(NID, OID)
        if(orderStatus == "Canceled") {
            resolve('order_already_canceled')
            return
        }

        // update in OrderOf table
        var query = "UPDATE OrderOf SET Status = 'Canceled' WHERE NID = ? AND OID = ?"
        db.run(query, NID, OID, function(err) {
            if (err) {
                reject(err)
                return
            }
        })
        resolve('cancel_order_success')
    })
}



// SELLER ORDER
async function getCustomerNameByOID(OID) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT NID FROM OrderOf WHERE OID = ?"
        db.each(query, OID, async function(err, row) {
            if (err) {
                reject(err)
                return
            }
            var customerName = await getCustomerName(row.NID)
            resolve(customerName)
        })
    })
}

async function getListOIDSeller(SID) {
    return new Promise(function(resolve, reject) {
        var query = "SELECT OID FROM OrderDetail WHERE SID = ?"
        var listOID = []
        db.all(query, SID, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            allRows.forEach((item) => {
                if(!listOID.includes(item.OID))
                    listOID.push(item.OID)
            })
            resolve(listOID)
        })
    })
}

async function getOrderedProductsSeller(OID, SID) {
    return new Promise(async function(resolve, reject) {
        var orderedProducts = []
        // get ordered product
        var query = "SELECT distinct p.PID as productId, p.CATEGORY as productCategory, p.PRODUCT_NAME as productName, w.UNIT_PRICE as productPrice, s.STORE_NAME as sellerName, p.IMAGE_URL as imageURI, p.DESCRIPTION as productDescription, w.STOCK as stock FROM Product p JOIN Warehouse w ON(p.PID = w.PID) JOIN OrderDetail od ON(od.PID = p.PID) JOIN Seller s ON(od.SID = s.SID) WHERE od.OID = ? AND w.SID = ? AND od.SID = ?"
        db.all(query, OID, SID, SID, async function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            allRows.forEach((row) => {
                orderedProducts.push(row)
            })
        })
        resolve(orderedProducts)
    })
}

module.exports.getSellerOrderList = (SID) => { 
    return new Promise(async function(resolve, reject) {
        var listOfOrder = []
        // get orders id
        var listOID = await getListOIDSeller(SID)
        // loop through each OID to get detail info
        for(const OID of listOID) {
            // get list of ordered products
            var orderedProducts = await getOrderedProductsSeller(OID, SID)

            // calculate items total price
            var itemTotal = await calcItemTotal(orderedProducts)

            // calculate delivery charges
            var deliveryCharges = deliveryChargesByProduct * orderedProducts.length

            // get order address of current OID
            var orderAddress = await getOrderAddress(OID)

            // get order status of current OID
            var orderStatus = await getOrderStatus(OID)
        
            // get customer name of current OID
            var customerName = await getCustomerNameByOID(OID)
            
            var order = await initOrder(OID, orderAddress, orderedProducts, itemTotal, deliveryCharges, orderStatus,customerName)
            listOfOrder.push(order)
        }
        resolve(listOfOrder)
    })
}

async function checkOrderSellerStatus(OID) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT Status FROM OrderOf WHERE OID = ?"
        db.each(query, OID, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            resolve(row.Status)
        })  
    })
}

module.exports.acceptOrderSeller = (SID, OID) => {
    return new Promise(async function(resolve, reject) {
        // check order status
        var orderStatus = await checkOrderSellerStatus(OID)
        if(orderStatus == "Accepted") {
            resolve('order_seller_already_accepted')
            return
        }
        else if(orderStatus == "Canceled") {
            resolve('cannot_accept_an_canceled_order')
            return
        }

        // update in OrderOf table
        var query = "UPDATE OrderOf SET Status = 'Accepted' WHERE OID = ?"
        db.run(query, OID, function(err) {
            if (err) {
                reject(err)
                return
            }
        })
        resolve('accept_order_seller_success')
    })
}

module.exports.cancelOrderSeller = (SID, OID) => {
    return new Promise(async function(resolve, reject) {
        // check order status
        var orderStatus = await checkOrderSellerStatus(OID)
        if(orderStatus == "Canceled") {
            resolve('order_seller_already_canceled')
            return
        }

        // update in OrderOf table
        var query = "UPDATE OrderOf SET Status = 'Canceled' WHERE OID = ?"
        db.run(query, OID, function(err) {
            if (err) {
                reject(err)
                return
            }
        })
        resolve('cancel_order_seller_success')
    })
}
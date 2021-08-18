// account service
// get and process raw data from database


var db = require('../database/database') // get an instance of database

async function checkValidity(account) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT AID, USERNAME, PASSWORD FROM Account WHERE USERNAME = ?"
        db.all(query, account.username, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            if(allRows.length == 0) {
                resolve('account_not_exist')
                return
            }
            if(allRows[0].PASSWORD == account.password) {
                resolve(allRows[0].AID) // account valid
                return
            }
            resolve('account_not_valid')
        })  
    })
}

async function checkUserType(userID) {
    return new Promise(async function(resolve, reject) {
        db.serialize(() => {
            var query = "SELECT AID FROM NormalAccount WHERE AID = ?"
            db.all(query, userID, function(err, allRows) {
                if(err) {
                    reject(err)
                    return
                }
                if(allRows.length == 0) {
                    resolve('seller')
                    return
                }
                resolve('normal_user')
                
            })  
        })
    })
}

async function getSID(userID) {
    return new Promise(async function(resolve, reject) {
        db.serialize(() => {
            var query = "SELECT SID FROM SellerAccount WHERE AID = ?"
            db.all(query, userID, function(err, allRows) {
                if(err) {
                    reject(err)
                    return
                }
                resolve(allRows[0].SID)           
            })  
        })
    })
}

async function getNID(userID) {
    return new Promise(async function(resolve, reject) {
        db.serialize(() => {
            var query = "SELECT NID FROM NormalAccount WHERE AID = ?"
            db.all(query, userID, function(err, allRows) {
                if(err) {
                    reject(err)
                    return
                }
                resolve(allRows[0].NID)        
            })  
        })
    })
}

module.exports.getNormalUserInfo = (NID) => {
    return new Promise(async function(resolve, reject) {
        db.serialize(() => {
            var query = "SELECT FULL_NAME as fullname, PHONE_NUMBER as phoneNumber FROM NormalUser WHERE NID = ?"
            db.all(query, NID, function(err, allRows) {
                if(err) {
                    reject(err)
                    return
                }
                resolve(allRows[0])        
            })  
        })
    })
}

module.exports.getSellerInfo = (SID) => {
    return new Promise(async function(resolve, reject) {
        db.serialize(() => {
            var query = "SELECT STORE_NAME as storeName, STORE_ADDRESS as storeAddress, PHONE_NUMBER as phoneNumber FROM Seller WHERE SID = ?"
            db.all(query, SID, function(err, allRows) {
                if(err) {
                    reject(err)
                    return
                }
                resolve(allRows[0])        
            })    
        })
    })
}

module.exports.checkLogin = (account) => {
    return new Promise(async function(resolve, reject) {
        // check username, password
        var checkMsg = await checkValidity(account)
        if(checkMsg == "account_not_exist") {
            var response = {
                ack: null,
                ID: "",
            }
            response.ack = 'account_not_exist'
            resolve(response)
            return
        }
        if(checkMsg == "account_not_valid") { // wrong username or password
            var response = {
                ack: null,
                ID: "",
            }
            response.ack = 'account_not_valid'
            resolve(response)
            return
        }
        // case account valid => checkMsg contain AID
        var userID = checkMsg.toString()
        // check whether seller or normal user
        var userType = await checkUserType(userID)
        if(userType == "seller") {
            var response = {
                ack: null,
                ID: null,
            }
            response.ack = 'seller_account_valid'
            response.ID = await getSID(userID)
            resolve(response)
            return
        }
        var response = {
            ack: null,
            ID: null,
        }
        response.ack = 'normal_user_account_valid'
        response.ID = await getNID(userID)
        resolve(response)
    })
}

async function generateNewAccountId(account) {
    return new Promise(async function(resolve, reject) {
        // generate new ID
        var newAccountId = 0
        var query = "SELECT MAX(AID) as MAX_AID FROM Account"
        db.each(query, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            if(row.length == 0)
                newAccountId = 0
            else {
                newAccountId = parseInt(`${row.MAX_AID}`) + 1
                account.accountId = newAccountId.toString().padStart(3, '0')
                resolve()
            }     
        })
    })
}

async function checkAccountExist(account) {
    return new Promise(async function(resolve, reject) {
        var query = "SELECT AID, USERNAME, PASSWORD FROM Account WHERE USERNAME = ?"
        db.all(query, account.username, function(err, allRows) {
            if(err) {
                reject(err)
                return
            }
            if(allRows.length > 0) {
                resolve('account_exist')
                return
            }
            resolve('account_not_exist')
        })  
    })
}

async function generateNormalUserId() {
    return new Promise(async function(resolve, reject) {
        // generate new ID
        var newId = 0
        var query = "SELECT MAX(NID) as MAX_NID FROM NormalUser"
        db.each(query, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            if(row.length == 0)
                newId = 0
            else {
                newId = parseInt(`${row.MAX_NID}`) + 1
                resolve(newId.toString().padStart(3, '0'))
            }     
        })
    })
}

module.exports.addNormalAccount = (account) => {
    return new Promise(async function(resolve, reject) {
        // check username, password
        var checkMsg = await checkAccountExist(account)
        if(checkMsg == "account_exist") {
            resolve('account_exist')
            return
        }
        // case account not exist => can create a new account
        // generate new account ID
        await generateNewAccountId(account)
        // generate normal user ID
        var NID = await generateNormalUserId()

        db.serialize(() => {
            // add to Account table
            var query = "INSERT INTO Account VALUES (?, ?, ?)"
            var params = [account.accountId, account.username, account.password]
            db.run(query, params, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // add to NormalUser table
                var query = "INSERT INTO NormalUser VALUES (?, ?, ?)"
                var params = [NID, account.fullname, account.phoneNumber]
                db.run(query, params, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                })
            })

            db.serialize(async () => {
                // add to NormalAccount table
                var query = "INSERT INTO NormalAccount VALUES (?, ?)"
                var params = [account.accountId, NID]
                db.run(query, params, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                })
                var response = {
                    ack: 'sign_up_success',
                    NID: NID
                }
                resolve(response)
            })
        })
    })
}


async function generateSellerId() {
    return new Promise(async function(resolve, reject) {
        // generate new ID
        var newId = 0
        var query = "SELECT MAX(SID) as MAX_SID FROM Seller"
        db.each(query, function(err, row) {
            if(err) {
                reject(err)
                return
            }
            if(row.length == 0)
                newId = 0
            else {
                newId = parseInt(`${row.MAX_SID}`) + 1
                resolve(newId.toString().padStart(3, '0'))
            }     
        })
    })
}

module.exports.addSellerAccount = (account) => {
    return new Promise(async function(resolve, reject) {
        // check username, password
        var checkMsg = await checkAccountExist(account)
        if(checkMsg == "account_exist") {
            resolve('account_exist')
            return
        }
        // case account not exist => can create a new account
        // generate new account ID
        await generateNewAccountId(account)
        // generate seller ID
        var SID = await generateSellerId()

        db.serialize(() => {
            // add to Account table
            var query = "INSERT INTO Account VALUES (?, ?, ?)"
            var params = [account.accountId, account.username, account.password]
            db.run(query, params, function(err) {
                if (err) {
                    reject(err)
                    return
                }
            })

            db.serialize(async () => {
                // add to Seller table
                var query = "INSERT INTO Seller VALUES (?, ?, ?, ?)"
                var params = [SID, account.storeName, account.storeAddress, account.phoneNumber]
                db.run(query, params, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                })
            })

            db.serialize(async () => {
                // add to SellerAccount table
                var query = "INSERT INTO SellerAccount VALUES (?, ?)"
                var params = [account.accountId, SID]
                db.run(query, params, function(err) {
                    if(err) {
                        reject(err)
                        return
                    }
                })
                var response = {
                    ack: 'sign_up_success',
                    SID: SID
                }
                resolve(response)
            })
        })
    })
}
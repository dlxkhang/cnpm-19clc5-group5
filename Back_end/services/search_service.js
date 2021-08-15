// Search engine service
// get and process raw data from database


var db = require('../database/database') // get an instance of database


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

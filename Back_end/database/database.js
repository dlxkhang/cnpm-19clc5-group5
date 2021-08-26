const sqlite3 = require('sqlite3').verbose();
// setup database connection
var db = new sqlite3.Database('D:/Development/cnpm-19clc5-group5/Back_end/database/muzee.db', (err) => {
    if(err) {
        return console.log(err)
    }
    console.log('Connected to database')
})


module.exports = db

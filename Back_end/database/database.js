const sqlite3 = require('sqlite3').verbose();
// setup database connection
var db = new sqlite3.Database('muzee.db', (err) => {
    if(err) {
        return console.log(err)
    }
    console.log('Connected to database')
})


module.exports = db

const sqlite3 = require('sqlite3').verbose();
// setup database connection
var db = new sqlite3.Database('D:\\Study\\SecondYear\\Semester_3\\Introduction_to_Software_Engineering\\Project\\Test\\database\\muzee.db', (err) => {
    if(err) {
        return console.log(err)
    }
    console.log('Connected to database')
})


module.exports = db

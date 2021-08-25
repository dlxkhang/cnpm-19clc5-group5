const express = require('express');
const app = express();
const router = require('./router/server_router');

// express declarations
app.use(express.json()) // for parsing application/json
app.use(express.urlencoded({ extended: true })) // for parsing


var port = 3000;


app.use('/', router);


app.listen(port, "192.168.31.133", function() {
    console.log('Server listening on port ' + port);
});

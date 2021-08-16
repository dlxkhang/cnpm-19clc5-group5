
var express = require('express');
var app = express();
var PORT = 3000;
  
app.post('/', (req, res) => {
  res.send(req.body) // gui lai request body = http response
})

app.listen(PORT, function(err){
    if (err) console.log(err);
    console.log("Server listening on PORT", PORT);
}); 

// var request = require('request');
// function updateClient(postData){
//     var clientServerOptions = {
//         uri: 'http://localhost:3000/api/order_seller/accept',
//         body: JSON.stringify(postData),
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         }
//     }
//     request(clientServerOptions, function (error, response) {
//         console.log(error,response.body);
//         return;
//     });
// }

// updateClient('hello')
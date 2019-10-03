const express = require('express');
var app = express();

var PersonController = require('../controller/PersonInfoController');

module.exports = function () {
    app.post('/register',PersonController.RegisterPerson);
    app.post('/login',PersonController.LoginPerson);

    //app.get('/:section', loginController.get);
    // app.get('/image/:name', galleryController.image);
    // app.get('/original_image/:name', galleryController.original_image);


    return app;
}
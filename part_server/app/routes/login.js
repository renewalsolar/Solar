const express = require('express');
var app = express();

var loginController = require('../controller/loginController.js');

module.exports = function () {
    //app.post('/', upload.array('image', 1), galleryController.post);

    app.get('/:section', loginController.get);
    // app.get('/image/:name', galleryController.image);
    // app.get('/original_image/:name', galleryController.original_image);


    return app;
}
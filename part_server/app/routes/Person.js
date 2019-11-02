const express = require('express');
var app = express();

var PersonController = require('../controller/PersonController');

module.exports = function () {
    app.post('/register',PersonController.RegisterPerson);
    app.post('/login',PersonController.LoginPerson);

    app.post('/edit/:person_id',PersonController.EditPerson); // 회원 정보 수정
    app.get('/delete/:person_id',PersonController.deletePerson);


    //app.get('/:section', loginController.get);
    // app.get('/image/:name', galleryController.image);
    // app.get('/original_image/:name', galleryController.original_image);


    return app;
}
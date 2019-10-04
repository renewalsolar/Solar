const express = require('express');
var app = express();

var MapController = require('../controller/MapController');

module.exports = function () {
    app.get('/',MapController.get);

    return app;
}
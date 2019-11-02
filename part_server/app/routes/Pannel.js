const express = require('express');
var app = express();

var PannelController = require('../controller/PannelController');

module.exports = function () {
    app.post('/register',PannelController.RegisterPannel);
    app.post('/update/:pannel_id',PannelController.UpdatePannel); // 일일 누계 발전량
    app.post('/edit/:pannel_id',PannelController.EditPannel); // 판넬 정보 수정

    app.get('/info/:auth_id',PannelController.infoPannel);
    app.get('/info',PannelController.allInfoPannel);
    app.get('/delete/:panel_id',PannelController.deletePanel);


    return app;
}
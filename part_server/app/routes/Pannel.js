const express = require('express');
var app = express();

var PannelController = require('../controller/PannelController');

module.exports = function () {
    app.post('/register',PannelController.RegisterPannel);
    app.post('/update/:pannel_id',PannelController.UpdatePannel); // 일일 누계 발전량
    app.post('/edit/:pannel_id',PannelController.EditPannel); // 판넬 정보 수정

    app.get('/info/:auth_id',PannelController.infoPannel); // 개인 판넬 정보(개인 판넬 위치, 판넬 id, 최대 출력량)
    app.get('/personal_info/:auth_id',PannelController.PersonalPannel); // 개인 판넬 정보(개인 판넬 위치, 판넬 id, 최대 출력량)
    app.get('/personal_panels/:auth_id',PannelController.PersonalGraph);

    app.get('/info',PannelController.allInfoPannel); // 전체 판넬 정보(전체 판넬 위치, 최대 출력량)
    app.get('/delete/:panel_id',PannelController.deletePanel); // 판넬 삭제


    return app;
}
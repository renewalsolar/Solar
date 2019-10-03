var Pannel = require('../models/Pannel');

module.exports = {
    RegisterPannel: function (req, res, next) {

        var dayOutput_Array = [];
        var obj = new Object();

        obj.date = new Date().toISOString().substr(0, 10).replace('T', ' ');
        obj.output = "0";

        dayOutput_Array.push(obj);

        var newPannel = new Pannel({
            auth_id: req.body.auth_id,
            maxOutput: req.body.maxOutput,
            dayOutput: dayOutput_Array,
            lanx: req.body.lanx,
            lany: req.body.lany
        });

        newPannel.save(function (error, data) {
            if (error) {
                res.send({ success: false });
            }
            else {
                res.send({ success: true });
            }
        }); // DB 정보 저장
    },

    UpdatePannel: function (req, res, next) {

        Pannel.update({ _id: req.params.pannel_id }, {
            $set:{}
        });
    },
}
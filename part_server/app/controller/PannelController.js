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
            address: req.body.address
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
        var date = new Date().toISOString().substr(0, 10).replace('T', ' ');

        Pannel.find({"_id" : req.params.pannel_id, "dayOutput": { "$elemMatch": { "date": date } } }).exec(function (error, p) {
            if (p.length==0) {
                //dont find panel
                var obj = new Object();
                obj.date = new Date().toISOString().substr(0, 10).replace('T', ' ');
                obj.output = "0";

                Pannel.update({"_id" : req.params.pannel_id },{$push:{ dayOutput : obj}},function(error,data){
                    if(error){
                      console.log(error);
                    }else{
                      console.log(data);
                }});
                res.send({ success: success });
            }
            else {
                //find panel
                Pannel.updateOne({"_id" : req.params.pannel_id , "dayOutput": { "$elemMatch": { "date": date } } },
                {$set:{"dayOutput.$.output":req.body.output}},function(error,data){
                    if(error){
                      console.log(error);
                    }else{
                      console.log(data);
                }});
                res.send({ success: true });
            }
        });

    },

    infoPannel: function (req, res, next) {
        Pannel.find({ auth_id: req.params.auth_id }).sort({}).exec(function (error, p) {
            if (error) {
                console.log(error);
            }
            else {
                res.send(p);
            }
        })

    }



}
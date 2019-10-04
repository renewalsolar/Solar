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

        console.log(date);

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
                res.send({ success: true });
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
        Pannel.find({ auth_id: req.params.auth_id }).exec(function (error, p) {
            if (error) {
                console.log(error);
            }
            else {
                res.send(p);
            }
        })
    },

    //오늘 발전량 [{"dayOutput":[{"output":"3333"}],"address":"서울 강남구 압구정로 102 형지제2빌딩"},{"dayOutput":[{"output":"0"}],"address":"서울 서대문구 홍제동 285-14 "}]
    allInfoPannel: function (req, res, next) {

        var date = new Date().toISOString().substr(0, 10).replace('T', ' ');
        Pannel.find({dayOutput: { "$elemMatch": { date: date }}},{_id:0,address:1, dayOutput : {$slice :-1}, 'dayOutput.output': 1}).exec(function (error, p) {
            if (error) {
                console.log(error);
            }
            else {
                res.send(p);
            }
        })
    }


}
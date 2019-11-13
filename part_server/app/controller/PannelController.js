var Pannel = require('../models/Pannel');
var User = require('../models/Person');

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

        User.updateOne({ "id": req.body.auth_id },
            { $set: { "hasPV": true } }, function (error, data) {
                if (error) {
                    console.log(error);
                }
                else{
                    console.log(data); 
                }
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

        Pannel.find({ "_id": req.params.pannel_id, "dayOutput": { "$elemMatch": { "date": date } } }).exec(function (error, p) {
            if (p.length == 0) {
                //dont find panel
                var obj = new Object();
                obj.date = new Date().toISOString().substr(0, 10).replace('T', ' ');
                obj.output = "0";

                Pannel.update({ "_id": req.params.pannel_id }, { $push: { dayOutput: obj } }, function (error, data) {
                    if (error) {
                        console.log(error);
                    } else {
                        console.log(data);
                    }
                });
                res.send({ success: true });
            }
            else {
                //find panel
                Pannel.findOne({ "_id": req.params.pannel_id, "dayOutput": { "$elemMatch": { "date": date } }},function(error, res){
                    console.log(res);
                });

                Pannel.updateOne({ "_id": req.params.pannel_id, "dayOutput": { "$elemMatch": { "date": date } } },
                    { $set: { "dayOutput.$.output": req.body.output } }, function (error, data) {
                        if (error) {
                            console.log(error);
                        } else {
                            console.log(data);
                        }
                    });
                res.send({ success: true });
            }
        });
    },

    //판넬 정보 수정
    EditPannel: function (req, res, next) {
        Pannel.updateOne({ "_id": req.params.pannel_id },
            { $set: { "maxOutput": req.body.maxOutput, "address": req.body.address } }, function (error, data) {
                if (error) {
                    console.log(error);
                } else {
                    console.log(data);
                }
            });
        res.send({ success: true });

    },

    //사용자 개인 판넬 조회 판넬id, 주소, 최대출력량
    infoPannel: function (req, res, next) {
        Pannel.find({ auth_id: req.params.auth_id }, { _id: 1, address: 1, maxOutput: 1 }).exec(function (error, p) {
            if (error) {
                console.log(error);
            }
            else {
                res.send(p);
            }
        })
    },

    //개인별 일일 발전량
    PersonalPannel: function (req, res, next) {
        var date = new Date().toISOString().substr(0, 10).replace('T', ' ');
        Pannel.find({ auth_id: req.params.auth_id, dayOutput: { "$elemMatch": { date: date } } }, { dayOutput: { $slice: -1 }, 'dayOutput.output': 1 }).exec(function (error, p) {
            if (error) {
                console.log(error);
            }
            else {
                res.send(p);
            }
        })
    },

    //그래프 최근 30일
    PersonalGraph: function (req, res, next) {
        var date = new Date().toISOString().substr(0, 10).replace('T', ' ');
        Pannel.find({ auth_id: req.params.auth_id }, { dayOutput: { $slice: -30 }, 'dayOutput.output': 1 }).exec(function (error, p) {
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
        Pannel.find({ dayOutput: { "$elemMatch": { date: date } } }, { _id: 0, address: 1, maxOutput: 1, dayOutput: { $slice: -1 }, 'dayOutput.output': 1 }).exec(function (error, p) {
            if (error) {
                console.log(error);
            }
            else {
                res.send(p);
            }
        })
    },

    deletePanel: function (req, res, next) {
        Pannel.findOne({ _id: req.params.panel_id }).exec(function (error, p) {

            if (p != null) {
                Pannel.findOne({ auth_id: p.auth_id }).count().exec(function (error2, cnt) {
                    console.log(cnt);

                    if (cnt <= 1) {
                        User.updateOne({ "id": p.auth_id },
                        { $set: { "hasPV": false } }, function (error, data) {
                            if (error) {
                                console.log(error);
                            }else{
                                console.log(data); 
                            }
                        });
                    }

                    Pannel.deleteOne({ _id: req.params.panel_id }, function (error, data) {
                        if (error) {
                            console.log(error);
                        } else{
                            console.log(data); 
                        }
                    });
                });
            }
        });
        res.send({ success: true });
    }
}
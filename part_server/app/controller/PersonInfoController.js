var Person = require('../models/Person');

module.exports = {
    RegisterPerson: function (req, res, next) {

        Person.findOne({ id: req.body.id }, function (error, p) {
            if (p == null) {
                var newPerson = new Person({
                    name: req.body.name,
                    id: req.body.id,
                    password: req.body.password,
                    address: req.body.address,
                    hasPV: false
                });
                newPerson.save(function (error, data) {
                    if (error) {
                        res.send({ success: false });
                    }
                    else {
                        res.send({ success: true });
                    }
                }); // DB 정보 저장
            }

            else {
                res.send({ message: "중복 된 id입니다", success: false });
            }
        });
    },

    LoginPerson: function (req, res, next) {

        Person.findOne({ id: req.body.id, password: req.body.password }, function (error, Person) {
            if (Person == null) {
                res.send({ message: "아이디와 비밀번호를 확인해주세요", success: false, hasPV: false});
            }
            else {
                res.send({ message: "Authorized", success: true, name:Person.name ,hasPV: Person.hasPV });
            }
        });
    },
}
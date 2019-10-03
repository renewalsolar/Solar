var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PannelSchema = new Schema({
    auth_id: String, //주인

    maxOutput:String,
    dayOutput:[{
        date: String,
        output: String
    }],
    
    lanx:String,
    lany:String
});

module.exports = mongoose.model('pannel', PannelSchema);
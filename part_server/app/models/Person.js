var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var PersonSchema = new Schema({
    name: String,
    id: String,
    password: String,
    address: String,
    hasPV: Boolean
});

module.exports = mongoose.model('person', PersonSchema);
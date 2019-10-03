var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var loginSchema = new Schema({
    name: String,
    password: String
});

module.exports = mongoose.model('login', loginSchema);
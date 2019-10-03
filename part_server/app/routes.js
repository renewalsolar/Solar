var PersonRouter = require('./routes/Person');
var PannelRouter = require('./routes/Pannel')

module.exports = function(app){
    app.use('/api/person', PersonRouter());
    app.use('/api/pannel', PannelRouter());
}
var PersonRouter = require('./routes/Person');
var PannelRouter = require('./routes/Pannel');
var mapRouter = require('./routes/Map');

module.exports = function (app) {
    app.use('/api/person', PersonRouter());
    app.use('/api/pannel', PannelRouter());
    app.use('/api/map', mapRouter());
}
var PersonRouter = require('./routes/Person');

module.exports = function(app){
    app.use('/api/person', PersonRouter());
}
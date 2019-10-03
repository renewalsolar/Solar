var loginRouter = require('./routes/login');

module.exports = function(app){
    app.use('/api/login', loginRouter());
}
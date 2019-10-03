var express        = require('express');
var app            = express();
var mongoose       = require('mongoose');
var bodyParser     = require('body-parser');
var methodOverride = require('method-override');
var logger         = require('morgan');
var path           = require('path');
var cors           = require('cors');

var app = express();
var port = process.env.PORT || 3001; // set PORT

mongoose.Promise = global.Promise;
mongoose.set('useUnifiedTopology', true);

const mongoDB = 'mongodb://127.0.0.1:27017/solar' // 호스트:포트/DB명
const promise = mongoose.connect(mongoDB, {
  useNewUrlParser: true 
 // useMongoClient: true 
});

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(cors());
app.use(logger('tiny'));
app.use(bodyParser.json());
app.use(bodyParser.json({ type: 'application/vnd.api+json' })); // parse application/vnd.api+json as json
app.use(bodyParser.urlencoded({ extended: true })); // parse application/x-www-frorm-urlencoded
app.use(methodOverride('X-HTTP-Method-Override')); // override with the X-HTTP-Method-Override header in the request. simulate DELETE/PUT


//app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'uploads')));

var server = app.listen(port);
server.timeout = 5000;
var io = require('socket.io')(server);

require('./app/routes')(app);
app.set('socketio',io);

console.log('SERVER ON');
exports = module.exports = app;
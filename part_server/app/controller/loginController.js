var Gallery = require('../models/login');

module.exports = {

    get: function (req, res, next) {
        Gallery.find({ section: req.params.section }).sort({}).exec(function (error, Gallery) {
            if (error) {
                console.log(error);
            }
            else {
                res.send(Gallery);
            }
        })
    }

   
}
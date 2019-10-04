const fs = require('fs');

module.exports = { 
    get: function (req, res) {
        res.render('api.php');
        // fs.exists(path.main_path + path.splash_path + req.params.name, function (exists) {
        //     if (exists) {
        //         fs.readFile(path.main_path + path.splash_path + req.params.name, function (err, data) {
        //             res.end(data);
        //         });
        //     } else {
        //         fs.readFile(path.main_path + path.splash_path + '/no_image.png', function (err, data) {
        //             res.end(data);
        //         });
        //     }
        // });
    }

}
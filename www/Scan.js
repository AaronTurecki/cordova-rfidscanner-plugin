//////////////////////////////////////////
// Scan.js
//
//////////////////////////////////////////
var exec = require('cordova/exec');

var Scan =
{
    read : function( success, error )
    {
        exec(success, error, "Scan", "read", [])
    }
}

module.exports = Scan;

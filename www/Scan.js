//////////////////////////////////////////
// Scan.js
//
//////////////////////////////////////////
var exec = require('cordova/exec');

var Scan =
{
    scan : function( success, error )
    {
        exec(success, error, "Scan", "scan", [])
    }
}

module.exports = Scan;

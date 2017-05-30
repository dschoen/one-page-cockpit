auth.factory('authService',
		function (Base64, $http, $rootScope, CONFIG, messageService) {
		 
	var authService = {};
	
	authService.user = {
			username: null,
			password: null,
			authdata: null,
			secret:null,
	};
	authService.observers = [];
	 
	// ----------------------------------------------------------
	
    authService.login = function (username, password) {

    	var pend = messageService.addPending("Login");
    	
    	var base = Base64.encode(username+":"+password);
    	var data = { auth: base };
    	
    	return $http({
			  method: 'POST',
			  url: CONFIG.BACKEND_URL + "/login",
			  data: data,
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
											
				//set Credentials
				authService.setCredentials(username, password);
				authService.user.secret = response.data.secret;
				
				// set overall auth Token
				$http.defaults.headers.common.Authorization = authService.user.secret;
				
				// close Pending
				messageService.closePending(pend);
				
				// call Observers init
				angular.forEach(authService.observers, function(callback){
				      callback.callback();
				});
				
			}, function errorCallback(response) {
			    messageService.addAlert("danger", "Authentication failed.");
			    console.log("Authentication failed");
			    messageService.closePending(pend);
			    return null;
			  });
    };

    // ----------------------------------------------------------
    
    authService.setCredentials = function (username, password) {
        var authdata = Base64.encode(username + ':' + password);
        
        authService.user.username = username;
        authService.user.authdata =  authdata;

        $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
    };

    // ----------------------------------------------------------
    
    authService.clearCredentials = function () {
        $http.defaults.headers.common.Authorization = 'Basic ';
    };

    // ----------------------------------------------------------
    
    authService.isAuthenticated = function(callback) {
    	if (authService.user.authdata) {    		
    		return true;
    	} else {
    		return false;
    	}
    }
    
    // ----------------------------------------------------------
    
    authService.registerObserver = function(callback) {
		authService.observers.push(callback);
	} 
    
    // ----------------------------------------------------------
    
    return authService;
})

// ----------------------------------------------------------------------------------------------

.factory('Base64', function () {
/* jshint ignore:start */

var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';

return {
    encode: function (input) {
        var output = "";
        var chr1, chr2, chr3 = "";
        var enc1, enc2, enc3, enc4 = "";
        var i = 0;

        do {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);

            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;

            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }

            output = output +
                keyStr.charAt(enc1) +
                keyStr.charAt(enc2) +
                keyStr.charAt(enc3) +
                keyStr.charAt(enc4);
            chr1 = chr2 = chr3 = "";
            enc1 = enc2 = enc3 = enc4 = "";
        } while (i < input.length);

        return output;
    },

    decode: function (input) {
        var output = "";
        var chr1, chr2, chr3 = "";
        var enc1, enc2, enc3, enc4 = "";
        var i = 0;

        // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
        var base64test = /[^A-Za-z0-9\+\/\=]/g;
        if (base64test.exec(input)) {
            window.alert("There were invalid base64 characters in the input text.\n" +
                "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                "Expect errors in decoding.");
        }
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

        do {
            enc1 = keyStr.indexOf(input.charAt(i++));
            enc2 = keyStr.indexOf(input.charAt(i++));
            enc3 = keyStr.indexOf(input.charAt(i++));
            enc4 = keyStr.indexOf(input.charAt(i++));

            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;

            output = output + String.fromCharCode(chr1);

            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }

            chr1 = chr2 = chr3 = "";
            enc1 = enc2 = enc3 = enc4 = "";

        } while (i < input.length);

        return output;
    }
};

/* jshint ignore:end */
});
auth.controller('authController', function($scope, authService, messageService) {
		
	var ctrl = this;	//remember controller	
	
	// --- Methods ----------------------------------------------
	
	// reset login status
    authService.clearCredentials();

    // ----------------------------------------------------------
    
    $scope.login = function() {
    	
        authService.login($scope.username, $scope.password).then( function() {
        	
        	// update authentication status
        	$scope.authenticated = authService.isAuthenticated();
        	
        });
    };
    
    // ----------------------------------------------------------
    
    $scope.logout = function() {
    	
    	//TODO implement
    	alert("logout");
    };
	
    // -----------------------------------------------------------
    
    $scope.authenticated = authService.isAuthenticated();
    $scope.data = {};
    $scope.data.user = authService.user;
    $scope.username = null;
    $scope.password = null;
		
    return ctrl;    
});

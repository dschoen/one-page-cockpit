message.controller('messageController', function($scope, messageService) {
		
		// --- Alerts ---
		$scope.alerts = messageService.alerts;
		
		// -- Pendings ---
		
		$scope.pendings = messageService.pendings;   	    	    
	    
		// --- Methods ---
	    
	    $scope.closeAlert = function(index) {
	    	messageService.closeAlert(index);
	    }
});
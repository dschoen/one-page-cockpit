
// create the controller and inject Angular's $scope
memo.controller('memoController', function($scope, memoService, messageService) {
		
	var ctrl = this;	//remember controller
	
	// --- Methods ----------------------------------------------
	
	ctrl.getMemos = function() {		
		
		memoService.getMemos().then( function(data) {
			$scope.memos = data;			
		});		
	}
	
	// ----------------------------------------------------------
	
	ctrl.addMemo = function() {
		//TOFO
	}
	
	ctrl.getMemos();
	
    return ctrl;    
});

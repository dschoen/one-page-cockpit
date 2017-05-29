
// create the controller and inject Angular's $scope
memo.controller('memoController', function($scope, memoService, messageService, $window, authService) {
		
	var ctrl = this;	//remember controller
	
	// --- Methods ----------------------------------------------
	
	ctrl.getMemos = function() {		
		
		if(!authService.isAuthenticated()) {							
			return;
		}
		
		memoService.getMemos().then( function(data) {
			$scope.memos = data;			
		});		
	}
	
	// ----------------------------------------------------------
	
	$scope.openMemoFormNew = function() {
		$scope.data.showMemoOverlay = "overlay-visible";
		$scope.data.memo = memoService.getMemoTemplate();
	};
	
	// ----------------------------------------------------------
	
	$scope.cancelMemoForm = function() {
		$scope.data.showMemoOverlay = "overlay-hidden";
		// reset Template
		$scope.data.memo = memoService.getMemoTemplate();
	};
	
	// ----------------------------------------------------------
	
	$scope.openMemoFormEdit = function(memo) {
		$scope.data.showMemoOverlay = "overlay-visible";
		
		// Make a Copy of the chosen Card
		$scope.data.memo = JSON.parse(JSON.stringify(memo));
	};
	
	// ----------------------------------------------------------
	
	$scope.submitMemoForm = function() {
		$scope.data.showMemoOverlay = "overlay-hidden";
		
		memoService.addOrEditMemo($scope.data.memo).then( function() {
			
			// reset memo in scope
			$scope.data.memo = memoService.getMemoTemplate();
			// Reload all memos
			ctrl.getMemos();
		});
	};
	
	// ----------------------------------------------------------
	
	$scope.deleteMemo = function(memo) {
		memoService.deleteMemo(memo).then( function() {
			ctrl.getMemos();
		});
	};
	
	// ----------------------------------------------------------
	
	$scope.printMemo = function(divName) {
		var style1 = '<link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />';
		var style2 = '<link rel="stylesheet" href="https:///netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css" />';
		var style3 = '<link rel="stylesheet" type="text/css" href="assets/css/memo-print.css" />';

		
		var printContents = document.getElementById(divName).innerHTML;
		var popupWin = window.open('', '_blank', 'width=1000,height=1000');
		popupWin.document.open();
		popupWin.document.write('<html><head>'+style1+style2+style3+'</head><body onload="window.print()">' + printContents + '</body></html>');
		popupWin.document.close();
		
	}
	
	// ----------------------------------------------------------
	
	ctrl.callback = function() {
		ctrl.getMemos();
	}
	
	// ----------------------------------------------------------
	
	$scope.data = {};
	$scope.data.showMemoOverlay = 'overlay-hidden';	
	$scope.data.memo 			= memoService.getMemoTemplate();
	
	// ----------------------------------------------------------
	
	ctrl.init = function() {
		//register callback
		authService.registerObserver(ctrl);
	}
	
	// ----------------------------------------------------------
	
	ctrl.init();
	
    return ctrl;    
});

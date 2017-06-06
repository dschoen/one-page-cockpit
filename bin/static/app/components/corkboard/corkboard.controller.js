corkboard.controller('corkboardController', function($scope, corkboardService, messageService, CONFIG, authService) {
		
	var ctrl = this;	//remember controller
	
	// --- Methods ----------------------------------------------

	ctrl.getBoards = function() {		
		
		if(!authService.isAuthenticated()) {								
			return;
		} 
		
		corkboardService.getBoards().then( function(data) {
			$scope.data.boards = data;
			$scope.data.currentBoard = corkboardService.currentBoard;			
		});		
	}
	
	// --- Methods ----------------------------------------------

	ctrl.getBoard = function() {		
		
		// TODO	
	}
	
	// ----------------------------------------------------------
	
	$scope.openCardFormNew = function() {
		$scope.data.showCardOverlay = "overlay-visible";
		$scope.data.card = corkboardService.getCardTemplate();
	};
	
	// ----------------------------------------------------------
	
	$scope.openBoardFormNew = function() {
		
		// TODO
		alert("New Board");
	};
	
	// ----------------------------------------------------------
	
	$scope.openCardFormEdit = function(card) {
		$scope.data.showOverlay = "overlay-visible";
		
		// Make a Copy of the chosen Card
		$scope.data.card = JSON.parse(JSON.stringify(card));
		// parse Dates
		$scope.data.card.startdate = ($scope.data.card.startdate != null ? new Date ($scope.data.card.startdate) : null);
		$scope.data.card.enddate = ($scope.data.card.enddate != null ? new Date ($scope.data.card.enddate) : null);
	};

	// ----------------------------------------------------------
	
	$scope.submitCardForm = function() {
		$scope.data.showCardOverlay = "overlay-hidden";
		
		corkboardService.addOrEditCard($scope.data.card).then( function() {
			
			// reset card in scope
			$scope.data.card = corkboardService.getCardTemplate();
			// Reload all Cards
			ctrl.getBoard();
		});
	};

	// ----------------------------------------------------------
	
	$scope.cancelCardForm = function() {
		$scope.data.showOverlay = "overlay-hidden";
		// reset Template
		$scope.data.card = corkboardService.getCardTemplate();
	};
	
	// ----------------------------------------------------------
	
	$scope.deleteCard = function(card) {
		corkboardService.deleteCard(card).then( function() {
			ctrl.getCards();
		});
	};
	
	// ----------------------------------------------------------
	
	$scope.printCard = function(divName) {
		// TODO
	}
	
	// ----------------------------------------------------------
	
	ctrl.callback = function() {
		ctrl.getBoards();
	}
	
	// ----------------------------------------------------------
	
	$scope.data = {};
	$scope.data.today 				= new Date().toISOString();
	$scope.data.showCardOverlay 	= 'overlay-hidden';	
	$scope.data.showBoardOverlay 	= 'overlay-hidden';	
	$scope.data.card 				= corkboardService.getCardTemplate;
	$scope.data.efforts 			= CONFIG.EFFORTS;
	
	// ----------------------------------------------------------
	
	ctrl.init = function() {
		//register callback
		authService.registerObserver(ctrl);
	}	
	
	// ----------------------------------------------------------
	
	ctrl. init();
	
    return ctrl;    
});

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
			$scope.data.currentCards = corkboardService.currentCards;
		});		
	}
	
	// --- Methods ----------------------------------------------

	ctrl.updateCurrentBoard = function() {
		
		corkboardService.updateCurrentBoard().then( function(data) {
			$scope.data.currentBoard = corkboardService.currentBoard;
			$scope.data.currentCards = corkboardService.currentCards;
		});	
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
		$scope.data.showCardOverlay = "overlay-visible";
		
		// Make a Copy of the chosen Card
		$scope.data.card = JSON.parse(JSON.stringify(card));
		
		// create real Date Objects for Form
		$scope.data.card.startDate = ($scope.data.card.startDate ? new Date($scope.data.card.startDate) : null );
		$scope.data.card.endDate = ($scope.data.card.endDate ? new Date($scope.data.card.endDate) : null);
	};

	// ----------------------------------------------------------
	
	$scope.submitCardForm = function() {
		$scope.data.showCardOverlay = "overlay-hidden";
		
		corkboardService.addOrEditCard($scope.data.card).then( function() {
			
			// reset card in scope
			$scope.data.card = corkboardService.getCardTemplate();
			// Reload current Board
			ctrl.updateCurrentBoard();
		});
	};

	// ----------------------------------------------------------
	
	$scope.cancelCardForm = function() {
		$scope.data.showCardOverlay = "overlay-hidden";
		// reset Template
		$scope.data.card = corkboardService.getCardTemplate();
	};
	
	// ----------------------------------------------------------
	
	$scope.deleteCard = function(card) {
		corkboardService.deleteCard(card).then( function() {
			ctrl.updateCurrentBoard();
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
	$scope.data.today 				= new Date();
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
corkboard.controller('corkboardController', function($scope, corkboardService, messageService, CONFIG) {
		
	var ctrl = this;	//remember controller
	
	// --- Methods ----------------------------------------------

	ctrl.getCards = function() {		
		
		corkboardService.getCards().then( function(data) {
			$scope.data.cards = data;
		});		
	}		
	
	ctrl.getCards();
	
	// ----------------------------------------------------------
	
	$scope.openCardFormNew = function() {
		$scope.data.showOverlay = "overlay-visible";
		$scope.data.card = corkboardService.getCardTemplate();
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
		$scope.data.showOverlay = "overlay-hidden";
		
		corkboardService.addOrEditCard($scope.data.card).then( function() {
			
			// reset card in scope
			$scope.data.card = corkboardService.getCardTemplate();
			// Reload all Cards
			ctrl.getCards();
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
	
	
	$scope.data = {};
	$scope.data.today 		= new Date().toISOString();
	$scope.data.showOverlay = 'overlay-hidden';	
	$scope.data.card 		= corkboardService.getCardTemplate;
	$scope.data.prios 		= CONFIG.PRIORITIES;
	$scope.data.cats 		= CONFIG.CATEGORIES;
	$scope.data.efforts 	= CONFIG.EFFORTS;
	
    return ctrl;    
});

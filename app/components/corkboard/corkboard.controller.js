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
	
	$scope.openCardFormEdit = function(card) {
		$scope.data.showOverlay = "overlay-visible";
		
		$scope.data.card = card;
		$scope.data.card.startdate = new Date($scope.data.card.startdate);
		$scope.data.card.enddate = new Date($scope.data.card.enddate);
	};

	$scope.submitCardForm = function() {
		$scope.data.showOverlay = "overlay-hidden";
		
		$scope.data.card.startdate = $scope.data.card.startdate.toISOString().substr(0, 10);
		$scope.data.card.enddate = $scope.data.card.enddate.toISOString().substr(0, 10);
		
		corkboardService.addOrEditCard($scope.data.card).then( function() {
			ctrl.getCards();
		});
	};

	$scope.cancelCardForm = function() {
		$scope.data.showOverlay = "overlay-hidden";
		$scope.data.card = corkboardService.getCardTemplate();
	};
	
	$scope.deleteCard = function(card) {
		corkboardService.deleteCard(card).then( function() {
			ctrl.getCards();
		});
	};
	
	// ----------------------------------------------------------
	
	
	$scope.data = {};
	$scope.data.today 		= new Date().toISOString().substr(0, 10);;
	$scope.data.showOverlay = 'overlay-hidden';	
	$scope.data.card 		= corkboardService.cardTemplate;
	$scope.data.prios 		= CONFIG.PRIORITIES;
	$scope.data.cats 		= CONFIG.CATEGORIES;
	$scope.data.efforts 	= CONFIG.EFFORTS;
	
    return ctrl;    
});

corkboard.controller('corkboardController', function($scope, corkboardService, messageService) {
		
	var ctrl = this;	//remember controller
	
	ctrl.card = {
			"id": '',
			"title": 'test',
			"text": '',
			"status": '',
			"priority": '',
			"category": ''	
	};
	
	// --- Methods ----------------------------------------------

	ctrl.getCards = function() {		
		
		corkboardService.getCards().then( function(data) {
			$scope.cards = data;
		});		
	}		
	
	ctrl.getCards();
	
	// ----------------------------------------------------------
	
	$scope.openCardForm = function() {
		$scope.showOverlay = "overlay-visible";
		console.log("Open Form.");
	};
	
	$scope.openCardFormEdit = function(card) {
		$scope.showOverlay = "overlay-visible";
		$scope.card = card;
		console.log("Open Form.");
	};

	$scope.submitCardForm = function() {
		$scope.showOverlay = "overlay-hidden";
		
		ctrl.card = $scope.card;	
		console.log(JSON.stringify(ctrl.card));
		corkboardService.addOrEditCard(ctrl.card);
	};

	$scope.cancelCardForm = function() {
		$scope.showOverlay = "overlay-hidden";
	};
	
	// ----------------------------------------------------------
	$scope.prios = corkboardService.prios;
	$scope.cats = corkboardService.cats;
	
	$scope.showOverlay = 'overlay-hidden';
	
	$scope.card = ctrl.card;
	
    return ctrl;    
});

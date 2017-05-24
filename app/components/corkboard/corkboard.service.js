corkboard.factory('corkboardService', function ($http, CONFIG, messageService) {
		
	corkboardService = this;
	
	corkboardService.cards = [];	
	
	// ------------------------------------------
	
	corkboardService.getCards = function() {
		
		var pend = messageService.addPending("Cards")
		
		return $http({
			  method: 'GET',
			  url: CONFIG.BACKEND_URL + '/cards',
			  data: '',
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				// update List of Cards
				corkboardService.cards = response.data;
				
				messageService.closePending(pend);					
				return corkboardService.sort(corkboardService.cards);
				
			}, function errorCallback(response) {
			    messageService.addAlert("danger", "Cannot read Cards.");
			    console.log("Cannot read Cards");
			    messageService.closePending(pend);
			    return null;
			  });		
	}
    
	// ------------------------------------------
	
	corkboardService.moveCard = function(id, priority, category) {

		console.log("moveCard " + id);
		
		// get Data
		var card;
		for(var i = 0; i < corkboardService.cards.length; i++) {			
			card = corkboardService.cards[i];
			if (card.id == id) {
				
				// edit Card data
				card.priority = priority;
				card.category = category;
				
				// update Card List
				corkboardService.cards[i] = card;
				break;			
			}
		}				
				
		// update remote Card data		
		return $http({
			  method: 'PUT',
			  url: CONFIG.BACKEND_URL + '/cards/' + id,
			  data: angular.toJson(card),
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				messageService.addAlert("success", "Card moved.");
				
			}, function errorCallback(response) {
				messageService.addAlert("danger", "Cannot update Card");				
			    return null;
			});			
	}
	
	// ------------------------------------------
	
	corkboardService.addOrEditCard = function(card) {

		console.log("addOrEditCard " + card.title);	
		console.log(JSON.stringify(card));
		
		var http_method = (card.id > 0 ? 'PUT' : 'POST');		
		
		// add remote Card data		
		return $http({
			  method: http_method,
			  url: CONFIG.BACKEND_URL + '/cards/',
			  data: angular.toJson(card),
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {				
				messageService.addAlert("success", "Card saved.");
				return;
				
			}, function errorCallback(response) {
				messageService.addAlert("danger", "Cannot add Card");				
			    return null;
			});			
	}
	
	// ------------------------------------------
	
	corkboardService.deleteCard = function(card) {

		console.log("deleteCard " + card.id);
				
		// add remote Card data		
		return $http({
			  method: 'DELETE',
			  url: CONFIG.BACKEND_URL + '/cards/' + card.id,
			  data: '',
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				messageService.addAlert("success", "Card deleted.");
				return;
				
			}, function errorCallback(response) {
				messageService.addAlert("danger", "Cannot delete Card");				
			    return null;
			});			
	}
	
	// ------------------------------------------
	
	corkboardService.sort = function(cards) {
		
		var grid = {};
		var priorities = CONFIG.PRIORITIES;
		var categories = CONFIG.CATEGORIES;
		
				
		// set up grid
		for (var i=0; i<priorities.length; i++  ){
			for (var j=0; j<categories.length; j++  ){				
				var field = priorities[i]+"-"+categories[j];				
				grid[field] = new Array();				
			}			
		}		
				
		// sort Cards into grid
		for(var i = 0; i < cards.length; i++) {			
			var card = cards[i];			
			var field = card.priority+"-"+card.category;			
			grid[ field ].push(card);
		}
		
		return grid;
	}
	
    // ------------------------------------------
    
	corkboardService.getCardTemplate = function() {
		
		let createDate = new Date().toISOString().substr(0, 10);
		
		var cardTemplate = {
				"id": '0',
				"title": '',
				"text": '',
				"status": 'none',
				"priority": 'medium',
				"category": 'inProgress',
				"effort" : 'normal',				
				"createdate": createDate,
				"startdate": '',
				"enddate": ''
		};
		
		return cardTemplate;
	}
	
	// ------------------------------------------
	
    return corkboardService;
});
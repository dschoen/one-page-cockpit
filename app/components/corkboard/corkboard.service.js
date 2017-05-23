corkboard.factory('corkboardService', function ($http, CONFIG, messageService) {
		
	corkboardService = this;
	
	corkboardService.cards = [];	
	corkboardService.prios = ["high", "medium", "low"];
	corkboardService.cats = ["backlog", "inProgress", "todo"];
	
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
			  data: JSON.stringify(card),
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

		console.log("addCard " + card.title);	
		console.log(JSON.stringify(card));
				
		// add remote Card data		
		return $http({
			  method: 'POST',
			  url: CONFIG.BACKEND_URL + '/cards/',
			  data: JSON.stringify(card),
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				messageService.addAlert("success", "Card saved.");
				
			}, function errorCallback(response) {
				messageService.addAlert("danger", "Cannot add Card");				
			    return null;
			});			
	}
	
	// ------------------------------------------
	
	corkboardService.sort = function(cards) {
		
		var data = ["high", "medium", "low"];
		data["high"] = new Array("backend", "inProgress", "todo");
		data["medium"] = new Array("backend", "inProgress", "todo");
		data["low"] = new Array ("backend", "inProgress", "todo");
		
		
		for(var i = 0; i < cards.length; i++) {			
			var card = cards[i];
			
			data[ card.priority ][ card.category ] = new Array();
			data[ card.priority ][ card.category ].push(card);
		}
		
		return data;
	}
	
    // ------------------------------------------
    
    return corkboardService;
});
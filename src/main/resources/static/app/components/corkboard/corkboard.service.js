corkboard.factory('corkboardService', function ($http, $rootScope, CONFIG, messageService, authService) {
		
	corkboardService = this;
	
	corkboardService.boards = [];
	corkboardService.cards = [];
	corkboardService.currentBoard = {};
	
	// ------------------------------------------
	
	corkboardService.getBoards = function() {
		
		var pend = messageService.addPending("Boards")
		
		return $http({
			  method: 'GET',
			  url: CONFIG.BACKEND_URL + '/user/' + authService.user.userId + '/boards',
			  data: '',
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				// update List of Boards
				corkboardService.boards = response.data;
				
				// set active Board
				// TODO check if there is at least one
				corkboardService.currentBoard = response.data[0];				
				
				messageService.closePending(pend);
				return corkboardService.boards;				
				
			}, function errorCallback(response) {
			    messageService.addAlert("danger", "Cannot read Boards.");
			    console.log("Cannot read Boards");
			    messageService.closePending(pend);
			    return null;
			  });		
	}
	
	// ------------------------------------------
	
	corkboardService.getCards = function() {
		
		var pend = messageService.addPending("Cards")
		
		return $http({
			  method: 'GET',
			  url: CONFIG.BACKEND_URL + 'boards/'+corkboardService.currentBoard.boardId+'/cards',
			  data: '',
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				// update List of Cards
				corkboardService.cards = response.data;
				
				messageService.closePending(pend);					
				return corkboardService.prepareCards(corkboardService.cards);
				
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

		console.log("addOrEditCard: " + card.title);	
		console.log(JSON.stringify(card));
			
		
		// add enddatestring			
		if (card.endDate) {
			var date = new Date (card.enddate);
//			card.endDateString = date.getDate()+"."+(date.getMonth()+1)+"."+date.getFullYear();
		} else {
//			card.endDatestring = "";
		}	
		
		//set currentBoard
		card.boardId = corkboardService.currentBoard.boardId;
		
		// if new Request
		if (card.cardId == 0) {
			var http_method = 'POST';
			var http_url = CONFIG.BACKEND_URL + 'boards/'+corkboardService.currentBoard.boardId+'/cards/';
		} else {
			var http_method = 'PUT';
			var http_url = CONFIG.BACKEND_URL + 'boards/'+corkboardService.currentBoard.boardId+'/cards/';
		}
		
		// add remote Card data		
		return $http({
			  method: http_method,
			  url: http_url,
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
	
	corkboardService.prepareCards = function(cards) {
		
		var grid = {};
		var rows = sorkboardService.currentBoard.boardRows;
		var cols = sorkboardService.currentBoard.boardColumns;
		
				
		// set up grid
		for (var i=0; i<rows.length; i++  ){
			for (var j=0; j<cols.length; j++  ){
				let row = rows[i];
				let col = cols[j];				
				var field = row.name+"-"+col.name;				
				grid[field] = new Array();				
			}			
		}		
				
		// sort Cards into grid
		for(var i = 0; i < cards.length; i++) {			
			var card = cards[i];			
			var field = card.boardRow.name+"-"+card.boardColumn.name;			
			grid[ field ].push(card);
		}
		
		return grid;
	}
	
    // ------------------------------------------
    
	corkboardService.getCardTemplate = function() {
		
		var cardTemplate = {
				"cardId": '0',
				"title": '',
				"text": '',
				"status": 'none',
				"boardId": null,
				"boardRow": null,
				"boardRowId": null,
				"boardColumn": null,
				"boardColumnId": null,
				"effort" : 'normal',				
				"createDate": null,
				"startDate": null,
				"endDate": null,
				"endDateString": ''					
		};
		
		return cardTemplate;
	}
	
	// ------------------------------------------
	
    return corkboardService;
});
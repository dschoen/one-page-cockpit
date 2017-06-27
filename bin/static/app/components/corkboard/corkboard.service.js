corkboard.factory('corkboardService', function ($http, $rootScope, CONFIG, messageService, authService, $filter) {
		
	corkboardService = this;	
	corkboardService.boards = [];
	corkboardService.currentCards = [];
	corkboardService.currentBoard = {};
	
	// ------------------------------------------
	
	corkboardService.getBoards = function() {
		
		var pend = messageService.addPending("Boards")
		
		return $http({
			  method: 'GET',
			  url: CONFIG.BACKEND_URL + 'users/' + authService.user.userId + '/boards',
			  data: '',
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				// update List of Boards
				corkboardService.boards = response.data;
				
				// set active Board
				if (corkboardService.currentBoard.boardId == null) {
					corkboardService.currentBoard = response.data[0];
				}
				
				// generate current set of Cards
				corkboardService.currentCards = corkboardService.prepareCards(corkboardService.currentBoard.cards);
				
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
	
	corkboardService.updateCurrentBoard = function() {
		
		var pend = messageService.addPending("CurrentBoard")
		
		return $http({
			  method: 'GET',
			  url: CONFIG.BACKEND_URL + 'boards/'+corkboardService.currentBoard.boardId,
			  data: '',
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				// update current Board
				corkboardService.currentBoard = response.data;
				
				// generate current set of Cards
				corkboardService.currentCards = corkboardService.prepareCards(corkboardService.currentBoard.cards);
				
				messageService.closePending(pend);					
				return corkboardService.currentCards;
				
			}, function errorCallback(response) {
			    messageService.addAlert("danger", "Cannot read Cards.");
			    console.log("Cannot read Cards");
			    messageService.closePending(pend);
			    return null;
			});		
	}
    
	// ------------------------------------------
	
	corkboardService.moveCard = function(id, rowId, columnId) {
		
		// get Data
		var card;
		for(var i = 0; i < corkboardService.currentBoard.cards.length; i++) {			
			card = corkboardService.currentBoard.cards[i];
			
			if (card.cardId == id) {				
				// prepare Card data
				card.boardRowId 	= rowId;
				card.boardColumnId 	= columnId;
				card.boardRow 		= null;
				card.boardColumn	= null;			
				break;			
			}
		}
				
		// update remote Card data		
		return $http({
			  method: 'PUT',
			  url: CONFIG.BACKEND_URL + 'boards/'+corkboardService.currentBoard.boardId+'/cards/' + id,
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
		
	
		// prepare Card data
		card.boardId = corkboardService.currentBoard.boardId;
		card.boardRow 		= null;
		card.boardColumn	= null;
		card.createDate		= null;
		card.lastUpdate		= null;	
		
		// if new Request
		if (card.cardId == 0) {
			var http_method = 'POST';
			var http_url = CONFIG.BACKEND_URL + 'boards/'+corkboardService.currentBoard.boardId+'/cards/';
		} else {
			var http_method = 'PUT';
			var http_url = CONFIG.BACKEND_URL + 'boards/'+corkboardService.currentBoard.boardId+'/cards/'+card.cardId;
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
	
	corkboardService.addOrEditBoard = function(board) {

		console.log("addOrEditBoard: " + board.name);	
			
		// prepare Board data
		board.createDate		= null;
		board.lastUpdate		= null;
		board.cards				= null;
		
		// if new Request
		if (board.boardId > 1) {
			var http_method = 'PUT';
			var http_url = CONFIG.BACKEND_URL + 'users/'+authService.user.userId+'/boards/'+board.boardId;
		} else {
			var http_method = 'POST';
			var http_url = CONFIG.BACKEND_URL + 'users/'+authService.user.userId+'/boards';
		}
		
		// add remote Card data		
		return $http({
			  method: http_method,
			  url: http_url,
			  data: angular.toJson(board),
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {				
				messageService.addAlert("success", "Board saved.");
				return;
				
			}, function errorCallback(response) {
				messageService.addAlert("danger", "Cannot add Board");				
			    return null;
			});			
	}
	
	// ------------------------------------------
	
	corkboardService.deleteCard = function(card) {
				
		// add remote Card data		
		return $http({
			  method: 'DELETE',
			  url: CONFIG.BACKEND_URL + 'boards/'+corkboardService.currentBoard.boardId+'/cards/' + card.cardId,
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
		var rows = corkboardService.currentBoard.boardRows;
		var cols = corkboardService.currentBoard.boardColumns;
		
				
		// set up grid
		for (var i=0; i<rows.length; i++  ){
			for (var j=0; j<cols.length; j++  ){
				let row = rows[i];
				let col = cols[j];				
				var field = row.boardRowId+"-"+col.boardColumnId;				
				grid[field] = new Array();				
			}			
		}		
				
		// sort Cards into grid
		for(var i = 0; i < cards.length; i++) {			
			var card = cards[i];			
			var field = card.boardRow.boardRowId+"-"+card.boardColumn.boardColumnId;
			
			// prepare additional Fields
			card.endDateString = (card.endDate ? $filter('date')((card.endDate.epochSecond * 1000),'dd.MM.yyyy') : null);
			card.startDate = (card.startDate ? new Date(card.startDate.epochSecond * 1000) : null);
			card.endDate = (card.endDate ? new Date(card.endDate.epochSecond * 1000) : null);			
			card.boardColumnId = card.boardColumn.boardColumnId;
			card.boardRowId = card.boardRow.boardRowId;
			
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
				"boardRowId": corkboardService.currentBoard.boardRows[0].boardRowId,
				"boardColumn": null,
				"boardColumnId": corkboardService.currentBoard.boardColumns[0].boardColumnId,
				"effort" : 'normal',				
				"createDate": null,
				"startDate": null,
				"endDate": null,
				"endDateString": ''					
		};		
		return cardTemplate;
	}
	
	// ------------------------------------------
    
	corkboardService.getBoardTemplate = function() {
		
		var boardTemplate = {				
				"boardId": null,
				"name": '',
				"boardRows": [''],
				"boardColumns": [''],				
		};		
		return boardTemplate;
	}
	
	// ------------------------------------------
	
    return corkboardService;
});
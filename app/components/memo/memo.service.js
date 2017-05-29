memo.factory('memoService', function ($http, CONFIG, messageService) {
		 
	memoService = this;
	
	memoService.memos = [];

    
    // ------------------------------------------
	  
	memoService.getMemos = function() {
		
		var pend = messageService.addPending("Memos")
		
		return $http({
			  method: 'GET',
			  url: CONFIG.BACKEND_URL + '/memos',
			  data: '',
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				messageService.closePending(pend);					
				return response.data;
				
			}, function errorCallback(response) {
			    messageService.addAlert("danger", "Cannot read Memos.");
			    console.log("Cannot read Memos");
			    messageService.closePending(pend);
			    return null;
			  });		
	}
    
	// ------------------------------------------
	
	memoService.addOrEditMemo = function(memo) {

		console.log("addOrEditMemo: " + memo.title);	
		console.log(JSON.stringify(memo));	
		
		var http_method = (memo.id > 0 ? 'PUT' : 'POST');
		var http_url = (memo.id > 0 ? CONFIG.BACKEND_URL + '/memos/' + memo.id : CONFIG.BACKEND_URL + '/memos/');
		
		// set lastupdate
		memo.lastupdate = new Date();
		
		// add remote memo data		
		return $http({
			  method: http_method,
			  url: http_url,
			  data: angular.toJson(memo),
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {				
				messageService.addAlert("success", "Memo saved.");
				return;
				
			}, function errorCallback(response) {
				messageService.addAlert("danger", "Cannot add or edit Memo");				
			    return null;
			});			
	}
	
	// ------------------------------------------
	
	memoService.deleteMemo = function(memo) {

		console.log("deleteMemo " + memo.id);
				
		// add remote Card data		
		return $http({
			  method: 'DELETE',
			  url: CONFIG.BACKEND_URL + '/memos/' + memo.id,
			  data: '',
	          headers: {
	              "Content-Type": "application/json"
	          }
			}).then(function successCallback(response) {
				
				messageService.addAlert("success", "Memo deleted.");
				return;
				
			}, function errorCallback(response) {
				messageService.addAlert("danger", "Cannot delete Memo.");				
			    return null;
			});			
	}
	
	// ------------------------------------------
    
	memoService.getMemoTemplate = function() {
		
		let createDate = new Date();
		
		var memoTemplate = {
				"id": '0',
				"title": '',
				"text": '',				
				"createdate": createDate,
				"lastupdate": null	
		};
		
		return memoTemplate;
	}
	
    // ------------------------------------------
	
	
	
    return memoService;
});
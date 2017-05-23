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
    
    return memoService;
});
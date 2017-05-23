/**
 *  messageService 
 */

message.factory('messageService', function($rootScope, $timeout) {
    
	var messageService = {};
	
	messageService.id = 0;
    
    messageService.alerts = new Array();
    messageService.pendings = new Array();
    
    // -- Methods --------------------------------------

    /**
     * add alert
     */
    messageService.addAlert = function(type, msg) {
    	
    	messageService.alerts.push({'type': type, 'msg': msg});
    	
    	index = messageService.alerts.length-1;
    	
    	$timeout(function(){
    		messageService.alerts.splice(index, 1);
        }, 5000);
    	
    }

    // --------------------------------------------------
    
    /**
     * @depricated
     * close alert
     */
    messageService.closeAlert = function(index) {
    	messageService.alerts.splice(index, 1);
    }

    // --------------------------------------------------    
    
    /**
     * add pending
     */
    messageService.addPending = function(msg) {    	
    	
    	messageService.id++;   	
    	messageService.pendings.push({'id':messageService.id, 'msg':msg});   		
    	
    	return messageService.id;
    }

    // --------------------------------------------------
    
    /**
     * close alert
     */
    messageService.closePending = function(id) {
    	
    	for(var i = 0; i < messageService.pendings.length; i++) {    	    
    		var pending = messageService.pendings[i];    		
    	    if (pending.id == id) {
    	    	messageService.pendings.splice(i, 1);
    	    	return;
    	    }
    	}
    }
    
    // --------------------------------------------------
    
    return messageService;
 });
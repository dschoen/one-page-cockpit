onpaco.config(function($routeProvider) {
	
	$routeProvider	
		.when('/', {
		    templateUrl : 'index.html'
		})
	    .otherwise({
	    	redirectTo: "/"
	    });
});
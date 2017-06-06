corkboard.directive('draggable', function(corkboardService) {
    return function(scope, element) {
    	// this gives us the native JS object
        var el = element[0];

        el.draggable = true;

        el.addEventListener(
            'dragstart',
            function(e) {
                e.dataTransfer.effectAllowed = 'move';
                e.dataTransfer.setData('id', this.id);
                                
                this.classList.add('drag');
                return false;
            },
            false
        );

        el.addEventListener(
            'dragend',
            function(e) {
            	this.classList.remove('drag');
                return false;
            },
            false
        );
        return false;
	}
});

corkboard.directive('droppable', function() {
    return {
        scope: {},
        link: function(scope, element, attrs) {
            // again we need the native object
            var el = element[0];
            
            el.addEventListener(
            	    'dragover',
            	    function(e) {
            	        e.dataTransfer.dropEffect = 'move';
            	        // allows us to drop
            	        if (e.preventDefault) e.preventDefault();
            	        this.classList.add('drag-over');
            	        return false;
            	    },
            	    false
            	);
            el.addEventListener(
            	    'dragenter',
            	    function(e) {
            	        this.classList.add('drag-over');
            	        return false;
            	    },
            	    false
            	);

        	el.addEventListener(
        	    'dragleave',
        	    function(e) {
        	        this.classList.remove('drag-over');
        	        return false;
        	    },
        	    false
        	);
        	el.addEventListener(
    		    'drop',
    		    function(e) {
    		    	    		    	
    		        // Stops some browsers from redirecting.
    		        if (e.stopPropagation) e.stopPropagation();

    		        this.classList.remove('drag-over');

    		        // Append Card in new Field
    		        var item = document.getElementById(e.dataTransfer.getData('id'));
    		        this.appendChild(item);

    		        var id = e.dataTransfer.getData('id');
    		        var row = attrs.row;
    		        var col = attrs.column;
    		        
    		        console.log("Dropped Card: "+ id + " to " + row + "/" + col );
    		        
    		        // call Service to update Card data
    		        corkboardService.moveCard(id, row, col);
    		        
    		        return false;
    		    },
    		    false
    		);
        }
    }
});


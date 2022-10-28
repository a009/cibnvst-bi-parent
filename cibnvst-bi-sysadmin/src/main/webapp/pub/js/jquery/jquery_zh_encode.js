jQuery.param=function( a ) {
	var s = [ ];
	var encode=function(str){
		str=encodeURIComponent(encodeURIComponent(str));
		str=str.replace(/\+/g,"%u002B");
		return str;
	};
	function add( key, value ){
		s[ s.length ] = encode(key) + '=' + encode(value);
	};
	// If an array was passed in, assume that it is an array
	// of form elements
	if ( jQuery.isArray(a) || a.jquery )
		// Serialize the form elements
		jQuery.each( a, function(){
			add( this.name, this.value );
		});

	// Otherwise, assume that it's an object of key/value pairs
	else
		// Serialize the key/values
		for ( var j in a )
			// If the value is an array then the key names need to be repeated
			if ( jQuery.isArray(a[j]) )
				jQuery.each( a[j], function(){
					add( j, this );
				});
			else
				add( j, jQuery.isFunction(a[j]) ? a[j]() : a[j] );

	// Return the resulting serialization
	return s.join("&").replace(/%20/g, "+");
}
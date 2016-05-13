var bundle = new ResourceBundle();
var messages;

$.get("i18n", function(data)
{
	bundle.initialize(data);
});

function ResourceBundle()
{
	messages = [];
}

ResourceBundle.prototype.initialize = function(json)
{
	var objs = eval(json);
	
	for(var i=0; i<objs.length; i++)
	{
		var obj = objs[i];
		messages[obj.key] = obj.value;
	}
};

ResourceBundle.prototype.get = function(pKey, params){
	
	var msg = "";
	
	if(messages)
	{
		msg = messages[pKey];
		
		for(var i=1; i< params.length; i++)
		{
			var er = new RegExp("\\{"+ (i-1)+"\}", "g");
			msg = msg.replace(er, params[i]);
		}
		
	}
	
	return msg;
};


ResourceBundle.prototype.get = function(pKey){
	
	var msg = "";
	
	if(messages)
	{
		msg = messages[pKey];
	}
	
	return msg;
};
function loadImg(thislink, thislinkencoded, thisname, thisID){
		
    $(".vfm-zoom").html("<i class=\"fa fa-refresh fa-spin\"></i><img class=\"preimg\" src=\"vfm-thumb.php?thumb="+ thislink +"\" \/>");
    // remove extension from filename    
    // fileExtension = '.' + thisname.replace(/^.*\./, '');
    // thisname = thisname.replace(fileExtension, '');
    $("#zoomview .thumbtitle").html(thisname);
    $("#zoomview").data('id', thisID);
	
    var firstImg = $('.preimg');
    firstImg.css('display','none');
	
    $("#zoomview").modal();
	
    firstImg.one('load', function() {
        $(".vfm-zoom .fa-refresh").fadeOut();
        $(this).fadeIn();
        checkNextPrev(thisID);
		$(".vfmlink").attr("href", "download/"+thislinkencoded);
		}).each(function() {
		if(this.complete) $(this).load();
	});   
}

$(document).on('click', '.stalabel', function(){
    $(this).parent().toggleClass('aperto');
});


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
     /*   <?php if ($setUp->getConfig('enable_prettylinks') == true) { ?>
            $(".vfmlink").attr("href", "download/"+thislinkencoded);
/*        <?php } else { ?>
            $(".vfmlink").attr("href", "vfm-admin/vfm-downloader.php?q="+thislinkencoded);
        <?php 
        } ?>
*/        }).each(function() {
            if(this.complete) $(this).load();
    });   
}

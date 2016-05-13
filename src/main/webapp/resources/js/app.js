/*! modernizr 3.0.0-alpha.4 (Custom Build) | MIT *
 * http://modernizr.com/download/#-touchevents-prefixes-teststyles !*/
!function(e,n,t){function o(e,n){return typeof e===n}function s(){var e,n,t,s,a,i,r;for(var l in c){if(e=[],n=c[l],n.name&&(e.push(n.name.toLowerCase()),n.options&&n.options.aliases&&n.options.aliases.length))for(t=0;t<n.options.aliases.length;t++)e.push(n.options.aliases[t].toLowerCase());for(s=o(n.fn,"function")?n.fn():n.fn,a=0;a<e.length;a++)i=e[a],r=i.split("."),1===r.length?Modernizr[r[0]]=s:(!Modernizr[r[0]]||Modernizr[r[0]]instanceof Boolean||(Modernizr[r[0]]=new Boolean(Modernizr[r[0]])),Modernizr[r[0]][r[1]]=s),f.push((s?"":"no-")+r.join("-"))}}function a(e){var n=p.className,t=Modernizr._config.classPrefix||"";if(h&&(n=n.baseVal),Modernizr._config.enableJSClass){var o=new RegExp("(^|\\s)"+t+"no-js(\\s|$)");n=n.replace(o,"$1"+t+"js$2")}Modernizr._config.enableClasses&&(n+=" "+t+e.join(" "+t),h?p.className.baseVal=n:p.className=n)}function i(){return"function"!=typeof n.createElement?n.createElement(arguments[0]):h?n.createElementNS.call(n,"http://www.w3.org/2000/svg",arguments[0]):n.createElement.apply(n,arguments)}function r(){var e=n.body;return e||(e=i(h?"svg":"body"),e.fake=!0),e}function l(e,t,o,s){var a,l,f,c,d="modernizr",u=i("div"),h=r();if(parseInt(o,10))for(;o--;)f=i("div"),f.id=s?s[o]:d+(o+1),u.appendChild(f);return a=i("style"),a.type="text/css",a.id="s"+d,(h.fake?h:u).appendChild(a),h.appendChild(u),a.styleSheet?a.styleSheet.cssText=e:a.appendChild(n.createTextNode(e)),u.id=d,h.fake&&(h.style.background="",h.style.overflow="hidden",c=p.style.overflow,p.style.overflow="hidden",p.appendChild(h)),l=t(u,e),h.fake?(h.parentNode.removeChild(h),p.style.overflow=c,p.offsetHeight):u.parentNode.removeChild(u),!!l}var f=[],c=[],d={_version:"3.0.0-alpha.4",_config:{classPrefix:"",enableClasses:!0,enableJSClass:!0,usePrefixes:!0},_q:[],on:function(e,n){var t=this;setTimeout(function(){n(t[e])},0)},addTest:function(e,n,t){c.push({name:e,fn:n,options:t})},addAsyncTest:function(e){c.push({name:null,fn:e})}},Modernizr=function(){};Modernizr.prototype=d,Modernizr=new Modernizr;var u=d._config.usePrefixes?" -webkit- -moz- -o- -ms- ".split(" "):[];d._prefixes=u;var p=n.documentElement,h="svg"===p.nodeName.toLowerCase(),m=d.testStyles=l;Modernizr.addTest("touchevents",function(){var t;if("ontouchstart"in e||e.DocumentTouch&&n instanceof DocumentTouch)t=!0;else{var o=["@media (",u.join("touch-enabled),("),"heartz",")","{#modernizr{top:9px;position:absolute}}"].join("");m(o,function(e){t=9===e.offsetTop})}return t}),s(),a(f),delete d.addTest,delete d.addAsyncTest;for(var v=0;v<Modernizr._q.length;v++)Modernizr._q[v]();e.Modernizr=Modernizr}(window,document);/* 
*
* Close alert message
*
*/
$(document).on('click', '.closealert', function () {
    $(this).parent().fadeOut();
});

/**
*
* Call image preview 
*
*/
$(document).on('click', 'a.thumb', function(e) {
    e.preventDefault();
    $(".navigall").remove();
    var thislink = $(this).data('link');
    var thislinkencoded = $(this).data('linkencoded');
    var thisname = $(this).data('name');
    var thisID = $(this).parents('.rowa').attr('id');

    loadImg(thislink, thislinkencoded, thisname, thisID);
});

/**
*
* Return first item from the end of the gallery
*
*/
jQuery.fn.firstAfter = function(selector) {
    return this.nextAll(selector).first();
};
jQuery.fn.firstBefore = function(selector) {
    return this.prevAll(selector).first();
};
/**
*
* Setup gallery navigation
*
*/
function checkNextPrev(currentID){

    var current = $('#'+currentID);
    var nextgall = current.firstAfter('.gallindex').find('.vfm-gall');
    var prevgall = current.firstBefore('.gallindex').find('.vfm-gall');

    if (nextgall.length > 0){

        var nextlink = nextgall.data('link');  
        var nextlinkencoded = nextgall.data('linkencoded');
        var nextname = nextgall.data('name');
        var nextID = current.firstAfter('.gallindex').attr('id');

        if ($('.nextgall').length < 1) {
            $(".vfm-zoom").append('<a class="nextgall navigall"><span class="fa-stack"><i class="fa fa-angle-right fa-stack-1x fa-inverse"></i></span></a>');
        }
        $(".nextgall").data('link', nextlink)
        $(".nextgall").data('linkencoded', nextlinkencoded)
        $(".nextgall").data('name', nextname)
        $(".nextgall").data('id', nextID)
    } else {
        $(".nextgall").remove();
    }

    if (prevgall.length > 0){

        var prevlink = prevgall.data('link');  
        var prevlinkencoded = prevgall.data('linkencoded');
        var prevname = prevgall.data('name');
        var prevID = current.firstBefore('.gallindex').attr('id');

        if ($('.prevgall').length < 1) {
            $(".vfm-zoom").append('<a class="prevgall navigall"><span class="fa-stack"><i class="fa fa-angle-left fa-stack-1x fa-inverse"></i></span></a>');
        }
        $(".prevgall").data('link', prevlink)
        $(".prevgall").data('linkencoded', prevlinkencoded)
        $(".prevgall").data('name', prevname)
        $(".prevgall").data('id', prevID)
    } else {
        $(".prevgall").remove();
    }
}
/**
*
* navigate through image preview gallery
*
*/
$(document).on('click', 'a.navigall', function(e) {
    var thislink = $(this).data('link');
    var thislinkencoded = $(this).data('linkencoded');
    var thisname = $(this).data('name');
    var thisID = $(this).data('id');
    $(".navigall").remove();

    loadImg(thislink, thislinkencoded, thisname, thisID);
});

/**
*
* navigate with arrow keys
*
*/
$(document).keydown(function(e) {
    if(e.keyCode == 39 && $('.nextgall').length > 0) { // right
        $('.nextgall').trigger('click');
    }

    if(e.keyCode == 37 && $('.prevgall').length > 0) { // left
        $('.prevgall').trigger('click');
    }
});

$(document).ready(function(e) {
    $('#zoomview').on('hidden.bs.modal', function () {
       $(".navigall").remove();
    });
});
/**
*
* Rename file and folder 
*
*/
$(document).on('click', '.rename a', function () {

    var thisname = $(this).data('thisname');
    var thisdir = $(this).data('thisdir');
    var thisext = $(this).data('thisext');

    $("#newname").val(thisname);
    $("#oldname").val(thisname);

    $("#dir").val(thisdir);
    $("#ext").val(thisext);
    $("#modalchangename").modal();
});


/** 
* 
* User panel 
*
*/
$(document).on('click', '.edituser', function () {

    var thisname = $(this).data('thisname');
    var thisdir = $(this).data('thisdir');
    var thisext = $(this).data('thisext');

    $("#newname").val(thisname);
    $("#oldname").val(thisname);

    $("#dir").val(thisdir);
    $("#ext").val(thisext);
});

/**
*
* password confirm
*
*/
$("#usrForm").submit(function(e){

    if($("#oldp").val().length < 1) {
        $("#oldp").focus();
        e.preventDefault();
    }

    if($("#newp").val() != $("#checknewp").val()) {
        $("#checknewp").focus();
        e.preventDefault();
    }
});

/**
*
* password reset 
*
*/
$("#rpForm").submit(function(e){

    if ($("#rep").val().length < 1) {
        $("#rep").focus();
        e.preventDefault();
    }

    if ($("#rep").val() != $("#repconf").val()) {
        $("#repconf").focus();
        e.preventDefault();
    }
});


/**
*
* send link to reset password 
*
*/
$(document).on('submit', '#sendpwd', function (event) {

    event.preventDefault();

    $(".mailpreload").fadeIn(function(){
        $.ajax({
            type: "POST",
            url: "vfm-admin/ajax/sendpwd.php",
            data: $( "#sendpwd" ).serialize()
            })
            .done(function( msg ) {
                $('.sendresponse').html(msg).fadeIn();
                $(".mailpreload").fadeOut();
                $('#captcha-sm').attr('src', 'vfm-admin/captcha.php?'+(new Date).getTime());
                $('#sendpwd .panel-body input').val('');
            })
            .fail(function() {
                $(".mailpreload").fadeOut();
                $('.sendresponse').html('<div class=\"alert alert-danger\">Error connecting: ajax/sendpwd</div>').fadeIn();
                $('#captcha-sm').attr('src', 'vfm-admin/captcha.php?'+(new Date).getTime());
        });
    });
});

/**
*
* add mail recipients (file sharing) 
*
*/
$(document).on('click', '.shownext', function () {
    var $lastinput = $(this).parent().prev().find('.form-group:last-child .addest');

    if ($lastinput.val().length < 5) {
        $lastinput.focus();
    } else {
        var $newdest, $inputgroup, $addon, $input;
        
        $input = $('<input name="send_cc[]" type="email" class="form-control addest">');
        $addon = $('<span class="input-group-addon"><i class="fa fa-envelope fa-fw"></i></span>');
        $inputgroup = $('<div class="input-group"></div>').append($addon).append($input);
        $newdest = $('<div class="form-group bcc-address"></div>').append($inputgroup);

        $(".wrap-dest").append($newdest);
    }
});

/**
*
* slide fade mail form
*
*/
$.fn.slideFadeToggle = function(speed, easing, callback) {
    return this.animate({
        opacity: 'toggle',
        height: 'toggle'
    }, speed, easing, callback);
};

$(document).on('click', '.openmail', function () {
    $('#sendfiles').slideFadeToggle();
});


/**
*
* create a random string
*
*/
function randomstring() 
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (var i=0; i < 8; i++) {
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return text;
}

/**
*
* file sharing password widget
*
*/
function passwidget()
{
    if ($('#use_pass').prop('checked')) {
        $('.seclink').show();
    } else {
        $('.seclink').hide();
    } 
    $('.sharelink, .passlink').val('');
    $('.shalink, #sendfiles, .openmail').hide();
    $('.passlink').prop('readonly', false);
    $('.createlink-wrap').fadeIn();
}

$(document).on('change', '#use_pass', function() {
    $('.alert').alert('close');
    passwidget();
});

/**
*
* change input value on select files
*
*/
$(document).on('change', '.btn-file :file', function () {
    var input = $(this),
    numFiles = input.get(0).files ? input.get(0).files.length : 1,
    label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, label]);
});

/**
*
* Check - Uncheck all
*
*/
$(document).on('click', '#selectall', function (e) {
    e.preventDefault();
    
    $('.selecta').prop('checked',!$('.selecta').prop('checked'));
    checkSelecta();
});


/**
*
* Disable/Enable group action buttons & new directory submit
*
*/

function checkSelecta(){
    $('.selecta').each(function(){
        var $rowa = $(this).closest('.rowa');
        if ($(this).prop('checked')) {
            $rowa.addClass('attivo');
        } else {
            $rowa.removeClass('attivo');
        }
    });

    if ($('.selecta:checked').length > 0) {
        $('.groupact, .manda').attr("disabled", false);
    } else {
        $('.groupact, .manda').attr("disabled", true);
    } 
}

$(document).on('change', '.selecta', function () {
    checkSelecta();
});

$(document).ready(function(){
    $('.groupact, .manda, .upfolder').attr("disabled", true);
    checkSelecta();

    $('.upload_dirname').keyup(function() {
        if($(this).val().length > 0){
            $('.upfolder').attr("disabled", false);
        } else {
            $('.upfolder').attr("disabled", true);
       }
    });
});


/**
*
* Check - Uncheck all
*
*/
$(document).on('click', '.selectallusers', function() {
    $('.selectme').prop('checked',!$('.selectme').prop('checked'));
    checkNotiflist();
});

/**
*
* Change notify users icon
*
*/
function checkNotiflist(){
    var anyUserChecked = $('#userslist :checkbox:checked').length > 0;
    if (anyUserChecked == true) {
        $('.check-notif').removeClass('fa-circle-o').addClass('fa-check-circle');
    } else {
        $('.check-notif').removeClass('fa-check-circle').addClass('fa-circle-o');
    }
}

$(document).ready(function(){
    $('#userslist :checkbox').change(function() {
        checkNotiflist();
    });
});
/**
*
* Fade In filemanager tables on load
*
*/
$(window).load(function(){
    $(".tableblock").animate({
        opacity: 1
        }, 500, function() {
    });
});

/**
*
* SetUp datatable for Folders
*
*/
function callFoldersTable($sPaginationType, $iDisplayLength){

    $('#sortable').dataTable({
        "ordering": false,
        "pagingType": $sPaginationType,

        // hide pagination if we have only one page
        "fnDrawCallback": function() { 
            var paginateRow = $(this).parent().find('.dataTables_paginate');                        
            if (this.fnSettings().fnRecordsDisplay() <= this.fnSettings()._iDisplayLength) {
                paginateRow.css('display', 'none');
            } else {
                paginateRow.css('display', 'block');
            }
        },
        "pageLength": $iDisplayLength,
        "language": {
            "sEmptyTable":      "Não há arquivos",
            "sInfo":            "_START_ / _END_ - _TOTAL_ ",
            "sInfoEmpty":       "",
            "sInfoFiltered":    "",
            "sInfoPostFix":     "",
            "sLengthMenu":      "<i class='fa fa-list-ol'></i> _MENU_",
            "sLoadingRecords":  "<i class='fa fa-refresh fa-spin'></i>",
            "sProcessing":      "<i class='fa fa-refresh fa-spin'></i>",
            "sSearch":          "<span class='input-group-addon'><i class='fa fa-search'></i></span> ",
            "sZeroRecords":     "--",
            "oPaginate": {
                "sFirst": "<i class='fa fa-angle-double-left'></i>",
                "sLast": "<i class='fa fa-angle-double-right'></i>",
                "sPrevious": "<i class='fa fa-angle-left'></i>",
                "sNext": "<i class='fa fa-angle-right'></i>"
            }
        }
    });
}

/**
*
* SetUp datatable for Files
*
*/
var oTable;

/*'full_numbers', true, true, 10, 6, 'desc' */

function callFilesTable($sPaginationType, $bPaginate, $bFilter, $iDisplayLength, $fnSortcol, $fnSortdir){

    $('#sort').addClass('ghost');

    $.extend($.fn.dataTableExt.oStdClasses, {
        "sSortAsc": "header headerSortDown",
        "sSortDesc": "header headerSortUp",
        "sSortable": "header"
    }); 

    oTable = $('#sort').dataTable({
        "pagingType": $sPaginationType,
        "paging": $bPaginate,
        "searching": $bFilter,
        "pageLength": $iDisplayLength,

        

        "columnDefs": [ 
            { 
                "targets": [1, 2, 3], 
                "orderable": true, 
                "searchable": true,
                "type": 'natural'
            }
        ],
        "language": {
            "sEmptyTable":      bundle.get('J0006'),
            "sInfo":            "_START_ / _END_ - _TOTAL_ ",
            "sInfoEmpty":       "",
            "sInfoFiltered":    "",
            "sInfoPostFix":     "",
            "sLengthMenu":      "<i class='fa fa-list-ol'></i> _MENU_",
            "sLoadingRecords":  "<i class='fa fa-refresh fa-spin'></i>",
            "sProcessing":      "<i class='fa fa-refresh fa-spin'></i>",
            "sSearch":          "<span class='input-group-addon'><i class='fa fa-search'></i></span> ",
            "sZeroRecords":     "<span class='centertext'>"+bundle.get('J0006')+"</span>",
            "oPaginate": {
                "sFirst": "<i class='fa fa-angle-double-left'></i>",
                "sLast": "<i class='fa fa-angle-double-right'></i>",
                "sPrevious": "<i class='fa fa-angle-left'></i>",
                "sNext": "<i class='fa fa-angle-right'></i>"
            }
           
        } 
    });
 
    oTable.fnSort( [ [$fnSortcol, $fnSortdir] ] );

    /**
    *
    * Uncomment the following code
    * to disable instant search input,
    * enable enter key and
    * enable search button
    */
    
    // $('.dataTables_filter input').unbind('keyup').bind('keyup', function(e){
    //     if (e.which == 13){
    //         oTable.fnFilter($(this).val(), null, false, true);
    //     }
    // });

    // $('.dataTables_filter .input-group-addon').on('click',function(e){
    //     oTable.fnFilter($('.dataTables_filter input').val(), null, false, true);
    // });
    $('#sort').removeClass('ghost');
}

/**
*
* FILE SHARING
*
*/
function createShareLink($insert4, $time, $hash, $pulito, $activepagination, $maxzipfiles, $selectfiles, $toomanyfiles, $prettylinks) {

    var dvar;

    $(document).on('click', '#createlink', function () {

        $('.alert').alert('close');
        var alertmess = '<div class="alert alert-warning alert-dismissible" role="alert">'
                        + $insert4 +'</div>';
        var shortlink, passw
        // check if wants a password
        if ($('#use_pass').prop('checked')) {
            

            if (!$('.setpass').val()) {
                passw = randomstring();
            } else {
                if ($('.setpass').val().length < 4) {
                    $('.setpass').focus();
                    $('.seclink').after(alertmess);
                    return;
                } else {
                    passw = $('.setpass').val();
                }
            }  
        }

        $.ajax({
            cache: false,
            type: "POST",
            url: "vfm-admin/ajax/shorten.php",
            data: {
                atts: divar.join(','),
                time: $time,
                hash: $hash,
                pass: passw
            }
        })
        .done(function( msg ) {
            shortlink = $pulito + '/?dl=' + msg;
            $(".sharelink").val(shortlink);
            $(".sharebutt").attr('href', shortlink);
            $(".passlink").val(passw);
            $('.passlink').prop('readonly', true);
            
            $('.createlink-wrap').fadeOut('fast', function(){
                $('.shalink').fadeIn();
                $(".openmail").fadeIn();
            });
            // console.log(msg);
        })
        .fail(function() {
            console.log('ERROR generating shortlink');
        });
    });

    $(document).on('click', '.manda', function () {
        if($(".selecta:checked").size() > 0) {
            divar = [];

            if ($activepagination == true) {

                var sData = $('.selecta', oTable.fnGetNodes()).serializeArray();
                var numfiles = sData.length;

                jQuery.each( sData, function( i, field ) {
                    divar.push(field.value);
                });
            } else {
                var numfiles = $(".selecta:checked").size();
                $(".selecta:checked").each(function(){
                    divar.push($(this).val());
                });
            }
               
            // reset form
            $(".addest").val('');
            $(".bcc-address").remove();

            $('.seclink, .shalink, .mailresponse, #sendfiles, .openmail').hide();
            $('.sharelink, .passlink').val('');
            $(".sharebutt").attr('href', '#');
            $('.createlink-wrap').fadeIn();

            passwidget();

            // populate send inputs
            $(".attach").val(divar.join(','));
            $(".numfiles").html(numfiles);

            // open modal
            $("#sendfilesmodal").modal();

            $("#sendfiles").unbind('submit').submit(function(event) {
                event.preventDefault();
                $(".mailpreload").fadeIn();
                var now = $.now();

                $.ajax({
                    cache: false,
                    type: "POST",
                    url: "vfm-admin/ajax/sendfiles.php?t="+now,
                    data: $("#sendfiles").serialize()
                })
                .done(function( msg ) {
                    $('.mailresponse').html('<div class="alert alert-success">' + msg + '</p>').fadeIn();
                    $(".addest").val('');
                    $(".bcc-address").remove();
                    $(".mailpreload").fadeOut();
                })
                .fail(function() {
                    $(".mailpreload").fadeOut();
                    $('.mailresponse').html('<div class="alert alert-danger">Error</div>');
                });
            });
        } else {
            alert($selectfiles);
        }
    }); // end .manda click

    $(document).on('click', '.multid', function(e) {
        e.preventDefault();
        if($(".selecta:checked").size() > 0) {
            divar = [];

            if ($activepagination == true) {
                var sData = $('.selecta', oTable.fnGetNodes()).serializeArray();
                var numfiles = sData.length;

                jQuery.each( sData, function( i, field ) {
                    divar.push(field.value);
                });
            } else {
                var numfiles = $(".selecta:checked").size();
                $(".selecta:checked").each(function(){
                    divar.push($(this).val());
                });
            }

            if (numfiles >= $maxzipfiles) {
                alert($toomanyfiles + ' ' + $maxzipfiles);
            } else {
                //
                // generate short url for multiple downloads
                //
                var shortlink

                $.ajax({
                    cache: false,
                    type: "POST",
                    url: "vfm-admin/ajax/shorten.php",
                    data: {
                        atts: divar.join(','),
                        time: $time,
                        hash: $hash
                    }
                })
                .done(function( msg ) {
                    if ($prettylinks) {
                        shortlink = 'download/dl/' + msg;
                    } else {
                        shortlink = 'vfm-admin/vfm-downloader.php?dl=' + msg;
                    }

                    $(".sendlink").attr("href", shortlink);

                    $("#downloadmulti .numfiles").html(numfiles);
                    
                    // open modal
                    $('#downloadmulti').modal();
                    // console.log(msg);
                })
                .fail(function() {
                    console.log('ERROR generating shortlink');
                });
            }
        } else {
            alert($selectfiles);
        }
    }); // end .multid click
}

/**
*
* DELETE FILES
*
*/
function setupDelete($confirmthisdel, $confirmdel, $activepagination, $time, $hash, $doit, $selectfiles) {
    //
    // Delete single files
    //
    $(document).on('click', 'td.del a', function() {
        var answer = confirm($confirmthisdel + $(this).attr("data-name") + "\"");
        return answer;
    });

    //
    // Delete multiple files
    //
    $(document).on('click', '.removelink', function(e) {
        e.preventDefault();
        var answer = confirm($confirmdel);
        
        var deldata = $('#delform').serializeArray();

        if (answer == true) {
            $.ajax({
                type: "POST",
                url: "vfm-admin/vfm-del.php",
                data: deldata
            })
            .done(function( msg ) {
                if (msg == "ok") {
                    window.location = window.location.href.split('&del')[0];
                } else {
                    $(".delresp").html(msg);
                }
            })
             .fail(function() {
                alert( 'error' );
            });
         } else {
            return answer;
         }
    });

    //
    // Setup multi delete button
    //
    $(document).on('click', '.multic', function(e) {
        e.preventDefault();
        if($(".selecta:checked").size() > 0) {

            if ($activepagination == true) {
                var sData = $('.selecta', oTable.fnGetNodes()).serializeArray();
                var numfiles = sData.length;

                jQuery.each(sData, function(i, field) {
                    $("#delform").append("<input type=\"hidden\" name=\"setdel[]\" value=\""+field.value+"\">");
                });
            } else {
            
                var numfiles = $(".selecta:checked").size();

                $(".selecta:checked").each(function(){
                    $("#delform").append("<input type=\"hidden\" name=\"setdel[]\" value=\""+$(this).val()+"\">");
                });
            }

            $("#delform").append("<input type=\"hidden\" name=\"t\" value=\"" + $time + "\">");
            $("#delform").append("<input type=\"hidden\" name=\"h\" value=\"" + $hash + "\">");
            $("#delform").append("<input type=\"hidden\" name=\"doit\" value=\"" + $doit + "\">");

            $("#deletemulti .numfiles").html(numfiles);
            $('#deletemulti').modal();
        } else {
            alert($selectfiles);
        }
    }); 
}



/**
*
* MOVE FILES
*
*/
function setupMove($activepagination, $selectfiles, $time, $hash, $doit) {
//
// Setup multi move form
//
    $(document).on('click', '.multimove', function(e) {
        e.preventDefault();
        if($(".selecta:checked").size() > 0) {

        if ($activepagination == true) {
            var sData = $('.selecta', oTable.fnGetNodes()).serializeArray();
            // var numfiles = sData.length;

            jQuery.each(sData, function(i, field) {
                $("#moveform").append("<input type=\"hidden\" name=\"setmove[]\" value=\""+field.value+"\">");
            });
        } else { 
            $(".selecta:checked").each(function(){
                $("#moveform").append("<input type=\"hidden\" name=\"setmove[]\" value=\""+$(this).val()+"\">");
            });
        }
            $("#moveform").append("<input type=\"hidden\" name=\"t\" value=\"" + $time + "\">");
            $("#moveform").append("<input type=\"hidden\" name=\"h\" value=\"" + $hash + "\">");
            $("#moveform").append("<input type=\"hidden\" name=\"doit\" value=\"" + $doit + "\">");

            $('#movemulti').modal();
        } else {
            alert($selectfiles);
        }
    }); 

    $(document).on('click', '.movelink', function(e) {
        e.preventDefault();
        
        var dest = $(this).data('dest');
        $("#moveform").append("<input type=\"hidden\" name=\"dest\" value=\""+dest+"\">");

        var movedata = $('#moveform').serializeArray();

        $.ajax({
            type: "POST",
            url: "vfm-admin/vfm-move.php",
            data: movedata
        })
        .done(function(msg) {

            if (msg == "ok") {
                window.location = window.location.href.split('&del')[0];
            } else {
                var alert = '<div class="alert alert-danger" role="alert">'+msg+'</div>'
                $("#movemulti .hiddenalert").html(alert);
            }
        })
        .fail(function() {
            var alert = '<div class="alert alert-danger" role="alert">Error connecting vfm-move.php</div>'
            $("#movemulti .hiddenalert").html(alert);
        });
    });
}

var oTableSortable;
$(document).ready(function() {
	oTableSortable  = $('#sortable').dataTable({
		"searching": false,
		 "ordering": true,
		"aaSorting": [[ 0, 1 ]] // Sort by first column descending
	});
});

$(document).on('click', '#orderAlpha', function() {
	oTableSortable.fnSort( [ [1, 'asc'] ] );
});

$(document).on('click', '#orderNumber', function() {
	oTableSortable.fnSort( [ [0, 'asc'] ] );
});
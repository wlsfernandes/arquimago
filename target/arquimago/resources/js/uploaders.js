/*
* MIT Licensed
* http://www.23developer.com/opensource
* http://github.com/23/resumable.js
* Steffen Tiedemann Christensen, steffen@23company.com
*/
(function(){"use strict";var e=function(t){function u(e,t){var n=this;n.opts={};n.getOpt=e.getOpt;n._prevProgress=0;n.resumableObj=e;n.file=t;n.fileName=t.fileName||t.name;n.size=t.size;n.relativePath=t.webkitRelativePath||n.fileName;n.uniqueIdentifier=r.generateUniqueIdentifier(t);n._pause=false;n.container="";var i=false;var s=function(e,t){switch(e){case"progress":n.resumableObj.fire("fileProgress",n);break;case"error":n.abort();i=true;n.chunks=[];n.resumableObj.fire("fileError",n,t);break;case"success":if(i)return;n.resumableObj.fire("fileProgress",n);if(n.isComplete()){n.resumableObj.fire("fileSuccess",n,t)}break;case"retry":n.resumableObj.fire("fileRetry",n);break}};n.chunks=[];n.abort=function(){var e=0;r.each(n.chunks,function(t){if(t.status()=="uploading"){t.abort();e++}});if(e>0)n.resumableObj.fire("fileProgress",n)};n.cancel=function(){var e=n.chunks;n.chunks=[];r.each(e,function(e){if(e.status()=="uploading"){e.abort();n.resumableObj.uploadNextChunk()}});n.resumableObj.removeFile(n);n.resumableObj.fire("fileProgress",n)};n.retry=function(){n.bootstrap();var e=false;n.resumableObj.on("chunkingComplete",function(){if(!e)n.resumableObj.upload();e=true})};n.bootstrap=function(){n.abort();i=false;n.chunks=[];n._prevProgress=0;var e=n.getOpt("forceChunkSize")?Math.ceil:Math.floor;var t=Math.max(e(n.file.size/n.getOpt("chunkSize")),1);for(var r=0;r<t;r++){(function(e){window.setTimeout(function(){n.chunks.push(new a(n.resumableObj,n,e,s));n.resumableObj.fire("chunkingProgress",n,e/t)},0)})(r)}window.setTimeout(function(){n.resumableObj.fire("chunkingComplete",n)},0)};n.progress=function(){if(i)return 1;var e=0;var t=false;r.each(n.chunks,function(n){if(n.status()=="error")t=true;e+=n.progress(true)});e=t?1:e>.999?1:e;e=Math.max(n._prevProgress,e);n._prevProgress=e;return e};n.isUploading=function(){var e=false;r.each(n.chunks,function(t){if(t.status()=="uploading"){e=true;return false}});return e};n.isComplete=function(){var e=false;r.each(n.chunks,function(t){var n=t.status();if(n=="pending"||n=="uploading"||t.preprocessState===1){e=true;return false}});return!e};n.pause=function(e){if(typeof e==="undefined"){n._pause=n._pause?false:true}else{n._pause=e}};n.isPaused=function(){return n._pause};n.resumableObj.fire("chunkingStart",n);n.bootstrap();return this}function a(e,t,n,i){var s=this;s.opts={};s.getOpt=e.getOpt;s.resumableObj=e;s.fileObj=t;s.fileObjSize=t.size;s.fileObjType=t.file.type;s.offset=n;s.callback=i;s.lastProgressCallback=new Date;s.tested=false;s.retries=0;s.pendingRetry=false;s.preprocessState=0;var o=s.getOpt("chunkSize");s.loaded=0;s.startByte=s.offset*o;s.endByte=Math.min(s.fileObjSize,(s.offset+1)*o);if(s.fileObjSize-s.endByte<o&&!s.getOpt("forceChunkSize")){s.endByte=s.fileObjSize}s.xhr=null;s.test=function(){s.xhr=new XMLHttpRequest;var e=function(e){s.tested=true;var t=s.status();if(t=="success"){s.callback(t,s.message());s.resumableObj.uploadNextChunk()}else{s.send()}};s.xhr.addEventListener("load",e,false);s.xhr.addEventListener("error",e,false);var t=[];var n=s.getOpt("query");if(typeof n=="function")n=n(s.fileObj,s);r.each(n,function(e,n){t.push([encodeURIComponent(e),encodeURIComponent(n)].join("="))});t.push(["resumableChunkNumber",encodeURIComponent(s.offset+1)].join("="));t.push(["resumableChunkSize",encodeURIComponent(s.getOpt("chunkSize"))].join("="));t.push(["resumableCurrentChunkSize",encodeURIComponent(s.endByte-s.startByte)].join("="));t.push(["resumableTotalSize",encodeURIComponent(s.fileObjSize)].join("="));t.push(["resumableType",encodeURIComponent(s.fileObjType)].join("="));t.push(["resumableIdentifier",encodeURIComponent(s.fileObj.uniqueIdentifier)].join("="));t.push(["resumableFilename",encodeURIComponent(s.fileObj.fileName)].join("="));t.push(["resumableRelativePath",encodeURIComponent(s.fileObj.relativePath)].join("="));s.xhr.open("GET",r.getTarget(t));s.xhr.timeout=s.getOpt("xhrTimeout");s.xhr.withCredentials=s.getOpt("withCredentials");r.each(s.getOpt("headers"),function(e,t){s.xhr.setRequestHeader(e,t)});s.xhr.send(null)};s.preprocessFinished=function(){s.preprocessState=2;s.send()};s.send=function(){var e=s.getOpt("preprocess");if(typeof e==="function"){switch(s.preprocessState){case 0:e(s);s.preprocessState=1;return;case 1:return;case 2:break}}if(s.getOpt("testChunks")&&!s.tested){s.test();return}s.xhr=new XMLHttpRequest;s.xhr.upload.addEventListener("progress",function(e){if(new Date-s.lastProgressCallback>s.getOpt("throttleProgressCallbacks")*1e3){s.callback("progress");s.lastProgressCallback=new Date}s.loaded=e.loaded||0},false);s.loaded=0;s.pendingRetry=false;s.callback("progress");var t=function(e){var t=s.status();if(t=="success"||t=="error"){s.callback(t,s.message());s.resumableObj.uploadNextChunk()}else{s.callback("retry",s.message());s.abort();s.retries++;var n=s.getOpt("chunkRetryInterval");if(n!==undefined){s.pendingRetry=true;setTimeout(s.send,n)}else{s.send()}}};s.xhr.addEventListener("load",t,false);s.xhr.addEventListener("error",t,false);var n={resumableChunkNumber:s.offset+1,resumableChunkSize:s.getOpt("chunkSize"),resumableCurrentChunkSize:s.endByte-s.startByte,resumableTotalSize:s.fileObjSize,resumableType:s.fileObjType,resumableIdentifier:s.fileObj.uniqueIdentifier,resumableFilename:s.fileObj.fileName,resumableRelativePath:s.fileObj.relativePath,resumableTotalChunks:s.fileObj.chunks.length};var i=s.getOpt("query");if(typeof i=="function")i=i(s.fileObj,s);r.each(i,function(e,t){n[e]=t});var o=s.fileObj.file.slice?"slice":s.fileObj.file.mozSlice?"mozSlice":s.fileObj.file.webkitSlice?"webkitSlice":"slice",u=s.fileObj.file[o](s.startByte,s.endByte),a=null,f=s.getOpt("target");if(s.getOpt("method")==="octet"){a=u;var l=[];r.each(n,function(e,t){l.push([encodeURIComponent(e),encodeURIComponent(t)].join("="))});f=r.getTarget(l)}else{a=new FormData;r.each(n,function(e,t){a.append(e,t)});a.append(s.getOpt("fileParameterName"),u)}s.xhr.open("POST",f);s.xhr.timeout=s.getOpt("xhrTimeout");s.xhr.withCredentials=s.getOpt("withCredentials");r.each(s.getOpt("headers"),function(e,t){s.xhr.setRequestHeader(e,t)});s.xhr.send(a)};s.abort=function(){if(s.xhr)s.xhr.abort();s.xhr=null};s.status=function(){if(s.pendingRetry){return"uploading"}else if(!s.xhr){return"pending"}else if(s.xhr.readyState<4){return"uploading"}else{if(s.xhr.status==200){return"success"}else if(r.contains(s.getOpt("permanentErrors"),s.xhr.status)||s.retries>=s.getOpt("maxChunkRetries")){return"error"}else{s.abort();return"pending"}}};s.message=function(){return s.xhr?s.xhr.responseText:""};s.progress=function(e){if(typeof e==="undefined")e=false;var t=e?(s.endByte-s.startByte)/s.fileObjSize:1;if(s.pendingRetry)return 0;var n=s.status();switch(n){case"success":case"error":return 1*t;case"pending":return 0*t;default:return s.loaded/(s.endByte-s.startByte)*t}};return this}if(!(this instanceof e)){return new e(t)}this.version=1;this.support=typeof File!=="undefined"&&typeof Blob!=="undefined"&&typeof FileList!=="undefined"&&(!!Blob.prototype.webkitSlice||!!Blob.prototype.mozSlice||!!Blob.prototype.slice||false);if(!this.support)return false;var n=this;n.files=[];n.defaults={chunkSize:1*1024*1024,forceChunkSize:false,simultaneousUploads:3,fileParameterName:"file",throttleProgressCallbacks:.5,query:{},headers:{},preprocess:null,method:"multipart",prioritizeFirstAndLastChunk:false,target:"/",testChunks:true,generateUniqueIdentifier:null,maxChunkRetries:undefined,chunkRetryInterval:undefined,permanentErrors:[404,415,500,501],maxFiles:undefined,withCredentials:false,xhrTimeout:0,maxFilesErrorCallback:function(e,t){var r=n.getOpt("maxFiles");alert("Please upload "+r+" file"+(r===1?"":"s")+" at a time.")},minFileSize:1,minFileSizeErrorCallback:function(e,t){alert(e.fileName||e.name+" is too small, please upload files larger than "+r.formatSize(n.getOpt("minFileSize"))+".")},maxFileSize:undefined,maxFileSizeErrorCallback:function(e,t){alert(e.fileName||e.name+" is too large, please upload files less than "+r.formatSize(n.getOpt("maxFileSize"))+".")},fileType:[],fileTypeErrorCallback:function(e,t){alert(e.fileName||e.name+" has type not allowed, please upload files of type "+n.getOpt("fileType")+".")}};n.opts=t||{};n.getOpt=function(t){var n=this;if(t instanceof Array){var i={};r.each(t,function(e){i[e]=n.getOpt(e)});return i}if(n instanceof a){if(typeof n.opts[t]!=="undefined"){return n.opts[t]}else{n=n.fileObj}}if(n instanceof u){if(typeof n.opts[t]!=="undefined"){return n.opts[t]}else{n=n.resumableObj}}if(n instanceof e){if(typeof n.opts[t]!=="undefined"){return n.opts[t]}else{return n.defaults[t]}}};n.events=[];n.on=function(e,t){n.events.push(e.toLowerCase(),t)};n.fire=function(){var e=[];for(var t=0;t<arguments.length;t++)e.push(arguments[t]);var r=e[0].toLowerCase();for(var t=0;t<=n.events.length;t+=2){if(n.events[t]==r)n.events[t+1].apply(n,e.slice(1));if(n.events[t]=="catchall")n.events[t+1].apply(null,e)}if(r=="fileerror")n.fire("error",e[2],e[1]);if(r=="fileprogress")n.fire("progress")};var r={stopEvent:function(e){e.stopPropagation();e.preventDefault()},each:function(e,t){if(typeof e.length!=="undefined"){for(var n=0;n<e.length;n++){if(t(e[n])===false)return}}else{for(n in e){if(t(n,e[n])===false)return}}},generateUniqueIdentifier:function(e){var t=n.getOpt("generateUniqueIdentifier");if(typeof t==="function"){return t(e)}var r=e.webkitRelativePath||e.fileName||e.name;var i=e.size;return i+"-"+r.replace(/[^0-9a-zA-Z_-]/img,"")},contains:function(e,t){var n=false;r.each(e,function(e){if(e==t){n=true;return false}return true});return n},formatSize:function(e){if(e<1024){return e+" bytes"}else if(e<1024*1024){return(e/1024).toFixed(0)+" KB"}else if(e<1024*1024*1024){return(e/1024/1024).toFixed(1)+" MB"}else{return(e/1024/1024/1024).toFixed(1)+" GB"}},getTarget:function(e){var t=n.getOpt("target");if(t.indexOf("?")<0){t+="?"}else{t+="&"}return t+e.join("&")}};var i=function(e){r.stopEvent(e);o(e.dataTransfer.files,e)};var s=function(e){e.preventDefault()};var o=function(e,t){var i=0;var s=n.getOpt(["maxFiles","minFileSize","maxFileSize","maxFilesErrorCallback","minFileSizeErrorCallback","maxFileSizeErrorCallback","fileType","fileTypeErrorCallback"]);if(typeof s.maxFiles!=="undefined"&&s.maxFiles<e.length+n.files.length){if(s.maxFiles===1&&n.files.length===1&&e.length===1){n.removeFile(n.files[0])}else{s.maxFilesErrorCallback(e,i++);return false}}var o=[];r.each(e,function(e){var a=e.name.split(".");var f=a[a.length-1].toLowerCase();if(s.fileType.length>0&&!r.contains(s.fileType,f)){s.fileTypeErrorCallback(e,i++);return false}if(typeof s.minFileSize!=="undefined"&&e.size<s.minFileSize){s.minFileSizeErrorCallback(e,i++);return false}if(typeof s.maxFileSize!=="undefined"&&e.size>s.maxFileSize){s.maxFileSizeErrorCallback(e,i++);return false}if(!n.getFromUniqueIdentifier(r.generateUniqueIdentifier(e))){(function(){var r=new u(n,e);window.setTimeout(function(){n.files.push(r);o.push(r);r.container=typeof t!="undefined"?t.srcElement:null;n.fire("fileAdded",r,t)},0)})()}});window.setTimeout(function(){n.fire("filesAdded",o)},0)};n.uploadNextChunk=function(){var e=false;if(n.getOpt("prioritizeFirstAndLastChunk")){r.each(n.files,function(t){if(t.chunks.length&&t.chunks[0].status()=="pending"&&t.chunks[0].preprocessState===0){t.chunks[0].send();e=true;return false}if(t.chunks.length>1&&t.chunks[t.chunks.length-1].status()=="pending"&&t.chunks[t.chunks.length-1].preprocessState===0){t.chunks[t.chunks.length-1].send();e=true;return false}});if(e)return true}r.each(n.files,function(t){if(t.isPaused()===false){r.each(t.chunks,function(t){if(t.status()=="pending"&&t.preprocessState===0){t.send();e=true;return false}})}if(e)return false});if(e)return true;var t=false;r.each(n.files,function(e){if(!e.isComplete()){t=true;return false}});if(!t){n.fire("complete")}return false};n.assignBrowse=function(e,t){if(typeof e.length=="undefined")e=[e];r.each(e,function(e){var r;if(e.tagName==="INPUT"&&e.type==="file"){r=e}else{r=document.createElement("input");r.setAttribute("type","file");r.style.display="none";e.addEventListener("click",function(){r.style.opacity=0;r.style.display="block";r.focus();r.click();r.style.display="none"},false);e.appendChild(r)}var i=n.getOpt("maxFiles");if(typeof i==="undefined"||i!=1){r.setAttribute("multiple","multiple")}else{r.removeAttribute("multiple")}if(t){r.setAttribute("webkitdirectory","webkitdirectory")}else{r.removeAttribute("webkitdirectory")}r.addEventListener("change",function(e){o(e.target.files,e);e.target.value=""},false)})};n.assignDrop=function(e){if(typeof e.length=="undefined")e=[e];r.each(e,function(e){e.addEventListener("dragover",s,false);e.addEventListener("drop",i,false)})};n.unAssignDrop=function(e){if(typeof e.length=="undefined")e=[e];r.each(e,function(e){e.removeEventListener("dragover",s);e.removeEventListener("drop",i)})};n.isUploading=function(){var e=false;r.each(n.files,function(t){if(t.isUploading()){e=true;return false}});return e};n.upload=function(){if(n.isUploading())return;n.fire("uploadStart");for(var e=1;e<=n.getOpt("simultaneousUploads");e++){n.uploadNextChunk()}};n.pause=function(){r.each(n.files,function(e){e.abort()});n.fire("pause")};n.cancel=function(){for(var e=n.files.length-1;e>=0;e--){n.files[e].cancel()}n.fire("cancel")};n.progress=function(){var e=0;var t=0;r.each(n.files,function(n){e+=n.progress()*n.size;t+=n.size});return t>0?e/t:0};n.addFile=function(e,t){o([e],t)};n.removeFile=function(e){for(var t=n.files.length-1;t>=0;t--){if(n.files[t]===e){n.files.splice(t,1)}}};n.getFromUniqueIdentifier=function(e){var t=false;r.each(n.files,function(n){if(n.uniqueIdentifier==e)t=n});return t};n.getSize=function(){var e=0;r.each(n.files,function(t){e+=t.size});return e};return this};if(typeof module!="undefined"){module.exports=e}else if(typeof define==="function"&&define.amd){define(function(){return e})}else{window.Resumable=e}})();

/*!
 * jQuery Form Plugin
 * version: 3.51.0-2014.06.20
 * Requires jQuery v1.5 or later
 * Copyright (c) 2014 M. Alsup
 * Examples and documentation at: http://malsup.com/jquery/form/
 * Project repository: https://github.com/malsup/form
 * Dual licensed under the MIT and GPL licenses.
 * https://github.com/malsup/form#copyright-and-license
 */
!function(e){"use strict";"function"==typeof define&&define.amd?define(["jquery"],e):e("undefined"!=typeof jQuery?jQuery:window.Zepto)}(function(e){"use strict";function t(t){var r=t.data;t.isDefaultPrevented()||(t.preventDefault(),e(t.target).ajaxSubmit(r))}function r(t){var r=t.target,a=e(r);if(!a.is("[type=submit],[type=image]")){var n=a.closest("[type=submit]");if(0===n.length)return;r=n[0]}var i=this;if(i.clk=r,"image"==r.type)if(void 0!==t.offsetX)i.clk_x=t.offsetX,i.clk_y=t.offsetY;else if("function"==typeof e.fn.offset){var o=a.offset();i.clk_x=t.pageX-o.left,i.clk_y=t.pageY-o.top}else i.clk_x=t.pageX-r.offsetLeft,i.clk_y=t.pageY-r.offsetTop;setTimeout(function(){i.clk=i.clk_x=i.clk_y=null},100)}function a(){if(e.fn.ajaxSubmit.debug){var t="[jquery.form] "+Array.prototype.join.call(arguments,"");window.console&&window.console.log?window.console.log(t):window.opera&&window.opera.postError&&window.opera.postError(t)}}var n={};n.fileapi=void 0!==e("<input type='file'/>").get(0).files,n.formdata=void 0!==window.FormData;var i=!!e.fn.prop;e.fn.attr2=function(){if(!i)return this.attr.apply(this,arguments);var e=this.prop.apply(this,arguments);return e&&e.jquery||"string"==typeof e?e:this.attr.apply(this,arguments)},e.fn.ajaxSubmit=function(t){function r(r){var a,n,i=e.param(r,t.traditional).split("&"),o=i.length,s=[];for(a=0;o>a;a++)i[a]=i[a].replace(/\+/g," "),n=i[a].split("="),s.push([decodeURIComponent(n[0]),decodeURIComponent(n[1])]);return s}function o(a){for(var n=new FormData,i=0;i<a.length;i++)n.append(a[i].name,a[i].value);if(t.extraData){var o=r(t.extraData);for(i=0;i<o.length;i++)o[i]&&n.append(o[i][0],o[i][1])}t.data=null;var s=e.extend(!0,{},e.ajaxSettings,t,{contentType:!1,processData:!1,cache:!1,type:u||"POST"});t.uploadProgress&&(s.xhr=function(){var r=e.ajaxSettings.xhr();return r.upload&&r.upload.addEventListener("progress",function(e){var r=0,a=e.loaded||e.position,n=e.total;e.lengthComputable&&(r=Math.ceil(a/n*100)),t.uploadProgress(e,a,n,r)},!1),r}),s.data=null;var c=s.beforeSend;return s.beforeSend=function(e,r){r.data=t.formData?t.formData:n,c&&c.call(this,e,r)},e.ajax(s)}function s(r){function n(e){var t=null;try{e.contentWindow&&(t=e.contentWindow.document)}catch(r){a("cannot get iframe.contentWindow document: "+r)}if(t)return t;try{t=e.contentDocument?e.contentDocument:e.document}catch(r){a("cannot get iframe.contentDocument: "+r),t=e.document}return t}function o(){function t(){try{var e=n(g).readyState;a("state = "+e),e&&"uninitialized"==e.toLowerCase()&&setTimeout(t,50)}catch(r){a("Server abort: ",r," (",r.name,")"),s(k),j&&clearTimeout(j),j=void 0}}var r=f.attr2("target"),i=f.attr2("action"),o="multipart/form-data",c=f.attr("enctype")||f.attr("encoding")||o;w.setAttribute("target",p),(!u||/post/i.test(u))&&w.setAttribute("method","POST"),i!=m.url&&w.setAttribute("action",m.url),m.skipEncodingOverride||u&&!/post/i.test(u)||f.attr({encoding:"multipart/form-data",enctype:"multipart/form-data"}),m.timeout&&(j=setTimeout(function(){T=!0,s(D)},m.timeout));var l=[];try{if(m.extraData)for(var d in m.extraData)m.extraData.hasOwnProperty(d)&&l.push(e.isPlainObject(m.extraData[d])&&m.extraData[d].hasOwnProperty("name")&&m.extraData[d].hasOwnProperty("value")?e('<input type="hidden" name="'+m.extraData[d].name+'">').val(m.extraData[d].value).appendTo(w)[0]:e('<input type="hidden" name="'+d+'">').val(m.extraData[d]).appendTo(w)[0]);m.iframeTarget||v.appendTo("body"),g.attachEvent?g.attachEvent("onload",s):g.addEventListener("load",s,!1),setTimeout(t,15);try{w.submit()}catch(h){var x=document.createElement("form").submit;x.apply(w)}}finally{w.setAttribute("action",i),w.setAttribute("enctype",c),r?w.setAttribute("target",r):f.removeAttr("target"),e(l).remove()}}function s(t){if(!x.aborted&&!F){if(M=n(g),M||(a("cannot access response document"),t=k),t===D&&x)return x.abort("timeout"),void S.reject(x,"timeout");if(t==k&&x)return x.abort("server abort"),void S.reject(x,"error","server abort");if(M&&M.location.href!=m.iframeSrc||T){g.detachEvent?g.detachEvent("onload",s):g.removeEventListener("load",s,!1);var r,i="success";try{if(T)throw"timeout";var o="xml"==m.dataType||M.XMLDocument||e.isXMLDoc(M);if(a("isXml="+o),!o&&window.opera&&(null===M.body||!M.body.innerHTML)&&--O)return a("requeing onLoad callback, DOM not available"),void setTimeout(s,250);var u=M.body?M.body:M.documentElement;x.responseText=u?u.innerHTML:null,x.responseXML=M.XMLDocument?M.XMLDocument:M,o&&(m.dataType="xml"),x.getResponseHeader=function(e){var t={"content-type":m.dataType};return t[e.toLowerCase()]},u&&(x.status=Number(u.getAttribute("status"))||x.status,x.statusText=u.getAttribute("statusText")||x.statusText);var c=(m.dataType||"").toLowerCase(),l=/(json|script|text)/.test(c);if(l||m.textarea){var f=M.getElementsByTagName("textarea")[0];if(f)x.responseText=f.value,x.status=Number(f.getAttribute("status"))||x.status,x.statusText=f.getAttribute("statusText")||x.statusText;else if(l){var p=M.getElementsByTagName("pre")[0],h=M.getElementsByTagName("body")[0];p?x.responseText=p.textContent?p.textContent:p.innerText:h&&(x.responseText=h.textContent?h.textContent:h.innerText)}}else"xml"==c&&!x.responseXML&&x.responseText&&(x.responseXML=X(x.responseText));try{E=_(x,c,m)}catch(y){i="parsererror",x.error=r=y||i}}catch(y){a("error caught: ",y),i="error",x.error=r=y||i}x.aborted&&(a("upload aborted"),i=null),x.status&&(i=x.status>=200&&x.status<300||304===x.status?"success":"error"),"success"===i?(m.success&&m.success.call(m.context,E,"success",x),S.resolve(x.responseText,"success",x),d&&e.event.trigger("ajaxSuccess",[x,m])):i&&(void 0===r&&(r=x.statusText),m.error&&m.error.call(m.context,x,i,r),S.reject(x,"error",r),d&&e.event.trigger("ajaxError",[x,m,r])),d&&e.event.trigger("ajaxComplete",[x,m]),d&&!--e.active&&e.event.trigger("ajaxStop"),m.complete&&m.complete.call(m.context,x,i),F=!0,m.timeout&&clearTimeout(j),setTimeout(function(){m.iframeTarget?v.attr("src",m.iframeSrc):v.remove(),x.responseXML=null},100)}}}var c,l,m,d,p,v,g,x,y,b,T,j,w=f[0],S=e.Deferred();if(S.abort=function(e){x.abort(e)},r)for(l=0;l<h.length;l++)c=e(h[l]),i?c.prop("disabled",!1):c.removeAttr("disabled");if(m=e.extend(!0,{},e.ajaxSettings,t),m.context=m.context||m,p="jqFormIO"+(new Date).getTime(),m.iframeTarget?(v=e(m.iframeTarget),b=v.attr2("name"),b?p=b:v.attr2("name",p)):(v=e('<iframe name="'+p+'" src="'+m.iframeSrc+'" />'),v.css({position:"absolute",top:"-1000px",left:"-1000px"})),g=v[0],x={aborted:0,responseText:null,responseXML:null,status:0,statusText:"n/a",getAllResponseHeaders:function(){},getResponseHeader:function(){},setRequestHeader:function(){},abort:function(t){var r="timeout"===t?"timeout":"aborted";a("aborting upload... "+r),this.aborted=1;try{g.contentWindow.document.execCommand&&g.contentWindow.document.execCommand("Stop")}catch(n){}v.attr("src",m.iframeSrc),x.error=r,m.error&&m.error.call(m.context,x,r,t),d&&e.event.trigger("ajaxError",[x,m,r]),m.complete&&m.complete.call(m.context,x,r)}},d=m.global,d&&0===e.active++&&e.event.trigger("ajaxStart"),d&&e.event.trigger("ajaxSend",[x,m]),m.beforeSend&&m.beforeSend.call(m.context,x,m)===!1)return m.global&&e.active--,S.reject(),S;if(x.aborted)return S.reject(),S;y=w.clk,y&&(b=y.name,b&&!y.disabled&&(m.extraData=m.extraData||{},m.extraData[b]=y.value,"image"==y.type&&(m.extraData[b+".x"]=w.clk_x,m.extraData[b+".y"]=w.clk_y)));var D=1,k=2,A=e("meta[name=csrf-token]").attr("content"),L=e("meta[name=csrf-param]").attr("content");L&&A&&(m.extraData=m.extraData||{},m.extraData[L]=A),m.forceSync?o():setTimeout(o,10);var E,M,F,O=50,X=e.parseXML||function(e,t){return window.ActiveXObject?(t=new ActiveXObject("Microsoft.XMLDOM"),t.async="false",t.loadXML(e)):t=(new DOMParser).parseFromString(e,"text/xml"),t&&t.documentElement&&"parsererror"!=t.documentElement.nodeName?t:null},C=e.parseJSON||function(e){return window.eval("("+e+")")},_=function(t,r,a){var n=t.getResponseHeader("content-type")||"",i="xml"===r||!r&&n.indexOf("xml")>=0,o=i?t.responseXML:t.responseText;return i&&"parsererror"===o.documentElement.nodeName&&e.error&&e.error("parsererror"),a&&a.dataFilter&&(o=a.dataFilter(o,r)),"string"==typeof o&&("json"===r||!r&&n.indexOf("json")>=0?o=C(o):("script"===r||!r&&n.indexOf("javascript")>=0)&&e.globalEval(o)),o};return S}if(!this.length)return a("ajaxSubmit: skipping submit process - no element selected"),this;var u,c,l,f=this;"function"==typeof t?t={success:t}:void 0===t&&(t={}),u=t.type||this.attr2("method"),c=t.url||this.attr2("action"),l="string"==typeof c?e.trim(c):"",l=l||window.location.href||"",l&&(l=(l.match(/^([^#]+)/)||[])[1]),t=e.extend(!0,{url:l,success:e.ajaxSettings.success,type:u||e.ajaxSettings.type,iframeSrc:/^https/i.test(window.location.href||"")?"javascript:false":"about:blank"},t);var m={};if(this.trigger("form-pre-serialize",[this,t,m]),m.veto)return a("ajaxSubmit: submit vetoed via form-pre-serialize trigger"),this;if(t.beforeSerialize&&t.beforeSerialize(this,t)===!1)return a("ajaxSubmit: submit aborted via beforeSerialize callback"),this;var d=t.traditional;void 0===d&&(d=e.ajaxSettings.traditional);var p,h=[],v=this.formToArray(t.semantic,h);if(t.data&&(t.extraData=t.data,p=e.param(t.data,d)),t.beforeSubmit&&t.beforeSubmit(v,this,t)===!1)return a("ajaxSubmit: submit aborted via beforeSubmit callback"),this;if(this.trigger("form-submit-validate",[v,this,t,m]),m.veto)return a("ajaxSubmit: submit vetoed via form-submit-validate trigger"),this;var g=e.param(v,d);p&&(g=g?g+"&"+p:p),"GET"==t.type.toUpperCase()?(t.url+=(t.url.indexOf("?")>=0?"&":"?")+g,t.data=null):t.data=g;var x=[];if(t.resetForm&&x.push(function(){f.resetForm()}),t.clearForm&&x.push(function(){f.clearForm(t.includeHidden)}),!t.dataType&&t.target){var y=t.success||function(){};x.push(function(r){var a=t.replaceTarget?"replaceWith":"html";e(t.target)[a](r).each(y,arguments)})}else t.success&&x.push(t.success);if(t.success=function(e,r,a){for(var n=t.context||this,i=0,o=x.length;o>i;i++)x[i].apply(n,[e,r,a||f,f])},t.error){var b=t.error;t.error=function(e,r,a){var n=t.context||this;b.apply(n,[e,r,a,f])}}if(t.complete){var T=t.complete;t.complete=function(e,r){var a=t.context||this;T.apply(a,[e,r,f])}}var j=e("input[type=file]:enabled",this).filter(function(){return""!==e(this).val()}),w=j.length>0,S="multipart/form-data",D=f.attr("enctype")==S||f.attr("encoding")==S,k=n.fileapi&&n.formdata;a("fileAPI :"+k);var A,L=(w||D)&&!k;t.iframe!==!1&&(t.iframe||L)?t.closeKeepAlive?e.get(t.closeKeepAlive,function(){A=s(v)}):A=s(v):A=(w||D)&&k?o(v):e.ajax(t),f.removeData("jqxhr").data("jqxhr",A);for(var E=0;E<h.length;E++)h[E]=null;return this.trigger("form-submit-notify",[this,t]),this},e.fn.ajaxForm=function(n){if(n=n||{},n.delegation=n.delegation&&e.isFunction(e.fn.on),!n.delegation&&0===this.length){var i={s:this.selector,c:this.context};return!e.isReady&&i.s?(a("DOM not ready, queuing ajaxForm"),e(function(){e(i.s,i.c).ajaxForm(n)}),this):(a("terminating; zero elements found by selector"+(e.isReady?"":" (DOM not ready)")),this)}return n.delegation?(e(document).off("submit.form-plugin",this.selector,t).off("click.form-plugin",this.selector,r).on("submit.form-plugin",this.selector,n,t).on("click.form-plugin",this.selector,n,r),this):this.ajaxFormUnbind().bind("submit.form-plugin",n,t).bind("click.form-plugin",n,r)},e.fn.ajaxFormUnbind=function(){return this.unbind("submit.form-plugin click.form-plugin")},e.fn.formToArray=function(t,r){var a=[];if(0===this.length)return a;var i,o=this[0],s=this.attr("id"),u=t?o.getElementsByTagName("*"):o.elements;if(u&&!/MSIE [678]/.test(navigator.userAgent)&&(u=e(u).get()),s&&(i=e(':input[form="'+s+'"]').get(),i.length&&(u=(u||[]).concat(i))),!u||!u.length)return a;var c,l,f,m,d,p,h;for(c=0,p=u.length;p>c;c++)if(d=u[c],f=d.name,f&&!d.disabled)if(t&&o.clk&&"image"==d.type)o.clk==d&&(a.push({name:f,value:e(d).val(),type:d.type}),a.push({name:f+".x",value:o.clk_x},{name:f+".y",value:o.clk_y}));else if(m=e.fieldValue(d,!0),m&&m.constructor==Array)for(r&&r.push(d),l=0,h=m.length;h>l;l++)a.push({name:f,value:m[l]});else if(n.fileapi&&"file"==d.type){r&&r.push(d);var v=d.files;if(v.length)for(l=0;l<v.length;l++)a.push({name:f,value:v[l],type:d.type});else a.push({name:f,value:"",type:d.type})}else null!==m&&"undefined"!=typeof m&&(r&&r.push(d),a.push({name:f,value:m,type:d.type,required:d.required}));if(!t&&o.clk){var g=e(o.clk),x=g[0];f=x.name,f&&!x.disabled&&"image"==x.type&&(a.push({name:f,value:g.val()}),a.push({name:f+".x",value:o.clk_x},{name:f+".y",value:o.clk_y}))}return a},e.fn.formSerialize=function(t){return e.param(this.formToArray(t))},e.fn.fieldSerialize=function(t){var r=[];return this.each(function(){var a=this.name;if(a){var n=e.fieldValue(this,t);if(n&&n.constructor==Array)for(var i=0,o=n.length;o>i;i++)r.push({name:a,value:n[i]});else null!==n&&"undefined"!=typeof n&&r.push({name:this.name,value:n})}}),e.param(r)},e.fn.fieldValue=function(t){for(var r=[],a=0,n=this.length;n>a;a++){var i=this[a],o=e.fieldValue(i,t);null===o||"undefined"==typeof o||o.constructor==Array&&!o.length||(o.constructor==Array?e.merge(r,o):r.push(o))}return r},e.fieldValue=function(t,r){var a=t.name,n=t.type,i=t.tagName.toLowerCase();if(void 0===r&&(r=!0),r&&(!a||t.disabled||"reset"==n||"button"==n||("checkbox"==n||"radio"==n)&&!t.checked||("submit"==n||"image"==n)&&t.form&&t.form.clk!=t||"select"==i&&-1==t.selectedIndex))return null;if("select"==i){var o=t.selectedIndex;if(0>o)return null;for(var s=[],u=t.options,c="select-one"==n,l=c?o+1:u.length,f=c?o:0;l>f;f++){var m=u[f];if(m.selected){var d=m.value;if(d||(d=m.attributes&&m.attributes.value&&!m.attributes.value.specified?m.text:m.value),c)return d;s.push(d)}}return s}return e(t).val()},e.fn.clearForm=function(t){return this.each(function(){e("input,select,textarea",this).clearFields(t)})},e.fn.clearFields=e.fn.clearInputs=function(t){var r=/^(?:color|date|datetime|email|month|number|password|range|search|tel|text|time|url|week)$/i;return this.each(function(){var a=this.type,n=this.tagName.toLowerCase();r.test(a)||"textarea"==n?this.value="":"checkbox"==a||"radio"==a?this.checked=!1:"select"==n?this.selectedIndex=-1:"file"==a?/MSIE/.test(navigator.userAgent)?e(this).replaceWith(e(this).clone(!0)):e(this).val(""):t&&(t===!0&&/hidden/.test(a)||"string"==typeof t&&e(this).is(t))&&(this.value="")})},e.fn.resetForm=function(){return this.each(function(){("function"==typeof this.reset||"object"==typeof this.reset&&!this.reset.nodeType)&&this.reset()})},e.fn.enable=function(e){return void 0===e&&(e=!0),this.each(function(){this.disabled=!e})},e.fn.selected=function(t){return void 0===t&&(t=!0),this.each(function(){var r=this.type;if("checkbox"==r||"radio"==r)this.checked=t;else if("option"==this.tagName.toLowerCase()){var a=e(this).parent("select");t&&a[0]&&"select-one"==a[0].type&&a.find("option").selected(!1),this.selected=t}})},e.fn.ajaxSubmit.debug=!1});

/*
* jquery.client
*/
!function(){var i={init:function(){this.browser=this.searchString(this.dataBrowser)||"An unknown browser",this.version=this.searchVersion(navigator.userAgent)||this.searchVersion(navigator.appVersion)||"an unknown version",this.OS=this.searchString(this.dataOS)||"an unknown OS"},searchString:function(i){for(var n=0;n<i.length;n++){var r=i[n].string,t=i[n].prop;if(this.versionSearchString=i[n].versionSearch||i[n].identity,r){if(-1!=r.indexOf(i[n].subString))return i[n].identity}else if(t)return i[n].identity}},searchVersion:function(i){var n=i.indexOf(this.versionSearchString);if(-1!=n)return parseFloat(i.substring(n+this.versionSearchString.length+1))},dataBrowser:[{string:navigator.userAgent,subString:"Chrome",identity:"Chrome"},{string:navigator.userAgent,subString:"OmniWeb",versionSearch:"OmniWeb/",identity:"OmniWeb"},{string:navigator.vendor,subString:"Apple",identity:"Safari",versionSearch:"Version"},{prop:window.opera,identity:"Opera"},{string:navigator.vendor,subString:"iCab",identity:"iCab"},{string:navigator.vendor,subString:"KDE",identity:"Konqueror"},{string:navigator.userAgent,subString:"Firefox",identity:"Firefox"},{string:navigator.vendor,subString:"Camino",identity:"Camino"},{string:navigator.userAgent,subString:"Netscape",identity:"Netscape"},{string:navigator.userAgent,subString:"MSIE",identity:"Explorer",versionSearch:"MSIE"},{string:navigator.userAgent,subString:"Gecko",identity:"Mozilla",versionSearch:"rv"},{string:navigator.userAgent,subString:"Mozilla",identity:"Netscape",versionSearch:"Mozilla"}],dataOS:[{string:navigator.platform,subString:"Win",identity:"Windows"},{string:navigator.platform,subString:"Mac",identity:"Mac"},{string:navigator.userAgent,subString:"iPhone",identity:"iPhone/iPod"},{string:navigator.platform,subString:"Linux",identity:"Linux"}]};i.init(),window.$.client={os:i.OS,browser:i.browser}}();



/* 
* Send upload notification 
* to selected users, or refresh page
*/
function notifyusers() {

    var locazio = location.pathname;
    var queri = location.search;
    queri = queri.replace('&response', '');
    queri = queri.replace('?response', '');
    queri = queri.replace('?del', '?nodel');
    queri = queri.replace('&del', '&nodel');
    if (queri == "") { queri = "?" } else { queri = queri+"&"; }

	var anyUserChecked = $('#userslist :checkbox:checked').length > 0;

	if (anyUserChecked == true) {

	    var now = $.now();

	    $.ajax({
	        cache: false,
	        type: "POST",
	        url: "vfm-admin/ajax/sendupnotif.php?t="+now,
	        data: $("#userslist").serialize()

	    })
	    .done(function(msg) {
	    	setTimeout(function() {
	            location.href = locazio+queri+"response"
	        }, 800);
	    });

	} else {
		setTimeout(function() {
	        location.href = locazio+queri+"response"
	    }, 800);
	}
}
/*
* call resumable.js
*/
function resumableJsSetup($android, $target, $placeholder, $singleprogress) {
    $android = $android || 'no';
    $singleprogress = $singleprogress || false;

    var ua = navigator.userAgent.toLowerCase();
    var android = $android;

    var r = new Resumable({
	    target: 'vfm-admin/chunk.php?loc='+$target,
	    simultaneousUploads:2,
	    prioritizeFirstAndLastChunk: true,

	    // maxFiles: 1, // uncomment this to disable multiple uploading
		// maxFileSize: 10*1024*1024, // uncomment this to limit the max file size (the example sets 10Mb)

	    minFileSizeErrorCallback:function(file, errorCount) {
	        setTimeout(function() {
	            alert(file.fileName||file.name +' is not valid.');
	        }, 1000);
	    }
    });

    var percentVal = 0;
    var roundval = 0;

    if (r.support && android == 'no') {

        r.assignBrowse(document.getElementById('upchunk'));
        r.assignDrop(document.getElementById('uparea'));

        $("#fileToUpload").attr("placeholder", $placeholder);

        r.on('uploadStart', function(){
            $("#resumer").remove();
           	$("#upchunk").before("<button class=\"btn btn-primary\" id=\"resumer\"><i class=\"fa fa-pause\"></i></button>");
            window.onbeforeunload = function() {
                return 'Are you sure you want to leave?';
            }
	        $('#resumer').on('click', function(){
	        	r.pause();
	        });
        });
        r.on('pause', function(){
            $("#resumer").remove();
            $("#upchunk").before("<button class=\"btn btn-primary\" id=\"resumer\"><i class=\"fa fa-play\"></i></button>");

	        $('#resumer').on('click', function(){
	        	r.upload();
	        });
            
        });

        r.on('progress', function(){
            percentVal = r.progress()*100;
            roundval = percentVal.toFixed(1);

            $('.upbar p').html(roundval+'%');
            $(".upbar").width(percentVal+'%');
        });

        // upload progress for individual files
        if ($singleprogress == true) { 
            r.on('fileProgress', function(file){
                percentVal = file.progress(true)*100;
                $('.upbarfile p').html(file.fileName);
                $(".upbarfile").width(percentVal+'%');
            });
        }

        r.on('error', function(message, file){
            console.log(message, file);
        });

        r.on('fileAdded', function(file, event){
            r.upload();
        });

        // add file path 
        // to notification message
        r.on('fileSuccess', function(file, event){
            var newinput = '<input type="hidden" name="filename[]" value="'+file.fileName+'">';
            $("#userslist").append(newinput);
        });
        
        r.on('complete', function(){
            window.onbeforeunload = null;
            notifyusers();
        });

        // Drag & Drop
        $('#uparea').on(
            'dragstart dragenter dragover',
            function(e) {
                $(".overdrag").css('display','block');
        });
        $('.overdrag').on(
            'drop dragleave dragend mouseup',
            function(e) {
                $(".overdrag").css('display','none');
        });

    } else {

        // Resumable.js is not supported, fall back on the form.js method
        var ie = ((document.all) ? true : false);

        $("#upchunk").remove();
        $('#upformsubmit').prop('disabled', true).show();

        if (ie || ($.client.os === 'Windows' && $.client.browser === 'Safari' ) || android === 'yes') {
            
            // form.js is not supported ( < IE 10 or Safari on Wondows), fall back on the old classic form method
            $('#upload_file').css('display','table-cell');
            $('.ie_hidden').remove();
            $(document).on('click', '#upformsubmit', function(e) {
                $('#fileToUpload').val('Loading....');
            });
        } else {

            $(document).on('click', '#fileToUpload', function() {
                $('.upload_file').trigger('click');
            });
            $(document).on('click', '#upformsubmit', function(e) {
                e.preventDefault();
                $('.upload_file').trigger('click');
            });
        }

        $(document).ready(function(){

            var progress = $('#progress-up');
            var probar = $('.upbar');
            var prop = $('.upbar p');

            $('#upForm').ajaxForm({
                beforeSend: function() {            
                    progress.css('opacity', 1);
                },
                uploadProgress: function(event, position, total, percentComplete) {
                    var percentVal = percentComplete ;
                    var roundval = percentComplete.toFixed(1);
                    
                    probar.width(percentVal);
                    prop.html(roundval);
                },
                success: function() {
                    var percentVal = '100';
                    probar.width(percentVal+'%');
                    prop.html(percentVal+'%');
                },
                complete: function(xhr) {
					notifyusers();
                }
            });
        });
    
        $('.btn-file :file').on('fileselect', function (event, numFiles, label) {
            var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;

	        // add file path 
	        // to notification message
		    var files = $(this)[0].files;
		    for (var i = 0; i < files.length; i++) {
		        var newinput = '<input type="hidden" name="filename[]" value="'+files[i].name+'">';
		        $("#userslist").append(newinput);
		    }
            if (input.length) {
                input.val(log);
                // auto start upload after select if browser is not IE
                if (!ie) {
                    $("#upForm").submit();
                } else {
                    $('#upformsubmit').prop('disabled', false);
                }
            }
        });
    }
}
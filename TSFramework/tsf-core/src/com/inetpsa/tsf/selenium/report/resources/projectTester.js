
var idSuccess = new Array();
var idFailure = new Array();

function initPage(){
    initStatusBar();
}

function initStatusBar(){
    if (document.layers) {
        document.captureEvents(Event.MOUSEOVER | Event.MOUSEOUT);
    }
    document.onmouseover = hideStatusBar;
    document.onmouseout = hideStatusBar;
}

function hideStatusBar(){
    window.status = ''
    return true;
}

function changeCss(theclass, element, value){
    var cssRules;
    if (document.all) {
        cssRules = 'rules';
    } else if (document.getElementById) {
    	cssRules = 'cssRules';
    }
    for (var i = 0; i < document.styleSheets.length; i++) {
    	// On teste si document.styleSheets[i][cssRules] est non null, car Chrome a du mal...
    	if (document.styleSheets[i][cssRules]) {
	        for (var j = 0; j < document.styleSheets[i][cssRules].length; j++) {
	        	// Chrome a décidé que c'etait mieux de tout foutre en minuscule :)
	            if (document.styleSheets[i][cssRules][j].selectorText.toLowerCase() == theclass.toLowerCase()) {
	                document.styleSheets[i][cssRules][j].style[element] = value;
	            }
	        }
    	}
    }
}

function showHideSuccess(){
    showHide(idSuccess, "success");
}

function showHideFailure(){
    showHide(idFailure, "failure");
}

function showHideGroup(theclass){
    var cssRules;
    if (document.all) {
        cssRules = 'rules';
    } else if (document.getElementById) {
        cssRules = 'cssRules';
    }
    for (var i = 1; i < document.styleSheets.length; i++) {
    	// On teste si document.styleSheets[i][cssRules] est non null, car Chrome a du mal avec cssRules...
    	if (document.styleSheets[i][cssRules]) {
			for (var j = 0; j < document.styleSheets[i][cssRules].length; j++) {
			    aClass = document.styleSheets[i][cssRules][j].selectorText.toLowerCase();
			    if (aClass == theclass.toLowerCase()) {
			        style = document.styleSheets[i][cssRules][j].style["display"];
			        document.styleSheets[i][cssRules][j].style["display"] = style == "none" ? "" : "none";
			    }
			}
    	}   
    }
}

function showHide(idArray, idCheck){
    var form = document.forms[0];
    var checked = document.getElementById(idCheck).checked;
    var display = checked ? "" : "none";
    for (i = 0; i < idArray.length; i++) {
        changeCss("." + idArray[i], "display", display);
    }
}

function setChecked(idCheck, checked){
    document.getElementById(idCheck).checked = checked;
}

function initShowOptions(){
    initPage();
    var show = getURLParam("show");
    if (show == "single") {
        id = getURLParam("id");
        changeCss("." + id, "display", "");
    }
    else
        if (show == "success") {
            setChecked("success", true);
            setChecked("failure", false);
            showHideSuccess();
        }
        else
            if (show == "failure") {
                setChecked("success", false);
                setChecked("failure", true);
                showHideFailure();
            }
            else {
                setChecked("success", true);
                setChecked("failure", true);
                showHideSuccess();
                showHideFailure();
            }
}

function getURLParam(name){
    var value = "";
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.location.href);
    if (results != null) {
        value = results[1];
    }
    return value;
}


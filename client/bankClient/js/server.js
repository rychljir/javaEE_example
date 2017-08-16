var serverURL = "http://localhost:8080/";

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

var getAjaxData = function (object, resultFunction, xHeaders) {
    if (xHeaders === undefined) {
        xHeaders = {'Content-Type':'application/json' };
    }

    $.ajax({
        url: serverURL + object.path,
        headers: xHeaders,
        type: "GET",
        Accept: "application/json",
        cache: false,
        success: function (result) {
            console.log(result);
            resultFunction.call(object, result);
        },
        complete: function (xhr, textStatus) {
            crudResult(xhr.status);
        }
    });
}

var postAjaxData = function (object, path, data, resultFunction) {
    $.ajax({
        url: serverURL + path,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        Accept: "application/json",
        cache: false,
        dataType: "json",
        data: JSON.stringify(data),
        success: function (result) {
            resultFunction.call(object, result);
        },
        complete: function (xhr, textStatus) {
            crudResult(xhr.status);
        }
    });
}

var putAjaxData = function (object, path, data, resultFunction, xHeaders) {

    if (xHeaders === undefined) {
        xHeaders = {'Accept': 'application/json', 'Content-Type':'application/json'};
    }

    $.ajax({
        url: serverURL + path,
        headers: xHeaders,
        type: "PUT",
        Accept: "application/json",
        cache: false,
        dataType: "json",
        data: JSON.stringify(data),
        success: function (result) {
            resultFunction.call(object, result);
        },
        complete: function (xhr, textStatus) {
            crudResult(xhr.status);
        }
    });
}

var deleteAjaxData = function (object, path, resultFunction) {
    $.ajax({
        url: serverURL + path,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        type: "DELETE",
        Accept: "application/json",
        cache: false,
        success: function (result) {
            resultFunction.call(object, result);
        },
        complete: function (xhr, textStatus) {
            crudResult(xhr.status);
        }
    });
}

var getAjaxPersons = function (object, resultFunction, xHeaders) {
    if (xHeaders === undefined) {
        xHeaders = {'Content-Type':'application/json' };
    }

    $.ajax({
        url: serverURL + "person",
        headers: xHeaders,
        type: "GET",
        Accept: "application/json",
        cache: false,
        success: function (result) {
            console.log(result);
            resultFunction.call(object, result);
        },
        complete: function (xhr, textStatus) {
            crudResult(xhr.status);
        }
    });
}

var getAjaxAccounts = function (object, resultFunction, xHeaders) {
    if (xHeaders === undefined) {
        xHeaders = {'Content-Type':'application/json' };
    }

    $.ajax({
        url: serverURL + "account",
        headers: xHeaders,
        type: "GET",
        Accept: "application/json",
        cache: false,
        success: function (result) {
            console.log(result);
            resultFunction.call(object, result);
        },
        complete: function (xhr, textStatus) {
            crudResult(xhr.status);
        }
    });
}

var getAjaxDataWithPath = function (object, path,  resultFunction, xHeaders) {
    if (xHeaders === undefined) {
        xHeaders = {'Content-Type':'application/json' };
    }

    $.ajax({
        url: serverURL + path,
        headers: xHeaders,
        type: "GET",
        Accept: "application/json",
        cache: false,
        success: function (result) {
            console.log(result);
            resultFunction.call(object, result);
        },
        complete: function (xhr, textStatus) {
            crudResult(xhr.status);
        }
    });
}

var postCalculation = function (object, path,  resultFunction, xHeaders) {
    if (xHeaders === undefined) {
        xHeaders = {'Content-Type':'application/json' };
    }

    $.ajax({
        url: serverURL + path,
        headers: xHeaders,
        type: "POST",
        Accept: "application/json",
        cache: false,
        success: function (result) {
            console.log(result);
            resultFunction.call(object, result);
        },
        complete: function (xhr, textStatus) {
            crudResult(xhr.status);
        }
    });
}

var getCalculations = function (object, path,  resultFunction, xHeaders) {
    if (xHeaders === undefined) {
        xHeaders = {'Content-Type':'application/json' };
    }
    var cnt = 0;
    (function poll() {
        $.ajax({
            url: serverURL + path,
            headers: xHeaders,
            type: "GET",
            Accept: "application/json",
            cache: false,
            error: function(xhr_data) {
                // terminate the script
            },
            success: function(xhr_data) {
                if(typeof xhr_data === 'undefined'){
                    cnt++;
                    setTimeout(function() { poll(); }, 1000); // wait 15 seconds than call ajax request again
                }else{
                    if (xhr_data.status == 'pending') {
                        if (cnt < 20) {
                            cnt++;
                            setTimeout(function() { poll(); }, 1000); // wait 15 seconds than call ajax request again
                        }
                    } else {
                        resultFunction.call(object, xhr_data);
                    }
                }
            },
        });
    })();


}
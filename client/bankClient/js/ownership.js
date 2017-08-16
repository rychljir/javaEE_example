var Ownership = function(path, targetDiv) {
	this.path = path;
	this.targetDiv = targetDiv;
	this.data = [];

	var self = this;
}

Ownership.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}

Ownership.prototype.showAccounts = function() {
    var data = new Object();
    data.personId = $("#ownr_personInput").val();

    if (data.personId.length===0) {
    } else {
        var path = this.path+"?id="+data.personId;
        getAjaxDataWithPath(this, path, this.printResult);
    }
}

Ownership.prototype.printPersons = function(result) {
    if (result.length > 0) {
        var sel = document.getElementById('ownr_personInput');
        while (sel.firstChild) {
            sel.removeChild(sel.firstChild);
        }
        var fragment = document.createDocumentFragment();

        result.forEach(function(per, index) {
            var opt = document.createElement('option');
            opt.innerHTML = per.lastname + " " + per.firstname;
            opt.value = per.id;
            fragment.appendChild(opt);
        });
        sel.appendChild(fragment);
    }
}

Ownership.prototype.printResult = function(result) {
	this.data = [];
	$(this.targetDiv).empty();

	// result = result.destination;

	if (result.length > 0) {
        var html = '<table class="table table-striped">';
        html += '<thead id="desc_header"><tr>';
        html += '<th id="id">ID</th>';
        html += '<th id="number">Number</th>';
        html += '<th id="balance">Balance</th>';
        html += '<th id="currency">Currency</th>';
        html += '</tr></thead>';

        html += '<tbody>';
        for(var i=0; i<result.length; i++) {
            this.data[result[i].id] = result[i];

            html += '<tr>';
            html += '<td>' + result[i].id + '</td>';
            html += '<td>' + result[i].number + '</td>';
            html += '<td>' + result[i].balance + '</td>';
            html += '<td>' + result[i].currency + '</td>';
            html += '</tr>';
        }
        html += '</tbody>';
        html += '</table>';

        $(this.targetDiv).append(html);
    }else{
        var html = '<table class="table table-striped">';
        html += '<thead id="desc_header"><tr>';
        html += '<th id="id">ID</th>';
        html += '<th id="number">Number</th>';
        html += '<th id="balance">Balance</th>';
        html += '<th id="currency">Currency</th>';
        html += '</tr></thead>';

        html += '<tbody>';
        html += '</tbody>';
        html += '</table>';

        $(this.targetDiv).append(html);
    }
}
var Mortgage = function(path, targetDiv, targetModal) {
	this.path = path;
	this.targetDiv = targetDiv;
	this.targetModal = targetModal;
	this.data = [];

	var self = this;
}

Mortgage.prototype.prefillModalData = function(path) {
	$('#edit_mortgage_path').val( path );
	$('#edit_mortgage_valueInput').val( this.data[path].value );
	$('#edit_mortgage_yearsInput').val( this.data[path].years );
}

Mortgage.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}

Mortgage.prototype.printAccountsForInsert = function(result) {
    if (result.length > 0) {
        var sel = document.getElementById('mortgage_accountInput');
        while (sel.firstChild) {
            sel.removeChild(sel.firstChild);
        }
        var fragment = document.createDocumentFragment();

        result.forEach(function(per, index) {
            var opt = document.createElement('option');
            opt.innerHTML = per.number;
            opt.value = per.id;
            fragment.appendChild(opt);
        });
        sel.appendChild(fragment);
    }
}

Mortgage.prototype.printAccountsForUpdate = function(result) {
    if (result.length > 0) {
        var sel = document.getElementById('edit_mortgage_accountInput');
        while (sel.firstChild) {
            sel.removeChild(sel.firstChild);
        }
        var fragment = document.createDocumentFragment();

        result.forEach(function(per, index) {
            var opt = document.createElement('option');
            opt.innerHTML = per.number;
            opt.value = per.id;
            fragment.appendChild(opt);
        });
        sel.appendChild(fragment);
    }
}

Mortgage.prototype.create = function() {
	var data = new Object();
    data.value = $("#mortgage_valueInput").val();
    data.years = $("#mortgage_yearsInput").val();
    data.account = $("#mortgage_accountInput").val();

	if (data.value.length===0) {
	} else {
        var path = this.path+"?&value="+data.value+"&years="+data.years+"&idaccount="+data.account;
		postAjaxData(this, path, data, this.alertFunction);
		$('#mortgage_form')[0].reset();
	}
}

Mortgage.prototype.update = function() {
    var data = new Object();
    data.value = $("#edit_mortgage_valueInput").val();
    data.years = $("#edit_mortgage_yearsInput").val();
    data.account = $("#edit_mortgage_accountInput").val();
	data.id = $("#edit_mortgage_path").val();

	if (data.value.length===0 || data.years.length===0) {
	} else {
        var path = this.path+"?id="+data.id+"&value="+data.value+"&years="+data.years+"&idaccount="+data.account;
		putAjaxData(this, path, data, this.alertFunction);
	}
	return false;
}

Mortgage.prototype.printResult = function(result) {
	this.data = [];
	$(this.targetDiv).empty();

	// result = result.destination;

	if (result.length > 0) {
		var html = '<table class="table table-striped">';
		html += '<thead id="desc_header"><tr>';
		html += '<th id="id">ID</th>';
		html += '<th id="value">Value</th>';
		html += '<th id="years">Years</th>';
		html += '<th id="account">Account number</th>';
    	html += '</tr></thead>';

    	html += '<tbody>';
		for(var i=0; i<result.length; i++) {
			this.data[result[i].id] = result[i];

            html += '<tr>';
			html += '<td>' + result[i].id + '</td>';
			html += '<td>' + result[i].value + '</td>';
			html += '<td>' + result[i].years + '</td>';
			html += '<td>' + result[i].account + '</td>';
            html += '<td><span class="update_button glyphicon glyphicon-pencil" aria-hidden="true" path="' + result[i].id  +'" data-toggle="modal" data-target="'+this.targetModal+'"></span></td>';
            html += '<td><span class="delete_button glyphicon glyphicon-trash" aria-hidden="true" path="'+ this.path +'?id=' + result[i].id  +'"></span></td>';
            html += '</tr>';
        }
        html += '</tbody>';
        html += '</table>';

        $(this.targetDiv).append(html);
    }
}
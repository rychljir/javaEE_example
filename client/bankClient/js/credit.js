var Credit = function(path, targetDiv, targetModal) {
	this.path = path;
	this.targetDiv = targetDiv;
	this.targetModal = targetModal;
	this.data = [];

	var self = this;
}

Credit.prototype.prefillModalData = function(path) {
	$('#edit_credit_path').val( path );
	$('#edit_credit_valueInput').val( this.data[path].value );
	$('#edit_credit_percentageInput').val( this.data[path].percentage );
}

Credit.prototype.printAccountsForInsert = function(result) {
    if (result.length > 0) {
        var sel = document.getElementById('credit_accountInput');
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

Credit.prototype.printAccountsForUpdate = function(result) {
    if (result.length > 0) {
        var sel = document.getElementById('edit_credit_accountInput');
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


Credit.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}

Credit.prototype.create = function() {
	var data = new Object();
    data.value = $("#credit_valueInput").val();
    data.percentage = $("#credit_percentageInput").val();
	data.account = $("#credit_accountInput").val();

	if (data.value.length===0) {
	} else {
        var path = this.path+"?&value="+data.value+"&percentage="+data.percentage+"&idaccount="+data.account;
		postAjaxData(this, path, data, this.alertFunction);
		$('#credit_form')[0].reset();
	}
}

Credit.prototype.update = function() {
    var data = new Object();
    data.value = $("#edit_credit_valueInput").val();
    data.percentage = $("#edit_credit_percentageInput").val();
    data.account = $("#edit_credit_accountInput").val();
	data.id = $("#edit_credit_path").val();

	if (data.value.length===0 || data.percentage.length===0) {
	} else {
        var path = this.path+"?id="+data.id+"&value="+data.value+"&percentage="+data.percentage+"&idaccount="+data.account;
		putAjaxData(this, path, data, this.alertFunction);
	}
	return false;
}

Credit.prototype.printResult = function(result) {
	this.data = [];
	$(this.targetDiv).empty();

	// result = result.destination;

	if (result.length > 0) {
		var html = '<table class="table table-striped">';
		html += '<thead id="desc_header"><tr>';
		html += '<th id="id">ID</th>';
		html += '<th id="value">Value</th>';
		html += '<th id="percentage">Percentage</th>';
		html += '<th id="account">Account number</th>';
    	html += '</tr></thead>';

    	html += '<tbody>';
		for(var i=0; i<result.length; i++) {
			this.data[result[i].id] = result[i];

            html += '<tr>';
			html += '<td>' + result[i].id + '</td>';
			html += '<td>' + result[i].value + '</td>';
			html += '<td>' + result[i].percentage + '</td>';
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
var Account = function(path, targetDiv, targetModal) {
	this.path = path;
	this.targetDiv = targetDiv;
	this.targetModal = targetModal;
	this.data = [];

	var self = this;
}

Account.prototype.prefillModalData = function(path) {
	$('#edit_account_path').val( path );
	$('#edit_account_numberInput').val( this.data[path].number );
	$('#edit_account_balanceInput').val( this.data[path].balance );
	$('#edit_account_currencyInput').val( this.data[path].currency );
}

Account.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}

Account.prototype.printPersonsForInsert = function(result) {
    if (result.length > 0) {
        var sel = document.getElementById('account_personInput');
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

Account.prototype.printPersonsForUpdate = function(result) {
    if (result.length > 0) {
        var sel = document.getElementById('edit_account_personInput');
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

Account.prototype.create = function() {
	var data = new Object();
    data.number = $("#account_numberInput").val();
    data.balance = $("#account_balanceInput").val();
	data.currency = $("#account_currencyInput").val();
    data.person = $("#account_personInput").val();

	if (data.number.length===0) {
	} else {
        var path = this.path+"?&number="+data.number+"&balance="+data.balance+"&currency="+data.currency+"&idperson="+data.person;
		postAjaxData(this, path, data, this.alertFunction);
		$('#account_form')[0].reset();
	}
}

Account.prototype.update = function() {
    var data = new Object();
    data.number = $("#edit_account_numberInput").val();
    data.balance = $("#edit_account_balanceInput").val();
    data.currency = $("#edit_account_currencyInput").val();
    data.person = $("#edit_account_personInput").val();
	data.id = $("#edit_account_path").val();

	if (data.number.length===0 || data.balance.length===0 || data.currency.length===0) {
	} else {
        var path = this.path+"?id="+data.id+"&number="+data.number+"&balance="+data.balance+"&currency="+data.currency+"&idperson="+data.person;
		putAjaxData(this, path, data, this.alertFunction);
	}
	return false;
}

Account.prototype.printResult = function(result) {
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
        html += '<th id="person">Person</th>';
    	html += '</tr></thead>';

    	html += '<tbody>';
		for(var i=0; i<result.length; i++) {
			this.data[result[i].id] = result[i];

            html += '<tr>';
			html += '<td>' + result[i].id + '</td>';
			html += '<td>' + result[i].number + '</td>';
			html += '<td>' + result[i].balance + '</td>';
			html += '<td>' + result[i].currency + '</td>';
            html += '<td>' + result[i].person + '</td>';
            html += '<td><span class="update_button glyphicon glyphicon-pencil" aria-hidden="true" path="' + result[i].id  +'" data-toggle="modal" data-target="'+this.targetModal+'"></span></td>';
            html += '<td><span class="delete_button glyphicon glyphicon-trash" aria-hidden="true" path="'+ this.path +'?id=' + result[i].id  +'"></span></td>';
            html += '</tr>';
        }
        html += '</tbody>';
        html += '</table>';

        $(this.targetDiv).append(html);
    }
}
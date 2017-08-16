var Client = function(path, targetDiv, targetModal) {
	this.path = path;
	this.targetDiv = targetDiv;
	this.targetModal = targetModal;
	this.data = [];

	var self = this;
}

Client.prototype.prefillModalData = function(path) {
	$('#edit_client_path').val( path );
	$('#edit_client_firstnameInput').val( this.data[path].firstname );
	$('#edit_client_lastnameInput').val( this.data[path].lastname );
	$('#edit_client_birthInput').val( this.data[path].birth );
    $('#edit_client_numberInput').val( this.data[path].number );
}

Client.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}

Client.prototype.create = function() {
	var data = new Object();
    data.firstname = $("#client_firstnameInput").val();
    data.lastname = $("#client_lastnameInput").val();
	data.birth = $("#client_birthInput").val();
    data.number = $("#client_numberInput").val();

	if (data.firstname.length===0) {
	} else {
        var path = this.path+"?&firstname="+data.firstname+"&lastname="+data.lastname+"&birth="+data.birth+"&number="+data.number;
		postAjaxData(this, path, data, this.alertFunction);
		$('#client_form')[0].reset();
	}
}

Client.prototype.update = function() {
    var data = new Object();
    data.firstname = $("#edit_client_firstnameInput").val();
    data.lastname = $("#edit_client_lastnameInput").val();
    data.birth = $("#edit_client_birthInput").val();
    data.number = $("#edit_client_numberInput").val();
	data.id = $("#edit_client_path").val();

	if (data.firstname.length===0 || data.lastname.length===0 || data.birth.length===0 || data.number.length===0) {
	} else {
        var path = this.path+"?id="+data.id+"&firstname="+data.firstname+"&lastname="+data.lastname+"&birth="+data.birth+"&number="+data.number;
		putAjaxData(this, path, data, this.alertFunction);
	}
	return false;
}

Client.prototype.printResult = function(result) {
	this.data = [];
	$(this.targetDiv).empty();

	// result = result.destination;

	if (result.length > 0) {
		var html = '<table class="table table-striped">';
		html += '<thead id="desc_header"><tr>';
		html += '<th id="id">ID</th>';
		html += '<th id="firstname">Firstname</th>';
		html += '<th id="lastname">Lastname</th>';
		html += '<th id="birth">Birth</th>';
        html += '<th id="number">Client number</th>';
    	html += '</tr></thead>';

    	html += '<tbody>';
		for(var i=0; i<result.length; i++) {
			this.data[result[i].id] = result[i];

            html += '<tr>';
			html += '<td>' + result[i].id + '</td>';
			html += '<td>' + result[i].firstname + '</td>';
			html += '<td>' + result[i].lastname + '</td>';
			html += '<td>' + result[i].birth + '</td>';
            html += '<td>' + result[i].number + '</td>';
            html += '<td><span class="update_button glyphicon glyphicon-pencil" aria-hidden="true" path="' + result[i].id  +'" data-toggle="modal" data-target="'+this.targetModal+'"></span></td>';
            html += '<td><span class="delete_button glyphicon glyphicon-trash" aria-hidden="true" path="'+ this.path +'?id=' + result[i].id  +'"></span></td>';
            html += '</tr>';
        }
        html += '</tbody>';
        html += '</table>';

        $(this.targetDiv).append(html);
    }
}
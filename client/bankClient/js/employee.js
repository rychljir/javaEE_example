var Employee = function(path, targetDiv, targetModal) {
	this.path = path;
	this.targetDiv = targetDiv;
	this.targetModal = targetModal;
	this.data = [];

	var self = this;
}

Employee.prototype.prefillModalData = function(path) {
	$('#edit_empl_path').val( path );
	$('#edit_empl_firstnameInput').val( this.data[path].firstname );
	$('#edit_empl_lastnameInput').val( this.data[path].lastname );
	$('#edit_empl_birthInput').val( this.data[path].birth );
    $('#edit_empl_salaryInput').val( this.data[path].salary );
}

Employee.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}

Employee.prototype.create = function() {
	var data = new Object();
    data.firstname = $("#empl_firstnameInput").val();
    data.lastname = $("#empl_lastnameInput").val();
	data.birth = $("#empl_birthInput").val();
    data.salary = $("#empl_salaryInput").val();

	if (data.firstname.length===0) {
	} else {
        var path = this.path+"?&firstname="+data.firstname+"&lastname="+data.lastname+"&birth="+data.birth+"&salary="+data.salary;
		postAjaxData(this, path, data, this.alertFunction);
		$('#empl_form')[0].reset();
	}
}

Employee.prototype.update = function() {
    var data = new Object();
    data.firstname = $("#edit_empl_firstnameInput").val();
    data.lastname = $("#edit_empl_lastnameInput").val();
    data.birth = $("#edit_empl_birthInput").val();
    data.salary = $("#edit_empl_salaryInput").val();
	data.id = $("#edit_empl_path").val();

	if (data.firstname.length===0 || data.lastname.length===0 || data.birth.length===0 || data.salary.length===0) {
	} else {
        var path = this.path+"?id="+data.id+"&firstname="+data.firstname+"&lastname="+data.lastname+"&birth="+data.birth+"&salary="+data.salary;
		putAjaxData(this, path, data, this.alertFunction);
	}
	return false;
}

Employee.prototype.printResult = function(result) {
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
        html += '<th id="salary">Salary</th>';
    	html += '</tr></thead>';

    	html += '<tbody>';
		for(var i=0; i<result.length; i++) {
			this.data[result[i].id] = result[i];

            html += '<tr>';
			html += '<td>' + result[i].id + '</td>';
			html += '<td>' + result[i].firstname + '</td>';
			html += '<td>' + result[i].lastname + '</td>';
			html += '<td>' + result[i].birth + '</td>';
            html += '<td>' + result[i].salary + '</td>';
            html += '<td><span class="update_button glyphicon glyphicon-pencil" aria-hidden="true" path="' + result[i].id  +'" data-toggle="modal" data-target="'+this.targetModal+'"></span></td>';
            html += '<td><span class="delete_button glyphicon glyphicon-trash" aria-hidden="true" path="'+ this.path +'?id=' + result[i].id  +'"></span></td>';
            html += '</tr>';
        }
        html += '</tbody>';
        html += '</table>';

        $(this.targetDiv).append(html);
    }
}
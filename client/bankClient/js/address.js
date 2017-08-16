var Address = function(path, targetDiv, targetModal) {
	this.path = path;
	this.targetDiv = targetDiv;
	this.targetModal = targetModal;
	this.data = [];

	var self = this;
}

Address.prototype.prefillModalData = function(path) {
	$('#edit_addr_path').val( path );
	$('#edit_addr_streetInput').val( this.data[path].street );
	$('#edit_addr_cityInput').val( this.data[path].city );
	$('#edit_addr_PostalInput').val( this.data[path].postal );
}

Address.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}

Address.prototype.create = function() {
	var data = new Object();
    data.street = $("#addr_streetInput").val();
    data.city = $("#addr_cityInput").val();
	data.postal = $("#addr_postalInput").val();

	if (data.street.length===0) {
	} else {
        var path = this.path+"?&street="+data.street+"&city="+data.city+"&postal="+data.postal
		postAjaxData(this, path, data, this.alertFunction);
		$('#addr_form')[0].reset();
	}
}

Address.prototype.update = function() {
    var data = new Object();
    data.street = $("#edit_addr_streetInput").val();
    data.city = $("#edit_addr_cityInput").val();
    data.postal = $("#edit_addr_PostalInput").val();
	data.id = $("#edit_addr_path").val();

	if (data.street.length===0 || data.city.length===0 || data.postal.length===0) {
	} else {
	    var path = this.path+"?id="+data.id+"&street="+data.street+"&city="+data.city+"&postal="+data.postal
		putAjaxData(this, path, data, this.alertFunction);
	}
	return false;
}

Address.prototype.printResult = function(result) {
	this.data = [];
	$(this.targetDiv).empty();

	// result = result.destination;

	if (result.length > 0) {
		var html = '<table class="table table-striped">';
		html += '<thead id="desc_header"><tr>';
		html += '<th id="id">ID</th>';
		html += '<th id="street">Street</th>';
		html += '<th id="city">City</th>';
		html += '<th id="postal">Postal code</th>';
    	html += '</tr></thead>';

    	html += '<tbody>';
		for(var i=0; i<result.length; i++) {
			this.data[result[i].id] = result[i];

            html += '<tr>';
			html += '<td>' + result[i].id + '</td>';
			html += '<td>' + result[i].street + '</td>';
			html += '<td>' + result[i].city + '</td>';
			html += '<td>' + result[i].postal + '</td>';
            html += '<td><span class="update_button glyphicon glyphicon-pencil" aria-hidden="true" path="' + result[i].id  +'" data-toggle="modal" data-target="'+this.targetModal+'"></span></td>';
            html += '<td><span class="delete_button glyphicon glyphicon-trash" aria-hidden="true" path="'+ this.path +'?id=' + result[i].id  +'"></span></td>';
            html += '</tr>';
        }
        html += '</tbody>';
        html += '</table>';

        $(this.targetDiv).append(html);
    }
}
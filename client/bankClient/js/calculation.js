var Calculation = function(path, targetDiv) {
	this.path = path;
	this.targetDiv = targetDiv;
	this.data = [];

	var self = this;
}

Calculation.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}


Calculation.prototype.sendCalculation = function() {
    var data = new Object();
    data.age = $("#calc_ageInput").val();
    data.years = $("#calc_yearInput").val();
    var img = document.getElementById('ajax-loader-img');
    img.style.visibility = 'visible';
    if (data.age.length===0) {
    } else {
        var path = this.path+"?age="+data.age+"&years="+data.years;
        postCalculation(this, path, this.getResults);
    }
}

Calculation.prototype.getResults = function(result) {
    console.log(result.uuid);
    if (result.uuid.length===0) {
    } else {
        var path = this.path+"?uuid="+result.uuid;
        getCalculations(this, path, this.printResult);
    }
}

Calculation.prototype.printResult = function(result) {
    var img = document.getElementById('ajax-loader-img');
    img.style.visibility = 'hidden';
    $('#calc_form')[0].reset();
	this.data = [];
	$(this.targetDiv).empty();

	if (result === null) {
    }else{
        var html = '<h2>';
        html += 'Result';
        html += '</h2>';
        html += '<h4>';
        html += result.calculation;
        html += '</h4>';

        $(this.targetDiv).append(html);
    }
}
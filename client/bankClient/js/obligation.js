var Obligation = function(mortDiv, creditDiv) {
	this.mortDiv = mortDiv;
	this.creditDiv = creditDiv;
	this.data = [];

	var self = this;
}

Obligation.prototype.alertFunction = function(result, xHeader) {
	getAjaxData(this, this.printResult, xHeader);
}

Obligation.prototype.showObligations = function() {
    var data = new Object();
    data.accountId = $("#oblig_accountInput").val();

    if (data.accountId.length===0) {
    } else {
        var path = "accountmortgage?id="+data.accountId;
        getAjaxDataWithPath(this, path, this.printMortgage);
        var path = "accountcredit?id="+data.accountId;
        getAjaxDataWithPath(this, path, this.printCredit);
    }
}

Obligation.prototype.printAccounts = function(result) {
    if (result.length > 0) {
        var sel = document.getElementById('oblig_accountInput');
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

Obligation.prototype.printMortgage = function(result) {
	this.data = [];
	$(this.mortDiv).empty();
        var html =  '<h4>Mortgages</h4>';
        html += '<table class="table table-striped">';
        html += '<thead id="desc_header"><tr>';
        html += '<th id="id">ID</th>';
        html += '<th id="value">Value</th>';
        html += '<th id="years">Years</th>';
        html += '</tr></thead>';

        html += '<tbody>';
        for(var i=0; i<result.length; i++) {
            this.data[result[i].id] = result[i];

            html += '<tr>';
            html += '<td>' + result[i].id + '</td>';
            html += '<td>' + result[i].value + '</td>';
            html += '<td>' + result[i].years + '</td>';
            html += '</tr>';
        }
        html += '</tbody>';
        html += '</table>';

        $(this.mortDiv).append(html);
}

Obligation.prototype.printCredit = function(result) {
    this.data = [];
    $(this.creditDiv).empty();

    var html =  '<h4>Credits</h4>';
    html += '<table class="table table-striped">';
    html += '<thead id="desc_header"><tr>';
    html += '<th id="id">ID</th>';
    html += '<th id="value">Value</th>';
    html += '<th id="percentage">Percentage</th>';
    html += '</tr></thead>';

    html += '<tbody>';
    for(var i=0; i<result.length; i++) {
        this.data[result[i].id] = result[i];

        html += '<tr>';
        html += '<td>' + result[i].id + '</td>';
        html += '<td>' + result[i].value + '</td>';
        html += '<td>' + result[i].percentage + '</td>';
        html += '</tr>';
    }
    html += '</tbody>';
    html += '</table>';

    $(this.creditDiv).append(html);
}
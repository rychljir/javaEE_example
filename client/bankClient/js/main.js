$(document).ready(function() {
	var address = new Address("address", "#addressList", "#edit_address_modal");
    var employee = new Employee("employee", "#employeeList", "#edit_employee_modal");
    var client = new Client("client", "#clientList", "#edit_client_modal");
    var account = new Account("account", "#accountList", "#edit_account_modal");
    var mortgage = new Mortgage("mortgage", "#mortgageList", "#edit_mortgage_modal");
    var credit = new Credit("credit", "#creditList", "#edit_credit_modal");
    var ownership = new Ownership("personaccount", "#ownerList");
    var obligation = new Obligation("#mortList", "#credList");
    var calculation = new Calculation("calculation", "#calcValue");

	// READ handler
    $("#addressNav").click(function() { getAjaxData(address, address.printResult); });
    $("#employeeNav").click(function() { getAjaxData(employee, employee.printResult); });
    $("#clientNav").click(function() { getAjaxData(client, client.printResult); });
    $("#accountNav").click(function() { getAjaxData(account, account.printResult); getAjaxPersons(account, account.printPersonsForInsert);});
    $("#mortgageNav").click(function() { getAjaxData(mortgage, mortgage.printResult); getAjaxAccounts(mortgage, mortgage.printAccountsForInsert); });
    $("#creditNav").click(function() { getAjaxData(credit, credit.printResult); getAjaxAccounts(credit, credit.printAccountsForInsert);});
    $("#ownerNav").click(function() { getAjaxPersons(ownership, ownership.printPersons); });
    $("#obligNav").click(function() { getAjaxAccounts(obligation, obligation.printAccounts); });

	// CREATE handleer
    $("#addr_form").submit(function(e) { address.create(); return false; });
    $("#empl_form").submit(function(e) { employee.create(); return false; });
    $("#client_form").submit(function(e) { client.create(); return false; });
    $("#account_form").submit(function(e) { account.create(); return false; });
    $("#mortgage_form").submit(function(e) { mortgage.create(); return false; });
    $("#credit_form").submit(function(e) { credit.create(); return false; });

    $("#owner_form").submit(function(e) { ownership.showAccounts(); return false; });
    $("#oblig_form").submit(function(e) { obligation.showObligations(); return false; });
    $("#calc_form").submit(function(e) { calculation.sendCalculation(); return false; });

	// UPDATE handler
    $("#edit_addr_form").submit(function(e) { address.update(); return false; });
    $("#edit_empl_form").submit(function(e) { employee.update(); return false; });
    $("#edit_client_form").submit(function(e) { client.update(); return false; });
    $("#edit_account_form").submit(function(e) { account.update(); return false; });
    $("#edit_mortgage_form").submit(function(e) { mortgage.update(); return false; });
    $("#edit_credit_form").submit(function(e) { credit.update(); return false; });

	// DELETE handler
    $(address.targetDiv).on("click", ".delete_button", function() { deleteAjaxData(address, $(this).attr('path'), address.alertFunction); });
    $(employee.targetDiv).on("click", ".delete_button", function() { deleteAjaxData(employee, $(this).attr('path'), employee.alertFunction); });
    $(client.targetDiv).on("click", ".delete_button", function() { deleteAjaxData(client, $(this).attr('path'), client.alertFunction); });
    $(account.targetDiv).on("click", ".delete_button", function() { deleteAjaxData(account, $(this).attr('path'), account.alertFunction); });
    $(mortgage.targetDiv).on("click", ".delete_button", function() { deleteAjaxData(mortgage, $(this).attr('path'), mortgage.alertFunction); });
    $(credit.targetDiv).on("click", ".delete_button", function() { deleteAjaxData(credit, $(this).attr('path'), credit.alertFunction); });

	// UPDATE prefill data
    $(address.targetDiv).on("click", ".update_button", function() { address.prefillModalData($(this).attr('path')); });
    $(employee.targetDiv).on("click", ".update_button", function() { employee.prefillModalData($(this).attr('path')); });
    $(client.targetDiv).on("click", ".update_button", function() { client.prefillModalData($(this).attr('path')); });
    $(account.targetDiv).on("click", ".update_button", function() { account.prefillModalData($(this).attr('path')); getAjaxPersons(account, account.printPersonsForUpdate);});
    $(mortgage.targetDiv).on("click", ".update_button", function() { mortgage.prefillModalData($(this).attr('path')); getAjaxAccounts(mortgage, mortgage.printAccountsForUpdate);});
    $(credit.targetDiv).on("click", ".update_button", function() { credit.prefillModalData($(this).attr('path'));getAjaxAccounts(credit, credit.printAccountsForUpdate); });
});

function crudResult(code) {
	console.log(code);

	if (code === undefined || code === 200 || code === 204 || code === 500) {
		$("#errorMsg").html("");
		return;
	}

	var msg = "Something is wrong";

	if (code === 401) {
		msg = "Acces denied! You have to be logged in.";
	} else if (code === 403) {
		msg = "Acces denied! You dont't have access rights.";
	}

	$("#errorMsg").html(msg);
}
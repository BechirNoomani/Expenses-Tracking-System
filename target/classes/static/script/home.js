
    $(document).ready(function () {
try{
        function showAddExpenseModal() {
            $.ajax({
                url: '/expenses/add',
                success: function (response) {
                    $('#addExpenseModal .modal-content').html(response);
                    $('#addExpenseModal').modal('show');
                }
            });
        }

        $('.btn-add-expense').click(showAddExpenseModal);

        $('#addExpenseModal form').submit(function (e) {
            e.preventDefault();
            $.ajax({
                url: $(this).attr('action'),
                type: 'POST',
                data: $(this).serialize(),
                success: function (response) {
                    $('#addExpenseModal').modal('hide');
                }
            });
        });

        function showAddIncomeModal() {
            $.ajax({
                url: '/incomes/add',
                success: function (response) {
                    $('#addIncomeModal .modal-content').html(response);
                    $('#addIncomeModal').modal('show');
                }
            });
        }

        $('.btn-add-income').click(showAddIncomeModal);

        $('#addIncomeModal form').submit(function (e) {
            e.preventDefault();
            $.ajax({
                url: $(this).attr('action'),
                type: 'POST',
                data: $(this).serialize(),
                success: function (response) {
                    $('#addIncomeModal').modal('hide');
                }
            });
        });

        $('.fa-pen-to-square').click(function () {
            let transId = $(this).data('transaction-id');
            let transType = $(this).data('transaction-type');
            showEditExpenseModal(transId, transType);
        });

        $('.delete-button').click(function () {
            let transactionId = $(this).closest('form').data('transaction-id');
            let transactionType = $(this).closest('form').data('transaction-type');
            $('#confirmationModal').modal('show').data('transaction-id', transactionId).data('transaction-type', transactionType);
        });

        function showEditExpenseModal(transId, transType) {
            let url = transType === 'Expense' ? '/expenses/update/' : '/incomes/update/';
            $.ajax({
                url: url + transId,
                success: function (response) {
                    $('#addExpenseModal .modal-content').html(response);
                    $('#addExpenseModal').modal('show');
                }
            });
        }

        $('#addExpenseModal form').submit(function (e) {
            e.preventDefault();
            let expenseId = $(this).find('input[type="hidden"]').val();
            $.ajax({
                url: $(this).attr('action'),
                type: 'POST',
                data: $(this).serialize(),
                success: function (response) {
                    $('#addExpenseModal').modal('hide');
                }
            });
        });

        $('#confirmationModal .confirm-delete').click(function () {
            let transactionId = $('#confirmationModal').data('transaction-id');
            let transactionType = $('#confirmationModal').data('transaction-type');
            $('form[data-transaction-id="' + transactionId + '"][data-transaction-type="' + transactionType + '"]').submit();
        });

        //**************filters************
        $("#applyFilters").click(function () {
            const startDate = $("#startDate").val();
            const endDate = $("#endDate").val();
            const descriptionFilter = $("#descriptionFilter").val();
            const minAmount = parseFloat($("#min-input").val());
            const maxAmount = parseFloat($("#max-input").val());
            console.log($("#min-input").val());
            const url = `/?startDate=${startDate}&endDate=${endDate}&descriptionFilter=${descriptionFilter}&minAmount=${minAmount}&maxAmount=${maxAmount}`;
       window.location.replace(url);

        });
    $("#resetFilters").click(function () {
        console.log("clik")
        window.location.replace("/");

    });


    function checkBalanceAlert() {
        let balance = $("#balance").val();
        balance = parseFloat(balance);
        console.log(balance);
        if (balance < 0) {
            $("#warningAlert").fadeIn(300).delay(1500).fadeOut(400);
        }
    }


    $(document).ready(function() {
        checkBalanceAlert();
    });

    }catch(e){
        console.log(e);}
    });


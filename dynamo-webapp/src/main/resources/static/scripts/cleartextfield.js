$(".reset").click(function() {
    		$(this).closest('form').find("input[type=text],textarea").val("");
    		$('input:checkbox').removeAttr('checked');
    		$('#myform').submit();
  
});

	

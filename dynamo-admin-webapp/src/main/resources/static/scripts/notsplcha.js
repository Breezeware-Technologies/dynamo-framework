$('.notsplcha').bind('keypress', function(e) {
//	console.log(e.which);
	if ($('.notsplcha').val().length < 20) {
		var k = e.which;
		var ok = k >= 65 && k <= 90 || // A-Z
		k >= 97 && k <= 122 || // a-z
		k >= 48 && k <= 57 || // 0-9
		k === 32;

		if (!ok) {
			e.preventDefault();
		}
	}
});

$(document).ready(function () {
    $(".textNumbers").keydown(function (e) {
        if (e.shiftKey) e.preventDefault();
        else {
            var nKeyCode = e.keyCode;
            //Ignore Backspace and Tab keys
            if (nKeyCode == 8 || nKeyCode == 9) return;
            if (nKeyCode < 95) {
                if (nKeyCode < 48 || nKeyCode > 57) e.preventDefault();
            } else {
                if (nKeyCode < 96 || nKeyCode > 105) e.preventDefault();
            }
        }
    });
});


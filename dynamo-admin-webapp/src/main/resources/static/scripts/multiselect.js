
////type

$(".devicetype-checkbox dt a").on('click', function() {
    $(".devicetype-checkbox dd ul").slideToggle('fast');
});

function getSelectedValue(id) {
    return $("#" + id).find("dt a span.value").html();
}

$(document).bind('click', function(e) {
    var $clicked = $(e.target);
    if (!$clicked.parents().hasClass("devicetype-checkbox")) $(".devicetype-checkbox dd ul").hide();
});

$(document).ready(function() {
	
	$(".circle-type").css("color", "#01A99D");
	
    $("input[name='category.name']").on("change", function() {
    	
        var title = $(this).closest('.mutliSelect-type').find('input[type="checkbox"]').val(),
            title = $(this).val() + "";

        	var itemCount = $("input[name='category.name']:checked").length;

        $(".textfiledcontent-type").css("font-weight","normal");

        if ($(this).is(':checked')) {
        		var newvalue = $('<span class="button-color-type" title="' + title + '">' + title + '</span>')
            .appendTo('.typecontent');
        		
        } 
        else {
        		$('span[title="' + title + '"]').remove();
        	
        }

    });

});

///location

$(".devicelocation-checkbox dt a").on('click', function() {
    $(".devicelocation-checkbox dd ul").slideToggle('fast');
});

function getSelectedValue(id) {
    return $("#" + id).find("dt a span.value").html();
}

$(document).bind('click', function(e) {
    var $clicked = $(e.target);
    if (!$clicked.parents().hasClass("devicelocation-checkbox")) $(".devicelocation-checkbox dd ul").hide();
});

$(document).ready(function(){
	
    $(".circle-location").css("color", "#29ABE2");
    
    $("input[name='location.name']").on("change", function() {
    	
        var title = $(this).closest('.mutliSelect-location').find('input[type="checkbox"]').val(),
            title = $(this).val() + "";

        var itemCount = $("input[name='location.name']:checked").length;


        $(".textfiledcontent-location").css("font-weight","normal");
        
        if ($(this).is(':checked')) {
        	
                var newvalue = $('<span class="button-color-location" title="' + title + '">' + title + '</span>')
                    .appendTo('.locationcontent');
                
        }
             else {
                $('span[title="' + title + '"]').remove();
            }
      
    });

});


////
/*

$(".devicelocation-checkbox dt a").on('click', function() {
    $(".devicelocation-checkbox dd ul").slideToggle('fast');
});

function getSelectedValue(id) {
    return $("#" + id).find("dt a span.value").html();
}

$(document).bind('click', function(e) {
    var $clicked = $(e.target);
    if (!$clicked.parents().hasClass("devicelocation-checkbox")) $(".devicelocation-checkbox dd ul").hide();
});

$(function() {
    $("input[name='location.name']").on("change", function() {
        var title = $(this).closest('.mutliSelect-location').find('input[type="checkbox"]').val(),
            title = $(this).val() + "";

        var itemCount = $("input[name='location.name']:checked").length;

        var newvalue = $('<span class="button-color-location" title="' + title + '">' + title + '<button class="btn location-remove white" type="button"><i class="far fa-times-circle"></i></button>' + '</span>')
            .appendTo('.locationcontent').data('src', this);

        $(".textfiledcontent-location").css("font-weight","normal");
        
        if ($(this).is(':checked')) {
            if (itemCount == 1) {
                // alert("1")
                $('.textfiledcontent-location').html('<span title="' + title + '">' + title + '</span>');
                newvalue;
                $(".default1").hide();
                $(".circle-location").css("color", "#29ABE2");
            } else {
                // alert("2")
                $(".textfiledcontent-location").html(itemCount + " items selected");
                newvalue;
                $(".circle-location").css("color", "#29ABE2");
            }
        } else {
            if (itemCount == 0) {
                // alert("uncheck 0")
                $(".textfiledcontent-location").html("Select");
                $(".locationcontent").html("");
                $(".circle-location").css("color", "white");
            } else {
                // alert("Unchecked")
                $(".textfiledcontent-location").html(itemCount + " items selected");
                $('span[title="' + title + '"]').remove();
                $(".circle-location").css("color", "#29ABE2");
            }
        }


        $(document).on('click', '.location-remove', function() {
            var itemCount2 = $(".devicelocation:checked").length;
            $(".textfiledcontent-location").html(itemCount2 + " items selected");
            var $li = $(this).closest('.button-color-location');
            $($li.data('src')).prop('checked', false);
            $li.remove();
            if (jQuery(".devicelocation:checked").length == 0) {
//                alert("lenght 0")
                $(".textfiledcontent-location").html("Select");
                $(".locationcontent").html("");
                $(".circle-location").css("color", "white");
            }
            
        });
        
    });

});
*/



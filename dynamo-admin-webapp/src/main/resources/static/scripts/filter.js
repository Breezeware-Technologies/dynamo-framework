// isotope
var $grid = $('.grid').isotope({
    itemSelector: '.grid-item',
    getSortData: {
        name: '.name',
    }
});
// filter functions
var filterFns = {};

// bind filter button click
$('#filters').on('click', 'button', function() {
    var filterValue = $(this).attr('data-filter');
    // use filterFn if matches value
    // filterValue = filterFns[filterValue] || filterValue;
    $grid.isotope({ filter: filterValue });
});

// bind sort button click
$('#sorts').on('click', 'button', function() {
    var sortByValue = $(this).attr('data-sort-by');
    $grid.isotope({ sortBy: sortByValue });
});

// change is-checked class on buttons
$('.button-group').each(function(i, buttonGroup) {
    var $buttonGroup = $(buttonGroup);
    $buttonGroup.on('click', 'button', function() {
        $buttonGroup.find('.is-checked').removeClass('is-checked');
        $(this).addClass('is-checked');
    });
});

var $grid = $('.grid').isotope({
    // options
});
// filter items on button click
var relatedFilter = {
    "filter": ['aller1', 'aller2', 'aller3', 'aller4', 'aller5',
        'aller6', 'aller7', 'aller8', 'aller14', 'aller15',
    ],
};
$.each(relatedFilter, function(key, value) {

    $(value[key]).on('click', '.clic', function() {
        var filterValue = $(this).attr('data-filter');
        $grid.isotope({ filter: filterValue });
    });
});

$(function() {
    var selectedClass = "";
    $(".button1").click(function() {
        selectedClass = $(this).attr("data-rel");
        window.sel = $(this).attr("class");
        $("#cd-timeline").fadeTo(100, 1);
        $("#cd-timeline .all").not("." + selectedClass).fadeOut();
        setTimeout(function() {
            $("." + selectedClass).fadeIn();
            $("#cd-timeline").fadeTo("fast", 1);
        }, 10);

    });
});

$(function() {
    $("button[name=user-type]:button").click(function() {
        if (window.sel == "button1 srtbtn-2 pl-3 pr-3 padall1 is-checked rounded-left") {
            $('.firsty').show();
            $('.nexty').hide();
        } else if (window.sel == "pl-3 button1 pr-3 srtbtn-2 padall1 font-weight-bold") {
            $('.firsty').show();
            $('.nexty').hide();
        } else if (window.sel == "pl-3 button1 pr-3 srtbtn-2 padall1 font-weight-bold b") {
            $('.firsty').show();
            $('.nexty').hide();
        } else if (window.sel == "pl-5 button1 pr-5 srtbtn-2 padall1 rounded-right font-weight-bold") {
            $('.firsty').show();
            $('.nexty').hide();
        }
    });
    $("button[name=five-year]:button").click(function() {
        if (window.sel == "button1 srtbtn-2 pl-3 pr-3 padall1 is-checked rounded-left") {
            $('.firsty').show();
            $('.nexty').show();
        } else if (window.sel == "pl-3 button1 pr-3 srtbtn-2 padall1 font-weight-bold") {
            $('.firsty').show();
            $('.nexty').show();
        } else if (window.sel == "pl-3 button1 pr-3 srtbtn-2 padall1 font-weight-bold b") {
            $('.firsty').show();
            $('.nexty').show();
        } else if (window.sel == "pl-5 button1 pr-5 srtbtn-2 padall1 rounded-right font-weight-bold") {
            $('.firsty').show();
            $('.nexty').show();
        }
    });
});

function parseDate(input) {
    var parts = input.match(/(\d+)/g);
    var date = new Date(parts[2], parts[1] - 1, parts[0]);
    return new Date(parts[2], parts[1] - 1, parts[0]);
}

function sortHistory(event) {

    if (event === "Sort Ascending") {

        var year = $.makeArray($(".year"));
        year.sort(function(a, b) {
            return $(a).text() < $(b).text();
        });
        var firstElems = $.makeArray($(".firstDate"));
        firstElems.sort(function(a, b) {
            return parseDate($(a).text()) < parseDate($(b).text());
        });
        var secondElems = $.makeArray($(".secondDate"));
        secondElems.sort(function(a, b) {
            return parseDate($(a).text()) < parseDate($(b).text());
        });
        $("#cd-timeline").html(year);
        $("#first").html(firstElems);
        $("#second").html(secondElems);


    } else if (event === "Sort Descending") {
        var year = $.makeArray($(".year"));
        console.log(year);
        year.sort(function(a, b) {
            return $(a).text() > $(b).text();
        });
        var firstElems = $.makeArray($(".firstDate"));
        firstElems.sort(function(a, b) {
            return parseDate($(a).text()) > parseDate($(b).text());
        })
        var secondElems = $.makeArray($(".secondDate"));
        secondElems.sort(function(a, b) {
            return parseDate($(a).text()) > parseDate($(b).text());
        });
        $('#cd-timeline').html(year);
        $("#first").html(firstElems);
        $("#second").html(secondElems);

    }

}
//table Hor fixied head
jQuery(document).ready(function() {
    jQuery(".main-table").clone(true).appendTo('#table-scroll').addClass('clone');
});

jQuery(document).ready(function() {
    jQuery(".main-table1").clone(true).appendTo('#table-scroll1').addClass('clone');
});
jQuery(document).ready(function() {
    jQuery(".main-table2").clone(true).appendTo('#table-scroll2').addClass('clone');
});

$('.panel-collapse').on('show.bs.collapse', function() {
    $(this).siblings('.panel-heading').addClass('active');
});

$('.panel-collapse').on('hide.bs.collapse', function() {
    $(this).siblings('.panel-heading').removeClass('active');
});
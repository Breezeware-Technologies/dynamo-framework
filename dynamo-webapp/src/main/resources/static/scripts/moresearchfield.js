//More Search Option 
$(".more-link").click(function(e) {
    $(this).closest(".text-container").toggleClass("show-more")
    $(this).addClass("d-block")
    e.preventDefault();
});



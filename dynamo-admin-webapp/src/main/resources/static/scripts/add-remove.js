$(document)
		.ready(
				function() {

					$(".add-more")
							.click(
									function() {
										var spanEltsCount = $(".appFeatureIndex").length;
										// console.log("# of span elements = " +
										// spanEltsCount);
										var newIndex = spanEltsCount;
										var html = "<span class=\"appFeatureIndex hide\" > </span> <div class=\"copy\"> <div class=\"control-group input-group\"> <div class=\" row mb-3 mt-3 field1\"><div class=\"col-md-5 pr-0\"><label class=\"desc1 float-left\">Feature Name </label> <input required type=\"text\" class=\"form-control notsplcha\" id=\"appFeatures"
												+ newIndex
												+ ".name\" name=\"appFeatures["
												+ newIndex
												+ "].name\"></div><div class=\"col-md-6 pr-0\"><label class=\"desc1 float-left\">Feature Description</label> <input type=\"text\" class=\"form-control\"id=\"appFeatures"
												+ newIndex
												+ ".description\" name=\"appFeatures["
												+ newIndex
												+ "].description\"> </div> <div class=\"col-md-1 mt-4\"> <button class=\"btn remove\" type=\"button\"> <i class=\"fas fa-times-circle\"></i></button></div></div></div></div>";

										// console.log("HTML = " + html);

										// var html = $(".copy").html();
										$(".new-feature-div").before(html);
									});

					$("body").on("click", ".remove", function() {
						$(this).parents(".control-group").remove();
					});
				});
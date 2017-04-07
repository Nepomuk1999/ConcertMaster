function showLoadAnimation(selector) {
    if(!$(selector).hasClass("loader-background")) {
        $(selector).append('<div class="loader-background"></div><div class="loader"></div></div>');
    } else {
        $(selector).toggle(true);
    }
}

function hideLoadAnimation(selector) {
    var loaderBackground = $(selector).find(".loader-background");

    if(loaderBackground.length) {
        loaderBackground.toggle(false);
    }

    var loader = $(selector).find(".loader");

    if(loader.length) {
        loader.toggle(false);
    }
}
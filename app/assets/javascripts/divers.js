
define(["jquery", "turbo"], function() {
    manyInitialisations();
});


/**
 * Fonction initialisant divers éléments (boutons, boutons admin, sous-catégories ...)
 */
function manyInitialisations() {

    $('#Growls').on(
        'click',
        '.icon-cancel',
        function() {
            var parent = $(this).parent();
            parent.css('height', parent.outerHeight(false) + 'px');

            parent.addClass('closed');
        }
    );
}


/**
 * Fonction permettant de vérifier si la string json passée en paramètre possède des messages d'erreur de validation.
 * Cherchera dans le parent passé en paramètre les champs à mettre à jour avec les messages, et sinon, exécutera la
 * fonction onSuccess passée en paramètre.
 *
 * @param json La string json dans laquelle chercher les messages d'erreurs de validation.
 */
function checkValidationFromJson(json) {
    $('#Growls div').remove();

    for(var key in json) {
        if(key == "id" && json[key] == "ValidationErrors") {
            var i = 0;
            for(var e in json["errors"]) {
                var error = json["errors"][e];

                addMessage(error['message'], i * 100);

                i++;
            }
        }
    }
}


/**
 * Permet d'afficher un message d'erreur à l'utilisateur
 *
 * @param msg Le message à afficher
 * @param delay Le délais d'affichage du message
 */
function addMessage(msg, delay) {
    var message = $('<div><a class="icon-cancel"></a>' + msg + '</div>');
    message.appendTo('#Growls');

    delay = (delay == undefined || delay == null ? 100 : delay);

    message.delay(delay).queue(function() {
        $(this).addClass('shown');
    });
}
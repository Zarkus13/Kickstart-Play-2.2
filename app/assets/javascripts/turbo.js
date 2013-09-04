
$(function() {
    $('[ajax]').on(
        'click',
        function(e) {
            e.preventDefault();
            new Turbo($(this)).sendAjaxForm();
        }
    );


});

function Turbo(elem) {
    Turbo.prototype.element = $(elem);
}

Turbo.prototype.handle = function(callbacks, datas) {
    var self = this;
    $.each(
        callbacks.match(/(\S+)/gi),
        function(i, e) {
            $.each(
                e.match(/(\$[a-zA-Z0-9-_.]+)/gi),
                function(j, p) {
                    e = e.replace(p, 'datas.' + p.replace('$', ''));
                }
            );

            eval('self.' + e);
        }
    );
}

Turbo.prototype.handleFromJson = function(json) {
    var self = this
    for(var k in json) {
        var params = "";
        var i = 0;

        for(var k2 in json[k]) {
            if(i > 0) params += ", ";
            params += 'json["' + k + '"]["' + k2 + '"]';
            i++;
        }

        eval('self.' + k + "(" + params + ")");
    }
}

Turbo.prototype.reload = function() {
    window.location.reload();
}

Turbo.prototype.redirect = function(url) {
    window.location = url;
}

Turbo.prototype.growls = function(messages) {
    $('#Growls div').remove();

    var i = 0;
    $.each(
        messages,
        function(id, msg) {
            addMessage(id + " : " + msg, i * 100);
            i++;
        }
    );
}

Turbo.prototype.success = function(json) {
    this.handleFromJson(json);
}

Turbo.prototype.errors = function(json) {
    this.growls(json);
}


/**
 * Fonction permettant d'envoyer un formulaire en ajax, et d'afficher les messages d'erreurs s'il y en a.
 * Si la validation passe, une fonction de callback peut être appelée.
 *
 * @param form Le formulaire à envoyer.
 * @param callback La fonction à appeler si la validation passe.
 */
Turbo.prototype.sendAjaxForm = function() {
    $('#LoadingImage').removeClass('hidden');

    if(this.element[0]['tagName'] == "FORM")
        var form = this.element;
    else
        var form = this.element.parents('form').eq(0);

    var formArray = form.serializeArray();
    var dataArray = new Array();

    for(var i = 0 ; i < formArray.length ; i++) {
        if(this.element.attr('id') == 'FormAddComment' && formArray[i].name == 'comment.side.id')
            formArray[i].value = $('#FormAddComment [name="comment.side.id"] option[selected="selected"]').val();

        if(formArray[i].name != 'el-select')
            dataArray.push(formArray[i]);
    }

    var self = this;
    $.ajax({
        url: form.attr('action'),
        data: dataArray,
        type: form.attr('method'),
        success: function(datas) {
            $('#LoadingImage').addClass('hidden');

            if(datas.reload !== undefined)
                window.location.reload();
            else if(datas.redirect !== undefined)
                windows.location = datas.redirect;

            if(self.element.attr('onComplete') !== undefined) {
                self.handle(self.element.attr('onComplete'), datas);
            }

            if(datas['error'] !== undefined || datas['errors'] !== undefined) {
                if(self.element.attr('onError') !== undefined) {
                    self.handle(self.element.attr('onError'), datas);
                }
                else {
                    self.handleFromJson(datas);
                }
            }
            else if(datas['success'] !== undefined) {
                if(self.element.attr('onSuccess') !== undefined) {
                    self.handle(self.element.attr('onSuccess'), datas['success']);
                }
                else {
                    self.handleFromJson(datas['success']);
                }
            }
        }
    });
}
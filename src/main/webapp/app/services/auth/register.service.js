(function () {
    'use strict';

    angular
        .module('netdelsolutionApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();

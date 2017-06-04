(function() {
    'use strict';

    angular
        .module('netdelsolutionApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = [ '$timeout', 'Auth', 'LoginService', '$http'];

    function RegisterController ($timeout, Auth, LoginService, $http) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.registerAccount = {};
        vm.success = null;

        vm.locateMe = function() {
            $http.get("/api/location").then(function (response) {
                        vm.registerAccount.address = response.data.location;
                    });
        };



        //TODO: hardcoded to be removed
        vm.issues = ["Gun Safety", "Child Rights", "Marijuiana Reform"];
        $timeout(function (){angular.element('#login').focus();});

        function register () {
            if (vm.registerAccount.issue) {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey =  'en' ;
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'email address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
            }
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('netdelsolutionApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http'];

    function HomeController ($scope, Principal, LoginService, $state, $http) {
        var vm = this;
        var login;

        Principal.identity().then(function(account) {
                    login = account.login;
                    $http.get("/api/projects?login=" + login).then(function (response) {
                        vm.projects = response.data;
                    });
        });




        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();

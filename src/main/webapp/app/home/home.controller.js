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

                    $http.get("/api/projects?createdBy=" + login).then(function (response) {
                                                        vm.myProjects = response.data;
                                      });
        });



        this.searchLegal = function(city) {

            $http.get("/api/resources?category=Legal&city=" + city).then(function (response) {
                                    vm.legals = response.data;
                  });
        };

        this.searchTraining = function(city) {

                    $http.get("/api/resources?category=Training&city=" + city).then(function (response) {
                                            vm.trainingType = response.data;
                          });
                };

        this.searchMedical = function() {

                    $http.get("/api/resources?category=Medical").then(function (response) {
                                            vm.medicals = response.data;
                          });
                };

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

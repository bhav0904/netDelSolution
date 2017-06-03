(function() {
    'use strict';

    angular
        .module('netdelsolutionApp')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['$stateParams', 'Auth', 'LoginService'];

    function ProjectController ($stateParams, Auth, LoginService) {
        var vm = this;
        vm.subject = null;
        vm.address = null;
        vm.description = null;

        function saveEntity(subject, address, description){
            vm.subject = subject;
            vm.address = address;
            vm.description = description;
        }
    }
})();
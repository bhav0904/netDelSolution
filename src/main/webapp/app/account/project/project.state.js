(function() {
    'use strict';

    angular
        .module('netdelsolutionApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('project', {
            // parent: 'account',
            url: '/project',
            // data: {
            //     authorities: []
            // },
            views: {
                'content@': {
                    templateUrl: 'app/account/project/project.html',
                    controller: 'ProjectController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
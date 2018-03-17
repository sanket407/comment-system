angular.module('CommentApp')
.config(function($routeProvider) {
    $routeProvider.when('/register', {
        templateUrl: '/templates/pages/register.html',
        controller: 'RegisterController',
        controllerAs: 'ctrl'
    })
    .when('/comments', {
        templateUrl:  'templates/pages/comment-list.html',
        controller: 'CommentController',
        controllerAs: 'ctrl'
    })
    .otherwise( { redirectTo: '/comments'} );
});

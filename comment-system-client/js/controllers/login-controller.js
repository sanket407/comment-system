angular.module('CommentApp')
    .controller('LoginController',['UserService','$scope','$location', function(UserService, $scope, $location) {

      var ctrl = this;

      $scope.UserService = UserService;

      ctrl.err = '';

      ctrl.guestUser = {
        id : -1,
        username : 'Guest',
        isGuest : true
      };

      ctrl.login = function() {
        UserService.authenticate(ctrl.username, ctrl.password).then(function(response){
          var user = response.data;
          UserService.setUser(user);
          ctrl.username = '';
          ctrl.password = '';
          ctrl.err = '';
          $location.path('/comments');
        },function(response){
          ctrl.err = response.data.message;
        });
      }

      ctrl.logout = function() {
        UserService.setUser(ctrl.guestUser);
        ctrl.err = '';
        $location.path('/comments');
      }



}]);

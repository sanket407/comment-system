angular.module('CommentApp')
    .controller('RegisterController',['UserService','$location', function(UserService, $location) {

      var ctrl = this;
      ctrl.username = '';
      ctrl.password = '';
      ctrl.err = '';
      ctrl.register = function(){
        UserService.register(ctrl.username, ctrl.password).then(function(response) {
          ctrl.err = '';
          UserService.setUser(response.data);
          $location.path('/comments');
        }, function(error){
          ctrl.err = error.data.message;
        });
      };
}]);

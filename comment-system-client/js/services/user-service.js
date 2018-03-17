angular.module('CommentApp')
    .factory('UserService',['$http', function UserServiceFactory($http) {

      var service = this;

        return {
          currentUser : {
            id : -1,
            username : 'Guest',
            isGuest : true
          },
          authenticate : function(username, password) {
            var user = {
              username : username,
              password : password
            }

            return $http.post('http://localhost:8080/users/authenticate/',user);
          },
          register : function(username ,password) {
            var user = {
              username : username,
              password : password
            }
            return $http.post('http://localhost:8080/users/new/',user);
          },
          setUser : function(user) {
            this.currentUser = user;
          }
        }
}]);

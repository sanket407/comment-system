angular.module('CommentApp')
    .factory('CommentService',['$http', function ItemStorageFactory($http) {

        var service = this;


        return {
            list : [],
            setList: function(newList) {
              this.list = newList;
            },
            getList: function() {
                 return $http.get('http://localhost:8080/comments/root').then(function(response) {
                    return response.data;
                });
            },
            addNewRootComment: function(newComment, username) {
                return $http.post('http://localhost:8080/comments/user/' + username, newComment).then(function(response){
                    return $http.get('http://localhost:8080/comments/root').then(function(response){
                      return response.data;
                    });
                });
            },
            addNewReplyComment: function(parentCommentId, newComment, username) {
                return $http.post('http://localhost:8080/comments/user/' + username + '/parentId/' + parentCommentId, newComment).then(function(response){
                    return $http.get('http://localhost:8080/comments/root').then(function(response){
                      return response.data;
                    });
                });
            },
            deleteComment: function(commentId) {
                return $http.delete('http://localhost:8080/comments/' + commentId).then(function(response) {
                  return $http.get('http://localhost:8080/comments/root').then(function(response){
                    return response.data;
                  });
                });
              }
          }

        }]);

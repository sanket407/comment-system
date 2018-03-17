angular.module('CommentApp')
    .controller('CommentController',['CommentService','UserService','$scope','$location',
      function(CommentService, UserService, $scope, $location) {

      var ctrl = this;

      $scope.UserService = UserService;
      $scope.CommentService = CommentService;

        ctrl.newComment = '';

      CommentService.getList().then(function(data){
       CommentService.setList(data);
      });



      ctrl.addComment = function(){
        CommentService.addNewRootComment(ctrl.newComment, UserService.currentUser.username).then(function(data){
          CommentService.setList(data);
          ctrl.newComment = '';
          $location.path('/comments');
        });
      }
}]);

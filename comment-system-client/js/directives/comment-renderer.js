angular.module('CommentApp')
    .directive('commentRenderer',['CommentService', 'UserService', '$location',
     function(CommentService, UserService, $location) {
        function link(scope, element, attrs) {
          scope.UserService = UserService;

          scope.reset = function(comment) {
            comment.isExpanded = false;
            comment.isReplying = false;
          };
          scope.toggleExpansion = function(comment) {
            comment.isExpanded = !comment.isExpanded;
          };
          scope.showReplyArea = function(comment) {
            comment.isReplying = true;
          };
          scope.hideReplyArea = function(comment) {
            comment.isReplying = false;
          };
          scope.addNewReplyComment = function(parentComment, newComment) {
            CommentService.addNewReplyComment(parentComment.id, newComment, UserService.currentUser.username).then(function(data){
              CommentService.setList(data);
              scope.hideReplyArea(parentComment);
                $location.path('/comments');
            });

          };
          scope.deleteComment = function(comment) {
            CommentService.deleteComment(comment.id).then(function(data){
              CommentService.setList(data);
              $location.path('/comments');
            });
          };
        }
        return {
            restrict: 'E',
            templateUrl: 'templates/directives/comment-renderer.html',
            scope: {
                list: '='
            },
            link: link
        };
    }]);

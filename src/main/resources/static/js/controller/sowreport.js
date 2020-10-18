app.controller('sowreportController',
    function ($scope, $timeout, $http, $location, $filter, Notification) {
        $scope.bshimServerURL = "/bs";
        $scope.CountryNameText = "";
        $scope.StatusText = "";
        $scope.operation = 'Create';
        $scope.inactiveStatus = "Active";

        $scope.format = 'dd/MM/yyyy';

        $scope.fromDate = new Date();
        $scope.toDate = new Date();
        $scope.openDate2 = function () {
            $scope.popup2.opened = true;
        };
        $scope.popup2 = {
            opened: false
        };
        $scope.popup1 = {
            opened: false
        };
        $scope.openDate1 = function () {
            $scope.popup1.opened = true;
        };


        $scope.getClassList = function () {
            $http.post($scope.bshimServerURL + '/getClassList').then(function (response) {
                var data = response.data;
                $scope.classList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };
        $scope.getClassList();

        $scope.chapter = function(){
            $http.post($scope.bshimServerURL + '/getChapter').then(function (response) {
                var data = response.data;
                $scope.chapterList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };
        $scope.chapter();


        $scope.getLessonPlanList = function () {
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL  + '/getLessonPlanList').then(function (response) {
                var data = response.data;
                $scope.planList= data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getLessonPlanList();

        $scope.viewDetails = function () {
            $http.post($scope.bshimServerURL +"/getAllDetails?fromDate="+$scope.fromDate + "&toDate="+$scope.toDate + "&studentGrade="+$scope.studentGrade +"&class="+$scope.class +"&subjectId="+$scope.subjectId +"&chapterId="+$scope.chapterId).then(function (response) {
                var data = response.data;
                $scope.planList= data;
            })
        };


        $scope.getGradeList = function () {
            $http.post($scope.bshimServerURL + '/getGradeListNormal?status='+$scope.statusText).then(function (response) {
                var data = response.data.object;
                $scope.gradeList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };
        $scope.getGradeList();

        $scope.subject = function(){
            $http.post($scope.bshimServerURL + '/getSubject').then(function (response) {
                var data = response.data;
                $scope.subjectList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        };
        $scope.subject();
    });
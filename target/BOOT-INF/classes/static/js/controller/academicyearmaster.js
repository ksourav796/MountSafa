app.controller('academicyearmasterController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.bshimServerURL = "/bs";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;

        $scope.today = function() {
            $scope.fromDate= new Date();
            $scope.todate = new Date();
        };
        $scope.today();
        $scope.format = 'dd/MM/yyyy';

        $scope.open1 = function() {
            $scope.popup1.opened = true;
        };

        $scope.popup1 = {
            opened: false
        };

        $scope.open2 = function() {
            $scope.popup2.opened = true;
        };
        $scope.popup2 = {
            opened: false
        };

        $scope.feeconfigurationList=function () {
            $window.location.href = '/home#!/configuration';

        };

        $scope.importPopup = function(){
            $("#import_academicyear").modal('show');
        }

        $scope.saveAcademicYearImport = function(){
            $scope.isDisabled= true;
            var formElement = document.getElementById("details");
            var details = new FormData(formElement);
            $http.post($scope.bshimServerURL  + '/saveAcademicYearImport',details,
                { headers: {'Content-Type': undefined},
                    transformRequest: angular.identity,
                }).then(function (response) {
                    $("#import_academicyear").modal('hide');
                    $scope.getPaginatedAcademicYearList();
                    $scope.isDisabled= false;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.isDisabled= false;
                }
            )
        }
        $scope.removeAcademicMaster = function () {
            $scope.acdyrId="";
            $scope.acdname = "";
            $scope.acddesc = "";
            $scope.fromDate= new Date();
            $scope.todate= new Date();
            $scope.statusText = "Active";
            document.getElementById("checkboxStatus").checked = false;
        };
        $scope.EditAcademics = function(data) {
            $scope.acdyrId=data.acdyrId;
            $scope.acdname=data.acdyrName;
            $scope.acddesc=data.acdyrDescription;
            $scope.fromDate=Date.parse(data.fromDate);
            $scope.todate=Date.parse(data.toDate);
            $scope.statusText=data.status;
            $scope.operation='Edit';
            $('#student-title').text("Edit Academics");
            $("#add_academic_master").modal('show');
        },function (error) {
            Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});

        };
        $scope.getPaginatedAcademicYearList = function (page) {
            if(angular.isUndefined($scope.searchText)){
                $scope.searchText="";
            }
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
                    break;
                case 'nextPage':
                    $scope.isNext = true;
                    $scope.isPrev = false;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.pageNo = $scope.pageNo + 1;
                    break;
                case 'prevPage':
                    $scope.isPrev = true;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.pageNo = $scope.pageNo - 1;
                    break;
                default:
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
            }
            var paginationDetails;
            paginationDetails = {
                firstPage: $scope.firstPage,
                lastPage: $scope.lastPage,
                pageNo: $scope.pageNo,
                prevPage: $scope.prevPage,
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            $http.post($scope.bshimServerURL  + '/getPaginatedAcdemicYearList?searchText=' + $scope.searchText+'&checkboxStatus='+$scope.checkboxStatus, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                $scope.academicYearList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getPaginatedAcademicYearList();
        $scope.addacademic = function () {
            $scope.removeAcademicMaster();
            $scope.operation='create';
            $('#student-title').text("Add Academic Master");
            $("#add_academic_master").modal('show');
        };
        $scope.fromDateList = [];
        $scope.toDateList = [];
        $scope.saveAcademicMaster=function () {
            $scope.isDisabled= true;
            var frmDate = $("#fromDate").val();
            var toDate = $("#todate").val();
            fromDateList = frmDate.split("/");
            var fromdd = parseInt(fromDateList[0]);
            var fromMonth = parseInt(fromDateList[1]);
            var fromyear = parseInt(fromDateList[2]);
            toDateList = toDate.split("/");
            var todd = parseInt(toDateList[0]);
            var toMonth = parseInt(toDateList[1]);
            var toyear = parseInt(toDateList[2]);

            if (angular.isUndefined($scope.acdname) || $scope.acdname == '') {
                Notification.warning({message: 'Academic Name can not be Empty', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
           else if (fromdd>=todd && fromMonth==toMonth && fromyear>=toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else if (fromdd<=todd && fromMonth<=toMonth && fromyear>toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else if (fromdd>todd && fromMonth<toMonth && fromyear>toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else if (fromdd<todd && fromMonth>toMonth && fromyear>toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else if (fromdd==todd && fromMonth>toMonth && fromyear==toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else if (fromdd>todd && fromMonth>toMonth && fromyear>toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else if (fromdd==todd && fromMonth==toMonth && fromyear==toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else if (fromdd<todd && fromMonth>toMonth && fromyear==toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else if (fromdd==todd && fromMonth>toMonth && fromyear>toyear) {
                Notification.warning({message: 'From date should not be greater than To date', positionX: 'center', delay: 2000});
                $scope.isDisabled= false;
            }
            else {
                var saveAcademicDetails;
                saveAcademicDetails = {
                    acdyrId: $scope.acdyrId,
                    acdyrName: $scope.acdname,
                    acdyrDescription: $scope.acddesc,
                    fromDate: $scope.fromDate,
                    toDate: $scope.todate,
                    status:$scope.statusText
                };
                $http.post($scope.bshimServerURL + "/saveAcademicMaster", angular.toJson(saveAcademicDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $("#add_academic_master").modal('hide');
                        if($scope.operation=='Edit'){
                            Notification.success({
                                message: 'AcademicMaster is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }else {
                            Notification.success({
                                message: 'AcademicMaster is Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }
                        $scope.removeAcademicMaster();
                        $scope.getPaginatedAcademicYearList();
                        $scope.isDisabled= false;

                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.isDisabled= false;
                });
            }
        };

        $scope.DeleteAcademics = function (data) {
            bootbox.confirm({
                title: "Alert",
                message: "Do you want to Continue ?",
                buttons: {
                    confirm: {
                        label: 'OK'
                    },
                    cancel: {
                        label: 'Cancel'
                    }
                },
                callback: function (result) {
                    if(result == true){
                        var deleteDetails = {
                            acdyrName:data.acdyrName,
                            acdyrDescription:data.acdyrDescription,
                            fromDate:data.fromDate,
                            acdyrId:data.acdyrId,
                            toDate:data.toDate,
                            status:$scope.statusText
                        };
                        $http.post($scope.bshimServerURL +"/deleteAcademics", angular.toJson(deleteDetails, "Create")).then(function (response, status, headers, config) {
                            var data = response.data;
                            $scope.getPaginatedAcademicYearList();
                            if(data==true){
                                Notification.success({
                                    message: 'Successfully Deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }else {
                                Notification.warning({
                                    message: 'Cannot delete Already in Use',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                        }, function (error) {
                            Notification.warning({
                                message: 'Cannot be delete,already it is using',
                                positionX: 'center',
                                delay: 2000
                            });
                        });
                    }
                }
            });
        };

    });
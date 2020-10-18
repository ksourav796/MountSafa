app.controller('discounttypeController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.bshimServerURL = "/bs";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        
        $scope.feeconfigurationList=function () {
            $window.location.href = '/home#!/configuration';

        };
        $scope.removeDiscountType = function () {
            $scope.discountId="";
            $scope.disname = "";
        };
        $scope.getDiscountTypeList = function (val) {
            if (angular.isUndefined(val)) {
                val = "";
            }

            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL  + '/getDiscountTypeList?searchText=' + val).then(function (response) {
                var data = response.data;
                $scope.discountTypeList= data;
                $scope.searchText = val;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getDiscountTypeList();
        $scope.addDiscount = function () {
            $scope.removeDiscountType();
            $scope.operation='create';
            $('#student-title').text("Add Discount Type");
            $("#add_discount_type").modal('show');
        };
        $scope.fromDateList = [];
        $scope.toDateList = [];
        $scope.saveDiscountType=function () {
            $scope.isDisabled= true;
            var saveDiscountDetails;
            saveDiscountDetails = {
                discountId: $scope.discountId,
                discountType: $scope.disname
                };
                $http.post($scope.bshimServerURL + "/saveDiscountType", angular.toJson(saveDiscountDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $("#add_discount_type").modal('hide');
                        if($scope.operation=='Edit'){
                            Notification.success({
                                message: 'DiscoutType is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }else {
                            Notification.success({
                                message: 'DiscoutType is Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }
                        $scope.removeDiscountType();
                        $scope.getDiscountTypeList();
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
        };

        $scope.EditDiscountType = function(data) {
            $scope.discountId=data.discountId;
            $scope.disname=data.discountType;
            $scope.operation='Edit';
            $('#student-title').text("Edit Discount Type");
            $("#add_discount_type").modal('show');
        },function (error) {
            Notification.error({message: 'Something went wrong, please try again',positionX: 'center',delay: 2000});

        };

        $scope.DeleteDiscountType = function (data) {
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
                            discountId:data.discountId,
                            discountType:data.discountType
                        };
                        $http.post($scope.bshimServerURL +"/deleteDiscountType", angular.toJson(deleteDetails, "Create")).then(function (response, status, headers, config) {
                            var data = response.data;
                            $scope.getDiscountTypeList();
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
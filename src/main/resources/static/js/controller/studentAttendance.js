app.controller('studentAttendanceController',
    function ($scope, $timeout, $http, $location, $filter, Notification) {
        $scope.bshimServerURL = "/bs";
        $scope.CountryNameText = "";
        $scope.StatusText = "";
        $scope.operation = 'Create';
        $scope.inactiveStatus = "Active";
        $scope.ADate = new Date();
        $scope.removeStudentAttendence = function () {
            $scope.aId = "";
            $scope.studentName = "";
            $scope.studentLevel = "";
            $scope.studentClass = "";
            $scope.ADate = "";
            $scope.First = "";
            $scope.Last = "";
            $scope.Time = "";
            $scope.status = "";
            $scope.isDisabled= false;
            $scope.operation = "";
        };

        $scope.addStudentAttendance = function () {
            var today = new Date();
            // var date = today.getFullYear()+'/'+(today.getMonth()+1)+'/'+today.getDate();
            var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
            var dateTime = time;
            $(".loader").css("display", "block");
            $scope.student = "";
            $scope.ADate = new Date();
            $scope.Time = "";
            $scope.status = "Active";
            $("#add_new_student_Attendance_modal").modal('show');
        }

        $scope.format = 'dd/MM/yyyy';

        $scope.openA = function () {
            $scope.popupA.opened = true;
        };

        $scope.popupA = {
            opened: false
        };

        $scope.editStudentAttendence = function (data) {
            $scope.studentAttendenceId = data.studentAttendenceId;
            $scope.class = parseInt(data.studentClass);
            $scope.studentGrade = data.studentLevel;
            // $scope.present = data.attendance;
            $scope.ADate = new Date (data.aDate);
            $scope.status = data.status;
            $scope.student1 = angular.fromJson(data.studentAttendence);
            $scope.studentList = [];
            angular.forEach($scope.student1,function (val,key) {
                var create=[];
                create.studentName=val.student;
                create.gender=val.gender;
                create.present=val.attendance;
                $scope.studentList.push(create);
            });
            $scope.operation = 'Edit';
            $('#state-title').text("Edit Student Attendance");
            $("#submit").text("Save");
            $("#add_new_student_Attendance_modal").modal('show');

        }

        $scope.viewStudentAttendence = function (data) {
            $scope.studentAttendenceId = data.studentAttendenceId;
            $scope.class = data.className;
            $scope.studentGrade = data.levelName;
            $scope.ADate = new Date (data.aDate);
            $scope.status = data.status;
            $scope.student1 = angular.fromJson(data.studentAttendence);
            $("#add_view_student_Attendance_modal").modal('show');
        }

        $scope.getGradeList = function () {
            $http.post($scope.bshimServerURL + '/getGradeListNormal').then(function (response) {
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


        $scope.getStudentList= function () {
            $http.post($scope.bshimServerURL +"/getStudentAttendanceList").then(function (response) {
                var data = response.data;
                $scope.studentAsessList = data;
            })
        };
        $scope.getStudentList();

        $scope.getStudentListBasedOnClass = function (data) {
            if($scope.class==null){
                $scope.getStudentList();
            }
            $http.post($scope.bshimServerURL + "/getStudentListBasedOnClass?id="+data).then(function (response) {
                var data = response.data;
                $scope.studentList=data;
                $scope.allList=[];
                angular.forEach($scope.studentList,function (val,key) {
                    $scope.allList.push({
                        serialNo:key,
                        studentName:val.studentName,
                        gender:val.gender,
                        present:"present"
                    });
                });
                $scope.studentList=angular.fromJson($scope.allList);

            })
        };

        $scope.levelClass = function(gradeId){
            var url = "/bs/getClassLevel?levelId=" + gradeId;
            $http.post(url).then(function (response) {
                var data = response.data;
                $scope.classList = angular.copy(data);
                $scope.classes=[];
                angular.forEach($scope.classList,function (val,key) {
                    if(val.status=="Active"){
                        $scope.classes.push(val);
                    }

                })

            })
        }


        $scope.saveStudentAttendance = function () {

            $scope.allList=[];
            angular.forEach($scope.studentList,function (val,key) {
                $scope.allList.push({
                    serialNo:key,
                    student:val.studentName,
                    gender:val.gender,
                    attendance:val.present
                });
            });
            $scope.isDisabled = true;
            var saveAttendance;
            saveAttendance = {
                studentAttendenceId: $scope.studentAttendenceId,
                studentClass: $scope.class,
                studentLevel: $scope.studentGrade,
                attendance: $scope.present,
                aDate: $scope.ADate,
                firstCheckIn: $scope.First,
                lastCheckOut: $scope.Last,
                time: $scope.Time,
                status: $scope.status,
                studentAttendence:angular.toJson($scope.allList)

            };
            $http.post('/bs' + '/saveStudentAttendance', angular.toJson(saveAttendance, "Create")).then(function (response, status, headers, config) {
                var data = response.data;
                if (data == "") {
                    $scope.isDisabled = false;
                    Notification.error({
                        message: ' Already Created',
                        positionX: 'center',
                        delay: 2000
                    });
                }
                else {
                    $("#add_new_student_Attendance_modal").modal('hide');
                    Notification.success({
                        message: 'Student Attendance  Created  successfully',
                        positionX: 'center',
                        delay: 2000
                    });
                    $scope.getStudentList();
                }

            }, function (error) {
                Notification.error({

                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });

                $scope.isDisabled = false;

            });
        }

        $scope.ViewStudentAttendenceList = function (student) {
            $(".loader").css("display", "block");
            if (angular.isUndefined(student.studentAttendenceId)) {
                student.studentAttendenceId = "";
            }
            $http.post($scope.bshimServerURL + "/getStudentAttendenceDetails?studentAttendenceId=" + student.studentAttendenceId+'&type='+"Student").then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.viewStudentattendence(student)
            })
        };

        $scope.viewStudentattendence = function (data, attendenceDetailsList) {
            $scope.viewStudentAttendenceList =data;
            $scope.studentAttendenceId =data.studentAttendenceId;
            console.log(data);
            angular.forEach(attendenceDetailsList, function (val, key) {
                $scope.list = [];
                angular.forEach(val.installmentsPojos, function (val1, key) {
                    $scope.list.push({
                        checkBox: val1.checkBox,
                        dueDate: new Date(val1.dueDate),
                        paidAmt: parseInt(val1.paidAmt),
                        installmentsAmount: parseInt(val1.dueAmt)
                    })
                })
            });
            angular.forEach($scope.selectedFeeList, function (val, key) {
                if (val.checkBox == false) {
                    val.check = false;
                } else {
                    val.check = true;
                }
            });
            $scope.getTotal();
            $scope.back_wizardEdit();
            $scope.operation = 'View';
            $('#view-student-title').text("VIEW STUDENT");
            $("#view_StudentAttendence_modal").modal('show');
            // $("#saveId").hide();
        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };





        $scope.deleteStudentAttendence = function (data) {
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
                    if (result == true) {
                        $http.post('/bs' + '/deleteStudentAttendence?studentAttendenceId='+ data).then(function (response) {
                            var data = response.data;
                            $scope.status = data.status;
                            if ($scope.status == "InActive") {
                                Notification.success({
                                    message: 'It is Already in use So Status Changes to Inactive',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            } else {
                                Notification.success({
                                    message: 'Successfully Deleted',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            $scope.getStudentList();
                        }, function (error) {
                            Notification.error({
                                message: 'Something went wrong, please try again',
                                positionX: 'center',
                                delay: 2000
                            });
                        });
                    }
                }
            });
        };

        });
app.controller('enqController',
    function ($scope, $timeout, $http, $location, $filter, Notification) {
        $scope.bshimServerURL = "/bs";
        $scope.operation = 'Create';
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        // $scope.pick=false;

        $scope.open1 = function() {
            $scope.popup1.opened = true;
        };
        $scope.remarklist= [];
        $scope.popup1 = {
            opened: false
        };
        $scope.openDate2 = function () {
            $scope.popup2.opened = true;
        };
        $scope.popup2 = {
            opened: false
        };
        $scope.today = new Date();
        $scope.today1 = function () {
            $scope.enqDate = new Date();
            $scope.date = new Date();
        };
        $scope.today1();
        $scope.format = 'dd/MM/yyyy';
        $("#back123").hide();
        $("#nextEdit123").hide();
        $("#saveIds").show();
        $scope.next_wizard = function (grade) {

            if ( grade == null || grade == "") {
                Notification.error({
                    message: 'please Select Level',
                    positionX: 'center',
                    delay: 2000
                });
            }
            else if (angular.isUndefined($scope.studentName) || $scope.studentName == '') {
                Notification.warning({message: 'please Enter the student Name', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.fatherName) || $scope.fatherName == '') {
                    Notification.warning({message: 'please Enter the Father Name', positionX: 'center', delay: 2000});
                    $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.motherName) || $scope.motherName == '') {
                    Notification.warning({message: 'please Enter the Mother Name', positionX: 'center', delay: 2000});
                    $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.telephoneNo) || $scope.telephoneNo == '') {
                Notification.warning({message: 'please Enter the Telephone No', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.address) || $scope.address == '') {
                Notification.warning({message: 'please Enter the Address', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.emailId) || $scope.emailId == '') {
                Notification.warning({message: 'please Enter the EmailId', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else {
                $("#sub_steps12").removeClass("in active");
                $("#sub_steps22").addClass("tab-pane fade in active");
                if($scope.operation == 'Edit'){
                    $("#nextEdit123").hide();
                }
                $("#next123").hide();
                $("#back123").show();
                $("#saveIds").show();
            }
        }
        $scope.back_wizard = function () {

            $("#sub_steps22").removeClass("in active");
            $("#sub_steps12").addClass("tab-pane fade in active");
            if($scope.operation == 'Edit'){
                $("#nextEdit123").show();
            }else{
                $("#next123").show();
            }

            $("#back123").hide();
            $("#saveIds").show();
        }
        $scope.remarklist= [];
        $scope.addNew = function(){
            $scope.remarklist.push({});
        }

        $scope.getStudentListBasedOnStatus = function (status) {
            $scope.statusText=null;
            if (status != null) {
                if (angular.isUndefined(status)) {
                    status = "";
                }
                $http.post($scope.bshimServerURL + "/getStudentListBasedOnStatus?searchText=" + status).then(function (response) {
                    var data = response.data.object;
                    console.log(data);
                    $scope.gradeLists = data;

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            }
        };
        $scope.getStudentListBasedOnStatus();

        $scope.openAssessment = function (data) {
            $scope.studName=data.studentName;
            $scope.eId=data.enquiryId;
            $scope.telephone=data.telephoneNo;
            $scope.gd = parseInt(data.grade);
            if(data.assessmentsPojos!=""&& data.assessmentsPojos !=null){
                $scope.remarklist = angular.fromJson(data.assessmentsPojos.remarks);
                $scope.assessStatus =data.assessmentsPojos.assessmentStatus;
                $scope.date = new Date(data.assessmentsPojos.date);
            }
            $scope.date = new Date();
            $scope.parentName = data.fatherName;
            $scope.mother = data.motherName;

            if(data.status == 'Assesment'){
                $("#add_new_assessment_modal").modal('show');
            }
        };
        $scope.removeAssesmnts = function () {
            $scope.enquiryId ="";
            $scope.studName ="";
            $scope.parentName ="";
            $scope.telephone ="";
            $scope.gd ="";
            $scope.date="";
            $scope.assessStatus="";
        };
        $scope.removeRemarksList = function(data){
            $scope.index=data;
            $scope.remarklist.splice($scope.index,1);
        };
        $scope.removeCounslar = function () {
            $scope.cRemarks ="";
            $scope.cGrade ="";
        };


        $scope.saveAssessment = function(data){

            var saveAssessment;
            saveAssessment = {
                enquiryId: $scope.eId,
                studentName: $scope.studName,
                parentName: $scope.parentName,
                phoneNo: $scope.telephone,
                gradeId: $scope.gd,
                date: $scope.date,
                assessmentStatus: $scope.assessStatus,
                remarksList: data,
                fatherName :$scope.parentName,
                motherName:$scope.mother
            };
            $http.post($scope.bshimServerURL + '/AssessmentsSave', angular.toJson(saveAssessment, "Create")).then(function (response, status, headers, config) {
                var data = response.data;
                $scope.removeAssesmnts();
                $scope.getEnqList();
                $("#add_new_assessment_modal").modal('hide');
                Notification.success({
                    message: 'Assesment Created  successfully',
                    positionX: 'center',
                    delay: 2000
                });

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });

        }

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

        $scope.getFeeTypeMasterLists = function (grdID) {
            if (grdID != null) {
                $(".loader").css("display", "block");
                $http.post($scope.bshimServerURL + "/getFeeLists?gradeId=" + grdID).then(function (response) {
                    var data = response.data.object;
                    $scope.registerfeeList = data;
                    $scope.selectedFeeList = [];
                    angular.forEach($scope.registerfeeList, function (data) {
                        $scope.selectedFeeList.push({
                            feeTypeId: data.feeTypeId,
                            feeTypeName: data.feeTypeName,
                            feeAmount: data.feeAmount,
                            gradeId: data.level.branchId,
                            paidAmount: parseInt(0),
                            checkBox: false,
                        });

                    })
                    $scope.totalPayableAmt = parseInt(0);
                    angular.forEach($scope.selectedFeeList, function (value, key) {
                        $scope.totalPayableAmt += parseInt(value.paidAmount);


                    });

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            }
        };
        $scope.getEditFeeTypeMasterList = function (val) {

            $scope.selectedFeeList = [];
            $scope.registerfeeList = val.feePojoList;
            angular.forEach($scope.registerfeeList, function (data) {
                $scope.selectedFeeList.push({
                    feeTypeId: data.feeTypeId,
                    feeTypeName: data.feeTypeName,
                    feeAmount: data.feeAmount,
                    gradeId: data.gradeId,
                    paidAmount: data.paidAmount,
                    checkBox: data.checkBox,
                });

            });
            $scope.totalPayableAmt = parseInt(0);
            angular.forEach($scope.selectedFeeList, function (value, key) {
                $scope.totalPayableAmt += parseInt(value.paidAmount);
            });
        };
        $scope.removeEnq = function () {

            $scope.enquiryId ="";
            $scope.enquiryNo ="";
            $scope.enqClass ="";
            $scope.enqDate = new Date();
            $scope.studentName ="";
            $scope.telephoneNo ="";
            $scope.address ="";
            $scope.emailId="";
            $scope.reference="";
            $scope.grade="";
            $scope.siblingDetails="";
            $scope.siblingGrade="";
            $scope.academicDetailsOfStud="";
            $scope.status="";
            $scope.fatherName="";
            $scope.motherName="";
            $scope.referrel="";
            $scope.transport="";
            $scope.option="";
            $scope.annualIncome="";
            $scope.qualification="";
            $scope.profession="";
            $scope.pick="";
        };

        $scope.getGradeList = function () {
            $scope.gradeList=null;
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL + '/getGradeList').then(function (response) {
                var data = response.data.object;
                $scope.gradeList = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getGradeList();
        $scope.getEnqGenNo = function () {
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL  + '/generateEnqNo').then(function (response) {
                $scope.enquiryNo= response.data;


            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };


        $scope.getEnqList = function () {
            if (angular.isUndefined($scope.statusList)) {
                $scope.statusList = "";
            }
            if (angular.isUndefined($scope.gradeVal)|| $scope.gradeVal==null) {
                $scope.gradeVal = "";
            }
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL  + '/getEnqList?status=' + $scope.statusList+"&grade="+$scope.gradeVal).then(function (response) {
                var data = response.data;
                $scope.enqFormList= data;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };

        $scope.getEnqList();
        $scope.editEnqForm = function(data) {
            $("#next123").hide();
            $("#nextEdit123").show();
            $scope.enquiryId =data.enquiryId;
            $scope.enquiryNo =data.enquiryNo;
            $scope.enqClass =data.enqClass;
            $scope.enqDate =new Date(data.enqDate);
            $scope.studentName =data.studentName;
            $scope.telephoneNo =data.telephoneNo;
            $scope.address =data.address;
            $scope.emailId=data.emailId;
            $scope.reference=data.reference;
            $scope.grade=parseInt(data.grade);
            $scope.siblingDetails=data.siblingDetails;
            $scope.siblingGrade=parseInt(data.siblingGrade);
            $scope.academicDetailsOfStud=data.academicDetailsOfStud;
            $scope.statusText=data.status;
            $scope.fatherName =data.fatherName;
            $scope.motherName = data.motherName;
            $scope.referrel = data.referrel;
            $scope.transport = data.transport;
            $scope.option = data.option1;
            $scope.annualIncome = data.annualIncome;
            $scope.qualification = data.qualification;
            $scope.profession = data.profession;
            $scope.pick = data.pick;
            $scope.operation = 'Edit';
            $scope.getEditFeeTypeMasterList(data);
            $('#enq-title').text("Edit EnquirForm");
            $("#add_new_enquiry_modal").modal('show');
        }, function (error) {
            Notification.error({
                message: 'Something went wrong, please try again',
                positionX: 'center',
                delay: 2000
            });
        };
        $scope.addEnqForm1 = function () {
            $scope.getEnqGenNo();
            $('#enq-title').text("Add Branch");
            $scope.statusText = "Enquiry";
            $("#submit").text("Save");
            $scope.back_wizard();
            $("#sub_steps22").removeClass("in active");
            $scope.selectedFeeList = [];
            $("#add_new_enquiry_modal").modal('show');
        };


        $scope.removesDetails = function(){
            $("#sub_steps22").removeClass("in active");
            $("#sub_steps12").addClass("tab-pane fade in active");
        }

        // $scope.hidefunc=function(){
        //     if($scope.transport=="YES"){
        //         $scope.pick=true;
        //     }
        //     else{
        //         $scope.pick=false;
        //     }
        // }


        $scope.saveEnqForm = function () {
            if($scope.totalPayableAmt == 0){
                $scope.statusText = "Enquiry";
            }else{
                $scope.statusText ="Assesment";
            }
            if($scope.totalPayableAmt > 0) {
                angular.forEach($scope.selectedFeeList, function (value, key) {
                    if (value.checkBox == true && (value.paidAmount > value.feeAmount)) {
                        Notification.warning({message: 'Paying Amount must be equal to Fee Amount', positionX: 'center', delay: 2000});
                        $scope.isDisabled = false;
                    }
                    if (value.checkBox == true && (value.paidAmount != value.feeAmount)) {
                        Notification.warning({message: 'please pay the Full Amount', positionX: 'center', delay: 2000});
                        $scope.isDisabled = false;
                    }

                });
            }

            if (angular.isUndefined($scope.studentName) || $scope.studentName == ''||$scope.studentName==null) {
                Notification.warning({message: 'please Enter the student Name', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.fatherName) || $scope.fatherName == '') {
                Notification.warning({message: 'please Enter the Father Name', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.motherName) || $scope.motherName == '') {
                Notification.warning({message: 'please Enter the Mother Name', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.telephoneNo) || $scope.telephoneNo == '') {
                Notification.warning({message: 'please Enter the Telephone No', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.address) || $scope.address == '') {
                Notification.warning({message: 'please Enter the Address', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }
            else if (angular.isUndefined($scope.emailId) || $scope.emailId == '') {
                Notification.warning({message: 'please Enter the EmailId', positionX: 'center', delay: 2000});
                $scope.isDisabled = false;
            }

            else {
                var saveEnqDetails;
                saveEnqDetails = {
                    enquiryId: $scope.enquiryId,
                    enquiryNo: $scope.enquiryNo,
                    enqDate: $scope.enqDate,
                    studentName: $scope.studentName,
                    telephoneNo: $scope.telephoneNo,
                    address: $scope.address,
                    emailId: $scope.emailId,
                    reference: $scope.reference,
                    grade: $scope.grade,
                    siblingDetails: $scope.siblingDetails,
                    siblingGrade: $scope.siblingGrade,
                    academicDetailsOfStud: $scope.academicDetailsOfStud,
                    status: $scope.statusText,
                    totalPaidAmt: $scope.totalPayableAmt,
                    feePojoList: $scope.selectedFeeList,
                    fatherName: $scope.fatherName,
                    motherName: $scope.motherName,
                    referrel: $scope.referrel,
                    transport: $scope.transport,
                    option1: $scope.option,
                    annualIncome: $scope.annualIncome,
                    qualification: $scope.qualification,
                    profession: $scope.profession,
                    enqClass: $scope.enqClass,
                    pick: $scope.pick
                };
                $http.post($scope.bshimServerURL + '/EnquiryFormSave', angular.toJson(saveEnqDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $scope.removeEnq();
                        $scope.removesDetails();
                        $scope.getEnqList();
                        $("#add_new_enquiry_modal").modal('hide');
                        Notification.success({
                            message: 'Enquiry Form Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                    }

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });


                // }
                // $scope.isDisabled= false;
            }
        };

        $scope.deleteEnqForm = function (id) {
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
                        $http.post($scope.bshimServerURL +"/enquiryDelete?enqId="+id, angular.toJson()).then(function (response, status, headers, config) {
                            var data = response.data;
                            $scope.getEnqList();
                            if(data==true) {
                                Notification.success({
                                    message: 'Successfully Deleted',
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


        $scope.openConsularPopup = function (enquery) {
            $scope.eqId= enquery.enquiryId;
            $scope.counsalrDetails=[] ;
            $scope.counsalrDetails=enquery.counslarPojos;
            if($scope.counsalrDetails!=null) {
                if (enquery.counslarPojos.grade != null) {
                    $scope.cGrade = parseInt(enquery.counslarPojos.grade);
                }
                if (enquery.counslarPojos.remarks != null) {
                    $scope.cRemarks = enquery.counslarPojos.remarks;
                }
            }
            $http.post($scope.bshimServerURL + '/getAssessmentsList?enquiryId='+enquery.enquiryId).then(function (response) {
                var data = response.data.object;
                if(data.assessmentStatus != 'Accepted'){
                    $("#enqueryno").val(enquery.enquiryNo);
                    $("#consular").modal('show');
                }
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })



        }


        $scope.getEnqTotal = function (checkStatus) {
            if(!angular.isUndefined(checkStatus)) {
                if (checkStatus.checkBox == true) {
                    checkStatus.paidAmount = checkStatus.feeAmount;

                } else {
                    checkStatus.paidAmount = parseInt(0);
                }
            }
            $scope.totalPayableAmt = parseInt(0);
            angular.forEach($scope.selectedFeeList, function (value, key) {
                $scope.totalPayableAmt += parseInt(value.paidAmount);


            });
        }

        $scope.saveCounslar = function(){

            $http.post($scope.bshimServerURL + '/counslarSave?enquiryId='+$scope.eqId +"&remarks="+$scope.cRemarks +"&grade=" +$scope.cGrade).then(function (response) {
                var data= response;

            });
            $("#consular").modal('hide');
            $scope.removeCounslar();
            $scope.getEnqList();

        }

        $scope.openAdmission = function (enquery) {
            $scope.eqId= enquery.enquiryId;
            $http.post($scope.bshimServerURL + '/getAssessmentsList?enquiryId='+enquery.enquiryId).then(function (response) {
                var data = response.data.object;
                if(data.assessmentStatus != 'Prohibited'){

                    $scope.addNewAdmission($scope.eqId);
                }
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })



        }

    });
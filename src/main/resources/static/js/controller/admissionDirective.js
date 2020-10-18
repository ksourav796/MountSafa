/**
 * Created by sahera on 18/3/19.
 */


app.directive("addNewAdmission", function ($http, Notification) {
    return {
        restrict: 'E',
        templateUrl: "partials/addmissionDirective.html",
        link: function ($scope, $timeout, $location, $filter) {
            $scope.bshimServerURL = "/bs";
            $scope.operation = 'Create';
            $scope.inactiveStatus = "Active";
            $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
            $scope.next_wizardEdit = function () {
                if ($scope.acdYearId === '' || $scope.acdYearId == null || angular.isUndefined($scope.acdYearId)) {
                    Notification.error({message: 'Enter Acadamic Year', positionX: 'center', delay: 2000});
                }
                else if ($scope.levelId === '' || $scope.levelId == null || angular.isUndefined($scope.levelId)) {
                    Notification.error({message: 'Enter level ', positionX: 'center', delay: 2000});
                }
                else if ($scope.classId === '' || $scope.classId == null || angular.isUndefined($scope.classId)) {
                    Notification.error({message: 'Enter class', positionX: 'center', delay: 2000});
                }
                else if ($scope.semester === '' || $scope.semester == null || angular.isUndefined($scope.semester)) {
                    Notification.error({message: 'Enter semester', positionX: 'center', delay: 2000});
                }
                else if ($scope.stdname === '' || $scope.stdname == null || angular.isUndefined($scope.stdname)) {
                    Notification.error({message: 'Enter Student Name', positionX: 'center', delay: 2000});
                }
                else if ($scope.genderText === '' || $scope.genderText == null || angular.isUndefined($scope.genderText)) {
                    Notification.error({message: 'Enter Student Gender', positionX: 'center', delay: 2000});
                }

                else {
                    $("#sub_step12").removeClass("in active");
                    $("#sub_step22").addClass("in active");
                    $("#next").hide();
                    $("#next1").show();
                    $("#back1").show();
                    $scope.getFeeTypeMasterList($scope.acdYearId, $scope.levelId, $scope.stuId);
                }
            }
            $scope.next_wizardEdit1 = function () {
                $("#sub_step22").removeClass("in active");
                $("#sub_step23").addClass("in active");
                $("#next1").hide();
                $("#next2").show();
                $("#back1").show();

            }
            $scope.next_wizardEdit2 = function () {
                $("#sub_step23").removeClass("in active");
                $("#sub_step24").addClass("in active");
                $("#next2").hide();
                $("#next3").show();
                $("#back1").show();

            }
            $scope.next_wizardEdit3 = function () {
                $("#sub_step24").removeClass("in active");
                $("#sub_step25").addClass("in active");
                $("#next3").hide();
                $("#back1").show();
                $("#saveId").show();


            }
            $scope.back_wizardEdit = function () {
                $("#sub_step22").removeClass("in active");
                $("#sub_step23").removeClass("in active");
                $("#sub_step24").removeClass("in active");
                $("#sub_step25").removeClass("in active");
                $("#sub_step12").addClass("in active");
                $("#next").show();
                $("#back1").hide();

                $("#next1").hide();
                $("#next2").hide();
                $("#next3").hide();
                $("#saveId").hide();


            }
            $scope.back_wizardEdit1 = function () {
                $("#sub_step22").removeClass("in active");
                $("#sub_step23").removeClass("in active");
                $("#sub_step24").removeClass("in active");
                $("#sub_step25").removeClass("in active");
                $("#sub_step12").addClass("in active");
                $("#next").show();
                $("#back").hide();

            };
            $scope.format = 'dd/MM/yyyy';
            $scope.open1 = function () {
                $scope.popup1.opened = true;
            };

            $scope.popup1 = {
                opened: false
            };
            $scope.format = 'dd/MM/yyyy';
            $scope.openad1 = function () {
                $scope.popupad1.opened = true;
            };

            $scope.popupad1 = {
                opened: false
            };
            $scope.open2 = function () {
                $scope.popup2.opened = true;
            };

            $scope.popup2 = {
                opened: false
            };
            $scope.open3 = function ($event, dt) {
                $event.preventDefault();
                $event.stopPropagation();
                dt.open = true;
            };

            $scope.previousSchoolsDetails = [];
            $scope.addNewPreviousDetails = function(){
                if($scope.previousSchoolsDetails==""){
                    $scope.previousSchoolsDetails = [];
                }
                $scope.previousSchoolsDetails.push({
                    schoolno:'',
                    schoolName:'',
                    schoolYear:'',
                    schoolGrade:'',

                });
            };

            $scope.sbillingDetailsList = [];
            $scope.AddNewRowForSiblings = function(){
                if($scope.sbillingDetailsList==""){
                    $scope.sbillingDetailsList = [];
                }
                $scope.sbillingDetailsList.push({
                    sbillPrevSchool:'',
                    sbillDOB:new Date(),
                    sbillName:'',
                    sbilladminNo:'',

                });
            };

            $scope.spokenList = [];
            $scope.addSpokenLang = function(){
                if($scope.spokenList==""){
                    $scope.spokenList = [];
                }
                $scope.spokenList.push({
                    spokenlaug:''
                });
            };

            $scope.addNewAdmission = function (enquiryId) {
                $scope.dateofbirth=new Date();
                $scope.admindt=new Date();
                $scope.fatherDOB=new Date();
                $scope.motherDOB=new Date();
                $scope.age = "0";
                $scope.sameascorresponding=true;
                $scope.guarSameAsCorresAdd=true;
                if (enquiryId != null) {
                    $('#admission-title').text("Add Admission");
                    $scope.StatusText = "Active";
                    $http.post($scope.bshimServerURL + '/getAssessmentsList?enquiryId=' + enquiryId).then(function (response) {
                        var data = response.data.object;
                        $scope.levelId = parseInt(data.gradeId);
                        $scope.stdname = data.studentName;
                        $scope.fatherName = data.parentName;
                        $scope.fatherTelNo = data.phoneNo;
                        $scope.motherName = data.motherName;
                        $scope.motherName = data.motherName;

                    });
                }
                $scope.addSpokenLang();
                $scope.addNewPreviousDetails();
                $scope.AddNewRowForSiblings();
                $("#sub_step25").removeClass("in active");
                $("#sub_step12").addClass("in active");
                $("#back1").hide();
                $("#next").show();
                $("#saveId").hide();
                $("#submit").text("Save");
                $("#add_new_admission_modal").modal('show');
            };
            $scope.getGradeList = function () {
                $(".loader").css("display", "block");
                $http.post($scope.bshimServerURL + "/getGradeList").then(function (response) {
                    var data = response.data.object;
                    console.log(data);
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

            $scope.calculateAge = function () {
                var ageDif = Date.now() - $scope.dateofbirth.getTime();
                var ageDate = new Date(ageDif); // miliseconds from epoch
                $scope.age= Math.abs(ageDate.getUTCFullYear() - 1970);
            }

            $scope.getBranchesList = function (val) {
                if (angular.isUndefined(val)) {
                    val = "";
                }

                $(".loader").css("display", "block");
                $http.post($scope.bshimServerURL  + '/getBranchesList?searchText=' + val).then(function (response) {
                    var data = response.data;
                    $scope.branchesList= data;
                    $scope.searchText = val;

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })

            };
            $scope.getBranchesList();
            $scope.getAcademicYearList = function () {
                $(".loader").css("display", "block");
                $http.post($scope.bshimServerURL + "/getAcademicYearList").then(function (response) {
                    var data = response.data.object;
                    $scope.academicYearList = data;

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })

            };
            $scope.getAcademicYearList();

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
            $scope.getCountryList = function (val) {
                if (angular.isUndefined(val)) {
                    val = "";
                }

                $(".loader").css("display", "block");
                $http.post($scope.bshimServerURL  + '/getCountryList?searchText=' + val).then(function (response) {
                    var data = response.data;
                    $scope.countryList= data;
                    $scope.searchText = val;

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })

            };
            $scope.getCountryList();

            $scope.countryState = function(country,type){
                var url = "/bs/getCountryState?countryId=" + country;
                $http.post(url).then(function (response) {
                    var data = response.data;
                    if(type=='CorrespondanceAdr'){
                        $scope.stateList = angular.copy(data);
                        }
                    else if(type=='PerAdr'){
                        $scope.statePerList = angular.copy(data);
                    }
                    else if(type=='FatherAdr'){
                        $scope.stateFatherList = angular.copy(data);
                    }
                    else if(type=='MotherAdr'){
                        $scope.stateMotherList = angular.copy(data);
                    }
                    else if(type=='GuarCorAdr'){
                        $scope.stateGuarList = angular.copy(data);
                    }
                    else if(type=='GuarPerAdr'){
                        $scope.stateGuarPerList = angular.copy(data);
                    }
                })
            }
            $scope.countryState();

            $scope.stateCity = function(state,type){
                if (angular.isUndefined(state)|| state==null) {
                    state = "";
                }
                var url = "/bs/getStateCity?stateId=" + state;
                $http.post(url).then(function (response) {
                    var data = response.data;
                    if(type=='CorrespondanceAdr'){
                        $scope.cityList = angular.copy(data);

                    }
                    else if(type=='PerAdr'){
                        $scope.cityPerList = angular.copy(data);
                    }
                    else if(type=='FatherAdr'){
                        $scope.cityFatherList = angular.copy(data);
                    }
                    else if(type=='MotherAdr'){
                        $scope.cityMotherList = angular.copy(data);
                    }
                    else if(type=='GuarCorAdr'){
                        $scope.cityGuarList = angular.copy(data);
                    }
                    else if(type=='GuarPerAdr'){
                        $scope.cityGuarPerList = angular.copy(data);
                    }
                })
            }
            $scope.stateCity();

            $scope.getSemesterList = function () {
                $http.post($scope.bshimServerURL + '/getSemesterList').then(function (response) {
                    var data = response.data;
                    $scope.semesterList = data;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });
            };
            $scope.getSemesterList();

            $scope.saveAdmission = function () {
                var saveAdmissionDetails;
                    saveAdmissionDetails = {
                        studentId: $scope.studentId,
                        countryId: $scope.countryId,
                        countryName: $scope.CountryNameText,
                        branchId: $scope.branchId,
                        gradeId: $scope.levelId,
                        classId: $scope.classId,
                        admissionDate: $scope.admindt,
                        gender: $scope.genderText,
                        acdYearId: $scope.acdYearId,
                        passportNo: $scope.PassportNoNameText,
                        birthCertificate: $scope.birthcertificateText,
                        age: $scope.age,
                        telNo: $scope.phone,
                        email: $scope.emailid,
                        semester: $scope.semester,
                        studentName: $scope.stdname,
                        nationality: $scope.nationality,
                        dateofbirth: $scope.dateofbirth,
                        placeOfBirth: $scope.placeOfBirth,
                        fatherOccupation: $scope.fatheroccupation,
                        language: $scope.launguage,
                        medicine: $scope.medicine,
                        corresponding: angular.toJson($scope.corresponding),
                        previousSchoolsDetails: angular.toJson($scope.previousSchoolsDetails),
                        siblingsInformation: angular.toJson($scope.sbillingDetailsList),
                        spokenlang: angular.toJson($scope.spokenList),
                        permanent: angular.toJson($scope.permanent),
                        motherOfficeAddress: angular.toJson($scope.motherOfficeAddress),
                        fatherOfficeAddress: angular.toJson($scope.fatherOfficeAddress),
                        gaurdianPermanentAddress: angular.toJson($scope.guardPerAddress),
                        gaurdianCorrespondenceAddress: angular.toJson($scope.guardCorrAddress),
                        immunization: $scope.immunization,
                        food: $scope.food,
                        others: $scope.others,
                        asthma: $scope.asthma,
                        epilepsy: $scope.epilepsy,
                        illnessOthers: $scope.illnessOthers,
                        medicineState: $scope.medicineState,
                        immunizationState: $scope.immunizationState,
                        foodState: $scope.foodState,
                        othersState: $scope.othersState,
                        asthmaState: $scope.asthmaState,
                        epilepsyState: $scope.epilepsyState,
                        illnessOthersState: $scope.illnessOthersState,
                        fatherName: $scope.fatherName,
                        fatherPassportNo: $scope.fatherPassNo,
                        fatherICNO: $scope.fatherICNO,
                        faxNo: $scope.fatherFax,
                        fatherNationality: $scope.fathernationality,
                        fathersDOB: $scope.fatherDOB,
                        fatherEducation: $scope.fathereducation,
                        incomePerAnnum: $scope.fatherincome,
                        fatherEmployer: $scope.fatherEmployer,
                        natureofbusiness: $scope.fatherBusiness,
                        fatherContactNo: $scope.fatherTelNo,
                        fatherMobileNo: $scope.fatherMobileNo,
                        fatherEmailId: $scope.fatherEmail,
                        motherPassportNo: $scope.motherPassNo,
                        motherName: $scope.motherName,
                        motherICNO: $scope.motherICNO,
                        motherfaxNo: $scope.motherFax,
                        fatherPrimaryContact: $scope.parentPrimaryContact,
                        motherPrimaryContact: $scope.motherPrimaryContact,
                        gaurdianPrimaryContact: $scope.GuarPrimaryContact,
                        motherNationality: $scope.mothernationality,
                        motherDOB: $scope.motherDOB,
                        motherOccupation: $scope.motheroccupation,
                        motherEducation: $scope.mothereducation,
                        motherincomePerAnnum: $scope.motherincome,
                        motherEmployer: $scope.motherEmployer,
                        mothernatureofbusiness: $scope.motherBusiness,
                        motherContactNo: $scope.motherTelNo,
                        motherMobileNo: $scope.motherMobileNo,
                        motherEmailId: $scope.motherEmail,
                        gaurdianName: $scope.guardianName,
                        gaurdianNationality: $scope.guardiannationality,
                        feeTypeMasterPojoList: $scope.selectedFeeList,
                        gaurdianRelation: $scope.guardianRelation,
                        gaurdianPassportNo: $scope.guardianPassNo,
                        gaurdianOccupation: $scope.guardianoccupation,
                        gaurdianEmployeer: $scope.guardianEmployer,
                        gaurdianICNO: $scope.guardianICNO,
                        gaurdianMobileNo: $scope.guardianMobileNo,
                        guardianTelNo: $scope.guardianTelNo,
                        studentProfileId: $scope.profileId,
                        studentPhoto: $scope.fileToUpload,
                        studBirthCer: $scope.fileToUpload1,
                        studIdentityCard: $scope.fileToUpload2,
                        parentIdentityCard: $scope.fileToUpload3,
                        parentMarrCer: $scope.fileToUpload4,
                        prevSchoolCer: $scope.fileToUpload5,
                        studOtherDoc: $scope.fileToUpload6,
                        sameascorresponding:$scope.sameascorresponding,
                        guarSameAsCorresAdd:$scope.guarSameAsCorresAdd
                    };
                    $scope.selectedFeeList = [] ;
                    angular.forEach($scope.feeTypeMasterList, function (val, key) {
                        $scope.list = [];
                        angular.forEach(val.installmentsPojos, function (val1, key) {
                            $scope.list.push({
                                checkBox: val1.checkBox,
                                dueDate: new Date(val1.dueDate),
                                paidAmt: parseInt(val1.paidAmt),
                                installmentsAmount: parseInt(val1.dueAmt),
                                discountRemarks: val1.discountRemarks
                            })
                        })
                        $scope.selectedFeeList.push({
                            feeTypeId: val.feeTypeId,
                            studentFeeDetailsId: val.studentFeeDetailsId,
                            feeTypeName: val.feeTypeName,
                            feeAmount:val.feeTypeAmount,
                            status: val.status,
                            installmentsAmount: val.installmentsAmount,
                            installments: val.noOfInstallments,
                            acdyrmaster: val.acdyrmaster,
                            discountRemarks: val.discountRemarks,
                            discountType: val.discountType,
                            dueDate: val.dueDate,
                            payable: val.payable,
                            discount: val.discount,
                            gradeMaster: val.gradeMaster,
                            paidAmt: val.paidAmount,
                            value: val.value,
                            payingFee: val.payable,
                            installmentsPojosList: $scope.list,
                            checkBox: val.checkBox,
                            feeTypeStatus: val.feeTypeStatus,
                            itemImage: $scope.fileToUpload
                        });
                    });
                    angular.forEach($scope.selectedFeeList, function (val, key) {
                        if (val.checkBox == false) {
                            val.check = false;
                        } else {
                            val.check = true;
                        }
                        if (val.installments > 1 && val.value == "false") {
                            val.value = "true";
                        }
                    })
                    $scope.getTotal();
                    $http.post($scope.bshimServerURL + '/saveAdmission', angular.toJson(saveAdmissionDetails, "Create")).then(function (response, status, headers, config) {
                        var data = response.data;
                        if (data == "") {
                            Notification.error({message: 'Already exists', positionX: 'center', delay: 2000});
                        }
                        else {
                            $scope.getAdmissionList();
                            $scope.removeAdmissioDetails();
                            $("#add_new_admission_modal").modal('hide');
                            $("#add_new_fee_modal").modal('hide');
                            if (!angular.isUndefined(data) && data !== null) {
                                $scope.countrySearchText = "";
                            }
                            Notification.success({
                                message: 'Admission Created  successfully',
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
            };
            $scope.removespoken = function(data){
                $scope.index=data;
                $scope.spokenList.splice($scope.index,1);
            };
            $scope.removeSbillingDetails = function(data){
                $scope.index=data;
                $scope.sbillingDetailsList.splice($scope.index,1);
            };
            $scope.removePreviousSchoolsDetails = function(data){
                $scope.index=data;
                $scope.previousSchoolsDetails.splice($scope.index,1);
            };
            $scope.getFeeTypeMasterList = function (acId, grdID) {
                if (grdID != null && acId != null) {
                    $(".loader").css("display", "block");
                    $http.post($scope.bshimServerURL + "/getFeeList?academicId=" + acId + "&gradeId=" + grdID).then(function (response) {
                        var data = response.data.object;
                        console.log("yyyyyy:" + data);
                        $scope.feeTypeMasterList = data;
                        $scope.selectedFeeList = [];
                        angular.forEach($scope.feeTypeMasterList, function (data) {
                            $scope.selectedFeeList.push({
                                feeTypeId: data.feeTypeId,
                                feeTypeName: data.feeTypeName,
                                feeAmount: data.feeAmount,
                                status: data.status,
                                installmentsAmount: data.installmentsAmount,
                                installments: data.installments,
                                acdyrmaster: data.acdyrmaster,
                                dueDate: data.dueDate,
                                discountRemarks: null,
                                gradeMaster: data.gradeMaster,
                                value: data.value,
                                payingFee: data.payingFee,
                                paidAmt: parseInt(0),
                                installmentsPojosList: data.installmentsPojosList,
                                checkBox: true,
                                feeTypeStatus: true
                            });
                        })
                        $scope.setInstallments();
                    }, function (error) {
                        Notification.error({
                            message: 'Something went wrong, please try again',
                            positionX: 'center',
                            delay: 2000
                        });
                    })
                }
            };
            $scope.setInstallments = function () {
                angular.forEach($scope.selectedFeeList, function (val, key) {
                    val.installments = 1;
                    val.installmentsPojosList.push({
                        installmentsAmount: val.feeAmount,
                        dueDate: new Date()
                    })

                })
                $scope.getTotal();
            };
            $scope.getTotal = function () {

                $scope.totalFeeValue = parseInt(0);
                $scope.totalDiscountAmt = parseInt(0);
                $scope.totalPayableAmt = parseInt(0);
                $scope.noOfInstallments = parseInt(0);
                $scope.totalInstallmentsAmt = parseInt(0);
                angular.forEach($scope.selectedFeeList, function (value, key) {

                    if (value.checkBox === true) {
                        $scope.totalFeeValue += parseInt(value.feeAmount);
                        if (parseInt(value.discount) > 0)
                            $scope.totalDiscountAmt += parseInt(value.discount);
                        $scope.totalPayableAmt += parseInt(value.payingFee);
                        $scope.noOfInstallments += parseInt(value.installments);
                        angular.forEach(value.installmentsPojosList, function (val, key) {
                            $scope.totalInstallmentsAmt += parseInt(val.installmentsAmount);
                        })
                    }
                    else {
                        value.installmentsPojosList = [];
                        value.installments = 1;
                        value.installmentsPojosList.push({'installmentsAmount': '', 'dueDate': ''});
                        if (angular.isUndefined(value.discount)) {
                            value.discount = parseInt(0);
                        }
                        value.installmentsPojosList[0].installmentsAmount = parseInt(value.feeAmount - parseInt(value.discount));
                        value.installmentsPojosList[0].dueDate = new Date();
                    }
                    if (isNaN($scope.totalDiscountAmt)) {

                        $scope.totalDiscountAmt = parseInt(0);
                    }
                    if (isNaN($scope.totalPayableAmt)) {
                        $scope.totalPayableAmt = parseInt(0);
                    }
                });
            }

            $scope.getAdmissionList = function (page) {
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
                if (angular.isUndefined($scope.searchText)) {
                    $scope.searchText = "";
                }
                var classes= $scope.className;
                if(angular.isUndefined(classes)||classes==null){
                    classes="";
                    $scope.className=null;
                }
                $http.post($scope.bshimServerURL + "/getpaginatedadmission?&type=" + $scope.inactiveStatus+ '&searchText=' + $scope.searchText+ '&className=' + classes, angular.toJson(paginationDetails)).then(function (response) {
                    var data = response.data;
                    console.log(data);
                    var i = 0;
                    $scope.admissionList = data.list;
                    $scope.first = data.firstPage;
                    $scope.last = data.lastPage;
                    $scope.prev = data.prevPage;
                    $scope.next = data.nextPage;
                    $scope.pageNo = data.pageNo;
                    $scope.listStatus = true;
                    // $scope.removeState();

                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };
            $scope.getAdmissionList();


            // $scope.getAdmissionList = function (val) {
            //     if (angular.isUndefined(val)) {
            //         val = "";
            //     }
            //     $(".loader").css("display", "block");
            //     $http.post($scope.bshimServerURL  + '/getAdmissionList?searchText=' + val).then(function (response) {
            //         var data = response.data;
            //         $scope.admissionList= data;
            //         $scope.searchText = val;
            //
            //     }, function (error) {
            //         Notification.error({
            //             message: 'Something went wrong, please try again',
            //             positionX: 'center',
            //             delay: 2000
            //         });
            //     })
            // };
            // $scope.getAdmissionList();


            $scope.removeAdmissioDetails = function () {
                $scope.countryId = "";
                $scope.CountryNameText = "";
                $scope.branchId = null;
                $scope.levelId = null;
                $scope.classId = null;
                $scope.admindt = "";
                $scope.genderText = "";
                $scope.PassportNoNameText = "";
                $scope.birthcertificateText = "";
                $scope.age = "";
                $scope.phone = "";
                $scope.semester = null;
                $scope.stdname = "";
                $scope.nationality = null;
                $scope.dateofbirth = "";
                $scope.placeOfBirth = "";
                $scope.launguage = "";
                $scope.medicine = "";
                $scope.emailid="";
                $scope.corresponding = "";
                $scope.permanent = "";
                $scope.acdYearId=null;
                $scope.immunization = "";
                $scope.food = "";
                $scope.others = "";
                $scope.fatheroccupation="";
                $scope.asthma = "";
                $scope.epilepsy = "";
                $scope.illnessOthers = "";
                $scope.medicineState = "";
                $scope.immunizationState = "";
                $scope.foodState = "";
                $scope.othersState = "";
                $scope.asthmaState = "";
                $scope.epilepsyState = "";
                $scope.illnessOthersState = "";
                $scope.fatherName = "";
                $scope.fatherPassNo = "";
                $scope.fatherICNO = "";
                $scope.fatherFax = "";
                $scope.fathernationality = "";
                $scope.fatherDOB = "";
                $scope.fathereducation = "";
                $scope.fatherincome = "";
                $scope.fatherEmployer = "";
                $scope.fatherBusiness = "";
                $scope.fatherTelNo = "";
                $scope.fatherMobileNo = "";
                $scope.fatherEmail = "";
                $scope.motherPassNo = "";
                $scope.motherName = "";
                $scope.motherICNO = "";
                $scope.motherFax = "";
                $scope.mothernationality = "";
                $scope.motherDOB = "";
                $scope.motheroccupation = "";
                $scope.mothereducation = "";
                $scope.motherincome = "";
                $scope.motherEmployer = "";
                $scope.motherBusiness = "";
                $scope.motherTelNo = "";
                $scope.motherMobileNo = "";
                $scope.motherEmail = "";
                $scope.guardianName = "";
                $scope.guardiannationality = "";
                $scope.guardianRelation = "";
                $scope.guardianPassNo = "";
                $scope.guardianoccupation = "";
                $scope.guardianEmployer = "";
                $scope.guardianICNO = "";
                $scope.guardianMobileNo = "";
                $scope.guardianTelNo = "";
                $scope.previousSchoolsDetails=[];
                $scope.sbillingDetailsList=[];
            };
            $scope.imageUpload = function (event) {
                var files = event.target.files;
                var file = files[0];
                var srcString;
                var imageCompressor = new ImageCompressor;
                var compressorSettings = {
                    toWidth: 200,
                    toHeight: 200,
                    mimeType: 'image/png',
                    mode: 'strict',
                    quality: 1,
                    grayScale: false,
                    sepia: false,
                    threshold: false,
                    speed: 'low'
                };
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        binaryString = readerEvt.target.result;
                        $('.image-preview').attr('src', binaryString);
                    };
                    reader.readAsDataURL(file);
                    reader.onloadend = function () {
                        srcString = $('.image-preview').attr("src");
                        imageCompressor.run(srcString, compressorSettings, proceedCompressedImage);
                    };
                }
            };
            function proceedCompressedImage(compressedSrc) {
                $('#image-preview').attr('src', compressedSrc);
                $scope.fileToUpload = compressedSrc;
            }

            $scope.imageUpload1 = function (event1) {
                var files = event1.target.files;
                var file = files[0];
                var srcString;
                var imageCompressor = new ImageCompressor;
                var compressorSettings = {
                    toWidth: 200,
                    toHeight: 200,
                    mimeType: 'image/png',
                    mode: 'strict',
                    quality: 1,
                    grayScale: false,
                    sepia: false,
                    threshold: false,
                    speed: 'low'
                };
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        binaryString = readerEvt.target.result;
                        $('.image-preview1').attr('src', binaryString);
                    };
                    reader.readAsDataURL(file);
                    reader.onloadend = function () {
                        srcString = $('.image-preview1').attr("src");
                        imageCompressor.run(srcString, compressorSettings, proceedCompressedImage1);
                    };
                }
            };
            $scope.imageUpload2 = function (event2) {
                var files = event2.target.files;
                var file = files[0];
                var srcString;
                var imageCompressor = new ImageCompressor;
                var compressorSettings = {
                    toWidth: 200,
                    toHeight: 200,
                    mimeType: 'image/png',
                    mode: 'strict',
                    quality: 1,
                    grayScale: false,
                    sepia: false,
                    threshold: false,
                    speed: 'low'
                };
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        binaryString = readerEvt.target.result;
                        $('.image-preview2').attr('src', binaryString);
                    };
                    reader.readAsDataURL(file);
                    reader.onloadend = function () {
                        srcString = $('.image-preview2').attr("src");
                        imageCompressor.run(srcString, compressorSettings, proceedCompressedImage2);
                    };
                }
            };
            $scope.imageUpload3 = function (event3) {
                var files = event3.target.files;
                var file = files[0];
                var srcString;
                var imageCompressor = new ImageCompressor;
                var compressorSettings = {
                    toWidth: 200,
                    toHeight: 200,
                    mimeType: 'image/png',
                    mode: 'strict',
                    quality: 1,
                    grayScale: false,
                    sepia: false,
                    threshold: false,
                    speed: 'low'
                };
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        binaryString = readerEvt.target.result;
                        $('.image-preview3').attr('src', binaryString);
                    };
                    reader.readAsDataURL(file);
                    reader.onloadend = function () {
                        srcString = $('.image-preview3').attr("src");
                        imageCompressor.run(srcString, compressorSettings, proceedCompressedImage3);
                    };
                }
            };

            $scope.imageUpload7 = function (event7) {
                var files = event7.target.files;
                var file = files[0];
                var srcString;
                var imageCompressor = new ImageCompressor;
                var compressorSettings = {
                    toWidth: 200,
                    toHeight: 200,
                    mimeType: 'image/png',
                    mode: 'strict',
                    quality: 1,
                    grayScale: false,
                    sepia: false,
                    threshold: false,
                    speed: 'low'
                };
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        binaryString = readerEvt.target.result;
                        $('.image-preview7').attr('src', binaryString);
                    };
                    reader.readAsDataURL(file);
                    reader.onloadend = function () {
                        srcString = $('.image-preview7').attr("src");
                        imageCompressor.run(srcString, compressorSettings, proceedCompressedImage7);
                    };
                }
            };


            $scope.imageUpload4 = function (event4) {
                var files = event4.target.files;
                var file = files[0];
                var srcString;
                var imageCompressor = new ImageCompressor;
                var compressorSettings = {
                    toWidth: 200,
                    toHeight: 200,
                    mimeType: 'image/png',
                    mode: 'strict',
                    quality: 1,
                    grayScale: false,
                    sepia: false,
                    threshold: false,
                    speed: 'low'
                };
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        binaryString = readerEvt.target.result;
                        $('.image-preview4').attr('src', binaryString);
                    };
                    reader.readAsDataURL(file);
                    reader.onloadend = function () {
                        srcString = $('.image-preview4').attr("src");
                        imageCompressor.run(srcString, compressorSettings, proceedCompressedImage4);
                    };
                }
            };
            $scope.imageUpload5 = function (event5) {
                var files = event5.target.files;
                var file = files[0];
                var srcString;
                var imageCompressor = new ImageCompressor;
                var compressorSettings = {
                    toWidth: 200,
                    toHeight: 200,
                    mimeType: 'image/png',
                    mode: 'strict',
                    quality: 1,
                    grayScale: false,
                    sepia: false,
                    threshold: false,
                    speed: 'low'
                };
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        binaryString = readerEvt.target.result;
                        $('.image-preview5').attr('src', binaryString);
                    };
                    reader.readAsDataURL(file);
                    reader.onloadend = function () {
                        srcString = $('.image-preview5').attr("src");
                        imageCompressor.run(srcString, compressorSettings, proceedCompressedImage5);
                    };
                }
            };
            $scope.imageUpload6 = function (event6) {
                var files = event6.target.files;
                var file = files[0];
                var srcString;
                var imageCompressor = new ImageCompressor;
                var compressorSettings = {
                    toWidth: 200,
                    toHeight: 200,
                    mimeType: 'image/png',
                    mode: 'strict',
                    quality: 1,
                    grayScale: false,
                    sepia: false,
                    threshold: false,
                    speed: 'low'
                };
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        binaryString = readerEvt.target.result;
                        $('.image-preview6').attr('src', binaryString);
                    };
                    reader.readAsDataURL(file);
                    reader.onloadend = function () {
                        srcString = $('.image-preview6').attr("src");
                        imageCompressor.run(srcString, compressorSettings, proceedCompressedImage6);
                    };
                }
            };



            function proceedCompressedImage1(compressedSrc) {
                $('#image-preview1').attr('src', compressedSrc);
                $scope.fileToUpload1 = compressedSrc;
            }

            function proceedCompressedImage2(compressedSrc) {
                $('#image-preview2').attr('src', compressedSrc);
                $scope.fileToUpload2 = compressedSrc;
            }

            function proceedCompressedImage3(compressedSrc) {
                $('#image-preview3').attr('src', compressedSrc);
                $scope.fileToUpload3 = compressedSrc;
            }

            function proceedCompressedImage4(compressedSrc) {
                $('#image-preview4').attr('src', compressedSrc);
                $scope.fileToUpload4 = compressedSrc;
            }

            function proceedCompressedImage5(compressedSrc) {
                $('#image-preview5').attr('src', compressedSrc);
                $scope.fileToUpload5 = compressedSrc;
            }

            function proceedCompressedImage6(compressedSrc) {
                $('#image-preview6').attr('src', compressedSrc);
                $scope.fileToUpload6 = compressedSrc;
            }



        }
    }


});
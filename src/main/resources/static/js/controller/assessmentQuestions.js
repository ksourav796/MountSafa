app.controller('assessmentQuestionsController',
    function ($scope, $timeout, $http, $location, $filter, Notification) {
        $scope.bshimServerURL = "/bs";
        $scope.operation = 'Create';
        $scope.inactiveStatus = "Active";
        $scope.ButtonStatus = "InActive";
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.clicked = false;

        // $scope.questionList=[];
        $scope.removeaaa = function ($index) {
          $scope.questionList.splice($index,1);
        };

        $scope.addAssessmentQuestions = function () {

            $scope.LevelName=null;
            $scope.classId=null;
            $scope.subjectId=null;
            $scope.chapter=null;
            $scope.topic=null;
            $scope.semesterId=null;
            $scope.term=null;
            $scope.academicyear=null;
            $scope.questionList=[];
            $scope.classesList=[];
            $scope.subjectList=[];
            $scope.chapterList=[];
            $scope.topicLists=[];
            $scope.termList=[];
            $("#add_new_assessment_Questions_modal").modal('show');
        };
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
        $scope.classes = function(level){
            var url = "/bs/getClassLevel?levelId=" + level;
            $http.post(url).then(function (response) {
                var data = response.data;
                $scope.classesList = angular.copy(data);

            })
        }


        $scope.inactivegrade = function () {
            if ($scope.clicked == false) {
                $scope.inactiveStatus = "InActive";
                $scope.ButtonStatus = "Active";
                var page = "Page";
            }
            else {
                $scope.inactiveStatus = "Active";
                $scope.ButtonStatus = "InActive";
                var page = "";
            }
            $scope.clicked = !$scope.clicked;
            $scope.getPaginatedAssessQuesList();

        };
        $scope.getPaginatedAssessQuesList = function (page) {
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
            $http.post($scope.bshimServerURL + "/getpaginatedAssessQues?&searchText=" + $scope.searchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                var i = 0;
                $scope.assessmentList = data.list;
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
        $scope.getPaginatedAssessQuesList();

        $scope.compSubComp = function (component) {
            component.subComponent=null;
            var url = "/bs/getSubCompBasedOnComp?componentId=" + component.componentNm;
            $http.post(url).then(function (response) {
                var data = response.data;
                component.subComponentList = angular.copy(data);

            })

        }

        $scope.getComponentList = function (val) {
            if (angular.isUndefined(val)) {
                val = "";
            }
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL + '/getComponentList?searchText=' + val).then(function (response) {
                var data = response.data;
                $scope.componentList = data;
                $scope.searchText = val;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };

        $scope.getComponentList();
        $scope.getchapterListBasedOnSubject = function (subjectId) {
            $http.post($scope.bshimServerURL +"/getChapterSubject?subjectId="+subjectId).then(function (response) {
                var data = response.data;
                $scope.chapterList = data;

            })
        };
        $scope.getTermListBasedOnSemester = function (semesterId) {
            $http.post($scope.bshimServerURL +"/getTermListbySemester?semesterId="+semesterId).then(function (response) {
                var data = response.data;
                $scope.termList = data;

            })
        };

        $scope.getTopicList = function (chapterId) {
            $http.post($scope.bshimServerURL + '/getTopicListbyChapter?chapterId=' + chapterId).then(function (response) {
                var data = response.data;
                $scope.topicLists = data;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };

        // $scope.questionsList=[];
        // $scope.addNew = function(){
        //     if($scope.questionsList==""){
        //         $scope.questionsList = [];
        //     }
        //     $scope.questionsList.push({
        //         question1:''
        //
        //     });
        // };

        $scope.questionList=[];
        $scope.addNewQuestions = function () {
            $scope.questionList.push({
                question1:'',
                remarks:'',
                comments:'',
                recommendation:'',
                maxmarks:'',
                questiontype:'',
                componentNm:null,
                subComponent:null,
                description:''
            })
        }

        $scope.remove=function(){
            $scope.coursedetails.pop();
        };

        $scope.semesterList = function () {
            $http.post($scope.bshimServerURL +"/getSemesterList").then(function (response) {
                var data = response.data;
                $scope.semesterlists = data;
            })
        }
        $scope.semesterList();
        $scope.subjectclass = function(className){
            var url = "/bs/getSubjectClass?classesId=" + className;
            $http.post(url).then(function (response) {
                var data = response.data;
                $scope.subjectList = angular.copy(data);

                $scope.subjectss=[];
                angular.forEach($scope.subjectList,function (val,key) {
                    if(val.status=="Active"){
                        $scope.subjectss.push(val);
                    }

                })
            })
        }

        $scope.rubicsList=[];
        $scope.getList = function (object) {
            $scope.obj = object;
                $scope.rubicsList.push({
                    question: '',
                    answer: '',
                    remarks: '',
                    comments: '',
                    recommendation: ''
                })
            object.rubicsList = $scope.rubicsList;
        }

        $scope.multipleChoiceList=[];
        $scope.getmultipleList = function (object) {
            $scope.obj = object;
            // for (var i=0;i<data;i++){
                $scope.multipleChoiceList.push({
                    question: '',
                    answer: '',
                    remarks: '',
                    comments: '',
                    recommendation: ''
                })
            // }
            object.multipleChoiceList= $scope.multipleChoiceList;
        };
        $scope.removecross = function(index){
            $scope.rubicsList.splice(index,1);
        };
        $scope.removemultiplechoice = function(index){
            $scope.multipleChoiceList.splice(index,1);
        };
        
        $scope.editAssessmentQuestions = function (assessment) {
            $http.post($scope.bshimServerURL+"/editAssessmentQuestions?id="+assessment.assessmentQuestionsId).then(function (response) {
                var data = response.data;
                $scope.assessmentQuestionsId = data.assessmentQuestionsId;
                $scope.classes(data.levelId);
                $scope.LevelName = data.levelId;
                $scope.subjectclass(data.classId);
                $scope.classId = data.classId;
                $scope.getchapterListBasedOnSubject(data.subjectId);
                $scope.subjectId = data.subjectId;
                $scope.getTopicList(data.chapterId);
                $scope.chapter = data.chapterId;
                $scope.topic = data.topicId;
                $scope.semesterId = data.semesterId;
                $scope.getTermListBasedOnSemester(data.semesterId);
                $scope.term = data.termId;
                $scope.academicyear = data.academicyear;
                $scope.questionList=[];
                angular.forEach(data.assessmentQuestionDetailsPojos,function (val,key) {
                    val.multipleChoiceList=angular.fromJson(val.multipleChoiceList);
                    val.rubicsList=angular.fromJson(val.rubicsList);
                    val.subComponent = val.subComponent;
                    val.componentNm = val.componentNm;
                    val.questionType = val.questionType;
                    val.answer = val.answer;
                    val.maxMarks = val.maxMarks;
                    val.question = val.question;
                    val.remarks = val.remarks;
                    val.comments = val.comments;
                    val.recommendation = val.recommendation;
                    $scope.questionList.push(val)
                });

                $("#add_new_assessment_Questions_modal").modal('show');
            });
        }
        
        $scope.InstrumentList = function () {
            $http.post($scope.bshimServerURL+"/getInstrumentList").then(function (response) {
                var data = response.data;
                $scope.instrumentLists= data;
            })
        };
        $scope.InstrumentList();

        // $scope.assessmentDetailsList = function () {
        //     $http.post($scope.bshimServerURL+"/getAssessmentQuestionsList").then(function (response) {
        //         var data = response.data;
        //         $scope.assessmentList = data;
        //     })
        // }
        // $scope.assessmentDetailsList();
        $scope.saveAssessmentQuestions = function () {
            angular.forEach($scope.questionList,function (val,key) {
                val.multipleChoiceList=angular.toJson(val.multipleChoiceList);
                val.rubicsList=angular.toJson(val.rubicsList);
            })
            var details;
            details={
                assessmentQuestionsId:$scope.assessmentQuestionsId,
                levelId: $scope.LevelName,
                classId: $scope.classId,
                subjectId: $scope.subjectId,
                chapterId: $scope.chapter,
                topicId: $scope.topic,
                semesterId: $scope.semesterId,
                termId: $scope.term,
                academicyear: $scope.academicyear,
                assessmentQuestionDetailsPojos:$scope.questionList

            };
            $http.post($scope.bshimServerURL +"/saveAssessmentQuestions",angular.toJson(details)).then(function (response) {
                var data = response.data;
                if (data === "") {
                    $scope.isDisabled = false;
                    Notification.error({
                        message: 'Assessment Question  Already Created',
                        positionX: 'center',
                        delay: 2000
                    });
                }
                else {
                    $scope.getPaginatedAssessQuesList();
                    $("#add_new_assessment_Questions_modal").modal('hide');
                    Notification.success({
                        message: 'Assessment Question  Created  successfully',
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
                $scope.isDisabled = false;

            });
            // };
        }
        $scope.getAcademicYearList = function () {
            $(".loader").css("display", "block");
            $http.post($scope.bshimServerURL  + '/getAcademicYearList').then(function (response) {
                var data = response.data;
                $scope.academicYearList= data.object;

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })

        };
        $scope.getAcademicYearList();
        $scope.deleteAssessment = function (data) {
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
                        $http.post($scope.bshimServerURL +"/deleteAssessment?id="+data.assessmentQuestionsId).then(function (response, status, headers, config) {
                            var data = response.data;
                            $scope.getPaginatedAssessQuesList();
                            if(data==""){
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
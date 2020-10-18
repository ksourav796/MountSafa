app.controller('attendanceReportController',
    function ($scope, $timeout, $http, $location, $filter, Notification) {
        $scope.bshimServerURL = "/bs";
        $scope.CountryNameText = "";
        $scope.StatusText = "";
        $scope.operation = 'Create';
        $scope.inactiveStatus = "Active";
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

        $scope.GetMonthName=function(monthNumber) {
            var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
            return months[monthNumber - 1];
        };
        $scope.getDaysInMonth = function(month,year) {
            // Here January is 1 based
            //Day 0 is the last day in the previous month
            return new Date(year, month, 0).getDate();
// Here January is 0 based
// return new Date(year, month+1, 0).getDate();
        };

        $scope.myDate=function(day,month) {
            var d = new Date();
            var n = d.getFullYear();

            if(day<10){
                day="0"+day
            }


            if(month<10){
                month="0"+month
            }
            var date=n+"-"+month+"-"+day;
            var a = new Date(date);

            var days = new Array(7);
            days[0] = "Sunday";
            days[1] = "Monday";
            days[2] = "Tuesday";
            days[3] = "Wednesday";
            days[4] = "Thursday";
            days[5] = "Friday";
            days[6] = "Saturday";
            var r = days[a.getDay()];
            return r;

        }
        $scope.getSearch=function(studentGrade,classes,month){
            var month=$("#month").val();
            if (angular.isUndefined(studentGrade)) {
                studentGrade = "";
            }
            if (angular.isUndefined(classes)) {
                classes = "";
            }
            if (angular.isUndefined(month)) {
                month = "";
            }
            $http.post($scope.bshimServerURL + "/getSearch?studentGrade=" + studentGrade+'&classes=' + classes+'&month=' + month).then(function (response) {
                var data = response.data;
                $scope.reportList = data;

                var d = new Date();
                var n = d.getFullYear();

                var days= $scope.getDaysInMonth(month,n);
                var monthname=$scope.GetMonthName(month);

                var html='<table class="display table table-hover dataTable no-footer" style=" border:none; width: 100%;">' +
                    '<tbody><tr>'+
                    '<td style="border:none; overflow: auto;">' +
                    '<table class="display table table-hover dataTable no-footer" style="border:none; border:none; overflow: auto"><tbody><tr style="border-top: 1px solid black !important;"><td colspan="2">Month : '+monthname+'</td><td>G</td>';

                for(var i=1;i<=days;i++){

                    html+='<td>'+i+'</td>';

                }

                html+='<td>T.A</td><td>T.W.D</td></tr><tr><td colspan="2" style="font-size: 10px; padding: 12px 2px;">&nbsp;</td><td>&nbsp;</td>';
                for(var i=1;i<=days;i++){
                    var daysName=$scope.myDate(i,month);
                    daysName=daysName.substr(0,2);
                    html+='<td>'+daysName+'</td>';

                }



                html+='<td>&nbsp;</td><td>&nbsp;</td></tr>';
                for(var i=0;i<data.length;i++){
                    html+='<tr><td colspan="2" style="font-size: 10px; padding: 12px 2px;">'+data[i]["Student"]+'</td>'+
                        '<td>'+data[i]["Gender"].substr(0,1).toUpperCase()+'</td>';
                    var checklist=data[i]["List"];
                    var absent=0;
                    var present=0;
                    for(var j=1;j<=days;j++){
                        var cdate=j;

                        var currentdate=n+"-"+month+"-"+cdate;
                        var flag=0;
                        for(var k=0;k<checklist.length;k++){

                            var dbdate=checklist[k]['Date'];
                            var status=checklist[k]['attendance'];

                            var current_datetime = new Date(dbdate);
                            var formatted_date = current_datetime.getFullYear() + "-" + (current_datetime.getMonth() + 1) + "-" + current_datetime.getDate();
                            if(formatted_date==currentdate){

                                if(status.substr(0,1).toUpperCase()=='A'){
                                    absent++;

                                }else{
                                    present++;
                                }

                                html+='<td>'+status.substr(0,1).toUpperCase()+'</td>';
                                flag++;
                            }
                        }

                        if(flag==0){

                            html+='<td>&nbsp;</td>';

                        }
                    }

                    var totaldays=parseInt(absent)+parseInt(present);
                    html+='<td>'+absent+'</td><td>'+totaldays+'</td></tr>';

                }

                html+='</tbody></table>';

                $("#attendencereport").html(html);
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
            $(".loader").css("display", "block");
            $("#submit").text("Search");
        };


        $scope.addReport = function () {
            $scope.monthsList = [];
            $scope.monthsList.push({
                id: 1,
                name: "january"

            }, {
                id: 2,
                name: "Febraury"
            }, {
                id: 3,
                name: "March"
            }, {
                id: 4,
                name: "April"
            }, {
                id: 5,
                name: "May"
            }, {
                id: 6,
                name: "June"
            }, {
                id: 7,
                name: "July"
            }, {
                id: 8,
                name: "August"
            }, {
                id: 9,
                name: "September"
            }, {
                id: 10,
                name: "October"
            }, {
                id: 11,
                name: "November"
            }, {
                id: 12,
                name: "December"
            });
            var option='<option>SELECT</option>';
for(var i=0;i<$scope.monthsList.length;i++){
    option+='<option  value="'+$scope.monthsList[i]["id"]+'">'+$scope.monthsList[i]["name"]+'</option>'
}

$("#month").html(option);
            $("#add_new_student_Report_modal").modal('show');
        }
        $scope.addReport();
        $scope.getMonth = function (month) {
            var date = new Date();
            var i=$scope.getDaysInMonth(month,date.getFullYear());
            $scope.DaysList=[];
            for (var j=0;j<i;j++){
                $scope.DaysList.push(j+1);
            }
            $scope.fromDate=$scope.DaysList[0]+"/"+(parseInt(month)) +"/"+date.getFullYear();
            $scope.toDate = $scope.DaysList[$scope.DaysList.length-1] +"/"+parseInt(month) +"/"+date.getFullYear();
            $scope.getListaa();
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

        $scope.getListaa = function () {
            $http.post($scope.bshimServerURL + "/getList?fromDate="+$scope.fromDate +"&toDate="+$scope.toDate).then(function (response) {
                var data = response.data;
                $scope.getList =data;
            })
        }

        $scope.getDaysInMonth = function(month,year) {
            return new Date(year, month, 0).getDate();
        };



    });
package com.hyva.bsfms.bs.bsservice;

import com.google.gson.Gson;
import com.hyva.bsfms.bs.Quartz.TenantContext;
import com.hyva.bsfms.bs.bsentities.*;
import com.hyva.bsfms.bs.bsentities.Class;
import com.hyva.bsfms.bs.bsmapper.*;
import com.hyva.bsfms.bs.bspojo.*;
import com.hyva.bsfms.bs.bsrespositories.*;
import com.hyva.bsfms.bs.bsutil.ObjectMapperUtils;
import com.hyva.bsfms.pusher.PusherService;
import com.hyva.bsfms.pusher.pusherMapper.MasterMapper;
import com.hyva.bsfms.pusher.pusherMapper.TransactionMapper;
import com.hyva.bsfms.pusher.pusherPojo.MasterPojo;
import com.hyva.bsfms.pusher.pusherPojo.TransactionPojo;
import com.hyva.bsfms.util.FileSystemOperations;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.lowagie.text.Font;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.client.RestTemplate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;


@Component
public class BsUserService {

    @Autowired
    BsUserRepository bsUserRepository;
    @Autowired
    TimeTableRepository timeTableRepository;
    @Autowired
    GradingMasterRepository gradingMasterRepository;
    @Autowired
    AssesmentSubmissionRepository assesmentSubmissionRepository;
    @Autowired
    AssesmentCreatorRepository assesmentCreatorRepository;
    @Autowired
    AssesmentSubmissionDetailsRepository assesmentSubmissionDetailsRepository;
    @Autowired
    AssesmentSubmissionDetailsExcelRepository assesmentSubmissionDetailsExcelRepository;
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    BsVisitorEntryRepository bsVisitorEntryRepository;
    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    BsBankDetailsRepository bsBankDetailsRepository;
    @Autowired
    BsAttendanceMgtRepository bsAttendanceMgtRepository;
    @Autowired
    BsstudentAttendanceRepository bsstudentAttendanceRepository;
    @Autowired
    BsPayrollRepository bsPayrollRepository;
    @Autowired
    BsEarningsRepository bsEarningsRepository;
    @Autowired
    BsSalaryConfigRepository bsSalaryConfigRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    BsDeductionRepository bsDeductionRepository;
    @Autowired
    BsInterviewRepository bsInterviewRepository;
    @Autowired
    BsEmployeeRepository bsEmployeeRepository;
    @Autowired
    BsSuppliersRepository bsSuppliersRepository;
    @Autowired
    BsRemaindersRepository bsRemaindersRepository;
    @Autowired
    BsSemesterRepository bsSemesterRepository;
    @Autowired
    BsClassRepository bsClassRepository;
    @Autowired
    BsDesignationRepository bsDesignationRepository;
    @Autowired
    BsStudentRepository bsStudentRepository;
    @Autowired
    BsHrApplicRepository bsHrApplicRepository;
    @Autowired
    BsGrademasterRepository bsGrademasterRepository;
    @Autowired
    LeaveFormRepository leaveFormRepository;
    @Autowired
    BsSchoolBranchDetailsRepository bsSchoolBranchDetailsRepository;
    @Autowired
    BsAcademicYearMasterRepository bsAcademicYearMasterRepository;
    @Autowired
    BsFeeTypeMasterRepository bsFeeTypeMasterRepository;
    @Autowired
    BsMailRepository bsMailRepository;
    @Autowired
    EmployeesarRepository employeesarRepository;
    @Autowired
    AssessorRepository assessorRepository;
    @Autowired
    BsSchedulerRepository bsSchedulerRepository;
    @Autowired
    BsStudentFeeRepository bsStudentFeeRepository;
    @Autowired
    BsFeeReceiptRepository bsFeeReceiptRepository;
    @Autowired
    BsStudentFeeDetailsRepository bsStudentFeeDetailsRepository;
    @Autowired
    BsFeeReceiptDetailsRepository bsFeeReceiptDetailsRepository;
    @Autowired
    PusherService pusherService;
    @Autowired
    BsInstallmentsRepository bsInstallmentsRepository;
    @Autowired
    SchedulerService schedulerService;
    @Autowired
    CartMasterRepository CartMasterRepository;
    @Autowired
    BsDiscountTypeRepository discountTypeRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    BsPositionRepository positionRepository;
    @Autowired
    BsInstrumentsRepository instrumentsRepository;
    @Autowired
    BsAssessmentTypeRepository assessmentTypeRepository;
    @Autowired
    BsSubComponentRepository subComponentRepository;
    @Autowired
    BsCustomApproverRepository customApproverRepository;
    @Autowired
    AccountTypeRepository accountTypeRepository;
    @Autowired
    AccountGroupRepository accountGroupRepository;
    @Autowired
    AccountMasterRepository accountMasterRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CheckRepository checkRepository;
    @Autowired
    BranchesRepository branchesRepository;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    ApprovalRepository approvalRepository;
    @Autowired
    ResourceCategoryRepository resourceCategoryRepository;
    @Autowired
    BsTrainingModuleRepository trainingModuleRepository;
    @Autowired
    BsDoctorPanelRepository doctorPanelRepository;
    @Autowired
    BsEmailTemplateRepository emailTemplateRepository;
    @Autowired
    BsNotificationRepository notificationRepository;
    @Autowired
    BsQualificationRepository qualificationRepository;
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    HolidayRepositories holidayRepositories;
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    WeekdayRepository weekdayRepository;
    @Autowired
    LeaveRepository leaveRepository;
    @Autowired
    EnquiryRepository enquiryRepository;
    @Autowired
    AgegroupRepository agegroupRepository;
    @Autowired
    TermRepository termRepository;
    @Autowired
    BsAssessmentQuestionsRepository bsAssessmentQuestionsRepository;
    @Autowired
    BsAssessmentQuestionDetailsRepository bsAssessmentQuestionDetailsRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    SubjectCategoryRepository subjectCategoryRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    LessonPlanRepository lessonPlanRepository;
    @Autowired
    RegisterFeeRepository registerFeeRepository;
    @Autowired
    SowRepository sowRepository;
    @Autowired
    PosFormSetupRepository posFormSetupRepository;
    @Autowired
    PosPaymentMethodRepository posPaymentMethodRepository;
    @Autowired
    BsTeachingObservationRepository teachingObservationRepository;
    @Autowired
    TopicDetailsRepository topicDetailsRepository;
    @Autowired
    BsTeacherClearanceRepository teacherClearanceRepository;
    @Autowired
    PosBankRepository posBankRepository;
    @Autowired
    GLTransactionRepository glTransactionRepository;
    @Autowired
    BsSchoolBranchDetailsRepository schoolBranchDetailsRepository;
    @Autowired
    StudentAssesmentMarksRepository studentAssesmentMarksRepository;
    @Autowired
    StudentAssesmentMarksDetailsRepository studentAssesmentMarksDetailsRepository;
    @Autowired
            PermissionRepository permissionRepository;
    @Autowired
    LessonPlanDetailsRepository lessonPlanDetailsRepository;
    @Autowired
    PeriodsMasterRepository periodsRepository;
    @Autowired
            FacilityDetailsRepository facilityDetailsRepository;
    @Autowired
            IHESLessonPlanRepository ihesLessonPlanRepository;
 @Autowired
            GeneralSettingsRepository generalSettingsRepository;
 @Autowired
            MarksSubmissionRepository marksSubmissionRepository;
 @Autowired
            MarksSubmissionDetailsRepository marksSubmissionDetailsRepository;



    int paginatedConstants = 10;

    public Settings SaveGeneralSettings(SettingsPojo details) throws Exception {
        Settings general = null;
        byte byteArray[];
        String fileName = FileSystemOperations.getImagesDirItem() + File.separator + details.getCompanyName() + ".png";
        List<Settings> list = generalSettingsRepository.findAll();
        if (list.size() > 0) {
            details.setId(list.get(0).getId());
        }
        if (!StringUtils.isEmpty(details.getAttachFile())&&details.getAttachFile().contains("data:image")) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(details.getAttachFile().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                details.setAttachFile(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(details.getId()!=null) {
            Settings settings = generalSettingsRepository.findById(details.getId());
            Path sourceDirectory = Paths.get(settings.getAttachFile());
            Path targetDirectory = Paths.get(fileName);
//            Files.copy(sourceDirectory, targetDirectory);
            details.setAttachFile(settings.getAttachFile());
        }

        general = BsGeneralSettingsMapper.mapPojoToEntity(details);
        general.setAttachFile(details.getAttachFile());
        generalSettingsRepository.save(general);
        return general;
    }

    public Settings generalSettingsList() {
        Settings general = null;
        general = generalSettingsRepository.findById(Long.valueOf("1"));
        if(general!=null){
        if (!StringUtils.isEmpty(general.getAttachFile())) {
            if (!general.getAttachFile().equalsIgnoreCase("")) {
                String imageLocation = FileSystemOperations.getImagesDirItem() + File.separator + general.getCompanyName() + ".png";

                String fileDirectory = File.separator;
                if (fileDirectory.equals("\\"))//Windows OS
                    imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                else//Linux or MAC
                    imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                general.setAttachFile(imageLocation);
            }
        }
        }
        return general;
    }

    public void saveMailSchedule(MailSchedulerData mailSchedulerData) throws Exception {
        Mail mailServerPops = bsMailRepository.findOne(1L);
        mailSchedulerData.setFromMail(mailServerPops);
        String filename = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ReportPojo reportPojo = new ReportPojo();
        reportPojo.setFromDate(mailSchedulerData.getFromDate());
        reportPojo.setToDate(mailSchedulerData.getToDate());
        if (StringUtils.isNotEmpty(mailSchedulerData.getReportName()))
            switch (mailSchedulerData.getReportName()) {
                case "feeDue":
                    if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(mailSchedulerData.getReportType(), "application/pdf")) {
                        downloadFeeDueReportPdf(outputStream, reportPojo);
                        filename = "FeeDueReport.pdf";
                    } else {
                        downloadFeeDueReportExcel(outputStream, reportPojo);
                        filename = "FeeDueReport.xls";
                    }
                    break;
                case "feeCollect":
                    if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(mailSchedulerData.getReportType(), "application/pdf")) {
                        downloadFeeCollectedReportPdf(outputStream, reportPojo);
                        filename = "FeeCollectedReport.pdf";
                    } else {
                        downloadFeeCollectedReportExcel(outputStream, reportPojo);
                        filename = "FeeCollectedReport.xls";
                    }
                    break;
            }
        if (StringUtils.isEmpty(mailSchedulerData.getBody())) {
            mailSchedulerData.setBody("");
        }
        mailSchedulerData.setDbKeyword(TenantContext.getCurrentTenant());
        MailService.sendMailWithAttachment(mailSchedulerData.getFromMail(),
                mailSchedulerData.getToEmail(), "", mailSchedulerData.getSubject(),
                mailSchedulerData.getBody(), mailSchedulerData.getReportType(),
                outputStream.toByteArray(), filename);
    }

    public List<SchedulerData> getSchedulerList() {
        return bsSchedulerRepository.findAll();
    }

    public void deleteMailSchedulerDetails(String schedulerid) {
        bsSchedulerRepository.delete(Long.parseLong(schedulerid));
    }

    public User userValidate(BsUserPojo bsUserPojo) {
        User user = bsUserRepository.findByUserNameAndPasswordUserAndBranchCode(
                bsUserPojo.getUserName(), bsUserPojo.getPasswordUser(),bsUserPojo.getBranchCode());
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public Holiday saveHoliday(HolidayPojo holidayPojo) {
        Holiday holiday = null;
//        if (holidayPojo.getId() != null) {
//            holiday = holidayRepositories.findAllByHolidayNameAndIdNotIn(holidayPojo.getHolidayName(), holidayPojo.getId());
//        } else {
//            holiday = holidayRepositories.findAllByHolidayName(holidayPojo.getHolidayName());
//        }
            holiday = BsHolidayMapper.mapPojoToEntity(holidayPojo);
            holidayRepositories.save(holiday);
            return holiday;

//        else {
//            return null;
//        }
    }

    public Trainer saveTrainer(TrainerPojo trainerPojo) {
        Trainer trainer = null;
        if (trainerPojo.getId() != null) {
            trainer = trainerRepository.findAllByNameAndIdNotIn(trainerPojo.getName(), trainerPojo.getId());
        } else {
            trainer = trainerRepository.findAllByName(trainerPojo.getName());
        }
        if (trainer == null) {
            trainer = BsTrainerMapper.mapPojoToEntity(trainerPojo);
            trainerRepository.save(trainer);
            return trainer;
        } else {
            return null;
        }
    }

    ;

    public User saveUserDetails(BsUserPojo bsUserPojo) {
        User user = null;
        user = bsUserRepository.findByEmail(bsUserPojo.getEmail());
        if (user != null) {
            user = null;
        } else {
            user = BsUserMapper.mapPojoToEntity(bsUserPojo);
            bsUserRepository.save(user);
        }
        return user;
    }

    //call to save StudentTable
    public StudentPojo SaveStudentDetails(StudentPojo saveStudentDetails) throws JSONException, ParseException, IOException {
        Student student = null;
        List<SchoolBranchDetails> schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll();
        if (schoolBranchDetails.size() == 0) {
            SchoolBranchDetails branchDetails = new SchoolBranchDetails();
            branchDetails.setReceiptNo(0L);
            bsSchoolBranchDetailsRepository.save(branchDetails);
        }
        GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(saveStudentDetails.getGradeId());
        AcademicYearMaster acdobj = bsAcademicYearMasterRepository.findByAcdyrId(saveStudentDetails.getAcdYearId());
        saveStudentDetails.setAcademicYearMaster(acdobj);
        saveStudentDetails.setGradeMaster(grdmstrobj);
        List<Student> studList = (List<Student>) bsStudentRepository.findAll();
        int studId = 0;
        for (Student stud : studList) {
            studId = Math.toIntExact(stud.getStudentId());
        }
        if (saveStudentDetails.getStudentId() != null) {
            Student student2 = bsStudentRepository.findByStudentId(saveStudentDetails.getStudentId()).get(0);
            saveStudentDetails.setStudentProfileId(student2.getStudentProfileId());
            saveStudentDetails.setType("OldStudent");
        } else {
            String admissionNo = BsStudentMapper.generateStudentProfileNo(grdmstrobj.getGradeName(), acdobj.getFromDate(), studId);
            saveStudentDetails.setStudentProfileId(admissionNo);
            saveStudentDetails.setType("NewStudent");
        }
        student = BsStudentMapper.saveStudent(saveStudentDetails);


        bsStudentRepository.save(student);
        //          commented for time being
        Gson gson = new Gson();
        MasterPojo masterPojoStudent = MasterMapper.convetToMasterPojo1(student);
        masterPojoStudent.setMasterFlag("Student");
        masterPojoStudent.setSubscriptionType("RTR");
        masterPojoStudent.setStatus("Active");
        String JsonStudentString = gson.toJson(masterPojoStudent);
        CartMaster cartMaster = CartMasterRepository.findOne(1l);
        String cartID = "";
        if (cartMaster != null){
            cartID = cartMaster.getHiConnectCompanyRegNo();
        }
        pusherService.BroadCastMasterData(JsonStudentString, cartID, cartID, "AddCustomer", "AddMaster");

        if (saveStudentDetails.getFeeTypeMasterPojoList() != null) {
            StudentPojo saveStudentDetails1 = new StudentPojo();
            saveStudentDetails1.setStudentProfileId(saveStudentDetails.getStudentProfileId());
            saveStudentDetails1.setStudentName(saveStudentDetails.getStudentName());
            saveStudentDetails1.setFeeTypeMasterPojoList(saveStudentDetails.getFeeTypeMasterPojoList());
            Student student1 = SaveStudentfeee(saveStudentDetails1);
        }

        String JsonBrainyStudentString = gson.toJson(saveStudentDetails);
        User user = bsUserRepository.findOne(1l);
        String branchCode = "";
        if (!user.equals(null)) branchCode = user.getBranchCode();
        String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString, branchCode, branchCode, "AddStudent", "AddMaster");
        return saveStudentDetails;


    }

    //call to save StudentFeeTable
    public Student SaveStudentfeee(StudentPojo saveStudentDetails1) throws JSONException, ParseException, IOException {
        Student student = bsStudentRepository.findByStudentNameAndStudentProfileId(saveStudentDetails1.getStudentName(), saveStudentDetails1.getStudentProfileId());
        Mail mail = bsMailRepository.findOne(1L);
        Gson gson = new Gson();
        CartMaster cartMaster = CartMasterRepository.findOne(1l);
        String cartID = "";
        if (cartMaster != null) {
            cartID = cartMaster.getHiConnectCompanyRegNo();
        }
        StudentFeePojo studentFeeTypeMasterPojo = new StudentFeePojo();
        studentFeeTypeMasterPojo.setStudent(student);
        studentFeeTypeMasterPojo.setGradeMaster(student.getLevel());
        studentFeeTypeMasterPojo.setAcademicYearMaster(student.getAcademicYearMaster());
        studentFeeTypeMasterPojo.setStudentName(student.getStudentName());
        studentFeeTypeMasterPojo.setFeetypeList(saveStudentDetails1.getFeeTypeMasterPojoList());
        Double totalFee = 0.00, totalDiscount = 0.00;
        for (FeeTypeMasterPojo feelist : studentFeeTypeMasterPojo.getFeetypeList()) {
            if (feelist.isCheckBox() == true) {
                totalFee = totalFee + feelist.getFeeAmount();
                if (feelist.getDiscount() == null) {
                    feelist.setDiscount(0.00);
                }
                totalDiscount = totalDiscount + feelist.getDiscount();
            }
        }
        studentFeeTypeMasterPojo.setTotalFeeAmount(totalFee);
        studentFeeTypeMasterPojo.setTotalPayable(totalFee - totalDiscount);
        studentFeeTypeMasterPojo.setDiscount(totalDiscount);
        StudentFee studentFee = null;
        studentFee = bsStudentFeeRepository.findByStudent(student);
        if (studentFee != null) {
            Double remainingAmt = studentFeeTypeMasterPojo.getTotalPayable() - studentFee.getTotalPayable();
            if (student.getLevel() == studentFee.getLevel()) {
                studentFeeTypeMasterPojo.setGradeStatus("true");
            } else {
                studentFeeTypeMasterPojo.setGradeStatus("false");
            }
            studentFee.setLevel(student.getLevel());
            studentFee.setAcademicYearMaster(student.getAcademicYearMaster());
            studentFee.setTotalFeeAmount(studentFeeTypeMasterPojo.getTotalFeeAmount());
            studentFee.setTotalPayable(studentFeeTypeMasterPojo.getTotalPayable());
            studentFee.setDueAmount(studentFee.getDueAmount() + remainingAmt);
            studentFee.setStudentName(student.getStudentName());
            studentFee.setStudent(student);
        } else {
            studentFee = BsStudentFeeMapper.saveStudentFee(studentFeeTypeMasterPojo);
        }
        bsStudentFeeRepository.save(studentFee);
        studentFeeTypeMasterPojo.setStudentFee(studentFee);
        studentFeeTypeMasterPojo.setStudentId(student.getStudentId());
        List<StudentFeeDetails> studentFeedetails = null;
        studentFeedetails = saveStudentFeeDetails(studentFeeTypeMasterPojo);
        for (int i = 0; i < studentFeedetails.size(); i++) {
            studentFeeTypeMasterPojo.getFeetypeList().get(i).setPaidAmt(studentFeedetails.get(i).getPaidAmt());
            studentFeedetails.get(i).setFeetypemaster(bsFeeTypeMasterRepository.findByFeeTypeId(saveStudentDetails1.getFeeTypeMasterPojoList().get(i).getFeeTypeId()));
        }
        bsStudentFeeDetailsRepository.save(studentFeedetails);
        if (StringUtils.equalsIgnoreCase(studentFeeTypeMasterPojo.getGradeStatus(), "false")) {
            if (studentFee != null) {
                List<Installments> installments = bsInstallmentsRepository.findByStudentFee(studentFee);
                bsInstallmentsRepository.delete(installments);
            }
        }
        for (FeeTypeMasterPojo feeTypeMasterPojo : studentFeeTypeMasterPojo.getFeetypeList()) {
            if (feeTypeMasterPojo.getPaidAmt() == 0) {
                FeeTypeMaster feeTypeMaster = bsFeeTypeMasterRepository.findByFeeTypeId(feeTypeMasterPojo.getFeeTypeId());
                List<Installments> installment = bsInstallmentsRepository.findByStudentFeeAndFeeTypeMasterAndPaidAmtEquals(studentFee, feeTypeMaster, 0.00);
                bsInstallmentsRepository.delete(installment);
                for (InstallmentsPojo installmentsPojo : feeTypeMasterPojo.getInstallmentsPojosList()) {
                    Installments installments = new Installments();
                    installments.setFeeTypeName(feeTypeMasterPojo.getFeeTypeName());
                    installments.setInstallmentsAmount(installmentsPojo.getInstallmentsAmount());
                    installments.setDueDate(installmentsPojo.getDueDate());
                    installments.setStudentFee(studentFeeTypeMasterPojo.getStudentFee());
                    installments.setDiscountRemarks(installmentsPojo.getDiscountRemarks());
                    installments.setStatus("pending");
                    installments.setFeeTypeMaster(feeTypeMaster);
                    bsInstallmentsRepository.save(installments);
                }
            }
        }
        if (StringUtils.equalsIgnoreCase(student.getStudentStatus(), "Active")) {
            for (StudentFeeDetails studentFeeDetail : studentFeedetails) {
                if (studentFeeDetail.getCheckboxstatus() == true) {
                    List<Installments> installments = bsInstallmentsRepository.findByStudentFeeAndFeeTypeMaster(studentFee, studentFeeDetail.getFeetypemaster());
                    List<SchedulerData> schedulerDataList = bsSchedulerRepository.findAllByStudent(studentFee.getStudent().getStudentId().toString());
                    bsSchedulerRepository.delete(schedulerDataList);
                    for (Installments installments1 : installments) {
                        if (StringUtils.isNotEmpty(student.getFatherEmailId())) {
                            if (installments1.getDueDate().after(new Date())) {
                                if (installments1.getPaidAmt() < installments1.getInstallmentsAmount()) {
                                    for (int i = 1; i <= 3; i++) {
                                        SchedulerData schedulerData = new SchedulerData();
                                        schedulerData.setDatabaseKeyWord(TenantContext.getCurrentTenant());
                                        schedulerData.setReportName("DueRemainder" + bsSchedulerRepository.findAll().size());
                                        schedulerData.setScheduleType("Yearly");
                                        schedulerData.setStudent(student.getStudentId().toString());
                                        schedulerData.setInstallmentsId(installments1.getInstallmentsId().toString());
                                        schedulerData.setTime("10:00:00");
                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                                        c.setTime(sdf.parse(installments1.getDueDate().toString()));
                                        c.add(Calendar.DAY_OF_MONTH, -(i * 3));
                                        String newDate = sdf1.format(c.getTime());
                                        schedulerData.setDate(newDate);
                                        if (StringUtils.isNotEmpty(student.getMotherEmailId())) {
                                            schedulerData.setToEmailId(student.getFatherEmailId() + "," + student.getMotherEmailId());
                                        } else {
                                            schedulerData.setToEmailId(student.getFatherEmailId());
                                        }
                                        bsSchedulerRepository.save(schedulerData);
                                        MailSchedulerData mailSchedulerData = new MailSchedulerData();
                                        mailSchedulerData.setDbKeyword(schedulerData.getDatabaseKeyWord());
                                        mailSchedulerData.setToEmail(schedulerData.getToEmailId());
                                        mailSchedulerData.setScheduleTime(schedulerData.getTime());
                                        mailSchedulerData.setInstallmentsId(schedulerData.getInstallmentsId());
                                        mailSchedulerData.setScheduleType(schedulerData.getScheduleType());
                                        mailSchedulerData.setReportName(schedulerData.getReportName());
                                        mailSchedulerData.setScheduleDate(schedulerData.getDate());
                                        mailSchedulerData.setStudent(schedulerData.getStudent());
                                        mailSchedulerData.setFromMail(mail);
                                        mailSchedulerData.setReportType("Pdf");
                                        schedulerService.schedule(mailSchedulerData);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        StudentPojo saveStudentDetails = new StudentPojo();
        saveStudentDetails.setStudentProfileId(student.getStudentProfileId());
        saveStudentDetails.setStudentName(student.getStudentName());
        saveStudentDetails.setFeeTypeMasterPojoList(saveStudentDetails.getFeeTypeMasterPojoList());

        TransactionPojo transactionPojo = TransactionMapper.studentToTransaction(saveStudentDetails);
        transactionPojo.setAmtToBePaid(totalFee);
        transactionPojo.setDiscountAmount(totalDiscount);
        transactionPojo.setTotalCheckOutamt(totalFee);
        String JsonInString = gson.toJson(transactionPojo);
        String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "SIN", "Sales");
        String JsonBrainyStudentString = gson.toJson(saveStudentDetails);
        User user = bsUserRepository.findOne(1l);
        String branchCode = "";
        if (!user.equals(null)) branchCode = user.getBranchCode();
        String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString, branchCode, branchCode, "AddStudent", "AddMaster");
        return student;
    }


    public List<StudentFeeDetails> saveStudentFeeDetails(StudentFeePojo saveStudentDetails) {
        List<StudentFeeDetails> studentFeeDetails = new ArrayList<>();
        Student student = null;
        StudentFee studentfee = null;
        for (FeeTypeMasterPojo feeTypeMasterPojo : saveStudentDetails.getFeetypeList()) {
            StudentFeeDetails details = new StudentFeeDetails();
            if (saveStudentDetails.getStudentId() != null) {
                student = bsStudentRepository.findByStudentId(saveStudentDetails.getStudentId()).get(0);
                studentfee = bsStudentFeeRepository.findByStudent(student);
                saveStudentDetails.setStudentFee(studentfee);
                if (feeTypeMasterPojo.getStudentFeeDetailsId() != null) {
                    details = bsStudentFeeDetailsRepository.findOne(feeTypeMasterPojo.getStudentFeeDetailsId());
                }
                if (StringUtils.equalsIgnoreCase(saveStudentDetails.getGradeStatus(), "false")) {
                    if (studentfee != null) {
                        List<StudentFeeDetails> studentFeeDetails1 = bsStudentFeeDetailsRepository.findByStudentfee(studentfee);
                        bsStudentFeeDetailsRepository.delete(studentFeeDetails1);
                    }
                }
            }
            if (details == null) {
                details = new StudentFeeDetails();
            }
            if (details.getPaidAmt() == null) {
                details.setPaidAmt(0.00);
            }
            if (feeTypeMasterPojo.getInstallments() >= 1 && details.getPaidAmt() == 0) {
                details.setNoOfInstallments(feeTypeMasterPojo.getInstallments());
                details.setFeeTypeName(feeTypeMasterPojo.getFeeTypeName());
                details.setDiscountRemarks(feeTypeMasterPojo.getDiscountRemarks());
                details.setDiscountType(feeTypeMasterPojo.getDiscountType());
                details.setFeeTypeAmount(feeTypeMasterPojo.getFeeAmount());
                details.setCheckboxstatus(Boolean.valueOf(feeTypeMasterPojo.isCheckBox()));
                if (feeTypeMasterPojo.getDiscount() == null) {
                    Double discount = 0.0;
                    details.setInstallmentsAmount(feeTypeMasterPojo.getFeeAmount() - discount);
                    details.setPendingFee(feeTypeMasterPojo.getFeeAmount() - discount);
                    details.setDiscount(discount);
                    details.setPayable(feeTypeMasterPojo.getFeeAmount() - discount);
                } else {
                    details.setInstallmentsAmount(feeTypeMasterPojo.getFeeAmount() - feeTypeMasterPojo.getDiscount());
                    details.setPendingFee(feeTypeMasterPojo.getFeeAmount() - feeTypeMasterPojo.getDiscount());
                    details.setDiscount(feeTypeMasterPojo.getDiscount());
                    details.setPayable(feeTypeMasterPojo.getFeeAmount() - feeTypeMasterPojo.getDiscount());
                }
                details.setStatus("Pending");
            }
            details.setStudentfee(saveStudentDetails.getStudentFee());
            studentFeeDetails.add(details);
        }
        return studentFeeDetails;
    }

    public Boolean deleteStudentDetails(StudentPojo StudentDetails) {
        Student student = bsStudentRepository.findByStudentId(StudentDetails.getStudentId()).get(0);
        StudentFee studentFee = bsStudentFeeRepository.findByStudent(student);
        List<FeeReceipt> feeReceipt = bsFeeReceiptRepository.findByStudentFee(studentFee);
        if (feeReceipt.size() == 0) {
            bsStudentFeeDetailsRepository.delete(bsStudentFeeDetailsRepository.findByStudentfee(studentFee));
            bsInstallmentsRepository.delete(bsInstallmentsRepository.findByStudentFee(studentFee));
            bsStudentFeeRepository.delete(bsStudentFeeRepository.findByStudent(student));
            bsStudentRepository.delete(student);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteGradeDetails(GradeMasterPojo details) {
        GradeMaster gradeMaster = bsGrademasterRepository.findByGradeId(details.getGradeId());
        List<FeeTypeMaster> list = bsFeeTypeMasterRepository.findByLevel(gradeMaster);
        if (list.size() == 0) {
            bsGrademasterRepository.delete(details.getGradeId());
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteAcademicDetails(AcademicYearMasterPojo details) {
        AcademicYearMaster academicYearMaster = bsAcademicYearMasterRepository.findByAcdyrId(details.getAcdyrId());
        List<FeeTypeMaster> list = bsFeeTypeMasterRepository.findByAcdyrmaster(academicYearMaster);
        if (list.size() == 0) {
            bsAcademicYearMasterRepository.delete(details.getAcdyrId());
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteDiscountDetails(DiscountTypePojo details) {
        discountTypeRepository.delete(details.getDiscountId());
        return true;
    }

    public Boolean deleteFeeDetails(FeeTypeMasterPojo Details) {
        FeeTypeMaster feeTypeMaster = bsFeeTypeMasterRepository.findOne(Details.getFeeTypeId());
        List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByFeetypemaster(feeTypeMaster);
        if (studentFeeDetails.size() == 0) {
            bsFeeTypeMasterRepository.delete(feeTypeMaster);
            return true;
        } else {
            return false;
        }
    }


    public List<GradeMasterPojo> gradeMasterList() {
        List<GradeMaster> grade = new ArrayList<>();
        grade = bsGrademasterRepository.findByGradeStatus("Active");
        List<GradeMasterPojo> gdPojoList = ObjectMapperUtils.mapAll(grade, GradeMasterPojo.class);
        return gdPojoList;
    }


    public List<SchoolBranchDetailsPojo> schoolBranchDetailsList() {
        List<SchoolBranchDetails> branchDetailsList = new ArrayList<>();
        branchDetailsList =bsSchoolBranchDetailsRepository.findAll();
        List<SchoolBranchDetailsPojo> gdPojoList = ObjectMapperUtils.mapAll(branchDetailsList, SchoolBranchDetailsPojo.class);
        return gdPojoList;
    }

    public List<GradeMasterPojo> gradeList(Long academicID) {
        AcademicYearMaster academicYearMaster = bsAcademicYearMasterRepository.findByAcdyrId(academicID);
        List<FeeTypeMaster> feeTypeMasters = new ArrayList<>();
        feeTypeMasters = bsFeeTypeMasterRepository.findByAcdyrmaster(academicYearMaster);
        List<GradeMaster> grade1 = new ArrayList<>();
        Map<GradeMaster, List<FeeTypeMaster>> feeList =
                feeTypeMasters.parallelStream().collect(groupingBy(w -> w.getLevel()));
        for (Map.Entry m : feeList.entrySet()) {
            GradeMaster grade = (GradeMaster) m.getKey();
            if (StringUtils.equalsIgnoreCase(grade.getGradeStatus(), "Active")) {
                grade1.add(grade);
            }
        }
        List<GradeMasterPojo> gdPojoList = ObjectMapperUtils.mapAll(grade1, GradeMasterPojo.class);
        return gdPojoList;
    }

    public List<GradeMasterPojo> gradeMasterList2(String searchText, String checkboxForInActive, String userId) {
        List<GradeMaster> grade = new ArrayList<>();
        User user = bsUserRepository.findOne(Long.parseLong(userId));
        if (StringUtils.isBlank(searchText) && checkboxForInActive.equals("true")) {
            grade =bsGrademasterRepository.findAll();
        }
        if (StringUtils.isBlank(searchText) && checkboxForInActive.equals("false")) {
            if (user.getUseraccount_id() == 1) {
                grade = bsGrademasterRepository.findByGradeStatus("Active");
            } else {
                grade = bsGrademasterRepository.findByGradeStatusAndUserId("Active", user);
            }
        }
        if (searchText != "") {
            String status = "Active";
            grade = bsGrademasterRepository.findByGradeNameIsStartingWithAndGradeStatusAndUserId(searchText, status, user);
        }
        if (searchText != "" && checkboxForInActive.equals("true")) {
            grade = bsGrademasterRepository.findByGradeNameIsStartingWith(searchText);
        }
        List<GradeMasterPojo> gdPojoList = ObjectMapperUtils.mapAll(grade, GradeMasterPojo.class);
        return gdPojoList;
    }

    public List<GradeMasterPojo> gradeMasterListBasedOnInactive() {
        List<GradeMaster> grade = new ArrayList<>();
        grade = bsGrademasterRepository.findByGradeStatus("InActive");
        List<GradeMasterPojo> gdPojoList = ObjectMapperUtils.mapAll(grade, GradeMasterPojo.class);
        return gdPojoList;
    }

    public List<FeeTypeMasterPojo> feeTypeMasterList() {
        List<FeeTypeMaster> feemaster = new ArrayList<>();
        feemaster = bsFeeTypeMasterRepository.findByStatus("Active");
        List<FeeTypeMasterPojo> ftPojoList = ObjectMapperUtils.mapAll(feemaster, FeeTypeMasterPojo.class);
        return ftPojoList;
    }

    public List<FeeTypeMasterPojo> feeTypeMasterList2(String searchText, String checkboxForInActive) {
        List<FeeTypeMaster> feemaster = new ArrayList<>();
        GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeName(searchText);
        if (StringUtils.isBlank(searchText) && checkboxForInActive.equals("true")) {
            feemaster = bsFeeTypeMasterRepository.findAll();
        }
        if (StringUtils.isBlank(searchText) && checkboxForInActive.equals("false")) {
            feemaster = bsFeeTypeMasterRepository.findByStatus("Active");
        }
        if (searchText != "") {
            String status = "Active";
            if (grdmstrobj != null) {
                feemaster = bsFeeTypeMasterRepository.findByLevel(grdmstrobj);
            } else {
                feemaster = bsFeeTypeMasterRepository.findByFeeTypeNameIsStartingWithAndStatus(searchText, status);
            }
        }
        if (searchText != "" && checkboxForInActive.equals("true")) {
            if (grdmstrobj != null) {
                feemaster = bsFeeTypeMasterRepository.findByLevel(grdmstrobj);
            } else {
                feemaster = bsFeeTypeMasterRepository.findByFeeTypeNameIsStartingWith(searchText);
            }
        }

        List<FeeTypeMasterPojo> ftPojoList = ObjectMapperUtils.mapAll(feemaster, FeeTypeMasterPojo.class);
        for (FeeTypeMasterPojo feeTypeMasterPojo : ftPojoList) {
            feeTypeMasterPojo.setAcdId(feeTypeMasterPojo.getAcdyrmaster().getAcdyrId().intValue());
            feeTypeMasterPojo.setGradeId(feeTypeMasterPojo.getLevel().getGradeId().intValue());
        }
        return ftPojoList;
    }

    public List<FeeTypeMasterPojo> feeListOfAcademicAndGrade(Long academicId, Long gradeId) {
        List<FeeTypeMaster> feemaster = new ArrayList<>();
        AcademicYearMaster academicYearMaster = bsAcademicYearMasterRepository.findByAcdyrId(academicId);
        GradeMaster gradeMaster = bsGrademasterRepository.findByGradeId(gradeId);
        feemaster = bsFeeTypeMasterRepository.findByAcdyrmasterAndLevelAndStatus(academicYearMaster, gradeMaster, "Active");
        List<FeeTypeMasterPojo> ftPojoList = ObjectMapperUtils.mapAll(feemaster, FeeTypeMasterPojo.class);
        return ftPojoList;
    }

    //studentFeeList
    public List<StudentFeePojo> studentFeeList(String searchText, String grade, String student, String feeType) {
        List<StudentFeePojo> studentPojoList = null;
        if (grade != "" && student == "") {
            GradeMaster gdobj = bsGrademasterRepository.findByGradeId(Long.parseLong(grade));
            List<StudentFee> studentfeeTypelist = bsStudentFeeRepository.findByLevel(gdobj);
            if (studentfeeTypelist != null) {
                studentPojoList = ObjectMapperUtils.mapAll(studentfeeTypelist, StudentFeePojo.class);
            } else {
                studentPojoList = null;
            }
        } else if (student != "" && grade == "") {
            List<Student> stuobj = bsStudentRepository.findByStudentId(Long.parseLong(student));
            List<StudentFee> studentfeeTypelist = Collections.singletonList(bsStudentFeeRepository.findByStudent(stuobj.get(0)));
            studentPojoList = ObjectMapperUtils.mapAll(studentfeeTypelist, StudentFeePojo.class);
        } else if (student != "" && grade != ""){
            GradeMaster gdobj = bsGrademasterRepository.findByGradeId(Long.parseLong(grade));
            List<Student> stuobj = bsStudentRepository.findByStudentId(Long.parseLong(student));
            List<StudentFee> studentfeeTypelist = Collections.singletonList(bsStudentFeeRepository.findByStudentAndLevel(stuobj.get(0), gdobj));
            studentPojoList = ObjectMapperUtils.mapAll(studentfeeTypelist, StudentFeePojo.class);
        } else {
            List<StudentFee> studentfeeTypelist = bsStudentFeeRepository.findAll();
            studentPojoList = ObjectMapperUtils.mapAll(studentfeeTypelist, StudentFeePojo.class);
        }
        for (StudentFeePojo pojolist : studentPojoList) {
            pojolist.setShowDetails(false);
            StudentFee studentobj = bsStudentFeeRepository.findByStudentFeeId(pojolist.getStudentFeeId());
            pojolist.setDueAmount(studentobj.getDueAmount());
            pojolist.setStatus(studentobj.getStudent().getStudentStatus());
            List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByStudentfee(studentobj);
            pojolist.setStudentFeeDetailsList(studentFeeDetails);
            pojolist.setTotalFeeAmount(0d);
            pojolist.setTotalPayable(0d);
            pojolist.setDueAmount(0d);
            pojolist.setPaidAmount(0d);
            for (StudentFeeDetails studentFeeDetails1 : studentFeeDetails) {
                if (StringUtils.equalsIgnoreCase(studentFeeDetails1.getFeetypemaster().getType(), feeType) && studentFeeDetails1.getCheckboxstatus() == true) {
                    pojolist.setTotalFeeAmount(pojolist.getTotalFeeAmount() + studentFeeDetails1.getFeeTypeAmount());
                    pojolist.setTotalPayable(pojolist.getTotalPayable() + studentFeeDetails1.getPayable());
                    pojolist.setPaidAmount(studentFeeDetails1.getPaidAmt() + pojolist.getPaidAmount());
                    pojolist.setDueAmount(pojolist.getTotalPayable() - pojolist.getPaidAmount());
                }
            }
        }
        return studentPojoList;
    }

    public List<AcademicYearMasterPojo> getAcademicYearList() {
        List<AcademicYearMaster> academics = new ArrayList<>();
        String Status = "Active";
        academics = bsAcademicYearMasterRepository.findByStatus(Status);
        List<AcademicYearMasterPojo> acdPojoList = ObjectMapperUtils.mapAll(academics, AcademicYearMasterPojo.class);
        return acdPojoList;
    }


    public List<StudentFeeDetails> getStudentFeeDetails(String studentId) {
        List<StudentFeeDetails> studentFeeDetailslist = new ArrayList<>();
        List<Student> student = bsStudentRepository.findByStudentId(Long.parseLong(studentId));
        if (student.size() > 0) {
            Student studentobj = bsStudentRepository.findByStudentNameAndAdmissionFormNo(student.get(0).getStudentName(), student.get(0).getAdmissionFormNo());
            StudentFee studentFeeobj = bsStudentFeeRepository.findByStudent(studentobj);

            studentFeeDetailslist = bsStudentFeeDetailsRepository.findByStudentfee(studentFeeobj);

            List<StudentFeeDetails> acdPojoList = ObjectMapperUtils.mapAll(studentFeeDetailslist, StudentFeeDetails.class);
            return acdPojoList;
        } else {
            return null;
        }

    }

    public List<AcademicYearMasterPojo> getAcademicYear2List(String searchText, String checkboxStatus) {
        List<AcademicYearMaster> academics = new ArrayList<>();
        if (StringUtils.isBlank(searchText) && checkboxStatus.equalsIgnoreCase("true")) {
            academics = bsAcademicYearMasterRepository.findAll();
        }
        if (StringUtils.isBlank(searchText) && checkboxStatus.equalsIgnoreCase("false")) {
            String status = "Active";
            academics = bsAcademicYearMasterRepository.findByStatus(status);
        } else if (!StringUtils.isEmpty(searchText)) {
            academics = bsAcademicYearMasterRepository.findByAcdyrNameIsStartingWith(searchText);
        }
        List<AcademicYearMasterPojo> acdPojoList = ObjectMapperUtils.mapAll(academics, AcademicYearMasterPojo.class);
        return acdPojoList;

    }

    public BasePojo getPaginatedAcdemicYearList(String searchText, String status, BasePojo basePojo) {
        List<AcademicYearMaster> academics = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "acdyrId"));
        if (basePojo.isLastPage() == true) {
            List<AcademicYearMaster> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsAcademicYearMasterRepository.findAllByAcdyrNameContaining(searchText);
            } else {
                list1 = bsAcademicYearMasterRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        AcademicYearMaster academicYearMaster = new AcademicYearMaster();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "acdyrId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            academicYearMaster = bsAcademicYearMasterRepository.findFirstByAcdyrNameContainingAndStatus(searchText, status, sort);
            academics = bsAcademicYearMasterRepository.findAllByAcdyrNameContainingAndStatus(searchText, status, pageable);
        } else {
            academicYearMaster = bsAcademicYearMasterRepository.findFirstByStatus(status, sort);
            academics = bsAcademicYearMasterRepository.findAllByStatus(status, pageable);
        }
        if (academics.contains(academicYearMaster)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<AcademicYearMasterPojo> acdPojoList = ObjectMapperUtils.mapAll(academics, AcademicYearMasterPojo.class);
        basePojo = calculatePagination(basePojo, acdPojoList.size());
        basePojo.setList(acdPojoList);
        return basePojo;
    }
    public BasePojo getPaginatedFeeTypeList(String searchText, String status, BasePojo basePojo) {
        List<FeeTypeMaster> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "feeTypeId"));
        if (basePojo.isLastPage() == true) {
            List<FeeTypeMaster> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsFeeTypeMasterRepository.findAllByFeeTypeNameContaining(searchText);
            } else {
                list1 = bsFeeTypeMasterRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        FeeTypeMaster feeType = new FeeTypeMaster();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "feeTypeId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            feeType = bsFeeTypeMasterRepository.findFirstByFeeTypeNameContainingAndStatus(searchText, status, sort);
            list = bsFeeTypeMasterRepository.findAllByFeeTypeNameContainingAndStatus(searchText, status, pageable);
        } else {
            feeType = bsFeeTypeMasterRepository.findFirstByStatus(status, sort);
            list = bsFeeTypeMasterRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(feeType)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<FeeTypeMasterPojo> acdPojoList = ObjectMapperUtils.mapAll(list, FeeTypeMasterPojo.class);
        basePojo = calculatePagination(basePojo, acdPojoList.size());
        basePojo.setList(acdPojoList);
        return basePojo;
    }

    public BasePojo getPaginatedDiscountTypeList(String searchText, String status, BasePojo basePojo) {
        List<DiscountType> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "discountId"));
        if (basePojo.isLastPage() == true) {
            List<DiscountType> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = discountTypeRepository.findAllByDiscountTypeContaining(searchText);
            } else {
                list1 = discountTypeRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        DiscountType type = new DiscountType();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "discountId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            type = discountTypeRepository.findFirstByDiscountTypeContainingAndDiscountStatus(searchText, status, sort);
            list = discountTypeRepository.findAllByDiscountTypeContainingAndDiscountStatus(searchText, status, pageable);
        } else {
            type = discountTypeRepository.findFirstByDiscountStatus(status, sort);
            list = discountTypeRepository.findAllByDiscountStatus(status, pageable);
        }
        if (list.contains(type)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<DiscountTypePojo> acdPojoList = ObjectMapperUtils.mapAll(list, DiscountTypePojo.class);
        basePojo = calculatePagination(basePojo, acdPojoList.size());
        basePojo.setList(acdPojoList);
        return basePojo;
    }

    public BasePojo getPaginatedStudentList(String searchText, String checkboxStatus, String gradeSearch, String studentSearch, BasePojo basePojo) {
        List<Student> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "studentId"));
        if (basePojo.isLastPage() == true) {
            List<Student> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText.trim()) || (StringUtils.isNotBlank(gradeSearch) ||
                    (StringUtils.isNotBlank(studentSearch)))) {
                list1 = getList(searchText, gradeSearch, studentSearch);
            } else {
                list1 = bsStudentRepository.findAllByStudentStatus(checkboxStatus);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        Student student = new Student();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "studentId"));
        }
        if (!StringUtils.isEmpty(searchText.trim()) || (StringUtils.isNotBlank(gradeSearch) ||
                (StringUtils.isNotBlank(studentSearch)))) {
            student = getFirstObject(sort, searchText, gradeSearch, studentSearch);
            list = getPaginatedList(pageable, searchText, gradeSearch, studentSearch);
        } else {
            student = bsStudentRepository.findFirstByStudentStatus(checkboxStatus, sort);
            list = bsStudentRepository.findAllByStudentStatus(checkboxStatus, pageable);
        }
        if (list.contains(student)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);

        }
        if (StringUtils.isEmpty(checkboxStatus)) {
            checkboxStatus = "Active";
        }
        List<StudentPojo> acdPojoList = BsStudentMapper.mapStudentEntityToPojo(list);
        for(StudentPojo s : acdPojoList){
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(s.getGradeId());
                s.setGradeName(gradeMaster.getGradeName());
        }
        basePojo = calculatePagination(basePojo, acdPojoList.size());
        basePojo.setList(acdPojoList);
        return basePojo;
    }

    public List<AttendanceMgt> getStudentAttendanceReportList(Date from, Date to) {
        List<AttendanceMgt> attendanceMgtList = bsAttendanceMgtRepository.findAllByADateBetween(from, to);
        return attendanceMgtList;
    }

    @Transactional
    public List<Student> getList(String searchText, String gradeSearch, String studentSearch) {
        List<Student> list = bsStudentRepository.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("studentName"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(gradeSearch.trim())) {
                    GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(gradeSearch));
                    Predicate predicates = cb.equal(root.get("level"), gradeMaster);
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(studentSearch.trim())) {
                    Predicate predicates = cb.equal(root.get("studentId"), Long.parseLong(studentSearch.trim()));
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        });
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Transactional
    public Student getFirstObject(Sort sort, String searchText, String gradeSearch, String studentSearch) {
        List<Student> list = bsStudentRepository.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("studentName"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(gradeSearch.trim())) {
                    GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(gradeSearch));
                    Predicate predicates = cb.equal(root.get("level"), gradeMaster);
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(studentSearch.trim())) {
                    Predicate predicates = cb.equal(root.get("studentId"), Long.parseLong(studentSearch.trim()));
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        }, sort);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Student> getPaginatedList(Pageable pageable, String searchText, String gradeSearch, String studentSearch) {
        Page<Student> list = bsStudentRepository.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText.trim())) {
                    Predicate predicates = cb.like(root.get("studentName"), "%" + searchText.trim() + "%");
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(gradeSearch.trim())) {
                    GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(gradeSearch));
                    Predicate predicates = cb.equal(root.get("level"), gradeMaster);
                    predicatesList.add(predicates);
                }
                if (!StringUtils.isEmpty(studentSearch.trim())) {
                    Predicate predicates = cb.equal(root.get("studentId"), Long.parseLong(studentSearch.trim()));
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        }, pageable);
        return list.getContent();
    }

    public List<StudentPojo> getStudentList(String searchText, String grade, String student, String checkboxStatusForStudent) {
        List<StudentPojo> studPojoList = null;
        List<Student> studList = new ArrayList<>();
        if (searchText != "") {
            studList = bsStudentRepository.findStudentByStudentNameIsLike("%" + searchText + "%");
        }
        if (grade != "" && student == "") {
            GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(Long.valueOf(grade));
            studList = bsStudentRepository.findByLevel(grdmstrobj);
        } else if (student != "" && grade == "") {
            studList = bsStudentRepository.findByStudentId(Long.valueOf(student));
        } else if (student != "" && grade != "") {
            studList = bsStudentRepository.findByStudentId(Long.valueOf(student));
        } else if (student == "" && grade == "" && searchText == "" && checkboxStatusForStudent.equals("false")) {
            String status = "Active";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        } else if (student == "" && grade == "" && searchText == "" && checkboxStatusForStudent.equals("true")) {
            String status = "InActive";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        } else {
            String status = "Active";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        }
        studPojoList = ObjectMapperUtils.mapAll(studList, StudentPojo.class);
        for (StudentPojo studentPojo : studPojoList) {
            studentPojo.setAcdYearId(studentPojo.getAcademicYearMaster().getAcdyrId());
            studentPojo.setGradeId(studentPojo.getGradeMaster().getGradeId());
        }
        return studPojoList;
    }

    //    getStudentBasedOnGradeList
    public List<StudentPojo> getStudentBasedOnGradeList(String searchText) {
        List<StudentPojo> studPojoList = null;
        if (searchText == "") {
            List<Student> studList = (List<Student>) bsStudentRepository.findAllByStudentStatus("Active");
            studPojoList = ObjectMapperUtils.mapAll(studList, StudentPojo.class);
        } else {
            GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(Long.valueOf(searchText));
            List<Student> studList = bsStudentRepository.findByLevel(grdmstrobj);
            studPojoList = ObjectMapperUtils.mapAll(studList, StudentPojo.class);
        }
        return studPojoList;
    }


    public List<GradeMasterPojo> getStudentBasedOnStatusList(String searchText) {

        List<EnquiryForm> enquiryForms = null;

        List<GradeMasterPojo> gradeMasterPojos = null;
        if (searchText == "") {
         enquiryForms = (List<EnquiryForm>) enquiryRepository.findAll();
        } else {
             enquiryForms = enquiryRepository.findByStatus(searchText);
        }
        for(EnquiryForm enquiryFormPOJO:enquiryForms){
            List<GradeMaster> grdmstrobj = bsGrademasterRepository.findAllByGradeId(Long.valueOf(enquiryFormPOJO.getLevel()));
            gradeMasterPojos = BsGradeMapper.mapgradeEntityToPojo(grdmstrobj);
        }
        return gradeMasterPojos;
    }

    public List<BsUserPojo> sassUserList() {
        List<User> users = (List<User>) bsUserRepository.findAll();
        List<BsUserPojo> bsUserPojoList = ObjectMapperUtils.mapAll(users, BsUserPojo.class);
        return bsUserPojoList;
    }

    public List<HolidayPojo> getholidayList() {
        List<Holiday> holidays =  holidayRepositories.findAll();
        List<HolidayPojo> holidayPojoList = ObjectMapperUtils.mapAll(holidays, HolidayPojo.class);
        return holidayPojoList;
    }

    public List<TrainerPojo> getTrainerList() {
        List<Trainer> trainers = trainerRepository.findAll();
        List<TrainerPojo> trainerPojoList = ObjectMapperUtils.mapAll(trainers, TrainerPojo.class);
        return trainerPojoList;
    }

    public GradeMaster SaveGradeMaster(GradeMasterPojo details) throws JSONException, IOException {
        GradeMaster gradeMaster = null;
        //findByGradeNameAndGradeIdNotIn
        User user = bsUserRepository.findOne(Long.parseLong(details.getUserId()));//getting userObj
        if (details.getGradeId() != null) {
            gradeMaster = bsGrademasterRepository.findByGradeNameAndGradeIdNotIn(details.getGradeName(), details.getGradeId());
            if (gradeMaster == null) {
                gradeMaster = BsGradeMapper.mapPojoToEntity(details);
                gradeMaster.setUserId(user);//userId saveing in gradeMaster Table
                return gradeMaster;
            } else {
                return null;
            }
        } else {
            gradeMaster = bsGrademasterRepository.findByGradeName(details.getGradeName());
            if (gradeMaster == null) {
                gradeMaster = BsGradeMapper.mapPojoToEntity(details);
                gradeMaster.setUserId(user);//userId saveing in gradeMaster Table
                bsGrademasterRepository.save(gradeMaster);
                return gradeMaster;
            } else {
                return null;
            }
        }
    }


    public GradeMaster saveGrade(GradeMasterPojo gradeMasterPojo) {
        GradeMaster gradeMaster = null;
        if (gradeMasterPojo.getGradeId() != null) {
            gradeMaster = bsGrademasterRepository.findByGradeNameAndGradeIdNotIn(gradeMasterPojo.getGradeName(), gradeMasterPojo.getGradeId());
        } else {
            gradeMaster = bsGrademasterRepository.findByGradeName(gradeMasterPojo.getGradeName());
        }
        if (gradeMaster == null) {
            gradeMaster = BsGradeMapper.mapPojoToEntity(gradeMasterPojo);
            bsGrademasterRepository.save(gradeMaster);
            return gradeMaster;
        } else {
            return null;
        }
    }

    public TrainingModule saveNewTrainingModule(TrainingModulePojo trainingModulePojo) {
        TrainingModule trainingModule = new TrainingModule();
        if (trainingModulePojo.getTrainingId() != null) {
            trainingModule = trainingModuleRepository.findByTrainingModuleNameAndTrainingIdNotIn(trainingModulePojo.getTrainingModuleName(), trainingModulePojo.getTrainingId());
        } else {
            trainingModule = trainingModuleRepository.findByTrainingModuleName(trainingModulePojo.getTrainingModuleName());
        }
        if (trainingModule == null) {
            TrainingModule trainingModule1 = BsTrainingModuleMapper.mapPojoToEntity(trainingModulePojo);
            trainingModuleRepository.save(trainingModule1);
            return trainingModule1;
        } else {
            return null;
        }

    }


    public Qualification saveNewQualificationMaster(QualificationPojo qualificationPojo) {
        Qualification qualification = new Qualification();
        if (qualificationPojo.getQualificationId() != null) {
            qualification = qualificationRepository.findByQualificationNameAndQualificationIdNotIn(qualificationPojo.getQualificationName(), qualificationPojo.getQualificationId());
        } else {
            qualification = qualificationRepository.findByQualificationName(qualificationPojo.getQualificationName());
        }
        if (qualification == null) {
            Qualification qualification1 = BsQualificationMapper.mapPojoToEntity(qualificationPojo);
            qualificationRepository.save(qualification1);
            return qualification1;
        } else {
            return null;
        }

    }


    public DoctorPanelMaster saveNewDoctorMaster(DoctorpanelPojo doctorpanelPojo) {
        DoctorPanelMaster doctorPanelMaster = new DoctorPanelMaster();
        if (doctorpanelPojo.getDoctorpanelId() != null) {
            doctorPanelMaster = doctorPanelRepository.findByDoctorNameAndDoctorpanelIdNotIn(doctorpanelPojo.getDoctorName(), doctorpanelPojo.getDoctorpanelId());
        } else {
            doctorPanelMaster = doctorPanelRepository.findByDoctorName(doctorpanelPojo.getDoctorName());
        }
        if (doctorPanelMaster == null) {
            DoctorPanelMaster doctorPanelMaster1 = BsDoctorpanelMapper.mapPojoToEntity(doctorpanelPojo);
            doctorPanelRepository.save(doctorPanelMaster1);
            return doctorPanelMaster1;
        } else {
            return null;
        }

    }


    public SchoolBranchDetails SaveSchoolBranchDetails(SchoolBranchDetailsPojo details) throws IOException, JSONException {
        SchoolBranchDetails schoolBranchDetails = null;
        List<SchoolBranchDetails> list = bsSchoolBranchDetailsRepository.findAll();
        if (list.size() > 0) {
            details.setSchoolBranchId(list.get(0).getSchoolBranchId());
        }
        schoolBranchDetails = BsSchoolBranchDetailsMapper.mapPojoToEntity(details);
        bsSchoolBranchDetailsRepository.save(schoolBranchDetails);
        MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(details);
        masterPojo.setMasterFlag("FeeTypeMaster");
        Gson gson = new Gson();
        String JsonInString = gson.toJson(masterPojo);
        CartMaster cartMaster = CartMasterRepository.findOne(1l);
        String cartID = "";
        if (cartMaster!=null) cartID = cartMaster.getHiConnectCompanyRegNo();
        String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "AddLocation", "AddMaster");
        String JsonBrainyStudentString = gson.toJson(details);
        User user = bsUserRepository.findOne(1l);
        String branchCode = "";
        if (!user.equals(null)) branchCode = user.getBranchCode();
        String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString, branchCode, branchCode, "AddBranch", "AddMaster");
        return schoolBranchDetails;
    }

    public FeeTypeMaster SaveFeeTypeMaster(FeeTypeMasterPojo details) throws JSONException, IOException {
        GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeName((details.getGradeName()));
        AcademicYearMaster acdobj = bsAcademicYearMasterRepository.findByAcdyrName(details.getAcdyrName());
        FeeTypeMaster feeTypeMaster = null;
        if (details.getFeeTypeId() != null) {
            if (feeTypeMaster == null) {
                feeTypeMaster = BsFeeTypeMasterMapper.mapPojoToEntity(details);
                feeTypeMaster.setLevel(grdmstrobj);
                feeTypeMaster.setAcdyrmaster(acdobj);
                bsFeeTypeMasterRepository.save(feeTypeMaster);
                MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(feeTypeMaster);
                masterPojo.setMasterFlag("FeeTypeMaster");
                Gson gson = new Gson();
                String JsonInString = gson.toJson(masterPojo);
                return feeTypeMaster;
            } else {
                return null;
            }

        } else {
            if (feeTypeMaster == null) {
                feeTypeMaster = BsFeeTypeMasterMapper.mapPojoToEntity(details);
                feeTypeMaster.setLevel(grdmstrobj);
                feeTypeMaster.setAcdyrmaster(acdobj);

                bsFeeTypeMasterRepository.save(feeTypeMaster);
                MasterPojo masterPojo = MasterMapper.convetToMasterPojo1(feeTypeMaster);
                masterPojo.setMasterFlag("FeeTypeMaster");
                Gson gson = new Gson();
                String JsonInString = gson.toJson(masterPojo);
                CartMaster cartMaster = CartMasterRepository.findOne(1l);
                String cartID = "";
                if (cartMaster != null) {
                    cartID = cartMaster.getHiConnectCompanyRegNo();
                }
                String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "AddItem", "AddMaster");
                String JsonBrainyStudentString = gson.toJson(details);
                User user = bsUserRepository.findOne(1l);
                String branchCode = "";
                if (!user.equals(null)) branchCode = user.getBranchCode();
                String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString, branchCode, branchCode, "AddFeeType", "AddMaster");
                return feeTypeMaster;
            } else {
                return null;
            }
        }
    }

    public void createGeneralLedgerForGTPurchase(String feeTypeName, Date date, double feeAmt, String receiptNo, String accountMasterName) {
        AccountMaster accountMaster = accountMasterRepository.findByAccountname(accountMasterName);
        GLTransactions gl = new GLTransactions();
        gl.setAccount(accountMaster);
        gl.setAmount(BigDecimal.valueOf(feeAmt));
        gl.setAcc_code(accountMaster != null ? accountMaster.getAccountcode() : null);
        gl.setSuppInNo(feeTypeName);
        gl.setTran_date(date);
        gl.setTran_no(receiptNo);
        gl.setFlagForGlTrans(feeTypeName);
        glTransactionRepository.save(gl);
    }

    public AcademicYearMaster SaveAcademicYearMaster(AcademicYearMasterPojo details) throws JSONException, IOException {
        AcademicYearMaster yearMaster = null;
        if (details.getAcdyrId() != null) {
            yearMaster = bsAcademicYearMasterRepository.findByAcdyrNameAndAcdyrIdIsNotIn(details.getAcdyrName(), details.getAcdyrId());
            if (yearMaster == null) {
                yearMaster = BsAcademicMapper.mapPojoToEntity(details);
                bsAcademicYearMasterRepository.save(yearMaster);
                return yearMaster;
            } else {
                return null;
            }

        } else {
            yearMaster = bsAcademicYearMasterRepository.findByAcdyrName(details.getAcdyrName());
            if (yearMaster == null) {
                yearMaster = BsAcademicMapper.mapPojoToEntity(details);
                bsAcademicYearMasterRepository.save(yearMaster);
                return yearMaster;
            } else {
                return null;
            }
        }
    }

    public DiscountType saveDiscountType(DiscountTypePojo discountTypePojo) {
        DiscountType type = new DiscountType();
        if (discountTypePojo.getDiscountId() != null) {
            type = discountTypeRepository.findByDiscountTypeAndDiscountIdNotIn(discountTypePojo.getDiscountType(), discountTypePojo.getDiscountId());
        } else {
            type = discountTypeRepository.findByDiscountType(discountTypePojo.getDiscountType());
        }
        if (type == null) {
            DiscountType discountType = BsDiscountMapper.mapPojoToEntity(discountTypePojo);
            discountTypeRepository.save(discountType);
            return discountType;
        } else {
            return null;
        }

    }

    public List<DiscountTypePojo> getDiscountTypeList(String search) {
        List<DiscountType> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = discountTypeRepository.findAll();
        } else {
            list = discountTypeRepository.findAllByDiscountType(search);
        }
        List<DiscountTypePojo> typePojos = BsDiscountMapper.mapEntityToPojo(list);
        return typePojos;
    }

    public StudentFeeDto getStudentFeeDetailsList(Long studentId, String type, String feeType, Long id) {
        StudentFee studentFee = bsStudentFeeRepository.findByStudent(bsStudentRepository.findByStudentId(studentId).get(0));
        StudentFeeDto studentFeeDto = new StudentFeeDto();
        if(studentFee!=null) {
            studentFeeDto.setStudentId(studentFee.getStudent().getStudentId());
            studentFeeDto.setStudentFeeId(studentFee.getStudentFeeId());
            studentFeeDto.setStudentName(studentFee.getStudent().getStudentName());
            studentFeeDto.setFatherName(studentFee.getStudent().getFatherName());
            studentFeeDto.setMotherName(studentFee.getStudent().getMotherName());
            studentFeeDto.setStudentType(studentFee.getStudent().getStudentType());
            studentFeeDto.setAcademicYear(studentFee.getAcademicYearMaster().getAcdyrName());
            studentFeeDto.setAcademicYearId(studentFee.getAcademicYearMaster().getAcdyrId());
            studentFeeDto.setStudentProfileId(studentFee.getStudent().getStudentProfileId());
            studentFeeDto.setGradeId(studentFee.getLevel().getGradeId());
            studentFeeDto.setGradeName(studentFee.getLevel().getGradeName());
            studentFeeDto.setTotalPaid(studentFee.getPaidAmount());
        }
        if (id != null) {
            FeeReceipt feeReceipt = bsFeeReceiptRepository.findByStudentFeeAndFeeReceiptID(studentFee, id);
            studentFeeDto.setBankName(feeReceipt.getBankName());
            studentFeeDto.setChequeNo(feeReceipt.getChequeNo());
            studentFeeDto.setChequeDate(feeReceipt.getChequeDate());
            studentFeeDto.setChequeStatus(feeReceipt.getChequeStatus());
        }
        List<StudentFeeDetailsPojo> studentFeeDetailsPojos = new ArrayList<>();
        List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByStudentfee(studentFee);
        List<Long> ids = new ArrayList<>();
        studentFeeDto.setBankAmt(0D);
        for (StudentFeeDetails studentFeeDetails1 : studentFeeDetails) {
            if (StringUtils.isNotEmpty(feeType)) {
                if (StringUtils.equalsIgnoreCase(studentFeeDetails1.getFeetypemaster().getType(), feeType)) {
                    studentFeeDto = getStudentFeeDetails(studentFeeDetails1, ids, type, studentFee, studentFeeDetailsPojos, studentFeeDto);
                }
            } else {
                studentFeeDto = getStudentFeeDetails(studentFeeDetails1, ids, type, studentFee, studentFeeDetailsPojos, studentFeeDto);
            }
        }
        if (StringUtils.equalsIgnoreCase(type, "Student")) {
            List<FeeTypeMaster> feeTypeMasters = bsFeeTypeMasterRepository.findByLevelAndAcdyrmasterAndFeeTypeIdNotInAndStatus(studentFee.getLevel(), studentFee.getAcademicYearMaster(), ids, "Active");
            for (FeeTypeMaster feeTypeMaster : feeTypeMasters) {
                StudentFeeDetailsPojo studentFeeDetailsPojo = new StudentFeeDetailsPojo();
                InstallmentsPojo installmentsPojo = new InstallmentsPojo();
                installmentsPojo.setPaidAmt(0.00);
                installmentsPojo.setDueAmt(feeTypeMaster.getFeeAmount());
                installmentsPojo.setInstallmentsAmount(feeTypeMaster.getFeeAmount());
                installmentsPojo.setPayingAmt(feeTypeMaster.getFeeAmount());
                installmentsPojo.setFeeTypeMaster(feeTypeMaster);
                List<InstallmentsPojo> list = new ArrayList<>();
                list.add(installmentsPojo);
                studentFeeDetailsPojo.setFeeTypeStatus(true);
                studentFeeDetailsPojo.setInstallmentsPojos(list);
                studentFeeDetailsPojo.setFeeTypeAmount(feeTypeMaster.getFeeAmount());
                studentFeeDetailsPojo.setNoOfInstallments(1);
                studentFeeDetailsPojo.setValue(feeTypeMaster.getValue());
                studentFeeDetailsPojo.setFeeTypeName(feeTypeMaster.getFeeTypeName());
                studentFeeDetailsPojo.setFeeTypeId(feeTypeMaster.getFeeTypeId());
                studentFeeDetailsPojo.setDiscount(0.00);
                studentFeeDetailsPojo.setDueAmount(feeTypeMaster.getFeeAmount());
                studentFeeDetailsPojo.setPayable(feeTypeMaster.getFeeAmount());
                studentFeeDetailsPojo.setPaidAmount(0.00);
                studentFeeDetailsPojo.setCheckBox(false);
                studentFeeDetailsPojos.add(studentFeeDetailsPojo);
            }
        }
        SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
        if (schoolBranchDetails != null) {
            studentFeeDto.setReceiptNo(String.valueOf(schoolBranchDetails.getReceiptNo() + 1));
        }
        studentFeeDto.setStudentFeeDetailsPojoList(studentFeeDetailsPojos);
        return studentFeeDto;
    }

    public StudentAttendencePojo getStudentAttendenceDetails(Long studentAttendenceId, String type) {
        StudentAttendence studentAttendence = bsstudentAttendanceRepository.findByStudentAttendenceId(studentAttendenceId);
        StudentAttendencePojo studentAttendencePojo = new StudentAttendencePojo();
        if(studentAttendence!=null) {
            studentAttendencePojo.setStudentAttendenceId(studentAttendence.getStudentAttendenceId());
            studentAttendencePojo.setStudentAttendence(studentAttendence.getStudentAttendence());
            studentAttendencePojo.setStudent(studentAttendence.getStudent());
            studentAttendencePojo.setaDate(studentAttendence.getaDate());
            studentAttendencePojo.setAttendance(studentAttendence.getAttendance());
            studentAttendencePojo.setStudentClass(studentAttendence.getStudentClass());
            studentAttendencePojo.setFirstCheckIn(studentAttendence.getFirstCheckIn());
            studentAttendencePojo.setLastCheckOut(studentAttendence.getLastCheckOut());
            studentAttendencePojo.setStudentLevel(studentAttendence.getStudentLevel());
            studentAttendencePojo.setTime(studentAttendence.getTime());
            studentAttendencePojo.setSerialNo(studentAttendence.getSerialNo());
        }
        return studentAttendencePojo;
    }

    public StudentFeeDto getStudentFeeDetails(StudentFeeDetails studentFeeDetails1, List<Long> ids, String type, StudentFee studentFee, List<StudentFeeDetailsPojo> studentFeeDetailsPojos, StudentFeeDto studentFeeDto) {
        ids.add(studentFeeDetails1.getFeetypemaster().getFeeTypeId());
        if (!StringUtils.equalsIgnoreCase(type, "Fee") || (StringUtils.equalsIgnoreCase(type, "Fee") && studentFeeDetails1.getCheckboxstatus() == true)) {
            List<Installments> installments = bsInstallmentsRepository.findByStudentFeeAndFeeTypeMaster(studentFee, studentFeeDetails1.getFeetypemaster());
            List<InstallmentsPojo> list = new ArrayList<>();
            for (Installments installments1 : installments) {
                InstallmentsPojo installmentsPojo = new InstallmentsPojo();
                installmentsPojo.setInstallmentsId(installments1.getInstallmentsId());
                if (installments1.getInstallmentsAmount() != null) {
                    installmentsPojo.setInstallmentsAmount(installments1.getInstallmentsAmount() - installments1.getPaidAmt());
                } else {
                    installmentsPojo.setInstallmentsAmount(studentFeeDetails1.getPendingFee());
                }
                installmentsPojo.setPayingAmt(installmentsPojo.getInstallmentsAmount());
                installmentsPojo.setPaidAmt(installments1.getPaidAmt());
                installmentsPojo.setDueAmt(installments1.getInstallmentsAmount());
                installmentsPojo.setDiscountRemarks(installments1.getDiscountRemarks());
                if (installmentsPojo.getInstallmentsAmount() != 0)
                    installmentsPojo.setCheckBox(true);
                else
                    installmentsPojo.setCheckBox(false);
                if (installments1.getDueDate() != null)
                    installmentsPojo.setDueDate(installments1.getDueDate());
                installmentsPojo.setStatus(installments1.getStatus());
                list.add(installmentsPojo);
            }
            StudentFeeDetailsPojo studentFeeDetailsPojo = new StudentFeeDetailsPojo();
            studentFeeDetailsPojo.setInstallmentsPojos(list);
            FeeTypeMaster feeTypeMaster = studentFeeDetails1.getFeetypemaster();
            if (StringUtils.equalsIgnoreCase(feeTypeMaster.getStatus(), "InActive")) {
                if (studentFeeDetails1.getCheckboxstatus() == false) {
                    studentFeeDetailsPojo.setFeeTypeStatus(false);
                } else {
                    studentFeeDetailsPojo.setFeeTypeStatus(true);
                }
            } else {
                studentFeeDetailsPojo.setFeeTypeStatus(true);
            }
            studentFeeDetailsPojo.setFeeTypeAmount(studentFeeDetails1.getFeeTypeAmount());
            studentFeeDetailsPojo.setStudentFeeDetailsId(studentFeeDetails1.getStudentFeeDetailsId());
            studentFeeDetailsPojo.setNoOfInstallments(studentFeeDetails1.getNoOfInstallments());
            studentFeeDetailsPojo.setValue(studentFeeDetails1.getFeetypemaster().getValue());
            studentFeeDetailsPojo.setFeeTypeName(studentFeeDetails1.getFeeTypeName());
            studentFeeDetailsPojo.setFeeTypeId(studentFeeDetails1.getFeetypemaster().getFeeTypeId());
            studentFeeDetailsPojo.setDiscountRemarks(studentFeeDetails1.getDiscountRemarks());
            studentFeeDetailsPojo.setDiscountType(studentFeeDetails1.getDiscountType());
            studentFeeDetailsPojo.setDiscount(studentFeeDetails1.getDiscount());
            if (studentFeeDetails1.getPaidAmt() != null)
                studentFeeDetailsPojo.setDueAmount(studentFeeDetails1.getPayable() - studentFeeDetails1.getPaidAmt());
            else
                studentFeeDetailsPojo.setDueAmount(studentFeeDetails1.getPayable());
            studentFeeDetailsPojo.setPayable(studentFeeDetails1.getPayable());
            studentFeeDetailsPojo.setPaidAmount(studentFeeDetails1.getPaidAmt());
            studentFeeDetailsPojo.setDueDate(studentFeeDetails1.getDueDate());
            studentFeeDetailsPojo.setCheckBox(studentFeeDetails1.getCheckboxstatus());
            studentFeeDetailsPojos.add(studentFeeDetailsPojo);
            studentFeeDto.setBankAmt((studentFeeDetails1.getPayable() - studentFeeDetails1.getPaidAmt()) + studentFeeDto.getBankAmt());
        }
        return studentFeeDto;
    }

    public List<StudentFee> retrieveStudentFee(ReportPojo filter, String type) {
        List<StudentFee> employees = bsStudentFeeRepository.findAll(new Specification<StudentFee>() {
            @Override
            public Predicate toPredicate(Root<StudentFee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();
                if (filter.getAcademicYearMaster() != null) {
                    Predicate predicates = cb.equal(root.get("academicYearMaster"), filter.getAcademicYearMaster());
                    predicatesList.add(predicates);
                }
                if (filter.getGradeMasters() != null)
                    if (filter.getGradeMasters().size() > 0) {
                        Predicate predicates = cb.in(root.get("level")).value(filter.getGradeMasters());
                        predicatesList.add(predicates);
                    }
                if (filter.getStudentId() != null) {
                    Predicate predicates = cb.equal(root.get("student").get("studentId"), filter.getStudentId());
                    predicatesList.add(predicates);
                }
                if (StringUtils.equalsIgnoreCase(type, "Due")) {
                    Predicate predicates = cb.equal(root.get("student").get("studentStatus"), "Active");
                    predicatesList.add(predicates);
                }
                return cb.and(predicatesList.toArray(new Predicate[0]));
            }
        });
        return employees;
    }

    public List<Installments> retrieveInstalments(ReportPojo filter) {
        List<StudentFee> list = retrieveStudentFee(filter, "Due");
        if (list.size() > 0) {
            List<Installments> employee = bsInstallmentsRepository.findAll(new Specification<Installments>() {
                @Override
                public Predicate toPredicate(Root<Installments> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicatesList = new ArrayList<>();
                    if (list.size() > 0) {
                        Predicate predicates = cb.in(root.get("studentFee")).value(list);
                        predicatesList.add(predicates);
                    }
                    if (filter.getFromDate() != null && filter.getToDate() != null) {
                        Predicate predicates = cb.between(root.get("dueDate"), filter.getFromDate(), filter.getToDate());
                        predicatesList.add(predicates);
                    }
                    return cb.and(predicatesList.toArray(new Predicate[0]));
                }
            });
            return employee;
        }
        return null;
    }

    public List<StudentFeePojo> getStudentDueList(ReportPojo reportPojo) {
        if (reportPojo.getGradeId() != null) {
            GradeMaster gradeMaster1 = bsGrademasterRepository.findByGradeName(reportPojo.getGradeId());
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeId(gradeMaster1.getGradeId());
            reportPojo.setGradeMasters(gradeMaster);
        }
        if (!StringUtils.isEmpty(reportPojo.getAcademicYearId())) {
            reportPojo.setAcademicYearMaster(bsAcademicYearMasterRepository.findOne(Long.parseLong(reportPojo.getAcademicYearId())));
        }
        List<StudentFeePojo> studentPojoList = new ArrayList<>();
        List<StudentFee> list = retrieveStudentFee(reportPojo, "Due");
        studentPojoList = ObjectMapperUtils.mapAll(list, StudentFeePojo.class);
        for (StudentFeePojo pojolist : studentPojoList) {
            pojolist.setShowDetails(false);
            StudentFee studentobj = bsStudentFeeRepository.findByStudentFeeId(pojolist.getStudentFeeId());
            pojolist.setPrimaryContactNo(studentobj.getStudent().getPrimaryContactNo());
            reportPojo.setStudentId(studentobj.getStudent().getStudentId());
            pojolist.setGradeName(studentobj.getStudent().getLevel().getGradeName());
            List<Installments> installmentsList = retrieveInstalments(reportPojo);
            pojolist.setDueAmount(0.00);
            for (Installments installments : installmentsList) {
                StudentFeeDetails studentFeeDetails = bsStudentFeeDetailsRepository.findByFeetypemasterAndStudentfee(installments.getFeeTypeMaster(), installments.getStudentFee());
                if (!StringUtils.isEmpty(reportPojo.getFeeType())) {
                    if (studentFeeDetails.getCheckboxstatus() == true && StringUtils.equalsIgnoreCase(studentFeeDetails.getFeetypemaster().getType(), reportPojo.getFeeType())) {
                        pojolist.setDueAmount(pojolist.getDueAmount() + (installments.getInstallmentsAmount() - installments.getPaidAmt()));
                    }
                } else {
                    if (studentFeeDetails.getCheckboxstatus() == true) {
                        pojolist.setDueAmount(pojolist.getDueAmount() + (installments.getInstallmentsAmount() - installments.getPaidAmt()));
                    }
                }
            }
            List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByStudentfee(studentobj);
            List<StudentFeeDetails> studentFeeDetailsList = new ArrayList<>();
            if (!StringUtils.isEmpty(reportPojo.getFeeType())) {
                for (StudentFeeDetails studentFeeDetails1 : studentFeeDetails) {
                    if (StringUtils.equalsIgnoreCase(studentFeeDetails1.getFeetypemaster().getType(), reportPojo.getFeeType())) {
                        studentFeeDetailsList.add(studentFeeDetails1);
                    }
                }
            } else {
                studentFeeDetailsList.addAll(studentFeeDetails);
            }
            pojolist.setStudentFeeDetailsList(studentFeeDetailsList);

        }
        return studentPojoList;
    }

    public List<FeeTypeMasterPojo> getReportDetails(ReportPojo reportPojo) {
        if (reportPojo.getGradeIds() != null) {
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeIdIn(reportPojo.getGradeIds());
            reportPojo.setGradeMasters(gradeMaster);
        }
        List<Installments> list = retrieveInstalments(reportPojo);
        List<FeeTypeMasterPojo> installmentsPojoList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int i = 1;
        for (Installments installments : list) {
            StudentFeeDetails studentFeeDetails = bsStudentFeeDetailsRepository.findByFeetypemasterAndStudentfee(installments.getFeeTypeMaster(), installments.getStudentFee());
            if (studentFeeDetails.getCheckboxstatus() == true) {
                FeeTypeMasterPojo feeTypeMasterPojo = new FeeTypeMasterPojo();
                if (list.size() > 0) {
                    feeTypeMasterPojo.setFeeTypeName(installments.getFeeTypeName() + "\t" + "Inst " + i++);
                } else {
                    feeTypeMasterPojo.setFeeTypeName(installments.getFeeTypeName());
                }
                feeTypeMasterPojo.setPaidAmt(installments.getPaidAmt());
                feeTypeMasterPojo.setDueAmt(installments.getInstallmentsAmount() - installments.getPaidAmt());
                feeTypeMasterPojo.setDueDate(dateFormat.format(installments.getDueDate()).toString());
//                if(feeTypeMasterPojo.getDueAmt()>0){
                installmentsPojoList.add(feeTypeMasterPojo);
//                }
            }
        }
        return installmentsPojoList;
    }

    public List<FeeReceipt> retrieveFeeCollected(ReportPojo filter) {
        List<StudentFee> list = retrieveStudentFee(filter, "Collect");
        if (list.size() > 0) {
            List<FeeReceipt> feeReceipts = bsFeeReceiptRepository.findAll(new Specification<FeeReceipt>() {
                @Override
                public Predicate toPredicate(Root<FeeReceipt> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicatesList = new ArrayList<>();
                    if (list.size() > 0) {
                        Predicate predicates = cb.in(root.get("studentFee")).value(list);
                        predicatesList.add(predicates);
                    }
                    if (filter.getFromDate() != null && filter.getToDate() != null) {
                        Predicate predicates = cb.between(root.get("receiptDate"), filter.getFromDate(), filter.getToDate());
                        predicatesList.add(predicates);
                    }
                    return cb.and(predicatesList.toArray(new Predicate[0]));
                }
            });
            return feeReceipts;
        }
        return null;
    }

    public List<FeeReceiptPojo> getReportCollected(ReportPojo reportPojo) {
        if (reportPojo.getGradeId() != null) {
            GradeMaster gradeMaster1 = bsGrademasterRepository.findByGradeName(reportPojo.getGradeId());
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeId(gradeMaster1.getGradeId());
            reportPojo.setGradeMasters(gradeMaster);
        }
        if (!StringUtils.isEmpty(reportPojo.getAcademicYearId())) {
            reportPojo.setAcademicYearMaster(bsAcademicYearMasterRepository.findOne(Long.parseLong(reportPojo.getAcademicYearId())));
        }
        List<FeeReceiptPojo> feeReceiptPojoArrayList = new ArrayList<>();
        List<FeeReceipt> list = retrieveFeeCollected(reportPojo);
        if (list != null) {
            Map<Long, List<FeeReceipt>> outputlist =
                    list.parallelStream().collect(groupingBy(w -> w.getStudentFee().getStudentFeeId()));
            for (Map.Entry receipt : outputlist.entrySet()) {
                StudentFee fee = bsStudentFeeRepository.findOne(Long.parseLong(receipt.getKey().toString()));
                FeeReceiptPojo feeReceiptPojo = new FeeReceiptPojo();
                for (FeeReceipt feeReceipt : (List<FeeReceipt>) receipt.getValue()) {
                    feeReceiptPojo.setStudentName(fee.getStudentName());
                    feeReceiptPojo.setStudentProfileId(fee.getStudent().getStudentProfileId());
                    feeReceiptPojo.setCashAmt(feeReceipt.getCashAmt() + feeReceiptPojo.getCashAmt());
                    feeReceiptPojo.setCardAmt(feeReceipt.getCardAmt() + feeReceiptPojo.getCardAmt());
                    if (StringUtils.equalsIgnoreCase(feeReceipt.getPaymentMode(), "Online")) {
                        feeReceiptPojo.setOnlineAmt(feeReceipt.getBankAmt() + feeReceiptPojo.getOnlineAmt());
                    } else {
                        feeReceiptPojo.setBankAmt(feeReceipt.getBankAmt() + feeReceiptPojo.getBankAmt());
                    }
                    feeReceiptPojo.setTotalAmt(feeReceipt.getTotalReceived() + feeReceiptPojo.getTotalAmt());
                }
                feeReceiptPojoArrayList.add(feeReceiptPojo);
            }
        }
        return feeReceiptPojoArrayList;
    }

    @Transactional
    public void downloadFeeDueReportExcel(OutputStream out, ReportPojo reportPojo) {
        try {
            List<StudentFeePojo> studentDueList = getStudentDueList(reportPojo);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
            String address = "";
            if (schoolBranchDetails != null) {
                if (!StringUtils.isEmpty(schoolBranchDetails.getAddress())) {
                    address = schoolBranchDetails.getAddress();
                }
            }
            String branchName = "";
            if (schoolBranchDetails != null) {
                if (!StringUtils.isEmpty(schoolBranchDetails.getBranchName())) {
                    branchName = schoolBranchDetails.getBranchName();
                }
            }
            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
            headerRow.createCell(2).setCellValue("INTERNATIONAL HOLISTIC MONTESSORI & SCHOOL" + "\n" + branchName + "\n" + address + "\n" + "Fee Due Report");
            CellRangeAddress cell = new CellRangeAddress(0, 2, 2, 4);
            sheet.addMergedRegion(cell);
            RegionUtil.setBorderTop(2, cell, sheet, hwb);
            RegionUtil.setBorderLeft(2, cell, sheet, hwb);
            RegionUtil.setBorderRight(2, cell, sheet, hwb);
            RegionUtil.setBorderBottom(2, cell, sheet, hwb);
            HSSFRow headerRow5 = sheet.createRow(3);
            headerRow5.createCell(0).setCellValue("From Date");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            headerRow5.createCell(1).setCellValue(dateFormat.format(reportPojo.getFromDate()));
            headerRow5.createCell(2).setCellValue("To Date");
            headerRow5.createCell(3).setCellValue(dateFormat.format(reportPojo.getToDate()));
            if (reportPojo.getAcademicYearMaster() != null) {
                HSSFRow headerRow6 = sheet.createRow(4);
                headerRow6.createCell(0).setCellValue("Academic Year");
                headerRow6.createCell(1).setCellValue(reportPojo.getAcademicYearMaster().getAcdyrName());
            }
            if (reportPojo.getGradeMasters() != null) {
                HSSFRow headerRow7 = sheet.createRow(5);
                headerRow7.createCell(0).setCellValue("Grades");
                int col = 1;
                for (GradeMaster gradeMaster : reportPojo.getGradeMasters())
                    headerRow7.createCell(col++).setCellValue(gradeMaster.getGradeName());
            }
            if (reportPojo.getFeeType() != null) {
                HSSFRow headerRow8 = sheet.createRow(6);
                headerRow8.createCell(0).setCellValue("FeeType");
                headerRow8.createCell(1).setCellValue(reportPojo.getFeeType());
            }
            HSSFRow headerRow1 = sheet.createRow(7);
            headerRow1.createCell(0).setCellValue("Student Profile ID");
            headerRow1.createCell(1).setCellValue("Name");
            headerRow1.createCell(2).setCellValue("Level");
            headerRow1.createCell(3).setCellValue("Total Fee");
            headerRow1.createCell(4).setCellValue("Total Discount");
            headerRow1.createCell(5).setCellValue("Total Paid");
            headerRow1.createCell(6).setCellValue("Total Due");
            int i = 7;
            double totalAmountPaid = 0, totalBalance = 0, totalFeeAmt = 0, totalDiscount = 0;
            for (StudentFeePojo list : studentDueList) {
                if (list.getDueAmount() > 0) {
                    HSSFRow row = sheet.createRow(++i);
                    totalFeeAmt = totalFeeAmt + list.getTotalFeeAmount();
                    totalDiscount = totalDiscount + (list.getTotalFeeAmount() - list.getTotalPayable());
                    totalAmountPaid = totalAmountPaid + list.getPaidAmount();
                    totalBalance = totalBalance + list.getDueAmount();
                    row.createCell(0).setCellValue(list.getStudent().getStudentProfileId());
                    row.createCell(1).setCellValue(list.getStudent().getStudentName());
                    row.createCell(2).setCellValue(list.getStudent().getLevel().getGradeName());
                    row.createCell(3).setCellValue(list.getTotalFeeAmount());
                    row.createCell(4).setCellValue(list.getTotalFeeAmount() - list.getTotalPayable());
                    row.createCell(5).setCellValue(list.getPaidAmount());
                    row.createCell(6).setCellValue(list.getDueAmount());
                    reportPojo.setStudentId(list.getStudent().getStudentId());
                    int col = 6, row1 = 6;
                    List<FeeTypeMasterPojo> feeTypeMasterPojoList = getReportDetails(reportPojo);
                    if (feeTypeMasterPojoList.size() > 0) {
                        for (FeeTypeMasterPojo feeTypeMasterPojo : feeTypeMasterPojoList) {
                            headerRow1.createCell(++row1).setCellValue("Fee Type");
                            headerRow1.createCell(++row1).setCellValue("Due Amount");
                            headerRow1.createCell(++row1).setCellValue("Due Date");
                            CellStyle style = hwb.createCellStyle();
                            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                            if (feeTypeMasterPojo.getDueAmt() == 0) {
                                HSSFCell cell1 = row.createCell(++col);
                                cell1.setCellValue(feeTypeMasterPojo.getFeeTypeName());
                                cell1.setCellStyle(style);
                                cell1 = row.createCell(++col);
                                cell1.setCellValue(feeTypeMasterPojo.getDueAmt());
                                cell1.setCellStyle(style);
                                cell1 = row.createCell(++col);
                                cell1.setCellValue(feeTypeMasterPojo.getDueDate());
                                cell1.setCellStyle(style);
                            } else {
                                row.createCell(++col).setCellValue(feeTypeMasterPojo.getFeeTypeName());
                                row.createCell(++col).setCellValue(feeTypeMasterPojo.getDueAmt());
                                row.createCell(++col).setCellValue(feeTypeMasterPojo.getDueDate());
                            }
                        }
                    }
                }
            }
            HSSFRow headerRow3 = sheet.createRow(++i);
            headerRow3.createCell(0).setCellValue("Total");
            headerRow3.createCell(1).setCellValue("");
            headerRow3.createCell(2).setCellValue("");
            headerRow3.createCell(3).setCellValue(totalFeeAmt);
            headerRow3.createCell(4).setCellValue(totalDiscount);
            headerRow3.createCell(5).setCellValue(totalAmountPaid);
            headerRow3.createCell(6).setCellValue(totalBalance);
            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    public MailDTO createSaveMailDetails(MailDTO saveMailDetails) {
        Mail mail = new Mail();
        mail.setUserName(saveMailDetails.getUserName());
        mail.setPassword(saveMailDetails.getPassword());
        mail.setPort(saveMailDetails.getPort());
        mail.setSmtpHost(saveMailDetails.getSmtpHost());
        mail.setForMail(saveMailDetails.getForMail());
        mail.setStatus("Active");
        bsMailRepository.save(mail);
        return saveMailDetails;
    }

    //downloadStudentDetailsExcel
    @Transactional
    public void downloadStudentDetailsExcel(OutputStream out, StudentFee studentDetails, List<StudentFeeDetails> studentFeeDetails) {
        try {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
            headerRow.createCell(2).setCellValue("Student Details");
            HSSFRow headerRow1 = sheet.createRow(5);
            headerRow1.createCell(0).setCellValue("Academic Year");
            headerRow1.createCell(1).setCellValue("Date Of Admission");
            headerRow1.createCell(2).setCellValue("Admission Form No");
            headerRow1.createCell(3).setCellValue("Level");
            headerRow1.createCell(4).setCellValue("Joining Date ");
            headerRow1.createCell(5).setCellValue("Student Name ");

            headerRow1.createCell(6).setCellValue("Permanent Address");
            headerRow1.createCell(7).setCellValue("Date Of Birth ");
            headerRow1.createCell(8).setCellValue("Local/Present Address");
            headerRow1.createCell(9).setCellValue("Gender ");
            headerRow1.createCell(10).setCellValue("Physical Condition ");

            headerRow1.createCell(11).setCellValue("Aadhaar No");
            headerRow1.createCell(12).setCellValue("Religion");
            headerRow1.createCell(13).setCellValue("Father Name");
            headerRow1.createCell(14).setCellValue("Father Email Id ");
            headerRow1.createCell(15).setCellValue("Father Mobile");

            headerRow1.createCell(16).setCellValue("Father Occupation");
            headerRow1.createCell(17).setCellValue("Mother Name");
            headerRow1.createCell(18).setCellValue("Mother Email Id");
            headerRow1.createCell(19).setCellValue("Mother Mobile ");
            headerRow1.createCell(20).setCellValue("Mother Occupation ");

            headerRow1.createCell(21).setCellValue("Primary Contact No");
            headerRow1.createCell(22).setCellValue("Local Guardian Name");
            headerRow1.createCell(23).setCellValue("Parents Annual Income");
            headerRow1.createCell(24).setCellValue("Guardian Number ");
            headerRow1.createCell(25).setCellValue("Status");
            int i = 5;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            HSSFRow row = sheet.createRow(++i);
            row.createCell(0).setCellValue(studentDetails.getStudent().getAcademicYearMaster().getAcdyrName());
            row.createCell(1).setCellValue(dateFormat.format(studentDetails.getStudent().getDateOfAdmission()));
            row.createCell(2).setCellValue(studentDetails.getStudent().getAdmissionFormNo());
            row.createCell(3).setCellValue(studentDetails.getStudent().getLevel().getGradeName());
            row.createCell(4).setCellValue(dateFormat.format(studentDetails.getStudent().getDateOfJoining()));
            row.createCell(5).setCellValue(studentDetails.getStudent().getStudentName());
            row.createCell(6).setCellValue(studentDetails.getStudent().getPermanentAddress());
            row.createCell(7).setCellValue(dateFormat.format(studentDetails.getStudent().getDateofbirth()));
            row.createCell(8).setCellValue(studentDetails.getStudent().getPresentAddress());
            row.createCell(9).setCellValue(studentDetails.getStudent().getGender());
            row.createCell(10).setCellValue(studentDetails.getStudent().getPhysicalCondition());

            row.createCell(11).setCellValue(studentDetails.getStudent().getAadhaarNo());
            row.createCell(12).setCellValue(studentDetails.getStudent().getReligion());
            row.createCell(13).setCellValue(studentDetails.getStudent().getFatherName());
            row.createCell(14).setCellValue(studentDetails.getStudent().getFatherEmailId());
            row.createCell(15).setCellValue(studentDetails.getStudent().getFatherContactNo());

            row.createCell(16).setCellValue(studentDetails.getStudent().getFatherOccupation());
            row.createCell(17).setCellValue(studentDetails.getStudent().getMotherName());
            row.createCell(18).setCellValue(studentDetails.getStudent().getMotherEmailId());
            row.createCell(19).setCellValue(studentDetails.getStudent().getMotherContactNo());
            row.createCell(20).setCellValue(studentDetails.getStudent().getMotherOccupation());

            row.createCell(21).setCellValue(studentDetails.getStudent().getPrimaryContactNo());
            row.createCell(22).setCellValue(studentDetails.getStudent().getGaurdianName());
            if (studentDetails.getStudent().getAnnualIncome() != null)
                row.createCell(23).setCellValue(studentDetails.getStudent().getAnnualIncome());
            else
                row.createCell(23).setCellValue("");
            row.createCell(24).setCellValue(studentDetails.getStudent().getGaurdianNumber());
            row.createCell(25).setCellValue(studentDetails.getStudent().getStudentStatus());
            ++i;
            HSSFRow headerRow6 = sheet.createRow(++i);
            headerRow6.createCell(4).setCellValue("Fee Configuration");
            CellRangeAddress cell6 = new CellRangeAddress(0, 2, 2, 4);
            sheet.addMergedRegion(cell6);

            HSSFRow headerRow7 = sheet.createRow(++i);
            headerRow7.createCell(1).setCellValue("Fee Name");
            headerRow7.createCell(2).setCellValue("Fee Amount");
            headerRow7.createCell(3).setCellValue("Discount");
            headerRow7.createCell(4).setCellValue("Payable");
            headerRow7.createCell(5).setCellValue("Installments ");
            headerRow7.createCell(6).setCellValue("Instalments Amount");
            headerRow7.createCell(7).setCellValue("Remarks");
            for (StudentFeeDetails list : studentFeeDetails) {
                if (list.getCheckboxstatus() == true) {
                    HSSFRow row2 = sheet.createRow(++i);
                    row2.createCell(1).setCellValue(list.getFeeTypeName());
                    row2.createCell(2).setCellValue(list.getFeeTypeAmount());
                    row2.createCell(3).setCellValue(list.getDiscount());
                    row2.createCell(4).setCellValue(list.getPayable());
                    row2.createCell(5).setCellValue(list.getNoOfInstallments());
                    row2.createCell(6).setCellValue(list.getInstallmentsAmount());
                    row2.createCell(7).setCellValue(list.getDiscountRemarks());
                }
            }
            HSSFRow headerRow8 = sheet.createRow(++i);
            headerRow8.createCell(0).setCellValue("");
            headerRow8.createCell(1).setCellValue("Total");
            headerRow8.createCell(2).setCellValue(studentDetails.getTotalFeeAmount());
            headerRow8.createCell(3).setCellValue(studentDetails.getTotalFeeAmount() - studentDetails.getTotalPayable());
            headerRow8.createCell(4).setCellValue(studentDetails.getTotalPayable());
            headerRow8.createCell(6).setCellValue(studentDetails.getTotalPayable());
            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }


    //downloadStudentExportToExcel

    @Transactional
    public void downloadStudentListExportToExcel(OutputStream out, List<Student> studentFeeAllDetails) {
        try {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
//            HSSFRow headerRow = sheet.createRow(0);
//            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
//            headerRow.createCell(2).setCellValue("Student Details");
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Academic Year");
            headerRow1.createCell(1).setCellValue("Date Of Admission");
            headerRow1.createCell(2).setCellValue("Admission Form No");
            headerRow1.createCell(3).setCellValue("Level");
            headerRow1.createCell(4).setCellValue("Joining Date ");
            headerRow1.createCell(5).setCellValue("Student Name ");

            headerRow1.createCell(6).setCellValue("Permanent Address");
            headerRow1.createCell(7).setCellValue("Date Of Birth ");
            headerRow1.createCell(8).setCellValue("Local/Present Address");
            headerRow1.createCell(9).setCellValue("Gender ");
            headerRow1.createCell(10).setCellValue("Physical Condition ");

            headerRow1.createCell(11).setCellValue("Aadhaar No");
            headerRow1.createCell(12).setCellValue("Religion");
            headerRow1.createCell(13).setCellValue("Father Name");
            headerRow1.createCell(14).setCellValue("Father Email Id ");
            headerRow1.createCell(15).setCellValue("Father Mobile");

            headerRow1.createCell(16).setCellValue("Father Occupation");
            headerRow1.createCell(17).setCellValue("Mother Name");
            headerRow1.createCell(18).setCellValue("Mother Email Id");
            headerRow1.createCell(19).setCellValue("Mother Mobile ");
            headerRow1.createCell(20).setCellValue("Mother Occupation ");

            headerRow1.createCell(21).setCellValue("Primary Contact No");
            headerRow1.createCell(22).setCellValue("Local Guardian Name");
            headerRow1.createCell(23).setCellValue("Parents Annual Income");
            headerRow1.createCell(24).setCellValue("Guardian Number ");
            headerRow1.createCell(25).setCellValue("Status");
            int i = 0;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            int j = 0;
            for (Student studentDetails : studentFeeAllDetails) {

                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(studentDetails.getAcademicYearMaster().getAcdyrName());
                if (studentDetails.getDateOfAdmission() != null) {
                row.createCell(1).setCellValue(dateFormat.format(studentDetails.getDateOfAdmission()));}
                row.createCell(2).setCellValue(studentDetails.getAdmissionFormNo());
                if (studentDetails.getLevel() != null) {
                row.createCell(3).setCellValue(studentDetails.getLevel().getGradeName());}
                if (studentDetails.getDateOfJoining() != null) {
                row.createCell(4).setCellValue(dateFormat.format(studentDetails.getDateOfJoining()));}
                if (studentDetails.getStudentName() != null) {
                row.createCell(5).setCellValue(studentDetails.getStudentName());}
                row.createCell(6).setCellValue(studentDetails.getPermanentAddress());
                if (studentDetails.getDateofbirth() != null) {
                row.createCell(7).setCellValue(dateFormat.format(studentDetails.getDateofbirth()));}
                row.createCell(8).setCellValue(studentDetails.getPresentAddress());
                row.createCell(9).setCellValue(studentDetails.getGender());
                row.createCell(10).setCellValue(studentDetails.getPhysicalCondition());

                row.createCell(11).setCellValue(studentDetails.getAadhaarNo());
                row.createCell(12).setCellValue(studentDetails.getReligion());
                row.createCell(13).setCellValue(studentDetails.getFatherName());
                row.createCell(14).setCellValue(studentDetails.getFatherEmailId());
                row.createCell(15).setCellValue(studentDetails.getFatherContactNo());

                row.createCell(16).setCellValue(studentDetails.getFatherOccupation());
                row.createCell(17).setCellValue(studentDetails.getMotherName());
                row.createCell(18).setCellValue(studentDetails.getMotherEmailId());
                row.createCell(19).setCellValue(studentDetails.getMotherContactNo());
                row.createCell(20).setCellValue(studentDetails.getMotherOccupation());

                row.createCell(21).setCellValue(studentDetails.getPrimaryContactNo());
                row.createCell(22).setCellValue(studentDetails.getGaurdianName());
                if (studentDetails.getAnnualIncome() != null) {
                    row.createCell(23).setCellValue(studentDetails.getAnnualIncome());
                }
                row.createCell(24).setCellValue(studentDetails.getGaurdianNumber());
                row.createCell(25).setCellValue(studentDetails.getStudentStatus());

            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    @Transactional
    public void downloadFeeListExcel(OutputStream out, List<StudentFee> studentFeeAllDetails) {
        try {
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");

            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Student Name");
            headerRow1.createCell(1).setCellValue("Student ProfileId");
            headerRow1.createCell(2).setCellValue("FeeType Name");
            headerRow1.createCell(3).setCellValue("Status");
            headerRow1.createCell(4).setCellValue("Fee Amount");
            headerRow1.createCell(5).setCellValue("Paying Fee");
            headerRow1.createCell(6).setCellValue("Payable");
            headerRow1.createCell(7).setCellValue("Discount");
            headerRow1.createCell(8).setCellValue("Value");
            headerRow1.createCell(9).setCellValue("Remark ");
            headerRow1.createCell(10).setCellValue("Installments");
            headerRow1.createCell(11).setCellValue("Installment1 Amount");
            headerRow1.createCell(12).setCellValue("Installment1 Date");
            headerRow1.createCell(13).setCellValue("Installment2 Amount");
            headerRow1.createCell(14).setCellValue("Installment2 Date");
            headerRow1.createCell(15).setCellValue("Installment3 Amount");
            headerRow1.createCell(16).setCellValue("Installment3 Date");
            headerRow1.createCell(17).setCellValue("Installment4 Amount");
            headerRow1.createCell(18).setCellValue("Installment4 Date");
            headerRow1.createCell(19).setCellValue("Installment5 Amount");
            headerRow1.createCell(20).setCellValue("Installment5 Date");
            headerRow1.createCell(21).setCellValue("Installment6 Amount");
            headerRow1.createCell(22).setCellValue("Installment6 Date");
            headerRow1.createCell(23).setCellValue("Installment7 Amount");
            headerRow1.createCell(24).setCellValue("Installment7 Date");
            headerRow1.createCell(25).setCellValue("Installment8 Amount");
            headerRow1.createCell(26).setCellValue("Installment8 Date");
            headerRow1.createCell(27).setCellValue("Installment9 Amount");
            headerRow1.createCell(28).setCellValue("Installment9 Date");
            headerRow1.createCell(29).setCellValue("Installment10 Amount");
            headerRow1.createCell(30).setCellValue("Installment10 Date");
            headerRow1.createCell(31).setCellValue("Installment11 Amount");
            headerRow1.createCell(32).setCellValue("Installment11 Date");
            headerRow1.createCell(33).setCellValue("Installment12 Amount");
            headerRow1.createCell(34).setCellValue("Installment12 Date");
            int i = 0;
            for (StudentFee studentDetails : studentFeeAllDetails) {
                List<StudentFeeDetails> feeDetailsList = bsStudentFeeDetailsRepository.findByStudentfee(studentDetails);
                for (StudentFeeDetails studentFeeDetails : feeDetailsList) {
                    HSSFRow row = sheet.createRow(++i);
                    row.createCell(0).setCellValue(studentDetails.getStudent().getStudentName());
                    row.createCell(1).setCellValue(studentDetails.getStudent().getStudentProfileId());
                    row.createCell(2).setCellValue(studentFeeDetails.getFeetypemaster().getFeeTypeName());
                    row.createCell(3).setCellValue(studentFeeDetails.getFeetypemaster().getStatus());
                    row.createCell(4).setCellValue(studentFeeDetails.getFeetypemaster().getFeeAmount());
                    row.createCell(5).setCellValue(studentFeeDetails.getFeetypemaster().getPayingFee());
                    row.createCell(6).setCellValue(studentFeeDetails.getPayable());
                    row.createCell(7).setCellValue(studentFeeDetails.getDiscount());
                    row.createCell(8).setCellValue(studentFeeDetails.getCheckboxstatus() == true ? "true" : "false");
                    row.createCell(9).setCellValue(studentFeeDetails.getDiscountRemarks());
                    row.createCell(10).setCellValue(studentFeeDetails.getNoOfInstallments());
                    List<Installments> installments = bsInstallmentsRepository.findByStudentFeeAndFeeTypeMaster(studentDetails, studentFeeDetails.getFeetypemaster());
                    int j = 11;
                    for (Installments installments1 : installments) {
                        Date date = installments1.getDueDate();
                        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        String dt = df.format(date);
                        row.createCell(j++).setCellValue(installments1.getInstallmentsAmount());
                        row.createCell(j++).setCellValue(dt);
                    }
                }
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }


    public Paragraph printSchoolDetails() {
        SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
        Paragraph preface2 = new Paragraph(schoolBranchDetails.getAddress());
        Paragraph preface1 = new Paragraph(schoolBranchDetails.getBranchName());
        Paragraph preface = new Paragraph("INTERNATIONAL HOLISTIC MONTESSORI & SCHOOL");
        preface.setAlignment(Element.ALIGN_CENTER);
        preface1.setAlignment(Element.ALIGN_CENTER);
        preface2.setAlignment(Element.ALIGN_CENTER);
        preface.add("\n");
        preface.add(preface1);
        preface.add(preface2);
        preface.add("\n");
        return preface;
    }
    public List<TermPojo> getTermsList(String search) {
        List<Term> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = termRepository.findAllByStatus("Active");
        } else {
            list = termRepository.findAllByTermName(search);
        }
        List<TermPojo> termPojos = BsUserMapper.mapTermEntityToPojo(list);
        return termPojos;
    }

    @Transactional
    public void downloadFeeCollectedReportExcel(OutputStream out, ReportPojo reportPojo) {
        try {
            List<FeeReceiptPojo> feeReceiptList = getReportCollected(reportPojo);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
            String address = "";
            if (schoolBranchDetails != null) {
                if (!StringUtils.isEmpty(schoolBranchDetails.getAddress())) {
                    address = schoolBranchDetails.getAddress();
                }
            }
            String branchName = "";
            if (schoolBranchDetails != null) {
                if (!StringUtils.isEmpty(schoolBranchDetails.getBranchName())) {
                    branchName = schoolBranchDetails.getBranchName();
                }
            }
            headerRow.createCell(2).setCellValue("INTERNATIONAL HOLISTIC MONTESSORI & SCHOOL" + "\n" + branchName + "\n" + address + "\n" + "Fee Collected Report");
            CellRangeAddress cell = new CellRangeAddress(0, 2, 2, 4);
            sheet.addMergedRegion(cell);
            RegionUtil.setBorderTop(2, cell, sheet, hwb);
            RegionUtil.setBorderLeft(2, cell, sheet, hwb);
            RegionUtil.setBorderRight(2, cell, sheet, hwb);
            RegionUtil.setBorderBottom(2, cell, sheet, hwb);
            HSSFRow headerRow5 = sheet.createRow(3);
            headerRow5.createCell(0).setCellValue("From Date");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            headerRow5.createCell(1).setCellValue(dateFormat.format(reportPojo.getFromDate()));
            headerRow5.createCell(2).setCellValue("To Date");
            headerRow5.createCell(3).setCellValue(dateFormat.format(reportPojo.getToDate()));
            HSSFRow headerRow6 = sheet.createRow(4);
            if (reportPojo.getAcademicYearMaster() != null) {
                headerRow6.createCell(0).setCellValue("Academic Year");
                headerRow6.createCell(1).setCellValue(reportPojo.getAcademicYearMaster().getAcdyrName());
            }
            if (reportPojo.getGradeMasters() != null) {
                HSSFRow headerRow7 = sheet.createRow(5);
                headerRow7.createCell(0).setCellValue("Grades");
                int col = 1;
                for (GradeMaster gradeMaster : reportPojo.getGradeMasters())
                    headerRow7.createCell(col++).setCellValue(gradeMaster.getGradeName());
            }
            HSSFRow headerRow1 = sheet.createRow(7);
            headerRow1.createCell(0).setCellValue("Name");
            headerRow1.createCell(1).setCellValue("Student Profile ID");
            headerRow1.createCell(2).setCellValue("Level");
            headerRow1.createCell(3).setCellValue("Cash");
            headerRow1.createCell(4).setCellValue("Bank");
            headerRow1.createCell(5).setCellValue("Card");
            headerRow1.createCell(6).setCellValue("Online");
            headerRow1.createCell(7).setCellValue("Total");
            int i = 7;
            double totalCashAmt = 0, totalBankAmt = 0, totalCardAmt = 0, totalOnlineAmt = 0, totalAmt = 0;
            for (FeeReceiptPojo list : feeReceiptList) {
                HSSFRow row = sheet.createRow(++i);
                totalCardAmt = totalCardAmt + list.getCardAmt();
                totalOnlineAmt = totalOnlineAmt + list.getOnlineAmt();
                totalCashAmt = totalCashAmt + list.getCashAmt();
                totalBankAmt = totalBankAmt + list.getBankAmt();
                totalAmt = totalAmt + list.getTotalAmt();
                row.createCell(0).setCellValue(list.getStudentName());
                row.createCell(1).setCellValue(list.getStudentProfileId());
                Student student = bsStudentRepository.findByStudentName(list.getStudentName());
                row.createCell(2).setCellValue(student.getLevel().getGradeName());
                row.createCell(3).setCellValue(list.getCashAmt());
                row.createCell(4).setCellValue(list.getBankAmt());
                row.createCell(5).setCellValue(list.getCardAmt());
                row.createCell(6).setCellValue(list.getOnlineAmt());
                row.createCell(7).setCellValue(list.getTotalAmt());
            }
            HSSFRow headerRow3 = sheet.createRow(++i);
            headerRow3.createCell(0).setCellValue("Total");
            headerRow3.createCell(1).setCellValue("");
            headerRow3.createCell(2).setCellValue("");
            headerRow3.createCell(3).setCellValue(totalCashAmt);
            headerRow3.createCell(4).setCellValue(totalBankAmt);
            headerRow3.createCell(5).setCellValue(totalCardAmt);
            headerRow3.createCell(6).setCellValue(totalOnlineAmt);
            headerRow3.createCell(7).setCellValue(totalAmt);
            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    @Transactional
    public void downloadassesmentsExcelExcel(OutputStream out,String subject,String level) {
        try {
            List<AssesmentSubmission> assesmentSubmissions = assesmentSubmissionRepository.findAllByLevelAndSubject(level,subject);
            List<AssesmentSubmissionDetailsExcel> assesmentSubmissionDetailsList=new ArrayList<>();
            for(AssesmentSubmission assesmentSubmission:assesmentSubmissions) {
                List<AssesmentSubmissionDetailsExcel> assesmentSubmissionDetails = assesmentSubmissionDetailsExcelRepository.findByAssesmentId(assesmentSubmission.getAsId());
                 assesmentSubmissionDetailsList.addAll(assesmentSubmissionDetails);
            }
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
            headerRow.createCell(2).setCellValue("Subject" + ":" + subject + "\n" + "Level" + ":" + level);
            List<LessonPlanPojo> resultList=new ArrayList<>();
            for(AssesmentSubmissionDetailsExcel assesmentSubmissionDetails:assesmentSubmissionDetailsList){
                AssesmentSubmission assesmentSubmission=assesmentSubmissionRepository.findOne(assesmentSubmissionDetails.getAssesmentId());
                SubComponent components=subComponentRepository.findOne(Long.parseLong(assesmentSubmission.getSubComponent()));
                Chapters chapters=chapterRepository.findOne(Long.parseLong(assesmentSubmission.getChapter()));
                LessonPlanPojo lessonPlanPojo=new LessonPlanPojo();
                lessonPlanPojo.setSubComponent(components.getSubComponentName());
                lessonPlanPojo.setChapter(chapters.getChapterName());
                lessonPlanPojo.setStudentName(assesmentSubmissionDetails.getStudentName());
                Gson gson=new Gson();
                Type type=new TypeToken<List<LessonPlanPojo>>(){}.getType();
                List<LessonPlanPojo> lessonPlanPojos=new ArrayList<>();
                lessonPlanPojos=gson.fromJson(assesmentSubmissionDetails.getTopicDetails(),type);
                Double totalMarks=0d,maxMarks=0d;
                for(LessonPlanPojo lessonPlanPojo1:lessonPlanPojos){
                    totalMarks=lessonPlanPojo1.getMarks()+totalMarks;
                    maxMarks=lessonPlanPojo1.getMaxMarks()+maxMarks;
                }
                lessonPlanPojo.setMarks(totalMarks);
                lessonPlanPojo.setMaxMarks(maxMarks);
                resultList.add(lessonPlanPojo);
            }
            List<LessonPlanPojo> result=new ArrayList<>();
            Map<LessonPlanPojo, List<LessonPlanPojo>> map1 =
                    resultList.stream().collect(groupingBy(p -> new LessonPlanPojo(p.getChapter(), p.getStudentName(),p.getSubComponent())));
            for(Map.Entry<LessonPlanPojo, List<LessonPlanPojo>> ls:map1.entrySet()){
                LessonPlanPojo lessonPlanPojo=new LessonPlanPojo();
                lessonPlanPojo.setSubComponent(ls.getKey().getSubComponent());
                lessonPlanPojo.setChapter(ls.getKey().getChapter());
                lessonPlanPojo.setStudentName(ls.getValue().get(0).getStudentName());
                Double totalMarks=0d,maxMarks=0d;
                for(LessonPlanPojo lessonPlanPojo1:ls.getValue()){
                    totalMarks=lessonPlanPojo1.getMarks()+totalMarks;
                    maxMarks=lessonPlanPojo1.getMaxMarks()+maxMarks;
                }
                lessonPlanPojo.setMarks(totalMarks);
                lessonPlanPojo.setMaxMarks(maxMarks);
                result.add(lessonPlanPojo);
            }
            Map<Pair<String, String>, List<LessonPlanPojo>> map =
                    result.parallelStream().collect(groupingBy(p -> Pair.of(p.getChapter(), p.getSubComponent())));
            List<LessonPlanPojo> lessonPlanPojos=new ArrayList<>();
            for(Map.Entry<Pair<String, String>, List<LessonPlanPojo>> m:map.entrySet()){
                LessonPlanPojo lessonPlanPojo=new LessonPlanPojo();
                lessonPlanPojo.setChapter(m.getKey().getFirst());
                lessonPlanPojo.setSubComponent(m.getKey().getSecond());
                SubComponent subComponent = subComponentRepository.findBySubComponentName(m.getKey().getSecond());
                Components components = componentRepository.findOne(subComponent.getComponentName());
                lessonPlanPojo.setComponent(components.getComponentName());
                lessonPlanPojos.add(lessonPlanPojo);
            }
            Map<String,List<AssesmentSubmissionDetailsExcel>> groupcomponent=assesmentSubmissionDetailsList.parallelStream().collect(groupingBy(o->o.getStudentName()));
            List<String> studentList=new ArrayList<>(groupcomponent.keySet());
            List<String> as=studentList.stream().sorted(Comparator.comparing(a->a)).collect(Collectors.toList());
            HSSFRow header = sheet.createRow(4);
            int col=3;
            int row=5;
            header.createCell(col++).setCellValue("Marks");
            for(String name:as) {
                header.createCell(col++).setCellValue(name);
            }
            Map<String,List<LessonPlanPojo>> lsp=lessonPlanPojos.parallelStream().collect(groupingBy(o->o.getComponent()));
            for(Map.Entry<String,List<LessonPlanPojo>> a:lsp.entrySet()) {
                for (LessonPlanPojo lp : a.getValue()) {
                    int col1 = 0;
                    header = sheet.createRow(row++);
                    header.createCell(col1++).setCellValue(lp.getChapter());
                    header.createCell(col1++).setCellValue(a.getKey());
                    header.createCell(col1++).setCellValue(lp.getSubComponent());
                    List<LessonPlanPojo> sortedstudents = map.get(Pair.of(lp.getChapter(), lp.getSubComponent())).stream()
                            .sorted(Comparator.comparing(LessonPlanPojo::getStudentName))
                            .collect(Collectors.toList());
                    header.createCell(col1++).setCellValue(sortedstudents.get(0).getMaxMarks());
                    for (LessonPlanPojo m1 : sortedstudents) {
                        header.createCell(col1++).setCellValue(m1.getMarks());
                    }
                }
                int co=3,c=2;
                header = sheet.createRow(row++);
                Components components = componentRepository.findByComponentName(a.getKey());
                List<AssesmentSubmission> ass = assesmentSubmissionRepository.findAllByLevelAndSubjectAndComponent(level,subject,components.getComponentId().toString());
                List<AssesmentSubmissionDetailsExcel> assesmentSubmissionList=new ArrayList<>();
                for(AssesmentSubmission assesmentSubmission:ass) {
                    List<AssesmentSubmissionDetailsExcel> assesmentSubmissionDetails = assesmentSubmissionDetailsExcelRepository.findByAssesmentId(assesmentSubmission.getAsId());
                    assesmentSubmissionList.addAll(assesmentSubmissionDetails);
                }
                List<LessonPlanPojo> res=new ArrayList<>();
                for(AssesmentSubmissionDetailsExcel assesmentSubmissionDetails:assesmentSubmissionList){
                    LessonPlanPojo lessonPlanPojo=new LessonPlanPojo();
                    lessonPlanPojo.setStudentName(assesmentSubmissionDetails.getStudentName());
                    Gson gson=new Gson();
                    Type type=new TypeToken<List<LessonPlanPojo>>(){}.getType();
                    List<LessonPlanPojo> lessonPlan=new ArrayList<>();
                    lessonPlan=gson.fromJson(assesmentSubmissionDetails.getTopicDetails(),type);
                    Double totalMarks=0d,maxmarks=0d;
                    for(LessonPlanPojo lesson:lessonPlan) {
                        totalMarks=lesson.getMarks()+totalMarks;
                        maxmarks=lesson.getMaxMarks()+maxmarks;
                    }
                    lessonPlanPojo.setMarks(totalMarks);
                    lessonPlanPojo.setMaxMarks(maxmarks);
                    res.add(lessonPlanPojo);
                }
                Map<String, Double> aa = res.stream()
                        .collect(groupingBy(LessonPlanPojo::getStudentName,
                                Collectors.summingDouble(LessonPlanPojo::getMarks)));
                Map<String, Double> maxMarks = res.stream()
                        .collect(groupingBy(LessonPlanPojo::getStudentName,
                                Collectors.summingDouble(LessonPlanPojo::getMaxMarks)));
                TreeMap<String, Double> sorted = new TreeMap<>(aa);
                header.createCell(co++).setCellValue("Total");
                for (Map.Entry<String, Double> m1 : sorted.entrySet()) {
                    header.createCell(co++).setCellValue(m1.getValue());
                }
                header = sheet.createRow(row++);
                header.createCell(c++).setCellValue("Grand Total");
                header.createCell(c++).setCellValue("");
                for (Map.Entry<String, Double> m1 : maxMarks.entrySet()) {
                    Double val=(sorted.get(m1.getKey()) / m1.getValue()) / (Double.parseDouble(components.getWeightage())/100);
                    header.createCell(c++).setCellValue(val.toString()+"%");
                }
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
//    @Transactional
//    public void downloadassesmentsExcelExcel(OutputStream out, String subject, String level) {
//        try {
//            List<AssesmentSubmission> assesmentSubmissionList = assesmentSubmissionRepository.findAllByLevelAndSubject(level, subject);
//            List<Map<String, String>> resultList = new ArrayList<>();
//            for (AssesmentSubmission assesmentSubmission : assesmentSubmissionList) {
//                Map<String, String> map = new HashMap<>();
//                map.put("chapterName", assesmentSubmission.getChapter());
//                map.put("componentName", assesmentSubmission.getComponent());
////            map.put("studentName", assesmentSubmission.getStudentsName());
//                resultList.add(map);
//            }
//            HSSFWorkbook hwb = new HSSFWorkbook();
//            CellStyle backgroundStyle = hwb.createCellStyle();
//
//            backgroundStyle.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
//            backgroundStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
//
//            backgroundStyle.setBorderBottom(CellStyle.BORDER_THIN);
//            backgroundStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//            backgroundStyle.setBorderLeft(CellStyle.BORDER_THIN);
//            backgroundStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//            backgroundStyle.setBorderRight(CellStyle.BORDER_THIN);
//            backgroundStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
//            backgroundStyle.setBorderTop(CellStyle.BORDER_THIN);
//            backgroundStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
//            HSSFSheet sheet = hwb.createSheet("First Sheet");
//            HSSFRow headerRow = sheet.createRow(0);
//            headerRow.setHeightInPoints((3 * sheet.getDefaultRowHeightInPoints()));
//            HSSFRow headerRow1 = sheet.createRow(1);
//            headerRow1.createCell(0).setCellValue("Student Name");
//            headerRow1.createCell(1).setCellValue("Chapter");
//            headerRow1.createCell(2).setCellValue("Component");
//            headerRow1.createCell(3).setCellValue("Topic");
//            headerRow1.createCell(4).setCellValue("Marks");
//            int i = 2;
//            for (Map<String, String> list : resultList) {
//                HSSFRow row = sheet.createRow(++i);
//                row.createCell(0).setCellValue(list.get("studentName"));
//                row.createCell(1).setCellValue(list.get("chapterName"));
//                row.createCell(2).setCellValue(list.get("componentName"));
//                Components components = componentRepository.findByComponentName(list.get("componentName"));
////                List<AssesmentSubmission> assesmentSubmissions = assesmentSubmissionRepository.findAllByStudentsNameAndChapterAndComponent(list.get("studentName"), list.get("chapterName"), list.get("componentName"));
//                List<LessonPlanPojo> lessonPlanPojos = new ArrayList<>();
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<LessonPlanPojo>>() {
//                }.getType();
////                for (AssesmentSubmission assesmentSubmission : assesmentSubmissions) {
////                lessonPlanPojos.addAll(gson.fromJson(assesmentSubmission.getAsList(),type));
////                }
//                double marks = 0, maxMarks = 0;
//                if (lessonPlanPojos.size() > 0) {
//                    row.createCell(3).setCellValue(lessonPlanPojos.get(0).getTopic());
//                    row.createCell(4).setCellValue(lessonPlanPojos.get(0).getMarks());
//                    marks = Double.parseDouble(lessonPlanPojos.get(0).getMarks());
//                    maxMarks = Double.parseDouble(lessonPlanPojos.get(0).getMaxMarks());
//                }
//                lessonPlanPojos.remove(0);
//                for (LessonPlanPojo lessonPlanPojo : lessonPlanPojos) {
//                    HSSFRow headerRow3 = sheet.createRow(++i);
//                    headerRow3.createCell(3).setCellValue(lessonPlanPojo.getTopic());
//                    headerRow3.createCell(4).setCellValue(lessonPlanPojo.getMarks());
//                    marks = Double.parseDouble(lessonPlanPojo.getMarks()) + marks;
//                    maxMarks = Double.parseDouble(lessonPlanPojo.getMaxMarks()) + maxMarks;
//                }
//                row = sheet.createRow(++i);
//                row.createCell(3).setCellValue("Total :");
//                row.createCell(4).setCellValue(marks);
//                row = sheet.createRow(++i);
//                row.createCell(3).setCellValue("Total Percentage:");
//                DecimalFormat df2 = new DecimalFormat("#");
//                row.createCell(4).setCellValue(df2.format((marks / maxMarks) * (Double.parseDouble(components.getWeightage()))) + "%");
//                row.setRowStyle(backgroundStyle);
//            }
//            hwb.write(out);
//            out.close();
//        } catch (IOException ioex) {
//            ioex.printStackTrace();
//        } catch (Exception gex) {
//            gex.printStackTrace();
//        }
//    }

    public void downloadFeeDueReportPdf(OutputStream outputStream, ReportPojo reportPojo) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFilterTable(reportPojo, "Fee Due Report");
            PdfPTable table1 = createFirstTableFeeDueReport(reportPojo);
            table.setHeaderRows(1);
            document.add(printSchoolDetails());
            document.add(table);
            document.add(table1);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PdfPTable createFilterTable(ReportPojo reportPojo, String type) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable tbl = new PdfPTable(a + 2);
        PdfPTable tbl2 = new PdfPTable(1);
        PdfPTable tbl3 = new PdfPTable(1);
        PdfPTable tbl4 = new PdfPTable(1);
        PdfPTable tab = new PdfPTable(1);
        Font f = new Font(getcustomfont(), 15, Font.BOLD, Color.WHITE);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase(type, f));
        p.setBackgroundColor(myColor);
        tab.addCell(p);
        PdfPCell p1 = new PdfPCell(new Phrase("From Date", font));
        p1.setBackgroundColor(myColor);
        PdfPCell p2 = new PdfPCell(new Phrase("To Date", font));
        p2.setBackgroundColor(myColor);
        tbl.addCell(p1);
        tbl.addCell(p2);
        if (reportPojo.getGradeIds() != null) {
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeIdIn(reportPojo.getGradeIds());
            reportPojo.setGradeMasters(gradeMaster);
        }
        if (!StringUtils.isEmpty(reportPojo.getAcademicYearId())) {
            reportPojo.setAcademicYearMaster(bsAcademicYearMasterRepository.findOne(Long.parseLong(reportPojo.getAcademicYearId())));
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tbl.addCell(new Phrase(dateFormat.format(reportPojo.getFromDate()) + "", font1));
        tbl.addCell(new Phrase(dateFormat.format(reportPojo.getToDate()) + "", font1));
        if (reportPojo.getAcademicYearMaster() != null) {
            PdfPCell p5 = new PdfPCell(new Phrase("Academic Year", font));
            p5.setBackgroundColor(myColor);
            tbl2.addCell(p5);
            tbl2.addCell(new Phrase(reportPojo.getAcademicYearMaster().getAcdyrName() + "", font1));
        }
        if (reportPojo.getGradeMasters() != null) {
            PdfPCell p6 = new PdfPCell(new Phrase("Grades", font));
            p6.setBackgroundColor(myColor);
            tbl3.addCell(p6);
            for (GradeMaster gradeMaster : reportPojo.getGradeMasters())
                tbl3.addCell(new Phrase(gradeMaster.getGradeName() + "", font1));
        }
        if (reportPojo.getFeeType() != null) {
            PdfPCell p7 = new PdfPCell(new Phrase("FeeType", font));
            p7.setBackgroundColor(myColor);
            tbl4.addCell(p7);
            tbl4.addCell(new Phrase(reportPojo.getFeeType() + "", font1));
        }
        tab.addCell(tbl);
        tab.addCell(tbl2);
        tab.addCell(tbl3);
        tab.addCell(tbl4);
        return tab;
    }

    public PdfPTable createFirstTableFeeDueReport(ReportPojo reportPojo) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        Font f1 = new Font(getcustomfont(), 8, Font.BOLD, Color.BLACK);
        PdfPTable table = new PdfPTable(a + 7);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell pc2 = new PdfPCell(new Phrase("Student Profile ID", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Name", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("Level", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Fee Amount", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("Discount", font));
        pc6.setBackgroundColor(myColor);
        PdfPCell pc7 = new PdfPCell(new Phrase("Paid", font));
        pc7.setBackgroundColor(myColor);
        PdfPCell pc8 = new PdfPCell(new Phrase("Due", font));
        pc8.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        table.addCell(pc7);
        table.addCell(pc8);
        List<StudentFeePojo> studentDueList = getStudentDueList(reportPojo);
        double totalAmountPaid = 0, totalBalance = 0, totalFeeAmt = 0, totalDiscount = 0;
        for (StudentFeePojo list : studentDueList) {
            if (list.getDueAmount() > 0) {
                totalFeeAmt = totalFeeAmt + list.getTotalFeeAmount();
                totalDiscount = totalDiscount + (list.getTotalFeeAmount() - list.getTotalPayable());
                totalAmountPaid = totalAmountPaid + list.getPaidAmount();
                totalBalance = totalBalance + list.getDueAmount();
                table.addCell(new Phrase(list.getStudent().getStudentProfileId() + "", font1));
                table.addCell(new Phrase(list.getStudent().getStudentName() + "", font1));
                table.addCell(new Phrase(list.getStudent().getLevel().getGradeName()+ "", font1));
                table.addCell(new Phrase(list.getTotalFeeAmount() + "", font1));
                table.addCell(new Phrase(list.getTotalFeeAmount() - list.getTotalPayable() + "", font1));
                table.addCell(new Phrase(list.getPaidAmount() + "", font1));
                table.addCell(new Phrase(list.getDueAmount() + "", font1));
                reportPojo.setStudentId(list.getStudent().getStudentId());
                List<FeeTypeMasterPojo> feeTypeMasterPojoList = getReportDetails(reportPojo);
                if (feeTypeMasterPojoList.size() > 0) {
                    table.addCell(new Phrase("", font1));
                    table.addCell(new Phrase("", font1));
                    table.addCell(new Phrase("", font1));
                    table.addCell(new Phrase("Fee Type", f1));
                    table.addCell(new Phrase("Due Amount", f1));
                    table.addCell(new Phrase("Due Date", f1));
                    table.addCell(new Phrase("", font1));
                    for (FeeTypeMasterPojo feeTypeMasterPojo : feeTypeMasterPojoList) {
                        table.addCell(new Phrase("", font1));
                        table.addCell(new Phrase("", font1));
                        table.addCell(new Phrase("", font1));
                        table.addCell(new Phrase(feeTypeMasterPojo.getFeeTypeName() + "", font1));
                        table.addCell(new Phrase(feeTypeMasterPojo.getDueAmt() + "", font1));
                        table.addCell(new Phrase(feeTypeMasterPojo.getDueDate() + "", font1));
                        table.addCell(new Phrase("", font1));
                    }
                }
            }
        }
        table.addCell(new Phrase("Total" + "", font1));
        table.addCell(new Phrase("", font1));
        table.addCell(new Phrase(totalFeeAmt + "", font1));
        table.addCell(new Phrase(totalDiscount + "", font1));
        table.addCell(new Phrase(totalAmountPaid + "", font1));
        table.addCell(new Phrase(totalBalance + "", font1));
        return table;
    }

    //downloadStudentDetailsPdf
    public void downloadStudentDetailsPdf(OutputStream outputStream, StudentFee studentDetails, List<StudentFeeDetails> studentFeeDetails) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableStudentDetails(studentDetails, studentFeeDetails);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PdfPTable createFirstTableStudentDetails(StudentFee studentDetails, List<StudentFeeDetails> studentFeeDetails) throws ParseException {
        int a = 0;
        PdfPTable tab = new PdfPTable(1);
        PdfPTable tab1 = new PdfPTable(1);
        PdfPTable tab2 = new PdfPTable(1);
        PdfPTable tab3 = new PdfPTable(1);
        PdfPTable tab4 = new PdfPTable(1);
        Font f = new Font(getcustomfont(), 15, Font.BOLD, Color.BLACK);
        PdfPTable table1 = new PdfPTable(a + 2);
        PdfPTable table2 = new PdfPTable(a + 2);
        PdfPTable table3 = new PdfPTable(a + 2);
        PdfPTable table4 = new PdfPTable(a + 7);
        PdfPTable table5 = new PdfPTable(a + 2);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.BLACK);
        Font font2 = new Font(getcustomfont(), 12, Font.NORMAL, Color.BLACK);
        Color myColor = WebColors.getRGBColor("#FFFFFF");
        PdfPCell p = new PdfPCell(new Phrase("Student Details", f));
        p.setBackgroundColor(myColor);
        p.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tab.addCell(p);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        PdfPCell p1 = new PdfPCell(new Phrase("Office Use", font2));
        p1.setBackgroundColor(myColor);
        p1.setBorder(PdfPCell.NO_BORDER);
        p1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell pc2 = new PdfPCell(new Phrase("Academic Year :" + studentDetails.getStudent().getAcademicYearMaster().getAcdyrName(), font));
        pc2.setBackgroundColor(myColor);
        pc2.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc3 = new PdfPCell(new Phrase("Level :" + studentDetails.getStudent().getLevel().getGradeName(), font));
        pc3.setBackgroundColor(myColor);
        pc3.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc4 = new PdfPCell(new Phrase("Date Of Admission :" + dateFormat.format(studentDetails.getStudent().getDateOfAdmission()), font));
        pc4.setBackgroundColor(myColor);
        pc4.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc5 = new PdfPCell(new Phrase("Joining Date :" + dateFormat.format(studentDetails.getStudent().getDateOfJoining()), font));
        pc5.setBackgroundColor(myColor);
        pc5.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc6 = new PdfPCell(new Phrase("Admission Form No :" + studentDetails.getStudent().getAdmissionFormNo(), font));
        pc6.setBackgroundColor(myColor);
        pc6.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc7 = new PdfPCell(new Phrase("", font));
        pc7.setBackgroundColor(myColor);
        pc7.setBorder(PdfPCell.NO_BORDER);
        PdfPCell p2 = new PdfPCell(new Phrase("Student", font2));
        p2.setBackgroundColor(myColor);
        p2.setBorder(PdfPCell.NO_BORDER);
        p2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell pc8 = new PdfPCell(new Phrase("Student Name :" + studentDetails.getStudent().getStudentName(), font));
        pc8.setBackgroundColor(myColor);
        pc8.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc9 = new PdfPCell(new Phrase("Permanent Address :" + studentDetails.getStudent().getPermanentAddress(), font));
        pc9.setBackgroundColor(myColor);
        pc9.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc10 = new PdfPCell(new Phrase("Date Of Birth :" + dateFormat.format(studentDetails.getStudent().getDateofbirth()), font));
        pc10.setBackgroundColor(myColor);
        pc10.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc11 = new PdfPCell(new Phrase("Local/Present Address :" + studentDetails.getStudent().getPresentAddress(), font));
        pc11.setBackgroundColor(myColor);
        pc11.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc12 = new PdfPCell(new Phrase("Gender :" + studentDetails.getStudent().getGender(), font));
        pc12.setBackgroundColor(myColor);
        pc12.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc13 = new PdfPCell(new Phrase("Physical Condition :" + studentDetails.getStudent().getPhysicalCondition(), font));
        pc13.setBackgroundColor(myColor);
        pc13.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc14 = new PdfPCell(new Phrase("Aadhaar No :" + studentDetails.getStudent().getAadhaarNo(), font));
        pc14.setBackgroundColor(myColor);
        pc14.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc15 = new PdfPCell(new Phrase("Religion :" + studentDetails.getStudent().getReligion(), font));
        pc15.setBackgroundColor(myColor);
        pc15.setBorder(PdfPCell.NO_BORDER);

        PdfPCell p3 = new PdfPCell(new Phrase("Parents", font2));
        p3.setBackgroundColor(myColor);
        p3.setBorder(PdfPCell.NO_BORDER);
        p3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell pc16 = new PdfPCell(new Phrase("Father Name :" + studentDetails.getStudent().getFatherName(), font));
        pc16.setBackgroundColor(myColor);
        pc16.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc17 = new PdfPCell(new Phrase("Father Email Id :" + studentDetails.getStudent().getFatherEmailId(), font));
        pc17.setBackgroundColor(myColor);
        pc17.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc18 = new PdfPCell(new Phrase("Father Mobile :" + studentDetails.getStudent().getFatherContactNo(), font));
        pc18.setBackgroundColor(myColor);
        pc18.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc19 = new PdfPCell(new Phrase("Father Occupation :" + studentDetails.getStudent().getFatherOccupation(), font));
        pc19.setBackgroundColor(myColor);
        pc19.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc20 = new PdfPCell(new Phrase("Mother Name :" + studentDetails.getStudent().getMotherName(), font));
        pc20.setBackgroundColor(myColor);
        pc20.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc21 = new PdfPCell(new Phrase("Mother Email Id :" + studentDetails.getStudent().getMotherEmailId(), font));
        pc21.setBackgroundColor(myColor);
        pc21.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc22 = new PdfPCell(new Phrase("Mother Mobile :" + studentDetails.getStudent().getMotherContactNo(), font));
        pc22.setBackgroundColor(myColor);
        pc22.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc23 = new PdfPCell(new Phrase("Mother Occupation :" + studentDetails.getStudent().getMotherOccupation(), font));
        pc23.setBackgroundColor(myColor);
        pc23.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc24 = new PdfPCell(new Phrase("Primary Contact No :" + studentDetails.getStudent().getPrimaryContactNo(), font));
        pc24.setBackgroundColor(myColor);
        pc24.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc25 = new PdfPCell(new Phrase("Local Guardian Name :" + studentDetails.getStudent().getGaurdianName(), font));
        pc25.setBackgroundColor(myColor);
        pc25.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc26 = null;
        if (studentDetails.getStudent().getAnnualIncome() != null) {
            pc26 = new PdfPCell(new Phrase("Parents Annual Income :" + studentDetails.getStudent().getAnnualIncome(), font));
        } else {
            pc26 = new PdfPCell(new Phrase("Parents Annual Income :" + "", font));
        }
        pc26.setBackgroundColor(myColor);
        pc26.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc27 = new PdfPCell(new Phrase("Guardian Number :" + studentDetails.getStudent().getGaurdianNumber(), font));
        pc27.setBackgroundColor(myColor);
        pc27.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc28 = new PdfPCell(new Phrase("Status :" + studentDetails.getStudent().getStudentStatus(), font));
        pc28.setBackgroundColor(myColor);
        pc28.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc29 = new PdfPCell(new Phrase("", font));
        pc29.setBackgroundColor(myColor);
        pc29.setBorder(PdfPCell.NO_BORDER);

        PdfPCell p4 = new PdfPCell(new Phrase("Fee Configuration", font2));
        p4.setBackgroundColor(myColor);
        p4.setBorder(PdfPCell.NO_BORDER);
        p4.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        PdfPCell pc30 = new PdfPCell(new Phrase("Fee Name ", font2));
        pc30.setBackgroundColor(myColor);

        PdfPCell pc31 = new PdfPCell(new Phrase("Fee Amount", font2));
        pc31.setBackgroundColor(myColor);
        PdfPCell pc32 = new PdfPCell(new Phrase("Discount", font2));
        pc32.setBackgroundColor(myColor);
        PdfPCell pc33 = new PdfPCell(new Phrase("Payable", font2));
        pc33.setBackgroundColor(myColor);
        PdfPCell pc34 = new PdfPCell(new Phrase("Installments", font2));
        pc34.setBackgroundColor(myColor);
        PdfPCell pc35 = new PdfPCell(new Phrase("Instalments Amount ", font2));
        pc35.setBackgroundColor(myColor);
        PdfPCell pc36 = new PdfPCell(new Phrase("Remarks ", font2));
        pc36.setBackgroundColor(myColor);
        table1.addCell(pc2);
        table1.addCell(pc3);
        table1.addCell(pc4);
        table1.addCell(pc5);
        table1.addCell(pc6);
        table1.addCell(pc7);

        table2.addCell(pc8);
        table2.addCell(pc9);
        table2.addCell(pc10);
        table2.addCell(pc11);
        table2.addCell(pc12);
        table2.addCell(pc13);
        table2.addCell(pc14);
        table2.addCell(pc15);

        table3.addCell(pc16);
        table3.addCell(pc17);
        table3.addCell(pc18);
        table3.addCell(pc19);
        table3.addCell(pc20);
        table3.addCell(pc21);
        table3.addCell(pc22);
        table3.addCell(pc23);
        table3.addCell(pc24);
        table3.addCell(pc25);
        table3.addCell(pc26);
        table3.addCell(pc27);
        table3.addCell(pc28);
        table3.addCell(pc29);

        table4.addCell(pc30);
        table4.addCell(pc31);
        table4.addCell(pc32);
        table4.addCell(pc33);
        table4.addCell(pc34);
        table4.addCell(pc35);
        table4.addCell(pc36);
        tab1.addCell(p1);
        tab2.addCell(p2);
        tab3.addCell(p3);
        tab4.addCell(p4);
        for (StudentFeeDetails list : studentFeeDetails) {
            if (list.getCheckboxstatus() == true) {
                table4.addCell(new Phrase(list.getFeeTypeName() + "", font2));
                table4.addCell(new Phrase(list.getFeeTypeAmount() + "", font2));
                table4.addCell(new Phrase(list.getDiscount() + "", font2));
                table4.addCell(new Phrase(list.getPayable() + "", font2));
                table4.addCell(new Phrase(list.getNoOfInstallments() + "", font2));
                table4.addCell(new Phrase(list.getInstallmentsAmount() + "", font2));
                if (list.getDiscountRemarks() != null) {
                    table4.addCell(new Phrase(list.getDiscountRemarks() + "", font2));
                } else {
                    table4.addCell(new Phrase("", font2));
                }
            }
        }
        PdfPCell p5 = new PdfPCell(new Phrase("Fee Configuration", font2));
        p5.setBackgroundColor(myColor);
        p5.setBorder(PdfPCell.NO_BORDER);
        p5.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//        table5.addCell(new Phrase("Total", font2));
//        table5.addCell(new Phrase("", ""));
        PdfPCell pc37 = new PdfPCell(new Phrase("Total Amount :" + studentDetails.getTotalFeeAmount(), font));
        pc37.setBackgroundColor(myColor);
        pc37.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc38 = new PdfPCell(new Phrase("Total Discount :" + (studentDetails.getTotalFeeAmount() - studentDetails.getTotalPayable()), font));
        pc38.setBackgroundColor(myColor);
        pc38.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc39 = new PdfPCell(new Phrase("Total Payable :" + studentDetails.getTotalPayable(), font));
        pc39.setBackgroundColor(myColor);
        pc39.setBorder(PdfPCell.NO_BORDER);
        PdfPCell pc40 = new PdfPCell(new Phrase("Total Instalments Amount :" + studentDetails.getTotalPayable(), font));
        pc40.setBackgroundColor(myColor);
        pc40.setBorder(PdfPCell.NO_BORDER);
        table5.addCell(pc37);
        table5.addCell(pc38);
        table5.addCell(pc39);
        table5.addCell(pc40);
        tab.addCell(tab1);
        tab.addCell(table1);
        tab.addCell(tab2);
        tab.addCell(table2);
        tab.addCell(tab3);
        tab.addCell(table3);
        tab.addCell(tab4);
        tab.addCell(table4);
        tab.addCell(table5);
        return tab;
    }

    public void downloadFeeCollectedReportPdf(OutputStream outputStream, ReportPojo reportPojo) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFilterTable(reportPojo, "Fee Collected Report");
            PdfPTable table1 = createFirstTableFeeCollectedReport(reportPojo);
            table.setHeaderRows(1);
            document.add(printSchoolDetails());
            document.add(table);
            document.add(table1);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PdfPTable createFirstTableFeeCollectedReport(ReportPojo reportPojo) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 8);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell pc2 = new PdfPCell(new Phrase("Student Profile ID", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Name", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("Level", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Cash ", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("Bank", font));
        pc6.setBackgroundColor(myColor);
        PdfPCell pc7 = new PdfPCell(new Phrase("Card", font));
        pc7.setBackgroundColor(myColor);
        PdfPCell pc8 = new PdfPCell(new Phrase("Online", font));
        pc8.setBackgroundColor(myColor);
        PdfPCell pc9 = new PdfPCell(new Phrase("Total", font));
        pc9.setBackgroundColor(myColor);
        table.addCell(pc3);
        table.addCell(pc2);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        table.addCell(pc7);
        table.addCell(pc8);
        table.addCell(pc9);
        List<FeeReceiptPojo> feePojoList = getReportCollected(reportPojo);
        double totalCardAmt = 0, totalOnlineAmt = 0, totalCashAmt = 0, totalBankAmt = 0, totalAmt = 0;
        for (FeeReceiptPojo list : feePojoList) {
            totalCardAmt = totalCardAmt + list.getCardAmt();
            totalOnlineAmt = totalOnlineAmt + list.getOnlineAmt();
            totalCashAmt = totalCashAmt + list.getCashAmt();
            totalBankAmt = totalBankAmt + list.getBankAmt();
            totalAmt = totalAmt + list.getTotalAmt();
            table.addCell(new Phrase(list.getStudentName() + "", font1));
            table.addCell(new Phrase(list.getStudentProfileId() + "", font1));
            Student student = bsStudentRepository.findByStudentName(list.getStudentName());
            table.addCell(new Phrase(student.getLevel().getGradeName() + "", font1));
            table.addCell(new Phrase(list.getCashAmt() + "", font1));
            table.addCell(new Phrase(list.getBankAmt() + "", font1));
            table.addCell(new Phrase(list.getCardAmt() + "", font1));
            table.addCell(new Phrase(list.getOnlineAmt() + "", font1));
            table.addCell(new Phrase(list.getTotalAmt() + "", font1));
        }
        table.addCell(new Phrase("Total" + "", font1));
        table.addCell(new Phrase("", font1));
        table.addCell(new Phrase("", font1));
        table.addCell(new Phrase(totalCashAmt + "", font1));
        table.addCell(new Phrase(totalBankAmt + "", font1));
        table.addCell(new Phrase(totalCardAmt + "", font1));
        table.addCell(new Phrase(totalOnlineAmt + "", font1));
        table.addCell(new Phrase(totalAmt + "", font1));
        return table;
    }

    public static BaseFont getcustomfont() {
        return FontFactory.getFont("arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, Color.BLACK).getBaseFont();
    }

    public List<FeeReceiptPojo> getReceiptDetails(Long studentFeeId, String feeType) {
        List<FeeReceipt> list = bsFeeReceiptRepository.findByStudentFeeAndFeeType(bsStudentFeeRepository.findOne(studentFeeId), feeType);
        List<FeeReceiptPojo> feeReceiptPojos = new ArrayList<>();
        for (FeeReceipt feeReceipt : list) {
            FeeReceiptPojo feeReceiptPojo = new FeeReceiptPojo();
            feeReceiptPojo.setReceiptDate(feeReceipt.getReceiptDate());
            feeReceiptPojo.setReceiptId(feeReceipt.getFeeReceiptID());
            feeReceiptPojo.setPaidAmt(feeReceipt.getTotalReceived());
            feeReceiptPojo.setReceiptNo(feeReceipt.getReceiptNo());
            feeReceiptPojo.setChequeStatus(feeReceipt.getChequeStatus());
            feeReceiptPojo.setBankAmt(feeReceipt.getBankAmt());
            feeReceiptPojo.setBankName(feeReceipt.getBankName());
            feeReceiptPojo.setTransactionNo(feeReceipt.getChequeNo());
            feeReceiptPojos.add(feeReceiptPojo);
        }
        return feeReceiptPojos;
    }

    public StudentFee getStudentDetails(Long id) {
        List<Student> student = bsStudentRepository.findByStudentId(id);
        StudentFee feeReceipt = bsStudentFeeRepository.findByStudent(student.get(0));
        return feeReceipt;
    }

    public List<StudentFeeDetails> getStudentFeeDetails(StudentFee studentDetails) {
        StudentFee feeReceipt = bsStudentFeeRepository.findByStudentFeeId(studentDetails.getStudentFeeId());
        List<StudentFeeDetails> studentFeeDetails = bsStudentFeeDetailsRepository.findByStudentfee(feeReceipt);

        return studentFeeDetails;
    }

    public List<StudentFee> getStudentFeeExportToExcel(String searchText, String grade, String student) {
        List<StudentFee> studList = new ArrayList<>();
        if (grade != "" && student == "") {
            GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(Long.valueOf(grade));
            studList = bsStudentFeeRepository.findByLevel(grdmstrobj);
        } else if (student != "" && grade == "") {
            studList = bsStudentFeeRepository.findAllByStudentFeeId(Long.valueOf(student));
        } else if (student != "" && grade != "") {
            studList = bsStudentFeeRepository.findAllByStudentFeeId(Long.valueOf(student));
        } else if (!StringUtils.isEmpty(searchText)) {
            studList = bsStudentFeeRepository.findStudentFeeByStudentNameIsLike("%" + searchText + "%");
        } else {
            studList = (List<StudentFee>) bsStudentFeeRepository.findAll();
        }
        return studList;
    }

    public List<Student> getStudentExportToExcelList(String searchText, String grade, String student, String checkboxStatusForStudent) {
        List<Student> studList = new ArrayList<>();
        if (grade != "" && student == "") {
            GradeMaster grdmstrobj = bsGrademasterRepository.findByGradeId(Long.valueOf(grade));
            studList = bsStudentRepository.findByLevel(grdmstrobj);
        } else if (student != "" && grade == "") {
            studList = bsStudentRepository.findByStudentId(Long.valueOf(student));
        } else if (student != "" && grade != "") {
            studList = bsStudentRepository.findByStudentId(Long.valueOf(student));
        } else if (student == "" && grade == "" && searchText == "" && checkboxStatusForStudent.equals("false")) {
            String status = "Active";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        } else if (student == "" && grade == "" && searchText == "" && checkboxStatusForStudent.equals("true")) {
            String status = "InActive";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        } else if (!StringUtils.isEmpty(searchText)) {
            studList = bsStudentRepository.findStudentByStudentNameIsLike("%" + searchText + "%");
        } else {
            String status = "Active";
            studList = (List<Student>) bsStudentRepository.findByStudentStatus(status);
        }
        return studList;
    }


    public FeeReceiptPojo getDuplicateReceipt(Long id) {
        FeeReceipt feeReceipt = bsFeeReceiptRepository.findOne(id);
        List<FeeReceiptDetails> feeReceiptDetails = bsFeeReceiptDetailsRepository.findByFeeReceipt(feeReceipt);
        StudentPojo studentPojo = new StudentPojo();
        Student student = feeReceipt.getStudentFee().getStudent();
        studentPojo.setStudentName(student.getStudentName());
        studentPojo.setFatherName(student.getFatherName());
        studentPojo.setAdmissionFormNo(student.getAdmissionFormNo());
        studentPojo.setAcademicYear(student.getAcademicYearMaster().getAcdyrName());
        studentPojo.setGradeName(student.getLevel().getGradeName());
        studentPojo.setReceiptNo(feeReceipt.getReceiptNo());
        studentPojo.setStudentProfileId(student.getStudentProfileId());
        studentPojo.setMotherName(student.getMotherName());
        studentPojo.setStudentType(student.getStudentType());
        FeeReceiptPojo feeReceiptPojo = new FeeReceiptPojo();
        feeReceiptPojo.setStudentPojo(studentPojo);
        feeReceiptPojo.setPaymentType(feeReceipt.getPaymentMode());
        feeReceiptPojo.setReceiptDate(feeReceipt.getReceiptDate());
        feeReceiptPojo.setCardNo(feeReceipt.getCardNo());
        feeReceiptPojo.setApprovalCode(feeReceipt.getApprovalCode());
        feeReceiptPojo.setTransactionNo(feeReceipt.getChequeNo());
        feeReceiptPojo.setApprovalCode(feeReceipt.getApprovalCode());
        feeReceiptPojo.setChequeDate(feeReceipt.getChequeDate());
        feeReceiptPojo.setBankName(feeReceipt.getBankName());
        feeReceiptPojo.setStudentFee(feeReceipt.getStudentFee());
        List<FeeTypeMasterPojo> feeTypeMasterPojoList = new ArrayList<>();
        if (StringUtils.isEmpty(feeReceipt.getChequeStatus()) || StringUtils.equalsIgnoreCase(feeReceipt.getChequeStatus(), "Clear")) {
            if (StringUtils.equalsIgnoreCase(feeReceipt.getPaymentMode(), "Bank")) {
                if (StringUtils.equalsIgnoreCase(feeReceipt.getChequeStatus(), "Clear")) {
                    feeReceiptPojo.setBankAmt(feeReceipt.getBankAmt() + feeReceiptPojo.getBankAmt());
                    feeReceiptPojo.setTotalPaid(feeReceipt.getTotalReceived() + feeReceiptPojo.getTotalPaid());
                }
            } else if (StringUtils.equalsIgnoreCase(feeReceipt.getPaymentMode(), "Online")) {
                feeReceiptPojo.setOnlineAmt(feeReceipt.getBankAmt() + feeReceiptPojo.getOnlineAmt());
                feeReceiptPojo.setTotalPaid(feeReceipt.getTotalReceived() + feeReceiptPojo.getTotalPaid());
            } else {
                feeReceiptPojo.setTotalPaid(feeReceipt.getTotalReceived() + feeReceiptPojo.getTotalPaid());
                feeReceiptPojo.setCardAmt(feeReceiptPojo.getCardAmt() + feeReceipt.getCardAmt());
                feeReceiptPojo.setCashAmt(feeReceiptPojo.getCashAmt() + feeReceipt.getCashAmt());
            }
            for (FeeReceiptDetails feeReceiptDetails1 : feeReceiptDetails) {
                FeeTypeMaster feeTypeMaster = bsFeeTypeMasterRepository.findByFeeTypeName(feeReceiptDetails1.getFeeType());
                StudentFeeDetails studentFeeDetails = bsStudentFeeDetailsRepository.findByFeetypemasterAndStudentfee(feeTypeMaster, bsStudentFeeRepository.findByStudent(student));
                FeeTypeMasterPojo feeTypeMasterPojo = new FeeTypeMasterPojo();
                feeTypeMasterPojo.setFeeTypeName(feeReceiptDetails1.getFeeType());
                feeTypeMasterPojo.setReceiptDate(feeReceipt.getReceiptDate());
                feeTypeMasterPojo.setReceiptNo(feeReceipt.getReceiptNo());
                feeTypeMasterPojo.setPayable(feeReceiptDetails1.getTotalReceived());
                feeTypeMasterPojo.setCheckBox(true);
                feeReceiptPojo.setTotalAmt(feeReceiptPojo.getTotalAmt() + studentFeeDetails.getFeeTypeAmount());
                feeReceiptPojo.setTotalDiscount(feeReceiptPojo.getTotalDiscount() + studentFeeDetails.getDiscount());
                feeReceiptPojo.setTotalPayable(feeReceiptPojo.getTotalPayable() + studentFeeDetails.getPayable());
                feeReceiptPojo.setTotalDue(feeReceiptPojo.getTotalPayable() - feeReceiptPojo.getTotalPaid());
                if (feeTypeMaster != null) {
                    feeTypeMasterPojoList.add(feeTypeMasterPojo);
                }
            }
        }
        feeReceiptPojo.setFeeTypeMasterPojoList(feeTypeMasterPojoList);
        return feeReceiptPojo;
    }

    public FeeReceiptPojo getAllPrintReceipt(Long id, String feeType) {
        StudentFee studentFee = bsStudentFeeRepository.findOne(id);
        StudentPojo studentPojo = new StudentPojo();
        Student student = studentFee.getStudent();
        List<FeeTypeMasterPojo> feeTypeMasterPojoList = new ArrayList<>();
        studentPojo.setStudentName(student.getStudentName());
        studentPojo.setFatherName(student.getFatherName());
        studentPojo.setAdmissionFormNo(student.getAdmissionFormNo());
        studentPojo.setAcademicYear(student.getAcademicYearMaster().getAcdyrName());
        studentPojo.setGradeName(student.getLevel().getGradeName());
        studentPojo.setStudentProfileId(student.getStudentProfileId());
        studentPojo.setMotherName(student.getMotherName());
        studentPojo.setStudentType(student.getStudentType());
        FeeReceiptPojo feeReceiptPojo = new FeeReceiptPojo();
        feeReceiptPojo.setStudentPojo(studentPojo);
        feeReceiptPojo.setStudentFee(studentFee);
        List<FeeReceipt> receiptList = bsFeeReceiptRepository.findByStudentFeeAndFeeType(studentFee, feeType);
        for (FeeReceipt feeReceipt : receiptList) {
            if (StringUtils.isEmpty(feeReceipt.getChequeStatus()) || StringUtils.equalsIgnoreCase(feeReceipt.getChequeStatus(), "Clear")) {
                if (StringUtils.equalsIgnoreCase(feeReceipt.getPaymentMode(), "Bank")) {
                    if (StringUtils.equalsIgnoreCase(feeReceipt.getChequeStatus(), "Clear")) {
                        feeReceiptPojo.setBankAmt(feeReceipt.getBankAmt() + feeReceiptPojo.getBankAmt());
                        feeReceiptPojo.setTotalPaid(feeReceipt.getTotalReceived() + feeReceiptPojo.getTotalPaid());
                    }
                } else if (StringUtils.equalsIgnoreCase(feeReceipt.getPaymentMode(), "Online")) {
                    feeReceiptPojo.setOnlineAmt(feeReceipt.getBankAmt() + feeReceiptPojo.getOnlineAmt());
                    feeReceiptPojo.setTotalPaid(feeReceipt.getTotalReceived() + feeReceiptPojo.getTotalPaid());
                } else {
                    feeReceiptPojo.setTotalPaid(feeReceipt.getTotalReceived() + feeReceiptPojo.getTotalPaid());
                    feeReceiptPojo.setCardAmt(feeReceiptPojo.getCardAmt() + feeReceipt.getCardAmt());
                    feeReceiptPojo.setCashAmt(feeReceiptPojo.getCashAmt() + feeReceipt.getCashAmt());
                }
                feeReceiptPojo.setTotalAmt(0);
                feeReceiptPojo.setTotalDiscount(0);
                feeReceiptPojo.setTotalPayable(0);
                feeReceiptPojo.setTotalDue(0);
                List<FeeReceiptDetails> feeReceiptDetails = bsFeeReceiptDetailsRepository.findByFeeReceipt(feeReceipt);
                for (FeeReceiptDetails feeReceiptDetails1 : feeReceiptDetails) {
                    FeeTypeMaster feeTypeMaster = bsFeeTypeMasterRepository.findByFeeTypeNameAndType(feeReceiptDetails1.getFeeType(), feeType);
                    StudentFeeDetails studentFeeDetails = bsStudentFeeDetailsRepository.findByFeetypemasterAndStudentfee(feeTypeMaster, studentFee);
                    FeeTypeMasterPojo feeTypeMasterPojo = new FeeTypeMasterPojo();
                    feeTypeMasterPojo.setFeeTypeName(feeReceiptDetails1.getFeeType());
                    feeTypeMasterPojo.setReceiptDate(feeReceipt.getReceiptDate());
                    feeTypeMasterPojo.setReceiptNo(feeReceipt.getReceiptNo());
                    feeTypeMasterPojo.setPayable(feeReceiptDetails1.getTotalReceived());
                    feeTypeMasterPojo.setCheckBox(true);
                    feeReceiptPojo.setTotalAmt(studentFeeDetails.getPaidAmt() + feeReceiptPojo.getTotalAmt());
                    feeReceiptPojo.setTotalDiscount(studentFeeDetails.getDiscount() + feeReceiptPojo.getTotalDiscount());
                    feeReceiptPojo.setTotalPayable(studentFeeDetails.getPayable() + feeReceiptPojo.getTotalPayable());
                    feeReceiptPojo.setTotalDue(studentFeeDetails.getPendingFee() + feeReceiptPojo.getTotalDue());
                    if (feeTypeMaster != null) {
                        feeTypeMasterPojoList.add(feeTypeMasterPojo);
                    }
                }
            }
        }
        feeReceiptPojo.setFeeTypeMasterPojoList(feeTypeMasterPojoList);
        return feeReceiptPojo;
    }

    public List<FeeReceiptPojo> receiptNoReport(ReportPojo reportPojo) {
        if (reportPojo.getGradeIds() != null) {
            List<GradeMaster> gradeMaster = bsGrademasterRepository.findAllByGradeIdIn(reportPojo.getGradeIds());
            reportPojo.setGradeMasters(gradeMaster);
        }
        if (!StringUtils.isEmpty(reportPojo.getAcademicYearId())) {
            reportPojo.setAcademicYearMaster(bsAcademicYearMasterRepository.findOne(Long.parseLong(reportPojo.getAcademicYearId())));
        }
        List<FeeReceiptPojo> feeReceiptPojoArrayList = new ArrayList<>();
        List<FeeReceipt> list = retrieveFeeCollected(reportPojo);
        if (list != null) {
            for (FeeReceipt receipt : list) {
                FeeReceiptPojo feeReceiptPojo = new FeeReceiptPojo();
                feeReceiptPojo.setStudentName(receipt.getStudentFee().getStudentName());
                feeReceiptPojo.setStudentProfileId(receipt.getStudentFee().getStudent().getStudentProfileId());
                feeReceiptPojo.setReceiptId(receipt.getFeeReceiptID());
                feeReceiptPojo.setReceiptNo(receipt.getReceiptNo());
                feeReceiptPojo.setReceiptDate(receipt.getReceiptDate());
                feeReceiptPojo.setPaidAmt(receipt.getTotalReceived());
                feeReceiptPojoArrayList.add(feeReceiptPojo);
            }
        }
        return feeReceiptPojoArrayList;
    }

    public StudentFeeDto saveStudentFeeDetails(StudentFeeDto studentFeeDto) throws JSONException, IOException {
        StudentFee studentFee = bsStudentFeeRepository.findByStudentFeeId(studentFeeDto.getStudentFeeId());
        if (studentFee != null) {
            FeeReceipt feeReceipt = null;
            if (studentFeeDto.getReceiptId() == null) {
                feeReceipt = new FeeReceipt();
            } else {
                feeReceipt = bsFeeReceiptRepository.findOne(studentFeeDto.getReceiptId());
                List<FeeReceiptDetails> feeReceiptDetails = bsFeeReceiptDetailsRepository.findByFeeReceipt(feeReceipt);
                bsFeeReceiptDetailsRepository.delete(feeReceiptDetails);
            }
            studentFee.setPaymentDate(studentFeeDto.getPaymentDate());
            studentFee.setPaymentType(studentFeeDto.getPaymentType());
            if (studentFee.getPaidAmount() < studentFee.getTotalPayable()) {
                if (StringUtils.equalsIgnoreCase(studentFeeDto.getPaymentType(), "Cash")) {
                    if (studentFee.getCashAmt() == null) {
                        studentFee.setCashAmt(0.0);
                    }
                    studentFee.setPaidAmount(studentFeeDto.getPayingFee() + studentFee.getPaidAmount());
                    studentFee.setDueAmount(studentFee.getTotalPayable() - studentFee.getPaidAmount());
                    studentFee.setCashAmt(studentFeeDto.getCashAmt() + studentFee.getCashAmt());
                    feeReceipt.setCashAmt(studentFeeDto.getCashAmt());
                } else if (StringUtils.equalsIgnoreCase(studentFeeDto.getPaymentType(), "Card")) {
                    if (studentFee.getCardAmt() == null) {
                        studentFee.setCardAmt(0.0);
                    }
                    studentFee.setPaidAmount(studentFeeDto.getPayingFee() + studentFee.getPaidAmount());
                    studentFee.setDueAmount(studentFee.getTotalPayable() - studentFee.getPaidAmount());
                    studentFee.setCardDetails(studentFeeDto.getCardDetails());
                    studentFee.setCardAmt(studentFeeDto.getCardAmt() + studentFee.getCardAmt());
                    feeReceipt.setCardAmt(studentFeeDto.getCardAmt());
                    if (StringUtils.isNotEmpty(studentFeeDto.getCardNo())) {
                        String cardNo = StringUtils.overlay(studentFeeDto.getCardNo(), StringUtils.repeat("X", studentFeeDto.getCardNo().length() - 4), 0, studentFeeDto.getCardNo().length() - 4);
                        feeReceipt.setCardNo(cardNo);
                        studentFeeDto.setCardNo(cardNo);
                    }
                    feeReceipt.setApprovalCode(studentFeeDto.getApprovalCode());
                } else if (StringUtils.equalsIgnoreCase(studentFeeDto.getPaymentType(), "Bank") || StringUtils.equalsIgnoreCase(studentFeeDto.getPaymentType(), "Online")) {
                    studentFee.setBankDetails(studentFeeDto.getBankDetails());
                    feeReceipt.setBankName(studentFeeDto.getBankName());
                    feeReceipt.setChequeNo(studentFeeDto.getChequeNo());
                    feeReceipt.setChequeDate(studentFeeDto.getChequeDate());
                    feeReceipt.setChequeStatus(studentFeeDto.getChequeStatus());
                    if (StringUtils.equalsIgnoreCase(feeReceipt.getChequeStatus(), "Clear")) {
                        if (studentFee.getBankAmt() == null) {
                            studentFee.setBankAmt(0.0);
                        }
                        studentFee.setPaidAmount(studentFeeDto.getPayingFee() + studentFee.getPaidAmount());
                        studentFee.setDueAmount(studentFee.getTotalPayable() - studentFee.getPaidAmount());
                        studentFee.setBankAmt(studentFeeDto.getBankAmt() + studentFee.getBankAmt());
                    } else {
                        studentFee.setBankAmt(0.0);
                    }
                    feeReceipt.setBankAmt(studentFeeDto.getBankAmt());
                }
                bsStudentFeeRepository.save(studentFee);
                if (studentFee.getDueAmount() == 0) {
                    List<SchedulerData> schedulerDataList = bsSchedulerRepository.findAllByStudent(studentFee.getStudent().getStudentId().toString());
                    bsSchedulerRepository.delete(schedulerDataList);
                }
                feeReceipt.setTotalPayable(studentFee.getTotalPayable());
                feeReceipt.setStudentFee(studentFee);
                feeReceipt.setReceiptDate(studentFee.getPaymentDate());
                feeReceipt.setTotalReceived(studentFeeDto.getPayingFee());
                feeReceipt.setPaymentMode(studentFee.getPaymentType());
                SchoolBranchDetails schoolBranchDetails = bsSchoolBranchDetailsRepository.findAll().get(0);
                schoolBranchDetails.setReceiptNo(schoolBranchDetails.getReceiptNo() + 1);
                bsSchoolBranchDetailsRepository.save(schoolBranchDetails);
                feeReceipt.setReceiptNo(String.valueOf(schoolBranchDetails.getReceiptNo()));
                for (StudentFeeDetailsPojo studentFeeDetailsPojo : studentFeeDto.getStudentFeeDetailsPojoList()) {
                    double amt = 0.00;
                    StudentFeeDetails studentFeeDetails = bsStudentFeeDetailsRepository.findOne(studentFeeDetailsPojo.getStudentFeeDetailsId());
                    feeReceipt.setFeeType(studentFeeDetails.getFeetypemaster().getType());
                    for (InstallmentsPojo installmentsPojo : studentFeeDetailsPojo.getInstallmentsPojos()) {
                        Installments installments = bsInstallmentsRepository.findOne(installmentsPojo.getInstallmentsId());
                        if (StringUtils.isEmpty(studentFeeDto.getChequeStatus()) || StringUtils.equalsIgnoreCase(studentFeeDto.getChequeStatus(), "Clear")) {
                            installments.setPaidAmt(installments.getPaidAmt() + installmentsPojo.getPayingAmt());
                            if (installments.getInstallmentsAmount() != null)
                                if (installments.getInstallmentsAmount() == installments.getPaidAmt()) {
                                    installments.setStatus("paid");
                                    bsSchedulerRepository.delete(bsSchedulerRepository.findAllByInstallmentsId(installments.getInstallmentsId().toString()));
                                }
                            studentFeeDetails.setPendingFee(studentFeeDetails.getPendingFee() - installmentsPojo.getPayingAmt());
                            if (studentFeeDetails.getPaidAmt() != null)
                                studentFeeDetails.setPaidAmt(installmentsPojo.getPayingAmt() + studentFeeDetails.getPaidAmt());
                            else
                                studentFeeDetails.setPaidAmt(installmentsPojo.getPayingAmt());
                            amt += installmentsPojo.getPayingAmt();
                        }
                        bsInstallmentsRepository.save(installments);
                    }
                    if (amt > 0) {
                        createGeneralLedgerForGTPurchase(studentFeeDetails.getFeetypemaster().getFeeTypeName(), studentFee.getPaymentDate(), amt, feeReceipt.getReceiptNo(), studentFeeDetails.getFeetypemaster().getAccountMaster());
                    }
                    studentFeeDetailsPojo.setPayingAmount(amt);
                    if (studentFeeDetails.getPaidAmt() == studentFeeDetails.getFeeTypeAmount())
                        studentFeeDetails.setStatus("paid");
                    bsStudentFeeDetailsRepository.save(studentFeeDetails);
                    bsFeeReceiptRepository.save(feeReceipt);
                    if (studentFeeDetailsPojo.isCheckBox() == true) {
                        FeeReceiptDetails feeReceiptDetails = new FeeReceiptDetails();
                        feeReceiptDetails.setFeeReceipt(feeReceipt);
                        feeReceiptDetails.setReceiptNo(feeReceipt.getReceiptNo());
                        feeReceiptDetails.setFeeType(studentFeeDetailsPojo.getFeeTypeName());
                        feeReceiptDetails.setTotalReceived(amt);
                        if (amt > 0)
                            bsFeeReceiptDetailsRepository.save(feeReceiptDetails);
                    }
                }
            }
        }
        Gson gson = new Gson();
//        TransactionPojo transactionPojo = TransactionMapper.feePaymentToTransaction(studentFeeDto);
//        String JsonInString = gson.toJson(transactionPojo);
        CartMaster cartMaster = CartMasterRepository.findOne(1l);
        String cartID = "";
        if(cartMaster!=null) {
            if (!cartMaster.equals(null)) cartID = cartMaster.getHiConnectCompanyRegNo();
//        String statusCode = pusherService.BroadCastMasterData(JsonInString, cartID, cartID, "CP", "sales");
            String JsonBrainyStudentString = gson.toJson(studentFeeDto);
            User user = bsUserRepository.findOne(1l);
            String branchCode = "";
            if (!user.equals(null)) branchCode = user.getBranchCode();
            String status = pusherService.BroadCastBrainyStarData(JsonBrainyStudentString, branchCode, branchCode, "FeePayment", "AddMaster");
            //String statusCode = pusherService.SavePusher(JsonInString, "", "SIN","Purchase");
        }
        return studentFeeDto;
    }

    public static String readDomainName() throws IOException {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = BsUserService.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(in);
            in.close();
        } catch (Exception e) {
        } finally {
            in.close();
        }
        return prop.getProperty("hisaas_domainname");
    }

    public String getHiConnectId(CartMasterPojo cartMasterPojo) throws IOException, JSONException {
        String url = readDomainName() + "/hiConnectService/getHiconnectNumber";//HiConnect Server URL
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("email", cartMasterPojo.getEmail());
        objectNode.put("username", cartMasterPojo.getUserName());
        objectNode.put("password", cartMasterPojo.getPassword());
        //  objectNode.put("type_flag", notificationType);
        //Spring Rest Client Call
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(objectNode.toString(), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        // LOGGER.info("Get All NotificationsList---->" + responseEntity);
        String uu = null;
        if (responseEntity.getStatusCode().is2xxSuccessful()) {

            String jsonString = responseEntity.getBody();
            JSONObject jsonObj = new JSONObject(jsonString);
            String detailObj = jsonObj.get("object").toString();
            JSONObject jsonObj1 = new JSONObject(detailObj);
            String detailObj1 = jsonObj1.get("hiConnectCompnyRegNo").toString();
            uu = detailObj1;

        }

        String jsonString = responseEntity.getBody();
        // JSONObject jsonObj = new JSONObject(jsonString);
        //  String detailObj = jsonObj.get("object").toString();


        return uu;

    }

    public CartMaster Cartmastersave(CartMasterPojo cartMasterPojo) throws IOException, JSONException {
        CartMaster master = CartMasterRepository.findOne(1l);
        String uu = getHiConnectId(cartMasterPojo);
        if (!uu.equals("null")) {
            master.setPassword(cartMasterPojo.getPassword());
            master.setEmail(cartMasterPojo.getEmail());
            master.setUserName(cartMasterPojo.getUserName());
            master.setHiConnectCompanyRegNo(uu);
            CartMasterRepository.save(master);
        }
        return master;
    }

    public List<CartMasterPojo> cartMasterList() {
        List<CartMaster> cartMasters = new ArrayList<>();
        cartMasters = (List<CartMaster>) CartMasterRepository.findAll();
        List<CartMasterPojo> PojoList = ObjectMapperUtils.mapAll(cartMasters, CartMasterPojo.class);
        return PojoList;
    }

    public void downloadFeeTypeExcel(OutputStream out, String searchText, String type) {
        try {
            List<FeeTypeMasterPojo> feeTypeMasterPojoList = feeTypeMasterList2(searchText, "true");
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("FeeType Name");
            headerRow1.createCell(1).setCellValue("Amount");
            headerRow1.createCell(2).setCellValue("Academic Year");
            headerRow1.createCell(3).setCellValue("Level Master");
            headerRow1.createCell(4).setCellValue("Status");
            int i = 0;
            for (FeeTypeMasterPojo pojo : feeTypeMasterPojoList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(pojo.getFeeTypeName());
                row.createCell(1).setCellValue(pojo.getFeeAmount());
                row.createCell(2).setCellValue(pojo.getAcdyrmaster().getAcdyrName());
                row.createCell(3).setCellValue(pojo.getLevel().getGradeName());
                row.createCell(4).setCellValue(pojo.getStatus());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    public void downloadGradeMasterExcel(OutputStream out, String searchText, String type, String userId) {
        try {
            List<GradeMasterPojo> gradeMasterPojoList = gradeMasterList2(searchText, type, userId);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Level Master Name");
            headerRow1.createCell(1).setCellValue("Description");
            int i = 0;
            for (GradeMasterPojo pojo : gradeMasterPojoList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(pojo.getGradeName());
                row.createCell(1).setCellValue(pojo.getGradeDescription());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    public void downloadAcademicYearExcel(OutputStream out, String searchText, String type) {
        try {
            List<AcademicYearMasterPojo> academicYearMasterPojoList = getAcademicYear2List(searchText, type);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("AcademicYear Name");
            headerRow1.createCell(1).setCellValue("Description");
            headerRow1.createCell(2).setCellValue("From Date(yyyy/MM/dd)");
            headerRow1.createCell(3).setCellValue("To Date(yyyy/MM/dd)");
            int i = 0;
            for (AcademicYearMasterPojo pojo : academicYearMasterPojoList) {
                HSSFRow row = sheet.createRow(++i);
                Date startDate = pojo.getFromDate();
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String recDate = df.format(startDate);
                Date endDate = pojo.getToDate();
                DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                String date = df1.format(endDate);
                row.createCell(0).setCellValue(pojo.getAcdyrName());
                row.createCell(1).setCellValue(pojo.getAcdyrDescription());
                row.createCell(2).setCellValue(recDate);
                row.createCell(3).setCellValue(date);
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }


    public BasePojo calculatePagination(BasePojo basePojo, int size) {
        if (basePojo.isLastPage() == true) {
            basePojo.setFirstPage(false);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(false);
        } else if (basePojo.isFirstPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(true);
            if (basePojo.isStatus() == true) {
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        } else if (basePojo.isNextPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setPrevPage(false);
            basePojo.setNextPage(false);
            if (basePojo.isStatus() == true) {
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        } else if (basePojo.isPrevPage() == true) {
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(false);
            if (basePojo.isStatus() == true) {
                basePojo.setPrevPage(true);
                basePojo.setFirstPage(true);
            }
        }
        if (size == 0) {
            basePojo.setLastPage(true);
            basePojo.setFirstPage(true);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(true);
        }
        return basePojo;
    }


    public Designation saveDes(DesignationPojo designationPojo) {
        Designation designation = new Designation();
        if (designationPojo.getDesignationId() != null) {
            designation = bsDesignationRepository.findByDesignationNameAndDesignationIdNotIn(designationPojo.getDesignationName(), designationPojo.getDesignationId());

        } else {
            designation = bsDesignationRepository.findByDesignationName(designationPojo.getDesignationName());
        }
        if (designation == null) {
            Designation designation1 = BsUserMapper.mapDesignationPojoToEntity(designationPojo);
            bsDesignationRepository.save(designation1);
            return designation1;
        } else {
            return null;
        }

    }

    public Assignment saveAssignment(AssignmentPojo assignmentPojo) {
        Assignment assignment = new Assignment();
        if (assignmentPojo.getAssignmentId() != null) {
            assignment = assignmentRepository.findByAssignmentNameAndAssignmentIdNotIn(assignmentPojo.getAssignmentName(), assignmentPojo.getAssignmentId());

        } else {
            assignment = assignmentRepository.findByAssignmentName(assignmentPojo.getAssignmentName());
        }
        if (assignment == null) {
            Assignment assignment1 = BsUserMapper.mapAssignmentPojoToEntity(assignmentPojo);
            assignmentRepository.save(assignment1);
            return assignment1;
        } else {
            return null;
        }

    }

    public LeaveFormMaster saveLeaveForm(LeaveFormDTO leaveFormDTO) {
        LeaveFormMaster leaveFormMaster = new LeaveFormMaster();
        if (leaveFormDTO.getLeaveFormId() != null) {
            leaveFormMaster = leaveFormRepository.findByNameAndLeaveFormIdNotIn(leaveFormDTO.getName(), leaveFormDTO.getLeaveFormId());

        } else {
            leaveFormMaster = leaveFormRepository.findByName(leaveFormDTO.getName());
        }
        if (leaveFormMaster == null) {
            LeaveFormMaster leaveFormMaster1 = BsLeaveFormMapper.mapLeaveFormPojoToEntity(leaveFormDTO);
            leaveFormRepository.save(leaveFormMaster1);
            return leaveFormMaster1;
        } else {
            return null;
        }

    }


    public OtherContacts saveContact(OtherContactsDTO otherContactsDTO) {
        OtherContacts otherContacts = new OtherContacts();
//        if (otherContactsDTO.getOtherContactId() != null) {
//            otherContacts = contactRepository.findByFullNameAndOtherContactIdNotIn(otherContactsDTO.getFullName(), otherContactsDTO.getOtherContactId());
//
//        }
//        else {
//            otherContacts = contactRepository.findByFullName(otherContactsDTO.getFullName());
//        }
//        if (otherContacts == null) {
        OtherContacts otherContacts1 = BsContactMapper.mapContactPojoToEntity(otherContactsDTO);
        contactRepository.save(otherContacts1);
        return otherContacts1;
    }
//        else {
//            return null;
//        }
//    }


    public Employeesar saveSarDetails(EmployeesarPojo employeesarPojo) {
        Employeesar employeesar = new Employeesar();
        if (employeesarPojo.getApprId() != null) {
            employeesar = employeesarRepository.findByStaffNameAndApprIdNotIn(employeesarPojo.getStaffName(), employeesarPojo.getApprId());

        } else {
            employeesar = employeesarRepository.findByStaffName(employeesarPojo.getStaffName());
        }
        if (employeesar == null) {
            Employeesar employeesar1 = BsEmployeesarMapper.mapEmployeesarPojoToEntity(employeesarPojo);
            employeesarRepository.save(employeesar1);
            return employeesar1;
        } else {
            return null;
        }

    }

    public Assessor saveAssessorDetails(AssessorPojo assessorPojo) {
        Assessor assessor = new Assessor();
        if (assessorPojo.getAssrId() != null) {
            assessor = assessorRepository.findByStaffNameAndAssrIdNotIn(assessorPojo.getStaffName(), assessorPojo.getAssrId());

        } else {
            assessor = assessorRepository.findByStaffName(assessorPojo.getStaffName());
        }
        if (assessor == null) {
            Assessor assessor1 = BsAssessorMapper.mapAssessorPojoToEntity(assessorPojo);
            assessorRepository.save(assessor1);
            return assessor1;
        } else {
            return null;
        }

    }

    public List<EmployeesarPojo> getSarApplicList(String status, String searchText) {
        List<Employeesar> list = new ArrayList<>();
        list = employeesarRepository.findAll();
        List<EmployeesarPojo> employeesarPojos = BsEmployeesarMapper.mapEmployeesarEntityToPojo(list);
        return employeesarPojos;
    }

    public List<AssessorPojo> getAssessorList(String status, String searchText) {
        List<Assessor> list = new ArrayList<>();
        list = assessorRepository.findAll();
        List<AssessorPojo> assessorPojos = BsAssessorMapper.mapAssessorEntityToPojo(list);
        return assessorPojos;
    }


    public List<DesignationPojo> getDesList(String status, String searchText) {
        List<Designation> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsDesignationRepository.findAllByDesignationStatus(status);
        } else {
            list = bsDesignationRepository.findByDesignationStatusAndDesignationNameContaining(status, searchText);
        }
        List<DesignationPojo> designationPojo = BsUserMapper.mapDesignationEntityToPojo(list);
        return designationPojo;
    }

    public List<LeaveFormDTO> getLeaveFormList(String status, String searchText) {
        List<LeaveFormMaster> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = leaveFormRepository.findAllByStatus(status);
        } else {
            list = leaveFormRepository.findAllByNameContainingAndStatus(status, searchText);
        }
        List<LeaveFormDTO> leaveFormDTOList = BsLeaveFormMapper.mapleaveFormEntityToPojo(list);
        return leaveFormDTOList;
    }

    public List<AssignmentPojo> getAssignmentList() {
        List<Assignment> list = new ArrayList<>();
        list = assignmentRepository.findAll();
        List<AssignmentPojo> assignmentPojoList = BsUserMapper.mapAssignmentEntityToPojo(list);
        return assignmentPojoList;
    }
// public List<GradeMasterPojo> getGradeList(String status, String searchText) {
//        List<GradeMaster> list = new ArrayList<>();
//        if (StringUtils.isEmpty(status)) {
//            status = "Active";
//        }
//        if (StrinassignmentPojoListgUtils.isEmpty(searchText)) {
//            list = bsGrademasterRepository.findAllByGradeStatus(status);
//        } else {
//            list = bsGrademasterRepository.findByGradeStatusAndGradeNameContaining(status, searchText);
//        }
//        List<GradeMasterPojo> gradeMasterPojos = BsUserMapper.mapGradeEntityToPojo(list);
//        return gradeMasterPojos;
//    }


    public void deleteDes(Long id) {
        bsDesignationRepository.delete(bsDesignationRepository.findByDesignationId(id));
    }

    public HrApplication addKIVModal(Long hrId) {
        HrApplication hrApplication = bsHrApplicRepository.findByHrId(hrId);
        return hrApplication;
    }

    public HrApplication addInterviewModal(Long hrId) {
        HrApplication hrApplication = bsHrApplicRepository.findByHrId(hrId);
        return hrApplication;
    }

    public HrApplication add2InterviewModal(Long hrId) {
        HrApplication hrApplication = bsHrApplicRepository.findByHrId(hrId);
        return hrApplication;
    }

    public Employee offerLetterInterviewModal(Long hrId) {
        HrApplication hrApplication = bsHrApplicRepository.findByHrId(hrId);
        Employee employee = bsEmployeeRepository.findByHrId(hrApplication.getHrId());
        return employee;
    }

    public HrApplicationPojo hrDetailsModal(Long hrId) {
        HrApplicationPojo hrApplicationPojo = new HrApplicationPojo();
        HrApplication hrApplication = bsHrApplicRepository.findByHrId(hrId);
        if (hrApplication != null) {
            Employee employee = bsEmployeeRepository.findByHrId(hrApplication.getHrId());
            if (employee != null) {
                hrApplicationPojo.setEmpBranch(employee.getEmpBranch());
                hrApplicationPojo.setEmpCode(employee.getEmployeeCode());
                hrApplicationPojo.setEmpUserName(employee.getUserName());
                hrApplicationPojo.setEmpUserType(employee.getUserType());
                hrApplicationPojo.setEmpDepartment(employee.getEmpDepartment());
                hrApplicationPojo.setTypeOfEmpl(employee.getTypeOfEmp());
                hrApplicationPojo.setEmployeeType(employee.getEmpType());
                hrApplicationPojo.setEmpCoordinator(employee.getEmpCoordinator());
                hrApplicationPojo.setEmpReportTo(employee.getReportTo());
                hrApplicationPojo.setEmpCapability(employee.getEmpCapability());
                hrApplicationPojo.setEmpSpeciality(employee.getSpeciality());
                hrApplicationPojo.setEmpAdharNo(employee.getAadharNo());
                hrApplicationPojo.setEmpExperience(employee.getExperience());
                hrApplicationPojo.setEmpPassword(employee.getPassword());
                hrApplicationPojo.setStatus(employee.getStatus());
                hrApplicationPojo.setEmpSalaryEffDate(employee.getSalEffectiveDate());
                hrApplicationPojo.setEmpearnings(employee.getEmpearnings());
                hrApplicationPojo.setEmpdeductions(employee.getEmpdeductions());
                hrApplicationPojo.setEmpBranch(employee.getEmpBranch());
            }
            hrApplicationPojo.setHrFullName(hrApplication.getHrFullName());
            hrApplicationPojo.setdOBText(hrApplication.getDOBText());
            hrApplicationPojo.setHrage(hrApplication.getHrage());
            hrApplicationPojo.setPositionText(hrApplication.getPositionText());
            hrApplicationPojo.setReportingDate(hrApplication.getReportingDate());
            hrApplicationPojo.setHrphoneNo(hrApplication.getHrphoneNo());
            hrApplicationPojo.setHrEmailAddress(hrApplication.getHrEmailAddress());
            hrApplicationPojo.setCity(hrApplication.getCity());
            hrApplicationPojo.setState(hrApplication.getState());
            hrApplicationPojo.setCountry(hrApplication.getCountry());
            hrApplicationPojo.setPassportNew(hrApplication.getPassportNew());
            hrApplicationPojo.setHrAddress1(hrApplication.getHrAddress1());
            hrApplicationPojo.setHrAddress2(hrApplication.getHrAddress2());
            hrApplicationPojo.setPostCode(hrApplication.getPostCode());
        }
        return hrApplicationPojo;
    }


    public EmployeePojo acceptanceDetails(Long hrId) {
        EmployeePojo employeePojo = new EmployeePojo();
        HrApplication hrApplication = bsHrApplicRepository.findByHrId(hrId);
        employeePojo.setEmployeeName(hrApplication.getHrFullName());
        employeePojo.setEmpDesignation(hrApplication.getPositionText());
        employeePojo.setBasicSal(hrApplication.getSalaryoffered());
        BankDetails bankDetails = bsBankDetailsRepository.findByHrId(hrApplication.getHrId());
        employeePojo.setHrId(hrId);
        if (bankDetails != null) {
            if (!StringUtils.isEmpty(bankDetails.getAcceptLetter())) {
                if (!bankDetails.getAcceptLetter().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + "IC" + bankDetails.getHrId() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    employeePojo.setAcceptLetter(imageLocation);
                }
            }
            employeePojo.setBankaccNo(bankDetails.getBankaccNo());
            employeePojo.setBankName(bankDetails.getBankName());
            employeePojo.setEpfaccNo(bankDetails.getEpfaccNo());
            employeePojo.setAddress(bankDetails.getAddress());
            employeePojo.setEmail(bankDetails.getEmail());
            employeePojo.setMobile(bankDetails.getMobile());
            employeePojo.setHome(bankDetails.getHome());
        }
        return employeePojo;
    }

    public void deleteEarning(Long id) {
        bsEarningsRepository.delete(bsEarningsRepository.findByEarningId(id));
    }

    public void deleteExitForm(Long id) {
        bsInterviewRepository.delete(bsInterviewRepository.findByEinterviewFormId(id));
    }

    public void DeletePayroll(Long id) {
        bsPayrollRepository.delete(bsPayrollRepository.findByEmpId(id));
    }


    public void deleteDeduction(Long id) {
        bsDeductionRepository.delete(bsDeductionRepository.findByDeductionId(id));
    }

    public Class saveClass(ClassPojo classPojo) {
        Class aClass = new Class();
//        if (classPojo.getClassId() != null) {
//            aClass = bsClassRepository.findByBranchIdAndLevelIdAndClassNameAndClassIdNotIn(classPojo.getBranchId(), classPojo.getLevelId(),classPojo.getClassName(),classPojo.getClassId());
//
//        } else {
//            aClass = bsClassRepository.findByBranchIdAndLevelIdAndClassName(classPojo.getBranchId(), classPojo.getLevelId(),classPojo.getClassName());
//        }

            Class aClass1 = BsUserMapper.mapClassPojoToEntity(classPojo);
            bsClassRepository.save(aClass1);
            return aClass1;
//         else {
//            return null;
//        }

    }


    public TimeTablePojo saveTimeTable(TimeTablePojo timeTablePojo) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Map<String,String>>>() {
        }.getType();
        List<Map<String,String>> menuPojos=gson.fromJson(timeTablePojo.getTtList(),listType);
        for(Map<String,String> list:menuPojos) {
            TimeTable timeTable = new TimeTable();
                timeTable.setTtId(timeTablePojo.getTtId());
                timeTable.setLevel(timeTablePojo.getLevel());
                timeTable.setClassName(timeTablePojo.getClassName());
                timeTable.setSubject(list.get("subject"));
                timeTable.setChooseDay(list.get("chooseDay"));
                timeTable.setPeriod(list.get("period"));
                timeTable.setFacility(list.get("facility"));
                timeTable.setTeacher(list.get("teacher"));
                timeTableRepository.save(timeTable);
            }
        return timeTablePojo;
    }



    public void deleteClass(Long id) {
        bsClassRepository.delete(bsClassRepository.findByClassId(id));
    }
    public void deleteAssessment(Long id) {
        AssessmentQuestions assessmentQuestions = bsAssessmentQuestionsRepository.findOne(id);
        List<AssessmentQuestionDetails> assessmentQuestionDetails = bsAssessmentQuestionDetailsRepository.findAllByAssessmentQuestions(assessmentQuestions);
        for (AssessmentQuestionDetails assessmentQuestionDetails1 : assessmentQuestionDetails) {
            bsAssessmentQuestionDetailsRepository.delete(assessmentQuestionDetails1.getAssessmentDetailsId());
        }
        bsAssessmentQuestionsRepository.delete(assessmentQuestions.getAssessmentQuestionsId());
    }
    public void deleteAssignment(Long id) {
        assignmentRepository.delete(assignmentRepository.findByAssignmentId(id));
    }


//    public SemesterPojo saveSemester(SemesterPojo semesterPojo) {
//        Semester semester = new Semester();
//        Gson gson = new Gson();
//        Type listType = new TypeToken<List<Long>>() {
//        }.getType();
//        List<Long> menuPojos=gson.fromJson(semesterPojo.getLevel(),listType);
//        Semester semester1 =new Semester();
//        for(Long list:menuPojos) {
//            semesterPojo.setLevel(list.toString());
//            semester = bsSemesterRepository.findBySemesterNameAndLevel(semesterPojo.getSemesterName(),semesterPojo.getLevel());
//            if (semester == null) {
//                semester1 = BsUserMapper.mapSemesterPojoToEntity(semesterPojo);
//                bsSemesterRepository.save(semester1);
//            }
//        }
//        return semesterPojo;
//    }

    public Semester saveSemester(SemesterPojo semesterPojo){
        Semester semester=new Semester();
        if (semesterPojo.getSemesterId() != null) {
            semester = bsSemesterRepository.findBySemesterNameAndSemesterIdNotIn(semesterPojo.getSemesterName(), semesterPojo.getSemesterId());
        } else {
            semester = bsSemesterRepository.findBySemesterName(semesterPojo.getSemesterName());
        }
        if (semester == null) {
            Semester semester1 = BsUserMapper.mapSemesterPojoToEntity(semesterPojo);
            bsSemesterRepository.save(semester1);
            return semester1;
        } else {
            return null;
        }

    }


    public Semester editSemester(SemesterPojo semesterPojo) {
        Semester semester = new Semester();
        semester = bsSemesterRepository.findBySemesterNameAndLevelAndSemesterIdNotIn(semesterPojo.getSemesterName(),semesterPojo.getLevel(), semesterPojo.getSemesterId());
        if (semester == null) {
            Semester semester1= BsUserMapper.mapSemesterPojoToEntity(semesterPojo);
            bsSemesterRepository.save(semester1);
            return semester1;
        } else {
            return null;
        }

    }

    public List<SemesterPojo> getSemesterList(String status, String searchText) {
        List<Semester> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsSemesterRepository.findAllBySemesterStatus(status);
        } else {
            list = bsSemesterRepository.findBySemesterStatusAndSemesterNameContaining(status, searchText);
        }
        List<SemesterPojo> semesterPojos = BsUserMapper.mapSemesterEntityToPojo(list);
        return semesterPojos;
    }

    public void deleteSemester(Long id) {
        bsSemesterRepository.delete(bsSemesterRepository.findBySemesterId(id));
    }

    public Remainders saveRemainders(RemaindersPojo remaindersPojo) {
        Remainders remainders = new Remainders();
        if (remaindersPojo.getRemainderId() != null) {
            remainders = bsRemaindersRepository.findByRemainderNameAndRemainderIdNotIn(remaindersPojo.getRemainderName(), remaindersPojo.getRemainderId());

        } else {
            remainders = bsRemaindersRepository.findByRemainderName(remaindersPojo.getRemainderName());
        }
        if (remainders == null) {
            Remainders remainders1 = BsUserMapper.mapRemaindersPojoToEntity(remaindersPojo);
            bsRemaindersRepository.save(remainders1);
            return remainders1;
        } else {
            return null;
        }

    }

    public Suppliers saveSuppliers(SuppliersPojo suppliersPojo) {
        Suppliers suppliers = new Suppliers();
        if (suppliersPojo.getSuppliersId() != null) {
            suppliers = bsSuppliersRepository.findBySuppliersNameAndSuppliersIdNotIn(suppliersPojo.getSuppliersName(), suppliersPojo.getSuppliersId());

        } else {
            suppliers = bsSuppliersRepository.findBySuppliersName(suppliersPojo.getSuppliersName());
        }
        if (suppliers == null) {
            Suppliers suppliers1 = BsUserMapper.mapSuppliersPojoToEntity(suppliersPojo);
            bsSuppliersRepository.save(suppliers1);
            return suppliers1;
        } else {
            return null;
        }

    }

    public List<RemaindersPojo> getRemaindersList(String status, String searchText) {
        List<Remainders> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsRemaindersRepository.findAllByRemainderStatus(status);
        } else {
            list = bsRemaindersRepository.findByRemainderStatusAndRemainderNameContaining(status, searchText);
        }
        List<RemaindersPojo> remaindersPojos = BsUserMapper.mapRemaindersEntityToPojo(list);
        return remaindersPojos;
    }


    public void deleteRemainder(Long id) {
        bsRemaindersRepository.delete(bsRemaindersRepository.findByRemainderId(id));
    }

    public List<SuppliersPojo> getSuppliersList(String status, String searchText) {
        List<Suppliers> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsSuppliersRepository.findAllBySuppliersStatus(status);
        } else {
            list = bsSuppliersRepository.findBySuppliersStatusAndSuppliersNameContaining(status, searchText);
        }
        List<SuppliersPojo> suppliersPojos = BsUserMapper.mapSuppliersEntityToPojo(list);
        return suppliersPojos;
    }

    public void deleteSuppliers(Long id) {
        bsSuppliersRepository.delete(bsSuppliersRepository.findBySuppliersId(id));
    }

//    public Country saveCountry(CountryDTO countryDTO) {
//        Country countries = new Country();
//        if (countryDTO.getCountryId() != null) {
//            countries = countryRepository.findByCountryNameAndCountryIdNotIn(countryDTO.getCountryName(), countryDTO.getCountryId());
//        } else {
//            countries = countryRepository.findByCountryName(countryDTO.getCountryName());
//        }
//        if (countries == null) {
//            Country country = BsUserMapper.mapCountryPojoToEntity(countryDTO);
//            countryRepository.save(country);
//            return country;
//        } else {
//            return null;
//        }
//
//    }
    public PeriodsMaster savePeriod(PeriodsDTO periodsDTO) {
        PeriodsMaster periodsMaster = new PeriodsMaster();
        if (periodsDTO.getPeriodId() != null) {
            periodsMaster = periodsRepository.findByPeriodNameAndPeriodIdNotIn(periodsDTO.getPeriodName(), periodsDTO.getPeriodId());
        } else {
            periodsMaster = periodsRepository.findByPeriodName(periodsDTO.getPeriodName());
        }
        if (periodsMaster == null) {
            PeriodsMaster periodsMaster1 = BsUserMapper.mapPeriodPojoToEntity(periodsDTO);
            periodsRepository.save(periodsMaster1);
            return periodsMaster1;
        } else {
            return null;
        }

    }

    public Position savePosition(PositionPojo positionPojo) {
        Position position = new Position();
//        if (positionPojo.getPositionId() != null) {
//            position = positionRepository.findByPositionNameAndPositionIdNotIn(positionPojo.getPositionName(), positionPojo.getPositionId());
//        } else {
//            position = positionRepository.findByPositionName(positionPojo.getPositionName());
//        }
//        if (position == null) {
        Position position1 = BsPositionMapper.mapPositionPojoToEntity(positionPojo);
        positionRepository.save(position1);
        return position1;
//        } else {
//            return null;
//        }

    }


    public Instruments saveInstruments(InstrumentsPojo instrumentsPojo) {
        Instruments instruments = new Instruments();
//        if (positionPojo.getPositionId() != null) {
//            position = positionRepository.findByPositionNameAndPositionIdNotIn(positionPojo.getPositionName(), positionPojo.getPositionId());
//        } else {
//            position = positionRepository.findByPositionName(positionPojo.getPositionName());
//        }
//        if (position == null) {
        Instruments instruments1 = BsInstrumentsMapper.mapInstrumentsPojoToEntity(instrumentsPojo);
        instrumentsRepository.save(instruments1);
        return instruments1;
//        } else {
//            return null;
//        }

    }


    public AssessmentType saveAssessmentType(AssessmentTypePojo assessmentTypePojo) {
        AssessmentType assessmentType = new AssessmentType();
//        if (positionPojo.getPositionId() != null) {
//            position = positionRepository.findByPositionNameAndPositionIdNotIn(positionPojo.getPositionName(), positionPojo.getPositionId());
//        } else {
//            position = positionRepository.findByPositionName(positionPojo.getPositionName());
//        }
//        if (position == null) {
        AssessmentType assessmentType1 = BsAssessmentTypeMapper.mapAssessmentTypePojoToEntity(assessmentTypePojo);
        assessmentTypeRepository.save(assessmentType1);
        return assessmentType1;
//        } else {
//            return null;
//        }

    }


    public SubComponent saveSubComponent(SubComponentPojo subComponentPojo) {
        SubComponent subComponent = new SubComponent();
//        if (positionPojo.getPositionId() != null) {
//            position = positionRepository.findByPositionNameAndPositionIdNotIn(positionPojo.getPositionName(), positionPojo.getPositionId());
//        } else {
//            position = positionRepository.findByPositionName(positionPojo.getPositionName());
//        }
//        if (position == null) {
        SubComponent subComponent1 = BsSubComponentMapper.mapSUbComponentPojoToEntity(subComponentPojo);
        subComponentRepository.save(subComponent1);
        return subComponent1;
//        } else {
//            return null;
//        }

    }

    public CustomApprover saveCustomApprover(CustomApproverPojo customApproverPojo) {
        CustomApprover customApprover = new CustomApprover();
//        if (positionPojo.getPositionId() != null) {
//            position = positionRepository.findByPositionNameAndPositionIdNotIn(positionPojo.getPositionName(), positionPojo.getPositionId());
//        } else {
//            position = positionRepository.findByPositionName(positionPojo.getPositionName());
//        }
//        if (position == null) {
        CustomApprover customApprover1 = BsCustomApproverMapper.mapCustomApproverPojoToEntity(customApproverPojo);
        customApproverRepository.save(customApprover1);
        return customApprover1;
//        } else {
//            return null;
//        }

    }

    public HrApplication saveKIVApplication(HrApplicationPojo hrApplicationPojo) {
        HrApplication hrApplication = new HrApplication();
        if (hrApplicationPojo.getHrId() != null) {
            hrApplication = bsHrApplicRepository.findOne(hrApplicationPojo.getHrId());
            hrApplication.setKivremarks(hrApplicationPojo.getKivremarks());
            bsHrApplicRepository.save(hrApplication);
            return hrApplication;
        } else {
            return null;
        }

    }

    public HrApplication savesecondInterviewDetails(HrApplicationPojo hrApplicationPojo) {
        HrApplication hrApplication = bsHrApplicRepository.findOne(hrApplicationPojo.getHrId());
        hrApplication.setNotes(hrApplicationPojo.getNotes());
        hrApplication.setSecondInteviewStatus(hrApplicationPojo.getSecondInteviewStatus());
        hrApplication.setSalaryoffered(hrApplicationPojo.getSalaryoffered());
        hrApplication.setReportingDate(hrApplicationPojo.getReportingDate());
        bsHrApplicRepository.save(hrApplication);
        return hrApplication;
    }


    public Country saveCountry(CountryDTO countryDTO){
        Country country=new Country();
        country=countryRepository.findByCountryName(countryDTO.getCountryName());
        countryRepository.save(country);
        return country;
    }


    public HrApplication saveInterviewschApplication(HrApplicationPojo hrApplicationPojo) {
        HrApplication hrApplication = new HrApplication();
        if (hrApplicationPojo.getHrId() != null) {
            hrApplication = bsHrApplicRepository.findOne(hrApplicationPojo.getHrId());
            hrApplication.setInterviewerSchedStatus(hrApplicationPojo.getInterviewerSchedStatus());
            hrApplication.setInterviewSchedNotes(hrApplicationPojo.getInterviewSchedNotes());
            hrApplication.setReportingDate(hrApplicationPojo.getReportingDate());
            hrApplication.setSalaryoffered(hrApplicationPojo.getSalaryoffered());
            bsHrApplicRepository.save(hrApplication);
        }

        return hrApplication;

    }

    public HrApplication saveInterviewApplication(HrApplicationPojo hrApplicationPojo) {
        HrApplication hrApplication = new HrApplication();
        if (hrApplicationPojo.getHrId() != null) {
            hrApplication = bsHrApplicRepository.findOne(hrApplicationPojo.getHrId());
            hrApplication.setInterviewDate(hrApplicationPojo.getInterviewDate());
            hrApplication.setInterviewerText(hrApplicationPojo.getInterviewerText());
            hrApplication.setInterviewNotes(hrApplicationPojo.getInterviewNotes());
            hrApplication.setInterviewerStatus("Schuleded");
            hrApplication.setInterviewTime(hrApplicationPojo.getInterviewtime());
            bsHrApplicRepository.save(hrApplication);
            return hrApplication;
        } else {
            return null;
        }

    }
    public IhesLessonPlan saveIhesEditMarks(IhesLessonPlanPojo ihesLessonPlanPojo) {
        IhesLessonPlan ihesLessonPlan = new IhesLessonPlan();
        if (ihesLessonPlanPojo.getIheslessonId() != null) {
            ihesLessonPlan = ihesLessonPlanRepository.findOne(ihesLessonPlanPojo.getIheslessonId());
            ihesLessonPlan.setInstructional(ihesLessonPlanPojo.getInstructional());
            ihesLessonPlan.setInvestigational(ihesLessonPlanPojo.getInvestigational());
            ihesLessonPlan.setImmersion(ihesLessonPlanPojo.getImmersion());
            ihesLessonPlanRepository.save(ihesLessonPlan);
            return ihesLessonPlan;
        } else {
            return null;
        }

    }

    public AccountType saveAccountType(AccountTypePojo accountTypePojo) {
        AccountType accountType = new AccountType();
        if (accountTypePojo.getAccountId() != null) {
            accountType = accountTypeRepository.findByAccountNameAndAccountIdNotIn(accountTypePojo.getAccountName(), accountTypePojo.getAccountId());
        } else {
            accountType = accountTypeRepository.findByAccountName(accountTypePojo.getAccountName());
        }
        if (accountType == null) {
            AccountType accountType1 = BsUserMapper.mapAccountTypePojoToEntity(accountTypePojo);
            accountTypeRepository.save(accountType1);
            return accountType1;
        } else {
            return null;
        }

    }

    public AccountGroup saveAccountGroup(AccountGroupPojo accountGroupPojo) {
        AccountGroup accountGroup = new AccountGroup();
        if (accountGroupPojo.getAccountId() != null) {
            accountGroup = accountGroupRepository.findByAccountNameAndAccountIdNotIn(accountGroupPojo.getAccountName(), accountGroupPojo.getAccountId());
        } else {
            accountGroup = accountGroupRepository.findByAccountName(accountGroupPojo.getAccountName());
        }
        if (accountGroup == null) {
            AccountGroup accountGroup1 = BsUserMapper.mapAccountGroupPojoToEntity(accountGroupPojo);
            accountGroupRepository.save(accountGroup1);
            return accountGroup1;
        } else {
            return null;
        }

    }

    public Student saveStudent(StudentDTO studentDTO) throws JSONException, ParseException, IOException {
        Student student = new Student();
        GradeMaster gradeMaster = new GradeMaster();
        AcademicYearMaster acdobj = new AcademicYearMaster();
        byte byteArray[];
        if (studentDTO.getStudentId() != null) {
            student = bsStudentRepository.findByStudentNameAndStudentIdNotIn(studentDTO.getStudentName(), studentDTO.getStudentId());
        } else {
            List<Student> studList = (List<Student>) bsStudentRepository.findAll();
            int studId = 0;
            for (Student stud : studList) {
                studId = Math.toIntExact(stud.getStudentId());
            }
            gradeMaster = bsGrademasterRepository.findByGradeId(studentDTO.getGradeId());
            String admissionNo = BsStudentMapper.generateStudentProfileNo(gradeMaster.getGradeName(), studentDTO.getDateofbirth(), studId);
            studentDTO.setStudentProfileId(admissionNo);
            student = bsStudentRepository.findByStudentName(studentDTO.getStudentName());
        }
        String fileName = FileSystemOperations.getImagesDirItem() + File.separator + "SP" + studentDTO.getStudentProfileId() + ".png";
        if (!StringUtils.isEmpty(studentDTO.getStudentPhoto())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(studentDTO.getStudentPhoto().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                studentDTO.setStudentPhoto(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName1 = FileSystemOperations.getImagesDirItem() + File.separator + "SBC" + studentDTO.getStudentProfileId() + ".png";
        if (!StringUtils.isEmpty(studentDTO.getStudBirthCer())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName1);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(studentDTO.getStudBirthCer().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                studentDTO.setStudBirthCer(fileName1);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        String fileName2 = FileSystemOperations.getImagesDirItem() + File.separator + "SIC" + studentDTO.getStudentProfileId() + ".png";
        if (!StringUtils.isEmpty(studentDTO.getStudIdentityCard())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName2);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(studentDTO.getStudIdentityCard().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                studentDTO.setStudIdentityCard(fileName2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName3 = FileSystemOperations.getImagesDirItem() + File.separator + "PIC" + studentDTO.getStudentProfileId() + ".png";
        if (!StringUtils.isEmpty(studentDTO.getParentIdentityCard())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName3);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(studentDTO.getParentIdentityCard().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                studentDTO.setParentIdentityCard(fileName3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName4 = FileSystemOperations.getImagesDirItem() + File.separator + "PMC" + studentDTO.getStudentProfileId() + ".png";
        if (!StringUtils.isEmpty(studentDTO.getParentMarrCer())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName4);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(studentDTO.getParentMarrCer().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                studentDTO.setParentMarrCer(fileName4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName5 = FileSystemOperations.getImagesDirItem() + File.separator + "PSC" + studentDTO.getStudentProfileId() + ".png";
        if (!StringUtils.isEmpty(studentDTO.getPrevSchoolCer())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName5);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(studentDTO.getPrevSchoolCer().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                studentDTO.setPrevSchoolCer(fileName5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName6 = FileSystemOperations.getImagesDirItem() + File.separator + "SOD" + studentDTO.getStudentProfileId() + ".png";
        if (!StringUtils.isEmpty(studentDTO.getStudOtherDoc())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName6);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(studentDTO.getStudOtherDoc().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                studentDTO.setStudOtherDoc(fileName6);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (student == null) {
            Student student1 = BsUserMapper.mapStudentPojoToEntity(studentDTO);
            gradeMaster = bsGrademasterRepository.findByGradeId(studentDTO.getGradeId());
            acdobj = bsAcademicYearMasterRepository.findByAcdyrId(studentDTO.getAcdYearId());
            student1.setAcademicYearMaster(acdobj);
            student1.setLevel(gradeMaster);
            if (studentDTO.getStudentId() != null) {
                Student stud = bsStudentRepository.findOne(studentDTO.getStudentId());
                if (StringUtils.isEmpty(studentDTO.getStudentPhoto())) {
                    student1.setStudentPhoto(stud.getStudentPhoto());
                }
                if (StringUtils.isEmpty(studentDTO.getStudBirthCer())) {
                    student1.setStudBirthCer(stud.getStudBirthCer());
                }
                if (StringUtils.isEmpty(studentDTO.getStudIdentityCard())) {
                    student1.setStudIdentityCard(stud.getStudIdentityCard());
                }
                if (StringUtils.isEmpty(studentDTO.getParentIdentityCard())) {
                    student1.setParentIdentityCard(stud.getParentIdentityCard());
                }
                if (StringUtils.isEmpty(studentDTO.getParentMarrCer())) {
                    student1.setParentMarrCer(stud.getParentMarrCer());
                }
                if (StringUtils.isEmpty(studentDTO.getPrevSchoolCer())) {
                    student1.setPrevSchoolCer(stud.getPrevSchoolCer());
                }
                if (StringUtils.isEmpty(studentDTO.getStudOtherDoc())) {
                    student1.setStudOtherDoc(stud.getStudOtherDoc());
                }
            }
            bsStudentRepository.save(student1);
            if (studentDTO.getFeeTypeMasterPojoList().size() > 0) {
                StudentPojo saveStudentDetails1 = new StudentPojo();
                saveStudentDetails1.setStudentProfileId(studentDTO.getStudentProfileId());
                saveStudentDetails1.setStudentName(studentDTO.getStudentName());
                saveStudentDetails1.setFeeTypeMasterPojoList(studentDTO.getFeeTypeMasterPojoList());
                SaveStudentfeee(saveStudentDetails1);
            }
            return student1;
        } else {
            return null;
        }

    }

    public HrApplicationPojo getSecondInterviewDetails(Long id) {
        HrApplication hrApplication = bsHrApplicRepository.findOne(id);
        HrApplicationPojo hrApplicationPojo = new HrApplicationPojo();
        hrApplicationPojo.setNotes(hrApplication.getNotes());
        hrApplicationPojo.setSecondInteviewStatus(hrApplication.getSecondInteviewStatus());
        hrApplicationPojo.setReportingDate(hrApplication.getReportingDate());
        hrApplicationPojo.setSalaryoffered(hrApplication.getSalaryoffered());
        return hrApplicationPojo;
    }

    public HrApplication saveHrApplication(HrApplicationPojo hrApplicationPojo) throws JSONException, ParseException, IOException {
        HrApplication hrApplication = new HrApplication();
        byte byteArray[];
        if (hrApplicationPojo.getHrId() != null) {
            hrApplication = bsHrApplicRepository.findByHrFullNameAndHrIdNotIn(hrApplicationPojo.getHrFullName(), hrApplicationPojo.getHrId());
        } else {
            List<HrApplication> hrApplicationList = (List<HrApplication>) bsHrApplicRepository.findAll();
            int studId = 0;
            for (HrApplication hrApplication1 : hrApplicationList) {
                studId = Math.toIntExact(hrApplication1.getHrId());
            }
            hrApplication = bsHrApplicRepository.findByHrFullName(hrApplicationPojo.getHrFullName());
        }

        String fileName = FileSystemOperations.getImagesDirItem() + File.separator + "IC" + hrApplicationPojo.getHrFullName() + ".png";
        if (!StringUtils.isEmpty(hrApplicationPojo.getIdentityCard())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(hrApplicationPojo.getIdentityCard().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                hrApplicationPojo.setIdentityCard(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName1 = FileSystemOperations.getImagesDirItem() + File.separator + "RES" + hrApplicationPojo.getHrFullName() + ".png";
        if (!StringUtils.isEmpty(hrApplicationPojo.getHrResume())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName1);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(hrApplicationPojo.getHrResume().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                hrApplicationPojo.setHrResume(fileName1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName2 = FileSystemOperations.getImagesDirItem() + File.separator + "RC" + hrApplicationPojo.getHrFullName() + ".png";
        if (!StringUtils.isEmpty(hrApplicationPojo.getRelevantcert())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName2);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(hrApplicationPojo.getRelevantcert().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                hrApplicationPojo.setRelevantcert(fileName2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName3 = FileSystemOperations.getImagesDirItem() + File.separator + "PC" + hrApplicationPojo.getHrFullName() + ".png";
        if (!StringUtils.isEmpty(hrApplicationPojo.getPhotocopy())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName3);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(hrApplicationPojo.getPhotocopy().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                hrApplicationPojo.setPhotocopy(fileName3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName4 = FileSystemOperations.getImagesDirItem() + File.separator + "TEC" + hrApplicationPojo.getHrFullName() + ".png";
        if (!StringUtils.isEmpty(hrApplicationPojo.getTertiaryeducerts())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName4);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(hrApplicationPojo.getTertiaryeducerts().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                hrApplicationPojo.setTertiaryeducerts(fileName4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName5 = FileSystemOperations.getImagesDirItem() + File.separator + "ORD" + hrApplicationPojo.getHrFullName() + ".png";
        if (!StringUtils.isEmpty(hrApplicationPojo.getOtherdocs())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName5);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(hrApplicationPojo.getOtherdocs().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                hrApplicationPojo.setOtherdocs(fileName5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (hrApplication == null) {
            HrApplication hrApplication1 = BsUserMapper.mapHrApplicPojoToEntity(hrApplicationPojo);
            if (hrApplicationPojo.getHrId() != null) {
                HrApplication hrApplication2 = bsHrApplicRepository.findOne(hrApplicationPojo.getHrId());
                if (StringUtils.isEmpty(hrApplicationPojo.getPhotocopy())) {
                    hrApplication1.setPhotocopy(hrApplication2.getPhotocopy());
                }
                if (StringUtils.isEmpty(hrApplicationPojo.getRelevantcert())) {
                    hrApplication1.setRelevantcert(hrApplication2.getRelevantcert());
                }
                if (StringUtils.isEmpty(hrApplicationPojo.getHrResume())) {
                    hrApplication1.setHrResume(hrApplication2.getHrResume());
                }
                if (StringUtils.isEmpty(hrApplicationPojo.getIdentityCard())) {
                    hrApplication1.setIdentityCard(hrApplication2.getIdentityCard());
                }
                if (StringUtils.isEmpty(hrApplicationPojo.getTertiaryeducerts())) {
                    hrApplication1.setTertiaryeducerts(hrApplication2.getTertiaryeducerts());
                }
                if (StringUtils.isEmpty(hrApplicationPojo.getOtherdocs())) {
                    hrApplication1.setOtherdocs(hrApplication2.getOtherdocs());
                }

            }
            bsHrApplicRepository.save(hrApplication1);
            return hrApplication1;
        } else {
            return null;
        }

    }

    public List<CountryDTO> getCountryList(String search) {
        List<Country> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = countryRepository.findAll();
        } else {
            list = countryRepository.findAllByCountryName(search);
        }
        List<CountryDTO> typePojos = BsUserMapper.mapCountryEntityToPojo(list);
        return typePojos;
    }

    public List<AccountTypePojo> getAccountTypeList(String search) {
        List<AccountType> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = accountTypeRepository.findAll();
        } else {
            list = accountTypeRepository.findAllByAccountName(search);
        }
        List<AccountTypePojo> typePojos = BsUserMapper.mapAccountTypeEntityToPojo(list);
        return typePojos;
    }

    public List<AccountGroupPojo> getAccountGroupList(String search) {
        List<AccountGroup> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = accountGroupRepository.findAll();
        } else {
            list = accountGroupRepository.findAllByAccountName(search);
        }
        List<AccountGroupPojo> typePojos = BsUserMapper.mapAccountGroupEntityToPojo(list);
        return typePojos;
    }

    public List<ListChatOfAccountDto> getAccountMasterList(String search) {
        List<AccountMaster> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = accountMasterRepository.findAll();
        } else {
            list = accountMasterRepository.findAllByAccountname(search);
        }
        List<ListChatOfAccountDto> typePojos = BsUserMapper.mapAccountMasterEntityToPojo(list);
        return typePojos;
    }


    public List<ListChatOfAccountDto> getAccountMasterListBasedOnReport(String search) {
        List<AccountMaster> list = new ArrayList<>();
        list = accountMasterRepository.findAllByReportvalue(search);
        List<ListChatOfAccountDto> typePojos = BsUserMapper.mapAccountMasterEntityToPojo(list);
        for (ListChatOfAccountDto accountMaster : typePojos) {
            double totalAmount = 0.0;
            AccountMaster accountMaster1 = accountMasterRepository.findOne(accountMaster.getAccountid());
            List<AccountMaster> accountMaster2 = accountMasterRepository.findAllByAmaccountid(accountMaster1);
            List<SecondChatOfAccountDto> secondLevelList = BsUserMapper.mapSecondAccountMasterEntityToPojo(accountMaster2);
            accountMaster.setSecondChatOfAccountDtoList(secondLevelList);
            for (SecondChatOfAccountDto secondChatOfAccountDto : secondLevelList) {
                double firstTotalAmt = 0.0;
                List<GLTransactions> glTransactions = glTransactionRepository.findAllByAccount(accountMasterRepository.findOne(secondChatOfAccountDto.getAccountid()));
                secondChatOfAccountDto.setGldetails(glTransactions);
                for (GLTransactions glTransactions1 : secondChatOfAccountDto.getGldetails()) {
                    firstTotalAmt = glTransactions1.getAmount().doubleValue() + firstTotalAmt;
                    secondChatOfAccountDto.setTotalAmt(firstTotalAmt);
                }
                List<AccountMaster> secondaccountMaster = accountMasterRepository.findAllByAmaccountid(accountMaster1);
                List<AccountMaster> secondaccountMasterList = accountMasterRepository.findAllByAmaccountid(secondaccountMaster.get(0));
                List<ThirdChatOfAccountDto> thirdLevelList = BsUserMapper.mapThirdAccountMasterEntityToPojo(secondaccountMasterList);
                accountMaster.setThirdChatOfAccountDtoList(thirdLevelList);
                for (ThirdChatOfAccountDto thirdChatOfAccountDto : thirdLevelList) {
                    double secondTotalAmt = 0.0;
                    List<GLTransactions> thirdGlTransactions = glTransactionRepository.findAllByAccount(accountMasterRepository.findOne(thirdChatOfAccountDto.getAccountid()));
                    thirdChatOfAccountDto.setGldetails(thirdGlTransactions);
                    for (GLTransactions glTransactions1 : thirdChatOfAccountDto.getGldetails()) {
                        secondTotalAmt = glTransactions1.getAmount().doubleValue() + secondTotalAmt;
                        thirdChatOfAccountDto.setTotalAmt(secondTotalAmt);
                    }
                }
            }
            List<GLTransactions> glTransactions = glTransactionRepository.findAllByAccount(accountMasterRepository.findOne(accountMaster.getAccountid()));
            accountMaster.setGldetails(glTransactions);
            for (SecondChatOfAccountDto glTransactions1 : accountMaster.getSecondChatOfAccountDtoList()) {
                totalAmount = glTransactions1.getTotalAmt() + totalAmount;
                accountMaster.setTotalAmt(totalAmount);
            }
            if (accountMaster.getThirdChatOfAccountDtoList() != null) {
                for (ThirdChatOfAccountDto thirdChatOfAccountDto : accountMaster.getThirdChatOfAccountDtoList()) {
                    totalAmount = thirdChatOfAccountDto.getTotalAmt() + totalAmount;
                    accountMaster.setTotalAmt(totalAmount);
                }
            }
            for (GLTransactions glTransactions1 : accountMaster.getGldetails()) {
                totalAmount = glTransactions1.getAmount().doubleValue() + totalAmount;
                accountMaster.setTotalAmt(totalAmount);
            }
        }
        return typePojos;
    }

    public SchoolBranchDetails getSchoolbranchDetails() {
        List<SchoolBranchDetails> company = schoolBranchDetailsRepository.findAll();
        SchoolBranchDetails schoolBranchDetails = schoolBranchDetailsRepository.findOne(company.get(0).getSchoolBranchId());
        return schoolBranchDetails;
    }

    @Transactional
    public List<ListChatOfAccountDto> saveCreateChartAcc(PostChartOfAccountDto accountDto) {
        List<ListChatOfAccountDto> list = null;
        AccountMaster accountMaster = null;
        AccountMaster parentAccountMaster = null;
        Integer autoGenerateSecoundLevelAccCode = 0;
        String substringOfAccCode;
        AccountMaster master = returnAccountMaster(accountDto);
        if (accountDto.getSecoundLevelStringAccCode() != null && accountDto.getFirstLevelStringAccCode() != null && !accountDto.getSecoundLevelStringAccCode().isEmpty() && !accountDto.getFirstLevelStringAccCode().isEmpty()) {
            substringOfAccCode = accountDto.getSecoundLevelStringAccCode().substring(0, accountDto.getSecoundLevelStringAccCode().lastIndexOf("/") + 1);
            accountMaster = maxAccountCodeValue(substringOfAccCode, "ThirdLevel");
            parentAccountMaster = getTopLevelAccountMaster(accountDto.getSecoundLevelAccountId());
            if (accountMaster != null) {
                autoGenerateSecoundLevelAccCode = 1 + Integer.parseInt(accountMaster.getAccountcode());
            } else {
                autoGenerateSecoundLevelAccCode = 0001;
            }
            master.setStringAccountCode(substringOfAccCode + String.format("%04d", autoGenerateSecoundLevelAccCode));
            master.setAmaccountid(parentAccountMaster);
            master.setAccountcode(String.valueOf(String.format("%04d", autoGenerateSecoundLevelAccCode)));
        } else if (accountDto.getFirstLevelStringAccCode() != null && !accountDto.getFirstLevelStringAccCode().isEmpty()) {
            substringOfAccCode = accountDto.getFirstLevelStringAccCode().substring(0, accountDto.getFirstLevelStringAccCode().indexOf("/") + 1);
            accountMaster = maxAccountCodeValue(substringOfAccCode, "SecoundLevel");
            parentAccountMaster = getTopLevelAccountMaster(accountDto.getFirstLevelAccountId());
            if (accountMaster != null) {
                autoGenerateSecoundLevelAccCode = 1 + Integer.parseInt(accountMaster.getAccountcode());
            } else {
                autoGenerateSecoundLevelAccCode = 001;
            }
            master.setStringAccountCode(substringOfAccCode + String.format("%03d", autoGenerateSecoundLevelAccCode) + "/0000");
            master.setAmaccountid(parentAccountMaster);
            master.setAccountcode(String.valueOf(String.format("%03d", autoGenerateSecoundLevelAccCode)));
        } else if (StringUtils.isEmpty(accountDto.getFirstLevelStringAccCode())) {
            AccountGroup accountGroup = accountGroupRepository.findOne(Long.parseLong(accountDto.getAccountGroup()));
            master.setStringAccountCode(accountGroup.getAccountCode() + "/000" + "/0000");
            master.setAmaccountid(parentAccountMaster);
            master.setAccountcode(accountGroup.getAccountCode());
        }
        AccountType accountType = accountTypeRepository.findOne(accountDto.getAccountTypeId());
        master.setReportvalue(accountDto.getReportvalue());
        master.setAccountTypeId(accountType);
        list = saveOrUpDateChartOfAccount(master);
        return list;

    }

    public List<ListChatOfAccountDto> saveOrUpDateChartOfAccount(AccountMaster accountMaster) {
        accountMasterRepository.save(accountMaster);
        return null;
    }

    public AccountMaster getTopLevelAccountMaster(String accountMasterId) {
        AccountMaster accountMaster = accountMasterRepository.findOne(Long.parseLong(accountMasterId));
        return accountMaster;
    }

    public AccountGroup getAccountGroup(Long accountGroupId) {
        AccountGroup accountGroup = accountGroupRepository.findOne(accountGroupId);
        return accountGroup;
    }

    public AccountMaster maxAccountCodeValue(String accountCode, String accountLevel) {
        List<AccountMaster> amList = new ArrayList<>();
        AccountMaster amObj = null;
        Query qry = null;
        try {
            switch (accountLevel) {
                case "ThirdLevel":
                    accountLevel.equals("ThirdLevel");
                    List<AccountMaster> list = accountMasterRepository.findAllBy(accountCode);
                    if (list.size() > 0) {
                        amObj = list.get(0);
                    }
                    break;
                case "SecoundLevel":
                    accountLevel.equals("SecoundLevel");
                    list = accountMasterRepository.findAllBy1(accountCode);
                    if (list.size() > 0) {
                        amObj = list.get(0);
                    }
                    break;
                case "FirstLevel":
                    accountLevel.equals("FirstLevel");
                    list = accountMasterRepository.findAllBy2(accountCode);
                    if (list.size() > 0) {
                        amObj = list.get(0);
                    }
                    break;
            }
//            amObj = accountMasterRepository.findAllBy(accountCode).get(0);
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return amObj;
    }

    public AccountMaster returnAccountMaster(PostChartOfAccountDto accountDto) {
        AccountMaster master = new AccountMaster();
        master.setAccountname(accountDto.getAccountName());
        master.setFlag(true);
        master.setStatus("Active");
        master.setAparcode(accountDto.getAccountType());
        master.setAgid(getAccountGroup(Long.parseLong(accountDto.getAccountGroup())));
        return master;
    }

    public List<AccountMaster> editAccountMAsterObj(Long accountId) {
        List<AccountMaster> createchartofacc = accountMasterRepository.findAllByAccountid(accountId);
        return createchartofacc;
    }

    @Transactional
    public AccountMaster editChartOfAccounts(PostChartOfAccountDto accountDto) {
        AccountMaster accountMaster = null;
        if (accountDto.getAccountid() != null) {
            accountMaster = accountMasterRepository.findOne(accountDto.getAccountid());
            accountMaster.setAccountname(accountDto.getAccountName());
            accountMaster.setAparcode(accountDto.getAccountType());
        }
        accountMasterRepository.save(accountMaster);
        return accountMaster;
    }

    @Transactional
    public AccountMaster editCheck(PostChartOfAccountDto accountDto) {
        AccountMaster accountMaster = null;
        if (accountDto.getAccountid() != null) {
            accountMaster = accountMasterRepository.findOne(accountDto.getAccountid());
            accountMaster.setAccountname(accountDto.getAccountName());
            accountMaster.setAparcode(accountDto.getAccountType());
        }
        accountMasterRepository.save(accountMaster);
        return accountMaster;
    }

    public List<StudentPojo> getAdmissionList(String search) {
        List<Student> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = bsStudentRepository.findAll();
        } else {
            list = bsStudentRepository.findAllByStudentName(search);
        }
        List<StudentPojo> typePojos = BsUserMapper.mapStudentEntityToPojo(list);
        if (typePojos.size() > 0) {
            for (StudentPojo studentPojo : typePojos) {
                if (!StringUtils.isEmpty(studentPojo.getStudentPhoto()))
                    if (!studentPojo.getStudentPhoto().equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + "SP" + studentPojo.getStudentProfileId() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        studentPojo.setStudentPhoto(imageLocation);
                    }
            }
            for (StudentPojo studentPojo : typePojos) {
                if (!StringUtils.isEmpty(studentPojo.getStudBirthCer()))
                    if (!studentPojo.getStudBirthCer().equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + "SBC" + studentPojo.getStudentProfileId() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        studentPojo.setStudBirthCer(imageLocation);
                    }
            }
            for (StudentPojo studentPojo : typePojos) {
                if (!StringUtils.isEmpty(studentPojo.getStudIdentityCard()))
                    if (!studentPojo.getStudIdentityCard().equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + "SIC" + studentPojo.getStudentProfileId() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        studentPojo.setStudIdentityCard(imageLocation);
                    }
            }
            for (StudentPojo studentPojo : typePojos) {
                if (!StringUtils.isEmpty(studentPojo.getParentIdentityCard()))
                    if (!studentPojo.getParentIdentityCard().equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + "PIC" + studentPojo.getStudentProfileId() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        studentPojo.setParentIdentityCard(imageLocation);
                    }
            }
            for (StudentPojo studentPojo : typePojos) {
                if (!StringUtils.isEmpty(studentPojo.getParentMarrCer()))
                    if (!studentPojo.getParentMarrCer().equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + "PMC" + studentPojo.getStudentProfileId() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        studentPojo.setParentMarrCer(imageLocation);
                    }
            }
            for (StudentPojo studentPojo : typePojos) {
                if (!StringUtils.isEmpty(studentPojo.getPrevSchoolCer()))
                    if (!studentPojo.getPrevSchoolCer().equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + "PSC" + studentPojo.getStudentProfileId() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        studentPojo.setPrevSchoolCer(imageLocation);
                    }
            }
            for (StudentPojo studentPojo : typePojos) {
                if (!StringUtils.isEmpty(studentPojo.getStudOtherDoc()))
                    if (!studentPojo.getStudOtherDoc().equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + "SOD" + studentPojo.getStudentProfileId() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        studentPojo.setStudOtherDoc(imageLocation);
                    }
            }
        }
        return typePojos;
    }

    public List<HrApplicationPojo> getHrAppliList(String search) {
        List<HrApplication> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = bsHrApplicRepository.findAll();
        } else {
            list = bsHrApplicRepository.findAllByHrFullName(search);
        }
        List<HrApplicationPojo> typePojos = BsUserMapper.mapHrEntityToPojo(list);
        for (HrApplicationPojo hrApplicationPojo : typePojos) {
            if (!StringUtils.isEmpty(hrApplicationPojo.getIdentityCard()))
                if (!hrApplicationPojo.getIdentityCard().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + "IC" + hrApplicationPojo.getHrFullName() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    hrApplicationPojo.setIdentityCard(imageLocation);
                }
        }
        for (HrApplicationPojo hrApplicationPojo : typePojos) {
            if (!StringUtils.isEmpty(hrApplicationPojo.getHrResume()))
                if (!hrApplicationPojo.getHrResume().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + "RES" + hrApplicationPojo.getHrFullName() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    hrApplicationPojo.setHrResume(imageLocation);
                }
        }
        for (HrApplicationPojo hrApplicationPojo : typePojos) {
            if (!StringUtils.isEmpty(hrApplicationPojo.getRelevantcert()))
                if (!hrApplicationPojo.getRelevantcert().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + "RC" + hrApplicationPojo.getHrFullName() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    hrApplicationPojo.setRelevantcert(imageLocation);
                }
        }
        for (HrApplicationPojo hrApplicationPojo : typePojos) {
            if (!StringUtils.isEmpty(hrApplicationPojo.getPhotocopy()))
                if (!hrApplicationPojo.getPhotocopy().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + "PC" + hrApplicationPojo.getHrFullName() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    hrApplicationPojo.setPhotocopy(imageLocation);
                }
        }
        for (HrApplicationPojo hrApplicationPojo : typePojos) {
            if (!StringUtils.isEmpty(hrApplicationPojo.getTertiaryeducerts()))
                if (!hrApplicationPojo.getTertiaryeducerts().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + "TEC" + hrApplicationPojo.getHrFullName() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    hrApplicationPojo.setTertiaryeducerts(imageLocation);
                }
        }
        for (HrApplicationPojo hrApplicationPojo : typePojos) {
            if (!StringUtils.isEmpty(hrApplicationPojo.getOtherdocs()))
                if (!hrApplicationPojo.getOtherdocs().equalsIgnoreCase("")) {
                    String imageLocation = FileSystemOperations.getImagesDir("") + "ORD" + hrApplicationPojo.getHrFullName() + ".png";
                    String fileDirectory = File.separator;
                    if (fileDirectory.equals("\\"))//Windows OS
                        imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                    else//Linux or MAC
                        imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                    hrApplicationPojo.setOtherdocs(imageLocation);
                }
        }
        return typePojos;
    }


    public Boolean deletePosition(PositionPojo details) {
        positionRepository.delete(details.getPositionId());
        return true;
    }

    public Boolean deleteInstruments(InstrumentsPojo details) {
        instrumentsRepository.delete(details.getInstrumentsId());
        return true;
    }


    public Boolean deleteAssessmentType(AssessmentTypePojo details) {
        assessmentTypeRepository.delete(details.getAssessmentTypeId());
        return true;
    }

    public Boolean deleteSubComponent(SubComponentPojo details) {
        subComponentRepository.delete(details.getSubComponentId());
        return true;
    }

    public Boolean deleteCustomApprover(CustomApproverPojo details) {
        customApproverRepository.delete(details.getCustomApproverId());
        return true;
    }

    public State saveState(StateDTO stateDTO) {
        State state = new State();
        if (stateDTO.getStateId() != null) {
            state = stateRepository.findByStateNameAndStateIdNotIn(stateDTO.getStateName(), stateDTO.getStateId());
        } else {
            state = stateRepository.findByStateName(stateDTO.getStateName());
        }
        if (state == null) {
            State state1 = BsUserMapper.mapStatePojoToEntity(stateDTO);
            stateRepository.save(state1);
            return state1;
        } else {
            return null;
        }

    }

    public Boolean deleteAdmission(Long id) {
        bsHrApplicRepository.delete(bsHrApplicRepository.findByHrId(id));
        return true;
    }


    public Boolean deleteState(StateDTO details) {
        stateRepository.delete(details.getStateId());
        return true;
    }

    public Boolean deletePermission(PermissionPojo details) {
        permissionRepository.delete(details.getPermissionId());
        return true;
    }

    public CheckListMaster saveCheck(CheckListMasterPojo checkListMasterPojo) {
        CheckListMaster checkListMaster = new CheckListMaster();
        CheckListMaster checkListMaster1 = BsCheckMapper.mapCheckPojoToEntity(checkListMasterPojo);
        if (!StringUtils.isEmpty(checkListMasterPojo.getLevel1())) {
            CheckListMaster checkListMaster2 = checkRepository.findOne(Long.parseLong(checkListMasterPojo.getLevel1()));
            checkListMaster1.setLevel1Id(checkListMaster2.getCheckId());
        }
        if (!StringUtils.isEmpty(checkListMasterPojo.getLevel2())) {
            CheckListMaster checkListMaster2 = checkRepository.findOne(Long.parseLong(checkListMasterPojo.getLevel2()));
            checkListMaster1.setLevel2Id(checkListMaster2.getCheckId());
        }
        if (!StringUtils.isEmpty(checkListMasterPojo.getLevel3())) {
            CheckListMaster checkListMaster2 = checkRepository.findOne(Long.parseLong(checkListMasterPojo.getLevel3()));
            checkListMaster1.setLevel3Id(checkListMaster2.getCheckId());
        }
        checkRepository.save(checkListMaster1);
        return checkListMaster1;
    }

    public LeaveMaster saveLeave(LeaveDTO leaveDTO) {
        LeaveMaster leave = new LeaveMaster();
        if (leaveDTO.getLeaveId() != null) {
            leave = leaveRepository.findByLeaveTypeAndLeaveIdNotIn(leaveDTO.getLeaveType(), leaveDTO.getLeaveId());
        } else {
            leave = leaveRepository.findByLeaveType(leaveDTO.getLeaveType());
        }
        if (leave == null) {
            LeaveMaster leaves = BsLeaveMapper.mapLeavePojoToEntity(leaveDTO);
            leaveRepository.save(leaves);
            return leaves;
        } else {
            return null;
        }

    }

    public List<CheckListMasterPojo> getCheckList(String search) {
        List<CheckListMaster> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = checkRepository.findAll();
        } else {
            list = checkRepository.findAllByAccountName(search);
        }
        List<CheckListMasterPojo> checkListMasterPojos = BsCheckMapper.mapcheckEntityToPojo(list);
        return checkListMasterPojos;
    }

    public List<LeaveDTO> getLeaveList(String search) {
        List<LeaveMaster> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = leaveRepository.findAll();
        } else {
            list = leaveRepository.findAllByLeaveType(search);
        }
        List<LeaveDTO> typePojos = BsLeaveMapper.mapleaveEntityToPojo(list);
        return typePojos;
    }

    public List<OtherContactsDTO> getContactsList(String search) {
        List<OtherContacts> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = contactRepository.findAll();
        } else {
            list = contactRepository.findAllByFullName(search);
        }
        List<OtherContactsDTO> otherContactsDTOS = BsContactMapper.mapContactEntityToPojo(list);
        return otherContactsDTOS;
    }

    public Boolean deleteCity(CityDTO details) {
        cityRepository.delete(details.getCityId());
        return true;
    }

    public Boolean deleteLeave(LeaveDTO details) {
        leaveRepository.delete(details.getLeaveId());
        return true;
    }

    public Boolean deleteContact(OtherContactsDTO details) {
        contactRepository.delete(details.getOtherContactId());
        return true;
    }

    public Boolean deleteAccountType(AccountTypePojo details) {
        accountTypeRepository.delete(details.getAccountId());
        return true;
    }

    public Boolean deleteAccountGroup(AccountGroupPojo details) {
        accountGroupRepository.delete(details.getAccountId());
        return true;
    }

    public Boolean deleteLeaveForm(LeaveFormDTO details) {
        leaveFormRepository.findByName(details.getName());
        return true;
    }

    public Branches saveBranches(BranchesPojo branchesPojo) {
        Branches branches = new Branches();
        User user=new User();
        if (branchesPojo.getBranchesId() != null) {
            branches = branchesRepository.findByBranchNameAndBranchesIdNotIn(branchesPojo.getBranchName(), branchesPojo.getBranchesId());
            user=bsUserRepository.findByUserName(branchesPojo.getUserName());
        } else {
            branches = branchesRepository.findByBranchName(branchesPojo.getBranchName());
            user=bsUserRepository.findByUserName(branchesPojo.getUserName());
        }
        if (branches == null&&user==null) {
            Branches branch = BsUserMapper.mapBranchesPojoToEntity(branchesPojo);
            branchesRepository.save(branch);
            user = bsUserRepository.findByBranchId(branch.getBranchesId());
            if (user == null) {
                user = new User();
            }
            user.setBranchCode(branchesPojo.getBranchCode());
            user.setBranchId(branch.getBranchesId());
            user.setPasswordUser(branchesPojo.getPassword());
            user.setUserName(branchesPojo.getUserName());
            user.setFull_name(branchesPojo.getBranchName());
            user.setStatus(branchesPojo.getStatus());
            bsUserRepository.save(user);
            return branch;
        } else {
            return null;
        }

    }


    public EmailTemplateMaster saveNewEmailMaster(EmailTemplatePojo emailTemplatePojo) {
        EmailTemplateMaster emailTemplateMaster = new EmailTemplateMaster();
        if (emailTemplatePojo.getEmailId() != null) {
            emailTemplateMaster = emailTemplateRepository.findByEmailNameAndEmailIdNotIn(emailTemplatePojo.getEmailName(), emailTemplatePojo.getEmailId());
        } else {
            emailTemplateMaster = emailTemplateRepository.findByEmailName(emailTemplatePojo.getEmailName());
        }
        if (emailTemplateMaster == null) {
            EmailTemplateMaster emailTemplateMaster1 = BsEmailMapper.mapPojoToEntity(emailTemplatePojo);
            emailTemplateRepository.save(emailTemplateMaster1);
            return emailTemplateMaster1;
        } else {
            return null;
        }

    }

    public Notification saveNewNotification(NotificationPojo notificationPojo) {
        Notification notification = new Notification();
        if (notificationPojo.getNotificationId() != null) {
            notification = notificationRepository.findByNotificationSubjectAndNotificationIdNotIn(notificationPojo.getNotificationSubject(), notificationPojo.getNotificationId());
        } else {
            notification = notificationRepository.findByNotificationSubject(notificationPojo.getNotificationSubject());
        }
        if (notification == null) {
            Notification notification1 = BsNotificationMapper.mapPojoToEntity(notificationPojo);
            notificationRepository.save(notification1);
            return notification1;
        } else {
            return null;
        }

    }


    public List<NotificationPojo> getNotificationList(String search) {
        List<Notification> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = notificationRepository.findAll();
        } else {
            list = notificationRepository.findAllByNotificationSubject(search);
        }
        List<NotificationPojo> notificationPojoList = BsNotificationMapper.mapNotificationEntityToPojo(list);
        return notificationPojoList;
    }

    public List<BranchesPojo> getBranchesList(String search) {
        List<Branches> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = branchesRepository.findAll();
        } else {
            list = branchesRepository.findAllByBranchName(search);
        }
        List<BranchesPojo> typePojos = BsUserMapper.mapBranchesEntityToPojo(list);
        return typePojos;
    }

    public List<EmailTemplatePojo> getEmailList(String search) {
        List<EmailTemplateMaster> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = emailTemplateRepository.findAll();
        } else {
            list = emailTemplateRepository.findAllByEmailName(search);
        }
        List<EmailTemplatePojo> emailTemplatePojoList = BsEmailMapper.mapEmailEntityToPojo(list);
        return emailTemplatePojoList;
    }


    public List<DoctorpanelPojo> getDoctorList(String search) {
        List<DoctorPanelMaster> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = doctorPanelRepository.findAll();
        } else {
            list = doctorPanelRepository.findAllByDoctorName(search);
        }
        List<DoctorpanelPojo> doctorpanelPojoList = BsDoctorpanelMapper.mapDoctorEntityToPojo(list);
        return doctorpanelPojoList;
    }

    public List<QualificationPojo> getQualificationList(String search) {
        List<Qualification> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = qualificationRepository.findAll();
        } else {
            list = qualificationRepository.findAllByQualificationName(search);
        }
        List<QualificationPojo> qualificationPojoList = BsQualificationMapper.mapQualificationEntityToPojo(list);
        return qualificationPojoList;
    }

    public BasePojo getPaginatedQualificationList(String status, BasePojo basePojo, String searchText) {
        List<Qualification> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "qualificationId"));
        if (basePojo.isLastPage() == true) {
            List<Qualification> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = qualificationRepository.findAllByQualificationNameContainingAndStatus(searchText, status);
            } else {
                list1 = qualificationRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "qualificationId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Qualification qualification = new Qualification();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "qualificationId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = qualificationRepository.findFirstByQualificationNameContainingAndStatus(searchText, status, sort);
            list = qualificationRepository.findAllByQualificationNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = qualificationRepository.findFirstByStatus(status, sort);
            list = qualificationRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<QualificationPojo> qualificationPojoList = BsQualificationMapper.mapQualificationEntityToPojo(list);
        basePojo = calculatePagination(basePojo, qualificationPojoList.size());
        basePojo.setList(qualificationPojoList);
        return basePojo;
    }

    public BasePojo getpaginatedstatelist(String status, BasePojo basePojo, String searchText) {
        List<State> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "stateId"));

        if (basePojo.isLastPage() == true) {
            List<State> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = stateRepository.findAllByStateNameContainingAndStatus(searchText, status);
            } else {
                list1 = stateRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "stateId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        State qualification = new State();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "stateId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = stateRepository.findFirstByStateNameContainingAndStatus(searchText, status, sort);
            list = stateRepository.findAllByStateNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = stateRepository.findFirstByStatus(status, sort);
            list = stateRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<StateDTO> PojoList = BsStateMapper.mapstateEntityToPojo(list);
        for(StateDTO s : PojoList){
            if(s.getCountryId()!=null) {
                Country country = countryRepository.findOne(Long.valueOf(s.getCountryId()));
                s.setCountryName(country.getCountryName());
            }
        }
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getcountrypagelist(String status, BasePojo basePojo, String searchText) {
        List<Country> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "countryId"));
        if (basePojo.isLastPage() == true) {
            List<Country> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = countryRepository.findAllByCountryNameContainingAndStatus(searchText, status);
            } else {
                list1 = countryRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "countryId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Country qualification = new Country();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "countryId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = countryRepository.findFirstByCountryNameContainingAndStatus(searchText, status, sort);
            list = countryRepository.findAllByCountryNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = countryRepository.findFirstByStatus(status, sort);
            list = countryRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<CountryDTO> PojoList = BsCountryMapper.mapcountryEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }
    public BasePojo getPeriodpagelist(String status, BasePojo basePojo, String searchText) {
        List<PeriodsMaster> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "periodId"));
        if (basePojo.isLastPage() == true) {
            List<PeriodsMaster> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = periodsRepository.findAllByPeriodNameContainingAndStatus(searchText, status);
            } else {
                list1 = periodsRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "periodId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        PeriodsMaster qualification = new PeriodsMaster();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "periodId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = periodsRepository.findFirstByPeriodNameContainingAndStatus(searchText, status, sort);
            list = periodsRepository.findAllByPeriodNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = periodsRepository.findFirstByStatus(status, sort);
            list = periodsRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<PeriodsDTO> PojoList = BsUserMapper.mapPeriodEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginatedAssignmentList(String status, BasePojo basePojo, String searchText) {
        List<Assignment> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "assignmentId"));
        if (basePojo.isLastPage() == true) {
            List<Assignment> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = assignmentRepository.findAllByAssignmentNameContainingAndStatus(searchText, status);
            } else {
                list1 = assignmentRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "assignmentId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Assignment assignment = new Assignment();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "assignmentId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            assignment = assignmentRepository.findFirstByAssignmentNameContainingAndStatus(searchText, status, sort);
            list = assignmentRepository.findAllByAssignmentNameContainingAndStatus(searchText, status, pageable);
        } else {
            assignment = assignmentRepository.findFirstByStatus(status, sort);
            list = assignmentRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(assignment)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<AssignmentPojo> PojoList = BsUserMapper.mapAssignmentEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpositionpagelist(String status, BasePojo basePojo, String searchText) {
        List<Position> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "positionId"));
        if (basePojo.isLastPage() == true) {
            List<Position> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = positionRepository.findAllByPositionNameContainingAndStatus(searchText, status);
            } else {
                list1 = positionRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "positionId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Position position = new Position();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "positionId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            position = positionRepository.findFirstByPositionNameContainingAndStatus(searchText, status, sort);
            list = positionRepository.findAllByPositionNameContainingAndStatus(searchText, status, pageable);
        } else {
            position = positionRepository.findFirstByStatus(status, sort);
            list = positionRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(position)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<PositionPojo> PojoList = BsPositionMapper.mapPositionEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getinstrumentspagelist(String status, BasePojo basePojo, String searchText) {
        List<Instruments> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "instrumentsId"));
        if (basePojo.isLastPage() == true) {
            List<Instruments> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = instrumentsRepository.findAllByInstrumentsNameContainingAndStatus(searchText, status);
            } else {
                list1 = instrumentsRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "instrumentsId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Instruments instruments = new Instruments();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "instrumentsId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            instruments = instrumentsRepository.findFirstByInstrumentsNameContainingAndStatus(searchText, status, sort);
            list = instrumentsRepository.findAllByInstrumentsNameContainingAndStatus(searchText, status, pageable);
        } else {
            instruments = instrumentsRepository.findFirstByStatus(status, sort);
            list = instrumentsRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(instruments)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<InstrumentsPojo> PojoList = BsInstrumentsMapper.mapInstrumentsEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getassessmentTypepagelist(String status, BasePojo basePojo, String searchText) {
        List<AssessmentType> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "assessmentTypeId"));
        if (basePojo.isLastPage() == true) {
            List<AssessmentType> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = assessmentTypeRepository.findAllByAssessmentTypeNameContainingAndStatus(searchText, status);
            } else {
                list1 = assessmentTypeRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "assessmentTypeId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        AssessmentType assessmentType = new AssessmentType();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "assessmentTypeId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            assessmentType = assessmentTypeRepository.findFirstByAssessmentTypeNameContainingAndStatus(searchText, status, sort);
            list = assessmentTypeRepository.findAllByAssessmentTypeNameContainingAndStatus(searchText, status, pageable);
        } else {
            assessmentType = assessmentTypeRepository.findFirstByStatus(status, sort);
            list = assessmentTypeRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(assessmentType)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<AssessmentTypePojo> assessmentTypePojoList = BsAssessmentTypeMapper.mapAssessmentTypeEntityToPojo(list);
        basePojo = calculatePagination(basePojo, assessmentTypePojoList.size());
        basePojo.setList(assessmentTypePojoList);
        return basePojo;
    }


    public BasePojo getsubComponentpagelist(String status, BasePojo basePojo, String searchText) {
        List<SubComponent> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "subComponentId"));
        if (basePojo.isLastPage() == true) {
            List<SubComponent> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = subComponentRepository.findAllBySubComponentNameContainingAndStatus(searchText, status);
            } else {
                list1 = subComponentRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "subComponentId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        SubComponent subComponent = new SubComponent();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "subComponentId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            subComponent = subComponentRepository.findFirstBySubComponentNameContainingAndStatus(searchText, status, sort);
            list = subComponentRepository.findAllBySubComponentNameContainingAndStatus(searchText, status, pageable);
        } else {
            subComponent = subComponentRepository.findFirstByStatus(status, sort);
            list = subComponentRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(subComponent)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<SubComponentPojo> subComponentList = BsSubComponentMapper.mapSubComponentEntityToPojo(list);
        for (SubComponentPojo s : subComponentList) {
            if (s.getComponentName() != null) {
                Components components = componentRepository.findOne((s.getComponentName()));
                s.setComponentNm(components.getComponentName());
            }
        }
            basePojo = calculatePagination(basePojo, subComponentList.size());
            basePojo.setList(subComponentList);
            return basePojo;
        }



    public BasePojo getcustomApproverlist(String status, BasePojo basePojo, String searchText) {
        List<CustomApprover> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "customApproverId"));
        if (basePojo.isLastPage() == true) {
            List<CustomApprover> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = customApproverRepository.findAllByCustomRoleContainingAndStatus(searchText, status);
            } else {
                list1 = customApproverRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "customApproverId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        CustomApprover customApprover = new CustomApprover();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "customApproverId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            customApprover = customApproverRepository.findFirstByCustomRoleContainingAndStatus(searchText, status, sort);
            list = customApproverRepository.findAllByCustomRoleContainingAndStatus(searchText, status, pageable);
        } else {
            customApprover = customApproverRepository.findFirstByStatus(status, sort);
            list = customApproverRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(customApprover)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<CustomApproverPojo> PojoList = BsCustomApproverMapper.mapCustomApproverEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginatedcitylist(String status, BasePojo basePojo, String searchText) {
        List<City> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "cityId"));
        if (basePojo.isLastPage() == true) {
            List<City> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = cityRepository.findAllByCityNameContainingAndStatus(searchText, status);
            } else {
                list1 = cityRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "cityId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        City qualification = new City();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "cityId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = cityRepository.findFirstByCityNameContainingAndStatus(searchText, status, sort);
            list = cityRepository.findAllByCityNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = cityRepository.findFirstByStatus(status, sort);
            list = cityRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<CityDTO> PojoList = BsCityMapper.mapcityEntityToPojo(list);
        for(CityDTO s : PojoList){
            Country country = countryRepository.findOne(s.getCountryId());
            if(s.getStateId()!=null) {
                State state = stateRepository.findOne(s.getStateId());
                s.setStateName(state.getStateName());
            }
            s.setCountryName(country.getCountryName());
        }
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginatedbranchlist(String status, BasePojo basePojo, String searchText) {
        List<Branches> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "branchesId"));
        if (basePojo.isLastPage() == true) {
            List<Branches> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = branchesRepository.findAllByBranchNameContainingAndStatus(searchText, status);
            } else {
                list1 = branchesRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "branchesId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Branches qualification = new Branches();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "branchesId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = branchesRepository.findFirstByBranchNameContainingAndStatus(searchText, status, sort);
            list = branchesRepository.findAllByBranchNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = branchesRepository.findFirstByStatus(status, sort);
            list = branchesRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<BranchesPojo> PojoList =BsUserMapper.mapBranchesEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginatedrolelist(String status, BasePojo basePojo, String searchText) {
        List<Roles> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "rolesId"));
        if (basePojo.isLastPage() == true) {
            List<Roles> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = rolesRepository.findAllByRoleContainingAndStatus(searchText, status);
            } else {
                list1 = rolesRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "rolesId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Roles qualification = new Roles();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "rolesId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = rolesRepository.findFirstByRoleContainingAndStatus(searchText, status, sort);
            list = rolesRepository.findAllByRoleContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = rolesRepository.findFirstByStatus(status, sort);
            list = rolesRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<RolesPojo> PojoList = BsRoleMapper.maproleEntityToPojo(list);
        for(RolesPojo s : PojoList){
            if(s.getBranchId()!=null) {
                Branches branches1 = branchesRepository.findOne(s.getBranchId());
                s.setBranchName(branches1.getBranchName());
            }
        }
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginatedresourcelist(String status, BasePojo basePojo, String searchText) {
        List<ResourceCategory> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "resourceCategoryId"));
        if (basePojo.isLastPage() == true) {
            List<ResourceCategory> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = resourceCategoryRepository.findAllByResourceCategoryNameContainingAndStatus(searchText, status);
            } else {
                list1 = resourceCategoryRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "resourceCategoryId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        ResourceCategory qualification = new ResourceCategory();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "resourceCategoryId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = resourceCategoryRepository.findFirstByResourceCategoryNameContainingAndStatus(searchText, status, sort);
            list = resourceCategoryRepository.findAllByResourceCategoryNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = resourceCategoryRepository.findFirstByStatus(status, sort);
            list = resourceCategoryRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<ResourceCategoryPojo> PojoList = BsResourcecateMapper.mapresourcecatEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginatedresourceslist(String status, BasePojo basePojo, String searchText) {
        List<Resource> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "resourceId"));
        if (basePojo.isLastPage() == true) {
            List<Resource> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = resourceRepository.findAllByResourceNameContainingAndStatus(searchText, status);
            } else {
                list1 = resourceRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "resourceId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Resource qualification = new Resource();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "resourceId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = resourceRepository.findFirstByResourceNameContainingAndStatus(searchText, status, sort);
            list = resourceRepository.findAllByResourceNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = resourceRepository.findFirstByStatus(status, sort);
            list = resourceRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<ResourcePojo> PojoList = BsResourceMapper.mapresourceEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginateddepartmentlist(String status, BasePojo basePojo, String searchText) {
        List<Department> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "departmentId"));
        if (basePojo.isLastPage() == true) {
            List<Department> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = departmentRepository.findAllByDepartmentNameContainingAndStatus(searchText, status);
            } else {
                list1 = departmentRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "departmentId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Department qualification = new Department();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "departmentId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = departmentRepository.findFirstByDepartmentNameContainingAndStatus(searchText, status, sort);
            list = departmentRepository.findAllByDepartmentNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = departmentRepository.findFirstByStatus(status, sort);
            list = departmentRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<DepartmentPojo> PojoList = BsDepartmentMapper.mapdptEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }
    public BasePojo getpaginatedadmission(String status, BasePojo basePojo, String searchText,Long className) {
        List<Student> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "studentId"));
        if (basePojo.isLastPage() == true) {
            List<Student> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsStudentRepository.findAllByStudentNameContainingAndStudentStatus(searchText, status);
            } else {
                list1 = bsStudentRepository.findAllByStudentStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "studentId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Student qualification = new Student();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "studentId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = bsStudentRepository.findFirstByStudentNameContainingAndStudentStatus(searchText, status, sort);
            list = bsStudentRepository.findFirstByStudentNameContainingAndStudentStatus(searchText, status, pageable);
        } else {
            qualification = bsStudentRepository.findFirstByStudentStatus(status, sort);
            list = bsStudentRepository.findAllByStudentStatus(status, pageable);
        }

        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<StudentPojo> acdPojoList = new ArrayList<>();
        List<Student> list1 = bsStudentRepository.findAllByClassId(className);
        if (className != null){
            acdPojoList = ObjectMapperUtils.mapAll(list1, StudentPojo.class);
            }
            else {
            acdPojoList = BsUserMapper.mapStudentEntityToPojo(list);
        }
            for (StudentPojo s : acdPojoList) {
                if (s.getClassId() != null) {
                    Class aClass = bsClassRepository.findOne(Long.parseLong(s.getClassId()));
                    s.setClassName(aClass.getClassName());
                }
            }

            if (acdPojoList.size() > 0) {
                for (StudentPojo studentPojo : acdPojoList) {
                    if (!StringUtils.isEmpty(studentPojo.getStudentPhoto()))
                        if (!studentPojo.getStudentPhoto().equalsIgnoreCase("")) {
                            String imageLocation = FileSystemOperations.getImagesDir("") + "SP" + studentPojo.getStudentProfileId() + ".png";
                            String fileDirectory = File.separator;
                            if (fileDirectory.equals("\\"))//Windows OS
                                imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                            else//Linux or MAC
                                imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                            studentPojo.setStudentPhoto(imageLocation);
                        }
                }
                for (StudentPojo studentPojo : acdPojoList) {
                    if (!StringUtils.isEmpty(studentPojo.getStudBirthCer()))
                        if (!studentPojo.getStudBirthCer().equalsIgnoreCase("")) {
                            String imageLocation = FileSystemOperations.getImagesDir("") + "SBC" + studentPojo.getStudentProfileId() + ".png";
                            String fileDirectory = File.separator;
                            if (fileDirectory.equals("\\"))//Windows OS
                                imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                            else//Linux or MAC
                                imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                            studentPojo.setStudBirthCer(imageLocation);
                        }
                }
                for (StudentPojo studentPojo : acdPojoList) {
                    if (!StringUtils.isEmpty(studentPojo.getStudIdentityCard()))
                        if (!studentPojo.getStudIdentityCard().equalsIgnoreCase("")) {
                            String imageLocation = FileSystemOperations.getImagesDir("") + "SIC" + studentPojo.getStudentProfileId() + ".png";
                            String fileDirectory = File.separator;
                            if (fileDirectory.equals("\\"))//Windows OS
                                imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                            else//Linux or MAC
                                imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                            studentPojo.setStudIdentityCard(imageLocation);
                        }
                }
                for (StudentPojo studentPojo : acdPojoList) {
                    if (!StringUtils.isEmpty(studentPojo.getParentIdentityCard()))
                        if (!studentPojo.getParentIdentityCard().equalsIgnoreCase("")) {
                            String imageLocation = FileSystemOperations.getImagesDir("") + "PIC" + studentPojo.getStudentProfileId() + ".png";
                            String fileDirectory = File.separator;
                            if (fileDirectory.equals("\\"))//Windows OS
                                imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                            else//Linux or MAC
                                imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                            studentPojo.setParentIdentityCard(imageLocation);
                        }
                }
                for (StudentPojo studentPojo : acdPojoList) {
                    if (!StringUtils.isEmpty(studentPojo.getParentMarrCer()))
                        if (!studentPojo.getParentMarrCer().equalsIgnoreCase("")) {
                            String imageLocation = FileSystemOperations.getImagesDir("") + "PMC" + studentPojo.getStudentProfileId() + ".png";
                            String fileDirectory = File.separator;
                            if (fileDirectory.equals("\\"))//Windows OS
                                imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                            else//Linux or MAC
                                imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                            studentPojo.setParentMarrCer(imageLocation);
                        }
                }
                for (StudentPojo studentPojo : acdPojoList) {
                    if (!StringUtils.isEmpty(studentPojo.getPrevSchoolCer()))
                        if (!studentPojo.getPrevSchoolCer().equalsIgnoreCase("")) {
                            String imageLocation = FileSystemOperations.getImagesDir("") + "PSC" + studentPojo.getStudentProfileId() + ".png";
                            String fileDirectory = File.separator;
                            if (fileDirectory.equals("\\"))//Windows OS
                                imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                            else//Linux or MAC
                                imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                            studentPojo.setPrevSchoolCer(imageLocation);
                        }
                }
                for (StudentPojo studentPojo : acdPojoList) {
                    if (!StringUtils.isEmpty(studentPojo.getStudOtherDoc()))
                        if (!studentPojo.getStudOtherDoc().equalsIgnoreCase("")) {
                            String imageLocation = FileSystemOperations.getImagesDir("") + "SOD" + studentPojo.getStudentProfileId() + ".png";
                            String fileDirectory = File.separator;
                            if (fileDirectory.equals("\\"))//Windows OS
                                imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                            else//Linux or MAC
                                imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                            studentPojo.setStudOtherDoc(imageLocation);
                        }
                }
            }

        basePojo = calculatePagination(basePojo, acdPojoList.size());
        basePojo.setList(acdPojoList);
        return basePojo;
    }


    public BasePojo getpaginatedsowList( BasePojo basePojo, String searchText) {
        List<Sow> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "sowId"));
        if (basePojo.isLastPage() == true) {
            List<Sow> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = sowRepository.findAllByLevelContaining(searchText);
            } else {
                list1 = sowRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "sowId"));
        }
//        if (StringUtils.isEmpty(status)) {
//            status = "Active";
//        }
        Sow qualification = new Sow();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "sowId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = sowRepository.findFirstByLevelContaining(searchText, sort);
            list = sowRepository.findAllByLevelContaining(searchText, pageable);
        } else {
            qualification = sowRepository.findFirstBy(sort);
            list = sowRepository.findAll();
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<SowPojo> PojoList = BsUserMapper.mapSowPlanEntityToPojo(list);
        for(SowPojo s : PojoList){
            if(s.getLevel()!=null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(s.getLevel());
                s.setLevelValue(gradeMaster.getGradeName());
            }

            if(s.getClasses()!=null) {
                Class aClass = bsClassRepository.findOne(s.getClasses());
                 s.setClassesValue(aClass.getClassName());
            }

            if(s.getSemester()!=null) {
                Semester semester = bsSemesterRepository.findOne(s.getSemester());
                s.setSemesterValue(semester.getSemesterName());
            }

            if(s.getSubject()!=null) {
                Subject subject = subjectRepository.findOne(s.getSubject());
                s.setSubjectValue(subject.getSubjectName());
            }

            if(s.getChapter()!=null) {
                Chapters chapters = chapterRepository.findOne(s.getChapter());
                s.setChapterValue(chapters.getChapterName());
            }

            if(s.getTerm()!=null) {
                Term term = termRepository.findOne(s.getTerm());
                s.setTermValue(term.getTermName());
            }
//            s.setLevelValue(gradeMaster.getGradeName());

        }
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getPaginationCompList(String status, BasePojo basePojo, String searchText) {
        List<Components> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "componentId"));
        if (basePojo.isLastPage() == true) {
            List<Components> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = componentRepository.findAllByComponentNameContainingAndStatus(searchText, status);
            } else {
                list1 = componentRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "componentId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Components qualification = new Components();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "componentId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = componentRepository.findAllByComponentNameContainingAndStatus(searchText, status, sort);
            list = componentRepository.findAllByComponentNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = componentRepository.findFirstByStatus(status, sort);
            list = componentRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<ComponentPojo> PojoList = BsComponentMapper.mapcomponentEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginatedagelist(String status, BasePojo basePojo, String searchText) {
        List<Agegroup> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "agegroupId"));
        if (basePojo.isLastPage() == true) {
            List<Agegroup> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = agegroupRepository.findAllByFromAgegroupContainingAndStatus(searchText, status);
            } else {
                list1 = agegroupRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "agegroupId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Agegroup qualification = new Agegroup();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "agegroupId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = agegroupRepository.findFirstByFromAgegroupContainingAndStatus(searchText, status, sort);
            list = agegroupRepository.findAllByFromAgegroupAndStatus(searchText, status, pageable);
        } else {
            qualification = agegroupRepository.findFirstByStatus(status, sort);
            list = agegroupRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<AgegroupPojo> PojoList = BsAgeGroupMapper.mapageEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getpaginatedgradelist(String status, BasePojo basePojo, String searchText) {
        List<GradeMaster> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "gradeId"));
        if (basePojo.isLastPage() == true) {
            List<GradeMaster> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsGrademasterRepository.findAllBygradeNameContainingAndGradeStatus(searchText, status);
            } else {
                list1 = bsGrademasterRepository.findAllByGradeStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "gradeId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        GradeMaster qualification = new GradeMaster();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "gradeId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = bsGrademasterRepository.findFirstBygradeNameAndGradeStatus(searchText, status, sort);
            list = bsGrademasterRepository.findAllBygradeNameContainingAndGradeStatus(searchText, status, pageable);
        } else {
            qualification = bsGrademasterRepository.findFirstByGradeStatus(status, sort);
            list = bsGrademasterRepository.findAllByGradeStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<GradeMasterPojo> PojoList = BsGradeMapper.mapgradeEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }


    public BasePojo getpaginatedLesssonPlan(BasePojo basePojo, String searchText) {
        List<LessonPlan> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "lessonPlanId"));
        if (basePojo.isLastPage() == true) {
            List<LessonPlan> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = lessonPlanRepository.findAllBySubjectContainingOrLevelContainingOrClassesContaining(searchText,searchText,searchText);
            } else {
                list1 = lessonPlanRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "lessonPlanId"));
        }
        LessonPlan lessonPlan = new LessonPlan();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "lessonPlanId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            lessonPlan = lessonPlanRepository.findBySubjectContainingOrLevelContainingOrClassesContaining(sort,searchText,searchText,searchText);
            list = lessonPlanRepository.findBySubjectContainingOrLevelContainingOrClassesContaining( pageable,searchText,searchText,searchText);
        } else {
            lessonPlan = lessonPlanRepository.findFirstBy(sort);
            list = lessonPlanRepository.findAllBy(pageable);
        }
        if (list.contains(lessonPlan)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }

        List<LessonPlanPojo> PojoList = BsUserMapper.mapLessonPlanEntityToPojo(list);
        if (PojoList.size() > 0) {
            for (LessonPlanPojo s : PojoList) {
                if (!StringUtils.isEmpty(s.getAttachFile())) {
                    if (!s.getAttachFile().equalsIgnoreCase("")) {
                        String imageLocation = FileSystemOperations.getImagesDir("") + "SP" + s.getLevel() + ".png";
                        String fileDirectory = File.separator;
                        if (fileDirectory.equals("\\"))//Windows OS
                            imageLocation = imageLocation.substring(imageLocation.indexOf("\\image")).replaceAll("\\\\", "/");
                        else//Linux or MAC
                            imageLocation = imageLocation.substring(imageLocation.indexOf("/image"));
                        s.setAttachFile(imageLocation);
                    }
                }
                if (s.getLevel() != null) {
                    GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(s.getLevel()));
                    s.setLevelName(gradeMaster.getGradeName());
                }
                if (s.getSubject() != null) {
                    Subject c = subjectRepository.findOne(Long.parseLong(s.getSubject()));
                    s.setSubjectName(c.getSubjectName());
                }
                if (s.getChapter() != null) {
                    Chapters chapters = chapterRepository.findOne(Long.parseLong(s.getChapter()));
                    s.setChapterName(chapters.getChapterName());
                }

                if (s.getTopic() != null) {
                    TopicDetails topic = topicDetailsRepository.findOne(Long.parseLong(s.getTopic()));
                    s.setTopicName(topic.getTopicName());
                }
                if (s.getTerm() != null) {
                    Term term = termRepository.findOne(Long.parseLong(s.getTerm()));
                    s.setTermName(term.getTermName());
                }


//                List<LessonPlanDetails> lessonPlanDetails=lessonPlanDetailsRepository.findByLessonPlanId(s.getLessonPlanId());
//                for(LessonPlanDetails lessonPlanDetails1:lessonPlanDetails) {
//                    s.setComponent(lessonPlanDetails1.getComponent());
                    if (StringUtils.isNotEmpty(s.getComponent())) {
                        Components term = componentRepository.findOne(Long.parseLong(s.getComponent()));
                        s.setComponentName(term.getComponentName());
                    }
//                    s.setSubComponent(lessonPlanDetails1.getSubComponent());
                    if (StringUtils.isNotEmpty(s.getSubComponent())) {
                        SubComponent subComponent = subComponentRepository.findOne(Long.parseLong(s.getSubComponent()));
                        s.setSubcomponentName(subComponent.getSubComponentName());
                    }
//                    s.setQuestionId(lessonPlanDetails1.getQuestionId());
                    if (s.getQuestionId() != null) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Long>>() {
                        }.getType();
                        List<AssessmentQuestionDetailsPojo> studentNames = new ArrayList<>();
                        List<Long> menuPojos = gson.fromJson(s.getQuestionId(), listType);
                        AssessmentQuestionDetails assessmentQuestionDetails = new AssessmentQuestionDetails();
                        for (Long questionList : menuPojos) {
                            assessmentQuestionDetails = bsAssessmentQuestionDetailsRepository.findOne(questionList);
                            AssessmentQuestionDetailsPojo assessmentQuestionDetailsPojo = BsUserMapper.mapAssQueEntityToPojo(assessmentQuestionDetails);
                            studentNames.add(assessmentQuestionDetailsPojo);
                            s.setQuestionDetailsName(studentNames);

                        }
//                    }
                }
            }
            basePojo = calculatePagination(basePojo, PojoList.size());
            basePojo.setList(PojoList);
        }
        return basePojo;
    }


    public BasePojo getpaginatedAssesmentCreator(BasePojo basePojo, String searchText) {
        List<AssesmentCreator> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "acreatorId"));
        if (basePojo.isLastPage() == true) {
            List<AssesmentCreator> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = assesmentCreatorRepository.findAllByAssesmentNameContaining(searchText);
            } else {
                list1 = assesmentCreatorRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "acreatorId"));
        }
        AssesmentCreator assesmentCreator = new AssesmentCreator();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "acreatorId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            assesmentCreator = assesmentCreatorRepository.findFirstByAssesmentNameContaining(searchText,sort);
            list = assesmentCreatorRepository.findAllByAssesmentNameContaining( searchText,pageable);
        } else {
            assesmentCreator = assesmentCreatorRepository.findFirstBy(sort);
            list = assesmentCreatorRepository.findAllBy(pageable);
        }
        if (list.contains(assesmentCreator)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<AssesmentCreatorPojo> assesmentCreatorPojos = BsUserMapper.mapCreatorEntityToPojo(list);
        for(AssesmentCreatorPojo s : assesmentCreatorPojos){
            if(s.getLevelId()!=null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(s.getLevelId()));
                s.setLevelName(gradeMaster.getGradeName());
            }

            if(s.getSemesterId()!=null) {
                Semester semester = bsSemesterRepository.findOne(Long.parseLong(s.getSemesterId()));
                s.setSemesterName(semester.getSemesterName());
            }

//            if(s.getTermId()!=null) {
//                Term term = termRepository.findOne(Long.parseLong(s.getTermId()));
//                s.setTermName(term.getTermName());
//            }
        }

        basePojo = calculatePagination(basePojo, assesmentCreatorPojos.size());
        basePojo.setList(assesmentCreatorPojos);
        return basePojo;
    }


    public BasePojo getpaginatedAssessQues(BasePojo basePojo, String searchText) {
        List<AssessmentQuestions> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "assessmentQuestionsId"));
        if (basePojo.isLastPage() == true) {
            List<AssessmentQuestions> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsAssessmentQuestionsRepository.findAllBySubjectIdContainingOrLevelIdContainingOrClassIdContaining(searchText,searchText,searchText);
            } else {
                list1 = bsAssessmentQuestionsRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "assessmentQuestionsId"));
        }
        AssessmentQuestions assessmentQuestions = new AssessmentQuestions();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "assessmentQuestionsId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            assessmentQuestions = bsAssessmentQuestionsRepository.findBySubjectIdContainingOrLevelIdContainingOrClassIdContaining(sort,searchText,searchText,searchText);
            list = bsAssessmentQuestionsRepository.findBySubjectIdContainingOrLevelIdContainingOrClassIdContaining( pageable,searchText,searchText,searchText);
        } else {
            assessmentQuestions = bsAssessmentQuestionsRepository.findFirstBy(sort);
            list = bsAssessmentQuestionsRepository.findAllBy(pageable);
        }
        if (list.contains(assessmentQuestions)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }

        List<AssessmentQuestionsPojo> PojoList = BsAssessmentQuestionsDetailsMapper.mapEntityToPojo(list);
        for(AssessmentQuestionsPojo s:PojoList) {
            if (s.getLevelId() != null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findOne((s.getLevelId()));
                s.setLevelName(gradeMaster.getGradeName());
            }
            if (s.getClassId() != null) {
                Class aClass = bsClassRepository.findOne((s.getClassId()));
                s.setClassName(aClass.getClassName());
            }
            if (s.getSubjectId() != null) {
                Subject c = subjectRepository.findOne((s.getSubjectId()));
                s.setSubjectName(c.getSubjectName());
            }
            if (s.getChapterId() != null) {
                Chapters chapters = chapterRepository.findOne((s.getChapterId()));
                s.setChapterName(chapters.getChapterName());
            }

//            PojoList.add(s);
        }
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public BasePojo getMarksSubList(BasePojo basePojo, String searchText) {
        List<MarksSubmission> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<MarksSubmission> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = marksSubmissionRepository.findAllByLevelIdContainingOrClassIdContainingOrSubjectIdContaining(searchText,searchText,searchText);
            } else {
                list1 = marksSubmissionRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        MarksSubmission marks = new MarksSubmission();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            marks = marksSubmissionRepository.findAllByLevelIdContainingOrClassIdContainingOrSubjectIdContaining(sort,searchText,searchText,searchText);
            list = marksSubmissionRepository.findAllByLevelIdContainingOrClassIdContainingOrSubjectIdContaining( pageable,searchText,searchText,searchText);
        } else {
            marks = marksSubmissionRepository.findFirstBy(sort);
            list = marksSubmissionRepository.findAllBy(pageable);
        }
        if (list.contains(marks)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }

        List<MarksSubmissionPojo> PojoList = BsUserMapper.mapEntityToPojo(list);
        for(MarksSubmissionPojo s:PojoList) {
            if (s.getLevelId() != null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findOne((s.getLevelId()));
                s.setLevelName(gradeMaster.getGradeName());
            }
            if (s.getClassId() != null) {
                Class aClass = bsClassRepository.findOne((s.getClassId()));
                s.setClassName(aClass.getClassName());
            }
            if (s.getSubjectId() != null) {
                Subject c = subjectRepository.findOne((s.getSubjectId()));
                s.setSubjectName(c.getSubjectName());
            }
            }
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }

    public void deleteMarksSub(Long id) {
        MarksSubmission marksSubmission = marksSubmissionRepository.findOne(id);
        List<MarksSubmissionDetails> details = marksSubmissionDetailsRepository.findAllByMarksSubId(marksSubmission.getId());
        for (MarksSubmissionDetails assessmentQuestionDetails1 : details) {
            marksSubmissionDetailsRepository.delete(assessmentQuestionDetails1.getId());
        }
        marksSubmissionRepository.delete(marksSubmission.getId());
    }

    public MarksSubmissionPojo editMarksSub(Long id) {
        MarksSubmissionPojo marksSubmissionPojo = new MarksSubmissionPojo();
        MarksSubmission marksSubmission = marksSubmissionRepository.findOne(id);
        marksSubmissionPojo.setId(marksSubmission.getId());
        marksSubmissionPojo.setLevelId(marksSubmission.getLevelId());
        marksSubmissionPojo.setClassId(marksSubmission.getClassId());
        marksSubmissionPojo.setSubjectId(marksSubmission.getSubjectId());
        marksSubmissionPojo.setEmployee(marksSubmission.getEmployee());
        return marksSubmissionPojo;
    }


    public List<TimeTablePojo> getTimetable(){
        List<TimeTable> list = timeTableRepository.findAll();
        List<TimeTablePojo> typePojos = BsUserMapper.mapTimetableEntityToPojo(list);
        for(TimeTablePojo pojo: typePojos){
            if(!StringUtils.isEmpty(pojo.getLevel())){
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(pojo.getLevel()));
                pojo.setLevelName(gradeMaster.getGradeName());

            }
            if(!StringUtils.isEmpty(pojo.getClassName())){
                Class aClass = bsClassRepository.findOne(Long.parseLong(pojo.getClassName()));
                pojo.setClsName(aClass.getClassName());
            }
            if(!StringUtils.isEmpty(pojo.getSubject())){
                Subject c = subjectRepository.findOne(Long.parseLong(pojo.getSubject()));
                pojo.setSubName(c.getSubjectName());
            }
            if(!StringUtils.isEmpty(pojo.getTeacher())){
                Employee emp = bsEmployeeRepository.findOne(Long.parseLong(pojo.getTeacher()));
                pojo.setTeacherName(emp.getEmployeeName());
            }
            if(!StringUtils.isEmpty(pojo.getPeriod())){
                PeriodsMaster per = periodsRepository.findOne(Long.parseLong(pojo.getPeriod()));
                pojo.setPeriodName(per.getPeriodName());
            }
            if(!StringUtils.isEmpty(pojo.getFacility())){
                FacilityDetails det = facilityDetailsRepository.findOne(Long.parseLong(pojo.getFacility()));
                pojo.setFacilityName(det.getFacilityName());
            }
        }
        return typePojos;
    }

    public TimetableDto generateTeacherReport(String teacherName) {
       Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "periodFrom"));
       List<PeriodsMaster> masters = periodsRepository.findAll(sort);
        TimetableDto dto = new TimetableDto();
        List<TimeTable> list = new ArrayList<>();
        if (StringUtils.isEmpty(teacherName)) {
            list = timeTableRepository.findAll();
        } else {
            list = timeTableRepository.findAllByTeacher(teacherName);
        }
        List<TimeTablePojo> typePojos = BsUserMapper.mapTimetableEntityToPojo(list);
        for(TimeTablePojo pojo: typePojos){
            if(pojo.getLevel() != null){
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(pojo.getLevel()));
                pojo.setLevelName(gradeMaster.getGradeName());

            }
            if(pojo.getClassName()!=null){
                Class aClass = bsClassRepository.findOne(Long.parseLong(pojo.getClassName()));
                pojo.setClsName(aClass.getClassName());
            }
            if(pojo.getSubject()!=null){
                Subject c = subjectRepository.findOne(Long.parseLong(pojo.getSubject()));
                pojo.setSubName(c.getSubjectName());
            }
            if(pojo.getTeacher()!=null){
                Employee emp = bsEmployeeRepository.findOne(Long.parseLong(pojo.getTeacher()));
                pojo.setTeacherName(emp.getEmployeeName());
            }
            if(pojo.getPeriod()!=null){
                PeriodsMaster per = periodsRepository.findOne(Long.parseLong(pojo.getPeriod()));
                pojo.setPeriodName(per.getPeriodName());
                pojo.setPeriodFrom(per.getPeriodFrom());
                pojo.setPeriodTo(per.getPeriodTo());
            }
            if(pojo.getFacility()!=null){
                FacilityDetails det = facilityDetailsRepository.findOne(Long.parseLong(pojo.getFacility()));
                pojo.setFacilityName(det.getFacilityName());
            }
        }
        dto.setPeriodsList(masters);
        dto.setList(typePojos);
        return dto;
    }

    public  List<Map<String,Object>> generateMasterReport(String weekday) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "periodFrom"));
        List<PeriodsMaster> masters = periodsRepository.findAll(sort);
        TimetableDto dto = new TimetableDto();
        List<TimeTable> list = new ArrayList<>();
        if (StringUtils.isEmpty(weekday)) {
            list = timeTableRepository.findAll();
        } else {
            list = timeTableRepository.findAllByChooseDay(weekday);
        }
        List<TimeTablePojo> typePojos = BsUserMapper.mapTimetableEntityToPojo(list);
        for(TimeTablePojo pojo: typePojos){
            if(pojo.getLevel() != null){
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(pojo.getLevel()));
                pojo.setLevelName(gradeMaster.getGradeName());

            }
            if(pojo.getClassName()!=null){
                Class aClass = bsClassRepository.findOne(Long.parseLong(pojo.getClassName()));
                pojo.setClsName(aClass.getClassName());
            }
            if(pojo.getSubject()!=null){
                Subject c = subjectRepository.findOne(Long.parseLong(pojo.getSubject()));
                pojo.setSubName(c.getSubjectName());
            }
            if(pojo.getTeacher()!=null){
                Employee emp = bsEmployeeRepository.findOne(Long.parseLong(pojo.getTeacher()));
                pojo.setTeacherName(emp.getEmployeeName());
            }
            if(pojo.getPeriod()!=null){
                PeriodsMaster per = periodsRepository.findOne(Long.parseLong(pojo.getPeriod()));
                pojo.setPeriodName(per.getPeriodName());
                pojo.setPeriodFrom(per.getPeriodFrom());
                pojo.setPeriodTo(per.getPeriodTo());
            }
            if(pojo.getFacility()!=null){
                FacilityDetails det = facilityDetailsRepository.findOne(Long.parseLong(pojo.getFacility()));
                pojo.setFacilityName(det.getFacilityName());
            }
        }
        dto.setWeekday(weekday);
        dto.setPeriodsList(masters);
        dto.setList(typePojos);
        Map<String, List<TimeTablePojo>> studlistGrouped =
                dto.getList().stream().collect(groupingBy(w -> w.getClsName()));
//        studlistGrouped.get("key");

        List<Map<String,Object>> abc = new ArrayList<>();
        Set<Map.Entry<String,List<TimeTablePojo>>> set = studlistGrouped.entrySet();

        for(Map.Entry<String,List<TimeTablePojo>> entry : set)
        {
            Map<String,Object> mapFromSet = new HashMap<String,Object>();
            mapFromSet.put("Class",entry.getKey());
            mapFromSet.put("List",entry.getValue());
            abc.add(mapFromSet);
        }
       return abc;
    }

    public List<IhesLessonPlanPojo> getIhesPlanList() {
        List<IhesLessonPlan> list = ihesLessonPlanRepository.findAll();
        List<IhesLessonPlanPojo> typePojos = BsUserMapper.mapIhesLessonEntityToPojo(list);
        for(IhesLessonPlanPojo pojo : typePojos) {
            if (pojo != null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(pojo.getLevelId()));
                pojo.setLevel(gradeMaster.getGradeName());
            }
            if (pojo.getClassId() != null) {
                Class aClass = bsClassRepository.findOne(Long.parseLong(pojo.getClassId()));
                pojo.setClasses(aClass.getClassName());
            }
            if (pojo.getSubjectId() != null) {
                Subject c = subjectRepository.findOne(Long.parseLong(pojo.getSubjectId()));
                pojo.setSubject(c.getSubjectName());
            }
            if (pojo.getChapterId() != null) {
                Chapters chapters = chapterRepository.findOne(Long.parseLong(pojo.getChapterId()));
                pojo.setChapter(chapters.getChapterName());
            }
            if (StringUtils.isNotEmpty(pojo.getTopicId())) {
                TopicDetails topicDetails = topicDetailsRepository.findOne(Long.parseLong(pojo.getTopicId()));
                pojo.setTopic(topicDetails.getTopicName());
            }
        }
        return typePojos;
    }

    public BasePojo getpaginatedholidaylist(String status, BasePojo basePojo, String searchText) {
        List<Holiday> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<Holiday> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = holidayRepositories.findAllByHolidayNameContainingAndStatus(searchText, status);
            } else {
                list1 = holidayRepositories.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }

        Holiday qualification = new Holiday();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            qualification = holidayRepositories.findFirstByHolidayNameContainingAndStatus(searchText, status, sort);
            list = holidayRepositories.findAllByHolidayNameContainingAndStatus(searchText, status, pageable);
        } else {
            qualification = holidayRepositories.findFirstByStatus(status, sort);
            list = holidayRepositories.findAllByStatus(status, pageable);
        }
        if (list.contains(qualification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<HolidayPojo> PojoList = BsHolidayMapper.mapholidayEntityToPojo(list);
        basePojo = calculatePagination(basePojo, PojoList.size());
        basePojo.setList(PojoList);
        return basePojo;
    }


    public List<TrainingModulePojo> getTrainingModuleList(String search){
        List<TrainingModule> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = trainingModuleRepository.findAll();
        } else {
            list = trainingModuleRepository.findAllByTrainingModuleName(search);
        }
        List<TrainingModulePojo> trainingModulePojoList = BsTrainingModuleMapper.mapTrainingEntityToPojo(list);
        return trainingModulePojoList; }

    public Boolean deleteBranches(BranchesPojo details) {
        branchesRepository.delete(details.getBranchesId());
        bsUserRepository.delete(bsUserRepository.findByBranchId(details.getBranchesId()));
        return true;
    }

    public Boolean deleteHoliday(HolidayPojo holidayPojo) {
        holidayRepositories.delete(holidayPojo.getId());
        return true;
    }

    public Boolean deleteTrainer(TrainerPojo trainerPojo) {
        trainerRepository.delete(trainerPojo.getId());
        return true;
    }

    public Boolean deleteEmail(EmailTemplatePojo details) {
        emailTemplateRepository.delete(details.getEmailId());
        return true;
    }


    public Boolean deleteDoctor(DoctorpanelPojo details) {
        doctorPanelRepository.delete(details.getDoctorpanelId());
        return true;
    }

    public Boolean deleteQualification(QualificationPojo details) {
        qualificationRepository.delete(details.getQualificationId());
        return true;
    }

    public Boolean DeleteNotification(NotificationPojo details) {
        notificationRepository.delete(details.getNotificationId());
        return true;
    }

    public Boolean deleteTrainingmodule(TrainingModulePojo details) {
        trainingModuleRepository.delete(details.getTrainingId());
        return true;
    }

    public Components saveComponent(ComponentPojo componentPojo) {
        Components components = new Components();
        if (componentPojo.getComponentId() != null) {
            components = componentRepository.findByComponentNameAndComponentIdNotIn(componentPojo.getComponentName(), componentPojo.getComponentId());
        } else {
            components = componentRepository.findByComponentName(componentPojo.getComponentName());
        }
        if (components == null) {
            Components components1 = BsComponentMapper.mapPojoToEntity(componentPojo);
            componentRepository.save(components1);
            return components1;
        } else {
            return null;
        }
    }

    public Roles saveRoles(RolesPojo rolesPojo) {
        Roles roles = new Roles();
        if (rolesPojo.getRolesId() != null) {
            roles = rolesRepository.findByBranchIdAndRoleAndRolesIdNotIn(rolesPojo.getBranchId(),rolesPojo.getRole(), rolesPojo.getRolesId());
        } else {
            roles = rolesRepository.findByRoleAndBranchId(rolesPojo.getRole(),rolesPojo.getBranchId());
        }
        if (roles == null) {
            Roles roles1 = BsUserMapper.mapRolesPojoToEntity(rolesPojo);
            rolesRepository.save(roles1);
            return roles1;
        } else {
            return null;
        }
    }
    public AssesmentCreator saveAssCre(AssesmentCreatorPojo assesmentCreatorPojo) {
        AssesmentCreator assesmentCreator = new AssesmentCreator();
        if (assesmentCreatorPojo.getAcreatorId() != null) {
            assesmentCreator = assesmentCreatorRepository.findAllBySemesterIdAndTermIdAndLevelIdAndClassIdAndSubjectIdAndChapterIdAndAcreatorIdNotIn(assesmentCreatorPojo.getSemesterId(), assesmentCreatorPojo.getTermId(),assesmentCreatorPojo.getLevelId(),assesmentCreatorPojo.getClassId(),assesmentCreatorPojo.getSubjectId(),assesmentCreatorPojo.getChapterId(),assesmentCreatorPojo.getAcreatorId());
        } else {
            assesmentCreator = assesmentCreatorRepository.findAllByAssesmentNameAndSemesterIdAndTermIdAndLevelIdAndClassIdAndSubjectIdAndChapterId(assesmentCreatorPojo.getAssesmentName(),assesmentCreatorPojo.getSemesterId(), assesmentCreatorPojo.getTermId(),assesmentCreatorPojo.getLevelId(),assesmentCreatorPojo.getClassId(),assesmentCreatorPojo.getSubjectId(),assesmentCreatorPojo.getChapterId());
        }
        if (assesmentCreator == null) {
            AssesmentCreator assesmentCreator1 = BsUserMapper.mapAssCrePojoToEntity(assesmentCreatorPojo);
            assesmentCreatorRepository.save(assesmentCreator1);
            return assesmentCreator1;
        } else {
            return null;
        }
    }


    public City saveCity(CityDTO cityDTO) {
        City city = new City();
        if (cityDTO.getCityId() != null) {
            city = cityRepository.findAllByStateIdAndCountryIdAndCityNameAndCityIdNotIn(cityDTO.getStateId(),cityDTO.getCountryId(),cityDTO.getCityName(), cityDTO.getCityId());
        } else {
            city = cityRepository.findAllByStateIdAndCountryIdAndCityName(cityDTO.getStateId(),cityDTO.getCountryId(),cityDTO.getCityName());
        }
        if (city == null) {
            City cities = BsUserMapper.mapCityPojoToEntity(cityDTO);
            cityRepository.save(cities);
            return cities;
        } else {
            return null;
        }
    }


    public Approval saveApproval(ApprovalPojo approvalPojo) {
        Approval approval1 = BsUserMapper.mapApprovalPojoToEntity(approvalPojo);
        approvalRepository.save(approval1);
        return approval1;
    }

    public List<RolesPojo> getRolesList(String search) {
        List<Roles> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = rolesRepository.findAll();
        } else {
            list = rolesRepository.findAllByRole(search);
        }
        List<RolesPojo> typePojos = BsUserMapper.mapRolesEntityToPojo(list);
        return typePojos;
    }

    public List<ApprovalPojo> getApprovalList(String search) {
        List<Approval> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = approvalRepository.findAll();
        } else {
            list = approvalRepository.findAllByApprover1(search);
        }
        List<ApprovalPojo> approvalPojos = BsUserMapper.mapApprovalEntityToPojo(list);
        return approvalPojos;
    }

    public List<ComponentPojo> getComponentList() {
        List<Components> list = new ArrayList<>();
        list = componentRepository.findAll();
        List<ComponentPojo> componentPojos = BsComponentMapper.mapcomponentEntityToPojo(list);
        return componentPojos;
    }

    public Boolean deleteRoles(RolesPojo details) {
        rolesRepository.delete(details.getRolesId());
        return true;
    }

    public ResourceCategory saveResourceCategory(ResourceCategoryPojo resourceCategoryPojo) {
        ResourceCategory resourceCtgry = BsUserMapper.mapResourceCategoryPojoToEntity(resourceCategoryPojo);
        resourceCategoryRepository.save(resourceCtgry);
        return resourceCtgry;

    }

    public List<ResourceCategoryPojo> getResourceCategoryList(String search) {
        List<ResourceCategory> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = resourceCategoryRepository.findAll();
        } else {
            list = resourceCategoryRepository.findAllByResourceCategoryName(search);
        }
        List<ResourceCategoryPojo> typePojos = BsUserMapper.mapResourceCategoryEntityToPojo(list);
        return typePojos;
    }

    public Boolean deleteResourceCategory(ResourceCategoryPojo details) {
        resourceCategoryRepository.delete(details.getResourceCategoryId());
        return true;
    }

    public Resource saveResource(ResourcePojo resourcePojo) {
        Resource resource = new Resource();
        if (resourcePojo.getResourceId() != null) {
            resource = resourceRepository.findByResourceNameAndResourceIdNotIn(resourcePojo.getResourceName(), resourcePojo.getResourceId());
        } else {
            resource = resourceRepository.findByResourceName(resourcePojo.getResourceName());
        }
        if (resource == null) {
            Resource resource1 = BsUserMapper.mapResourcePojoToEntity(resourcePojo);
            resourceRepository.save(resource1);
            return resource1;
        } else {
            return null;
        }

    }

    public List<ResourcePojo> getResourceList(String search) {
        List<Resource> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = resourceRepository.findAll();
        } else {
            list = resourceRepository.findAllByResourceName(search);
        }
        List<ResourcePojo> typePojos = BsUserMapper.mapResourceEntityToPojo(list);
        return typePojos;
    }

    public Boolean deleteResource(ResourcePojo details) {
        resourceRepository.delete(details.getResourceId());
        return true;
    }

    public Department saveDepartment(DepartmentPojo departmentPojo) {
        Department department = new Department();
        if (departmentPojo.getDepartmentId() != null) {
            department = departmentRepository.findByDepartmentNameAndBranchIdAndDepartmentIdNotIn(departmentPojo.getDepartmentName(),departmentPojo.getBranchId(), departmentPojo.getDepartmentId());
        } else {
            department = departmentRepository.findByDepartmentNameAndBranchId(departmentPojo.getDepartmentName(),departmentPojo.getBranchId());
        }
        if (department == null) {
            Department dept = BsUserMapper.mapDepartmentPojoToEntity(departmentPojo);
            departmentRepository.save(dept);
            return dept;
        } else {
            return null;
        }

    }

    public List<DepartmentPojo> getDepartmentList(String search) {
        List<Department> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = departmentRepository.findAll();
        } else {
            list = departmentRepository.findAllByDepartmentName(search);
        }
        List<DepartmentPojo> typePojos = BsUserMapper.mapDepartmentEntityToPojo(list);
        return typePojos;
    }

    public List<AssesmentSubPojo> getAssesmentSubList() {
        List<AssesmentSubmission> list = assesmentSubmissionRepository.findAllByFlag("SimpleAssesment");
        List<AssesmentSubPojo> assesmentSubPojos = BsUserMapper.mapAssSubEntityToPojo(list);
        for(AssesmentSubPojo pojo:assesmentSubPojos){
            if(pojo.getLevel()!=null){
                GradeMaster gradeMaster=bsGrademasterRepository.findByGradeId(Long.parseLong(pojo.getLevel()));
                pojo.setLevel(gradeMaster.getGradeId().toString());
                pojo.setLevelName(gradeMaster.getGradeName());
            }
            if(pojo.getClassId()!=null){
                Class cls = bsClassRepository.findByClassId(Long.parseLong(pojo.getClassId()));
                pojo.setClassId(cls.getClassId().toString());
                pojo.setClassName(cls.getClassName());
            }
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<LessonPlanPojo>>() {
        }.getType();
        for (AssesmentSubPojo m : assesmentSubPojos) {
            List<AssesmentSubmissionDetails> assesmentSubmissionDetails=assesmentSubmissionDetailsRepository.findByAssesmentId(m.getAsId());
            Map<String, List<LessonPlanPojo>> map = new HashMap<>();
            List<String> studentNames = new ArrayList<>();
            for (AssesmentSubmissionDetails assesmentSubPojo1 :assesmentSubmissionDetails) {
                studentNames.add(assesmentSubPojo1.getStudentName());
                map.put(assesmentSubPojo1.getStudentName(), gson.fromJson(assesmentSubPojo1.getTopicDetails(), type));
            }
            m.setStudentsName(gson.toJson(studentNames));
            m.setMapValue(map);
        }
        return assesmentSubPojos ;
    }
    public AssesmentSubPojo editAssesmentSub(Long id) {
        AssesmentSubmission assesmentSubmission = assesmentSubmissionRepository.findOne(id);
        AssesmentSubPojo assesmentSubPojos = BsUserMapper.mapAssEntityToPojo(assesmentSubmission);
        Gson gson = new Gson();
        Type type = new TypeToken<List<LessonPlanPojo>>() {
        }.getType();
        List<AssesmentSubmissionDetails> assesmentSubmissionDetails=assesmentSubmissionDetailsRepository.findByAssesmentId(assesmentSubPojos.getAsId());
        Map<String, List<LessonPlanPojo>> map = new HashMap<>();
        List<String> studentNames = new ArrayList<>();
        for (AssesmentSubmissionDetails assesmentSubPojo1 :assesmentSubmissionDetails) {
            studentNames.add(assesmentSubPojo1.getStudentName());
            map.put(assesmentSubPojo1.getStudentName(), gson.fromJson(assesmentSubPojo1.getTopicDetails(), type));
        }
        assesmentSubPojos.setStudentsName(gson.toJson(studentNames));
        assesmentSubPojos.setMapValue(map);
        LessonPlan lessonPlan=lessonPlanRepository.findAllByAssesmentId(id);
        assesmentSubPojos.setLessonplanId(lessonPlan.getLessonPlanId().toString());
        return assesmentSubPojos;
    }

    public Boolean deleteDepartment(DepartmentPojo details) {
        departmentRepository.delete(details.getDepartmentId());
        return true;
    }

    public Weekday saveWeekday(WeekdayPojo weekdayPojo) {
        Weekday weekday = weekdayRepository.findOne(1L);
        if (weekday == null) {
            weekday = new Weekday();
        }
        weekday = BsUserMapper.mapWeekdayPojoToEntity(weekdayPojo);
        weekdayRepository.save(weekday);
        return weekday;

    }

    public FacilityDetails saveFacility(FacilityDetailsPojo pojo) {
        FacilityDetails details = BsUserMapper.mapFacilityPojoToEntity(pojo);
        facilityDetailsRepository.save(details);
        return details;

    }

    public IhesLessonPlan saveIhes(IhesLessonPlanPojo pojo) {
        IhesLessonPlan details = BsUserMapper.mapIhesLessonPojoToEntity(pojo);
        ihesLessonPlanRepository.save(details);
        return details;
    }

    public List<FacilityDetailsPojo> getFacilityDetails() {
        List<FacilityDetails> list = facilityDetailsRepository.findAll();
        List<FacilityDetailsPojo> typePojos = BsUserMapper.mapFacilityEntityToPojo(list);
        return typePojos;
    }

    public Boolean deleteFacility(FacilityDetailsPojo details) {
        facilityDetailsRepository.delete(details.getFacilityId());
        return true;
    }

    public Boolean deleteIhes(IhesLessonPlanPojo details) {
        ihesLessonPlanRepository.delete(details.getIheslessonId());
        return true;
    }

    public List<WeekdayPojo> getWeekdayList() {
        List<Weekday> list = weekdayRepository.findAll();
        List<WeekdayPojo> typePojos = BsUserMapper.mapWeekdayEntityToPojo(list);
        return typePojos;
    }

    public List<String> getWeekday() {
        List<Weekday> list = weekdayRepository.findAll();
        Map<Key,Weekday> map = new HashMap<Key,Weekday>();
       List<String> weeks = new ArrayList<>();
        for( Weekday o : list ) {
            if(StringUtils.equalsIgnoreCase(o.getMonday(),"Working")){
                weeks.add("Monday");

            }
            if(StringUtils.equalsIgnoreCase(o.getTuesday(),"Working")){
                weeks.add("Tuesday");
            }
            if(StringUtils.equalsIgnoreCase(o.getWednesday(),"Working")){
                weeks.add("Wednesday");
            }
            if(StringUtils.equalsIgnoreCase(o.getThursday(),"Working")){
                weeks.add("Thursday");
            }
            if(StringUtils.equalsIgnoreCase(o.getFriday(),"Working")){
                weeks.add("Friday");
            }
             if(StringUtils.equalsIgnoreCase(o.getSaturday(),"Working")){
                 weeks.add("Saturday");
            }
            if(StringUtils.equalsIgnoreCase(o.getSunday(),"Working")){
                weeks.add("Sunday");
            }
        }


        return weeks;
    }

    public Boolean deleteWeekday(WeekdayPojo details) {
        weekdayRepository.delete(details.getWeekdayId());
        return true;
    }

    public List<StateDTO> getCountryState(Long countryName) {
        List<State> states = stateRepository.findAllByCountryIdAndStatus(countryName, "Active");
        List<StateDTO> stateList = BsUserMapper.mapStateEntityToPojo(states);
         return stateList;
    }
    public List<Map<String,String>> getTopicDetailsList(Long levelId,Long termId,Long subjectId,Long chapterId,Long topicId) {
        List<Map<String,String>> mapList = new ArrayList<>();
        List<Sow> topicDetails = sowRepository.findByLevelAndTermAndSubjectAndChapter(levelId,termId,subjectId,chapterId);
        for(Sow topicValues:topicDetails){
            Gson gson = new Gson();
            Type type=new TypeToken<List<Map<String,String>>>(){}.getType();
            List<Map<String,String>> topicList=gson.fromJson(topicValues.getSowList(),type);
            for(Map<String,String> m:topicList) {
                if (StringUtils.equalsIgnoreCase(m.get("topicId"),topicId.toString())) {
                    Map<String,String> map = new HashMap<>();
                    TopicDetails topicDetails1=topicDetailsRepository.findOne(topicId);
                    String topicName=topicDetails1.getTopicName();
                    map.put("topicName",topicName);
                    map.put("pageNo",m.get("pageNo"));
                    map.put("totalPages",m.get("activity"));
                    map.put("activity",m.get("activity"));
                    map.put("fromweek",m.get("fromweek"));
                    map.put("toweek",m.get("toweek"));
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }

    public List<Map<String,String>> getGradingList() {
        List<Map<String,String>> mapList = new ArrayList<>();
        List<GradingMaster> topicDetails = gradingMasterRepository.findAll();
        for(GradingMaster topicValues:topicDetails){
            Gson gson = new Gson();
            Type type=new TypeToken<List<Map<String,String>>>(){}.getType();
            List<Map<String,String>> topicList=gson.fromJson(topicValues.getList(),type);
            if(topicList!=null){
            for(Map<String,String> m:topicList) {
                Map<String,String> map = new HashMap<>();
                map.put("gradeMasterId",m.get("gradeMasterId"));
                map.put("minimum",m.get("minimum"));
                map.put("grading",m.get("grading"));
                map.put("maximum",m.get("maximum"));
                mapList.add(map);}
            }

        }
        return mapList;
    }
    public List<EmployeePojo> getdeptEmp(String departmentId) {
        List<Employee> employees = bsEmployeeRepository.findAllByEmpDepartmentAndStatus(departmentId, "Active");
        List<EmployeePojo> employeePojoList = BsUserMapper.mapEmployeeEntityToPojo(employees);
        return employeePojoList;
    }

    public List<StudentPojo> getStudentListBasedOnClass(Long id) {
        List<Student> students = bsStudentRepository.findAllByClassId(id);
        List<StudentPojo> studentDTOList = BsUserMapper.mapStudentEntityToPojo(students);
        return studentDTOList;
    }

    public ComponentPojo getcomponentvalue(Long subjectName) {
        Components components = componentRepository.findOne(subjectName);
        ComponentPojo componentPojoList = new ComponentPojo();
        componentPojoList.setWeightage(components.getWeightage());
        componentPojoList.setComponentName(components.getComponentName());
        componentPojoList.setStatus(components.getStatus());
        return componentPojoList;
    }

    public List<LessonPlanPojo> getMakrs(String name) {
        List<LessonPlan> lessonPlans = lessonPlanRepository.findAllByChapterAndAssessment(name,"detailassesment");
        List<LessonPlanPojo> lessonPlanPojoList = BsUserMapper.mapLessonPlanEntityToPojo(lessonPlans);
        for(LessonPlanPojo lessonPlanPojo:lessonPlanPojoList){
            TopicDetails topicDetails=topicDetailsRepository.findOne(Long.parseLong(lessonPlanPojo.getTopic()));
            lessonPlanPojo.setTopic(topicDetails.getTopicName());
        }
        return lessonPlanPojoList;
    }
    public List<LessonPlanPojo> getMarksBasedOnSimpleAssesment(String name,String subComponent) {
        List<LessonPlan> lessonPlans = lessonPlanRepository.findAllByChapterAndAssessmentAndSubComponent(name,"simpleassesment",subComponent);
        List<LessonPlanPojo> lessonPlanPojoList = BsUserMapper.mapLessonPlanEntityToPojo(lessonPlans);
        for(LessonPlanPojo lessonPlanPojo:lessonPlanPojoList){
            TopicDetails topicDetails=topicDetailsRepository.findOne(Long.parseLong(lessonPlanPojo.getTopic()));
            lessonPlanPojo.setTopic(topicDetails.getTopicName());
        }
        return lessonPlanPojoList;
    }


    public List<Map<String,String>> getMarksStudentAss(Long name) {
        AssesmentCreator assessmentQuestions = assesmentCreatorRepository.findOne(name);
        Gson gson=new Gson();
        Type type=new TypeToken<List<Map<String,String>>>(){}.getType();
        List<Map<String,String>> topicList=gson.fromJson(assessmentQuestions.getQuestionName(),type);
        return topicList;
    }

    public List<CheckListMasterPojo> getcheckList1() {
        List<CheckListMaster> checkListMasters = checkRepository.findAllByLevel1IdNullAndLevel2IdNull();
        List<CheckListMasterPojo> checkListMasterPojoList = BsCheckMapper.mapcheckEntityToPojo(checkListMasters);
        return checkListMasterPojoList;
    }

    public List<CheckListMasterPojo> getcheckList2(Long Level1) {
        List<CheckListMaster> checkListMaster = checkRepository.findAllByLevel1IdAndLevel2IdNullAndLevel3IdNull(Level1);
        List<CheckListMasterPojo> list = BsCheckMapper.mapcheckEntityToPojo(checkListMaster);
        return list;
    }

    public List<CheckListMasterPojo> getcheckList3(Long level2) {
        List<CheckListMaster> checkListMaster = checkRepository.findAllByLevel2Id(level2);
        List<CheckListMasterPojo> checkListMasterPojoList = BsCheckMapper.mapcheckEntityToPojo(checkListMaster);
        return checkListMasterPojoList;
    }

    public List<TeacherPojo> getEmployeeCode(String employeeName) {
        List<TeacherTable> teacherTables = teacherClearanceRepository.findAllByEmployeeName(employeeName);
        List<TeacherPojo> stateList = BsTeacherClearanceMapper.mapTeacherClearanceEntityToPojo(teacherTables);
        return stateList;
    }

    public List<ChapterPojo> getChapterSubject(String subjectName) {
        List<Chapters> chapters = chapterRepository.findAllBySubjectIdAndStatus(Long.parseLong(subjectName),"Active");
        List<ChapterPojo> list = BsUserMapper.mapChapterEntityToPojo(chapters);
        return list;
    }
    public List<SubComponentPojo> getSubCompBasedOnComp(Long id) {
        List<SubComponent> subComponents = subComponentRepository.findByComponentName(id);
        List<SubComponentPojo> list =BsSubComponentMapper.mapSubComponentEntityToPojo(subComponents);
        return list;
    }
    public List<SubjectPojo> getSubBasedOnClass(List<Long> stringList) {
        List<SubjectPojo> subjectPojos = new ArrayList<>();
        for(Long s:stringList) {
            List<Subject> subjects = subjectRepository.findAllByClassesId(s);
            List<SubjectPojo> list = BsUserMapper.mapSubjectEntityToPojo(subjects);
            subjectPojos.addAll(list);
        }
        return subjectPojos;
    }

    public List<TopicPojo> getTopicBasedOnChapter(List<Long> stringList) {
        List<TopicPojo> topicPojos = new ArrayList<>();
        for(Long s:stringList) {
            List<TopicDetails> topicDetailsList=topicDetailsRepository.findAllByChapter(s.toString());
            List<TopicPojo> topicPojoList=BsUserMapper.mapTopicDetailsEntityToPojo(topicDetailsList);
            topicPojos.addAll(topicPojoList);
        }
        return topicPojos;
    }

    public List<AssessmentQuestionDetailsPojo> getQuesBasedOnTopic(List<Long> stringList) {
        List<AssessmentQuestionDetailsPojo> assessmentQuestionDetailsPojoList = new ArrayList<>();
        for (Long s : stringList) {
            List<AssessmentQuestions> assessmentQuestionsList = bsAssessmentQuestionsRepository.findAllByTopicId(s);
            for(AssessmentQuestions assessmentQuestions:assessmentQuestionsList) {
                List<AssessmentQuestionDetails> assessmentQuestionDetails = bsAssessmentQuestionDetailsRepository.findAllByAssessmentQuestions(assessmentQuestions);
                for (AssessmentQuestionDetails assessmentQuestionDetails1 : assessmentQuestionDetails) {
                    AssessmentQuestionDetailsPojo assessmentQuestionDetailsPojo = new AssessmentQuestionDetailsPojo();
                    assessmentQuestionDetailsPojo.setQuestion(assessmentQuestionDetails1.getQuestion());
                    assessmentQuestionDetailsPojo.setMaxMarks(assessmentQuestionDetails1.getMaxMarks());
                    assessmentQuestionDetailsPojo.setAssessmentDetailsId(assessmentQuestionDetails1.getAssessmentDetailsId());
                    assessmentQuestionDetailsPojoList.add(assessmentQuestionDetailsPojo);
                }
            }
        }

        return assessmentQuestionDetailsPojoList;
    }

//    public MultiListPojo getQuesBasedOnTopicAndSubComponent(Long topicId, Long subComponentId, Long lessonPlanId, Long componentId) {
//        MultiListPojo multiListPojo=new MultiListPojo();
//        List<AssessmentQuestionDetailsPojo> assessmentQuestionDetailsPojoList = new ArrayList<>();
//        List<AssessmentQuestions> assessmentQuestionsList = bsAssessmentQuestionsRepository.findAllByTopicId(topicId);
//        for (AssessmentQuestions assessmentQuestions : assessmentQuestionsList) {
//            List<AssessmentQuestionDetails> assessmentQuestionDetails = bsAssessmentQuestionDetailsRepository.findAllByAssessmentQuestionsAndSubComponent(assessmentQuestions, subComponentId.toString());
//            for (AssessmentQuestionDetails assessmentQuestionDetails1 : assessmentQuestionDetails) {
//                AssessmentQuestionDetailsPojo assessmentQuestionDetailsPojo = new AssessmentQuestionDetailsPojo();
//                assessmentQuestionDetailsPojo.setQuestion(assessmentQuestionDetails1.getQuestion());
//                assessmentQuestionDetailsPojo.setMaxMarks(assessmentQuestionDetails1.getMaxMarks());
//                assessmentQuestionDetailsPojo.setAssessmentDetailsId(assessmentQuestionDetails1.getAssessmentDetailsId());
//                assessmentQuestionDetailsPojoList.add(assessmentQuestionDetailsPojo);
//            }
//        }
//        LessonPlanDetails lessonPlanDetails = lessonPlanDetailsRepository.findByLessonPlanIdAndComponentAndSubComponent(lessonPlanId, componentId.toString(), subComponentId.toString());
//        multiListPojo.setAssessmentQuestionDetailsPojo(assessmentQuestionDetailsPojoList);
//        if(lessonPlanDetails!=null) {
//            multiListPojo.setQuestionListDetails(lessonPlanDetails.getQuestionId());
//        }
//        return multiListPojo;
//    }
public List<AssessmentQuestionDetailsPojo> getQuesBasedOnTopicAndSubComponent(Long topicId,Long subComponentId) {
    List<AssessmentQuestionDetailsPojo> assessmentQuestionDetailsPojoList = new ArrayList<>();
    List<AssessmentQuestions> assessmentQuestionsList = bsAssessmentQuestionsRepository.findAllByTopicId(topicId);
    for(AssessmentQuestions assessmentQuestions:assessmentQuestionsList) {
        List<AssessmentQuestionDetails> assessmentQuestionDetails = bsAssessmentQuestionDetailsRepository.findAllByAssessmentQuestionsAndSubComponent(assessmentQuestions,subComponentId.toString());
        for (AssessmentQuestionDetails assessmentQuestionDetails1 : assessmentQuestionDetails) {
            AssessmentQuestionDetailsPojo assessmentQuestionDetailsPojo = new AssessmentQuestionDetailsPojo();
            assessmentQuestionDetailsPojo.setQuestion(assessmentQuestionDetails1.getQuestion());
            assessmentQuestionDetailsPojo.setMaxMarks(assessmentQuestionDetails1.getMaxMarks());
            assessmentQuestionDetailsPojo.setAssessmentDetailsId(assessmentQuestionDetails1.getAssessmentDetailsId());
            assessmentQuestionDetailsPojoList.add(assessmentQuestionDetailsPojo);
        }
    }

    return assessmentQuestionDetailsPojoList;
}
public List<AssessmentQuestionDetailsPojo> getQuesBasedOnSubject(Long subjectId) {
    List<AssessmentQuestionDetailsPojo> assessmentQuestionDetailsPojoList = new ArrayList<>();
    List<AssessmentQuestions> assessmentQuestionsList = bsAssessmentQuestionsRepository.findAllBySubjectId(subjectId);
    for(AssessmentQuestions assessmentQuestions:assessmentQuestionsList) {
        List<AssessmentQuestionDetails> assessmentQuestionDetails = bsAssessmentQuestionDetailsRepository.findAllByAssessmentQuestions(assessmentQuestions);
        for (AssessmentQuestionDetails assessmentQuestionDetails1 : assessmentQuestionDetails) {
            AssessmentQuestionDetailsPojo assessmentQuestionDetailsPojo = new AssessmentQuestionDetailsPojo();
            assessmentQuestionDetailsPojo.setQuestion(assessmentQuestionDetails1.getQuestion());
            assessmentQuestionDetailsPojo.setMaxMarks(assessmentQuestionDetails1.getMaxMarks());
            assessmentQuestionDetailsPojo.setQuestionType(assessmentQuestionDetails1.getQuestionType());
            assessmentQuestionDetailsPojo.setAssessmentDetailsId(assessmentQuestionDetails1.getAssessmentDetailsId());
            assessmentQuestionDetailsPojo.setAssessmentQuestions(assessmentQuestionDetails1.getAssessmentQuestions());
            assessmentQuestionDetailsPojoList.add(assessmentQuestionDetailsPojo);
        }
    }

    return assessmentQuestionDetailsPojoList;
}

    public List<Map<String, String>> getQuesAnsweList(Long questionId) {
        List<AssessmentQuestionDetailsPojo> assessmentQuestionDetailsPojoList = new ArrayList<>();
        List<Map<String, String>> topicList =new ArrayList<>();
        List<AssessmentQuestionDetails> assessmentQuestionDetails = bsAssessmentQuestionDetailsRepository.findAllByAssessmentDetailsId(questionId);
        for (AssessmentQuestionDetails assessmentQuestionDetails1 : assessmentQuestionDetails) {
            AssessmentQuestionDetailsPojo assessmentQuestionDetailsPojo = new AssessmentQuestionDetailsPojo();
            assessmentQuestionDetailsPojo.setMaxMarks(assessmentQuestionDetails1.getMaxMarks());
            Gson gson = new Gson();
            Type type = new TypeToken<List<Map<String, String>>>() {
            }.getType();
            topicList= gson.fromJson(assessmentQuestionDetails1.getAnswer(), type);
        }
        return topicList;
    }
    public List<TermPojo> getTermSemester(String semesterName) {
        List<Term> terms = termRepository.findAllBySemesterId(Long.parseLong(semesterName));
        List<TermPojo> list = BsUserMapper.mapTermEntityToPojo(terms);
        return list;
    }

    public List<ListChatOfAccountDto> getFirstLevelAccountMaster(Long id) {
        AccountMaster accountMaster = accountMasterRepository.findOne(id);
        AccountGroup accountGroup = accountGroupRepository.findOne(accountMaster.getAccountid());
        List<AccountMaster> accountMasters = accountMasterRepository.findByAgidAndAmaccountidIsNull(accountGroup);
        List<ListChatOfAccountDto> list = BsUserMapper.mapChartOfAccEntityToPojo(accountMasters);
        return list;
    }

    public List<ListChatOfAccountDto> secoundLevelChartOfAccount(Long firstLevelId) {
        AccountMaster accountMaster = accountMasterRepository.findOne(firstLevelId);
        List<AccountMaster> accountMaster1 = accountMasterRepository.findAllByAmaccountid(accountMaster);
        List<ListChatOfAccountDto> list = BsUserMapper.mapChartOfAccEntityToPojo(accountMaster1);
        return list;
    }

    public List<SubjectPojo> getclassSubject(String classId) {
        List<Subject> subjects = new ArrayList<>();
        List<SubjectPojo> subjectPojoList = new ArrayList<>();
        if (StringUtils.isEmpty(classId)) {
            subjects = subjectRepository.findAll();
            subjectPojoList = BsUserMapper.mapSubjectEntityToPojo(subjects);
        } else {
            subjects = subjectRepository.findAllByClassesId(Long.parseLong(classId));
            subjectPojoList = BsUserMapper.mapSubjectEntityToPojo(subjects);
        }
        return subjectPojoList;
    }

    public List<CityDTO> getStateCity(String stateId) {
        List<City> cityList=new ArrayList<>();
        if(!StringUtils.isEmpty(stateId)) {
            cityList = cityRepository.findAllByStateIdAndStatus(Long.parseLong(stateId), "Active");
        }
        List<CityDTO> city = BsUserMapper.mapCityEntityToPojo(cityList);
        return city;
    }

    public List<TopicPojo> getTopicListbyChapter(String chapterId) {
        List<TopicDetails> topicDetailsList=topicDetailsRepository.findAllByChapter(chapterId);
        List<TopicPojo> topicPojoList=BsUserMapper.mapTopicDetailsEntityToPojo(topicDetailsList);
        return topicPojoList;
    }

    public Agegroup saveAgegroup(AgegroupPojo agegroupPojo) {
        Agegroup agegroup = new Agegroup();
        agegroup = BsUserMapper.mapAgegroupPojoToEntity(agegroupPojo);
        agegroupRepository.save(agegroup);
        return agegroup;

    }

    public List<AgegroupPojo> getAgegroupList(String search) {
        List<Agegroup> list = agegroupRepository.findAll();
        List<AgegroupPojo> typePojos = BsUserMapper.mapAgegroupEntityToPojo(list);
        return typePojos;
    }

    public Boolean deleteAgegroup(AgegroupPojo details) {
        agegroupRepository.delete(details.getAgegroupId());
        return true;
    }


    public EnquiryFormPOJO saveEnqForm(EnquiryFormPOJO enquiryFormPOJO) {
        Gson gson = new Gson();
        EnquiryForm enq = new EnquiryForm();
        EnquiryFormPOJO enquiryFormPOJO1 = new EnquiryFormPOJO();
        if (enquiryFormPOJO.getEnquiryId() != null) {
            enq = enquiryRepository.findOne(enquiryFormPOJO.getEnquiryId());
        }
        enq.setEnquiryId(enquiryFormPOJO.getEnquiryId());
        enq.setEnquiryNo(enquiryFormPOJO.getEnquiryNo());
        enq.setEnqClass(enquiryFormPOJO.getEnqClass());
        enq.setEnqDate(enquiryFormPOJO.getEnqDate());
        enq.setStudentName(enquiryFormPOJO.getStudentName());
        enq.setTelephoneNo(enquiryFormPOJO.getTelephoneNo());
        enq.setAcademicDetailsOfStud(enquiryFormPOJO.getAcademicDetailsOfStud());
        enq.setAddress(enquiryFormPOJO.getAddress());
        enq.setEmailId(enquiryFormPOJO.getEmailId());
        enq.setReference(enquiryFormPOJO.getReference());
        enq.setLevel(enquiryFormPOJO.getGrade());
        enq.setSiblingGrade(enquiryFormPOJO.getSiblingGrade());
        enq.setStatus(enquiryFormPOJO.getStatus());
        enq.setSiblingDetails(enquiryFormPOJO.getSiblingDetails());
        enq.setTotalPaidAmt(enquiryFormPOJO.getTotalPaidAmt());
        enq.setFeeDeatils(gson.toJson(enquiryFormPOJO.getFeePojoList()));
        enq.setFatherName(enquiryFormPOJO.getFatherName());
        enq.setMotherName(enquiryFormPOJO.getMotherName());
        enq.setReferrel(enquiryFormPOJO.getReferrel());
        enq.setTransport(enquiryFormPOJO.getTransport());
        enq.setOption1(enquiryFormPOJO.getOption1());
        enq.setAnnualIncome(enquiryFormPOJO.getAnnualIncome());
        enq.setQualification(enquiryFormPOJO.getQualification());
        enq.setProfession(enquiryFormPOJO.getProfession());
        enq.setAnnualIncome(enquiryFormPOJO.getAnnualIncome());
        enq.setPick(enquiryFormPOJO.getPick());
        enquiryRepository.save(enq);
        enquiryFormPOJO1 = EnquiryFormMapper.mapEntityToPojo(enq);
        return enquiryFormPOJO1;
    }

    public AssessmentsPojo saveAssessments(AssessmentsPojo assessmentsPojo) {
        EnquiryForm enquiryForm = enquiryRepository.findOne(Long.parseLong(assessmentsPojo.getEnquiryId()));
        AssessmentsPojo assessments = new AssessmentsPojo();
        assessments.setStudentName(assessmentsPojo.getStudentName());
        assessments.setDate(assessmentsPojo.getDate());
        assessments.setGradeId(assessmentsPojo.getGradeId());
        assessments.setParentName(assessmentsPojo.getParentName());
        assessments.setPhoneNo(assessmentsPojo.getPhoneNo());
        assessments.setAssessmentStatus(assessmentsPojo.getAssessmentStatus());
        assessments.setFatherName(assessmentsPojo.getFatherName());
        assessments.setMotherName(assessmentsPojo.getMotherName());
        assessments.setEnquiryId(assessmentsPojo.getEnquiryId());
        assessments.setAssessmentStatus(assessmentsPojo.getAssessmentStatus());
        Gson gson = new Gson();
        assessments.setRemarks(gson.toJson(assessmentsPojo.getRemarksList()));
        enquiryForm.setAssessMentDetails(gson.toJson(assessments));
        enquiryRepository.save(enquiryForm);
        return assessmentsPojo;
    }



    public GradingMaster saveList(GradingMasterPojo gradingMasterPojo) {
        GradingMaster gradingMaster =new GradingMaster();
        if(gradingMasterRepository.findAll().size()>0)
            gradingMaster = gradingMasterRepository.findAll().get(0);
             if(gradingMaster.getGradeMasterId()!=null){
                 gradingMasterRepository.delete(gradingMaster.getGradeMasterId());
             }
            gradingMaster=BsUserMapper.mapGradingMasterPojoToEntity(gradingMasterPojo);
            gradingMasterRepository.save(gradingMaster);
        return gradingMaster;
    }


    public void saveCounslar(Long id, String remarks, String grade) {
        Gson gson = new Gson();
        EnquiryForm enquiryForm = enquiryRepository.findOne(id);
        Map<String, String> counslar = new HashMap<>();
        counslar.put("remarks", remarks);
        counslar.put("grade", grade);
        enquiryForm.setCounslarDetails(gson.toJson(counslar));
        enquiryRepository.save(enquiryForm);
        IntStream.of(new int[] {4, 7, 1, 8, 3, 9, 7}).filter((int i) -> i > 5).distinct().forEach(System.out::println);
    }


    public Boolean deleteEnqform(Long id) {
        enquiryRepository.delete(id);
        return true;
    }

    public String generateEnq() {
        String enquiryFormNo = null;
        List<EnquiryForm> enquiryForm = enquiryRepository.findAll();
        if (enquiryForm.size() == 0) {
            enquiryFormNo = "ENQ00001";
        } else {
            String form = enquiryForm.get(enquiryForm.size() - 1).getEnquiryNo();
            String[] splitForm = form.split("ENQ");
            int formno = Integer.parseInt(splitForm[1]);
            formno = formno + 1;
            enquiryFormNo = String.format("ENQ" + "%04d", formno);
        }
        return enquiryFormNo;
    }

    public String generateEmp() {
        String employeeFormNo = null;
        List<Employee> employees = bsEmployeeRepository.findAll();
        if (employees.size() == 0) {
            employeeFormNo = "EMP00001";

        } else {
            String form = employees.get(employees.size() - 1).getEmployeeCode();
            if (!form.contains("EMP00")) {
                employeeFormNo = "EMP00001";
            } else {
                String[] splitForm = form.split("EMP");
                int formno = Integer.parseInt(splitForm[1]);
                formno = formno + 1;
                employeeFormNo = String.format("EMP" + "%04d", formno);
            }
        }
        return employeeFormNo;
    }


    public String generateVis() {
        String visitorNo = null;
        List<VistorEntry> vistorEntries = bsVisitorEntryRepository.findAll();
        if (vistorEntries.size() == 0) {
            visitorNo = "VIS00001";

        } else {
            String form = vistorEntries.get(vistorEntries.size() - 1).getVisitorNo();
            String[] splitForm = form.split("VIS");
            int formno = Integer.parseInt(splitForm[1]);
            formno = formno + 1;
            visitorNo = String.format("VIS" + "%04d", formno);
        }
        return visitorNo;
    }

     public List<EnquiryFormPOJO> getEnqList(String status,String grade) {
        List<EnquiryForm> list = new ArrayList<>();
        if(!StringUtils.isEmpty(status)&&StringUtils.isEmpty(grade)){
            list= enquiryRepository.findByStatus(status);
        }
         else if(StringUtils.isEmpty(status)&&!StringUtils.isEmpty(grade)){
            list= enquiryRepository.findAllByLevel(grade);
        }
        else  if(!StringUtils.isEmpty(status)&&!StringUtils.isEmpty(grade)){
            list= enquiryRepository.findByStatusAndLevel(status,grade);
        }
        else{
            list= enquiryRepository.findAll();
        }

        List<EnquiryFormPOJO> pojoList = new ArrayList<>();
        EnquiryFormPOJO enquiryFormPOJO = new EnquiryFormPOJO();
        for (EnquiryForm enquiryForm : list) {
            enquiryFormPOJO = EnquiryFormMapper.mapEntityToPojo(enquiryForm);
            pojoList.add(enquiryFormPOJO);
        }

        return pojoList;
    }

    public List<FeeTypeMasterPojo> feeListOfGrade(Long gradeId) {
        List<FeeTypeMaster> feemaster = new ArrayList<>();
        GradeMaster gradeMaster = bsGrademasterRepository.findByGradeId(gradeId);
        feemaster = bsFeeTypeMasterRepository.findByLevelAndStatusAndType(gradeMaster, "Active", "registerFee");
        List<FeeTypeMasterPojo> ftPojoList = ObjectMapperUtils.mapAll(feemaster, FeeTypeMasterPojo.class);
        return ftPojoList;
    }

    public Term saveTerm(TermPojo termPojo) {
        Term term = new Term();
        if (termPojo.getTermId() != null) {
            term = termRepository.findByTermNameAndSemesterIdAndTermIdNotIn(termPojo.getTermName(), termPojo.getSemesterId(), termPojo.getTermId());
        } else {
            term = termRepository.findByTermNameAndSemesterId(termPojo.getTermName(),termPojo.getSemesterId());
        }
        if (term == null) {
            Term term1 = BsUserMapper.mapTermPojoToEntity(termPojo);
            termRepository.save(term1);
            return term1;
        } else {
            return null;
        }

    }

    public Employee saveEmployee(EmployeePojo employeePojo) {
        Employee employee = new Employee();
        User user=new User();
        if (employeePojo.getEmployeeId() != null) {
            employee = bsEmployeeRepository.findByEmployeeNameOrUserNameAndEmployeeIdNotIn(employeePojo.getEmployeeName(), employeePojo.getUserName(),employeePojo.getEmployeeId());
            user=bsUserRepository.findByUserName(employeePojo.getUserName());
        } else {
            employee = bsEmployeeRepository.findAllByEmployeeNameOrUserName(employeePojo.getEmployeeName(), employeePojo.getUserName());
            user=bsUserRepository.findByUserName(employeePojo.getUserName());
        }
        if (employee == null&&user==null) {
            Employee employee1 = BsUserMapper.mapEmployeePojoToEntity(employeePojo);
            String employeeFormNo = null;
            List<Employee> employees = bsEmployeeRepository.findAll();
            if (employees.size() == 0) {
                employeeFormNo = "EMP00001";

            } else {
                String form = employees.get(employees.size() - 1).getEmployeeCode();
                if (!form.contains("EMP00")) {
                    employeeFormNo = "EMP00001";
                } else {
                    String[] splitForm = form.split("EMP");
                    int formno = Integer.parseInt(splitForm[1]);
                    formno = formno + 1;
                    employeeFormNo = String.format("EMP" + "%04d", formno);
                }
            }
            bsEmployeeRepository.save(employee1);
            employee1.setEmployeeCode(employeeFormNo);
            SalaryConfig salaryConfig = bsSalaryConfigRepository.findByEmployeeId(employee1.getEmployeeId());
            if (salaryConfig != null) {
                bsSalaryConfigRepository.delete(salaryConfig);
            }
            salaryConfig = new SalaryConfig();
            salaryConfig.setBasicSal(employeePojo.getBasicSal());
            salaryConfig.setSalEffectiveDate(employeePojo.getSalEffectiveDate());
            salaryConfig.setEmpdeductions(employeePojo.getEmpdeductions());
            salaryConfig.setEmpearnings(employeePojo.getEmpearnings());
            salaryConfig.setEmployeeId(employee1.getEmployeeId());
            bsSalaryConfigRepository.save(salaryConfig);
            user =  bsUserRepository.findByOrganizationId(employee1.getEmployeeId());
            if (user == null) {
                user = new User();
            }
            Branches branches=branchesRepository.findOne(Long.parseLong(employeePojo.getEmpBranch()));
            user.setBranchCode(branches.getBranchCode());
            user.setOrganizationId(employee1.getEmployeeId());
            user.setPasswordUser(employeePojo.getPassword());
            user.setUserName(employeePojo.getUserName());
            user.setFull_name(employeePojo.getEmployeeName());
            user.setStatus(employeePojo.getStatus());
            bsUserRepository.save(user);
            return employee1;
        } else {
            return null;
        }

    }

    public List<EarningsPojo> getEarningList(String status, String searchText) {
        List<Earnings> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsEarningsRepository.findAllByStatus(status);
        } else {
            list = bsEarningsRepository.findByStatusAndEarningNameContaining(status, searchText);
        }
        List<EarningsPojo> earningDTOS = BsUserMapper.mapEarningsEntityToPojo(list);
        return earningDTOS;
    }

    public TeachingObservationForm saveTeachingObservation(TeachingObservationDTO teachingObservationDTO) {
        TeachingObservationForm teachingObservationForm = new TeachingObservationForm();
        if (teachingObservationDTO.getTeachingObservationId() != null) {
            teachingObservationForm = teachingObservationRepository.findByTeacherNameAndTeachingObservationIdNotIn(teachingObservationDTO.getTeacherName(), teachingObservationDTO.getTeachingObservationId());
        } else {
            teachingObservationForm = teachingObservationRepository.findByTeacherName(teachingObservationDTO.getTeacherName());
        }
        if (teachingObservationForm == null) {
            TeachingObservationForm teachingObservationForm1 = BsTeachingObservationMapper.mapTeachingObservationPojoToEntity(teachingObservationDTO);
            teachingObservationRepository.save(teachingObservationForm1);
            if (StringUtils.equalsIgnoreCase(teachingObservationDTO.getSignature(), "accepted")) {
                if (!StringUtils.isEmpty(teachingObservationDTO.getHrId())) {
                    HrApplication hrApplication = bsHrApplicRepository.findOne(Long.parseLong(teachingObservationDTO.getHrId()));
                    hrApplication.setInterviewerSchedStatus("Accepted");
                    hrApplication.setSalaryoffered(teachingObservationDTO.getSalaryoffered());
                    hrApplication.setReportingDate(teachingObservationDTO.getReportingDate());
                    bsHrApplicRepository.save(hrApplication);
                }
            }
            return teachingObservationForm1;
        } else {
            return null;
        }

    }

    public TeacherTable saveTeacherClearance(TeacherPojo teacherPojo) {
        TeacherTable teacherTable = BsTeacherClearanceMapper.mapTeacherClearancePojoToEntity(teacherPojo);
        teacherClearanceRepository.save(teacherTable);
        return teacherTable;
    }

    public List<TermPojo> getTermList(String semester) {
        List<Term> list = termRepository.findAllBySemesterId(Long.parseLong(semester));
        List<TermPojo> typePojos = BsUserMapper.mapTermEntityToPojo(list);
        return typePojos;
    }


    public List<TeachingObservationDTO> getTeacherObservationList(String search) {
        List<TeachingObservationForm> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = teachingObservationRepository.findAll();
        } else {
            list = teachingObservationRepository.findAllByTeacherName(search);
        }
        List<TeachingObservationDTO> teachingObservationDTOS = BsTeachingObservationMapper.mapTeachingObservationEntityToPojo(list);
        return teachingObservationDTOS;
    }

    public List<TeacherPojo> getTeacherClearanceList(String search) {
        List<TeacherTable> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = teacherClearanceRepository.findAll();
        } else {
            list = teacherClearanceRepository.findAllByTeacherNm(search);
        }
        List<TeacherPojo> teacherPojos = BsTeacherClearanceMapper.mapTeacherClearanceEntityToPojo(list);
        return teacherPojos;
    }

    public List<EmployeePojo> getEmployeeList(String search) {
        List<Employee> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = bsEmployeeRepository.findAll();
        } else {
            list = bsEmployeeRepository.findAllByEmployeeName(search);
        }
        List<EmployeePojo> employeePojos = BsUserMapper.mapEmployeeEntityToPojo(list);
        return employeePojos;
    }
//    public PeriodsDTO getPeriodList() {
//        List<PeriodsMaster> periodsMasters =periodsRepository.findAllByStatus("Active");
////        PeriodsDTO periodsDTO = new PeriodsDTO();
////        for(PeriodsMaster periodsMaster :periodsMasters){
////            periodsDTO.setPeriodId(periodsMaster.getPeriodId());
////            periodsDTO.setPeriodName(periodsMaster.getPeriodName());
////        }
//
//        return periodsDTO;
//    }
public List<PeriodsDTO> getPeriodList() {
    List<PeriodsMaster> list = periodsRepository.findAll();
    List<PeriodsDTO> typePojos = BsUserMapper.mapPeriodEntityToPojo(list);
    return typePojos;
}
    public void deleteEmployee(Long id) {
        bsEmployeeRepository.delete(bsEmployeeRepository.findByEmployeeId(id));
        bsUserRepository.delete(bsUserRepository.findByOrganizationId(id));
    }

    public void deleteApproval(Long id) {
        approvalRepository.delete(approvalRepository.findByApprovalId(id));
    }

    public Boolean deleteTerm(TermPojo details) {
        termRepository.delete(details.getTermId());
        return true;
    }


    public Boolean deleteTeacherClearance(TeacherPojo details) {
        teacherClearanceRepository.delete(details.getTeachingId());
        return true;
    }

    public Boolean DeleteTeacherObservation(TeachingObservationDTO details) {
        termRepository.delete(details.getTeachingObservationId());
        return true;
    }

    public Subject saveSubject(SubjectPojo subjectPojo) {
        Subject subject = new Subject();
        if (subjectPojo.getSubjectId() != null) {
            subject = subjectRepository.findBySubjectNameAndClassesIdAndSubjectIdNotIn(subjectPojo.getSubjectName(), subjectPojo.getClassesId(),subjectPojo.getSubjectId());
        } else {
            subject = subjectRepository.findBySubjectNameAndClassesId(subjectPojo.getSubjectName(),subjectPojo.getClassesId());
        }
        if (subject == null) {
            Subject subject1 = BsUserMapper.mapSubjectPojoToEntity(subjectPojo);
            subjectRepository.save(subject1);
            return subject1;
        } else {
            return null;
        }

    }

    public SubjectCategory saveSubjectCategory(SubjectCategoryPojo subjectCategoryPojo) {
        SubjectCategory subjectCategory = new SubjectCategory();
        subjectCategory = SubjectCategoryMapper.mapSubjectCategoryPojoToEntity(subjectCategoryPojo);
            subjectCategoryRepository.save(subjectCategory);
            return subjectCategory;
    }

    public List<SubjectPojo> getSubjectList(String search) {
        List<Subject> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = subjectRepository.findAllByStatus("Active");
        } else {
            list = subjectRepository.findAllBySubjectName(search);
        }
        List<SubjectPojo> typePojos = BsUserMapper.mapSubjectEntityToPojo(list);
        return typePojos;
    }

    public List<SubjectCategoryPojo> getSubjectCategoryList(String search) {
        List<SubjectCategory> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = subjectCategoryRepository.findAllByStatus("Active");
        } else {
            list = subjectCategoryRepository.findAllBySubjectCategroyName(search);
        }
        List<SubjectCategoryPojo> typePojos = SubjectCategoryMapper.mapcategoryEntityToPojo(list);
        return typePojos;
    }

    public List<GradeMasterPojo> getGradeList( ) {
        List<GradeMaster> gradeMasters =bsGrademasterRepository.findByGradeStatus("Active");
        List<GradeMasterPojo> gradeMasterPojoList = BsUserMapper.mapGradeEntityToPojo(gradeMasters);
        return gradeMasterPojoList;
    }

    public List<Subject> getSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects;
    }

    public List<Chapters> getChapter() {
        List<Chapters> chapters = chapterRepository.findAll();
        return chapters;
    }


    public List<LessonPlan> getAllDetails(String fromDate, String toDate, String studentGrade, String classa, String subjectId, String chapterId) {

        List<LessonPlan> lessonPlan = lessonPlanRepository.findAllByLevelAndClassesAndSubjectAndChapter(studentGrade, classa, subjectId, chapterId);

        return lessonPlan;
    }

    public Boolean deleteSubject(SubjectPojo details) {
        subjectRepository.delete(details.getSubjectId());
        return true;
    }

    public Boolean deleteSubjectCategory(SubjectCategoryPojo details) {
        subjectCategoryRepository.delete(details.getSubjectCategoryId());
        return true;
    }

    public Chapters saveChapter(ChapterPojo chapterPojo) {
        Chapters chapters = new Chapters();
        if (chapterPojo.getChapterId() != null) {
            chapters = chapterRepository.findAllByLevelIdAndClassIdAndSubjectIdAndChapterNameAndChapterIdNotIn(chapterPojo.getLevelId(),chapterPojo.getClassId(),chapterPojo.getSubjectId(),chapterPojo.getChapterName(),chapterPojo.getChapterId());
        } else {
            chapters = chapterRepository.findAllByLevelIdAndClassIdAndSubjectIdAndChapterName(chapterPojo.getLevelId(),chapterPojo.getClassId(),chapterPojo.getSubjectId(),chapterPojo.getChapterName());
        }
        if (chapters == null) {
            Chapters chapter = BsUserMapper.mapChapterPojoToEntity(chapterPojo);
            chapterRepository.save(chapter);
            return chapter;
        } else {
            return null;
        }

    }

    public List<ChapterPojo> getChapterList(String search) {
        List<Chapters> list = new ArrayList<>();
        if (StringUtils.isEmpty(search)) {
            list = chapterRepository.findAll();
        } else {
            list = chapterRepository.findAllByChapterName(search);
        }
        List<ChapterPojo> typePojos = BsUserMapper.mapChapterEntityToPojo(list);
        return typePojos;
    }

    public Boolean deleteChapter(ChapterPojo details) {
        chapterRepository.delete(details.getChapterId());
        return true;
    }

    public Topic saveTopic(TopicPojo topicPojo) {
        Topic topic = BsUserMapper.mapTopicPojoToEntity(topicPojo);
        Class classes=bsClassRepository.findOne(Long.parseLong(topicPojo.getClassId()));
        GradeMaster level=bsGrademasterRepository.findOne(Long.parseLong(topicPojo.getLevel()));
        Subject subject=subjectRepository.findOne(Long.parseLong(topicPojo.getSubject()));
        topic.setClassName(classes.getClassName());
        topic.setLevelName(level.getGradeName());
        topic.setSubjectName(subject.getSubjectName());
        topicRepository.save(topic);
        Gson gson=new Gson();
        Type type=new TypeToken<List<Map<String,String>>>(){}.getType();
        List<Map<String,String>> topicList=gson.fromJson(topicPojo.getTopicList(),type);
        if(topicPojo.getTopicId()!=null){
            List<TopicDetails> topicDetails=topicDetailsRepository.findByTopicId(topicPojo.getTopicId());
            topicDetailsRepository.delete(topicDetails);
        }
        for(Map<String,String> m:topicList){
            TopicDetails topicDetails=new TopicDetails();
            List<TopicDetails> list=topicDetailsRepository.findAllByChapterAndTopicName(m.get("chapter"),m.get("TopicName"));
            for(TopicDetails topicDetail:list){
                Topic topic1=topicRepository.findOne(topicDetail.getTopicId());
                if(StringUtils.equalsIgnoreCase(topic1.getClassId(),topic.getClassId())&&StringUtils.equalsIgnoreCase(topic1.getLevel(),topic.getLevel())&&StringUtils.equalsIgnoreCase(topic1.getSubject(),topic.getSubject())){
                    topicDetails=null;
                    break;
                }else {
                    topicDetails=new TopicDetails();
                }
            }
            if(topicDetails!=null) {
                topicDetails.setChapter(m.get("chapter"));
                topicDetails.setComponent(m.get("Assignment"));
                topicDetails.setTopicName(m.get("TopicName"));
                topicDetails.setStatus(m.get("status"));
                topicDetails.setTopicId(topic.getTopicId());
                topicDetailsRepository.save(topicDetails);
            }
        }
        return topic;
    }



    public AssesmentSubPojo saveAssSub(AssesmentSubPojo subPojo,Long id) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();




        List<String> studentNames = gson.fromJson(subPojo.getStudentsName(), type);
        AssesmentSubmission assesment = BsUserMapper.mapAssSubPojoToEntity(subPojo);
        assesmentSubmissionRepository.save(assesment);
        if(id!=null) {
            LessonPlan lessonPlan = lessonPlanRepository.findAllByLessonPlanId(id);
            lessonPlan.setAssesmentId(assesment.getAsId());
            lessonPlanRepository.save(lessonPlan);
        }
        List<AssesmentSubmissionDetails> assesmentSubmissionDetailsList=assesmentSubmissionDetailsRepository.findByAssesmentId(assesment.getAsId());
        assesmentSubmissionDetailsRepository.delete(assesmentSubmissionDetailsList);
        List<AssesmentSubmissionDetailsExcel> assesmentSubmissionDetailsExcelList=assesmentSubmissionDetailsExcelRepository.findByAssesmentId(assesment.getAsId());
        assesmentSubmissionDetailsExcelRepository.delete(assesmentSubmissionDetailsExcelList);
        for (String s : studentNames) {
            AssesmentSubmissionDetails assesmentSubmissionDetails=new AssesmentSubmissionDetails();
            assesmentSubmissionDetails.setAssesmentId(assesment.getAsId());
            assesmentSubmissionDetails.setStudentName(s);
            assesmentSubmissionDetails.setTopicDetails(gson.toJson(subPojo.getMapValue().get(s)));
            assesmentSubmissionDetailsRepository.save(assesmentSubmissionDetails);
            subPojo.setAsId(assesment.getAsId());
        }
        Map<String, List<LessonPlanPojo>> resultList = new HashMap<>();
        if(StringUtils.equalsIgnoreCase(subPojo.getFlag(),"DetailAssesment")) {
            List<LessonPlanPojo> studentList = new ArrayList<>();
            for (String s : studentNames) {
                studentList.addAll(subPojo.getMapValue().get(s));
            }
            studentNames=new ArrayList<>();
            Map<String, List<LessonPlanPojo>> list = studentList.parallelStream().collect(groupingBy(LessonPlanPojo::getStudentName));
            for (Map.Entry<String, List<LessonPlanPojo>> m : list.entrySet()) {
                LessonPlanPojo lessonPlanPojo1 = new LessonPlanPojo();
                for (LessonPlanPojo lessonPlanPojo : m.getValue()) {
                    lessonPlanPojo1.setTopic(subPojo.getTopicName());
                    lessonPlanPojo1.setMaxMarks(lessonPlanPojo.getMaxMarks() + lessonPlanPojo1.getMaxMarks());
                    lessonPlanPojo1.setMarks(lessonPlanPojo.getMarks() + lessonPlanPojo1.getMarks());
                }
                List<LessonPlanPojo> marksList = new ArrayList<>();
                marksList.add(lessonPlanPojo1);
                resultList.put(m.getKey(), marksList);
                studentNames.add(m.getKey());
            }
        }else {
            resultList=subPojo.getMapValue();
        }
        for (String s : studentNames) {
            AssesmentSubmissionDetailsExcel assesmentSubmissionDetailsExcel=new AssesmentSubmissionDetailsExcel();
            assesmentSubmissionDetailsExcel.setAssesmentId(assesment.getAsId());
            assesmentSubmissionDetailsExcel.setStudentName(s);
            assesmentSubmissionDetailsExcel.setTopicDetails(gson.toJson(resultList.get(s)));
            assesmentSubmissionDetailsExcelRepository.save(assesmentSubmissionDetailsExcel);
            subPojo.setAsId(assesment.getAsId());
        }
        return subPojo;
    }

    public StudentAssesmentMarksPojo saveStudentAss(StudentAssesmentMarksPojo subPojo) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> studentNames = gson.fromJson(subPojo.getStudentsName(), type);
        StudentAssesmentMarks assesment = BsUserMapper.mapStudentAssPojoToEntity(subPojo);
        studentAssesmentMarksRepository.save(assesment);
        List<StudentAssesmentMarksDetails> assesmentDetailsList=studentAssesmentMarksDetailsRepository.findByStudentAssesmentId(assesment.getSamId());
        studentAssesmentMarksDetailsRepository.delete(assesmentDetailsList);
        for (String s : studentNames) {
            StudentAssesmentMarksDetails assesmentSubmissionDetails=new StudentAssesmentMarksDetails();
            assesmentSubmissionDetails.setStudentAssesmentId(assesment.getSamId());
            assesmentSubmissionDetails.setStudentName(s);
            assesmentSubmissionDetails.setQuestionDetails(gson.toJson(subPojo.getMapValue().get(s)));
            studentAssesmentMarksDetailsRepository.save(assesmentSubmissionDetails);
            subPojo.setSamId(assesment.getSamId());
        }
        return subPojo;
    }
    public MarksSubmissionPojo saveMarksSubmission(MarksSubmissionPojo subPojo) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> studentNames = gson.fromJson(subPojo.getStudentName(), type);
        MarksSubmission assesment = BsUserMapper.mapMarksSubPojoToEntity(subPojo);
        marksSubmissionRepository.save(assesment);
        List<StudentAssesmentMarksDetails> assesmentDetailsList=studentAssesmentMarksDetailsRepository.findByStudentAssesmentId(assesment.getId());
        studentAssesmentMarksDetailsRepository.delete(assesmentDetailsList);
        for (String s : studentNames) {
            MarksSubmissionDetails marksSubmissionDetails=new MarksSubmissionDetails();
            marksSubmissionDetails.setMarksSubId(assesment.getId());
            marksSubmissionDetails.setStudentName(s);
            marksSubmissionDetails.setQuestionDetails(gson.toJson(subPojo.getMapValue().get(s)));
            marksSubmissionDetailsRepository.save(marksSubmissionDetails);
            subPojo.setId(assesment.getId());
        }
        return subPojo;
    }

    public LessonPlan saveAssLessonPlan(AssesmentSubPojo subPojo, Long id) {
        LessonPlan lessonPlan = lessonPlanRepository.findAllByLessonPlanId(id);
        subPojo.setAsId(lessonPlan.getAssesmentId());
        subPojo.setSubComponent(lessonPlan.getSubComponent());
        subPojo.setQuestionName(subPojo.getQuestionName());
        subPojo.setTopicName(subPojo.getTopicName());
        AssesmentSubPojo assesmentSubPojo = saveAssSub(subPojo,id);
        lessonPlan.setAssesmentId(assesmentSubPojo.getAsId());
        lessonPlan.setClasses(subPojo.getClassId());
        lessonPlan.setNewStatus("Completed");
        lessonPlanRepository.save(lessonPlan);
        return lessonPlan;
    }
//    public MarksSubmission saveMarksSubmission(MarksSubmissionPojo subPojo) {
//        MarksSubmission marksSubmission=new MarksSubmission();
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<String>>() {
//        }.getType();
//        List<String> studentNames = gson.fromJson(subPojo.getStudentName(), type);
//        for (String s : studentNames) {
//            AssesmentSubmissionDetails assesmentSubmissionDetails=new AssesmentSubmissionDetails();
//            assesmentSubmissionDetails.setStudentName(s);
//            assesmentSubmissionDetails.setTopicDetails(gson.toJson(subPojo.getMapValue().get(s)));
//            assesmentSubmissionDetailsRepository.save(assesmentSubmissionDetails);
//        }
//        marksSubmission.setClassId(subPojo.getClassId());
//        marksSubmission.setEmployee(subPojo.getEmployee());
//        marksSubmission.setSubjectId(subPojo.getSubjectId());
//        marksSubmission.setLevelId(subPojo.getLevelId());
//        marksSubmissionRepository.save(marksSubmission);
//        return marksSubmission;
//    }

    public LessonPlan edit(Long id) {
        LessonPlan lessonPlan = lessonPlanRepository.findAllByLessonPlanId(id);
        return lessonPlan;
    }

    public Boolean deleteTopic(TopicPojo details) {
        topicRepository.delete(details.getTopicId());
        topicDetailsRepository.delete(topicDetailsRepository.findByTopicId(details.getTopicId()));
        return true;
    }
    public Boolean deleteTT(TimeTablePojo timeTablePojo) {
        timeTableRepository.delete(timeTablePojo.getTtId());
        timeTableRepository.delete(timeTableRepository.findAllByTtId(timeTablePojo.getTtId()));
        return true;
    }

   public Boolean deleteList(GradingMasterPojo details) {
        gradingMasterRepository.delete(details.getGradeMasterId());
        return true;
    }

    public LessonPlan saveLessonPlan(LessonPlanPojo lessonPlanPojo) {
        LessonPlan lessonPlan = new LessonPlan();
        byte byteArray[];
        String fileName = FileSystemOperations.getImagesDirItem() + File.separator + "SP" + lessonPlanPojo.getLevel() + ".png";
        if (!StringUtils.isEmpty(lessonPlanPojo.getAttachFile())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(lessonPlanPojo.getAttachFile().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                lessonPlanPojo.setAttachFile(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (lessonPlanPojo.getLessonPlanId() != null) {
            lessonPlan = lessonPlanRepository.findByLevelAndClassesAndSubjectAndChapterAndTopicAndComponentAndSubComponentAndLessonPlanIdNotIn(lessonPlanPojo.getLevel(), lessonPlanPojo.getClasses(), lessonPlanPojo.getSubject(), lessonPlanPojo.getChapter(), lessonPlanPojo.getTopic(),lessonPlanPojo.getComponent(),lessonPlanPojo.getSubComponent(), lessonPlanPojo.getLessonPlanId());
        } else {
            lessonPlan = lessonPlanRepository.findByLevelAndClassesAndSubjectAndChapterAndTopicAndComponentAndSubComponent(lessonPlanPojo.getLevel(), lessonPlanPojo.getClasses(), lessonPlanPojo.getSubject(), lessonPlanPojo.getChapter(), lessonPlanPojo.getTopic(),lessonPlanPojo.getComponent(),lessonPlanPojo.getSubComponent());
        }
        if (lessonPlan == null) {
            LessonPlan plan = BsUserMapper.mapLessonPlanPojoToEntity(lessonPlanPojo);
            lessonPlanRepository.save(plan);
            return plan;
        } else {
            return null;
        }

    }
 public LessonPlanDetails saveLessonPlanDetails(LessonPlanPojo lessonPlanPojo) {
     LessonPlanDetails lessonPlanDetails = null;
     lessonPlanDetails=lessonPlanDetailsRepository.findByLessonPlanIdAndComponentAndSubComponent(lessonPlanPojo.getLessonPlanId(),lessonPlanPojo.getComponent(),lessonPlanPojo.getSubComponent());
     if(lessonPlanDetails==null){
         lessonPlanDetails = new LessonPlanDetails();
     }
     lessonPlanDetails.setAssessment(lessonPlanPojo.getAssessment());
     lessonPlanDetails.setLessonPlanId(lessonPlanPojo.getLessonPlanId());
     lessonPlanDetails.setComponent(lessonPlanPojo.getComponent());
     lessonPlanDetails.setSubComponent(lessonPlanPojo.getSubComponent());
     lessonPlanDetails.setWeightage(lessonPlanPojo.getNewComponent());
     lessonPlanDetails.setQuestionId(lessonPlanPojo.getQuestionId());
     lessonPlanDetails.setMaxMarks(String.valueOf(lessonPlanPojo.getMaxMarks()));
//     lessonPlanDetails = BsUserMapper.mapLessonPlanDetialsPojoToEntity(lessonPlanPojo);
     lessonPlanDetailsRepository.save(lessonPlanDetails);
     LessonPlan lessonPlan=lessonPlanRepository.findAllByLessonPlanId(lessonPlanPojo.getLessonPlanId());
     if(lessonPlan!=null){
         lessonPlan.setAssessment(lessonPlanPojo.getAssessment());
         lessonPlanRepository.save(lessonPlan);
     }
     return lessonPlanDetails;
    }


    public Sow saveSow(SowPojo sowPojo) {
        Sow sow = BsUserMapper.mapSowPojoToEntity(sowPojo);
        GradeMaster gradeMaster=bsGrademasterRepository.findByGradeId(sowPojo.getLevel());
        sow.setLevelName(gradeMaster.getGradeName());
        Subject subject=subjectRepository.findBySubjectId(sowPojo.getSubject());
        sow.setSubjectName(subject.getSubjectName());
        Chapters chapters=chapterRepository.findByChapterId(sowPojo.getChapter());
        sow.setChapterName(chapters.getChapterName());
        Class aClass=bsClassRepository.findByClassId(sowPojo.getClasses());
        sow.setClassesName(aClass.getClassName());
        Semester semester=bsSemesterRepository.findBySemesterId(sowPojo.getSemester());
        sow.setSemesterName(semester.getSemesterName());
        Term term=termRepository.findByTermId(sowPojo.getTerm());
        sow.setTermName(term.getTermName());
        sowRepository.save(sow);
        return sow;
    }

    public List<LessonPlanPojo> getLessonPlanList() {
        List<LessonPlan> list = lessonPlanRepository.findAll();
        List<LessonPlanPojo> typePojos = BsUserMapper.mapLessonPlanEntityToPojo(list);
        for(LessonPlanPojo pojo : typePojos){
            if(pojo.getLevel()!=null){
                GradeMaster gradeMaster=bsGrademasterRepository.findByGradeId(Long.parseLong(pojo.getLevel()));
                pojo.setLevel(gradeMaster.getGradeName());
            }
            if(pojo.getChapter()!=null){
                Chapters chapters=chapterRepository.findByChapterId(Long.parseLong(pojo.getChapter()));
                pojo.setChapter(chapters.getChapterName());
            }
            if(pojo.getTopic()!=null){
                TopicDetails topicDetails=topicDetailsRepository.findOne(Long.parseLong(pojo.getTopic()));
                pojo.setTopic(topicDetails.getTopicName());
            }
        }
        return typePojos;
    }


    public List<SowPojo> getSowList() {
        List<Sow> list = sowRepository.findAll();
        List<SowPojo> typePojos = BsUserMapper.mapSowPlanEntityToPojo(list);
        return typePojos;
    }

    public Boolean deleteSow(SowPojo details) {
        sowRepository.delete(details.getSowId());
        return true;
    }

    public Boolean deleteLessonPlan(LessonPlanPojo details) {
        lessonPlanRepository.delete(details.getLessonPlanId());
        return true;
    }

    public AssessmentsPojo getAssessMentList(Long enquiryId) {
        EnquiryForm enquiryForm = enquiryRepository.findOne(enquiryId);
        Gson gson = new Gson();
        Type type = new TypeToken<AssessmentsPojo>() {
        }.getType();
        AssessmentsPojo assessmentsPojo = gson.fromJson(enquiryForm.getAssessMentDetails(), type);
        return assessmentsPojo;

    }

    public BasePojo getNotificatuiPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<Notification> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "notificationId"));
        if (basePojo.isLastPage() == true) {
            List<Notification> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = notificationRepository.findAllByNotificationSubjectContainingAndNotificationStatus(searchText, status);
            } else {
                list1 = notificationRepository.findAllByNotificationStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "notificationId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Notification notification = new Notification();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "notificationId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            notification = notificationRepository.findFirstByNotificationSubjectContainingAndNotificationStatus(searchText, status, sort);
            list = notificationRepository.findAllByNotificationSubjectContainingAndNotificationStatus(searchText, status, pageable);
        } else {
            notification = notificationRepository.findFirstByNotificationStatus(status, sort);
            list = notificationRepository.findAllByNotificationStatus(status, pageable);
        }
        if (list.contains(notification)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<NotificationPojo> notificationPojoList = BsNotificationMapper.mapNotificationEntityToPojo(list);
        basePojo = calculatePagination(basePojo, notificationPojoList.size());
        basePojo.setList(notificationPojoList);
        return basePojo;
    }
    public BasePojo getPaginatedTopicList(BasePojo basePojo, String searchText) {
        List<Topic> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "topicId"));
        if (basePojo.isLastPage() == true) {
            List<Topic> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = topicRepository.findAllBySubjectNameContainingOrLevelNameContainingOrClassNameContaining(searchText,searchText,searchText);
            } else {
                list1 = topicRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "topicId"));
        }
        Topic topic = new Topic();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "topicId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            topic = topicRepository.findBySubjectNameContainingOrLevelNameContainingOrClassNameContaining(sort,searchText,searchText,searchText);
            list = topicRepository.findBySubjectNameContainingOrLevelNameContainingOrClassNameContaining( pageable,searchText,searchText,searchText);
        } else {
            topic = topicRepository.findFirstBy(sort);
            list = topicRepository.findAllBy(pageable);
        }
        if (list.contains(topic)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<TopicPojo> notificationPojoList = BsUserMapper.mapTopicEntityToPojo(list);
        Gson gson=new Gson();
        for(TopicPojo topicPojo:notificationPojoList){
            List<TopicDetails> topicDetailsList=topicDetailsRepository.findByTopicId(topicPojo.getTopicId());
            List<Map<String,String>> mapList=new ArrayList<>();
            for(TopicDetails topicDetails:topicDetailsList){
                Map<String,String> map=new HashMap<>();
                map.put("chapter",topicDetails.getChapter());
                map.put("Assignment",topicDetails.getComponent());
                map.put("TopicName",topicDetails.getTopicName());
                map.put("status",topicDetails.getStatus());
                mapList.add(map);
            }
            topicPojo.setTopicList(gson.toJson(mapList));
        }
        basePojo = calculatePagination(basePojo, notificationPojoList.size());
        basePojo.setList(notificationPojoList);
        return basePojo;
    }

    public TimetableDto generateReport(String LevelName,String classId) {
        TimetableDto dto = new TimetableDto();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "periodFrom"));
        List<PeriodsMaster> master = periodsRepository.findAll(sort);
        List<TimeTable> list = new ArrayList<>();
        if (StringUtils.isEmpty(LevelName) && StringUtils.isEmpty(classId)) {
            list = timeTableRepository.findAll();
        } else {
            list = timeTableRepository.findAllByLevelAndClassName(LevelName,classId);
        }
        List<TimeTablePojo> typePojos = BsUserMapper.mapTimetableEntityToPojo(list);
        for(TimeTablePojo pojo: typePojos){
            if(pojo.getLevel() != null){
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(pojo.getLevel()));
                pojo.setLevelName(gradeMaster.getGradeName());

            }
            if(pojo.getClassName()!=null){
                Class aClass = bsClassRepository.findOne(Long.parseLong(pojo.getClassName()));
                pojo.setClsName(aClass.getClassName());
            }
            if(pojo.getSubject()!=null){
                Subject c = subjectRepository.findOne(Long.parseLong(pojo.getSubject()));
                pojo.setSubName(c.getSubjectName());
            }
            if(pojo.getTeacher()!=null){
                Employee emp = bsEmployeeRepository.findOne(Long.parseLong(pojo.getTeacher()));
                pojo.setTeacherName(emp.getEmployeeName());
            }
            if(pojo.getPeriod()!=null){
                PeriodsMaster per = periodsRepository.findOne(Long.parseLong(pojo.getPeriod()));
                pojo.setPeriodName(per.getPeriodName());
                pojo.setPeriodFrom(per.getPeriodFrom());
                pojo.setPeriodTo(per.getPeriodTo());
            }
            if(pojo.getFacility()!=null){
                FacilityDetails det = facilityDetailsRepository.findOne(Long.parseLong(pojo.getFacility()));
                pojo.setFacilityName(det.getFacilityName());
            }

        }
        if(!StringUtils.isEmpty(LevelName)) {
            GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(LevelName));
            dto.setLevel(gradeMaster.getGradeName());
        }
        if(!StringUtils.isEmpty(classId)) {
            Class aClass = bsClassRepository.findOne(Long.parseLong(classId));
            dto.setClassName(aClass.getClassName());
        }
        dto.setList(typePojos);
        dto.setPeriodsList(master);
        return dto;
    }

    public PermissionDto getPermissionLevelsList() {
        PermissionDto dto = new PermissionDto();
        List<Permission> list = permissionRepository.findAll();
        List<Long> lists = permissionRepository.findByPermissionId();
        List<PermissionDto> permissionDtos1 = new ArrayList<>();
        Map<Long,List<Long>> resultList=new HashMap<>();

        for(Long permission:lists){
            List<Long> sublevels=new ArrayList<>();
            List<Permission> permissionList=permissionRepository.findAllByParentId(permission);
            for(Permission permission1:permissionList){
                sublevels.add(permission1.getPermissionId());
            }
            resultList.put(permission,sublevels);
        }
        Map<Long,PermissionDto> map=new HashMap<>();

        for(Permission permission:list){
            PermissionDto permissionDto1=new PermissionDto();
            permissionDto1.setId(permission.getPermissionId().toString());
            permissionDto1.setName(permission.getPermissionName());
            permissionDtos1.add(permissionDto1);
            map.put(permission.getPermissionId(),permissionDto1);

        }

        dto.setCategory(map);
        dto.setParentCategory(resultList);
        return dto;
    }
    public BasePojo getEmailPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<EmailTemplateMaster> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "emailId"));
        if (basePojo.isLastPage() == true) {
            List<EmailTemplateMaster> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = emailTemplateRepository.findAllByEmailNameContainingAndEmailStatus(searchText, status);
            } else {
                list1 = emailTemplateRepository.findAllByEmailStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "emailId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        EmailTemplateMaster emailTemplateMaster = new EmailTemplateMaster();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "emailId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            emailTemplateMaster = emailTemplateRepository.findFirstByEmailNameContainingAndEmailStatus(searchText, status, sort);
            list = emailTemplateRepository.findAllByEmailNameContainingAndEmailStatus(searchText, status, pageable);
        } else {
            emailTemplateMaster = emailTemplateRepository.findFirstByEmailStatus(status, sort);
            list = emailTemplateRepository.findAllByEmailStatus(status, pageable);
        }
        if (list.contains(emailTemplateMaster)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<EmailTemplatePojo> emailTemplatePojoList = BsEmailMapper.mapEmailEntityToPojo(list);
        basePojo = calculatePagination(basePojo, emailTemplatePojoList.size());
        basePojo.setList(emailTemplatePojoList);
        return basePojo;
    }

    public BasePojo getTrainingModulePaginatedList(String status, BasePojo basePojo, String searchText) {
        List<TrainingModule> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "trainingId"));
        if (basePojo.isLastPage() == true) {
            List<TrainingModule> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = trainingModuleRepository.findAllByTrainingModuleNameContainingAndTrainingStatus(searchText, status);
            } else {
                list1 = trainingModuleRepository.findAllByTrainingStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "trainingId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        TrainingModule trainingModule = new TrainingModule();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "trainingId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            trainingModule = trainingModuleRepository.findFirstByTrainingModuleNameContainingAndTrainingStatus(searchText, status, sort);
            list = trainingModuleRepository.findAllByTrainingModuleNameContainingAndTrainingStatus(searchText, status, pageable);
        } else {
            trainingModule = trainingModuleRepository.findFirstByTrainingStatus(status, sort);
            list = trainingModuleRepository.findAllByTrainingStatus(status, pageable);
        }
        if (list.contains(trainingModule)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<TrainingModulePojo> trainingModulePojoList = BsTrainingModuleMapper.mapTrainingEntityToPojo(list);
        basePojo = calculatePagination(basePojo, trainingModulePojoList.size());
        basePojo.setList(trainingModulePojoList);
        return basePojo;
    }

    public BasePojo getDoctorPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<DoctorPanelMaster> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "doctorpanelId"));
        if (basePojo.isLastPage() == true) {
            List<DoctorPanelMaster> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = doctorPanelRepository.findAllByDoctorNameContainingAndDoctorStatus(searchText, status);
            } else {
                list1 = doctorPanelRepository.findAllByDoctorStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "doctorpanelId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        DoctorPanelMaster doctorPanelMaster = new DoctorPanelMaster();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "doctorpanelId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            doctorPanelMaster = doctorPanelRepository.findFirstByDoctorNameContainingAndDoctorStatus(searchText, status, sort);
            list = doctorPanelRepository.findAllByDoctorNameContainingAndDoctorStatus(searchText, status, pageable);
        } else {
            doctorPanelMaster = doctorPanelRepository.findFirstByDoctorStatus(status, sort);
            list = doctorPanelRepository.findAllByDoctorStatus(status, pageable);
        }
        if (list.contains(doctorPanelMaster)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<DoctorpanelPojo> doctorpanelPojoList = BsDoctorpanelMapper.mapDoctorEntityToPojo(list);
        basePojo = calculatePagination(basePojo, doctorpanelPojoList.size());
        basePojo.setList(doctorpanelPojoList);
        return basePojo;
    }

    public BasePojo getSemesterPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<Semester> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "semesterId"));
        if (basePojo.isLastPage() == true) {
            List<Semester> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsSemesterRepository.findAllBySemesterNameContainingAndSemesterStatus(searchText, status);
            } else {
                list1 = bsSemesterRepository.findAllBySemesterStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "semesterId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Semester semester = new Semester();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "semesterId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            semester = bsSemesterRepository.findFirstBySemesterNameContainingAndSemesterStatus(searchText, status, sort);
            list = bsSemesterRepository.findAllBySemesterNameContainingAndSemesterStatus(searchText, status, pageable);
        } else {
            semester = bsSemesterRepository.findFirstBySemesterStatus(status, sort);
            list = bsSemesterRepository.findAllBySemesterStatus(status, pageable);
        }
        if (list.contains(semester)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<SemesterPojo> semesterPojoList = BsUserMapper.mapSemesterEntityToPojo(list);
        basePojo = calculatePagination(basePojo, semesterPojoList.size());
        basePojo.setList(semesterPojoList);
        return basePojo;
    }

    public List<AssesmentCreatorPojo> getCreatorList(){
        List<AssesmentCreator> list = new ArrayList<>();
        list = assesmentCreatorRepository.findAll();
        List<AssesmentCreatorPojo> assesmentCreatorPojos = BsUserMapper.mapCreatorEntityToPojo(list);
        for(AssesmentCreatorPojo s : assesmentCreatorPojos){
            if(s.getLevelId()!=null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(s.getLevelId()));
                s.setLevelName(gradeMaster.getGradeName());
            }

            if(s.getSemesterId()!=null) {
                Semester semester = bsSemesterRepository.findOne(Long.parseLong(s.getSemesterId()));
                s.setSemesterName(semester.getSemesterName());
            }

            if(s.getTermId()!=null) {
                Term term = termRepository.findOne(Long.parseLong(s.getTermId()));
                s.setTermName(term.getTermName());
            }
        }
        return  assesmentCreatorPojos;
    }

    public List<StudentAssesmentMarks> getStudentAssesmentList(){
        List<StudentAssesmentMarks> studentAssesmentMarksList = new ArrayList<>();
        List<StudentAssesmentMarks> studentAssesmentMarkss =studentAssesmentMarksRepository.findAll();
        for(StudentAssesmentMarks studentAssesmentMarks1:studentAssesmentMarkss){
           StudentAssesmentMarks studentAssesmentMarks= new StudentAssesmentMarks();
           studentAssesmentMarks.setSamId(studentAssesmentMarks1.getSamId());
            if (studentAssesmentMarks1.getLevel() != null) {
           GradeMaster gradeMaster = bsGrademasterRepository.findByGradeId(Long.parseLong(studentAssesmentMarks1.getLevel()));
           if(gradeMaster!=null)
           studentAssesmentMarks.setLevel(gradeMaster.getGradeName());}
            if (studentAssesmentMarks1.getChapter() != null) {
           Chapters chapters = chapterRepository.findByChapterId(Long.parseLong(studentAssesmentMarks1.getChapter()));
           studentAssesmentMarks.setChapter(chapters.getChapterName());}
            if (studentAssesmentMarks1.getSubject() != null) {
           Subject subject = subjectRepository.findBySubjectId(Long.parseLong(studentAssesmentMarks1.getSubject()));
           studentAssesmentMarks.setSubject(subject.getSubjectName());}
            if (studentAssesmentMarks1.getClassId() != null) {
           Class aClass = bsClassRepository.findByClassId(Long.parseLong(studentAssesmentMarks1.getClassId()));
           if(aClass!=null)
           studentAssesmentMarks.setClassId(aClass.getClassName());}
           studentAssesmentMarksList.add(studentAssesmentMarks);
       }
        return  studentAssesmentMarksList;

    }


    public List<ClassPojo> getClassList(String status, String searchText) {
        List<Class> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsClassRepository.findAllByClassStatus(status);
        } else {
            list = bsClassRepository.findByClassStatusAndClassNameContaining(status, searchText);
        }
        List<ClassPojo> classPojos = BsUserMapper.mapClassEntityToPojo(list);

        for(ClassPojo s : classPojos){
            if(s.getBranchId()!= null) {
                Branches branches = branchesRepository.findOne(s.getBranchId());
                if (s.getLevelId() != null) {
                    GradeMaster gradeMaster = bsGrademasterRepository.findOne(s.getLevelId());
                    s.setLevelName(gradeMaster.getGradeName());
                }
                s.setBranchName(branches.getBranchName());
            }
        }
//
        return classPojos;
    }

    public BasePojo getClassPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<Class> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "classId"));
        if (basePojo.isLastPage() == true) {
            List<Class> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsClassRepository.findAllByClassNameContainingAndClassStatus(searchText, status);
            } else {
                list1 = bsClassRepository.findAllByClassStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "classId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Class aClass = new Class();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "classId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            aClass = bsClassRepository.findFirstByClassNameContainingAndClassStatus(searchText, status, sort);
            list = bsClassRepository.findAllByClassNameContainingAndClassStatus(searchText, status, pageable);
        } else {
            aClass = bsClassRepository.findFirstByClassStatus(status, sort);
            list = bsClassRepository.findAllByClassStatus(status, pageable);
        }
        if (list.contains(aClass)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<ClassPojo> classPojoList = BsUserMapper.mapClassEntityToPojo(list);
        for(ClassPojo s : classPojoList){
            if(s.getBranchId()!=null) {
                Branches branches = branchesRepository.findOne(s.getBranchId());
                s.setBranchName(branches.getBranchName());
            }
            if(s.getLevelId()!=null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(s.getLevelId());
                s.setLevelName(gradeMaster.getGradeName());
            }
        }
        basePojo = calculatePagination(basePojo, classPojoList.size());
        basePojo.setList(classPojoList);
        return basePojo;
    }


    public BasePojo getSuppliersPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<Suppliers> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "suppliersId"));
        if (basePojo.isLastPage() == true) {
            List<Suppliers> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsSuppliersRepository.findAllBysuppliersNameContainingAndSuppliersStatus(searchText, status);
            } else {
                list1 = bsSuppliersRepository.findAllBySuppliersStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "suppliersId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Suppliers suppliers = new Suppliers();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "suppliersId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            suppliers = bsSuppliersRepository.findFirstBySuppliersNameContainingAndSuppliersStatus(searchText, status, sort);
            list = bsSuppliersRepository.findAllBySuppliersNameContainingAndSuppliersStatus(searchText, status, pageable);
        } else {
            suppliers = bsSuppliersRepository.findFirstBySuppliersStatus(status, sort);
            list = bsSuppliersRepository.findAllBySuppliersStatus(status, pageable);
        }
        if (list.contains(suppliers)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<SuppliersPojo> suppliersPojoList = BsUserMapper.mapSuppliersEntityToPojo(list);
        basePojo = calculatePagination(basePojo, suppliersPojoList.size());
        basePojo.setList(suppliersPojoList);
        return basePojo;
    }

    public BasePojo getRemaindersPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<Remainders> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "remainderId"));
        if (basePojo.isLastPage() == true) {
            List<Remainders> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsRemaindersRepository.findAllByRemainderNameContainingAndRemainderStatus(searchText, status);
            } else {
                list1 = bsRemaindersRepository.findAllByRemainderStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "remainderId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Remainders remainders = new Remainders();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "remainderId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            remainders = bsRemaindersRepository.findFirstByRemainderNameContainingAndRemainderStatus(searchText, status, sort);
            list = bsRemaindersRepository.findAllByRemainderNameContainingAndRemainderStatus(searchText, status, pageable);
        } else {
            remainders = bsRemaindersRepository.findFirstByRemainderStatus(status, sort);
            list = bsRemaindersRepository.findAllByRemainderStatus(status, pageable);
        }
        if (list.contains(remainders)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<RemaindersPojo> remaindersPojoList = BsUserMapper.mapRemaindersEntityToPojo(list);
        basePojo = calculatePagination(basePojo, remaindersPojoList.size());
        basePojo.setList(remaindersPojoList);
        return basePojo;
    }

    public BasePojo getDesignationPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<Designation> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "designationId"));
        if (basePojo.isLastPage() == true) {
            List<Designation> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsDesignationRepository.findAllByDesignationNameContainingAndDesignationStatus(searchText, status);
            } else {
                list1 = bsDesignationRepository.findAllByDesignationStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "designationId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Designation designation = new Designation();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "designationId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            designation = bsDesignationRepository.findFirstByDesignationNameContainingAndDesignationStatus(searchText, status, sort);
            list = bsDesignationRepository.findAllByDesignationNameContainingAndDesignationStatus(searchText, status, pageable);
        } else {
            designation = bsDesignationRepository.findFirstByDesignationStatus(status, sort);
            list = bsDesignationRepository.findAllByDesignationStatus(status, pageable);
        }
        if (list.contains(designation)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<DesignationPojo> designationPojoList = BsUserMapper.mapDesignationEntityToPojo(list);
        basePojo = calculatePagination(basePojo, designationPojoList.size());
        basePojo.setList(designationPojoList);
        return basePojo;
    }

    public BasePojo getPaginatedLeaveList(String status, BasePojo basePojo, String searchText) {
        List<LeaveMaster> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "leaveId"));
        if (basePojo.isLastPage() == true) {
            List<LeaveMaster> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = leaveRepository.findAllByLeaveTypeContainingAndStatus(searchText, status);
            } else {
                list1 = leaveRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "leaveId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        LeaveMaster leaveMaster = new LeaveMaster();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "leaveId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            leaveMaster = leaveRepository.findFirstByLeaveTypeContainingAndStatus(searchText, status, sort);
            list = leaveRepository.findAllByLeaveTypeContainingAndStatus(searchText, status, pageable);
        } else {
            leaveMaster = leaveRepository.findFirstByStatus(status, sort);
            list = leaveRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(leaveMaster)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<LeaveDTO> leaveDTOList = BsLeaveMapper.mapleaveEntityToPojo(list);
        basePojo = calculatePagination(basePojo, leaveDTOList.size());
        basePojo.setList(leaveDTOList);
        return basePojo;
    }

    public BasePojo getContactList(String status, BasePojo basePojo, String searchText) {
        List<OtherContacts> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "otherContactId"));
        if (basePojo.isLastPage() == true) {
            List<OtherContacts> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = contactRepository.findAllByFullNameContainingAndStatus(searchText, status);
            } else {
                list1 = contactRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "otherContactId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        OtherContacts otherContacts = new OtherContacts();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "otherContactId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            otherContacts = contactRepository.findFirstByFullNameContainingAndStatus(searchText, status, sort);
            list = contactRepository.findAllByFullNameContainingAndStatus(searchText, status, pageable);
        } else {
            otherContacts = contactRepository.findFirstByStatus(status, sort);
            list = contactRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(otherContacts)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<OtherContactsDTO> otherContactsDTOList = BsContactMapper.mapContactEntityToPojo(list);
        basePojo = calculatePagination(basePojo, otherContactsDTOList.size());
        basePojo.setList(otherContactsDTOList);
        return basePojo;
    }

    public BasePojo getPaginatedAcctGrpList(String status, BasePojo basePojo, String searchText) {
        List<AccountGroup> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "accountId"));
        if (basePojo.isLastPage() == true) {
            List<AccountGroup> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = accountGroupRepository.findAllByAccountNameContainingAndStatus(searchText, status);
            } else {
                list1 = accountGroupRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "accountId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        AccountGroup accountGroup = new AccountGroup();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "accountId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            accountGroup = accountGroupRepository.findFirstByAccountNameContainingAndStatus(searchText, status, sort);
            list = accountGroupRepository.findAllByAccountNameContainingAndStatus(searchText, status, pageable);
        } else {
            accountGroup = accountGroupRepository.findFirstByStatus(status, sort);
            list = accountGroupRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(accountGroup)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<AccountGroupPojo> accountGroupPojos = BsUserMapper.mapAccountGroupEntityToPojo(list);
        basePojo = calculatePagination(basePojo, accountGroupPojos.size());
        basePojo.setList(accountGroupPojos);
        return basePojo;
    }


    public BasePojo getPaginatedAcctTypeList(String status, BasePojo basePojo, String searchText) {
        List<AccountType> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "accountId"));
        if (basePojo.isLastPage() == true) {
            List<AccountType> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = accountTypeRepository.findAllByAccountNameContainingAndStatus(searchText, status);
            } else {
                list1 = accountTypeRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "accountId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        AccountType accountType = new AccountType();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "accountId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            accountType = accountTypeRepository.findFirstByAccountNameContainingAndStatus(searchText, status, sort);
            list = accountTypeRepository.findAllByAccountNameContainingAndStatus(searchText, status, pageable);
        } else {
            accountType = accountTypeRepository.findFirstByStatus(status, sort);
            list = accountTypeRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(accountType)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<AccountTypePojo> accountTypePojos = BsUserMapper.mapAccountTypeEntityToPojo(list);
        basePojo = calculatePagination(basePojo, accountTypePojos.size());
        basePojo.setList(accountTypePojos);
        return basePojo;
    }

    public BasePojo getTrainerPaginatedList(String status, BasePojo basePojo, String searchText) {
        List<Trainer> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        if (basePojo.isLastPage() == true) {
            List<Trainer> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = trainerRepository.findAllByNameContainingAndStatus(searchText, status);
            } else {
                list1 = trainerRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Trainer trainer = new Trainer();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            trainer = trainerRepository.findFirstByNameContainingAndStatus(searchText, status, sort);
            list = trainerRepository.findAllByNameContainingAndStatus(searchText, status, pageable);
        } else {
            trainer = trainerRepository.findFirstByStatus(status, sort);
            list = trainerRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(trainer)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<TrainerPojo> trainerPojoList = BsTrainerMapper.mapEntityToPojo(list);
        basePojo = calculatePagination(basePojo, trainerPojoList.size());
        basePojo.setList(trainerPojoList);
        return basePojo;
    }

    public BasePojo getPaginatedTermList(String status, BasePojo basePojo, String searchText) {
        List<Term> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "termId"));
        if (basePojo.isLastPage() == true) {
            List<Term> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = termRepository.findAllByTermNameContainingAndStatus(searchText, status);
            } else {
                list1 = termRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "termId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Term term = new Term();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "termId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            term = termRepository.findFirstByTermNameContainingAndStatus(searchText, status, sort);
            list = termRepository.findAllByTermNameContainingAndStatus(searchText, status, pageable);
        } else {
            term = termRepository.findFirstByStatus(status, sort);
            list = termRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(term)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<TermPojo> termPojoList = BsUserMapper.mapTermEntityToPojo(list);
        for(TermPojo pojo:termPojoList){
            if(pojo.getSemesterId()!=null){
                Semester semester=bsSemesterRepository.findOne(pojo.getSemesterId());
                pojo.setSemesterName(semester.getSemesterName());
            }
        }
        basePojo = calculatePagination(basePojo, termPojoList.size());
        basePojo.setList(termPojoList);
        return basePojo;
    }

    public List<StudentAttendencePojo> getStudentList() {
        List<StudentAttendence> list = bsstudentAttendanceRepository.findAll();
        List<StudentAttendencePojo> listPojo = BsStudentAttendenceMapper.mapStudentAttendanceEntityToPojo(list);
        return listPojo;
    }

    public List<Student> getstdListBasedOnClassAndLevel(String level, String classes) {
        GradeMaster gradeMaster = bsGrademasterRepository.findByGradeId(Long.parseLong(level));
        Class clas = bsClassRepository.findOne(Long.parseLong(classes));
        List<Student> studentList = new ArrayList<>();
        if (clas != null&&gradeMaster!=null) {
            studentList = bsStudentRepository.findAllByLevelAndClassId(gradeMaster, clas.getClassId());
        }
        return studentList;
    }

    public List<AssesmentCreatorPojo> getAssesmentListBasedOnAll(String semesterId,String term,String level,String classId,String subject,String chapterId,String topicId) {
        List<AssesmentCreator> assesmentCreatorList= new ArrayList<>();
        if(semesterId!=null&&term!=null&&level!=null&&classId!=null&&subject!=null&&chapterId!=null&&topicId!=null) {
            assesmentCreatorList = assesmentCreatorRepository.findAllBySemesterIdContainingAndTermIdContainingAndLevelIdContainingAndClassIdContainingAndSubjectIdContainingAndChapterIdContainingAndTopicIdContaining(semesterId,term,level,classId,subject,chapterId,topicId);
        }
        List<AssesmentCreatorPojo> creatorPojos=BsUserMapper.mapAssCreatorEntityToPojo(assesmentCreatorList);

        return creatorPojos;
    }

    public BasePojo getPaginatedSubjectList(String status, BasePojo basePojo, String searchText) {
        List<Subject> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "subjectId"));
        if (basePojo.isLastPage() == true) {
            List<Subject> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = subjectRepository.findAllBySubjectNameContainingAndStatus(searchText, status);
            } else {
                list1 = subjectRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "subjectId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Subject subject = new Subject();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "subjectId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            subject = subjectRepository.findFirstBySubjectNameContainingAndStatus(searchText, status, sort);
            list = subjectRepository.findAllBySubjectNameContainingAndStatus(searchText, status, pageable);
        } else {
            subject = subjectRepository.findFirstByStatus(status, sort);
            list = subjectRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(subject)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<SubjectPojo> subjectPojoList = BsUserMapper.mapSubjectEntityToPojo(list);
        for(SubjectPojo s : subjectPojoList) {
            if (s.getClassesId() != null) {
                Class aClass = bsClassRepository.findOne(s.getClassesId());
                s.setClassesName(aClass.getClassName());
            }
        }
        basePojo = calculatePagination(basePojo, subjectPojoList.size());
        basePojo.setList(subjectPojoList);
        return basePojo;
    }
    public BasePojo getPaginatedSubjectCategoryList(String status, BasePojo basePojo, String searchText) {
        List<SubjectCategory> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "subjectCategoryId"));
        if (basePojo.isLastPage() == true) {
            List<SubjectCategory> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = subjectCategoryRepository.findAllBySubjectCategroyNameContainingAndStatus(searchText, status);
            } else {
                list1 = subjectCategoryRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "subjectCategoryId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        SubjectCategory subjectCategory = new SubjectCategory();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "subjectCategoryId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            subjectCategory = subjectCategoryRepository.findFirstBySubjectCategroyNameContainingAndStatus(searchText, status, sort);
            list = subjectCategoryRepository.findAllBySubjectCategroyNameContainingAndStatus(searchText, status, pageable);
        } else {
            subjectCategory = subjectCategoryRepository.findFirstByStatus(status, sort);
            list = subjectCategoryRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(subjectCategory)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<SubjectCategoryPojo> subjectPojoList = SubjectCategoryMapper.mapcategoryEntityToPojo(list);
        basePojo = calculatePagination(basePojo, subjectPojoList.size());
        basePojo.setList(subjectPojoList);
        return basePojo;
    }

    public BasePojo getPaginatedEarningList(String status, BasePojo basePojo, String searchText) {
        List<Earnings> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "earningId"));
        if (basePojo.isLastPage() == true) {
            List<Earnings> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsEarningsRepository.findAllByEarningNameContainingAndStatus(searchText, status);
            } else {
                list1 = bsEarningsRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "earningId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Earnings earnings = new Earnings();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "earningId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            earnings = bsEarningsRepository.findFirstByEarningNameContainingAndStatus(searchText, status, sort);
            list = bsEarningsRepository.findAllByEarningNameContainingAndStatus(searchText, status, pageable);
        } else {
            earnings = bsEarningsRepository.findFirstByStatus(status, sort);
            list = bsEarningsRepository.findByStatus(status, pageable);
        }
        if (list.contains(earnings)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<EarningsPojo> earningsPojoList = BsUserMapper.mapEarningsEntityToPojo(list);
        basePojo = calculatePagination(basePojo, earningsPojoList.size());
        basePojo.setList(earningsPojoList);
        return basePojo;
    }

    public BasePojo getpaginatedCheckList(String status, BasePojo basePojo, String searchText) {
        List<CheckListMaster> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "checkId"));
        if (basePojo.isLastPage() == true) {
            List<CheckListMaster> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = checkRepository.findAllByAccountNameContainingAndStatus(searchText, status);
            } else {
                list1 = checkRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "checkId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        CheckListMaster checkListMaster = new CheckListMaster();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "checkId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            checkListMaster = checkRepository.findFirstByAccountNameContainingAndStatus(searchText, status, sort);
            list = checkRepository.findAllByAccountNameContainingAndStatus(searchText, status, pageable);
        } else {
            checkListMaster = checkRepository.findFirstByStatus(status, sort);
            list = checkRepository.findByStatus(status, pageable);
        }
        if (list.contains(checkListMaster)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<CheckListMasterPojo> checkListMasterPojoList = BsCheckMapper.mapcheckEntityToPojo(list);
        basePojo = calculatePagination(basePojo, checkListMasterPojoList.size());
        basePojo.setList(checkListMasterPojoList);
        return basePojo;
    }

    public Permission savePermission(PermissionPojo permissionPojo) {
        Permission permission1 = BsUserMapper.mapPerPojoToEntity(permissionPojo);
        if(permissionPojo.getParentId()==null){
            permission1.setParentId(0L);
        }
        if (permissionPojo.getParentId()!=null) {
            Permission permission2 = permissionRepository.findOne(permissionPojo.getParentId());
            permission1.setParentId(permission2.getPermissionId());
        }
        permissionRepository.save(permission1);
        return permission1;
    }

    public List<PermissionPojo> getPermissionList() {
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionPojo> permissionPojoList = BsUserMapper.mapPerEntityToPojo(permissions);
        return permissionPojoList;
    }

    public BasePojo getPaginationPermission(String status, BasePojo basePojo, String searchText) {
        List<Permission> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "permissionId"));
        if (basePojo.isLastPage() == true) {
            List<Permission> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = permissionRepository.findAllByPermissionNameContainingAndStatus(searchText, status);
            } else {
                list1 = permissionRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "permissionId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Permission permission = new Permission();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "permissionId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            permission = permissionRepository.findFirstByPermissionNameContainingAndStatus(searchText, status, sort);
            list = permissionRepository.findAllByPermissionNameContainingAndStatus(searchText, status, pageable);
        } else {
            permission = permissionRepository.findFirstByStatus(status, sort);
            list = permissionRepository.findByStatus(status, pageable);
        }
        if (list.contains(permission)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<PermissionPojo> permissionPojoList = BsUserMapper.mapPerEntityToPojo(list);
        basePojo = calculatePagination(basePojo, permissionPojoList.size());
        basePojo.setList(permissionPojoList);
        return basePojo;
    }

    public BasePojo getPaginatedDeductionList(String status, BasePojo basePojo, String searchText) {
        List<Deduction> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "deductionId"));
        if (basePojo.isLastPage() == true) {
            List<Deduction> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = bsDeductionRepository.findAllByDeductionNameContainingAndStatus(searchText, status);
            } else {
                list1 = bsDeductionRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "deductionId"));
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Deduction deduction = new Deduction();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "deductionId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            deduction = bsDeductionRepository.findFirstByDeductionNameContainingAndStatus(searchText, status, sort);
            list = bsDeductionRepository.findAllByDeductionNameContainingAndStatus(searchText, status, pageable);
        } else {
            deduction = bsDeductionRepository.findFirstByStatus(status, sort);
            list = bsDeductionRepository.findByStatus(status, pageable);
        }
        if (list.contains(deduction)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<DeductionPojo> deductionPojoList = BsUserMapper.mapDeductionEntityToPojo(list);
        basePojo = calculatePagination(basePojo, deductionPojoList.size());
        basePojo.setList(deductionPojoList);
        return basePojo;
    }

    public BasePojo getPaginatedChapterList(String status, BasePojo basePojo, String searchText) {
            List<Chapters> list = new ArrayList<>();
            basePojo.setPageSize(paginatedConstants);
            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "chapterId"));
            if (basePojo.isLastPage() == true) {
                List<Chapters> list1 = new ArrayList<>();
                if (!StringUtils.isEmpty(searchText)) {
                    list1 = chapterRepository.findAllByChapterNameContainingAndStatus(searchText, status);
                } else {
                    list1 = chapterRepository.findAllByStatus(status);
                }
                int size = list1.size() % paginatedConstants;
                int pageNo = list1.size() / paginatedConstants;
                if (size != 0) {
                    basePojo.setPageNo(pageNo);
                } else {
                    basePojo.setPageNo(pageNo - 1);
                }
                sort = new Sort(new Sort.Order(Sort.Direction.ASC, "chapterId"));
            }
            if (StringUtils.isEmpty(status)) {
                status = "Active";
            }
            Chapters chapters = new Chapters();
            Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
            if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
                sort = new Sort(new Sort.Order(Sort.Direction.ASC, "chapterId"));
            }
            if (!StringUtils.isEmpty(searchText)) {
                chapters = chapterRepository.findFirstByChapterNameContainingAndStatus(searchText, status, sort);
                list = chapterRepository.findAllByChapterNameContainingAndStatus(searchText, status, pageable);
            } else {
                chapters = chapterRepository.findFirstByStatus(status, sort);
                list = chapterRepository.findAllByStatus(status, pageable);
            }
            if (list.contains(chapters)) {
                basePojo.setStatus(true);
            } else {
                basePojo.setStatus(false);
            }
        List<ChapterPojo> chapterPojoList = BsUserMapper.mapChapterEntityToPojo(list);
        for(ChapterPojo c:chapterPojoList){
            if(c.getLevelId()!=null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findByGradeId(c.getLevelId());
                c.setLevelName(gradeMaster.getGradeName());
            }
            if(c.getSubjectId()!=null) {
                Subject subject = subjectRepository.findOne(c.getSubjectId());
                c.setSubjectName(subject.getSubjectName());
            }
            if(c.getClassId()!=null) {
                Class classes = bsClassRepository.findOne(c.getClassId());
                c.setClassName(classes.getClassName());
            }

        }
        basePojo = calculatePagination(basePojo, chapterPojoList.size());
        basePojo.setList(chapterPojoList);
        return basePojo;
    }
//    public BasePojo getPaginatedTopicList(String status, BasePojo basePojo, String searchText) {
//        List<Topic> list = new ArrayList<>();
//        basePojo.setPageSize(paginatedConstants);
//        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "topicId"));
//        if (basePojo.isLastPage() == true) {
//            List<Topic> list1 = new ArrayList<>();
//            if (!StringUtils.isEmpty(searchText)) {
//                list1 =topicRepository.findAllByTopicNameContainingAndStatus(searchText, status);
//            } else {
//                list1 = topicRepository.findAllByStatus(status);
//            }
//            int size = list1.size() % paginatedConstants;
//            if (size != 0) {
//                basePojo.setPageSize(size);
//            }
//            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "topicId"));
//        }
//        if (StringUtils.isEmpty(status)) {
//            status = "Active";
//        }
//        Topic topic = new Topic();
//        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
//        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
//            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "topicId"));
//        }
//        if (!StringUtils.isEmpty(searchText)) {
//            topic = topicRepository.findFirstByTopicNameContainingAndStatus(searchText, status, sort);
//            list = topicRepository.findAllByTopicNameContainingAndStatus(searchText, status, pageable);
//        } else {
//            topic = topicRepository.findFirstByStatus(status, sort);
//            list = topicRepository.findAllByStatus(status, pageable);
//        }
//        if (list.contains(topic)) {
//            basePojo.setStatus(true);
//        } else {
//            basePojo.setStatus(false);
//        }
//        List<TopicPojo> topicPojoList =BsUserMapper.mapTopicEntityToPojo(list);
//        basePojo = calculatePagination(basePojo, topicPojoList.size());
//        basePojo.setList(topicPojoList);
//        return basePojo;
//    }

    public ExitInterviewForm saveForm(ExitInterviewformPojo exitInterviewformPojo) {
        ExitInterviewForm exitInterviewForm = new ExitInterviewForm();
        if (exitInterviewformPojo.getEinterviewFormId() != null) {
            exitInterviewForm = bsInterviewRepository.findByEEmployeeNameAndEinterviewFormIdNotIn(exitInterviewformPojo.geteEmployeeName(), exitInterviewformPojo.getEinterviewFormId());
        } else {
            exitInterviewForm = bsInterviewRepository.findByEEmployeeName(exitInterviewformPojo.geteEmployeeName());
        }
        if (exitInterviewForm == null) {
            ExitInterviewForm exitInterviewForm1 = BsUserMapper.mapInterviewPojoToEntity(exitInterviewformPojo);
            bsInterviewRepository.save(exitInterviewForm1);
            return exitInterviewForm1;
        } else {
            return null;
        }

    }


    public Employee getDetailsByName(String name) {
        Employee employee = new Employee();
        employee = bsEmployeeRepository.findByEmployeeName(name);
        return employee;
    }


    public Employee getDetailByName(String name) {
        Employee employee = new Employee();
        employee = bsEmployeeRepository.findByEmployeeName(name);
        return employee;
    }


    public Earnings saveEarning(EarningsPojo earningsPojo) {
        Earnings earnings = new Earnings();
        if (earningsPojo.getEarningId() != null) {
            earnings = bsEarningsRepository.findByEarningNameAndEarningIdNotIn(earningsPojo.getEarningName(), earningsPojo.getEarningId());

        } else {
            earnings = bsEarningsRepository.findByEarningName(earningsPojo.getEarningName());
        }
        if (earnings == null) {
            Earnings earnings1 = BsUserMapper.mapEarningsPojoToEntity(earningsPojo);
            bsEarningsRepository.save(earnings1);
            return earnings1;
        } else {
            return null;
        }

    }

    public Deduction saveDeduction(DeductionPojo deductionDTO) {
        Deduction deduction = new Deduction();
        if (deductionDTO.getDeductionId() != null) {
            deduction = bsDeductionRepository.findByDeductionNameAndDeductionIdNotIn(deductionDTO.getDeductionName(), deductionDTO.getDeductionId());

        } else {
            deduction = bsDeductionRepository.findByDeductionName(deductionDTO.getDeductionName());
        }
        if (deduction == null) {
            Deduction deduction1 = BsUserMapper.mapDeductionPojoToEntity(deductionDTO);
            bsDeductionRepository.save(deduction1);
            return deduction1;
        } else {
            return null;
        }
    }

    public List<DeductionPojo> getDeductionList(String status, String searchText) {
        List<Deduction> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsDeductionRepository.findAllByStatus(status);
        } else {
            list = bsDeductionRepository.findByStatusAndDeductionNameContaining(status, searchText);
        }
        List<DeductionPojo> deductionDTOS = BsUserMapper.mapDeductionEntityToPojo(list);
        return deductionDTOS;
    }

    public EmployeePojo editEmployee(String employeeName) {
        EmployeePojo employeePojo = new EmployeePojo();
        Employee employee = bsEmployeeRepository.findByEmployeeName(employeeName);
        SalaryConfig salaryConfig = bsSalaryConfigRepository.findByEmployeeId(employee.getEmployeeId());
//        List<Employee> employees = new ArrayList<>();
//        employees.add(employee);
//        Employee employee1 = BsUserMapper.mapEmployeeEntityToPojo(employees);
//
//        EmployeePojo employeePojo = BsUserMapper.mapEmployeeEntityToPojo(employees).get(0);
//        List<SalaryConfig> salaryConfigs =bsSalaryConfigRepository.findByEmployeeId(id).get(0);
        if (salaryConfig != null) {
            employeePojo.setSalEffectiveDate(salaryConfig.getSalEffectiveDate());
            employeePojo.setBasicSal(salaryConfig.getBasicSal());
            employeePojo.setEmpearnings(salaryConfig.getEmpearnings());
            employeePojo.setEmpdeductions(salaryConfig.getEmpdeductions());
        }

        return employeePojo;
    }

    public FormsetupDTO editFormsetupMethod(String formsetupName) {
        FormSetUp formSetUp = posFormSetupRepository.findAllByTypename(formsetupName);
        List<FormSetUp> formSetUpList = new ArrayList<>();
        formSetUpList.add(formSetUp);
        FormsetupDTO formsetupDTO = BsUserMapper.mapFormSetupEntityToPojo(formSetUpList).get(0);
        return formsetupDTO;
    }


    public List<ExitInterviewformPojo> getEInterviewList(String status, String searchText) {
        List<ExitInterviewForm> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsInterviewRepository.findAllByEstatus(status);
        } else {
            list = bsInterviewRepository.findByEstatusAndEEmployeeNameContaining(status, searchText);
        }
        List<ExitInterviewformPojo> interviewformPojos = BsUserMapper.mapExitEntityToPojo(list);
        return interviewformPojos;
    }

    public Payroll savePayroll(PayrollPojo payrollPojo) {
        Payroll payroll = new Payroll();
        if (payrollPojo.getEmpId() != null) {
            payroll = bsPayrollRepository.findByEmpNameAndEmpIdNotIn(payrollPojo.getEmpName(), payrollPojo.getEmpId());
        } else {
            payroll = bsPayrollRepository.findByEmpName(payrollPojo.getEmpName());
        }
        if (payroll == null) {
            Payroll payroll1 = BsUserMapper.mapPayrollPojoToEntity((payrollPojo));
            bsPayrollRepository.save(payroll1);
            return payroll1;
        } else {
            return null;
        }
    }

    public List<PayrollPojo> getPayrollList(String status, String searchText) {
        List<Payroll> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsPayrollRepository.findAllByStatus(status);
        } else {
            list = bsPayrollRepository.findByStatusAndEmpNameContaining(status, searchText);
        }
        List<PayrollPojo> payrollPojos = BsUserMapper.mapPayrollEntityToPojo(list);
        return payrollPojos;
    }

    public Employee getPayrollByName(String name) {
        Employee employee = new Employee();
        employee = bsEmployeeRepository.findByEmployeeName(name);
        SalaryConfig salaryConfig = bsSalaryConfigRepository.findByEmployeeId(employee.getEmployeeId());
        employee.setBasicSal(salaryConfig.getBasicSal());
        return employee;
    }

    public Employee getAssessorByName(String name) {
        Employee employee = new Employee();
        employee = bsEmployeeRepository.findByEmployeeName(name);
        SalaryConfig salaryConfig = bsSalaryConfigRepository.findByEmployeeId(employee.getEmployeeId());
        employee.setBasicSal(salaryConfig.getBasicSal());
        return employee;
    }

    public Employee getEmployeeByName(String name) {
        Employee employee = new Employee();
        employee = bsEmployeeRepository.findByEmployeeName(name);
        SalaryConfig salaryConfig = bsSalaryConfigRepository.findByEmployeeId(employee.getEmployeeId());
        employee.setBasicSal(salaryConfig.getBasicSal());
        return employee;
    }

    public FormSetUp saveFormSetup(FormsetupDTO formsetupDTO) {
        FormSetUp form = new FormSetUp();
        if (formsetupDTO.getFormsetupId() != null) {
            form = posFormSetupRepository.findAllByTypenameAndFormsetupIdNotIn(formsetupDTO.getTypename(), formsetupDTO.getFormsetupId());
        } else {
            form = posFormSetupRepository.findAllByTypename(formsetupDTO.getTypename());
        }
        if (form == null) {
            FormSetUp formSetUp = BsUserMapper.mapFormSetupPojoToEntity(formsetupDTO);
            posFormSetupRepository.save(formSetUp);
            return formSetUp;
        } else {
            return null;
        }
    }

    public List<FormsetupDTO> getFormSetupList() {
        List<FormSetUp> list = new ArrayList<>();
        list = posFormSetupRepository.findAll();
        List<FormsetupDTO> formsetupDTOS = BsUserMapper.mapFormSetupEntityToPojo(list);
        return formsetupDTOS;
    }


    public BasePojo getPaginatedFormSetUpList(BasePojo basePojo, String searchText) {
        List<FormSetUp> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "formsetupId"));
        if (basePojo.isLastPage() == true) {
            List<FormSetUp> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posFormSetupRepository.findAllByTypenameContaining(searchText);
            } else {
                list1 = posFormSetupRepository.findAll();
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "formsetupId"));
        }
        FormSetUp formSetUp = new FormSetUp();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "formsetupId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            formSetUp = posFormSetupRepository.findFirstByTypenameContaining(searchText, sort);
            list = posFormSetupRepository.findAllByTypenameContaining(searchText, pageable);
        } else {
            formSetUp = posFormSetupRepository.findFirstBy(sort);
            list = posFormSetupRepository.findAllBy(pageable);
        }
        if (list.contains(formSetUp)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<FormsetupDTO> formsetupDTOList = BsUserMapper.mapFormSetupEntityToPojo(list);
        basePojo = calculatePagination(basePojo, formsetupDTOList.size());
        basePojo.setList(formsetupDTOList);
        return basePojo;
    }

    public PaymentMethodDTO editPaymentMethod(Long agentId) {
        PaymentMethod paymentMethod = posPaymentMethodRepository.findOne(agentId);
        List<PaymentMethod> agentList = new ArrayList<>();
        agentList.add(paymentMethod);
        PaymentMethodDTO agentDTO = BsUserMapper.mapPaymentMethodEntityToPojo(agentList).get(0);
        return agentDTO;
    }

    public PaymentMethod savePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod payment = new PaymentMethod();
        if (paymentMethodDTO.getPaymentmethodId() != null) {
            payment = posPaymentMethodRepository.findByPaymentmethodNameAndPaymentmethodIdNotIn(paymentMethodDTO.getPaymentmethodName(), paymentMethodDTO.getPaymentmethodId());
        } else {
            payment = posPaymentMethodRepository.findFirstByPaymentmethodName(paymentMethodDTO.getPaymentmethodName());
        }
        if (payment == null) {
            PaymentMethod paymentMethod = BsUserMapper.mapPaymentMethodPojoToEntity(paymentMethodDTO);
            posPaymentMethodRepository.save(paymentMethod);
            return paymentMethod;
        } else {
            return null;
        }
    }

    public AttendanceMgt saveAttendance(AttendanceMgtPojo attendanceMgtPojo) {
        AttendanceMgt attendanceMgt = new AttendanceMgt();
//        if (attendanceMgtPojo.getaId() != null) {
//            attendanceMgt = bsAttendanceMgtRepository.findByDepartmentAndAIdNotIn(attendanceMgtPojo.getDepartment(),attendanceMgtPojo.getaId());
//        } else {
//            attendanceMgt = bsAttendanceMgtRepository.findByENameAndDepartment(attendanceMgtPojo.geteName(),attendanceMgtPojo.getDepartment());
//        }
//        if (attendanceMgt == null) {
        AttendanceMgt attendanceMgt1 = BsAttendanceMgtMapper.mapAttendancePojoToEntity((attendanceMgtPojo));
        bsAttendanceMgtRepository.save(attendanceMgt1);
        return attendanceMgt1;
//        } else {
//            return null;
//        }

    }

    public StudentAttendence saveStudentAttendance(StudentAttendencePojo studentAttendencePojo) {
        StudentAttendence studentAttendence = new StudentAttendence();
//        if (studentAttendencePojo.getStudentAttendenceId() != null) {
//            studentAttendence = bsstudentAttendanceRepository.findAllByStudentLevelAndStudentClassAndStudentAttendenceIdNotIn(studentAttendencePojo.getStudentLevel(),studentAttendencePojo.getStudentClass(), studentAttendencePojo.getStudentAttendenceId());
//        } else {
//            studentAttendence = bsstudentAttendanceRepository.findAllByStudentClassAndStudentLevel(studentAttendencePojo.getStudentClass(), studentAttendencePojo.getStudentLevel());
//        }
//
//        if (studentAttendence == null) {
        StudentAttendence studentAttendence1 = BsStudentAttendenceMapper.mapStudentAttendencePojoToEntity((studentAttendencePojo));
        bsstudentAttendanceRepository.save(studentAttendence1);
        return studentAttendence1;
//        } else {
//            return null;
//        }

    }

    public List<AttendanceMgtPojo> getAttendanceList(String status, String searchText) {
        List<AttendanceMgt> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsAttendanceMgtRepository.findAllByStatus(status);
        } else {
            list = bsAttendanceMgtRepository.findByStatusAndENameContaining(status, searchText);
        }
        List<AttendanceMgtPojo> attendanceMgtPojos = BsAttendanceMgtMapper.mapAttendanceEntityToPojo(list);
        return attendanceMgtPojos;
    }

    public List<StudentAttendencePojo> getStudentAttendanceList(String status, String searchText) {
        List<StudentAttendence> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsstudentAttendanceRepository.findAllByStatus(status);
        } else {
            list = bsstudentAttendanceRepository.findByStatusAndStudent(status,searchText);
        }
        List<StudentAttendencePojo> studentAttendencePojos = BsStudentAttendenceMapper.mapStudentAttendanceEntityToPojo(list);
       for(StudentAttendencePojo pojo : studentAttendencePojos){
           if(pojo.getStudentLevel()!=null) {
               GradeMaster gradeMaster = bsGrademasterRepository.findOne(Long.parseLong(pojo.getStudentLevel()));
               pojo.setLevelName(gradeMaster.getGradeName());
           }

           if(pojo.getStudentClass()!=null){
               Class cls = bsClassRepository.findOne(Long.parseLong(pojo.getStudentClass()));
               pojo.setClassName(cls.getClassName());
           }
       }
        return studentAttendencePojos;
    }

    public void deleteAttendance(Long id) {
        bsAttendanceMgtRepository.delete(bsAttendanceMgtRepository.findByAId(id));}


    public void deleteStudentAttendence(Long studentAttendenceId) {
        bsstudentAttendanceRepository.delete(bsstudentAttendanceRepository.findByStudentAttendenceId(studentAttendenceId));
    }


    public List<PaymentMethodDTO> getPaymentMethodList(String status, String searchText) {
        List<PaymentMethod> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posPaymentMethodRepository.findAllByStatus(status);
        } else {
            list = posPaymentMethodRepository.findAllByStatusAndPaymentmethodName(status, searchText);
        }
        List<PaymentMethodDTO> paymentMethodDTOList = BsUserMapper.mapPaymentMethodEntityToPojo(list);
        return paymentMethodDTOList;
    }

    public List<PaymentMethodDTO> getPaymentTypes() {
        List<PaymentMethod> list = new ArrayList<>();
        list = posPaymentMethodRepository.findAll();
        List<PaymentMethodDTO> paymentMethodDTOList = BsUserMapper.mapPaymentMethodEntityToPojo(list);
        return paymentMethodDTOList;
    }


    public void deletePaymentMethod(Long paymentmethodId) {
        posPaymentMethodRepository.delete(paymentmethodId);
    }

    public Boolean deleteAS(AssesmentSubPojo details) {
        assesmentSubmissionRepository.delete(details.getAsId());
        assesmentSubmissionDetailsRepository.delete(assesmentSubmissionDetailsRepository.findByAssesmentId(details.getAsId()));
        return true;
    }

    public Boolean deleteAssessmentCreator(Long id) {
        AssesmentCreator assesmentCreator = assesmentCreatorRepository.findOne(id);
        assesmentCreatorRepository.delete(assesmentCreator.getAcreatorId());
        return true;
    }

    public BasePojo getPaginatedPayMethodList(String status, BasePojo basePojo, String searchText) {
        List<PaymentMethod> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "paymentmethodId"));
        if (basePojo.isLastPage() == true) {
            List<PaymentMethod> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posPaymentMethodRepository.findAllByPaymentmethodNameContainingAndStatus(searchText, status);
            } else {
                list1 = posPaymentMethodRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        PaymentMethod paymentMethod = new PaymentMethod();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "paymentmethodId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            paymentMethod = posPaymentMethodRepository.findFirstByPaymentmethodNameAndStatus(searchText, status, sort);
            list = posPaymentMethodRepository.findAllByPaymentmethodNameContainingAndStatus(searchText, status, pageable);
        } else {
            paymentMethod = posPaymentMethodRepository.findFirstByStatus(status, sort);
            list = posPaymentMethodRepository.findAllByStatus(status);
        }
        if (list.contains(paymentMethod)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<PaymentMethodDTO> payMethodDTOList = BsUserMapper.mapPaymentMethodEntityToPojo(list);
        basePojo = calculatePagination(basePojo, payMethodDTOList.size());
        basePojo.setList(payMethodDTOList);
        return basePojo;
    }

    public List<AccountMasterDTO> retrieveAccountMasterList(String accountCode) {
        List<AccountMaster> accountMaster = accountMasterRepository.findAllByAccountid(Long.parseLong(accountCode));
        List<AccountMasterDTO> accountMasterDTOList = BsUserMapper.mapAccMasterEntityToPojo(accountMaster);
        return accountMasterDTOList;
    }

    public void deleteBank(String bankName) {

        posBankRepository.delete(posBankRepository.findAllByBankName(bankName));
    }

    public void deleteVisitor(Long vistorId) {
        bsVisitorEntryRepository.delete(vistorId);

    }
    public List<ClassPojo> getClassLevel(Long id) {
        List<Class> classes = bsClassRepository.findAllByLevelIdAndClassStatus(id,"Active");
        List<ClassPojo> list = BsUserMapper.mapClassEntityToPojo(classes);
        return list;
    }

    public List<SubjectPojo> getSubjectClass(Long classesId) {
        Class classes = bsClassRepository.findOne(classesId);
        List<Subject> subjects = subjectRepository.findAllByClassesId(classes.getClassId());
        List<SubjectPojo> list = BsUserMapper.mapSubjectEntityToPojo(subjects);
        return list;
    }
    public AssessmentQuestions saveAssessmentQuestions(AssessmentQuestionsPojo assessmentQuestionsPojo){
        AssessmentQuestions assessmentQuestions = new AssessmentQuestions();
        assessmentQuestions = BsAssessmentQuestionsDetailsMapper.mapPojoToEntity(assessmentQuestionsPojo);
        bsAssessmentQuestionsRepository.save(assessmentQuestions);
        for (AssessmentQuestionDetailsPojo assessmentQuestionDetailsPojo:assessmentQuestionsPojo.getAssessmentQuestionDetailsPojos()){
            AssessmentQuestionDetails assessmentQuestionDetails = new AssessmentQuestionDetails();
            assessmentQuestionDetails.setAssessmentDetailsId(assessmentQuestionDetailsPojo.getAssessmentDetailsId());
            assessmentQuestionDetails.setAssessmentQuestions(assessmentQuestions);
            assessmentQuestionDetails.setMaxMarks(assessmentQuestionDetailsPojo.getMaxMarks());
            assessmentQuestionDetails.setQuestion(assessmentQuestionDetailsPojo.getQuestion());
            assessmentQuestionDetails.setInstrument(assessmentQuestionDetailsPojo.getInstrument());
            assessmentQuestionDetails.setSubComponent(assessmentQuestionDetailsPojo.getSubComponent());
            assessmentQuestionDetails.setComponentNm(assessmentQuestionDetailsPojo.getComponentNm());
            assessmentQuestionDetails.setRemarks(assessmentQuestionDetailsPojo.getRemarks());
            assessmentQuestionDetails.setComments(assessmentQuestionDetailsPojo.getComments());
            assessmentQuestionDetails.setRecommendation(assessmentQuestionDetailsPojo.getRecommendation());
            if(StringUtils.equalsIgnoreCase(assessmentQuestionDetailsPojo.getQuestionType(),"multiplechoice")){
                assessmentQuestionDetails.setAnswer(assessmentQuestionDetailsPojo.getMultipleChoiceList());
            }
            if(StringUtils.equalsIgnoreCase(assessmentQuestionDetailsPojo.getQuestionType(),"rubics")){
                assessmentQuestionDetails.setAnswer(assessmentQuestionDetailsPojo.getRubicsList());
            }
            if(StringUtils.equalsIgnoreCase(assessmentQuestionDetailsPojo.getQuestionType(),"trueorfalse")){
                assessmentQuestionDetails.setAnswer(assessmentQuestionDetailsPojo.getTrueorfalse());
            }
            if(StringUtils.equalsIgnoreCase(assessmentQuestionDetailsPojo.getQuestionType(),"description")){
                assessmentQuestionDetails.setAnswer(assessmentQuestionDetailsPojo.getDescription());
            }
            assessmentQuestionDetails.setQuestionType(assessmentQuestionDetailsPojo.getQuestionType());
            bsAssessmentQuestionDetailsRepository.save(assessmentQuestionDetails);
        }
        return assessmentQuestions;
    }


    public List<Topic> getTopicList(){
        List<Topic> topic = topicRepository.findAll();
        return topic;
    }

    public AssessmentQuestionsPojo editAssessmentQuestions(Long id) {
        AssessmentQuestionsPojo assessmentQuestionsPojo = new AssessmentQuestionsPojo();
        AssessmentQuestions assessmentQuestions = bsAssessmentQuestionsRepository.findOne(id);
        assessmentQuestionsPojo.setAssessmentQuestionsId(assessmentQuestions.getAssessmentQuestionsId());
        assessmentQuestionsPojo.setLevelId(assessmentQuestions.getLevelId());
        assessmentQuestionsPojo.setClassId(assessmentQuestions.getClassId());
        assessmentQuestionsPojo.setSubjectId(assessmentQuestions.getSubjectId());
        assessmentQuestionsPojo.setSemesterId(assessmentQuestions.getSemesterId());
        assessmentQuestionsPojo.setChapterId(assessmentQuestions.getChapterId());
        assessmentQuestionsPojo.setTermId(assessmentQuestions.getTermId());
        assessmentQuestionsPojo.setTopicId(assessmentQuestions.getTopicId());
        assessmentQuestionsPojo.setAcademicyear(assessmentQuestions.getAcademicyear());
        List<AssessmentQuestionDetails> assessmentQuestionDetailsList = bsAssessmentQuestionDetailsRepository.findAllByAssessmentQuestions(assessmentQuestions);
        List<AssessmentQuestionDetailsPojo> assessmentQuestionDetailsPojoList = new ArrayList<>();
        for (AssessmentQuestionDetails assessmentQuestionDetails : assessmentQuestionDetailsList) {
            AssessmentQuestionDetailsPojo assessmentQuestionDetailsPojo = new AssessmentQuestionDetailsPojo();
            assessmentQuestionDetailsPojo.setAnswer(assessmentQuestionDetails.getAnswer());
            assessmentQuestionDetailsPojo.setQuestion(assessmentQuestionDetails.getQuestion());
            assessmentQuestionDetailsPojo.setMaxMarks(assessmentQuestionDetails.getMaxMarks());
            assessmentQuestionDetailsPojo.setInstrument(assessmentQuestionDetails.getInstrument());
            assessmentQuestionDetailsPojo.setSubComponent(assessmentQuestionDetails.getSubComponent());
            assessmentQuestionDetailsPojo.setComponentNm(assessmentQuestionDetails.getComponentNm());
            assessmentQuestionDetailsPojo.setQuestionType(assessmentQuestionDetails.getQuestionType());
            assessmentQuestionDetailsPojo.setRemarks(assessmentQuestionDetails.getRemarks());
            assessmentQuestionDetailsPojo.setComments(assessmentQuestionDetails.getComments());
            assessmentQuestionDetailsPojo.setRecommendation(assessmentQuestionDetails.getRecommendation());
            if (StringUtils.equalsIgnoreCase(assessmentQuestionDetailsPojo.getQuestionType(), "multiplechoice")) {
                assessmentQuestionDetailsPojo.setMultipleChoiceList(assessmentQuestionDetails.getAnswer());
            }
            if (StringUtils.equalsIgnoreCase(assessmentQuestionDetailsPojo.getQuestionType(), "trueorfalse")) {
                assessmentQuestionDetailsPojo.setTrueorfalse(assessmentQuestionDetails.getAnswer());
            }
            if (StringUtils.equalsIgnoreCase(assessmentQuestionDetailsPojo.getQuestionType(), "rubics")) {
                assessmentQuestionDetailsPojo.setRubicsList(assessmentQuestionDetails.getAnswer());
            }
            if (StringUtils.equalsIgnoreCase(assessmentQuestionDetailsPojo.getQuestionType(), "description")) {
                assessmentQuestionDetailsPojo.setDescription(assessmentQuestionDetails.getAnswer());
            }
            assessmentQuestionDetailsPojoList.add(assessmentQuestionDetailsPojo);
        }
        assessmentQuestionsPojo.setAssessmentQuestionDetailsPojos(assessmentQuestionDetailsPojoList);
        return assessmentQuestionsPojo;
    }

    public List<InstrumentsPojo> getInstrumentList() {
        List<InstrumentsPojo> instrumentsPojoList = new ArrayList<>();
        List<Instruments> instrumentsList = instrumentsRepository.findAllByStatus("Active");
        for (Instruments instruments : instrumentsList) {
            InstrumentsPojo instrumentsPojo = new InstrumentsPojo();
            instrumentsPojo.setInstrumentsId(instruments.getInstrumentsId());
            instrumentsPojo.setInstrumentsName(instruments.getInstrumentsName());
            instrumentsPojo.setStatus(instruments.getStatus());
            instrumentsPojoList.add(instrumentsPojo);
        }
        return instrumentsPojoList;
    }
    public List<AssessmentQuestionsPojo> getAssessmentQuestionsList() {
        List<AssessmentQuestions> assessmentQuestionsDetails = bsAssessmentQuestionsRepository.findAll();
        List<AssessmentQuestionsPojo> assessmentQuestionsPojoList = BsAssessmentQuestionsDetailsMapper.mapEntityToPojo(assessmentQuestionsDetails);
        for(AssessmentQuestionsPojo assessmentQuestionsPojo : assessmentQuestionsPojoList){
            if(assessmentQuestionsPojo.getChapterId()!=null) {
                Chapters chapters = chapterRepository.findOne(assessmentQuestionsPojo.getChapterId());
                assessmentQuestionsPojo.setChapterName(chapters.getChapterName());
            }
            if(assessmentQuestionsPojo.getClassId()!=null) {
                Class c = bsClassRepository.findOne(assessmentQuestionsPojo.getClassId());
                assessmentQuestionsPojo.setClassName(c.getClassName());
            }
            if(assessmentQuestionsPojo.getSemesterId()!=null) {
                Semester semester = bsSemesterRepository.findOne(assessmentQuestionsPojo.getSemesterId());
                assessmentQuestionsPojo.setSemesterName(semester.getSemesterName());
            }
            if(assessmentQuestionsPojo.getSubjectId()!=null) {
                Subject subject = subjectRepository.findOne(assessmentQuestionsPojo.getSubjectId());
                assessmentQuestionsPojo.setSubjectName(subject.getSubjectName());
            }
            if(assessmentQuestionsPojo.getLevelId()!=null) {
                GradeMaster gradeMaster = bsGrademasterRepository.findOne(assessmentQuestionsPojo.getLevelId());
                assessmentQuestionsPojo.setLevelName(gradeMaster.getGradeName());
            }
        }
        return assessmentQuestionsPojoList;
    }

    public Boolean deleteComponent(ComponentPojo details) {
        componentRepository.delete(details.getComponentId());
        return true;
    }

    public Boolean deleteCountry(CountryDTO details) {
        countryRepository.delete(details.getCountryId());
        return true;
    }
    public Boolean deletePeriod(PeriodsDTO details) {
        periodsRepository.delete(details.getPeriodId());
        return true;
    }

    public BankDTO editBank(String bankName) {
        Bank bank = posBankRepository.findAllByBankName(bankName);
        List<Bank> bankList = new ArrayList<>();
        bankList.add(bank);
        BankDTO bankDTO = BsUserMapper.mapBankEntityToPojo(bankList).get(0);
        return bankDTO;
    }

    public List<AttendanceMgt> getStudentNameList(String name) {
        List<AttendanceMgt> attendanceMgt = bsAttendanceMgtRepository.findAllByEName(name);
        return attendanceMgt;
    }

    public Bank saveBank(BankDTO bankDTO) {
        Bank banks = new Bank();
        if (bankDTO.getBankId() != null) {
            banks = posBankRepository.findAllByBankNameAndBankIdNotIn(bankDTO.getBankName(), bankDTO.getBankId());
        } else {
            banks = posBankRepository.findAllByBankName(bankDTO.getBankName());
        }
        if (banks == null) {
            Bank bank = BsUserMapper.mapBankPojoToEntity(bankDTO);
            posBankRepository.save(bank);
            return bank;
        } else {
            return null;
        }
    }

    public BasePojo getPaginatedBankList(String status, BasePojo basePojo, String searchText) {
        List<Bank> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "bankId"));
        if (basePojo.isLastPage() == true) {
            List<Bank> list1 = new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posBankRepository.findAllByBankNameContainingAndStatus(searchText, status);
            } else {
                list1 = posBankRepository.findAllByStatus(status);
            }
            int size = list1.size() % paginatedConstants;
            int pageNo = list1.size() / paginatedConstants;
            if (size != 0) {
                basePojo.setPageNo(pageNo);
            } else {
                basePojo.setPageNo(pageNo - 1);
            }
        }
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        Bank bank = new Bank();
        Pageable pageable = new PageRequest(basePojo.getPageNo(), basePojo.getPageSize(), sort);
        if (basePojo.isNextPage() == true || basePojo.isFirstPage() == true) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "bankId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            bank = posBankRepository.findFirstByBankNameAndStatus(searchText, status, sort);
            list = posBankRepository.findAllByBankNameContainingAndStatus(searchText, status, pageable);
        } else {
            bank = posBankRepository.findFirstByStatus(status, sort);
            list = posBankRepository.findAllByStatus(status, pageable);
        }
        if (list.contains(bank)) {
            basePojo.setStatus(true);
        } else {
            basePojo.setStatus(false);
        }
        List<BankDTO> bankDTOList = BsUserMapper.mapBankEntityToPojo(list);
        basePojo = calculatePagination(basePojo, bankDTOList.size());
        basePojo.setList(bankDTOList);
        return basePojo;
    }

    public List<BankDTO> getBankList(String status, String searchText) {
        List<Bank> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posBankRepository.findAllByStatus(status);
        } else {
            list = posBankRepository.findAllByStatusAndBankName(status, searchText);
        }
        List<BankDTO> bankDTOList = BsUserMapper.mapBankEntityToPojo(list);
        return bankDTOList;
    }

    public BankDetails saveAcceptance(BankDetailsPojo bankDetailsPojo) {
        byte byteArray[];
        BankDetails bankDetails = new BankDetails();
        BankDetails bankDetails1 = bsBankDetailsRepository.findByHrId(bankDetailsPojo.getHrId());
        if (bankDetails1 != null) {
            bankDetails = bsBankDetailsRepository.findByHrId(bankDetailsPojo.getHrId());
        } else {
            bankDetails = new BankDetails();
        }
        String fileName = FileSystemOperations.getImagesDirItem() + File.separator + "IC" + bankDetailsPojo.getHrId() + ".png";
        if (!StringUtils.isEmpty(bankDetailsPojo.getAcceptLetter())) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                byteArray = org.apache.commons.codec.binary.Base64.decodeBase64(bankDetailsPojo.getAcceptLetter().split(",")[1]);
                fos.write(byteArray);
                fos.flush();
                fos.close();
                bankDetails.setAcceptLetter(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bankDetails.setBankName(bankDetailsPojo.getBankName());
        bankDetails.setBankaccNo(bankDetailsPojo.getBankaccNo());
        bankDetails.setEpfaccNo(bankDetailsPojo.getEpfaccNo());
        bankDetails.setAddress(bankDetailsPojo.getAddress());
        bankDetails.setEmail(bankDetailsPojo.getEmail());
        bankDetails.setMobile(bankDetailsPojo.getMobile());
        bankDetails.setHome(bankDetailsPojo.getHome());
        bankDetails.setStatusText(bankDetailsPojo.getStatusText());
        bankDetails.setHrId(bankDetailsPojo.getHrId());
        bankDetails.setStatusText("Active");
        bsBankDetailsRepository.save(bankDetails);
        return bankDetails;
    }

    public VistorEntry saveVisitor(VisitorEntryPojo visitorEntryPojo) {
        VistorEntry vistorEntry = new VistorEntry();
        if (visitorEntryPojo.getVistorId() != null) {
            vistorEntry = bsVisitorEntryRepository.findByVisitorNameAndVistorIdNotIn(visitorEntryPojo.getVisitorName(), visitorEntryPojo.getVistorId());
        } else {
            vistorEntry = bsVisitorEntryRepository.findByVisitorName(visitorEntryPojo.getVisitorName());
        }
        if (vistorEntry == null) {
            VistorEntry vistorEntry1 = BsUserMapper.mapVisitorPojoToEntity(visitorEntryPojo);
            bsVisitorEntryRepository.save(vistorEntry1);
            return vistorEntry1;
        } else {
            return null;
        }
    }

    public List<VisitorEntryPojo> getVisitorList(String status, String searchText) {
        List<VistorEntry> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = bsVisitorEntryRepository.findAllByVistorStatus(status);
        } else {
            list = bsVisitorEntryRepository.findByVistorStatusAndVisitorNameContaining(status, searchText);
        }
        List<VisitorEntryPojo> visitorEntryPojoList = BsUserMapper.mapVisitorEntityToPojo(list);
        return visitorEntryPojoList;
    }

    public List<ViewLedgerResponsePojo> getViewLedger(Long accountMasterId) {
        AccountMaster accountMaster = accountMasterRepository.findByAccountid(accountMasterId);
        List<GLTransactions> glTransactions = glTransactionRepository.findAllByAccount(accountMasterRepository.findOne(accountMaster.getAccountid()));
        List<ViewLedgerResponsePojo> bankDTOList = BsUserMapper.mapViewLedgerEntityToPojo(glTransactions);
        double totalAmt = 0;
        for (ViewLedgerResponsePojo viewLedgerResponsePojo : bankDTOList) {
            if (viewLedgerResponsePojo.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                viewLedgerResponsePojo.setDebit(viewLedgerResponsePojo.getAmount().doubleValue());
            } else {
                viewLedgerResponsePojo.setCredit(Math.abs(viewLedgerResponsePojo.getAmount().doubleValue()));
            }
            totalAmt = totalAmt + viewLedgerResponsePojo.getAmount().doubleValue();
            viewLedgerResponsePojo.setClosingBal(totalAmt < 0 ? Math.abs(totalAmt) + "cr" : totalAmt + "dr");
        }

        return bankDTOList;
    }

//    public List<ViewLedgerResponsePojo> getTrialBalance(){
//        List<AccountGroup> accountGroup = accountGroupRepository.findAll();
//        List<ViewLedgerResponsePojo> bankDTOList = BsUserMapper.mapTrailBalanceEntityToPojo(accountGroup);
//        double amt = 0;
//        for (ViewLedgerResponsePojo viewLedgerResponsePojo : bankDTOList) {
//            AccountGroup accountGroup1 = accountGroupRepository.findOne(viewLedgerResponsePojo.getGlID());
//            List<AccountMaster> accountMaster = accountMasterRepository.findAllByAgid(accountGroup1);
//            for (AccountMaster accountMaster1 : accountMaster) {
//                List<GLTransactions> glTransactions = glTransactionRepository.findAllByAccount(accountMasterRepository.findOne(accountMaster1.getAccountid()));
//                for (GLTransactions glTransactions1 : glTransactions) {
//                    amt = amt + glTransactions1.getAmount().doubleValue();
//                    if (glTransactions1.getAmount().compareTo(BigDecimal.ZERO) > 0) {
//                        viewLedgerResponsePojo.setDebit(glTransactions1.getAmount().doubleValue());
//                    } else {
//                        viewLedgerResponsePojo.setCredit(Math.abs(glTransactions1.getAmount().doubleValue()));
//                    }
//                }
//            }
//        }
//        return bankDTOList;
//    }


    public  List<Map<String,Object>> getAttendanceStudList(String level,String classId) {
        List<StudentAttendence> list = new ArrayList<>();
        if (StringUtils.isEmpty(level) && StringUtils.isEmpty(classId)) {
            list = bsstudentAttendanceRepository.findAll();
        } else {
            list = bsstudentAttendanceRepository.findAllByStudentLevelAndStudentClass(level,classId);
        }
        Gson gson = new Gson();
        Type type=new TypeToken<List<Map<String,String>>>(){}.getType();
        List<Map<String,String>> mapList= new ArrayList<>();
        List<AttendancePojo> AttendancePojo = new ArrayList<>();
        Map<String,List<Map<String,String>>> mapA = new HashMap();
        List<Map<String,String>> mapD = new ArrayList<>();
        for(StudentAttendence student:list){
            mapList = gson.fromJson(student.getStudentAttendence(),type);
            for(Map<String,String> mapB:mapList){
                Map<String,String> mapC = new HashMap<>();
                mapC.put("Student",mapB.get("student"));
                mapC.put("Gender",mapB.get("gender"));
                mapC.put("attendance",mapB.get("attendance"));
                mapC.put("Date",student.getaDate());
                mapD.add(mapC);

            }
            System.out.println("aaa"+mapD);
        }
        Map<Pair<String, String>, List<Map<String,String>>> map2 =
                mapD.stream().collect(groupingBy(p -> Pair.of( p.get("Student"),p.get("Gender"))));
        List<Map<String,Object>> abc = new ArrayList<>();
        Set<Map.Entry<Pair<String,String>, List<Map<String,String>>>> set = map2.entrySet();

        for(Map.Entry<Pair<String,String>, List<Map<String,String>>> entry : set)
        {
            Map<String,Object> mapFromSet = new HashMap<String,Object>();
            mapFromSet.put("Student",entry.getKey().getFirst());
            mapFromSet.put("Gender",entry.getKey().getSecond());
            mapFromSet.put("List",entry.getValue());
            abc.add(mapFromSet);
        }
        return abc;


    }








    }

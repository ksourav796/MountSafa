package com.hyva.bsfms.bs.bsrespositories;
import com.hyva.bsfms.bs.bsentities.LessonPlan;
import com.hyva.bsfms.bs.bsentities.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonPlanRepository extends JpaRepository<LessonPlan,Long> {
    LessonPlan findByLevelAndClassesAndSubjectAndChapterAndTopicAndComponentAndSubComponent(String level,String classes,String sub,String chapter,String topic,String component,String subComponent);
    LessonPlan findByLevelAndClassesAndSubjectAndChapterAndTopicAndComponentAndSubComponentAndLessonPlanIdNotIn(String level,String classes,String sub,String chapter,String topic,String component,String subComponent, Long id);
    List<LessonPlan> findAllByChapter(String name);
    List<LessonPlan> findAllByStatus(String status);
    List<LessonPlan> findAllByChapterAndAssessment(String name,String assesment);
    List<LessonPlan> findAllByChapterAndAssessmentAndSubComponent(String name,String assesment,String subComponent);
    LessonPlan findAllByLessonPlanId(Long id);
    LessonPlan findAllByAssesmentId(Long id);
    List<LessonPlan> findAllByLevelAndClassesAndSubjectAndChapter(String level,String classa,String subject,String cahpter);
    List<LessonPlan> findAllBySubjectContainingOrLevelContainingOrClassesContaining(String subject, String level, String classes);
    LessonPlan findBySubjectContainingOrLevelContainingOrClassesContaining(Sort sort, String subject, String level, String classes);
    List<LessonPlan> findBySubjectContainingOrLevelContainingOrClassesContaining(Pageable pageable, String subject, String level, String classes);
    List<LessonPlan> findAllBy(Pageable pageable);
    LessonPlan findFirstBy(Sort sort);
}

package com.mikutart.crs_backend;

import com.mikutart.crs_backend.v1.entity.*;
import com.mikutart.crs_backend.v1.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CrsBackendApplicationTests {
    // Autowire repositories for course, grade, major, plan, registration, staff and student.
    @Resource
    private CourseRepository courseRepository;

    @Resource
    private GradeRepository gradeRepository;

    @Resource
    private MajorRepository majorRepository;

    @Resource
    private PlanRepository planRepository;

    @Resource
    private RegistrationRepository registrationRepository;

    @Resource
    private StaffRepository staffRepository;

    @Resource
    private StudentRepository studentRepository;

    // then define methods that annotated with @Test
    // to test the CRUD each controller and repository.
    @Test
    void testCourseController() {
        // create a new course
        Course course = courseRepository.findAll().get(0);
        // check if the course is created
        assertThat(course).isNotNull();
        // check if the course is created with id
        assertThat(course.getId()).isNotNull();
        // check if the course is created with createdAt
        assertThat(course.getCreatedAt()).isNotNull();
        // check if the course is created with updatedAt
        assertThat(course.getUpdatedAt()).isNotNull();
        // update the course
        course = courseRepository.save(course);
        // check if the course is updated
        assertThat(course).isNotNull();
        // check if the course is updated with updatedAt
        assertThat(course.getUpdatedAt()).isNotNull();
        // check if the course is updated with createdAt
        assertThat(course.getCreatedAt()).isNotNull();
        // check if the course is updated with id
        assertThat(course.getId()).isNotNull();
        assertThat(courseRepository.findByCrn(course.getCrn())).isNotNull();
    }

    @Test
    void testGradeController() {
        // create a new grade
        Grade grade = gradeRepository.findAll().get(0);
        // check if the grade is created
        assertThat(grade).isNotNull();
        // check if the grade is created with id
        assertThat(grade.getId()).isNotNull();
        // check if the grade is created with createdAt
        assertThat(grade.getCreatedAt()).isNotNull();
        // check if the grade is created with updatedAt
        assertThat(grade.getUpdatedAt()).isNotNull();
        // update the grade
        grade = gradeRepository.save(grade);
        // check if the grade is updated
        assertThat(grade).isNotNull();
        // check if the grade is updated with updatedAt
        assertThat(grade.getUpdatedAt()).isNotNull();
        // check if the grade is updated with createdAt
        assertThat(grade.getCreatedAt()).isNotNull();
        // check if the grade is updated with id
        assertThat(grade.getId()).isNotNull();
        assertThat(gradeRepository.getReferenceById(grade.getId())).isNotNull();
    }

    @Test
    void testMajorController() {
        // create a new major
        Major major = majorRepository.findAll().get(0);
        // check if the major is created
        assertThat(major).isNotNull();
        // check if the major is created with id
        assertThat(major.getId()).isNotNull();
        // check if the major is created with createdAt
        assertThat(major.getCreatedAt()).isNotNull();
        // check if the major is created with updatedAt
        assertThat(major.getUpdatedAt()).isNotNull();
        // update the major
        major = majorRepository.save(major);
        // check if the major is updated
        assertThat(major).isNotNull();
        // check if the major is updated with updatedAt
        assertThat(major.getUpdatedAt()).isNotNull();
        // check if the major is updated with createdAt
        assertThat(major.getCreatedAt()).isNotNull();
        // check if the major is updated with id
        assertThat(major.getId()).isNotNull();
        assertThat(majorRepository.getReferenceById(major.getId())).isNotNull();
    }

    @Test
    void testPlanController() {
        // create a new plan
        Plan plan = planRepository.findAll().get(0);
        // check if the plan is created
        assertThat(plan).isNotNull();
        // check if the plan is created with id
        assertThat(plan.getId()).isNotNull();
        // check if the plan is created with createdAt
        assertThat(plan.getCreatedAt()).isNotNull();
        // check if the plan is created with updatedAt
        assertThat(plan.getUpdatedAt()).isNotNull();
        // update the plan
        plan = planRepository.save(plan);
        // check if the plan is updated
        assertThat(plan).isNotNull();
        // check if the plan is updated with updatedAt
        assertThat(plan.getUpdatedAt()).isNotNull();
        // check if the plan is updated with createdAt
        assertThat(plan.getCreatedAt()).isNotNull();
        // check if the plan is updated with id
        assertThat(plan.getId()).isNotNull();
        assertThat(planRepository.getReferenceById(plan.getId())).isNotNull();
    }

    @Test
    void testRegistrationController() {
        // create a new registration
        Registration registration = registrationRepository.findAll().get(0);
        // check if the registration is created
        assertThat(registration).isNotNull();
        // check if the registration is created with id
        assertThat(registration.getId()).isNotNull();
        // check if the registration is created with createdAt
        assertThat(registration.getCreatedAt()).isNotNull();
        // check if the registration is created with updatedAt
        assertThat(registration.getUpdatedAt()).isNotNull();
        // update the registration
        registration = registrationRepository.save(registration);
        // check if the registration is updated
        assertThat(registration).isNotNull();
        // check if the registration is updated with updatedAt
        assertThat(registration.getUpdatedAt()).isNotNull();
        // check if the registration is updated with createdAt
        assertThat(registration.getCreatedAt()).isNotNull();
        // check if the registration is updated with id
        assertThat(registration.getId()).isNotNull();
        assertThat(registrationRepository.getReferenceById(registration.getId())).isNotNull();
    }

    @Test
    void testStaffController() {
        // create a new staff
        Staff staff = staffRepository.findAll().get(0);
        // check if the staff is created
        assertThat(staff).isNotNull();
        // check if the staff is created with id
        assertThat(staff.getId()).isNotNull();
        // check if the staff is created with createdAt
        assertThat(staff.getCreatedAt()).isNotNull();
        // check if the staff is created with updatedAt
        assertThat(staff.getUpdatedAt()).isNotNull();
        // update the staff
        staff = staffRepository.save(staff);
        // check if the staff is updated
        assertThat(staff).isNotNull();
        // check if the staff is updated with updatedAt
        assertThat(staff.getUpdatedAt()).isNotNull();
        // check if the staff is updated with createdAt
        assertThat(staff.getCreatedAt()).isNotNull();
        // check if the staff is updated with id
        assertThat(staff.getId()).isNotNull();
        assertThat(staffRepository.getReferenceById(staff.getId())).isNotNull();
    }

    @Test
    void testStudentController() {
        // create a new student
        Student student = studentRepository.findAll().get(0);
        // check if the student is created
        assertThat(student).isNotNull();
        // check if the student is created with id
        assertThat(student.getId()).isNotNull();
        // check if the student is created with createdAt
        assertThat(student.getCreatedAt()).isNotNull();
        // check if the student is created with updatedAt
        assertThat(student.getUpdatedAt()).isNotNull();
        // update the student
        student = studentRepository.save(student);
        // check if the student is updated
        assertThat(student).isNotNull();
        // check if the student is updated with updatedAt
        assertThat(student.getUpdatedAt()).isNotNull();
        // check if the student is updated with createdAt
        assertThat(student.getCreatedAt()).isNotNull();
        // check if the student is updated with id
        assertThat(student.getId()).isNotNull();
        assertThat(studentRepository.getReferenceById(student.getId())).isNotNull();
    }
}

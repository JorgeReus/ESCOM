package mb;

import dao.ActivityDAO;
import dao.AnswerDAO;
import dao.GenericDAO;
import dao.GradeDAO;
import dao.UserDAO;
import entity.Activity;
import entity.Answer;
import entity.Grade;
import entity.Image;
import entity.Question;
import entity.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.BussinessConstants;
import util.NavigationConstants;

/**
 *
 * @author Reus Gaming PC
 */
@ManagedBean(name = "userActivityMB")
@SessionScoped
public class UserActivityMB extends GenericMB implements Serializable {

    @ManagedProperty(value = "#{loginMB}")
    private LoginMB loginMB;

    private Activity activity;
    private GenericDAO genericDAO;
    private Image realImage;
    private List<Image> selectedImages;
    private User student;
    private String activityRedirect;
    private List<Answer> answers;
    private List<User> students;
    private UserDAO userDAO;
    private ActivityDAO activityDAO;
    private List<Activity> activities;
    private AnswerDAO answerDAO;
    private Grade grade;
    private GradeDAO gradeDAO;

    @PostConstruct
    public void init() {
        activity = new Activity();
        genericDAO = new GenericDAO();
        realImage = new Image();
        selectedImages = new ArrayList<>();
        student = new User();
        answers = new ArrayList<>();
        students = new ArrayList<>();
        userDAO = new UserDAO();
        activities = new ArrayList<>();
        activityDAO = new ActivityDAO();
        answerDAO = new AnswerDAO();
        grade = new Grade();
        gradeDAO = new GradeDAO();
    }

    @Override
    public String prepareUpdate() {
        String redirect;
        selectedImages = new ArrayList<>();
        realImage = new Image();
        answers = new ArrayList<>();
        switch (activity.getActivityType().getTypeId()) {
            case BussinessConstants.ACTIVITY_TYPE_IMAGES:
                redirect = NavigationConstants.USER_ACTIVITY_EDIT_IMAGE;
                activityRedirect = NavigationConstants.USER_ACTIVITY_EDIT_IMAGE;
                break;
            case BussinessConstants.ACTIVITY_TYPE_VIDEO:
                redirect = NavigationConstants.USER_ACTIVITY_EDIT_VIDEO;
                for (Question questionIter : activity.getQuestions()) {
                    Answer newAnswer = new Answer();
                    newAnswer.setQuestion(questionIter);
                    newAnswer.getQuestion().setAnswer(newAnswer);
                    newAnswer.setUser(student);
                    answers.add(newAnswer);
                }
                activityRedirect = NavigationConstants.USER_ACTIVITY_EDIT_VIDEO;
                break;
            case BussinessConstants.ACTIVITY_TYPE_SOUND:
                redirect = NavigationConstants.USER_ACTIVITY_EDIT_AUDIO;
                for (Question questionIter : activity.getQuestions()) {
                    Answer newAnswer = new Answer();
                    newAnswer.setQuestion(questionIter);
                    newAnswer.getQuestion().setAnswer(newAnswer);
                    newAnswer.setUser(student);
                    answers.add(newAnswer);
                }
                activityRedirect = NavigationConstants.USER_ACTIVITY_EDIT_VIDEO;
                break;
            case BussinessConstants.ACTIVITY_TYPE_TEXT:
                redirect = NavigationConstants.USER_ACTIVITY_EDIT_TEXT;
                for (Question questionIter : activity.getQuestions()) {
                    Answer newAnswer = new Answer();
                    newAnswer.setQuestion(questionIter);
                    newAnswer.getQuestion().setAnswer(newAnswer);
                    newAnswer.setUser(student);
                    answers.add(newAnswer);
                }
                activityRedirect = NavigationConstants.USER_ACTIVITY_EDIT_TEXT;
                break;
            default:
                redirect = loginMB.gotoHome();
        }
        return redirect;
    }

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            realImage = (Image) genericDAO.findByID(Integer.valueOf(imageId), Image.class);
            return new DefaultStreamedContent(new ByteArrayInputStream(realImage.getImage()));
        }
    }

    public StreamedContent getVideo() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the video bytes.
            return new DefaultStreamedContent(new ByteArrayInputStream(activity.getVideo()));
        }
    }

    public StreamedContent getAudio() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the video bytes.
            return new DefaultStreamedContent(new ByteArrayInputStream(activity.getAudio()));
        }
    }

    public void onImageDrop(DragDropEvent ddEvent) {
        selectedImages.add(((Image) ddEvent.getData()));
    }

    @Override
    protected Boolean validateUpdate() {
        Boolean isValid = Boolean.TRUE;
        for (Answer answerIter : answers) {
            if (answerIter.getAnswer() == null || answerIter.getAnswer().isEmpty()) {
                addMessage("All questions must be answered", "messages", FacesMessage.SEVERITY_ERROR);
                isValid = Boolean.FALSE;
            }
        }
        return isValid;
    }

    public String updateImages() {
        String redirect = activityRedirect;

        student.setImages(selectedImages);
        if (student.getActivities() != null) {
            student.getActivities().add(activity);
        } else {
            student.setActivities(Collections.singletonList(activity));
        }
        if (genericDAO.update(student)) {
            addMessage("Succesfully completed exercise", "messages", FacesMessage.SEVERITY_INFO);
            redirect = loginMB.gotoHome();
        } else {
            addMessage("System error", "messages", FacesMessage.SEVERITY_ERROR);
        }

        return redirect;
    }

    @Override
    public String update() {
        String redirect = activityRedirect;
        if (validateUpdate()) {
            boolean hasError = false;
            if (student.getActivities() != null) {
                student.getActivities().add(activity);
            } else {
                student.setActivities(Collections.singletonList(activity));
            }
            for (Answer answerIter : answers) {
                if (!genericDAO.add(answerIter)) {
                    hasError = true;
                    addMessage("System Error", "messages", FacesMessage.SEVERITY_ERROR);
                    break;
                }
            }

            for (Question questionIter : activity.getQuestions()) {
                if (!genericDAO.update(questionIter)) {
                    hasError = true;
                    addMessage("System Error", "messages", FacesMessage.SEVERITY_ERROR);
                    break;
                }
            }
            if (!hasError && genericDAO.update(student)) {
                addMessage("Succesfully completed exercise", "messages", FacesMessage.SEVERITY_INFO);
                redirect = loginMB.gotoHome();
            } else {
                addMessage("System error", "messages", FacesMessage.SEVERITY_ERROR);
            }
        }
        return redirect;
    }

    // Students
    @Override
    public String prepareIndex() {
        students = userDAO.findByUserType(BussinessConstants.USER_TYPE_STUDENT);
        return NavigationConstants.USER_ACTIVITY_INDEX;
    }

    @Override
    public String prepareView() {
        activities = activityDAO.findAnsweredByUser(student.getUserId());
        return NavigationConstants.USER_ACTIVITY_VIEW;
    }

    public String prepareGrade() {
        String redirect;
        grade = new Grade();
        switch (activity.getActivityType().getTypeId()) {
            case BussinessConstants.ACTIVITY_TYPE_IMAGES:
                redirect = NavigationConstants.GRADE_USER_ACTIVITY_IMAGES;
                break;
            case BussinessConstants.ACTIVITY_TYPE_SOUND:
                redirect = NavigationConstants.GRADE_USER_ACTIVITY_AUDIO;
                break;
            case BussinessConstants.ACTIVITY_TYPE_VIDEO:
                redirect = NavigationConstants.GRADE_USER_ACTIVITY_VIDEO;
                break;
            case BussinessConstants.ACTIVITY_TYPE_TEXT:
                redirect = NavigationConstants.GRADE_USER_ACTIVITY_TEXT;
                break;
            default:
                redirect = loginMB.gotoHome();
        }
        return redirect;
    }

    public String findAnswerByQuestionAndUser(Integer questionId) {
        return answerDAO.findByQuestionAndUser(questionId, student.getUserId()).getAnswer();
    }

    public boolean validateGrade() {
        boolean isValid = true;
        if (grade.getGrade() == null) {
            addMessage("Grade field is required", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = false;
        } else if (grade.getGrade() > 10) {
            addMessage("Grade must be equal or lower that 10", "messages", FacesMessage.SEVERITY_ERROR);
            isValid = false;
        } else if (grade.getGrade() < 0) {
            isValid = false;
            addMessage("Grade must be equal or greater that 0", "messages", FacesMessage.SEVERITY_ERROR);
        }
        return isValid;
    }

    public String addGrade() {
        String redirect = "";
        if (validateGrade()) {
            grade.setActivity(activity);
            grade.setUser(student);
            Grade oldGrade = gradeDAO.findByActivityUser(activity.getActivityId(), student.getUserId());
            if (oldGrade == null) {
                if (genericDAO.add(grade)) {
                    addMessage("Succesfully graded activity", "messages", FacesMessage.SEVERITY_INFO);
                    redirect = prepareView();
                } else {
                    addMessage("System error", "messages", FacesMessage.SEVERITY_ERROR);
                }
            } else {
                oldGrade.setGrade(grade.getGrade());
                if (genericDAO.update(oldGrade)) {
                    addMessage("Succesfully graded activity", "messages", FacesMessage.SEVERITY_INFO);
                    redirect = prepareView();
                } else {
                    addMessage("System error", "messages", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        return redirect;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<Image> getSelectedImages() {
        return selectedImages;
    }

    public void setSelectedImages(List<Image> selectedImages) {
        this.selectedImages = selectedImages;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public LoginMB getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginMB loginMB) {
        this.loginMB = loginMB;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

}

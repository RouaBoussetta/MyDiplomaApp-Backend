package tn.uae.nawanera.spring.services;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Question;
import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.entities.skillassessment.Response;
import tn.uae.nawanera.spring.entities.skillassessment.Result;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.SkillAssessmentRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;

@Service
public class SkillAssessmentService implements ISkillAssessmentService {
	@Autowired
	SkillAssessmentRepository skillAssessmentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	VacancyRepository vacancyRepository;
	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	IUserservice iuserService;
	@Autowired
	INotificationService inotifService;
	@Autowired
	private EmailService emailService;

	private static final String APPLICATION_NAME = "serviceCal";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static com.google.api.services.calendar.Calendar client;

	URL url = getClass().getResource("mydiploma-343611-bd9f7bcc397c.p12");

	File keyFile = new File(url.getPath());
	GoogleClientSecrets clientSecrets;
	GoogleAuthorizationCodeFlow flow;
	Credential credential;

	HttpTransport transport;
	private String serviceAccount = "calendar-mydiploma-app@mydiploma-343611.iam.gserviceaccount.com";

	public SkillAssessment save(SkillAssessment skillassessment, List<Question> questions) {

		List<Question> questionlist = new ArrayList<>();
		for (Question q : questions) {
			questionlist.add(new Question());
			Question qt = new Question();
			qt.setQuestionorder(q.getQuestionorder());
			qt.setText(q.getText());

		}

		skillassessment.setQuestions(questionlist);

		skillassessment.setCreatedAt(LocalDateTime.now());
		skillassessment.setCreatedBy(iuserService.currentUser());
		return skillAssessmentRepository.save(skillassessment);
	}

	@Override
	public SkillAssessment save(SkillAssessment skillassessment) {

		skillassessment.setCreatedAt(LocalDateTime.now());
		skillassessment.setCreatedBy(iuserService.currentUser());
		return skillAssessmentRepository.save(skillassessment);
	}

	@Override
	public List<SkillAssessment> retreiveAll() {
		return skillAssessmentRepository.findAll();
	}

	@Override
	public List<SkillAssessment> retreiveOwnSkillAssessments() {
		return skillAssessmentRepository.findByCreatedBy(iuserService.currentUser());
	}

	@Override
	public List<SkillAssessment> retrieveAllPublished() {
		return skillAssessmentRepository.retrieveAllPublishedSkillAssessment();
	}

	@Override
	public List<SkillAssessment> retrieveAllByUser(int user) {

		User u = userRepository.findById(user);
		return skillAssessmentRepository.findByCreatedBy(u);
	}

	@Override
	public List<SkillAssessment> searchSkillAssessment(String query) {
		return new ArrayList<>();
	}

	@Override
	public SkillAssessment update(int id, SkillAssessment skillassessment) {

		SkillAssessment sa = skillAssessmentRepository.findById(id);
		if (!skillassessment.getTitle().equals(sa.getTitle()))
			sa.setTitle(skillassessment.getTitle());

		if (!skillassessment.getDescription().equals(sa.getDescription()))
			sa.setDescription(skillassessment.getDescription());

		return skillAssessmentRepository.save(sa);

	}

	@Override
	public void deleteSkillAssessment(SkillAssessment skillassessment) {
		skillAssessmentRepository.deleteById(skillassessment.getId());

	}

	@Override
	public Result checkInternAnswers(SkillAssessment sa, List<Response> answers) {
		Result results = new Result();
		int questions = sa.getQuestions().size();
		Application app = applicationRepository.findBySkillAssessment(sa);
		int score = 0;

		for (Question question : sa.getQuestions()) {

			for (Response r : answers) {

				if (question.getCorrectAnswer().getId() == r.getSelectedAnswer()) {

					results.addAnswer(true, questions, score);
					score += question.getQuestionScore();

				} else {
					results.addAnswer(false, questions, score);

				}
			}

		}

		app.setInternScore(score);
		applicationRepository.save(app);
		return results;
	}

	@Override
	public void publishSkillAssessment(int skillassessment) {
		SkillAssessment sk = skillAssessmentRepository.findById(skillassessment);

		sk.setIsPublished(true);
		skillAssessmentRepository.save(sk);

	}

	@Override
	public void publishSkillAssessment(String title) {
		SkillAssessment sk = skillAssessmentRepository.findByTitle(title);

		sk.setIsPublished(true);
		skillAssessmentRepository.save(sk);

	}

	@Override
	public SkillAssessment find(int id) {
		return skillAssessmentRepository.findById(id);
	}

	@Transactional
	@Override
	public void assignSAToIntern(int saId, int internId ) throws GeneralSecurityException, IOException {

		User intern = userRepository.findById(internId);
		
 		SkillAssessment sa = skillAssessmentRepository.findById(saId);
 		
		Application app = applicationRepository.findByIntern(intern);
 
 		 app.setSkillAssessment(sa);
		calendar();
		String description = "you are invited to do a qualification test Due to your interest in the internship offer posted by the company "
				+ iuserService.currentUser().getCompanyName();
		


	 //  attacheSa(intern.getEmail(), sa.getTitle(), "MyDiploma App", description);
 		  applicationRepository.save(app);
		inotifService.addNotification(intern, iuserService.currentUser(), "Qualifying Test",
				"you are invited to do a qualification test Due to your interest in the internship offer posted by "
						+ iuserService.currentUser().getCompanyName() + " company.");
 
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(intern.getEmail());
		email.setSubject("Qualifying Test");
		email.setText(

				intern.getUserImage() + "Dear " + intern.getFirstname() + ":\n"
						+ "Due to your interest in the internship offer posted by the company nawanera llc, you are required to do a qualification test.  \n"
						+ "please take your test as soon as possible."

		);

		emailService.sendEmail(email);

	}

	@Transactional
	@Override
	public void assignSAToIntern(String skTitle, String internUsername) throws GeneralSecurityException, IOException {

		User intern = userRepository.findByUsername(internUsername);
		SkillAssessment sa = skillAssessmentRepository.findByTitle(skTitle);
		Application app = applicationRepository.findByIntern(intern);

		if (app.getSkillAssessment() == null) {
			app.setSkillAssessment(sa);
			calendar();
			String description = "you are invited to do a qualification test Due to your interest in the internship offer posted by the company "
					+ iuserService.currentUser().getCompanyName();
		//	attacheSa("boussettaroua@gmail.com", sa.getTitle(), "MyDiploma App", description);
			inotifService.addNotification(intern, iuserService.currentUser(), "Qualifying Test",
					"you are invited to do a qualification test Due to your interest in the internship offer posted by "
							+ iuserService.currentUser().getCompanyName() + " company.");
/*
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(intern.getEmail());
			email.setSubject("Qualifying Test");
			email.setText(

					intern.getUserImage() + "Dear " + intern.getFirstname() + ":\n"
							+ "Due to your interest in the internship offer posted by the company nawanera llc, you are required to do a qualification test.  \n"
							+ "please take your test as soon as possible."

			);

			emailService.sendEmail(email);
*/
		}

	}

	public void calendar() throws GeneralSecurityException, IOException {

		Preconditions.checkArgument(!Strings.isNullOrEmpty(APPLICATION_NAME),
				"applicationName cannot be null or empty!");

		transport = GoogleNetHttpTransport.newTrustedTransport();

		credential = new GoogleCredential.Builder().setTransport(transport).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(serviceAccount)
				.setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
				.setServiceAccountPrivateKeyFromP12File(keyFile)

				.build();
		credential.refreshToken();
		new com.google.api.services.calendar.Calendar.Builder(transport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();

	}

	public void attacheSa(String email, String summary, String location, String description) throws IOException {

		Event event = new Event().setSummary(summary).setLocation(location).setDescription(description);

		DateTime startDateTime = new DateTime("2022-03-10T13:00:00+01:00");
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("UTC");
		event.setStart(start);

		DateTime endDateTime = new DateTime("2022-03-10T14:00:00+01:00");
		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("UTC");
		event.setEnd(end);

		String[] recurrence = new String[] { "RRULE:FREQ=DAILY;COUNT=1" };
		event.setRecurrence(Arrays.asList(recurrence));

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(10), };
		Event.Reminders reminders = new Event.Reminders().setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event.setReminders(reminders);
		client.events().insert(email, event).execute();

	}

	@Override
	public SkillAssessment getSkillAssessmentByTitle(String string) {

		return skillAssessmentRepository.findByTitle(string);
	}

}

package tn.uae.nawanera.spring.services;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.google.api.services.calendar.Calendar.Events;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import lombok.extern.slf4j.Slf4j;
import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Question;
import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.skillassessment.Response;
import tn.uae.nawanera.spring.entities.skillassessment.Result;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.SkillAssessmentRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;

@Slf4j
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
	IQuestionService iQuestionService;
	@Autowired
	INotificationService inotifService;
	@Autowired
	private EmailService emailService;

 	private static final String APPLICATION_NAME = "serviceCal";
   	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
 	private static com.google.api.services.calendar.Calendar client;
 	
 	URL url = getClass().getResource("auth.p12");
 	File keyFile=new File(url.getPath());
 	GoogleClientSecrets clientSecrets;
 	GoogleAuthorizationCodeFlow flow;
 	Credential credential;
 	
 	HttpTransport transport ;
 	private String serviceAccount="mydiploma@mydiploma-343611.iam.gserviceaccount.com";
	
 	private Set<Event> events = new HashSet<>();
 	final DateTime date1 = new DateTime("2022-05-05T16:30:00.000");
	final DateTime date2 = new DateTime(new Date());
 
	
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
	public void deleteSkillAssessment(int skillassessment) {
		
		List<Question> questions = iQuestionService.getQuestionByskillAssessment(skillassessment);
		for (Question q : questions) {
			iQuestionService.removeQuestion(q.getId());
		}
 		skillAssessmentRepository.deleteSK(skillassessment);
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
 		  String  subject="Qualifying Test";

		String description = "you are invited to do a qualification test Due to your interest in the internship offer posted by the company "
				+ iuserService.currentUser().getCompanyName();
		

		 attacheSa(intern.getEmail(), sa.getTitle(), description);
 		  applicationRepository.save(app);
		inotifService.addNotification(intern, iuserService.currentUser(), subject,
				"you are invited to do a qualification test Due to your interest in the internship offer posted by "
						+ iuserService.currentUser().getCompanyName() + " company.");
 
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(intern.getEmail());
		email.setSubject(subject);
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
		  String  subject="Qualifying Test";

		if (app.getSkillAssessment() == null) {
			app.setSkillAssessment(sa);
		 
			String description = "you are invited to do a qualification test Due to your interest in the internship offer posted by the company "
					+ iuserService.currentUser().getCompanyName();
			 attacheSa(intern.getEmail(), sa.getTitle(), description);
			inotifService.addNotification(intern, iuserService.currentUser(), subject,
					"you are invited to do a qualification test Due to your interest in the internship offer posted by "
							+ iuserService.currentUser().getCompanyName() + " company.");
			
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(intern.getEmail());
			email.setSubject(subject);
			email.setText(

					intern.getUserImage() + "Dear " + intern.getFirstname() + ":\n"
							+ "Due to your interest in the internship offer posted by the company nawanera llc, you are required to do a qualification test.  \n"
							+ "please take your test as soon as possible."
			);
			emailService.sendEmail(email);
		}
	
	}
	

	public void attacheSa(String email,String summary, String description) throws IOException, GeneralSecurityException {
		
		com.google.api.services.calendar.model.Events eventList;
 		 
 		
  		Preconditions.checkArgument( !Strings.isNullOrEmpty(APPLICATION_NAME ),
			      "applicationName cannot be null or empty!" );
			
  		transport = GoogleNetHttpTransport.newTrustedTransport();
			 	
			
			credential = new GoogleCredential.Builder().setTransport(transport)
		            .setJsonFactory(JSON_FACTORY)
		            .setServiceAccountId(serviceAccount)
		            .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
		            .setServiceAccountPrivateKeyFromP12File(keyFile)
		            
		            .build();
			credential.refreshToken();
			client = new com.google.api.services.calendar.Calendar.Builder(transport, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();
			log.info("**********************Client******************* \n"+client);
			Events events = client.events();
			eventList = events.list("primary").setTimeMin(date1).setTimeMax(date2).execute();
			
		
			Event event = new Event()
				    .setSummary(summary)
				 
				    .setDescription(description);

			DateTime startDateTime = new DateTime("2022-05-29T13:00:00+01:00");
			EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("UTC");
			event.setStart(start);

			DateTime endDateTime = new DateTime("2022-05-29T14:00:00+01:00");
			EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("UTC");
			event.setEnd(end);
				

				String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
				event.setRecurrence(Arrays.asList(recurrence));


				EventReminder[] reminderOverrides = new EventReminder[] {
				    new EventReminder().setMethod("email").setMinutes(24 * 60),
				    new EventReminder().setMethod("popup").setMinutes(10),
				};
				Event.Reminders reminders = new Event.Reminders()
				    .setUseDefault(false)
				    .setOverrides(Arrays.asList(reminderOverrides));
				event.setReminders(reminders);


 				
 				event = client.events().insert(email, event).execute();
 				log.info("Event created: %s\n", event.getHtmlLink());
 
	}
 
	@Override
	public SkillAssessment getSkillAssessmentByTitle(String string) {

		return skillAssessmentRepository.findByTitle(string);
	}

}

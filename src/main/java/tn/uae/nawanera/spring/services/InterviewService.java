package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.google.api.client.json.jackson2.JacksonFactory;
import java.net.URL;
import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Interview;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.InterviewRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;
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
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.ConferenceData;
import com.google.api.services.calendar.model.ConferenceParameters;
import com.google.api.services.calendar.model.ConferenceProperties;
import com.google.api.services.calendar.model.ConferenceSolutionKey;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterviewService implements IinterviewService {

	@Autowired
	InterviewRepository interviewRepository;
	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	SkillAssessmentService saService;
	@Autowired
	VacancyRepository vacancyRepository;
	@Autowired
	UserRepository userRepository;
/*
	private static final String APPLICATION_NAME = "serviceCal";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static com.google.api.services.calendar.Calendar client;

	URL url = getClass().getResource("auth.p12");
	File keyFile = new File(url.getPath());
	GoogleClientSecrets clientSecrets;
	GoogleAuthorizationCodeFlow flow;
	Credential credential;

	HttpTransport transport;
	private String serviceAccount = "mydiploma@mydiploma-343611.iam.gserviceaccount.com";

	private Set<Event> events = new HashSet<>();
*/
	@Autowired
	private EmailService emailService;

	@Override
	public Interview planifyInterview(Interview interview, int idapp)
			throws IOException, GeneralSecurityException, MessagingException {

		Application app = applicationRepository.findById(idapp);

		interview.setApplication(app);
		interview.setCreatedAt(LocalDateTime.now());
		interview.setLink("https://meet.google.com/wsm-ekqf-guo");

		interviewRepository.save(interview);
		app.setInterviewPlanned(true);
		applicationRepository.save(app);

		String description = "You have An interview with " + app.getVacancy().getPostedby().getFirstname() + " "
				+ app.getVacancy().getPostedby().getLastname() + "on " + interview.getInterviewDate() + " From"
				+ interview.getInterviewTime() + " To " + interview.getEndTime()
				+ " \n Meeting Link : https://meet.google.com/wsm-ekqf-guo";
	//	attacheInterviewEvent(app.getIntern().getEmail(), "Interview", description,interview.getInterviewDate(),
		//	 interview.getInterviewTime(),interview.getEndTime());
		 
		return interview;
	}
  
	@Override
	public List<Interview> retreiveInterviews() {

		return interviewRepository.findAll();
	}

	@Override
	public Interview getInterviewByApplication(int app) {
		Application a = applicationRepository.findById(app);

		return interviewRepository.findByApplication(a);
	}

	@Override
	public void rejectInterview(int id) {

		Interview intervieww = getInterviewByApplication(id);
		interviewRepository.delete(intervieww);
	}
/*
	public void attacheInterviewEvent(String email, String summary, String description,LocalDate date , LocalTime startTime ,LocalTime endTime)
			throws IOException, GeneralSecurityException {
		com.google.api.services.calendar.model.Events eventList;
		
		DateTimeFormatter
		  time = DateTimeFormatter.ISO_TIME;
		
		Preconditions.checkArgument(!Strings.isNullOrEmpty(APPLICATION_NAME),
				"applicationName cannot be null or empty!");
		transport = GoogleNetHttpTransport.newTrustedTransport();
		credential = new GoogleCredential.Builder().setTransport(transport).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(serviceAccount)
				.setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
				.setServiceAccountPrivateKeyFromP12File(keyFile).build();
		credential.refreshToken();
		client = new com.google.api.services.calendar.Calendar.Builder(transport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
		log.info("**********************Client******************* \n" + client);
		Events events = client.events();
		Event event = new Event().setSummary(summary).setDescription(description);
		DateTime startDateTime = new DateTime(date.toString()+"T"+startTime.format(time)+"+01:00");
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("UTC");
		event.setStart(start);
		DateTime endDateTime = new DateTime(date+"T"+endTime.format(time)+"+01:00");
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

		event = client.events().insert(email, event).execute();
	

	}
	
	*/

	/*
	 * public void attacheInteriew(String email, String summary, String description,
	 * LocalDate date , LocalTime startTime ,LocalTime endTime) throws IOException,
	 * GeneralSecurityException, MessagingException {
	 * 
	 * com.google.api.services.calendar.model.Events eventList; DateTimeFormatter
	 * time = DateTimeFormatter.ISO_TIME;
	 * 
	 * Preconditions.checkArgument(!Strings.isNullOrEmpty(APPLICATION_NAME),
	 * "applicationName cannot be null or empty!");
	 * 
	 * transport = GoogleNetHttpTransport.newTrustedTransport();
	 * 
	 * credential = new
	 * GoogleCredential.Builder().setTransport(transport).setJsonFactory(
	 * JSON_FACTORY) .setServiceAccountId(serviceAccount)
	 * .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
	 * .setServiceAccountPrivateKeyFromP12File(keyFile)
	 * 
	 * .build(); credential.refreshToken(); client = new
	 * com.google.api.services.calendar.Calendar.Builder(transport, JSON_FACTORY,
	 * credential) .setApplicationName(APPLICATION_NAME).build();
	 * log.info("**********************Client******************* \n" + client);
	 * 
	 * Events events = client.events(); eventList =
	 * events.list("primary").setTimeMin(date1).setTimeMax(date2).execute();
	 * 
	 * 
	 * Event event = new Event() .setSummary("Google I/O 2015")
	 * .setLocation("800 Howard St., San Francisco, CA 94103")
	 * .setDescription("A chance to hear more about Google's developer products.");
	 * 
	 * DateTime startDateTime = new DateTime("2022-06-02T17:00:00+01:00");
	 * EventDateTime start = new EventDateTime() .setDateTime(startDateTime)
	 * .setTimeZone("UTC"); event.setStart(start);
	 * 
	 * DateTime endDateTime = new DateTime("2022-06-02T18:00:00+01:00");
	 * EventDateTime end = new EventDateTime() .setDateTime(endDateTime)
	 * .setTimeZone("UTC"); event.setEnd(end);
	 * 
	 * String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
	 * event.setRecurrence(Arrays.asList(recurrence));
	 * 
	 * 
	 * 
	 * 
	 * EventReminder[] reminderOverrides = new EventReminder[] { new
	 * EventReminder().setMethod("email").setMinutes(24 * 60), new
	 * EventReminder().setMethod("popup").setMinutes(10), }; Event.Reminders
	 * reminders = new Event.Reminders() .setUseDefault(false)
	 * .setOverrides(Arrays.asList(reminderOverrides));
	 * event.setReminders(reminders);
	 * 
	 * String calendarId = "primary"; event = client.events().insert(calendarId,
	 * event).execute(); System.out.printf("Event created: %s\n",
	 * event.getHtmlLink());
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * /* Event event = new Event();
	 * 
	 * 
	 * DateTimeFormatter time = DateTimeFormatter.ISO_TIME;
	 * 
	 * System.out.println("******************"+ date);
	 * System.out.println("******************"+ startTime.format(time));
	 * 
	 * event.setStart(new EventDateTime().setDateTime(new
	 * DateTime(date.toString()+"T"+startTime.format(time)+"+01:00"))
	 * .setTimeZone("UTC")); event.setEnd(new EventDateTime().setDateTime(new
	 * DateTime(date+"T"+endTime.format(time)+"+01:00")). setTimeZone("UTC"));
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
	 * event.setRecurrence(Arrays.asList(recurrence));
	 * 
	 * 
	 * 
	 * EventReminder[] reminderOverrides = new EventReminder[] { new
	 * EventReminder().setMethod("email").setMinutes(24 * 60), new
	 * EventReminder().setMethod("popup").setMinutes(10), };
	 * 
	 * 
	 * Event.Reminders reminders = new Event.Reminders() .setUseDefault(false)
	 * .setOverrides(Arrays.asList(reminderOverrides));
	 * event.setReminders(reminders);
	 * 
	 * ConferenceData conferenceData = new ConferenceData();
	 * 
	 * conferenceData.setCreateRequest( new CreateConferenceRequest()
	 * .setConferenceSolutionKey( new ConferenceSolutionKey()
	 * .setType("hangoutsMeet")));
	 * 
	 * event.setConferenceData(conferenceData);
	 * 
	 * client.events().insert("primary", event).execute();
	 * System.out.printf("Event created: %s\n", event.getHtmlLink());
	 * System.out.printf("Hangout Link %s\n", event.getHangoutLink());
	 * System.out.printf("conferenceData %s\n", event.getConferenceData());
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 */

}

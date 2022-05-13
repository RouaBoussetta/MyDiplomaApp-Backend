package tn.uae.nawanera.spring.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
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
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InterviewService implements IinterviewService{
	
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
	final DateTime date1 = new DateTime("2022-05-05T16:30:00.000");
	final DateTime date2 = new DateTime(new Date());
 */
	@Override
	public Interview planifyInterview(Interview interview ,int idapp) throws IOException, GeneralSecurityException {
		
		Application app=applicationRepository.findById(idapp);
		
		interview.setApplication(app);
		interview.setCreatedAt(LocalDateTime.now());
		
		interviewRepository.save(interview);
		app.setInterviewPlanned(true);
		applicationRepository.save(app);
		
	// attacheInteriew("boussettaroua@gmail.com", "Interview", "You have An interview with "+app.getVacancy().getPostedby().getFirstname()+" "+app.getVacancy().getPostedby().getLastname());
		return interview;
	}
	@Override
	public List<Interview> retreiveInterviews() {
		
		return interviewRepository.findAll();
	}
	@Override
	public  Interview  getInterviewByApplication(int app) {
		Application a=applicationRepository.findById(app);
		
		return interviewRepository.findByApplication(a);
	}
	@Override
	public void rejectInterview (int id) {
		
		Interview intervieww=getInterviewByApplication(id);
		  interviewRepository.delete(intervieww);
	}
	
	 /*
	public void attacheInteriew(String email, String summary, String description)
			throws IOException, GeneralSecurityException {

		com.google.api.services.calendar.model.Events eventList;

		Preconditions.checkArgument(!Strings.isNullOrEmpty(APPLICATION_NAME),
				"applicationName cannot be null or empty!");

		transport = GoogleNetHttpTransport.newTrustedTransport();

		credential = new GoogleCredential.Builder().setTransport(transport).setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(serviceAccount)
				.setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
				.setServiceAccountPrivateKeyFromP12File(keyFile)

				.build();
		credential.refreshToken();
		client = new com.google.api.services.calendar.Calendar.Builder(transport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
		log.info("**********************Client******************* \n" + client);
		Events events = client.events();
		//eventList = events.list("primary").setTimeMin(date1).setTimeMax(date2).execute();

		Event event = new Event().setSummary(summary)

				.setDescription(description);
		
		

		DateTime startDateTime = new DateTime("2022-05-22T13:00:00+01:00");
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("UTC");
		event.setStart(start);

		DateTime endDateTime = new DateTime("2022-05-22T14:00:00+01:00");
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

		ConferenceProperties conferenceProperties=new ConferenceProperties();
		List<String> allowedConferenceSolutionTypes = new ArrayList< >();

		allowedConferenceSolutionTypes.add("hangoutsMeet");

		conferenceProperties.setAllowedConferenceSolutionTypes(allowedConferenceSolutionTypes);

		// .setConferenceProperties(conferenceProperties);
		
		
		ConferenceSolutionKey conferenceSKey = new ConferenceSolutionKey();
		
		conferenceSKey.setType("hangoutsMeet"); 
		
		
		CreateConferenceRequest createConferenceReq = new CreateConferenceRequest();
		createConferenceReq.setRequestId("sample232212"); // ID generated by you
		createConferenceReq.setConferenceSolutionKey(conferenceSKey);
		
		ConferenceData conferenceData = new ConferenceData();
		conferenceData.setCreateRequest(createConferenceReq);
		
		event.setConferenceData(conferenceData);
 	Calendar c=new Calendar();
 
 	c.setConferenceProperties(conferenceProperties);
 	
 
		try {
		    event = client.events().insert(email, event).setConferenceDataVersion(0).setSendNotifications(true).execute();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		System.out.printf("Event created: %s\n", event.getHtmlLink());

		System.out.printf("conference Type: %s\n", event.getConferenceData());
		System.out.printf("conference properties: %s\n", c.getConferenceProperties());
		 
 		System.out.printf("Hangout Link %s\n", event.getHangoutLink());

	}
 
 */

}

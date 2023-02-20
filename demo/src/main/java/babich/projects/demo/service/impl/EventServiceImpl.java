package babich.projects.demo.service.impl;

import babich.projects.demo.dto.EventDto;
import babich.projects.demo.mapper.EventMapper;
import babich.projects.demo.models.Club;
import babich.projects.demo.models.Event;
import babich.projects.demo.repository.ClubRepository;
import babich.projects.demo.repository.EventRepository;
import babich.projects.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static babich.projects.demo.mapper.EventMapper.mapToEvent;
import static babich.projects.demo.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
        Club club = clubRepository.findById(clubId).get();
        Event event = mapToEvent(eventDto);
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(EventMapper::mapToEventDto).collect(Collectors.toList());
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        Event event = mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void delete(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
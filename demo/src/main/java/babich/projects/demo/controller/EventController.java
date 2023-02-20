package babich.projects.demo.controller;

import babich.projects.demo.dto.EventDto;
import babich.projects.demo.models.Event;
import babich.projects.demo.models.UserEntity;
import babich.projects.demo.security.SecurityUtil;
import babich.projects.demo.service.EventService;
import babich.projects.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable(name = "clubId") Long clubId, Model model) {
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }

    @PostMapping("/events/{clubId}")
    public String saveEvent(@PathVariable("clubId") Long clubId,
                              @ModelAttribute("event") @Valid EventDto eventDto,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "events-create";
        }

        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs";
    }

    @GetMapping("/events")
    public String eventList(Model model) {
        UserEntity user = new UserEntity();
        List<EventDto> events = eventService.findAllEvents();
        String userEmail = SecurityUtil.getSessionUser();
        if (userEmail != null) {
            user = userService.findByEmail(userEmail);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model) {
        UserEntity user = new UserEntity();
        EventDto eventDto = eventService.findByEventId(eventId);
        String userEmail = SecurityUtil.getSessionUser();
        if (userEmail != null) {
            user = userService.findByEmail(userEmail);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") Long eventId, Model model) {
        EventDto event = eventService.findByEventId(eventId);
        model.addAttribute("event", event);
        return "event-edit";
    }

    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Long eventId,
                             @ModelAttribute("event") @Valid EventDto event,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", event);
            return "event-edit";
        }

        EventDto eventDto = eventService.findByEventId(eventId);
        event.setId(eventId);
        event.setClub(eventDto.getClub());
        eventService.updateEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.delete(eventId);
        return "redirect:/events";
    }
}
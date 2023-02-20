package babich.projects.demo.dto;

import babich.projects.demo.models.Club;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class EventDto {
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Start time cannot be empty")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "End time cannot be empty")
    private LocalDateTime endTime;
    @NotEmpty(message = "Type cannot be empty")
    private String type;
    @NotEmpty(message = "Photo link cannot be empty")
    private String photoUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Club club;
}
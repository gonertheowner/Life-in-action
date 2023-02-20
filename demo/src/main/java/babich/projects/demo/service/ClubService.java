package babich.projects.demo.service;

import babich.projects.demo.dto.ClubDto;
import babich.projects.demo.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();
    Club saveClub(ClubDto club);
    ClubDto findClubById(long clubId);
    void updateClub(ClubDto club);
    void delete(Long clubId);
    List<ClubDto> searchClubs(String query);
}
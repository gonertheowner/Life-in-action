package babich.projects.demo.service.impl;

import babich.projects.demo.dto.ClubDto;
import babich.projects.demo.mapper.ClubMapper;
import babich.projects.demo.models.Club;
import babich.projects.demo.models.UserEntity;
import babich.projects.demo.repository.ClubRepository;
import babich.projects.demo.repository.UserRepository;
import babich.projects.demo.security.SecurityUtil;
import babich.projects.demo.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static babich.projects.demo.mapper.ClubMapper.mapToClub;
import static babich.projects.demo.mapper.ClubMapper.mapToClubDto;

@Service
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<ClubDto> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map(ClubMapper::mapToClubDto).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String userEmail = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(userEmail);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepository.findById(clubId).get();
        return mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        String userEmail = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(userEmail);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void delete(Long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        return clubs.stream().map(ClubMapper::mapToClubDto).collect(Collectors.toList());
    }
}
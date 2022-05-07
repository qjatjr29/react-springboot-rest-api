package com.example.beommin.domain.orders.service;

import com.example.beommin.domain.orders.controller.user.PersonDto;
import com.example.beommin.domain.orders.entity.user.Manager;
import com.example.beommin.domain.orders.entity.user.Person;
import com.example.beommin.domain.orders.entity.user.User;
import com.example.beommin.domain.orders.repository.ManagerRepository;
import com.example.beommin.domain.orders.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.beommin.Utils.*;

@Service
public class ManagerService{
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public List<PersonDto> getAllManagers() {
        List<Manager> managers = managerRepository.findAll();
        List<PersonDto> personDtoList = new ArrayList<>();
        managers.forEach(manager -> personDtoList.add(PersonDto.of(manager)));
        return personDtoList;
    }

    public Optional<PersonDto> createManager(PersonDto personDto) {

        if(!validateEmailAndPassword(personDto.getEmail(), personDto.getPassword())) return Optional.empty();
        Optional<Manager> isExistManager = managerRepository.findByEmail(personDto.getEmail());
        if(isExistManager.isPresent()) {
            logger.info("매니저 생성 실패!");
            return Optional.empty();
        }
        Manager manager = new Manager(
                UUID.randomUUID(),
                personDto.getEmail(),
                personDto.getPassword(),
                personDto.getAddress(),
                personDto.getName(),
                personDto.getPhoneNumber(),
                LocalDate.now()
        );
        managerRepository.insert(manager);
        logger.info("매니저 생성!");
        return Optional.of(PersonDto.of(manager));

    }

    public PersonDto getManagerById(UUID managerId) {
        Optional<Manager> manager = managerRepository.findByManagerId(managerId);
        return getPersonDto(manager);
    }

    public UUID login(String loginId, String password) {
        Optional<Manager> login = managerRepository.login(loginId, password);
        return getPersonDto(login).getUserId();
    }

    public PersonDto updateManager(PersonDto personDto) {
        Optional<Manager> person = managerRepository.findByManagerId(personDto.getUserId());
        Manager updateManager = Optional.of(person).get()
                .orElseThrow(RuntimeException::new);
        updateManager.changeInfo(
                personDto.getName(),
                personDto.getAddress(),
                personDto.getPhoneNumber()
        );
        managerRepository.update(updateManager);
        return personDto;
    }

    public void deleteManager(UUID managerId) {
        Manager manager = Optional.of(managerRepository.findByManagerId(managerId)).get()
                .orElseThrow(RuntimeException::new);
        managerRepository.deleteById(manager);
    }


    private PersonDto getPersonDto(Optional<Manager> manager) {
        Person getPerson = Optional.of(manager).get()
                .orElseThrow(RuntimeException::new);
        return PersonDto.of(getPerson);
    }


}

package com.team992.pidl.Service;

import com.team992.pidl.DTOS.UserDTO;
import com.team992.pidl.Model.Entities.UserEntity;
import com.team992.pidl.Model.Entities.ContactEntity;
import com.team992.pidl.Model.Repositories.UserRepository;
import com.team992.pidl.Model.Repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    public String createUser(UserDTO userDTO){
        UserEntity entity = new UserEntity();
        entity.setUsername(userDTO.getUsername());
        entity.setPassword(userDTO.getPassword());
        entity.setContact(createOrGetContact(userDTO.getEmail()));
        userRepository.save(entity);
        logger.info("User was created with ID: {}", entity.getId());
        return entity.getId();
    }

    public UserDTO deleteUser(String id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));
        userRepository.deleteById(id);
        logger.info("User with id: {} was deleted.", id);

        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getContact().getEmail());
        dto.setPassword(userEntity.getPassword());
        return dto;
    }

    private ContactEntity createOrGetContact(String email) {
        return contactRepository.findByEmail(email).orElseGet(() -> {
            ContactEntity newContact = new ContactEntity();
            newContact.setEmail(email);
            return contactRepository.save(newContact);
        });
    }
}
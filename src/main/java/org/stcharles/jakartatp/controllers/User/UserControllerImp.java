package org.stcharles.jakartatp.controllers.User;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.User.UserOutput;
import org.stcharles.jakartatp.dao.User.UserDao;
import org.stcharles.jakartatp.model.User;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserControllerImp implements UserController {
    @Inject
    @Prod
    private UserDao userDao;

    @Override
    @Transactional
    public UserOutput create(String firstName, String lastName, String email) throws ValidationException {
        Optional<User> validEmail = Optional.ofNullable(userDao.getByEmail(email));
        if (validEmail.isPresent()) {
            throw new ValidationException("L'email sélectionner ne peut pas être insérer dans notre base");
        }
        User user = new User(firstName, lastName, email);
        userDao.persist(user);
        return new UserOutput(user);
    }

    @Override
    public List<UserOutput> getAll() {
        return userDao.getAll()
                .stream()
                .map(UserOutput::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserOutput get(Integer id) {
        return Optional.ofNullable(userDao.get(id))
                .map(UserOutput::new)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public Boolean delete(Integer userId) {
        return null;
    }

    @Override
    public List<UserOutput> getByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userDao.getByEmail(email));
        ArrayList<UserOutput> list = new ArrayList();
        list.add(new UserOutput(user.orElseThrow(NotFoundException::new)));
        return list;
    }

    @Override
    @Transactional
    public UserOutput update(Integer id, String firstName, String lastName, String email) {
        User user = Optional.ofNullable(userDao.get(id)).orElseThrow(NotFoundException::new);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        Optional<User> emailExist = Optional.ofNullable(userDao.getByEmail(email));
        if (emailExist.isPresent()) {
            throw new ValidationException("L'email ne peut pas être enregistrer car il existe déjà dans notre base");
        }
        user.setEmail(email);
        return new UserOutput(user);
    }


}

package org.stcharles.jakartatp.controllers.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.api.User.UserOutput;
import org.stcharles.jakartatp.dao.User.UserDao;
import org.stcharles.jakartatp.model.LoanState;
import org.stcharles.jakartatp.model.User;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The class User controller imp implements user controller
 */
@Prod
@ApplicationScoped
public class UserControllerImp implements UserController {
    @Inject
    @Prod
    private UserDao userDao;

    /**
     * Create
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @return UserOutput
     * @throws ValidationException
     * @Override
     */
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


    /**
     * Gets the all
     *
     * @return the all
     * @Override
     */
    public List<UserOutput> getAll() {

        return userDao.getAll()
                .stream()
                .map(UserOutput::new)
                .collect(Collectors.toList());
    }


    /**
     * Gets the
     *
     * @param id the id
     * @return the
     * @Override
     */
    public UserOutput get(Integer id) {

        return Optional.ofNullable(userDao.get(id))
                .map(UserOutput::new)
                .orElseThrow(NotFoundException::new);
    }


    /**
     * Delete
     *
     * @param userId the user identifier
     * @return Boolean
     * @Override
     */
    @Transactional
    public Boolean delete(Integer userId) {

        return null;
    }


    /**
     * Gets the by email
     *
     * @param email the email
     * @return the by email
     * @Override
     */
    public List<UserOutput> getByEmail(String email) {

        User user = Optional.ofNullable(userDao.getByEmail(email)).orElseThrow(NotFoundException::new);
        List<UserOutput> list = new ArrayList();
        list.add(new UserOutput(user));
        return list;
    }


    /**
     * Update
     *
     * @param id        the id
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @return UserOutput
     * @Override
     */

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


    /**
     * Remove
     *
     * @param id the id
     * @return Boolean
     * @Override
     */

    @Transactional
    public Boolean remove(Integer id) {

        User user = Optional.ofNullable(userDao.get(id)).orElseThrow(NotFoundException::new);
        if (user.getLoans().stream().anyMatch(loan -> loan.getStatus().equals(LoanState.EN_COURS))) {
            throw new ValidationException("Il y a des emprunts en cours vous ne pouvez pas supprimer cette utilisateur !");
        }
        try {
            userDao.remove(user);
            return true;
        } catch (Exception exception) {
            Logger.getAnonymousLogger(exception.getMessage());
            return false;
        }
    }
}

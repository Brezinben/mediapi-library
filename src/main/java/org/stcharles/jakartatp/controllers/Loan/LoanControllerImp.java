package org.stcharles.jakartatp.controllers.Loan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import org.stcharles.jakartatp.controllers.User.UserController;
import org.stcharles.jakartatp.dao.Item.ItemDao;
import org.stcharles.jakartatp.dao.Loan.LoanDao;
import org.stcharles.jakartatp.model.*;
import org.stcharles.jakartatp.qualifier.Prod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Prod
@ApplicationScoped

/**
 * The class Loan controller imp implements loan controller
 */
public class LoanControllerImp implements LoanController {

    @Inject
    @Prod
    private UserController userController;

    @Inject
    @Prod
    private LoanDao loanDao;

    @Inject
    @Prod
    private ItemDao itemDao;

    // predicate to filter the duplicates by the given key extractor.

    /**
     * Distinct by item identifier
     *
     * @param keyExtractor the key extractor
     * @return Predicate<T>
     */
    public static <T> Predicate<T> distinctByItemId(Function<? super T, Object> keyExtractor) {

        Map<Object, Boolean> uniqueMap = new ConcurrentHashMap<>();
        return t -> uniqueMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    /**
     * Gets the all
     *
     * @param userId the user identifier
     * @return the all
     * @Override
     */
    public List<Loan> getAll(Integer userId) {

        User user = userController.get(userId);
        return loanDao.getLoans(user);
    }


    /**
     * Gets the wanted loan
     *
     * @param userId the user identifier
     * @param loanId the loan identifier
     * @return the wanted loan
     * @Override
     */
    public Loan get(Integer userId, Integer loanId) {

        User user = userController.get(userId);
        List<Loan> loans = Optional.ofNullable(user.getLoans()).orElseThrow(NotFoundException::new);
        return loans.stream()
                .filter(loan -> loan.getId().equals(loanId))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }


    /**
     * Change status
     *
     * @param userId the user identifier
     * @param loanId the loan identifier
     * @param status the status
     * @return LoanOutput
     * @Override
     */
    public Loan changeStatus(Integer userId, Integer loanId, LoanState status) {

        Loan loan = this.get(userId, loanId);
        loan.setStatus(status);
        return loan;
    }


    /**
     * Create
     *
     * @param userId the user identifier
     * @param loan   the loan input
     * @return LoanOutput
     * @Override
     */
    public Loan create(Integer userId, Loan loan) {

        ArrayList<Loan> list = new ArrayList<>();
        list.add(loan);
        return createMultiple(userId, list).get(0);
    }


    /**
     * Create multiple
     *
     * @param userId the user identifier
     * @param loans  the loans
     * @return List<Loan>
     * @Override
     */
    @Transactional
    public List<Loan> createMultiple(Integer userId, List<Loan> loans) {

        int maxSize = 5;
        if (loans.size() > maxSize) {
            throw new ValidationException("Vous ne pouvez pas déclarer plus de " + maxSize + " enregistrements ici vous en avez " + loans.size());
        }
        //On filtre les items id pour  enlever les doublons
        loans = loans.stream()
                .filter(distinctByItemId(loan -> loan.getItem().getId()))
                .collect(Collectors.toList());

        loans.forEach(this::checkAndPersist);

        return loans;
    }


    /**
     * Gets the by status
     *
     * @param userId the user identifier
     * @param status the status
     * @return the by status
     * @Override
     */
    public List<Loan> getByStatus(Integer userId, LoanState status) {

        return loanDao.getLoans(userController.get(userId))
                .stream()
                .filter(loan -> loan.getStatus() == status)
                .collect(Collectors.toList());
    }


    /**
     * Update
     *
     * @param userId the user identifier
     * @param loanId the loan identifier
     * @param state  the state
     * @return Loan
     * @Override
     */
    @Transactional
    public Loan update(Integer userId, Integer loanId, LoanState state) {

        Loan loan = this.get(userId, loanId);
        if (loan.getStatus() == LoanState.RENDU) {
            throw new ValidationException("Cette emprunt est déjà rendu !");
        }
        if (state == LoanState.RENDU) {
            loan.setStatus(state);
            loan.getItem().setLoan(null);
            loan.setDateEnd(LocalDate.now());
        } else if (state == LoanState.EN_RETARD) {
            loan.setStatus(state);
        }
        return loan;
    }

    /**
     * Remove
     *
     * @param userId the user identifier
     * @param loanId the loan identifier
     * @return Boolean
     * @Override
     */
    @Transactional
    public Boolean remove(Integer userId, Integer loanId) {

        Loan loan = this.get(userId, loanId);
        if (loan.getStatus().equals(LoanState.EN_COURS)) {
            throw new ValidationException("Cette emprunt est actuellement utilisé, on ne peux donc le supprimer");
        }
        try {
            loanDao.remove(loan);
            return true;
        } catch (Exception exception) {
            Logger.getAnonymousLogger(exception.getMessage());
            return false;
        }
    }

    /**
     * Item validation
     *
     * @param item the item
     */
    private void itemValidation(Item item) {
        //Si il est deja a quelqu'un
        Optional<Loan> loan = Optional.ofNullable(item.getLoan());
        if (loan.isPresent()) {
            throw new ValidationException("L'item " + item.getId() + " ne peut vous être attribué");
        }
        //On empreinte pas un item inutilisable
        if (item.getState() == ItemState.INUTILISABLE) {
            throw new ValidationException("Vous ne pouvez pas prendre un item (id :" + item.getId() + ") non utilisable");
        }
    }

    @Transactional
    private void checkAndPersist(Loan loan) {
        itemValidation(loan.getItem());
        loanDao.persist(loan);
        //On set notre id dans loan pour la relation entre les deux
        Item item = loan.getItem();
        item.setLoan(loan);
    }
}

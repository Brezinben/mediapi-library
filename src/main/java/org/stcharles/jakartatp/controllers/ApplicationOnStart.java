package org.stcharles.jakartatp.controllers;


import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.transaction.Transactional;
import org.stcharles.jakartatp.dao.Loan.LoanDao;
import org.stcharles.jakartatp.model.LoanState;
import org.stcharles.jakartatp.qualifier.Prod;

import java.time.LocalDate;

@WebListener
class StartupListener implements ServletContextListener {
    @Inject
    @Prod
    private LoanDao loanDao;

    @Override
    @Transactional
    public void contextInitialized(ServletContextEvent event) {
        // Perform action during application's startup
        updateLateLoans();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Perform action during application's shutdown
    }

    /**
     * Viens mettre en retard les loans qui dépasse la date de aujourd'hui
     */
    private void updateLateLoans() {
        loanDao.getAllByStatus(LoanState.EN_COURS)
                .stream()
                .filter(loan -> LocalDate.now().isAfter(loan.getDateEnd()))
                .forEach(loan -> loan.setStatus(LoanState.EN_RETARD));
    }
}

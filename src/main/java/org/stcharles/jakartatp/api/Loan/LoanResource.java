package org.stcharles.jakartatp.api.Loan;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Loan.LoanController;
import org.stcharles.jakartatp.model.LoanState;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;

@Path("/users/{userId}/loans")
public class LoanResource {
    @Inject
    @Prod
    private LoanController loanController;

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public List<LoanOutput> getAll(@PathParam("userId") Integer userId, @QueryParam("status") LoanState status) {
        if (status != null) {
            return loanController.getByStatus(userId, status);
        }
        return loanController.getAll(userId);
    }

    @GET
    @Path("/{loanId}")
    @Produces("application/json")
    public LoanOutput get(@PathParam("userId") Integer userId, @PathParam("loanId") Integer loanId) {
        return loanController.get(userId, loanId);
    }


    @PUT
    @Path("/{loanId}")
    @Produces("application/json")
    public LoanOutput changeStatus(@PathParam("userId") Integer userId, @PathParam("loanId") Integer loanId, LoanState status) {
        return loanController.changeStatus(userId, loanId, status);
    }

    /*    @POST
        @Consumes("application/json")
        public Response create(@PathParam("userId") Integer userId, LoanInput loanInput) {
            LoanOutput loan = loanController.create(userId, loanInput);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(loan)
                    .build();
        }*/
    @POST
    @Consumes("application/json")
    public Response createMultiple(@PathParam("userId") Integer userId, List<LoanInput> loanInputs) {
        List<LoanOutput> loan = loanController.createMultiple(userId, loanInputs);
        return Response
                .status(Response.Status.CREATED)
                .entity(loan)
                .build();
    }
}

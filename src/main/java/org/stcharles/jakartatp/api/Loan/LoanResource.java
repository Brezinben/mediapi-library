package org.stcharles.jakartatp.api.Loan;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.stcharles.jakartatp.controllers.Loan.LoanController;
import org.stcharles.jakartatp.model.Loan;
import org.stcharles.jakartatp.model.LoanState;
import org.stcharles.jakartatp.qualifier.Prod;

import java.util.List;
import java.util.stream.Collectors;

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
            return loanController.getByStatus(userId, status).stream()
                    .map(LoanOutput::new)
                    .collect(Collectors.toList());
        }
        return loanController.getAll(userId).stream()
                .map(LoanOutput::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{loanId}")
    @Produces("application/json")
    public LoanOutput get(@PathParam("userId") Integer userId, @PathParam("loanId") Integer loanId) {
        Loan loan = loanController.get(userId, loanId);
        return new LoanOutput(loan);
    }


    @PUT
    @Path("/{loanId}")
    @Produces("application/json")
    public LoanOutput changeStatus(@PathParam("userId") Integer userId, @PathParam("loanId") Integer loanId, LoanState status) {
        Loan loan = loanController.changeStatus(userId, loanId, status);
        return new LoanOutput(loan);
    }

    @POST
    @Consumes("application/json")
    public Response createMultiple(@PathParam("userId") Integer userId, List<LoanInput> loanInputs) {

        List<Integer> itemsId = loanInputs.stream()
                .map(loanInput -> loanInput.itemId)
                .collect(Collectors.toList());

        List<LoanOutput> result = loanController.createMultiple(userId, itemsId)
                .stream()
                .map(LoanOutput::new)
                .collect(Collectors.toList());

        return Response
                .status(Response.Status.CREATED)
                .entity(result)
                .build();
    }

    @PUT
    @Path("/{loanId}")
    @Consumes("application/json")
    public Response update(@PathParam("userId") Integer userId, @PathParam("loanId") Integer loanId, BackLoanInput backLoanInput) {
        Loan loan = loanController.update(userId, loanId, backLoanInput.state);
        return Response
                .status(Response.Status.OK)
                .entity(new LoanOutput(loan))
                .build();
    }

    @DELETE
    @Path("/{loanId}")
    @Consumes("application/json")
    public Response remove(@PathParam("userId") Integer userId, @PathParam("loanId") Integer loanId) {
        Boolean deleted = loanController.remove(userId, loanId);
        Response.Status code = deleted ? Response.Status.NO_CONTENT : Response.Status.BAD_REQUEST;
        return Response.status(code).build();
    }

}

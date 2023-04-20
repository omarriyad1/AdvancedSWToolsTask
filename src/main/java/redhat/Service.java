package redhat;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Service {
	 @PersistenceContext
	private EntityManager entityManager;
	 
    @Path("calc")
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	 public Result calculate(Calculation calculation) {
	        int result;
	        switch (calculation.getOperation()) {
	            case "+":
	                result = calculation.getNumber1() + calculation.getNumber2();
	                break;
	            case "-":
	                result = calculation.getNumber1() - calculation.getNumber2();
	                break;
	            case "*":
	                result = calculation.getNumber1() * calculation.getNumber2();
	                break;
	            case "/": {
	                if (calculation.getNumber2() == 0)
	                    throw new IllegalArgumentException("Can't divide by zero");
	                result = calculation.getNumber1() / calculation.getNumber2();
	                break;
	            }
	            default:
	                throw new IllegalArgumentException("Unsupported operation");
	        }
	        entityManager.persist(calculation);
	        return new Result(result);
	    }
	 
	    @GET
	    @Path("calculations")
	    @Produces(MediaType.APPLICATION_JSON)
	    public List<Calculation> getAllCalculations() {
	    	return entityManager.createQuery("select c from Calculation c", Calculation.class).getResultList();
	    }
	    @Path("/")
	    @GET
	    public String getHealth() {
	        return "Up and running";
	    }
}

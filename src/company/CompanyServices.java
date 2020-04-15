package company;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.*;
import javax.ws.rs.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import companydata.*;


// start path: 'localhost:8080/resources/CompanyServices/'
@Path("CompanyServices")
public class CompanyServices {
    public DataLayer dataLayer;

    @Context
    UriInfo uriInfo;

    public CompanyServices() {
    }

    //////////////////////////////////////////// START DEPARTMENT HANDLING ////////////////////////////////////////////


    // Gets all departments from the company
    @Path("departments")
    @GET
    @Produces("application/json")
    public Response getAllDepartmentsFromCompany(@DefaultValue("1") @QueryParam("company") String inCompany) {
        try {
            dataLayer = new DataLayer("txm5483");
            List<Department> allDepartments = dataLayer.getAllDepartment(inCompany);
            if (allDepartments != null) {
                return Response.ok(new Gson().toJson(allDepartments)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "No departments for company " + inCompany + " found in database.\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Gets a specific department from the company
    @Path("department")
    @GET
    @Produces("application/json")
    public Response getDepartmentByIdFromCompany(@DefaultValue("1") @QueryParam("company") String inCompany, @QueryParam("dept_id") int inId) {
        try {
            dataLayer = new DataLayer("txm5483");
            Department departmentToGet = dataLayer.getDepartment(inCompany, inId);
            if (departmentToGet != null) {
                String departmentJson = new Gson().toJson(departmentToGet);
                return Response.ok(departmentJson).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "Department with ID " + inId + " was not found in database.\"}\"").build();
            }

        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Create a new department within a company
    @Path("department")
    @POST
    @Produces("application/json")
    public Response createDepartment(@DefaultValue("1") @FormParam("company") String inCompany,
                                     @DefaultValue("DefaultName") @FormParam("dept_name") String inDeptName,
                                     @DefaultValue("1") @FormParam("dept_no") String inDeptNo,
                                     @DefaultValue("DefaultLocation") @FormParam("dept_no") String inLocation) {

        try {
            dataLayer = new DataLayer("txm5483");
            Department newDepartment = new Department(inCompany, inDeptName, inDeptNo, inLocation);
            Department insertedDepartment = dataLayer.insertDepartment(newDepartment);
            if (insertedDepartment != null) {
                return Response.ok("{\"success\":" + new Gson().toJson(insertedDepartment) + "}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Could not insert new department into database for company " + inCompany +  "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Update a department within a company
    @Path("department")
    @PUT
    @Produces("application/json")
    public Response updateDepartment(String inJson) {
        try {
            dataLayer = new DataLayer("txm5483");

            // Convert input JSON to JsonObject & Create new dept variables
            JsonObject convertedJson = new Gson().fromJson(inJson, JsonObject.class);
            String newCompany = convertedJson.get("company").getAsString();
            int newDeptId = convertedJson.get("dept_id").getAsInt();
            String newDeptName = convertedJson.get("dept_name").getAsString();
            String newDeptNo = convertedJson.get("dept_no").getAsString();
            String newDeptLocation = convertedJson.get("location").getAsString();

            // Create updated department object
            Department updatedDepartment = new Department(newDeptId, newCompany, newDeptName, newDeptNo, newDeptLocation);
            Department insertedDepartment = dataLayer.updateDepartment(updatedDepartment);

            if (insertedDepartment != null) {
                return Response.ok("{\"success\":" + new Gson().toJson(insertedDepartment) + "}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "Could not update department " + newDeptName + "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Delete a department from a company
    @Path("department")
    @DELETE
    @Produces("application/json")
    public Response deleteDepartment(@DefaultValue("1") @QueryParam("company") String inCompany,
                                     @DefaultValue("1") @QueryParam("dept_id") Integer inDeptId) {
        try {
            dataLayer = new DataLayer("txm5483");
            int didDelete = dataLayer.deleteDepartment(inCompany, inDeptId);
            if (didDelete > 0) {
                return Response.ok("{\"success\":\"Department "+ inDeptId + " from " + inCompany + " Deleted Successfully\"}\"").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "Could not delete department with id " + inDeptId + "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }


    //////////////////////////////////////////// END DEPARTMENT HANDLING ////////////////////////////////////////////


    //////////////////////////////////////////// START EMPLOYEE HANDLING ////////////////////////////////////////////


    // Gets all employees from the company
    @Path("employees")
    @GET
    @Produces("application/json")
    public Response getAllEmployeesFromCompany(@DefaultValue("1") @QueryParam("company") String inCompany) {
        try {
            dataLayer = new DataLayer("txm5483");
            List<Employee> allEmployees = dataLayer.getAllEmployee(inCompany);
            if (allEmployees != null) {
                return Response.ok(new Gson().toJson(allEmployees)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "No employees for company " + inCompany + " found in database.\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Gets a specific employee from the company
    @Path("employee")
    @GET
    @Produces("application/json")
    public Response getEmployeeByIdFromCompany(@DefaultValue("1") @QueryParam("company") String inCompany, @QueryParam("emp_id") int inId) {
        try {
            dataLayer = new DataLayer("txm5483");
            Employee employeeToGet = dataLayer.getEmployee(inId);
            if (employeeToGet != null) {
                String departmentJson = new Gson().toJson(employeeToGet);
                return Response.ok(departmentJson).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "Employee with ID " + inId + " was not found in database.\"}\"").build();
            }

        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Create a new department within a company
    @Path("employee")
    @POST
    @Produces("application/json")
    public Response createEmployee(@DefaultValue("company") @FormParam("company") String inCompany,
                                   @DefaultValue("name") @FormParam("emp_name") String inName,
                                   @DefaultValue("employeeNumber") @FormParam("emp_no") String inEmployeeNo,
                                   @DefaultValue("hireDate") @FormParam("hire_date") String inHireDate,
                                   @DefaultValue("job") @FormParam("job") String inJob,
                                   @DefaultValue("salary") @FormParam("salary") Double inSalary,
                                   @DefaultValue("1") @FormParam("dept_id") Integer inDeptId,
                                   @DefaultValue("managerId") @FormParam("mng_id") Integer inManagerId) {

        try {
            dataLayer = new DataLayer("txm5483");
            java.sql.Date parsedHireDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(inHireDate).getTime());
            Employee newEmployee = new Employee(inName, inEmployeeNo, parsedHireDate, inJob, inSalary, inDeptId, inManagerId);
            Employee insertedEmployee = dataLayer.insertEmployee(newEmployee);
            if (insertedEmployee != null) {
                return Response.ok("{\"success\":" + new Gson().toJson(insertedEmployee) + "}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"response\":\"Could not insert new employee into company " + inCompany +  "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Update a department within a company
    @Path("employee")
    @PUT
    @Produces("application/json")
    public Response updateEmployee(String inJson) {
        try {
            dataLayer = new DataLayer("txm5483");

            // Convert input JSON to JsonObject & Create new employee variables
            JsonObject convertedJson = new Gson().fromJson(inJson, JsonObject.class);
            String newCompany = convertedJson.get("company").getAsString();
            int newEmpId = convertedJson.get("emp_id").getAsInt();
            String newName = convertedJson.get("emp_name").getAsString();
            String newEmpNo = convertedJson.get("emp_no").getAsString();
            String newHireDate = convertedJson.get("hire_date").getAsString();
            String newJob = convertedJson.get("job").getAsString();
            double newSalary = convertedJson.get("salary").getAsDouble();
            int newDeptId = convertedJson.get("dept_id").getAsInt();
            int newManagerId = convertedJson.get("mng_id").getAsInt();

            // Create updated employee object & insert into database
            java.sql.Date parsedHireDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(newHireDate).getTime());
            Employee updatedEmployee = new Employee(newEmpId, newName, newEmpNo, parsedHireDate, newJob, newSalary, newDeptId, newManagerId);
            Employee insertedEmployee = dataLayer.updateEmployee(updatedEmployee);

            if (insertedEmployee != null) {
                return Response.ok(new Gson().toJson(insertedEmployee)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Could not update employee " + newEmpNo +  "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Delete a employee from a company
    @Path("employee")
    @DELETE
    @Produces("application/json")
    public Response deleteEmployee(@DefaultValue("1") @QueryParam("company") String inCompany,
                                   @DefaultValue("1") @QueryParam("emp_id") Integer inEmpId) {
        try {
            dataLayer = new DataLayer("txm5483");

            int didDelete = dataLayer.deleteEmployee(inEmpId);
            if (didDelete > 0) {
                return Response.ok("{\"success\":\"Employee "+ inEmpId + " Deleted.\"}\"").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "Could not delete Employee " + inEmpId + "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }


    //////////////////////////////////////////// END EMPLOYEE HANDLING ////////////////////////////////////////////


    //////////////////////////////////////////// START TIMECARD HANDLING ////////////////////////////////////////////


    // Gets all timecards for an employee from the company
    @Path("timecards")
    @GET
    @Produces("application/json")
    public Response getAllTimecardsFromCompany(@DefaultValue("1") @QueryParam("company") String inCompany,
                                               @DefaultValue("1") @QueryParam("emp_id") int inEmployeeId) {
        try {
            dataLayer = new DataLayer("txm5483");
            List<Timecard> allTimecards = dataLayer.getAllTimecard(inEmployeeId);
            if (allTimecards != null) {
                return Response.ok(new Gson().toJson(allTimecards)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "No timecards for employee id " + inEmployeeId + " found in database.\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Gets a specific timecard from the company
    @Path("timecard")
    @GET
    @Produces("application/json")
    public Response getTimecardByIdFromCompany(@DefaultValue("1") @QueryParam("company") String inCompany,
                                               @DefaultValue("1") @QueryParam("timecard_id") int inTimecardId) {
        try {
            dataLayer = new DataLayer("txm5483");
            Timecard timecardToGet = dataLayer.getTimecard(inTimecardId);
            if (timecardToGet != null) {
                String departmentJson = new Gson().toJson(timecardToGet);
                return Response.ok(departmentJson).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "Timecard with ID " + inTimecardId + " was not found in database.\"}\"").build();
            }

        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Create a new timecard within a company
    @Path("timecard")
    @POST
    @Produces("application/json")
    public Response createTimecard(@DefaultValue("company") @FormParam("company") String inCompany,
                                   @DefaultValue("1") @FormParam("emp_id") int inEmployeeId,
                                   @DefaultValue("startTime") @FormParam("start_time") String inStartTime,
                                   @DefaultValue("endTime") @FormParam("end_time") String inEndTime) {

        try {
            dataLayer = new DataLayer("txm5483");
            java.sql.Timestamp parsedStartTime = new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inStartTime).getTime());
            java.sql.Timestamp parsedEndTime = new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inStartTime).getTime());

            Timecard newTimecard = new Timecard(parsedStartTime, parsedEndTime, inEmployeeId);
            Timecard insertedTimecard = dataLayer.insertTimecard(newTimecard);
            if (insertedTimecard != null) {
                return Response.ok("{\"success\":" + new Gson().toJson(insertedTimecard) + "}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"response\":\"Could not insert new Timecard into company " + inCompany +  "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Update a department within a company
    @Path("timecard")
    @PUT
    @Produces("application/json")
    public Response updateTimecard(String inJson) {
        try {
            dataLayer = new DataLayer("txm5483");

            // Convert input JSON to JsonObject & Create new employee variables
            JsonObject convertedJson = new Gson().fromJson(inJson, JsonObject.class);
            String newCompany = convertedJson.get("company").getAsString();
            int newTimecardId = convertedJson.get("timecard_id").getAsInt();
            String newStartTime = convertedJson.get("start_time").getAsString();
            String newEndTime = convertedJson.get("end_time").getAsString();
            int newEmployeeId = convertedJson.get("emp_id").getAsInt();

            // Create updated employee object & insert into database
            java.sql.Timestamp parsedStartTime = new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(newStartTime).getTime());
            java.sql.Timestamp parsedEndTime = new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(newEndTime).getTime());
            Timecard updatedTimecard = new Timecard(newTimecardId, parsedStartTime, parsedEndTime, newEmployeeId);
            Timecard insertedTimecard = dataLayer.updateTimecard(updatedTimecard);

            if (insertedTimecard != null) {
                return Response.ok(new Gson().toJson(insertedTimecard)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Could not update department " + newEmployeeId +  "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }

    // Delete a timecard from a company
    @Path("timecard")
    @DELETE
    @Produces("application/json")
    public Response deleteTimecard(@DefaultValue("1") @QueryParam("company") String inCompany,
                                   @DefaultValue("1") @QueryParam("timecard_id") Integer inTimecardId) {
        try {
            dataLayer = new DataLayer("txm5483");
            int didDelete = dataLayer.deleteTimecard(inTimecardId);
            if (didDelete > 0) {
                return Response.ok("{\"success\":\"Timecard "+ inTimecardId + " deleted.\"}\"").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\""+ "Could not delete Timecard " + inTimecardId + "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }


    //////////////////////////////////////////// END TIMECARD HANDLING ////////////////////////////////////////////


    //////////////////////////////////////////// START COMPANY HANDLING ////////////////////////////////////////////


    @Path("company")
    @DELETE
    @Produces("application/json")
    public Response deleteCompany(@DefaultValue("1") @QueryParam("company") String inCompany) {
        try {
            dataLayer = new DataLayer("txm5483");
            int didDelete = dataLayer.deleteCompany(inCompany);
            if (didDelete > 0) {
                return Response.ok("{\"success\":\""+ inCompany + "'s information deleted." + "\"}\"").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"response\":\""+ "Could not delete Company " + inCompany + "\"}\"").build();
            }
        } catch (Exception exception) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("{\"error\":\"DataLayer has thrown an exception}\"").build();
        } finally {
            dataLayer.close();
        }
    }


    //////////////////////////////////////////// END COMPANY HANDLING ////////////////////////////////////////////


}

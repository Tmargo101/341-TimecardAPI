package company;

import com.google.gson.Gson;
import companydata.DataLayer;
import companydata.Department;

import javax.ws.rs.core.Response;

public class Test {


    public static void main (String [] args) {
        DataLayer dataLayer = null;
        try {
            dataLayer = new DataLayer("txm5483");
            Department newDepartment = new Department("txm5483", "Test", "11", "Rochester");
            Department insertedDepartment = dataLayer.insertDepartment(newDepartment);
            if (insertedDepartment != null) {

                System.out.println(new Gson().toJson(insertedDepartment));
            } else {
                System.out.println("Failed");
                System.out.println(dataLayer.getAllDepartment("txm5483"));
            }
        } catch (Exception exception) {
            System.out.println("Exception");
        } finally {
            dataLayer.close();
        }

    }
}

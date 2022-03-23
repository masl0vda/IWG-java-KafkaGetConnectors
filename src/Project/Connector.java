package Project;

public class Connector {
    public String role;
    public String environment;
    public String connectionUser;
    public String connectionPassword;

    // newRole          Source|Sink
    // newEnvironment   Prod|Test
    public Connector(String newRole, String newEnvironment) {

        this.role = newRole;
        this.environment = newEnvironment;

        if (newEnvironment == "Prod") {
            this.connectionUser = "PROD_USER";
            this.connectionPassword = "PROD_PWD}";
        } else if(newEnvironment == "Test"){
            this.connectionUser = "TEST_USER";
            this.connectionPassword = "TEST_PWD";
        }
    }
}

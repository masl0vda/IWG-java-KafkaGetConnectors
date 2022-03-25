package Project;
public class Connection {
    public String connectionUser;
    public String connectionPassword;
    public Connection(String newEnvironment) {
        if (newEnvironment == "Prod") {
            this.connectionUser = "PROD_USER";
            this.connectionPassword = "PROD_PWD}";
        } else if (newEnvironment == "Test") {
            this.connectionUser = "TEST_USER";
            this.connectionPassword = "TEST_PWD";
        }
    }
}

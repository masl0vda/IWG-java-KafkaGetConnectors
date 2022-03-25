package Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
class Connector {
    public String role;
    public String environment;
    public Connector(String newRole, String newEnvironment) {
        this.role = newRole;
        this.environment = newEnvironment;
    }
}
public class Connectors {
    // getConnectors(newRole,newEnvironment)
    // newRole          Source|Sink
    // newEnvironment   Prod|Test
    public void getConnectors(String newRole, String newEnvironment, String newTableName, String newTopicName) {
        CityArray newCityArray = new CityArray();
        Connector newConnector = new Connector(newRole, newEnvironment);
        Connection newConnection = new Connection(newEnvironment);

        File file = new File(newTableName + "/" + newConnector.environment + "-" + newTableName + ".sh");
        file.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(file)) {
            for (City rowCity : newCityArray.GetCityArray()) {
                pw.println("cat << EOF > " + newConnector.role + "-connector-for_" + rowCity.name + "_" + newTableName + ".json");
                pw.println("{");
                pw.println("    \"name\": \"" + newConnector.role + "-connector-for_" + rowCity.name + "_" + newTableName + "\",");
                pw.println("    \"config\":");
                pw.println("    {");
                pw.println("    \"name\": \"" + newConnector.role + "-connector-for_" + rowCity.name + "_" + newTableName + "\",");
                pw.println("    \"connector.class\": \"io.confluent.connect.jdbc.Jdbc" + newConnector.role + "Connector\",");
                pw.println("    \"connection.url\": \"" + rowCity.connectionUrl + "\",");
                pw.println("    \"connection.user\": \"\\" + newConnection.connectionUser + "\",");
                pw.println("    \"connection.password\": \"\\" + newConnection.connectionPassword + "\",");
                pw.println("    \"transforms\": \"filterCity\",");
                pw.println("    \"transforms.filterCity.type\": \"io.confluent.connect.transforms.Filter\\$Value\",");
                pw.println("    \"transforms.filterCity.filter.type\": \"include\",");
                pw.println("    \"transforms.filterCity.filter.condition\": \"\\$[?(@.CITY_ID == " + rowCity.cityId + ")]\",");
                pw.println("    \"table.name.format\": \"" + newTableName + "\",");
                pw.println("    \"topics\": \"" + newTopicName + "\",");
                pw.println("    }");
                pw.println("}");
                pw.println("EOF");
                pw.println("curl -X POST -H \"Content-Type: application/json\" -H \"Accept: application/json\" -d @" + newConnector.role + "-connector-for_" + rowCity.name + "_" + newTableName + ".json http://kafka-connect:8083/connectors/");
                pw.println(" ");
            }
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
}
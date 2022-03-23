package Project;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        CityArray newCityArray = new CityArray();

        // new Connector(Role,Environment)
        // Role          Source|Sink
        // Environment   Prod|Test
        Connector newConnector = new Connector("Sink","Test");

        String tableName = "table_in_sql_db";
        String topicName = "topic_in_kafka";

        File file = new File (tableName +"/"+ newConnector.environment +"-"+ tableName +".sh");
        file.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(file)) {

            for (City rowCity: newCityArray.GetCityArray()) {
                pw.println("cat << EOF > "+ newConnector.role + "-connector-for_" + rowCity.name + "_" + tableName + ".json");
                pw.println("{");
                pw.println("    \"name\": \""+ newConnector.role + "-connector-for_" + rowCity.name + "_" + tableName + "\",");
                pw.println("    \"config\":");
                pw.println("    {");
                pw.println("    \"name\": \""+ newConnector.role +"-"-connector-for_" + rowCity.name + "_" + tableName + "\",");
                pw.println("    \"connector.class\": \"io.confluent.connect.jdbc.Jdbc"+ newConnector.role +"Connector\",");
                pw.println("    \"connection.url\": \"" + rowCity.connectionUrl + "\",");
                pw.println("    \"connection.user\": \"\\"+ newConnector.connectionUser +"\",");
                pw.println("    \"connection.password\": \"\\"+ newConnector.connectionPassword +"\",");
                pw.println("    \"transforms\": \"filterCity\",");
                pw.println("    \"transforms.filterCity.type\": \"io.confluent.connect.transforms.Filter\\$Value\",");
                pw.println("    \"transforms.filterCity.filter.type\": \"include\",");
                pw.println("    \"transforms.filterCity.filter.condition\": \"\\$[?(@.CITY_ID == " + rowCity.cityId + ")]\",");
                pw.println("    \"table.name.format\": \""tableName"\",");
                pw.println("    \"topics\": \""topicName"\",");
                pw.println("    }");
                pw.println("}");
                pw.println("EOF");
                pw.println("curl -X POST -H \"Content-Type: application/json\" -H \"Accept: application/json\" -d @"+ newConnector.role + "-connector-for_" + rowCity.name + "_" + tableName + ".json http://kafka-connect:8083/connectors/");
                pw.println(" ");
            }
        } catch (IOException exc) {
            System.out.println(exc);
        }
    }
}



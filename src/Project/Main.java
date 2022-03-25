package Project;

public class Main {
    public static void main(String[] args) {
        String tableName = "table_in_sql_db";
        String topicName = "topic_in_kafka";

        Connectors newConnectors = new Connectors();
        newConnectors.getConnectors("Sink","Prod",tableName,topicName);
    }
}



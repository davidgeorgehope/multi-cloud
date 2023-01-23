package com.elastic.multicloud;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class KafkaMessageProcessor {

    @Value("${gcp.project.id:elastic-product-marketing}")
    private String gcpProjectId;
    @Value("${gcp.credentials.path:/Users/davidhope/Downloads/elastic-product-marketing-e145e13fbc7c.json}")
    private String gcpCredentialsPath;

    public String fakeESCall() {
        // simulate some sync call like ES query
        try {
            //simulate some time-consuming stuff
            Thread.sleep(120);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return UUID.randomUUID().toString();
    }

    public String processMessage(final ConsumerRecord<String, String> record) {
        try {
            //simulate some time-consuming stuff
            Thread.sleep(200);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String datasetName = "gcpBellCanadaTestDataSet";
        String tableName = "gcpBellCanadaTestTable";
        insertingDataTypes(datasetName, tableName);

        return record.value();
    }



    public void insertingDataTypes(String datasetName, String tableName) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            //String projectId = "elastic-product-marketing";
            //File credentialsPath = new File("/Users/davidhope/Downloads/elastic-product-marketing-e145e13fbc7c.json");

            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            String projectId = gcpProjectId;
            File credentialsPath = new File(gcpCredentialsPath);

            // Load credentials from JSON key file. If you can't set the GOOGLE_APPLICATION_CREDENTIALS
            // environment variable, you can explicitly load the credentials file to construct the
            // credentials.
            GoogleCredentials credentials;
            try (FileInputStream serviceAccountStream = new FileInputStream(credentialsPath)) {
                credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Instantiate a client.
            BigQuery bigQuery =
                    BigQueryOptions.newBuilder()
                            .setCredentials(credentials)
                            .setProjectId(projectId)
                            .build()
                            .getService();

            Dataset dataset = bigQuery.getDataset(datasetName);
            TableId tableId = null;

            if(dataset.get(tableName) == null) {
                // Inserting data types
                Field name = Field.of("name", StandardSQLTypeName.STRING);
                Field age = Field.of("age", StandardSQLTypeName.INT64);
                Field school =
                        Field.newBuilder("school", StandardSQLTypeName.BYTES)
                                .setMode(Field.Mode.REPEATED)
                                .build();
                Field location = Field.of("location", StandardSQLTypeName.GEOGRAPHY);
                Field measurements =
                        Field.newBuilder("measurements", StandardSQLTypeName.FLOAT64)
                                .setMode(Field.Mode.REPEATED)
                                .build();
                Field day = Field.of("day", StandardSQLTypeName.DATE);
                Field firstTime = Field.of("firstTime", StandardSQLTypeName.DATETIME);
                Field secondTime = Field.of("secondTime", StandardSQLTypeName.TIME);
                Field thirdTime = Field.of("thirdTime", StandardSQLTypeName.TIMESTAMP);
                Field datesTime =
                        Field.of("datesTime", StandardSQLTypeName.STRUCT, day, firstTime, secondTime, thirdTime);
                Schema schema = Schema.of(name, age, school, location, measurements, datesTime);

                tableId = TableId.of(datasetName, tableName);
                TableDefinition tableDefinition = StandardTableDefinition.of(schema);
                TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();

                bigQuery.create(tableInfo);
            }else{
                Table table = bigQuery.getTable(datasetName, tableName);
                tableId = table.getTableId();
            }

            // Inserting Sample data
            Map<String, Object> datesTimeContent = new HashMap<>();
            datesTimeContent.put("day", "2019-1-12");
            datesTimeContent.put("firstTime", "2019-02-17 11:24:00.000");
            datesTimeContent.put("secondTime", "14:00:00");
            datesTimeContent.put("thirdTime", "2020-04-27T18:07:25.356Z");

            Map<String, Object> rowContent = new HashMap<>();
            rowContent.put("name", "Tom");
            rowContent.put("age", 30);
            rowContent.put("school", "Test University".getBytes());
            rowContent.put("location", "POINT(1 2)");
            rowContent.put("measurements", new Float[] {50.05f, 100.5f});
            rowContent.put("datesTime", datesTimeContent);

            InsertAllResponse response =
                    bigQuery.insertAll(InsertAllRequest.newBuilder(tableId).addRow(rowContent).build());

            if (response.hasErrors()) {
                // If any of the insertions failed, this lets you inspect the errors
                for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors().entrySet()) {
                    System.out.println("Response error: \n" + entry.getValue());
                }
            }
            System.out.println("Rows successfully inserted into table");
        } catch (BigQueryException e) {
            System.out.println("Insert operation not performed \n" + e.toString());
        }
    }

}

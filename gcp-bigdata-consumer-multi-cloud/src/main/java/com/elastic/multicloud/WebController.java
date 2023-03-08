/*
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.elastic.multicloud;

import com.google.cloud.bigquery.*;
import com.google.cloud.spring.bigquery.core.BigQueryTemplate;
import com.google.cloud.spring.bigquery.core.WriteApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/** Provides REST endpoint allowing you to load data files to BigQuery using Spring Integration. */
@RestController
@RequestMapping("/bigquery")
public class WebController {
/*

  private final BigQueryTemplate bigQueryTemplate;

  private static final String DATASET_NAME = "datasetName";

  @Value("${spring.cloud.gcp.bigquery.datasetName}")
  private String datasetName;

  public WebController(
      BigQueryTemplate bigQueryTemplate) {
    this.bigQueryTemplate = bigQueryTemplate;
  }

  @GetMapping("/")
  public Mono<Void> renderIndex(ModelMap map) {
    map.put(DATASET_NAME, this.datasetName);
    return Mono.fromRunnable(() -> {

    });
  }

  @GetMapping("/write-api-json-upload")
  public  Mono<Void>  renderUploadJson(ModelMap map) {
    map.put(DATASET_NAME, this.datasetName);
    return Mono.fromRunnable(() -> {

    });
  }


  @PostMapping("/uploadJsonFile")
  public Mono<Void> handleJsonFileUpload(
      @RequestParam("file") MultipartFile file,
      @RequestParam("tableName") String tableName,
      @RequestParam(name = "createTable", required = false) String createDefaultTable)
      throws IOException {
    CompletableFuture<WriteApiResponse> writeApiRes;
    if (createDefaultTable != null
        && createDefaultTable.equals("createTable")) { // create the default table
      writeApiRes =
              (CompletableFuture<WriteApiResponse>) this.bigQueryTemplate.writeJsonStream(
                  tableName, file.getInputStream(), getDefaultSchema());
    } else { // we are expecting the table to be already existing
      writeApiRes = (CompletableFuture<WriteApiResponse>) this.bigQueryTemplate.writeJsonStream(tableName, file.getInputStream());
    }

    return Mono.fromRunnable(() -> {
           getWriteApiResponse(writeApiRes, tableName);
    });

  }

  private Schema getDefaultSchema() {
    return Schema.of(
        Field.of("CompanyName", StandardSQLTypeName.STRING),
        Field.of("Description", StandardSQLTypeName.STRING),
        Field.of("SerialNumber", StandardSQLTypeName.NUMERIC),
        Field.of("Leave", StandardSQLTypeName.NUMERIC),
        Field.of("EmpName", StandardSQLTypeName.STRING));
  }

  @PostMapping("/uploadJsonText")
  public  Mono<Void> handleJsonTextUpload(
      @RequestParam("jsonRows") String jsonRows,
      @RequestParam("tableName") String tableName,
      @RequestParam(name = "createTable", required = false) String createDefaultTable) {
    CompletableFuture<WriteApiResponse> writeApiRes;
    if (createDefaultTable != null
        && createDefaultTable.equals("createTable")) { // create the default table

      writeApiRes =
              (CompletableFuture<WriteApiResponse>) this.bigQueryTemplate.writeJsonStream(
                  tableName, new ByteArrayInputStream(jsonRows.getBytes()), getDefaultSchema());
    } else { // we are expecting the table to be already existing
      writeApiRes =
              (CompletableFuture<WriteApiResponse>) this.bigQueryTemplate.writeJsonStream(
                  tableName, new ByteArrayInputStream(jsonRows.getBytes()));
    }

    return Mono.fromRunnable(() -> {
      getWriteApiResponse(writeApiRes, tableName);
    });
  }

  private   Mono<Void>  getWriteApiResponse(
      CompletableFuture<WriteApiResponse> writeApiFuture, String tableName) {
    String message = null;
    try {
      WriteApiResponse apiResponse = writeApiFuture.get();
      if (apiResponse.isSuccessful()) {
        message = "Successfully loaded data to " + tableName;
      } else if (apiResponse.getErrors() != null && !apiResponse.getErrors().isEmpty()) {
        message =
            String.format(
                "Error occurred while loading the file, printing first error %s. Use WriteApiResponse.getErrors() to get the complete list of errors",
                apiResponse.getErrors().get(0).getErrorMessage());
      }

    } catch (Exception e) {
      e.printStackTrace();
      message = "Error: " + e.getMessage();
    }
    return Mono.fromRunnable(() -> {
    });
  }


  @PostMapping("/uploadFile")
  public Mono<Void>   handleFileUpload(
      @RequestParam("file") MultipartFile file, @RequestParam("tableName") String tableName)
      throws IOException {

    CompletableFuture<Job> loadJob =
            (CompletableFuture<Job>) this.bigQueryTemplate.writeDataToTable(
                tableName, file.getInputStream(), FormatOptions.csv());
    return Mono.fromRunnable(() -> {
      getResponse(loadJob, tableName);
    });
  }

  @PostMapping("/uploadCsvText")
  public Mono<Void>  handleCsvTextUpload(
      @RequestParam("csvText") String csvData, @RequestParam("tableName") String tableName) {


    return Mono.fromRunnable(() -> {
    });
  }

  private Mono<Void>  getResponse(CompletableFuture<Job> loadJob, String tableName) {
    String message;
    try {
      Job job = loadJob.get();
      message = "Successfully loaded data file to " + tableName;
    } catch (Exception e) {
      e.printStackTrace();
      message = "Error: " + e.getMessage();
    }


    return Mono.fromRunnable(() -> {
    });
  }*/
}
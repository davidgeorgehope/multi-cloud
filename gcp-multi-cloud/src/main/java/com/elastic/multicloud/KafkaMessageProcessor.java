package com.elastic.multicloud;

import co.elastic.apm.api.Scope;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;

@Service
public class KafkaMessageProcessor {
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
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageProcessor.class);

    public String processMessage(final ConsumerRecord<String, String> record) {

       /* String recordValue;
        LOGGER.info("DAVID HEADER LOOP");

        for (Header h : record.headers()) {
            LOGGER.info("DAVID HEADER LOOP"+h.key()+"   "+new String(h.value()));

            if(h.key().equals("traceparent")){
                Transaction transaction = ElasticApm.startTransactionWithRemoteParent(key -> new String(h.value()));
                LOGGER.info("DAVID GETID"+new String(h.value()));
                LOGGER.info("DAVID GETID"+transaction.getId());
                LOGGER.info("DAVID GETID"+transaction.getTraceId());
                // creates a transaction representing the server-side handling of the request
            try  {
                LOGGER.info("STUFF HAPPENING"+1);
                final Scope scope = transaction.activate();
                LOGGER.info("STUFF HAPPENING");

                String name = "DAVID";
                transaction.setName(name);
                transaction.setType(Transaction.TYPE_REQUEST);
                try {
                    //simulate some time-consuming stuff
                    Thread.sleep(200);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                LOGGER.info("STUFF HAPPENING");


            } catch (Exception e) {
                LOGGER.error("STUFF HAPPENING",e);
                transaction.captureException(e);
                throw e;
            } finally {
                LOGGER.info("DAVID END");

                transaction.end();
            }
            }
        }*/
        return record.value();
    }
}

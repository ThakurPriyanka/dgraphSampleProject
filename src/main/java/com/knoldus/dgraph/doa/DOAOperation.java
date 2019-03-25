package com.knoldus.dgraph.doa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.knoldus.dgraph.connection.DgraphConnect;
import com.knoldus.dgraph.module.People;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto;
import io.dgraph.Transaction;
import io.dgraph.TxnConflictException;
import io.grpc.StatusRuntimeException;

import java.util.Collections;
import java.util.Map;

public class DOAOperation {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static DgraphClient dgraphClient = DgraphConnect.getDgraphConnectInstance().getDgraphClient();
    private static Config config = ConfigFactory.load("application.conf");

    public <T> boolean addNode(final T element) {
        DgraphProto.Mutation mutation = getMutation(element);
        try (Transaction txn = dgraphClient.newTransaction()) {
            try {
                txn.mutate(mutation);
                txn.commit();
            } catch (StatusRuntimeException | TxnConflictException dgraphException) {
                txn.discard();
                throw new RuntimeException("Unable to persist the transaction." + dgraphException.getMessage());
            }
            return true;
        }
    }

    public boolean alterSchema() {
        try {

            dgraphClient.alter(DgraphProto.Operation.newBuilder().setDropAll(true).build());

            // Set schema
            String schema = config.getString("schema");
            DgraphProto.Operation op = DgraphProto.Operation.newBuilder().setSchema(schema).build();
            dgraphClient.alter(op);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while altering schema." + e);
        }
    }

    public <T> DgraphProto.Mutation getMutation(final T element) {
        try {
            final String inputJson = objectMapper.writeValueAsString(element);

            return DgraphProto.Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(inputJson)).build();
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Error occurred while parsing." + ex.getMessage());
        }
    }

    public People getPeople() {
        // Query
        String query = config.getString("query.getPersons");
        Map<String, String> vars = Collections.singletonMap("$personName", "sam");
        try {
            DgraphProto.Response response = dgraphClient.newTransaction().queryWithVars(query, vars);
            // Deserialize
            People ppl = objectMapper.readValue(response.getJson().toByteArray(), People.class);

            return ppl;
        } catch (Exception ex) {
            throw new RuntimeException("Result can not be cast into object." + ex);
        }

    }
}

package com.knoldus.dgraph.connection;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.dgraph.DgraphClient;
import io.dgraph.DgraphGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DgraphConnect {
    private static DgraphConnect dgraphConnect;
    private static DgraphClient dgraphClient;
    private static Config config = ConfigFactory.load("application.conf");
    private static final String TEST_HOSTNAME = config.getString("dgraph.host");
    private static final int TEST_PORT = config.getInt("dgraph.port");

    private DgraphConnect() {
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress(TEST_HOSTNAME, TEST_PORT)
                        .usePlaintext(true).build();
        DgraphGrpc.DgraphStub stub = DgraphGrpc.newStub(channel);

        this.dgraphClient = new DgraphClient(stub);
    }

    public static DgraphClient getDgraphClient() {
        return dgraphClient;
    }

    public static DgraphConnect getDgraphConnectInstance() {
        if (dgraphConnect == null) {
            dgraphConnect = new DgraphConnect();
        }
        return dgraphConnect;
    }
}

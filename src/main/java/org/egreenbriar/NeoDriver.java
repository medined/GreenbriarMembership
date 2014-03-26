package org.egreenbriar;

import org.egreenbriar.graph.RelationshipTypes;
import org.egreenbriar.service.DatabaseService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public class NeoDriver {

    private static final String DB_PATH = "/Users/davidmedinets/Dropbox/eGreenbriar/membership/graphdb";

    public static void main(String[] args) {
        DatabaseService databaseService = new DatabaseService();
        databaseService.setDatabaseDirectory(DB_PATH);
        databaseService.initialize();

        GraphDatabaseService graphService = databaseService.getGraphService();

        Node community = databaseService.getCommunity();
        Node districts = databaseService.getDistricts();
        Node blocks = databaseService.getBlocks();
        Node houses = databaseService.getHouses();
        Node people = databaseService.getPeople();

        databaseService.log(people);
        try (Transaction tx = graphService.beginTx()) {
            for (Relationship relationship : houses.getRelationships(RelationshipTypes.HAS_HOUSE)) {
                databaseService.logRelationship(people, relationship);
                Node house = relationship.getEndNode();
                databaseService.log(house);
            }
        }
    }

}

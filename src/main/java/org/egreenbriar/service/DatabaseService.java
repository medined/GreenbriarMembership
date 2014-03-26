package org.egreenbriar.service;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.egreenbriar.graph.NodeTypes;
import org.egreenbriar.graph.RelationshipTypes;
import org.egreenbriar.model.Block;
import org.egreenbriar.model.District;
import org.egreenbriar.model.House;
import org.egreenbriar.model.Person;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private static final Logger log = Logger.getLogger(DatabaseService.class);
    
    @Value("${database.directory}")
    private String databaseDirectory = null;

    private GraphDatabaseService graphService = null;

    public static final String TYPE_PROPERTY = "qqq.type";
    public static final String VALUE_PROPERTY = "qqq.value";
    public static final String NAME_PROPERTY = "qqq.name";
    public static final String EQUALITY_PROPERTY = "qqq.equality";

    private Node root = null;
    private Node community = null;
    private Node districts = null;
    private Node blocks = null;
    private Node houses = null;
    private Node people = null;    

    @PostConstruct
    public void initialize() {
        setGraphService(new GraphDatabaseFactory().newEmbeddedDatabase(getDatabaseDirectory()));
        registerShutdownHook(getGraphService());
        try (Transaction tx = graphService.beginTx()) {
            root = graphService.getNodeById(0);
            tx.success();
        } catch (NotFoundException e) {
            root = create(NodeTypes.ROOT, "root");
        }
        community = uniqueChild(root, "community", NodeTypes.COMMUNITY, RelationshipTypes.HAS_COMMUNITY);
        setDistricts(uniqueChild(community, "districts", NodeTypes.DISTRICTS, RelationshipTypes.HAS_DISTRICTS));
        blocks = uniqueChild(community, "blocks", NodeTypes.BLOCKS, RelationshipTypes.HAS_BLOCKS);
        houses = uniqueChild(community, "houses", NodeTypes.HOUSES, RelationshipTypes.HAS_HOUSES);
        people = uniqueChild(community, "people", NodeTypes.PEOPLE, RelationshipTypes.HAS_PEOPLE);
    }

    private void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                getGraphService().shutdown();
            }
        });
    }

    public GraphDatabaseService getGraphService() {
        return graphService;
    }

    public void setGraphService(GraphDatabaseService graphService) {
        this.graphService = graphService;
    }

    public String getDatabaseDirectory() {
        return databaseDirectory;
    }

    public void setDatabaseDirectory(String databaseDirectory) {
        this.databaseDirectory = databaseDirectory;
    }

    public Node create(final NodeTypes type, final String equality) {
        Node node = null;
        try (Transaction tx = graphService.beginTx()) {
            node = graphService.createNode();
            node.setProperty(TYPE_PROPERTY, type.toString());
            node.setProperty(EQUALITY_PROPERTY, equality);
            tx.success();
        }
        return node;
    }

    public Node getRoot() {
        return root;
    }
    
    public Node getCommunity() {
        return community;
    }

    public Node createDistrict(final String name, final String representative) {
        Node district = uniqueChild(getDistricts(), name, NodeTypes.DISTRICT, RelationshipTypes.HAS_DISTRICT);
        try (Transaction tx = graphService.beginTx()) {
            district.setProperty("representative", representative);
            tx.success();
        }
        return district;
    }

    public Node createBlock(final Node owner, final String name, final String captain) {
        Node block = uniqueChild(owner, name, NodeTypes.BLOCK, RelationshipTypes.HAS_BLOCK);
        try (Transaction tx = graphService.beginTx()) {
            uniqueRelationship(blocks, RelationshipTypes.HAS_BLOCK, block);
            block.setProperty("captain", captain);
            tx.success();
        }
        return block;
    }

    public Node createHouse(final Node owner, final String houseNumber, final String streetName, final boolean notMember2012, final boolean notMember2013) {
        Node house = uniqueChild(owner, houseNumber + " " + streetName, NodeTypes.HOUSE, RelationshipTypes.HAS_HOUSE);
        try (Transaction tx = graphService.beginTx()) {
            uniqueRelationship(getHouses(), RelationshipTypes.HAS_HOUSE, house);
            house.setProperty("pk", UUID.randomUUID().toString());
            house.setProperty("member2012", notMember2012 ? "no" : "yes");
            house.setProperty("member2013", notMember2013 ? "no" : "yes");
            tx.success();
        }
        return house;
    }

    public Node createPerson(final Node owner, final String last, final String first, final String phone, final String email, final String comment, final boolean unlisted, final boolean noDirectory) {
        Node person = uniqueChild(owner, last + " " + first, NodeTypes.PERSON, RelationshipTypes.HAS_PERSON);
        try (Transaction tx = graphService.beginTx()) {
            uniqueRelationship(getPeople(), RelationshipTypes.HAS_PEOPLE, person);
            person.setProperty("pk", UUID.randomUUID().toString());
            person.setProperty("last", last);
            person.setProperty("last", last);
            person.setProperty("first", first);
            person.setProperty("phone", phone);
            person.setProperty("email", email);
            person.setProperty("comment", comment);
            person.setProperty("listed", unlisted ? "no" : "yes");
            person.setProperty("directory", noDirectory ? "no" : "yes");
            tx.success();
        }
        return person;
    }

    public Node uniqueChild(final Node owner, final String equality, final NodeTypes nodeType, final RelationshipTypes relationshipType) {
        if (owner == null) {
            throw new RuntimeException("owner must not be null.");
        }
        Node node = null;
        try (Transaction tx = graphService.beginTx()) {
            for (Relationship relationship : owner.getRelationships(relationshipType)) {
                if (relationship.getEndNode().getProperty(EQUALITY_PROPERTY).equals(equality)) {
                    node = relationship.getEndNode();
                    break;
                }
            }
            if (node == null) {
                node = create(nodeType, equality);
                uniqueRelationship(owner, relationshipType, node);
            }
            tx.success();
        }
        return node;
    }

    public void uniqueRelationship(final Node left, RelationshipTypes relationshipType, final Node right) {
        Node node = null;
        try (Transaction tx = graphService.beginTx()) {
            for (Relationship relationship : left.getRelationships(relationshipType)) {
                String endNodeEquality = (String) relationship.getEndNode().getProperty(EQUALITY_PROPERTY);
                String rightEquality = (String) right.getProperty(EQUALITY_PROPERTY);
                if (endNodeEquality.equals(rightEquality)) {
                    node = left;
                    break;
                }
            }
            if (node == null) {
                left.createRelationshipTo(right, relationshipType);
            }
            tx.success();
        }
    }

    /**
     * @return the districts
     */
    public Node getDistricts() {
        return districts;
    }

    /**
     * @param districts the districts to set
     */
    public void setDistricts(Node districts) {
        this.districts = districts;
    }

    public Node getHouses() {
        return houses;
    }

    /**
     * @return the people
     */
    public Node getPeople() {
        return people;
    }

    public String type(final Node node) {
        String rv = null;
        try {
            if (node != null) {
                rv = (String)node.getProperty(DatabaseService.TYPE_PROPERTY);
            }
        } catch (NotFoundException e) {
            // ignore
        }
        return rv;
    }

    public String equality(final Node node) {
        String rv = null;
        try {
            if (node != null) {
                rv = (String)node.getProperty(DatabaseService.EQUALITY_PROPERTY);
            }
        } catch (NotFoundException e) {
            // ignore
        }
        return rv;
    }

    public void log(final Node node) {
        try (Transaction tx = graphService.beginTx()) {
            log.info(String.format("--- %05d ------", node.getId()));
            log.info(node);
            for (String propertyName : node.getPropertyKeys()) {
                Object property = node.getProperty(propertyName);
                log.info(String.format("%s = [%s]", propertyName, property));
            }
            for (Relationship relationship : node.getRelationships()) {
                logRelationship(node, relationship);
            }
            tx.success();
        }
    }

    public void logProperties(final Node node) {
        try (Transaction tx = graphService.beginTx()) {
            log.info(String.format("--- %05d ------", node.getId()));
            for (String propertyName : node.getPropertyKeys()) {
                Object property = node.getProperty(propertyName);
                log.info(String.format("%s = [%s]", propertyName, property));
            }
            tx.success();
        }
    }

    public void logRelationships(final Node node) {
        try (Transaction tx = graphService.beginTx()) {
            log.info(String.format("--- %05d ------", node.getId()));
            for (Relationship relationship : node.getRelationships()) {
                logRelationship(node, relationship);
            }
            tx.success();
        }
    }

    public void logRelationship(final Node node, final Relationship relationship) {
        try (Transaction tx = graphService.beginTx()) {
            Node start = relationship.getStartNode();
            Node end = relationship.getEndNode();
            String name = relationship.getType().name();
            String direction = node != start ? "->" : "<-";
            log.info(String.format("%s %s %s %s %s", equality(start), direction, name, direction, equality(end)));
            tx.success();
        }
    }

    public Map<String, Block> getBlocks() {
        Map<String, Block> _blocks = new TreeMap<>();
        try (Transaction tx = graphService.beginTx()) {
            for (Relationship relationship : blocks.getRelationships(RelationshipTypes.HAS_BLOCK)) {
                log(relationship.getEndNode());
                Block block = new Block(relationship.getEndNode());
                _blocks.put(block.getBlockName(), block);
            }
            tx.success();
        }
        return _blocks;
    }

    public Block getBlock(String blockName) {
        Block block = null;
        try (Transaction tx = graphService.beginTx()) {
            for (Relationship relationship : blocks.getRelationships(RelationshipTypes.HAS_BLOCK)) {
                Node endNode = relationship.getEndNode();
                String endName = equality(endNode);
                if (blockName.equals(endName)) {
                    block = new Block(endNode);
                    break;
                }
            }
            tx.success();
        }
        return block;
    }

    public District getDistrict(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public House getHouse(String houseUuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Person getPerson(String personUuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

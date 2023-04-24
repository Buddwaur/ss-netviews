package netviews.netviewscli;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import models.Assignment;
import models.Association;
import models.NVWrapper;
import models.Node;

class NVWrapperTest {
	
	private NVWrapper policy;
	
	@BeforeEach
	void BeforeEach() {
		try {
			FileReader fileReader = new FileReader("/home/noah/netviews/netviewsCLI/netviewscli/src/main/java/test_files/med-topo-policy.json");
			Gson gson = new Gson();
			policy = gson.fromJson(fileReader, NVWrapper.class);
		}
		catch (FileNotFoundException e) {
			//do nothing
		}
		
	}

	@Test
	void testGetNodes() {
		ArrayList<Node> nodes = policy.getNodes();
		assertEquals(nodes.size(), 8);
		assertEquals(nodes.get(0).getName(), "role");
		assertEquals(nodes.get(0).getType(), "PC");
		
		assertEquals(nodes.get(1).getName(), "h1");
		assertEquals(nodes.get(1).getType(), "U");
		
		assertEquals(nodes.get(2).getName(), "h2");
		assertEquals(nodes.get(2).getType(), "O");
		
		assertEquals(nodes.get(3).getName(), "h3");
		assertEquals(nodes.get(3).getType(), "U");
		
		assertEquals(nodes.get(4).getName(), "h4");
		assertEquals(nodes.get(4).getType(), "O");
		
		assertEquals(nodes.get(5).getName(), "HRU");
		assertEquals(nodes.get(5).getType(), "UA");
		
		assertEquals(nodes.get(6).getName(), "Other");
		assertEquals(nodes.get(6).getType(), "UA");
		
		assertEquals(nodes.get(7).getName(), "Dev");
		assertEquals(nodes.get(7).getType(), "OA");
	}
	
	@Test
	void testGetAssignments() {
		ArrayList<Assignment> assignment = policy.getAssignments();
		
		assertEquals(7, assignment.size());
		
		assertEquals("h1", assignment.get(0).getSource());
		assertEquals("HRU", assignment.get(0).getTarget());
		
		assertEquals("h3", assignment.get(1).getSource());
		assertEquals("Other", assignment.get(1).getTarget());
		
		assertEquals("h2", assignment.get(2).getSource());
		assertEquals("Dev", assignment.get(2).getTarget());
		
		assertEquals("h4", assignment.get(3).getSource());
		assertEquals("Dev", assignment.get(3).getTarget());
		
		assertEquals("HRU", assignment.get(4).getSource());
		assertEquals("role", assignment.get(4).getTarget());
		
		assertEquals("Dev", assignment.get(5).getSource());
		assertEquals("role", assignment.get(5).getTarget());
		
		assertEquals("Other", assignment.get(6).getSource());
		assertEquals("role", assignment.get(6).getTarget());
		
	}
	
	@Test
	void testGetAssociations() {
		ArrayList<Association> associations = policy.getAssociations();
		
		assertEquals(1, associations.size());
		
		assertEquals("HRU", associations.get(0).getSource());
		assertEquals("Dev", associations.get(0).getTarget());
		assertEquals("tcp/22 arp icmp", associations.get(0).operationsString());
	}
	
	@Test
	void testAddNode() {
		assertEquals(8, policy.getNodes().size());
		
		try {
			policy.addNode("", "U", null);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(8, policy.getNodes().size());
		}
		
		try {
			policy.addNode("h5", "", null);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(8, policy.getNodes().size());
		}
		
		try {
			policy.addNode("h1", "U", null);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(8, policy.getNodes().size());
		}
		
		policy.addNode("h5", "O", null);
		
		ArrayList<Node> nodes = policy.getNodes();
		
		assertEquals(9, nodes.size());
		
		assertEquals("h5", nodes.get(8).getName());
		assertEquals("O", nodes.get(8).getType());
		
	}
	
	@Test 
	void testDeletNode() {
		assertEquals(8, policy.getNodes().size());
		
		try {
			policy.removeNode("h5");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(8, policy.getNodes().size());
		}
		
		policy.removeNode("h3");
		
		ArrayList<Node> nodes = policy.getNodes();
		
		assertEquals(7, nodes.size());
		
		assertEquals(nodes.get(0).getName(), "role");
		assertEquals(nodes.get(0).getType(), "PC");
		
		assertEquals(nodes.get(1).getName(), "h1");
		assertEquals(nodes.get(1).getType(), "U");
		
		assertEquals(nodes.get(2).getName(), "h2");
		assertEquals(nodes.get(2).getType(), "O");
		
		assertEquals(nodes.get(3).getName(), "h4");
		assertEquals(nodes.get(3).getType(), "O");
		
		assertEquals(nodes.get(4).getName(), "HRU");
		assertEquals(nodes.get(4).getType(), "UA");
		
		assertEquals(nodes.get(5).getName(), "Other");
		assertEquals(nodes.get(5).getType(), "UA");
		
		assertEquals(nodes.get(6).getName(), "Dev");
		assertEquals(nodes.get(6).getType(), "OA");
	}
	
	@Test
	void testAddAssignment() {
		assertEquals(7, policy.getAssignments().size());
		
		try {
			policy.addAssignment("", "h2");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(7, policy.getAssignments().size());
		}
		
		try {
			policy.addAssignment("h2", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(7, policy.getAssignments().size());
		}
		
		try {
			policy.addAssignment("h3", "Other");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(7, policy.getAssignments().size());
		}
		
		try {
			policy.addAssignment("h5", "HRU");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(7, policy.getAssignments().size());
		}
		
		try {
			policy.addAssignment("h3", "HRUS");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(7, policy.getAssignments().size());
		}
		
		policy.addAssignment("h3", "HRU");
		
		ArrayList<Assignment> assignment = policy.getAssignments();
		
		assertEquals(8, assignment.size());
		
		assertEquals("h3", assignment.get(7).getSource());
		assertEquals("HRU", assignment.get(7).getTarget());
	}
	
	@Test
	void testDeleteAssignment() {
		assertEquals(7, policy.getAssignments().size());
		
		try {
			policy.removeAssignment("h3", "HRU");
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(7, policy.getAssignments().size());
		}
		
		policy.removeAssignment("h4", "Dev");
		
		ArrayList<Assignment> assignment = policy.getAssignments();
		
		assertEquals(6, assignment.size());
		
		assertEquals("h1", assignment.get(0).getSource());
		assertEquals("HRU", assignment.get(0).getTarget());
		
		assertEquals("h3", assignment.get(1).getSource());
		assertEquals("Other", assignment.get(1).getTarget());
		
		assertEquals("h2", assignment.get(2).getSource());
		assertEquals("Dev", assignment.get(2).getTarget());
		
		assertEquals("HRU", assignment.get(3).getSource());
		assertEquals("role", assignment.get(3).getTarget());
		
		assertEquals("Dev", assignment.get(4).getSource());
		assertEquals("role", assignment.get(4).getTarget());
		
		assertEquals("Other", assignment.get(5).getSource());
		assertEquals("role", assignment.get(5).getTarget());
	}
	
	@Test
	void testAddAssociation() {
		assertEquals(1, policy.getAssociations().size());
		
		String[] operations = {"tcp/22", "arp", "icmp"};
		
		try {
			policy.addAssociation("", "h2", operations);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(1, policy.getAssociations().size());
		}
		
		try {
			policy.addAssociation("h2", "", operations);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(1, policy.getAssociations().size());
		}
		
		try {
			policy.addAssociation("HRU", "Dev", operations);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(1, policy.getAssociations().size());
		}
		
		try {
			policy.addAssociation("h5", "Other", operations);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(1, policy.getAssociations().size());
		}
		
		policy.addAssociation("Other", "HRU", operations);
		
		ArrayList<Association> associations = policy.getAssociations();
		
		assertEquals(2, associations.size());
		
		assertEquals("Other", associations.get(1).getSource());
		assertEquals("HRU", associations.get(1).getTarget());
		assertEquals("tcp/22 arp icmp", associations.get(1).operationsString());
	}
	
	@Test
	void testDeleteAssociation() {
		assertEquals(1, policy.getAssociations().size());
		
		try {
			policy.removeAssociation("HRU", "Other");
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(1, policy.getAssociations().size());
		}
		
		policy.removeAssociation("HRU", "Dev");
		
		assertEquals(0, policy.getAssociations().size());
		
	}

}

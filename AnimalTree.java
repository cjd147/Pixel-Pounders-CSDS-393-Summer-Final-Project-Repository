import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * AnimalTree represents the family tree rooted at one specific animal.
 * It is built from the existing mother/father/children links on Animal —
 * it does not store relationship data itself, it just organizes and
 * presents what's already there.
 */
public class AnimalTree {

    //fields

    //root represents the specific animal this tree is built from
    private final Animal root;

    //maxDepth guards against incomplete/bad data (e.g. an animal
    //accidentally linked as its own ancestor) causing infinite recursion
    private int maxDepth = 10;

    //Constructor
    public AnimalTree(Animal root){
        this.root = root;
    }

    public AnimalTree(Animal root, int maxDepth){
        this.root = root;
        this.maxDepth = maxDepth;
    }

    //Getter/Setters

    public Animal getRoot() {
        return root;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    //Tree-building methods

    //getAncestors builds the pedigree (mother/father) side of the tree
    public Node getAncestors(){
        return buildAncestors(root, 0);
    }

    private Node buildAncestors(Animal animal, int depth){
        if(animal == null || depth >= maxDepth){
            return null;
        }
        Node node = new Node(animal);
        node.mother = buildAncestors(animal.getMother(), depth + 1);
        node.father = buildAncestors(animal.getFather(), depth + 1);
        return node;
    }

    //getDescendants builds the children side of the tree
    public Node getDescendants(){
        return buildDescendants(root, 0);
    }

    private Node buildDescendants(Animal animal, int depth){
        if(animal == null || depth >= maxDepth){
            return null;
        }
        Node node = new Node(animal);
        List<Animal> children = animal.getChildren();
        if(children != null){
            for(Animal child : children){
                node.children.add(buildDescendants(child, depth + 1));
            }
        }
        return node;
    }

    //Display Methods

    public void printAncestors(){
        printAncestorNode(getAncestors(), 0);
    }

    private void printAncestorNode(Node node, int depth){
        if(node == null){
            return;
        }
        System.out.println("  ".repeat(depth) + node.animal.getNickname());
        printAncestorNode(node.mother, depth + 1);
        printAncestorNode(node.father, depth + 1);
    }

    public void printDescendants(){
        printDescendantNode(getDescendants(), 0);
    }

    private void printDescendantNode(Node node, int depth){
        if(node == null){
            return;
        }
        System.out.println("  ".repeat(depth) + node.animal.getNickname());
        for(Node child : node.children){
            printDescendantNode(child, depth + 1);
        }
    }

    //Nested Class Node represents one animal's position within this specific tree.
    public static class Node {

        //fields
        private final Animal animal;
        private Node mother;
        private Node father;
        private final List<Node> children = new ArrayList<>();

        //Constructor
        public Node(Animal animal){
            this.animal = animal;
        }

        //Getters
        public Animal getAnimal() {
            return animal;
        }

        public Node getMother() {
            return mother;
        }

        public Node getFather() {
            return father;
        }

        public List<Node> getChildren() {
            return children;
        }
    }

    //Relationship Search Methods

    //findRelationship searches for how the given animal relates to root (e.g. "sibling",
    //"grandchild", "second cousin"). Returns null if no relationship is found within maxDepth.
    public String findRelationship(Animal target){
        if(target == null){
            return null;
        }
        if(target == root){
            return "self";
        }

        Map<Animal, Integer> rootAncestors = getAncestorDistances(root);
        Map<Animal, Integer> targetAncestors = getAncestorDistances(target);

        Animal commonAncestor = null;
        int bestTotal = Integer.MAX_VALUE;
        int rootDistance = -1;
        int targetDistance = -1;

        for(Map.Entry<Animal, Integer> entry : rootAncestors.entrySet()){
            Animal candidate = entry.getKey();
            if(targetAncestors.containsKey(candidate)){
                int total = entry.getValue() + targetAncestors.get(candidate);
                if(total < bestTotal){
                    bestTotal = total;
                    commonAncestor = candidate;
                    rootDistance = entry.getValue();
                    targetDistance = targetAncestors.get(candidate);
                }
            }
        }

        if(commonAncestor == null){
            return null; //no relationship found within maxDepth
        }

        return describeRelationship(rootDistance, targetDistance);
    }

    //getAncestorDistances walks up from the given animal through mother/father,
    //returning a map of every ancestor (including the animal itself at distance 0)
    //to how many generations up it is.
    private Map<Animal, Integer> getAncestorDistances(Animal animal){
        Map<Animal, Integer> distances = new HashMap<>();
        collectAncestorDistances(animal, 0, distances);
        return distances;
    }

    private void collectAncestorDistances(Animal animal, int depth, Map<Animal, Integer> distances){
        if(animal == null || depth > maxDepth){
            return;
        }
        //Only keep the shortest path found (relevant if mother and father share an
        //ancestor further back, e.g. inbreeding)
        Integer existing = distances.get(animal);
        if(existing != null && existing <= depth){
            return;
        }
        distances.put(animal, depth);
        collectAncestorDistances(animal.getMother(), depth + 1, distances);
        collectAncestorDistances(animal.getFather(), depth + 1, distances);
    }

    //describeRelationship converts generational distances from root and target to their
    //closest common ancestor into a human-readable relationship label.
    private String describeRelationship(int rootDistance, int targetDistance){
        if(rootDistance == 0){
            return generationLabel(targetDistance, "child") + " (descendant)";
        }
        if(targetDistance == 0){
            return generationLabel(rootDistance, "parent") + " (ancestor)";
        }
        if(rootDistance == 1 && targetDistance == 1){
            return "sibling";
        }

        int cousinDegree = Math.min(rootDistance, targetDistance) - 1;
        int removed = Math.abs(rootDistance - targetDistance);

        if(cousinDegree == 0){
            boolean targetIsYounger = targetDistance > rootDistance;
            String label = targetIsYounger ? "niece/nephew" : "aunt/uncle";
            if(removed > 1){
                label = "great-".repeat(removed - 1) + label;
            }
            return label;
        }

        String label = ordinal(cousinDegree) + " cousin";
        if(removed > 0){
            label += ", " + numberWord(removed) + " removed";
        }
        return label;
    }

    private String generationLabel(int distance, String base){
        if(distance == 1){
            return base;
        }
        if(distance == 2){
            return "grand" + base;
        }
        return "great-".repeat(distance - 2) + "grand" + base;
    }

    private String ordinal(int n){
        String[] words = {"zeroth","first","second","third","fourth","fifth","sixth","seventh","eighth","ninth","tenth"};
        return (n >= 0 && n < words.length) ? words[n] : n + "th";
    }

    private String numberWord(int n){
        return switch (n) {
            case 1 -> "once";
            case 2 -> "twice";
            default -> n + " times";
        };
    }
}

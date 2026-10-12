/**
 * A custom user-defined type representing a store product.
 * Implements Comparable so it can be sorted within the Binary Search Tree based on its ID.
 */
public class Product implements Comparable<Product> {
    
    /** The unique identifier used for sorting in the BST. */
    private int productId;
    
    /** The name of the product. */
    private String name;
    
    /** The price of the product. */
    private double price;

    /**
     * Constructor for the Product type.
     *
     * @param id    The unique product ID.
     * @param name  The name of the product.
     * @param price The price of the product.
     */
    public Product(int id, String name, double price) {
        this.productId = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Compares this Product with another based on their Product ID.
     *
     * @param other The other Product to compare against.
     * @return A negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.productId, other.productId);
    }

    /**
     * Returns a string representation of the product, used for drawing the tree graphically.
     *
     * @return Formatted string, e.g., "Laptop[105]".
     */
    @Override
    public String toString() {
        return name + "[" + productId + "]";
    }

    /**
     * Helper method to parse a formatted string into a Product object.
     * Expected format from the GUI client: "ID-Name-Price" (e.g., "105-Laptop-999.99").
     *
     * @param input The raw string from the client.
     * @return A new Product instance.
     * @throws IllegalArgumentException if the format is incorrect.
     */
    public static Product parse(String input) {
        String[] parts = input.split("-");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid format. Use ID-Name-Price (e.g., 105-Laptop-999.99)");
        }
        return new Product(
            Integer.parseInt(parts[0].trim()), 
            parts[1].trim(), 
            Double.parseDouble(parts[2].trim())
        );
    }
}

package Service;

public interface CartService {
    void addItemToCart(int itemNums, int quantity);
    void removeItemFromCart(int itemId);
    void checkout();
    void addItemAsAdmin(int itemNums);
    void clearCart();



}

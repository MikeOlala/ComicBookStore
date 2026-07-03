import controller.OrderController;
import controller.PaymentController;
import controller.RegisterController;
import dto.OrderRequest;
import dto.PaymentRequest;
import dto.RegisterRequest;
import model.Cart;
import model.ComicBook;
import model.Customer;
import model.Order;
import strategy.CODPayment;

public class Main {

    public static void main(String[] args) {

        try {

            System.out.println("========== UC1 - REGISTER ==========");

            RegisterController registerController =
                    new RegisterController();

            RegisterRequest registerRequest =
                    new RegisterRequest(
                            "Nguyen Van A",
                            "vana@gmail.com",
                            "123456",
                            "123456"
                    );

            Customer customer =
                    registerController.register(registerRequest);

            System.out.println("Register Successfully");

            System.out.println(customer.getFullName());

            System.out.println(customer.getEmail());

            //-----------------------------------------------------

            System.out.println();

            System.out.println("========== UC5 - PLACE ORDER ==========");

            ComicBook comicBook = new ComicBook();

            comicBook.setComicId(1);

            comicBook.setTitle("One Piece");

            comicBook.setPrice(50000);

            comicBook.setStock(20);

            Cart cart = customer.getCart();

            cart.addItem(comicBook,2);

            OrderController orderController =
                    new OrderController();

            OrderRequest orderRequest =
                    new OrderRequest(
                            customer,
                            cart,
                            "Da Nang",
                            "0905123456"
                    );

            Order order =
                    orderController.placeOrder(orderRequest);

            System.out.println("Order Success");

            System.out.println(order.getOrderId());

            System.out.println(order.getTotalAmount());

            //-----------------------------------------------------

            System.out.println();

            System.out.println("========== UC6 - PAYMENT ==========");

            PaymentController paymentController =
                    new PaymentController();

            PaymentRequest paymentRequest =
                    new PaymentRequest(
                            order.getOrderId(),
                            new CODPayment()
                    );

            paymentController.pay(paymentRequest);

            System.out.println();

            System.out.println("Current State : "
                    + order.getState().getStateName());

        }

        catch (Exception ex) {

            ex.printStackTrace();

        }

    }

}
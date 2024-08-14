package cz.osu.prf.kip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    List<Invoice> invoices = getData();

    List<Customer> importantAtlantaCustomers = invoices.stream()
            .filter(q -> q.items().stream().mapToDouble(p -> p.totalPrice()).sum() > 10_000)
            .filter(q -> q.customer().address().city().equals("Atlanta"))
            .collect(Collectors.groupingBy(q -> q.customer()))
            .entrySet().stream()
            .filter(q -> q.getValue().size() > 5)
            .map(q -> q.getKey())
            .toList();
  }

  private static List<Invoice> getData() {
    Customer[] customers = new Customer[]{
            new Customer("John Doe", new Address("Atlanta")),
            new Customer("Jane Doe", new Address("Paris")),
            new Customer("Victor White", new Address("London")),
            new Customer("Marie-Jane McKenzie", new Address("Atlanta")),
            new Customer("Michael Paine", new Address("Ostrava"))};
    String[] invoiceItemTitles = new String[]{
            "Cleaning services", "Selling services", "Washing services", "IT Services", "Security services"};

    List<Invoice> ret = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      List<InvoiceItem> items = new ArrayList<>();
      int itemsCount = getRandomInt(1, 10);
      for (int j = 0; j < itemsCount; j++) {
        InvoiceItem item = new InvoiceItem(getRandomItem(invoiceItemTitles), getRandomInt(1, 5), getRandomInt(100, 5_000));
        items.add(item);
      }
      Customer customer = getRandomCustomer(customers);
      Invoice invoice = new Invoice(customer, items);
      ret.add(invoice);
    }
    return ret;
  }

  private static Customer getRandomCustomer(Customer[] customers) {
    int index = getRandomInt(0, customers.length);
    return customers[index];
  }

  private static String getRandomItem(String[] invoiceItemTitles) {
    int index = getRandomInt(0, invoiceItemTitles.length);
    return invoiceItemTitles[index];
  }

  private static int getRandomInt(int minimum, int maximum) {
    double tmp = Math.random() * (maximum - minimum) + minimum;
    return (int) tmp;
  }
}

record Address(String city) {}
record Customer(String name, Address address) {}
record InvoiceItem(String title, int amount, double itemPrice) {
  public double totalPrice() {
    return amount * itemPrice;
  }
}
record Invoice(Customer customer, List<InvoiceItem> items) {}

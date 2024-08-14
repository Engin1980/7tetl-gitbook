---
description: >-
  On this page, some other interesting techniques, approaches and tools not
  fitting elsewhere will be explained.
---

# 9 Other interesting tools

## Stream-collection trace evaluation

In modern collection handling in Java, an approach based on streams is very popular. The point is to convert the typical collection source (array, list, set, map/dictionary) into a collection stream and apply a filtering, selecting, ordering, or grouping function to achieve the required functionality.

{% hint style="info" %}
Here, we will not explain the principle of streams and their usage. We expect the reader to be familiar with this concept already. If not, you can freely skip this section.
{% endhint %}

THE IDEa IDE has a very nice and powerful visualization of chained functions and their temporal results in their sequential evaluation. This tool is called **Trace Current Stream Chain**.

Imagine the following example:

&#x20;/TODO Imgs/9-class.png

In this example, there is an invoice with invoice items. Every invoice item has a title, an amount, a price per item, and a total price per all items. Moreover, the invoice has a customer with a name, surname, and address.

{% hint style="info" %}
In real model, the classes will be much more complex. We are trying to keep the classes as simple as possible.
{% endhint %}

{% hint style="info" %}
Note that we implement those classes as records in the related source codes. The important effect of this is that the getter methods do not come with "get...()" prefix, but with the direct value name. So, there is no "getName()", but only "name()".
{% endhint %}

Let's have the following code:

```java
List<Customer> importantAtlantaCustomers = invoices.stream()
        .filter(q -> q.items().stream().mapToDouble(p -> p.totalPrice()).sum() > 10_000)
        .filter(q -> q.customer().address().city().equals("Atlanta"))
        .collect(Collectors.groupingBy(q -> q.customer()))
        .entrySet().stream()
        .filter(q -> q.getValue().size() > 5)
        .map(q -> q.getKey())
        .toList();
```

This code takes a list of invoices, converts them into a \`stream\`, and then:

1. filters (selects) only those with a total price over 10.000,
2. filters (selects) only those with the customers from Atlanta,
3. groups them by the customer; so, after this step, we are getting a customer and a list of her/his invoices w.r.t. the previous filters as a map/dictionary,
4. filters (selects) only those customers with more than five invoices
5. gets only the customers; so, we convert the map/dictionary into a single customers
6. convert the result into the list.

When executed, even in debugging it is hard to see, what exactly happens inside those chained functions. You will only get a result in the `importantAtlantaCustomers` dictionary. However, you can also go through every part of the stream chain and see, what exactly happens at every level. In the Debug menu, choose `Trace Current Stream Chain` and a new window will appear&#x20;

/TODO Imgs/9-trace-0.jpg&#x20;

{% hint style="info" %}
Sometimes it happens that the option `Trace Current Stream Chain` is grayed out even if the code is suspended on a stream chain. In this case, try using the "Search" function - use the zoom icon at the top right IDE corner and look for "Trace Current Stream Chain."
{% endhint %}

/TODO Imgs/9-trace-a.jpg&#x20;

As there are two "stream" parts - the first line and the fifth line (see the `stream()` function call), the IDE will ask which one you are interested in. If you choose the second line (the shorter one), the window with the analysis of the first stream-filter-filter-collect chain will appear. In this window, you can see a tab for every function. You can switch among the tabs and analyze, how the result was achieved for every function.&#x20;

/TODO Imgs/9-trace-b.jpg&#x20;

E.g., for filtering functions, you can check which items pass the filter and are forwarded for further processing. Similarly, for the last tab, you can see how the original list (a sequence) was transformed into the key-value map/dictionary:&#x20;

/TODO Imgs/9-trace-c.jpg /hint&#x20;

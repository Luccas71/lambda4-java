package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import entites.Product;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        String path = "/home/lucas/eclipse-workspace/lambda4-java/in.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            List<Product> list = new ArrayList<>();
            String line = br.readLine();

            while(line != null) {
                String[] fields = line.split(", ");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            double avg = list.stream()
                .map(p -> p.getPrice())
                .reduce(0.0, (x,y) -> x + y) / list.size();

            System.out.println("Average price: " + String.format("%.2f", avg));

            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> productsName = list.stream()
                .filter(p -> p.getPrice() < avg)
                .map(p -> p.getName())
                .sorted(comp.reversed())
                .collect(Collectors.toList());

            productsName.forEach(System.out::println);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
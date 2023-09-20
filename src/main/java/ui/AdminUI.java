package ui;

import categories.Categories;
import categories.CategoriesService;
import product.Product;
import product.ProductRepository;
import product.ProductService;
import product.Status;
import user.User;
import user.UserRepository;
import user.UserService;
import user.UserType;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class AdminUI {
    public static Scanner scannerInt = new Scanner(System.in);
    public static Scanner scannerStr = new Scanner(System.in);
    private final UserService userService = UserService.getInstance();
    private final CategoriesService categoriesService = CategoriesService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final ProductRepository productRepository = ProductRepository.getInstance();

    public void start(User user) {
        boolean isExited = false;
        while (!isExited) {
            System.out.print("""
                    1. Categoriyalar qo`shish
                    2. Categoriya o`chirish
                    3. Mahsulotlar kiritish
                    4. Mahsulot o`chirish
                    5. Mahsulotlar ro`yxatini ko`rish
                    6. Mahsulotlarga o`zgartirish kiritish
                    7. Admin qo`shish
                    8. Admin o`chirish
                                        
                    0. 🔙Chiqish
                    ⇒\s""");
            int command = scannerInt.nextInt();

            switch (command) {
                case 1 -> addCategories();
                case 2 -> removeCategories();
                case 3 -> addProducts();
                case 4 -> removeProducts();
                case 5 -> viewProductsList();
                case 6 -> updateProducts();
                case 7 -> addAdmin();
                case 8 -> deleteAdmin();
                case 0 -> isExited = true;
                default -> System.out.println("Noto'g'ri buyrug' kiritdingiz❗");
            }
        }
    }

    private void removeProducts() {
        System.out.println("Product o`chirilishi kerak bo`lgan categoriyani tanlang ⇒ ");
        int count1 = 0;
        for (Categories categories : categoriesService.getAll()) {
            System.out.println(count1 + 1 + " " + categories.getName());
            count1++;
        }
        int command1 = scannerInt.nextInt();


        if (command1 > 0 && command1 <= categoriesService.getAll().size()) {
            System.out.println("O'chirilishi kerak bo`lgan productni tanlang ⇒ ");
            List<Product> byCategoryId = productService.findByCategoryId(categoriesService.getAll().get(command1 - 1).getId());
            int count2 = 0;
            for (Product product : byCategoryId) {
                System.out.println(count2 + 1 + " " + product.getName() + " " + product.getModel() + " " + product.getPrice());
                count2++;
            }
            int command2 = scannerInt.nextInt();
            if (command2 > 0 && command2 <= byCategoryId.size()) {
                productService.delete(byCategoryId.get(command2 - 1).getId());
                System.out.println("Product muvaffaqqiyatli o'chirildi🎉");
            } else {
                System.out.println("Buyrug' noto'g'ri kiritildi❗");
            }
        } else {
            System.out.println("Buyrug' noto'g'ri kiritildi❗");
        }
    }

    private void removeCategories() {
        System.out.println("O'chirmoqchi bo'lgan categoriyani tanlang ⇒ ");
        int count = 0;
        for (Categories categories : categoriesService.getAll()) {
            System.out.println(count + 1 + " " + categories.getName());
            count++;
        }
        int command1 = scannerInt.nextInt();
        if (command1 > 0 && command1 <= categoriesService.getAll().size()) {
            List<Product> byCategoryId = productService.findByCategoryId(categoriesService.getAll().get(command1 - 1).getId());
            for (Product product : byCategoryId) {
                productService.delete(product.getId());
            }
            categoriesService.delete(categoriesService.getAll().get(command1 - 1).getId());
            System.out.println("Categoriya va uning productlari muvaffaqqiyatli o`chirildi🎉");
        } else {
            System.out.println("Noto`g`ri buyruq kiritdingiz!");
        }
    }

    private void updateProducts() {
        if (categoriesService.getAll().size() > 0) {
            System.out.println("Iltimos, mahsulot categoriyalaridan birini tanlang ⇒ ");
            int count1 = 0;
            for (Categories categories : categoriesService.getAll()) {
                System.out.println(count1 + 1 + " " + categories.getName());
                count1++;
            }
            int command1 = scannerInt.nextInt();

            if (command1 > 0 && command1 <= categoriesService.getAll().size()) {

                System.out.println("Iltimos, o'zgartirish kiritmoqchi bo'lgan mahsulotizni tanlang ⇒ ");
                int count2 = 0;
                List<Product> byCategoryId = productService.findByCategoryId(categoriesService.getAll().get(command1 - 1).getId());
                for (Product product : byCategoryId) {
                    System.out.println(count2 + 1 + " Nomi: " + product.getName() + "  Modeli: " + product.getModel() + "  Narxi: " + product.getPrice() + "  Izohi: " + product.getDescription());
                    count2++;
                }
                int command2 = scannerInt.nextInt();
                if (command2 > 0 && command2 <= byCategoryId.size()) {

                    boolean isExited2 = false;

                    while (!isExited2) {
                        System.out.println("""
                                1. Nomini o'zgartirish
                                2. Modelini o'zgartirish
                                3. Categoriyasini o'zgartirish
                                4. Narxini o'zgartirish
                                5. Izohini o'zgartirish
                                 
                                0. 🔙Chiqish
                                >>""");
                        int command3 = scannerInt.nextInt();

                        switch (command3) {
                            case 1 -> {
                                System.out.print("Yangi nomni kiriting ⇒ ");
                                String newName = scannerStr.nextLine();
                                Product product = byCategoryId.get(command2 - 1);
                                product.setName(newName);
                                productRepository.update(product);

                                System.out.println("O'zgartirish amalga oshirildi🎉");
                            }
                            case 2 -> {
                                System.out.print("Yangi modelni kirirting ⇒ ");
                                String newModel = scannerStr.nextLine();
                                Product product = byCategoryId.get(command2 - 1);
                                product.setModel(newModel);
                                productRepository.update(product);
                                System.out.println("O`zgartirish amalga oshirildi🎉");
                            }
                            case 3 -> {
                                System.out.println("Qaysi categoriyaga o`zgartirmoqchisiz ⇒ ");
                                int count3 = 0;
                                for (Categories categories : categoriesService.getAll()) {
                                    System.out.println(count3 + 1 + " " + categories.getName());
                                    count3++;
                                }
                                int command4 = scannerInt.nextInt();

                                if (command4 > 0 && command4 <= categoriesService.getAll().size()) {
                                    Product product = byCategoryId.get(command2 - 1);
                                    product.setCategories_id(categoriesService.getAll().get(command4 - 1).getId());
                                    productRepository.update(product);
                                    System.out.println("O`zgartirish amalga oshirildi🎉");
                                } else {
                                    System.out.println("Noto`g`ri buyruq kiritildi❗");
                                }
                            }
                            case 4 -> {
                                System.out.print("Yangi narxni kiriting ⇒ ");
                                try {
                                    double newPrice = scannerInt.nextDouble();
                                    Product product = byCategoryId.get(command2 - 1);
                                    product.setPrice(newPrice);
                                    productRepository.update(product);
                                    System.out.println("O`zgartirish amalga oshirildi🎉");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Narx xato kiritildi❗");
                                }

                            }
                            case 5 -> {
                                System.out.println("Yangi izohni kiriting ⇒ ");
                                String newDescription = scannerStr.nextLine();
                                Product product = byCategoryId.get(command2 - 1);
                                product.setDescription(newDescription);
                                productRepository.update(product);
                                System.out.println("O`zgartirish amalga oshirildi🎉");
                            }
                            case 0 -> isExited2 = true;
                            default -> System.out.println("Noto`g`ri buyruq kiritdingiz❗");
                        }
                    }
                }
            } else {
                System.out.println("Noto`g`ri buyruq kiritdingiz❗");
            }
        } else {
            System.out.println("Birorta ham category topilmadi❗");
        }
    }

    private void addCategories() {
        Categories categories = new Categories();
        System.out.println("Iltimos, categoriya nomini kiriting ⇒ ");
        String categoryName = scannerStr.nextLine();

        categories.setName(categoryName);
        categories.setId(UUID.randomUUID());
        categoriesService.add(categories);
    }

    private void deleteAdmin() {
        System.out.print("Admin olmoqchi bulgan user idisi kiriting ⇒ ");
        String id = scannerStr.nextLine();

        try {
            UUID uuid = UUID.fromString(id);
            Optional<User> optionalUser = userService.findById(uuid);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getUserType() != UserType.USER) {
                    user.setUserType(UserType.USER);
                    UserRepository.getInstance().update(user);
                } else {
                    System.out.println("Ushbu user adminlar ro'yhatida mavjud emas❗");
                }
            } else {
                System.out.println("Bu user admin emas❗");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Id xato kiritildi❗");
        }

    }

    private void addAdmin() {
        System.out.print("Admin qilmoqchi bulgan user idisi kiriting ⇒ ");
        String id = scannerStr.nextLine();
        try {
            UUID uuid = UUID.fromString(id);
            Optional<User> optionalUser = userService.findById(uuid);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getUserType() != UserType.ADMIN) {
                    user.setUserType(UserType.ADMIN);
                    UserRepository.getInstance().update(user);
                } else {
                    System.out.println("Ushbu user allaqachon admin bulgan❗");
                }
            } else {
                System.out.println("Bu user topilmadi❗");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Id xato kiritildi❗");
        }

    }

    private void viewProductsList() {
        boolean isExit = false;

        while (!isExit) {
            if (categoriesService.getAll().size() > 0) {
                int count = 0;
                for (Categories categories : categoriesService.getAll()) {
                    count++;
                    System.out.println(count + " - " + categories.getName() + " - " + productService.findByCategoryId(categories.getId()).size());
                }
                System.out.println("0. 🔙Chiqish");

                System.out.print("⇒ ");

                int key = scannerInt.nextInt();

                if (key == 0) {
                    isExit = true;
                } else if (key > 0 && categoriesService.getAll().size() >= key) {
                    if (productService.findByCategoryId(categoriesService.getAll().get(key - 1).getId()).size() > 0) {
                        for (Product product : productService.findByCategoryId(categoriesService.getAll().get(key - 1).getId())) {
                            System.out.println(product.getName() + " - " + product.getModel() + " - " + product.getPrice() + " so'm");
                        }
                    } else {
                        System.out.println("Mahsulotlar topilmadi❗");
                    }
                } else {
                    System.out.println("Noto'g'ri buyrug' kiritdingiz❗");
                }

            } else {
                isExit = true;
                System.out.println("Catogorylar topilmadi❗");
            }
        }
    }

    private void addProducts() {
        if (categoriesService.getAll().size() > 0) {
            int count = 0;

            System.out.println("⇓ Iltimos, mahsulot kiritilishi kerak bo`lgan categoriyani tanlang ⇓");
            for (Categories categories : categoriesService.getAll()) {
                System.out.println(count + 1 + ". " + categories.getName());
                count++;
            }
            int command = scannerInt.nextInt();


            if (command > 0 && command <= categoriesService.getAll().size()) {
                Product product = new Product();

                System.out.print("Mahsulot nomini kiriting ⇒ ");
                String productName = scannerStr.nextLine();
                product.setName(productName);

                System.out.println("Mahsulot modelini kiriting ⇒ ");
                String productModel = scannerStr.nextLine();
                product.setModel(productModel);

                System.out.println("Mahsulot narxini kiriting ⇒ ");
                double productPrice = scannerInt.nextDouble();
                product.setPrice(productPrice);

                System.out.println("Mahsulotga izoh kiriting ⇒ ");
                String productDescription = scannerStr.nextLine();
                product.setDescription(productDescription);

                product.setStatus(Status.START);
                product.setCategories_id(categoriesService.getAll().get(command - 1).getId());

                product.setId(UUID.randomUUID());
                productService.add(product);
                System.out.println("Mahsulot muvaffaqqiyatli qo'shildi🎉");
            } else {
                System.out.println("Noto`g`ri buyruq kiritdingiz❗");
            }
        } else {
            System.out.println("Catagory topilmadi❗");
        }
    }
}
package ui;

import categories.Categories;
import categories.CategoriesService;
import product.Product;
import product.ProductService;
import product.Status;
import user.User;
import user.UserRepository;
import user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserUI {
    private final UserService userService = UserService.getInstance();
    private final CategoriesService categoriesService = CategoriesService.getInstance();
    private final ProductService productService = ProductService.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();
    Scanner scannerInt = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);

    public void start(User user) {
        boolean isExited = false;
        while (!isExited) {
            System.out.print("""
                    1 ⇨ 🛒Mahsulot Xarid Qilish
                    2 ⇨ 🧺Savatim
                    3 ⇨ 🧾Xarid Qilgan Mahsulotlarim Tarixi
                    4 ⇨ 💰Mablag'
                    5 ⇨ ⚙️Sozlamalar
                                        
                    0 ⇨ 🔙Chiqish
                                    
                    ⇒\s""");
            int command = scannerInt.nextInt();

            switch (command) {
                case 1 -> mahsulotXaridQilish(user.getId());
                case 2 -> savatim(user.getId());
                case 3 -> xaridQilganMahsulotlarimTarixi(user);
                case 4 -> mablag(user);
                case 5 -> sozlamalar(user);
                case 0 -> isExited = true;
                default -> System.out.println("Notog'ri burug' kiritdingiz❗");
            }
        }
    }

    private void mahsulotXaridQilish(UUID uuid) {
        User user = userService.findById(uuid).get();
        if (categoriesService.getAll().size() > 0) {
            System.out.println("⇓ Mahsulotlar categoriyasini tanlang ⇓ ");
            int count = 0;
            for (Categories categories : categoriesService.getAll()) {
                count++;
                System.out.println(count + ". " + categories.getName());
            }
            System.out.println("\n0. 🔙Chiqish\n");
            System.out.print("⇒ ");

            int command = scannerInt.nextInt();
            if (command > 0 && command <= categoriesService.getAll().size()) {
                UUID id = categoriesService.getAll().get(command - 1).getId();
                List<Product> byCategoryId = productService.findByCategoryId(id);
                count = 0;
                for (Product product : byCategoryId) {
                    count++;
                    System.out.println(count + ". " + product.getName());
                }

                System.out.print("⇒ ");
                int command1 = scannerInt.nextInt();

                if (command1 > 0 && command1 <= productService.getAll().size()) {
                    Product product = productService.findByCategoryId(id).get(command1 - 1);

                    System.out.print(""" 
                            1 ⇨ 🧺Savatga qo'shish
                            2 ⇨ 💵Sotib olish

                            0 ⇨ 🔙Chiqish
                            ⇒\s""");
                    int command12 = scannerInt.nextInt();
                    switch (command12) {
                        case 1 -> addBacket(product, user);
                        case 2 -> purchase(product, user);
                        default -> System.out.println("Noto'g'ri burug' kiritdingiz❗️");
                    }
                } else {
                    System.out.println("Noto'g'ri buyrug' kiritdingiz❗️");
                }
            }
        } else {
            System.out.println("Birorta ham mahsulot yo'q❗️");
        }
    }

    private void purchase(Product product, User user) {
        if (user.getBalance() >= product.getPrice()) {
            List<Product> boughtProductsHistory = user.getBoughtProductsHistory();
            boughtProductsHistory.add(product);
            product.setStatus(Status.DELIVERED);
            user.setBoughtProductsHistory(boughtProductsHistory);
            user.setBalance(user.getBalance() - product.getPrice());

            userRepository.update(user);
            System.out.println("Xaridingiz uchun rahmat😊");
        } else {
            System.out.println("Hisobgingizdagi mablag yetarli emas❗️");
        }
    }

    private void addBacket(Product product, User user) {
        List<Product> basket = user.getBasket();
        basket.add(product);
        user.setBasket(basket);

        userRepository.update(user);
    }

    private void savatim(UUID uuid) {
        boolean isExited = false;
        while (!isExited) {
            User user = userService.findById(uuid).get();
            List<Product> basket = user.getBasket();
            int count = 1;
            double allPrice = 0;

            if (basket.size() > 0) {
                for (Product product : basket) {
                    System.out.println(count + ". " + product.getName() + " | " + product.getModel() + " | " + product.getPrice());
                    allPrice += product.getPrice();
                    count++;
                }

                System.out.println("\n\uD83E\uDDFEJami summa: " + allPrice);
                System.out.println("\n#. 💵Sotib olish");
                System.out.println("*. ❌O'chirish");
                System.out.println("%. 🧹Savatni tozalash");
                System.out.println("0. 🔙Chiqish" + "\n");

                System.out.print("⇒ ");
                String key = scannerStr.nextLine();
                switch (key) {
                    case "#" -> {
                        if (user.getBalance() >= allPrice) {
                            for (Product product : basket) {
                                product.setStatus(Status.DELIVERED);
                            }
                            List<Product> boughtProductsHistory = user.getBoughtProductsHistory();
                            boughtProductsHistory.addAll(basket);
                            user.setBoughtProductsHistory(boughtProductsHistory);
                            List<Product> products = new ArrayList<>();
                            user.setBasket(products);
                            user.setBalance(user.getBalance() - allPrice);

                            userRepository.update(user);
                            System.out.println("Xaridingiz uchun rahmat😊" + "\n");
                            if (products.size() == 0) {
                                isExited = true;
                            }
                        } else {
                            System.out.println("Balansingizda mablag' yetarli emas❗");
                        }
                    }
                    case "*" -> {
                        int num = 1;
                        for (Product product : basket) {
                            System.out.println(num + ". " + product.getName() + " | " + product.getModel() + " | " + product.getPrice());
                            num++;
                        }
                        System.out.println("O'chirmoqchi bo'lgan productni tanlang ⇨ ");
                        int deleteProduct = scannerInt.nextInt() - 1;
                        Product product = basket.get(deleteProduct);
                        List<Product> basket1 = user.getBasket();
                        basket1.remove(product);
                        user.setBasket(basket1);

                        userRepository.update(user);
                        System.out.println("Product o'chirildi✅" + "\n");
                        if (user.getBasket().size() == 0) {
                            isExited = true;
                        }
                    }
                    case "%" -> {
                        user.setBasket(new ArrayList<>());

                        userRepository.update(user);
                        System.out.println("🧹 Savat tozalandi" + "\n");
                        isExited = true;
                    }
                    case "0" -> isExited = true;
                    default -> System.out.println("Notog'ri buyrug' kiritdingiz❗️");
                }
            } else {
                System.out.println("Sizda birorta ham product yo'q❗" + "\n");
                break;
            }
        }
    }

    private void xaridQilganMahsulotlarimTarixi(User user) {
        if (user.getBoughtProductsHistory().size() > 0) {
            for (Product product : user.getBoughtProductsHistory()) {
                System.out.println("Mahsulot nomi ⇨ " + product.getName());
                System.out.println("Mahsulot tarifi ⇨ " + product.getDescription());
                System.out.println("Mahsulot modeli ⇨ " + product.getModel());
                if (product.getStatus().equals(Status.START)) {
                    System.out.println("Mahsulot statusi ⇨ Mahsulot yetkazib berilyapti");
                } else if (product.getStatus().equals(Status.DELIVERED)) {
                    System.out.println("Mahsulot statusi ⇨ Mahsulot yetkazib berildi\uD83D\uDE97");
                }
                System.out.println("Mahsulot narxi ⇨ " + product.getPrice() + "\n");
            }
        } else {
            System.out.println("Siz biror martta ham mahsulot harid qilmagansiz❗" + "\n");
        }
    }

    private void mablag(User user) {
        boolean isExited = false;
        while (!isExited) {
            System.out.println("Sizning idingiz ⇨ " + user.getId());
            System.out.println("Sizning ismingiz ⇨ " + user.getName());
            System.out.println("Sizning familiyangiz ⇨ " + user.getLastname());
            System.out.println("Sizning yoshingiz ⇨ " + user.getAge());

            String password = user.getPassword();
            int length = password.length();
            System.out.println("Sizning parolingiz ⇨ " + "*".repeat(length));
            System.out.println("Sizning balansingiz ⇨ " + user.getBalance() + "\n");
            System.out.println("1. ➕Balans qo'shish");
            System.out.println("0. 🔙Chiqish");

            int key = scannerInt.nextInt();
            switch (key) {
                case 1 -> addBalance(user);
                case 0 -> isExited = true;
                default -> System.out.println("Notog'ri buyrug' kiritdingiz❗" + "\n");
            }
        }
    }

    private void addBalance(User user) {
        System.out.print("Qo'shmoqchi bo'lgan mablag'ni kiriting ⇨ ");
        double balance = scannerInt.nextDouble();
        double userBalance = user.getBalance();
        user.setBalance(userBalance + balance);

        userRepository.update(user);
    }

    private void sozlamalar(User user) {
        boolean isExited = false;
        while (!isExited) {
            System.out.print("""
                    1. 🛅Parolni o'zgartirish
                    2. 📞Telefon raqamni o'zgartirish
                    3. 📧Email ni o'zgartirish
                    0. 🔙Chiqish
                    >> \s""");
            int command = scannerInt.nextInt();
            switch (command) {
                case 1 -> changePassword(user);
                case 2 -> changePhoneNumber(user);
                case 3 -> changeEmail(user);
                case 0 -> isExited = true;
                default -> System.out.println("Noto'g'ri buyruq kiritdingiz❗" + "\n");
            }
        }
    }

    private void changeEmail(User user) {
        System.out.print("Yangi email kiriting ⇒ ");
        String newEmail = scannerStr.nextLine();
        if (!userService.isExist(newEmail)) {
            if (user.getEmail().equals(newEmail)) {
                System.out.println("Bu email allaqachon mavjud❗");
            } else {
                user.setEmail(newEmail);
                userRepository.update(user);
                System.out.println("Email muvaffaqiyatli o'zagartirildi🎉" + "\n");
            }
        } else {
            System.out.println("Bu email bazada mavjud❗");
        }
    }

    private void changePhoneNumber(User user) {
        System.out.print("Yangi raqamni kiriting ⇒ ");
        String newPhoneNumber = scannerStr.nextLine();
        if (user.getPhoneNumber().equals(newPhoneNumber)) {
            System.out.println("Bu telefon raqam allaqachon foydalanilgan❗");
        } else {
            user.setPhoneNumber(newPhoneNumber);
            userRepository.update(user);
            System.out.println("Raqam muvaffaqiyatli o'zgartirildi🎉" + "\n");
        }
    }

    private void changePassword(User user) {
        System.out.print("Yangi parolni kiriting ⇒ ");
        String newPassword = scannerStr.nextLine();
        System.out.print("Parolni qayta kiriting ⇒ ");
        String newPassword2 = scannerStr.nextLine();
        if (newPassword.equals(newPassword2)) {
            user.setPassword(newPassword);
            System.out.println("Parol muvaffaqiyatli o'zgartirildi🎉");
            userRepository.update(user);
            System.out.println();
        } else {
            System.out.println("Parol mos kelmadi❗");
        }
    }
}


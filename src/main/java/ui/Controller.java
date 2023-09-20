package ui;

import user.User;
import user.UserService;
import user.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Controller implements UI {
    private final UserService userService = UserService.getInstance();
    private final Scanner scannerInt = new Scanner(System.in);
    private final Scanner scannerStr = new Scanner(System.in);
    @Override
    public void start() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("""
                    1. Ro'yhatdan O'tish
                    2. Kirish
                    0. 🔙Chiqish
                    >>\s""");

            int key = scannerInt.nextInt();
            switch (key) {
                case 1 -> createAccount();
                case 2 -> login();
                case 0 -> isExit = true;
                default -> System.out.println("Noto'g'ri buyruq kiritildi❗️");
            }
        }
    }
    private void createAccount()
    {
        System.out.print("Ismingizni kiriting ⇒ ");
        String name = scannerStr.nextLine();

        System.out.print("Familiyangizni kiriting ⇒ ");
        String surname = scannerStr.nextLine();

        System.out.print("Yoshingizni kiriting ⇒ ");
        int age = scannerInt.nextInt();

        System.out.print("Emailingizni kiriting ⇒ ");
        String email = scannerStr.nextLine();

        System.out.print("Parolingizni kiriting ⇒ ");
        String password = scannerStr.nextLine();

        System.out.print("Parolni-Qayta kiriting ⇒ ");
        String repeatPassword = scannerStr.nextLine();

        System.out.print("Telefon raqamingizni kiriting ⇒ ");
        String number = scannerStr.nextLine();

        if (password.equals(repeatPassword)) {
            if (userService.isExist(email)) {
                System.out.println("\uD83D\uDCE7Bu emaildan foydalanilingan❗️");
            } else {
                User user = new User(UUID.randomUUID(), name, surname, password, email, number, age, new ArrayList<>(), 0, UserType.USER, new ArrayList<>());
                userService.add(user);
                new UserUI().start(user);
            }
        } else {
            System.out.println("Parol mos kelmadi❗");
        }
    }
    private void login() {
        System.out.print("Emailingizni kiriting ⇨ ");
        String email = scannerStr.nextLine();

        System.out.print("Passwordni kiriting ⇨ ");
        String password = scannerStr.nextLine();

        if (userService.isExist(email)) {
            List<User> all = userService.getAll();

            for (User user : all) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    if (user.getPassword().equals(password)) {
                        UserType userType = user.getUserType();

                        if (userType == UserType.ADMIN) {
                            AdminUI adminUI = new AdminUI();
                            adminUI.start(user);
                        } else {
                            UserUI userUI = new UserUI();
                            userUI.start(user);
                        }
                    } else {
                        System.out.println("Email yoki password xato❗");
                    }
                    break;
                }
            }
        } else {
            System.out.println("Email topilmadi. Qaytadan urinib ko'ring ↻");
        }
    }


}

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class JegyKiadas {

    private static final String KILEPES_PARANCS = "VEGE";
    private static final int JEGY_HOSSZ = 3;
    private static final int JEGY_KOLTSEG = 390;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String napiKod = generalNapiKod();
        JegyEllenorzo ellenorzo = new JegyEllenorzo(napiKod);

        List<String> ervenyesJegyek = new ArrayList<>();
        List<String> csalokJegyei = new ArrayList<>();

        int osszesUtas = 0;
        int csalok = 0;
        boolean fut = true;

        System.out.println("Köszöntjük utasainkat!");
        System.out.println("Mai ellenőrzőkód: " + napiKod);

        while (fut) {
            try {
                System.out.println("\n----- Következő utas -----");
                System.out.print("Utas jegye (VEGE - kilépés): ");
                String input = sc.nextLine().trim();

                if (input.equalsIgnoreCase(KILEPES_PARANCS)) {
                    fut = false;
                } else if (!jegyFormatumHelyes(input)) {
                    throw new RosszInputException(
                            "Hibás formátum! A jegy 3 számjegyű, 1 és 9 között."
                    );
                } else {
                    osszesUtas++;
                    UtasObjektum utas = new UtasObjektum();
                    utas.setJegy(input);

                    if (ellenorzo.ellenoriz(input)) {
                        System.out.println("A jegy érvényes, kellemes utazást.");
                        ervenyesJegyek.add(input);
                    } else {
                        System.out.print("Második próbálkozás: ");
                        String masodik = sc.nextLine().trim();

                        if (ellenorzo.ellenoriz(masodik)) {
                            System.out.println("Most már érvényes.");
                            ervenyesJegyek.add(masodik);
                        } else {
                            System.out.println("Érvénytelen jegy.");
                            System.out.println("Egyenleg: " + utas.getPenz() + " Ft");

                            System.out.print("Vesz jegyet 390 Ft-ért? (i/n): ");
                            String valasz = beolvasValasz(sc);

                            if (valasz.equals("i") && utas.getPenz() >= JEGY_KOLTSEG) {
                                utas.fizetJegyert(JEGY_KOLTSEG);
                                System.out.println("Jegy megvéve.");
                                ervenyesJegyek.add(napiKod);
                            } else {
                                System.out.println("Utas leszállítva.");
                                csalok++;
                                csalokJegyei.add(input);
                            }
                        }
                    }
                }
            } catch (RosszInputException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\n--- ÖSSZESÍTÉS ---");
        if (osszesUtas > 0) {
            double arany = (double) csalok / osszesUtas * 100;
            System.out.printf("Csalók aránya: %.2f%%\n", arany);
        } else {
            System.out.println("Nem volt utas.");
        }

        fajlMentes(napiKod, ervenyesJegyek, csalokJegyei);
        System.out.println("Adatok elmentve.");
    }


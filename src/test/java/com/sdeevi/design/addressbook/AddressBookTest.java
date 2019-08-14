package com.sdeevi.design.addressbook;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AddressBookTest {

    private static AddressBook addressBook = new AddressBook();

    static {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/test/resources/us-500 copy2.csv")))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] c = StringUtils.split(line, ",");
                addressBook.addContact(c[0], c[1], c[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Main extends JFrame {

        public Main() {
            setSize(1000, 1000);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel searchLabel = new JLabel("Search: ");
            JTextField searchField = new JTextField();
            searchField.setPreferredSize(new Dimension(100, 20));
            add(searchLabel);
            add(searchField);

            JTextArea result = new JTextArea();
            result.setPreferredSize(new Dimension(1000, 500));
            add(result);

            searchField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    JTextField textField = (JTextField) e.getSource();
                    String text = textField.getText();

                    if (StringUtils.isNotEmpty(text)) {
                        result.setText(StringUtils.join(addressBook.search(text), "\n"));
                    }
                }

                public void keyTyped(KeyEvent e) {
                }

                public void keyPressed(KeyEvent e) {
                }
            });
        }

        public static void main(String[] args) {
            new Main().setVisible(true);
        }

        @Test
        public void search_thenReturnsSearch() {
            List<AddressBook.Contact> contactList = addressBook.search("LlI");
            contactList.sort(Comparator.comparing(AddressBook.Contact::toString));
            assertThat(contactList.toString(), is("[Alline Jeanty (574-656-2800), " +
                    "Dottie Hellickson (206-540-6076), " +
                    "Elli Mclaird (315-818-2638), " +
                    "Felicidad Poullion (856-305-9731), " +
                    "Gearldine Gellinger (972-934-6914), " +
                    "Jennifer Fallick (847-979-9545), " +
                    "Kallie Blackwood (415-315-2761), " +
                    "Leota Dilliard (408-752-3500), " +
                    "Lilli Scriven (325-631-1560), " +
                    "Mollie Mcdoniel (419-975-3182), " +
                    "Rikki Nayar (305-968-9487), " +
                    "Skye Fillingim (612-508-2655), " +
                    "Valentin Klimek (312-303-5453), " +
                    "Valentine Gillian (210-812-9597), " +
                    "Vallie Mondella (208-862-5339)]"));

            contactList = addressBook.search("MOllIE");
            contactList.sort(Comparator.comparing(AddressBook.Contact::toString));
            assertThat(contactList.toString(), is("[Mollie Mcdoniel (419-975-3182)]"));

            contactList = addressBook.search("311");
            contactList.sort(Comparator.comparing(AddressBook.Contact::toString));
            assertThat(contactList.toString(), is("[Fabiola Hauenstein (717-809-3119), " +
                    "Lilli Scriven (325-631-1560), " +
                    "Samira Heintzman (206-311-4137)]"));
        }
    }
}
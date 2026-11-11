package dz.univ.accounts.legacy;

import java.util.Arrays;
import java.util.List;

// NOT modify this class. It simulates the old university system (an external library).
public class LegacyStudentSystem {
    // Each record is a text line: ID;LASTNAME;FIRSTNAME;EMAIL;LEVEL
    public List<String> fetchRecords() {
        return Arrays.asList(
            "19990001;BENSALEM;Yacine;y.bensalem@old.univ.dz;L3",
            "19990002;OUALI;Nadia;n.ouali@old.univ.dz;M1",
            "19990003;BENBORDI;Rida;y.Rida@old.univ.dz;L1",
            "19990004;ALI;Ali;Ali@old.univ.dz;M2",
            "19990005;Alla;Mostapha;Mostapha@old.univ.dz;L1",
            "19990006;OUALI;Laila;laila@old.univ.dz;M1",
            "19990007;BENALI;Aicha;y.aicha@old.univ.dz;L2",
            "19990008;triqui;Hoda;hoda@old.univ.dz;M2",
            "19990009;SALEM;Bahi;y.salem@old.univ.dz;L3",
            "19990010;Aoun;hama;h.aoun@old.univ.dz;M1"
        );
    }
}

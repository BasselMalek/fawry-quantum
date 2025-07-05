package com.quantum.domain.interfaces;

import java.time.LocalDate;

public interface Perishable {

    LocalDate getExpiryDate();
    Boolean isExpired();

}

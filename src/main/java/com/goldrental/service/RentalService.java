package com.goldrental.service;

import com.goldrental.dto.RentalRequest;
import com.goldrental.dto.RentalDto;
import com.goldrental.dto.RentalResponse;

import java.util.List;

public interface RentalService {

    Boolean rentJewellery(RentalRequest request);

    RentalDto returnJewellery(Long rentalId);

    List<RentalResponse> getUserRentals(Long user_id);

    Boolean confirmRental(Long id);

    Boolean cancelRental(Long id, Long userId);
}

package com.telran.ticketsapp.data.tickets.dto;

import java.util.List;

public class HallStructureDto {
    List<LockedSeats> lockedSeats;
    List<PriceRangeDto> priceRanges;

    public HallStructureDto(List<LockedSeats> lockedSeats, List<PriceRangeDto> priceRanges) {
        this.lockedSeats = lockedSeats;
        this.priceRanges = priceRanges;
    }

    public HallStructureDto() {
    }

    public List<LockedSeats> getLockedSeats() {
        return lockedSeats;
    }

    public void setLockedSeats(List<LockedSeats> lockedSeats) {
        this.lockedSeats = lockedSeats;
    }

    public List<PriceRangeDto> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(List<PriceRangeDto> priceRanges) {
        this.priceRanges = priceRanges;
    }


}

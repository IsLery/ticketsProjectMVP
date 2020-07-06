package com.telran.ticketsapp.presentation.tickets.model;

import com.telran.ticketsapp.presentation.tickets.view.SelectedTicketsAdapter;

import java.util.Objects;

public class SeatModel implements Comparable<SeatModel>{
    String id;
    int row;
    int seatN;
    String seat;
    Double price;

    public SeatModel(String id) {
        this.id = id;
        String[] info = id.split("-");
        row = Integer.parseInt(info[0]);
        seat = info[1];
        price = Double.parseDouble(info[2]);
        seatN = Integer.parseInt(info[3]);
    }

    public SeatModel(int row, int seatN, String seat, Double price) {
        this.row = row;
        this.seatN = seatN;
        this.seat = seat;
        this.price = price;
        id = row+"-"+seat+"-"+price+"-"+seatN;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatModel seatModel = (SeatModel) o;
        return Objects.equals(id, seatModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public int compareTo(SeatModel o2) {
        int rw = Integer.compare(this.row, o2.row);
        if (rw != 0){
            return rw;
        }else {
            return Integer.compare(this.seatN, o2.seatN);
        }
    }

    public String getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getSeatN() {
        return seatN;
    }

    public String getSeat() {
        return seat;
    }

    public Double getPrice() {
        return price;
    }
}

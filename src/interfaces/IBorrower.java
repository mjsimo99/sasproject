package interfaces;

import dto.Borrower;

import java.util.List;

public interface IBorrower {

    void add(Borrower borrower);

    boolean update(Borrower borrower);

    boolean delete(String cin);

    List<Borrower> show();




}

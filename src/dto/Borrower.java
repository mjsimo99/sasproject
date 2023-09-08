package dto;

import java.util.List;

public class Borrower {


    private int id;
    private String name;
    private int tel;
    private String cin;
    private String dateOfBirth;

    private List<Book> book ;

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

    public Borrower(int id, String name, int tel, String cin, String dateOfBirth) {
            setId(id);
            setName(name);
            setTel(tel);
            setCin(cin);
            setDateOfBirth(dateOfBirth);
    }

    public String getCin() {
        return cin;
    }

    public int getId() {
        return id;
    }

    public int getTel() {
        return tel;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }



    @Override
    public String toString() {
        return "Borrower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel=" + tel +
                ", cin=" + cin +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}



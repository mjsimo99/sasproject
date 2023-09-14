package dto;

import java.util.List;

public class Borrower {


    private int id;
    private String name;
    private int tel;
    private String cin;

    private List<Book> book ;

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

    public Borrower(int id, String name, int tel, String cin) {
            setId(id);
            setName(name);
            setTel(tel);
            setCin(cin);
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


    public String getName() {
        return name;
    }

    public void setCin(String cin) {
        this.cin = cin;
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
                '}';
    }
}



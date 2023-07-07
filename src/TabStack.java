
/**
 * @author Sławek
 * Klasa implementująca stos za pomocą tablicy
 */
public class TabStack {
    private String[] stack = new String[20];
    private int size = 0;
    /**
     * Metoda zdejmująca wartość ze stosu
     * @return wartość z góry stosu
     */
    public String pop() throws IndexOutOfBoundsException {
        if(size == 0) throw new IndexOutOfBoundsException("Proba zdjecia z pustego stosu");
        size--;
        return stack[size];
    }
    /**
     * metoda dokładająca na stos
     * @param a - wartość dokładana do stosu
     */
    public void push(String a) throws IndexOutOfBoundsException {
        if(size == 20) throw new IndexOutOfBoundsException("Proba wstawienia na pelny stos");
        stack[size] = a;
        size++;
    }
    /**
     * Metoda zwraca tekstową reprezentację stosu
     */
    public String toString(){
        String tmp = "";
        for(int i = 0; i < size; i++)
            tmp += stack[i] + " ";
        return tmp;
    }
    /**
     * Metoda zwraca rozmiar stosu
     * @return size rozmiar stosu
     */
    public int getSize(){
        return size;
    }
    /**
     * Ustawia wartość stosu
     * @param i
     */
    public void setSize(int i){
        size = i;
    }
    /**
     * Metoda zwraca wartość z określonej pozycji stosu
     * @param i pozycja parametru do zobaczenia
     * @return wartość stosu
     */
    public String showValue(int i) throws IndexOutOfBoundsException {
        if(i < size)
            return stack[i];
        else throw new IndexOutOfBoundsException("Proba zdjecia wartosci z poza stosu");
    }
}
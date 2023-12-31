public class ONP {
    private TabStack stack = new TabStack();

    /**
     * Metoda sprawdza czy równanie kończy się znakiem '='
     *
     * @param rownanie równanie do sprawdzenia
     * @return true jeśli równanie jest poprawne, false jeśli niepoprawne
     */
    boolean czyPoprawneRownanie(String rownanie) {
        if (rownanie.endsWith("=")) {
            return true;
        }
        else {
            throw new IllegalArgumentException("Rownanie nie konczy sie na '='");
        }
    }

    /**
     * Metoda oblicza wartość wyrażenia zapisanego w postaci ONP
     *
     * @param rownanie równanie zapisane w postaci ONP
     * @return wartość obliczonego wyrażenia
     */
    public String obliczOnp(String rownanie) throws IllegalArgumentException {
        if (czyPoprawneRownanie(rownanie)) {
            stack.setSize(0);
            String wynik = "";
            Double a = 0.0;
            Double b = 0.0;
            for (int i = 0; i < rownanie.length(); i++) {
                if (rownanie.charAt(i) >= '0' && rownanie.charAt(i) <= '9') {
                    wynik += rownanie.charAt(i);
                    if (!(rownanie.charAt(i + 1) >= '0' && rownanie.charAt(i + 1) <= '9')) {
                        stack.push(wynik);
                        wynik = "";
                    }

                } else if (rownanie.charAt(i) == '=') {
                    return stack.pop();
                } else if (rownanie.charAt(i) == '!') {
                    String temp = stack.pop();
                    stack.push(silnia(temp) + "");
                } else if (rownanie.charAt(i) == 'p') {
                    a = Double.parseDouble(stack.pop());
                    if(a < 0) throw new IllegalArgumentException("Proba pierwiastkowania liczby mniejszej od 0, podano: " + a);
                    stack.push(Math.sqrt(a) + "");

                } else if (rownanie.charAt(i) != ' ') {
                    b = Double.parseDouble(stack.pop());
                    a = Double.parseDouble(stack.pop());
                    switch (rownanie.charAt(i)) {
                        case ('+'): {
                            stack.push((a + b) + "");
                            break;
                        }
                        case ('-'): {
                            stack.push((a - b) + "");
                            break;
                        }
                        case ('x'):
                            ;
                        case ('*'): {
                            stack.push((a * b) + "");
                            break;
                        }
                        case ('/'): {
                            if(b == 0) throw new IllegalArgumentException("Proba dzielenia przez 0");
                            stack.push((a / b) + "");
                            break;
                        }
                        case ('%'): {
                            stack.push((a % b) + "");
                            break;
                        }
                        case ('^'): {
                            stack.push(Math.pow(a, b) + "");
                            break;
                        }
                    }
                }
            }
            return "0.0";
        } else
            return "Brak '='";
    }

    /**
     * Metoda zamienia równanie na postać ONP
     *
     * @param rownanie równanie do zamiany na postać ONP
     * @return równanie w postaci ONP
     */
    public String przeksztalcNaOnp(String rownanie) {
        if (czyPoprawneRownanie(rownanie)) {
            String wynik = "";
            for (int i = 0; i < rownanie.length(); i++) {
                if (rownanie.charAt(i) >= '0' && rownanie.charAt(i) <= '9') {
                    wynik += rownanie.charAt(i);
                    if (!(rownanie.charAt(i + 1) >= '0' && rownanie.charAt(i + 1) <= '9' ))
                        wynik += " ";
                } else
                    switch (rownanie.charAt(i)) {
                        case ('+'):
                            ;
                        case ('-'): {
                            while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(")) {
                                wynik = wynik + stack.pop() + " ";
                            }
                            String str = "" + rownanie.charAt(i);
                            stack.push(str);
                            break;
                        }
                        case ('x'):
                            ;
                        case ('*'):
                            ;
                        case ('%'):
                            ;
                        case ('/'): {
                            while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(")
                                    && !stack.showValue(stack.getSize() - 1).equals("+")
                                    && !stack.showValue(stack.getSize() - 1).equals("-")) {
                                wynik = wynik + stack.pop() + " ";
                            }
                            String str = "" + rownanie.charAt(i);
                            stack.push(str);
                            break;
                        }
                        case ('!'):
                            ;
                        case ('p'):
                            ;
                        case ('^'): {
                            String str = "" + rownanie.charAt(i);
                            stack.push(str);
                            break;
                        }

                        case ('('): {
                            String str = "" + rownanie.charAt(i);
                            stack.push(str);
                            break;
                        }
                        case (')'): {
                            while (stack.getSize() > 0 && !stack.showValue(stack.getSize() - 1).equals("(")) {
                                wynik = wynik + stack.pop() + " ";
                            }
                            // zdjęcie ze stosu znaku (
                            if (stack.getSize() == 0) throw new IllegalArgumentException("Bledne nawiasy");
                            stack.pop();
                            break;
                        }
                        case ('='): {
                            while (stack.getSize() > 0) {
                                wynik = wynik + stack.pop() + " ";
                            }
                            wynik += "=";
                        }
                    }
            }
            return wynik;
        } else
            return "null";
    }

    public static void main(String[] args) {
        // 7 1 + 4 2 - 2 ^ * =
        String tmp = "";
        ONP onp = new ONP();
        String rownanieOnp;
        String wynik;
        if (args.length == 0) {
            tmp = "(2+3)*(6-2)%3=";
            try {
                System.out.print(tmp);
                rownanieOnp = onp.przeksztalcNaOnp(tmp);
                wynik = onp.obliczOnp(rownanieOnp);
                System.out.print(rownanieOnp);
                System.out.println(" " + wynik);
            }
            catch(IllegalArgumentException e) {
                System.out.print("Bledne rownanie: " + e.getMessage() + "\n");
            }
            catch(IndexOutOfBoundsException e2) {
                System.out.print("Wystapil blad z wykorzystanym stosem: " + e2.getMessage());
            }
        } else {
            for (int i = 0; i < args.length; i++) {
                try {
                    System.out.print(args[i] + " ");
                    rownanieOnp = onp.przeksztalcNaOnp(args[i]);
                    wynik = onp.obliczOnp(rownanieOnp);
                    System.out.print(rownanieOnp);
                    System.out.println(" " + wynik);
                }
                catch(IllegalArgumentException e) {
                    System.out.print("Bledne rownanie: " + e.getMessage() + "\n");
                }
                catch(IndexOutOfBoundsException e2) {
                    System.out.print("Wystapil blad z wykorzystanym stosem: " + e2.getMessage());
                }
            }
        }


    }

    static int silnia(String tekst) {
        int wynik = 1;
        try {
            int value = Integer.parseInt(tekst);
            if(value < 0) throw new IllegalArgumentException();
            for(int i = 1; i < value; i++) {
                wynik*=i;
            }

        } catch (IllegalArgumentException e ) {
            throw new IllegalArgumentException("Silnia musi byc liczba naturalna, podano: " + tekst);
        }

        return wynik;
    }


}
package algdat.hjelpeklasser;

public class BinTre<T> {
    private static final class Node<T>{
        private T verdi;
        private Node<T> venstre;
        private Node<T> hoyre;

        private Node(T verdi, Node<T> venstre, Node<T> hoyre){
            this.verdi = verdi;
            this.venstre = venstre;
            this.hoyre = hoyre;
        }

        private Node(T verdi){
            this(verdi, null, null);
        }
    }

    private Node<T> rot;
    private int antall;

    public BinTre(){
        rot = null;
        antall = 0;
    }

    public BinTre(int[] posisjon, T[] verdi){
        if (posisjon.length > verdi.length) throw new IllegalArgumentException("Verditabellen har for få elementer!");

        for (int i = 0; i < posisjon.length; i++) leggInn(posisjon[i], verdi[i]);
    }

    private Node<T> finnNode(int posisjon){
        if (posisjon < 1) return null;

        Node<T> p = rot;
        int filter = Integer.highestOneBit(posisjon >>= 1);

        for (; p != null && filter > 0; filter >>=1) p = (posisjon & filter) == 0 ? p.venstre : p.hoyre;

        return p;
    }

    public boolean finnes(int posisjon){
        return finnNode(posisjon) != null;
    }

    public T hent(int posisjon){
        Node<T> p = finnNode(posisjon);
        if (p == null) throw new IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        return p.verdi;
    }

    public T oppdater(int posisjon, T nyverdi){
        Node<T> p = finnNode(posisjon);
        if (p == null) throw new IllegalArgumentException("Posisjon (" + posisjon + ") finnes ikke i treet!");

        T gammelverdi = p.verdi;
        p.verdi = nyverdi;

        return gammelverdi;
    }

    public final void leggInn(int posisjon, T verdi){
        if (posisjon < 1) throw new IllegalArgumentException("Posisjon (" + posisjon + ") < 1!");

        Node<T> p = rot;
        Node<T> q = null;

        int filter = Integer.highestOneBit(posisjon) >> 1;

        while (p != null && filter > 0){
            q = p;
            p = (posisjon & filter) == 0 ? p.venstre : p.hoyre;
            filter >>= 1;
        }

        if (filter > 0) throw new IllegalArgumentException("Posisjon (" + posisjon + ") mangler forelder!");
        else if (p != null) throw new IllegalArgumentException("Posisjon (" + posisjon + ") finnes fra før!");

        p = new Node<>(verdi);

        if (q == null) rot = p;
        else if ((posisjon & 1) == 0) q.venstre = p;
        else q.hoyre = p;

        antall++;
    }

    public int getAntall(){
        return antall;
    }

    public boolean tom(){
        return antall == 0;
    }

}
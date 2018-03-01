package pkg1;
import java.util.*;

public class TypesValuesVariables {
    public static class GenericArray<T>{
        ArrayList<T> list;
        public GenericArray(){ }

        public GenericArray(T... elems){
            list = new ArrayList();
            for(int i = 0; i<elems.length; i++)
                list.add(elems[i]);
        }

        public void add(T[] array, T elem) {
            list.add(elem);
            array = list.toArray(array);
        }

        public void add(T elem) {list.add(elem);}

        public T get(int index) {return list.get(index);}
    }

    public static class GenericArraySub<T extends Number> extends GenericArray<T>{ }

    public interface GenInterface1<T> {
        void doSmth ();
    }

    public interface GenInterface2<T> extends GenInterface1<T>{
        void doSmth1 ();
    }

    public static class GenericClass1<T> implements GenInterface2<T> {
        @Override
        public void doSmth() {}
        public void doSmth1() {}
    }

    public static void test(String... args){
        TypesValuesVariables o = new TypesValuesVariables();
        o.testTypeErasure();
        o.testGenerics();
        o.testSubtyping();
        o.testSubtypingArrays();
        o.testHeapPollution();
    }

    public void testTypeErasure() {
        Integer[] array = new Integer[10];
        GenericArray<Integer> ga1 = new GenericArray<>(1,2,3);
        ga1.add(array, 4);
        GenericArray ga2 = new GenericArray(1,2,3);
        ga2.add(array, 4);
        GenericArray<?> ga3 = new GenericArray<Integer>(1,2,3);
        GenericArray<Long> ga4 = new GenericArray<>(1L,2L,3L);
    }

    public void testGenerics() {
        class GenericArrayOps <T> {
            GenericArray<T> array;

            public GenericArrayOps(GenericArray<T> array) {this.array = array;}

            public T getElem(int index) {return array.get(index);}
        }

        int index = 2;
        GenericArray<?> ga1;
        GenericArrayOps<?> gao;
        Object elem;

        ga1 = new GenericArray<>(1,2,3);
        gao = new GenericArrayOps(ga1);
        elem = gao.getElem(index);

        ga1 = new GenericArray<>("Jane", "Anna", "Iren");
        gao = new GenericArrayOps(ga1);
        elem = gao.getElem(index);
    }

    public void testSubtyping() {
        int[] iArray = new int[2];
        int count = 0;
        Object o;
        GenInterface2<?> gi2;

        while (count < iArray.length) {
            iArray[count] = count;
            ++count;
        }

        gi2 = new GenericClass1<Integer>();
        o = gi2;
    }

    public void testSubtypingArrays() {
        AbstractCollection<? extends Number>[] ac;
        AbstractList<?>[] abl;
        ArrayList<Integer>[] arl1 = new ArrayList[1];
        ArrayList<String>[] arl2 = new ArrayList[1];

        arl1[0] = new ArrayList<>(2);
        arl1[0].add(1);
        arl1[0].add(2);
        arl1[0].add(3);

        arl2[0] = new ArrayList<>(2);
        arl2[0].add("Hanna");
        arl2[0].add("Noel");
        arl2[0].add("Joanna");

        ac = arl1;
        abl = arl1;
        abl = arl2;
    }

    public void testHeapPollution() {
        Collection al = new ArrayList(1);
        Collection<String> sl = new ArrayList<>();
        Collection<?> ul;
        Object[] oa = new Object[3];


        al.add(1);
        al.add("Jane");

        sl.add("Jane");
        sl.add("Hanna");
        sl.add("Jude");

        ul = sl;

        oa[0] = al; // List of elements of mixed type
        oa[1] = sl; // List of String
        oa[2] = ul; // List of unknown type
    }
}

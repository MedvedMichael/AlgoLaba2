package com.company;


public class MyHashMap<K, V> {
    KeyValueData<K, V>[] table; // KeyValueData - data like "key - value"
    int capacity;
    int size = 0;
    int linearTemp;
    int totalCollisionCounter = 0;
//    float loadFactor = 0.8f;

    MyHashMap(int capacity) {
        this.capacity = capacity;
        table = new KeyValueData[capacity];
        findLinearTemp();
    }

//    private void findLinearTemp() {
//        int i = 2;
//        while (capacity % i == 0)
//            i++;
//        this.linearTemp = i;
//    }


    int findNOD(int bigNumber, int smallNumber){
        int q,r=-1;
        while(r!=0) {
            q=1;
            do {
                r = bigNumber - smallNumber * q;

                q++;
            } while (r >= smallNumber);
//            System.out.println(r);
            bigNumber = smallNumber;
            smallNumber = r;
        }
        return bigNumber;

    }

    private void findLinearTemp(){
        int number=2;
        while(true){
            int test = findNOD(capacity,number);
            if(test == 1)
                break;
            number++;
        }
        linearTemp = number;

    }

    // get hash from the key
//    static int hash(int h) {
//        h ^= (h >>> 20) ^ (h >>> 12);
//        return h ^ (h >>> 7) ^ (h >>> 4);
//    }

    private long hash(K key) {
        return MurmurHash2.hash32((String) key);
    }

    //    get index to put from hash and current length
    private static int getIndexFor(long hash, int length) {
        return (int) (hash % (length));
    }

    //    push "key - value" into the table
    void put(K key, V value) {
//        System.out.println(key + " " + value);
        if (key == null) {
            putWithNullKey(value);
            return;
        }

        long hash = hash(key);
        int index = getIndexFor(hash, capacity);
//        System.out.println(key + " " + index);

        V checked = checkAllKeyValueData(key, value, hash);
        if (checked != null)
            return;

        addKeyValueData(hash, key, value, index);
    }

//    int cococo = 1;
    //    check all "key - value" data if they have the same key
    private V checkAllKeyValueData(K key, V value, long hash) {
        int index = (int)(hash%capacity);
        int startIndex = index;
        int i=1;
//        cococo = 1;

        while (table[index] != null && !table[index].mKey.equals(key)) {
            index = increaseIndex(index);
            i++;
            if (index == startIndex)
                break;
        }

        if (table[index] == null)
            return null;

        if (index == startIndex && table[index] != null)
            return null;

        return table[index].mValue;


    }

    // push "key - value" data if key == null
    private void putWithNullKey(V value) {
        V checked = checkAllKeyValueData(null, value, 0);
        if (checked == null)
            return;
        addKeyValueData(0, null, value, 0);
    }

    int increaseIndex(int index) {
        index+=linearTemp;
        if(index>=capacity)
            index-=capacity;

        return index;
    }

    //  create KeyValueData element and put it with right index
    private void addKeyValueData(long hash, K key, V value, int index) {
        int startIndex = index;
        int i=1;
//        cococo = 1;

        if (table[index] != null) {
            index = increaseIndex(index);
            i++;

            while (table[index] != null && index != startIndex) {
                index = increaseIndex(index);
                i++;

            }

            if (index == startIndex && table[index] != null) {
//                System.out.println(index + " " + table[index].mKey + " " + key);
//                printAll();
                System.out.println("No empty spaces");
                return;
            }
        }


        KeyValueData<K, V> data = new KeyValueData<>(hash, key, value);
        table[index] = data;
        size++;

    }

    // returns the value for given key
    V get(K key) {
        long hash = hash(key);
        int index = getIndexFor(hash, capacity);
        int startIndex = index;


        int counter = 0;

        while (table[index] != null && !table[index].mKey.equals(key)) {
            counter++;
            index = increaseIndex(index);

            if (index == startIndex)
                break;
        }

        if (table[index] == null)
            return null;

        if (index == startIndex && table[index] != null && !table[index].mKey.equals(key))
            return null;

//        System.out.println(counter);
        totalCollisionCounter += counter;

        return table[index].mValue;
    }

//    void remove(K key) {
//        int hash = hash(key.hashCode());
//
//        int index = getIndexFor(hash, capacity);
//
//        KeyValueData<K, V> data = table[index];
//        if (data.next != null) {
//
//        }
//    }

//    void resize(int newCapacity) {
//        KeyValueData[] table2 = new KeyValueData[newCapacity];
//        capacity = newCapacity;
//        transferData(table2);
//
//    }

//    private void transferData(KeyValueData<K, V>[] newTable) {
//        KeyValueData[] copiedLastTable = this.table.clone();
//        this.table = newTable;
//        size = 0;
//        for (int i = 0; i < copiedLastTable.length; i++) {
//            KeyValueData<K, V> data = copiedLastTable[i];
//            while (data != null) {
//                put(data.mKey, data.mValue);
//                data = data.next;
//            }
//        }
//    }

    public void printAll() {
        for (int i = 0; i < table.length; i++) {
            KeyValueData<K, V> data = table[i];
            if (data != null) {
                System.out.print("Box " + i + ": ");
                System.out.print("<" + data.mKey + " - " + data.mValue + "> ");
                System.out.println();
            }
        }
        System.out.println();
    }


    static class KeyValueData<K, V> {
        K mKey;
        V mValue;
        long mHash;


        KeyValueData(long hash, K key, V value) {
            mHash = hash;
            mKey = key;
            mValue = value;
        }
    }
}






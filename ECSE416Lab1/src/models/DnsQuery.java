package models;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class DnsQuery {
    private DnsHeader header;
    private List<Byte> qname = new LinkedList<>();
    private char qtype;
    private char qclass;

    /**
     * constructor
     * @param name
     * @param qtype
     * @param qclass
     */
    public DnsQuery(String name, QtypeEnum qtype, char qclass){
        this.header = new DnsHeader.Builder().standardQueryHeader().build();
        setQname(name);
        this.qtype = qtype.getCode();
        this.qclass = qclass;
    }

    /**
     * Constrictor
     * @param name
     * @param qtype
     */
    public DnsQuery(String name, QtypeEnum qtype){
        this.header = new DnsHeader.Builder().standardQueryHeader().build();
        setQname(name);
        this.qtype = qtype.getCode();
        this.qclass = (char) 0x0001;
    }

    /**
     * Sets the Qname from a String to the proper format for sending
     * @param name
     */
    private void setQname(String name) {
        // fill in qname with proper data
        int l = name.length() + 2, i;
        char[] buff= new char[0];
        for(String label:name.split("\\.")){
            char[] sze = {(char) label.length()},
                    charLabels = label.toCharArray();
            buff = merge(buff, merge(sze, charLabels));
        }
        buff = merge(buff, new char[]{0}); //add a zero at the end to signify end of Name transmission
        for(i=0; i<l; i++) this.qname.add((byte)buff[i]);
    }

    @Override
    public String toString(){
        List<Byte> bigBoyBytes = this.toByteArray();
        byte[] bytes = new byte[bigBoyBytes.size()];
        int i=0;
        for(Byte b: bigBoyBytes){
            bytes[i++] = b;  // unboxing
        }
        return new String(bytes);
    }

    public List<Byte> toByteArray() {
        List<Byte> bytes = new LinkedList<>();
        byte[] header = this.header.toByteArray();
        for(byte b:header){
            bytes.add(b); // Autoboxing
        }
        bytes.addAll(this.qname);
        bytes.addAll(seperateChar(this.qtype));
        bytes.addAll(seperateChar(this.qclass));
        return bytes;
    }

    /**
     * Merge two char arrays together
     * @param a
     * @param b
     * @return
     */
    private static char[] merge(char[] a, char[] b){
        int i;
        char[] total =  new char[a.length + b.length];
        for(i=0; i< a.length; i++){
            total[i] = a[i];
        }
        for(i=a.length; i<(a.length + b.length); i++){
            total[i] = b[i-a.length];
        }
        return total;
    }

    private static <T> List<T> merge(List<T> a, List<T> b){
        a.addAll(b);
        return a;
    }

    /**
     * seperates a char into an array of bytes
     * @param a
     * @return
     */
    private static List<Byte> seperateChar(char a){
        List<Byte> val = new LinkedList<>();
        val.add((byte) ((a&0xFF00)>>8));
        val.add((byte) (a&0x00FF));
        return val;
    }

}

package models;

import java.util.Random;

public class DnsHeader {
    private char ID;
    private boolean QR;
    private byte Opcode;
    private boolean AA;
    private boolean TC;
    private boolean RD;
    private boolean RA;
    private char Z;
    private char RCODE;
    private char QDCOUNT;
    private char ANCOUNT;
    private char NSCOUNT;
    private char ARCOUNT;


    public DnsHeader(char ID, boolean QR, byte opcode, boolean AA, boolean TC, boolean RD, boolean RA, char z, char RCODE, char QDCOUNT, char ANCOUNT, char NSCOUNT, char ARCOUNT) {
        this.ID = ID;
        this.QR = QR;
        this.Opcode = opcode;
        this.AA = AA;
        this.TC = TC;
        this.RD = RD;
        this.RA = RA;
        this.Z = z;
        this.RCODE = RCODE;
        this.QDCOUNT = QDCOUNT;
        this.ANCOUNT = ANCOUNT;
        this.NSCOUNT = NSCOUNT;
        this.ARCOUNT = ARCOUNT;
    }

    public static class Builder {

        private char ID;
        private boolean QR;
        private byte Opcode;
        private boolean AA;
        private boolean TC;
        private boolean RD;
        private boolean RA;
        private char Z;
        private char RCODE;
        private char QDCOUNT;
        private char ANCOUNT;
        private char NSCOUNT;
        private char ARCOUNT;

        public Builder(){
            standardHeader();
        }

        public Builder standardQueryHeader(){
            standardHeader();
            return this;
        }

        private void standardHeader(){
            this.ID = (char) new Random().nextInt();
            this.QR=false;
            this.Opcode=0;
            this.AA=false;
            this.TC=false;
            this.RD=true;
            this.RA=false;
            this.Z=0;
            this.RCODE=0;
            this.QDCOUNT=1;
            this.ANCOUNT=0;
            this.NSCOUNT=0;
            this.ARCOUNT=0;
        }

        public Builder setID(char ID) {
            this.ID = ID;
            return this;
        }

        public Builder setQR(boolean QR) {
            this.QR = QR;
            return this;
        }

        public Builder setOpcode(byte opcode) {
            Opcode = opcode;
            return this;
        }

        public Builder setAA(boolean AA) {
            this.AA = AA;
            return this;
        }

        public Builder setTC(boolean TC) {
            this.TC = TC;
            return this;
        }

        public Builder setRD(boolean RD) {
            this.RD = RD;
            return this;
        }

        public Builder setRA(boolean RA) {
            this.RA = RA;
            return this;
        }

        public Builder setZ(char z) {
            Z = z;
            return this;
        }

        public Builder setRCODE(char RCODE) {
            this.RCODE = RCODE;
            return this;
        }

        public Builder setQDCOUNT(char QDCOUNT) {
            this.QDCOUNT = QDCOUNT;
            return this;
        }

        public Builder setANCOUNT(char ANCOUNT) {
            this.ANCOUNT = ANCOUNT;
            return this;
        }

        public Builder setNSCOUNT(char NSCOUNT) {
            this.NSCOUNT = NSCOUNT;
            return this;
        }

        public Builder setARCOUNT(char ARCOUNT) {
            this.ARCOUNT = ARCOUNT;
            return this;
        }

        public DnsHeader build() {
            return new DnsHeader(this.ID,this.QR,this.Opcode, this.AA, this.TC, this.RD, this.RA, this.Z, this.RCODE, this.QDCOUNT, this.ANCOUNT, this.NSCOUNT, this.ARCOUNT);
        }
    }

    public char getID() {
        return ID;
    }

    public void setID(char ID) {
        this.ID = ID;
    }

    public boolean isQR() {
        return QR;
    }

    public void setQR(boolean QR) {
        this.QR = QR;
    }

    public byte getOpcode() {
        return Opcode;
    }

    public void setOpcode(byte opcode) {
        Opcode = opcode;
    }

    public boolean isAA() {
        return AA;
    }

    public void setAA(boolean AA) {
        this.AA = AA;
    }

    public boolean isTC() {
        return TC;
    }

    public void setTC(boolean TC) {
        this.TC = TC;
    }

    public boolean isRD() {
        return RD;
    }

    public void setRD(boolean RD) {
        this.RD = RD;
    }

    public boolean isRA() {
        return RA;
    }

    public void setRA(boolean RA) {
        this.RA = RA;
    }

    public char getZ() {
        return Z;
    }

    public void setZ(char z) {
        Z = z;
    }

    public char getRCODE() {
        return RCODE;
    }

    public void setRCODE(char RCODE) {
        this.RCODE = RCODE;
    }

    public char getQDCOUNT() {
        return QDCOUNT;
    }

    public void setQDCOUNT(char QDCOUNT) {
        this.QDCOUNT = QDCOUNT;
    }

    public char getANCOUNT() {
        return ANCOUNT;
    }

    public void setANCOUNT(char ANCOUNT) {
        this.ANCOUNT = ANCOUNT;
    }

    public char getNSCOUNT() {
        return NSCOUNT;
    }

    public void setNSCOUNT(char NSCOUNT) {
        this.NSCOUNT = NSCOUNT;
    }

    public char getARCOUNT() {
        return ARCOUNT;
    }

    public void setARCOUNT(char ARCOUNT) {
        this.ARCOUNT = ARCOUNT;
    }

    @Override
    public String toString(){
        return new String(this.toCharArray());
    }

    public char[] toCharArray(){
        char[] buffer = new char[6];
        buffer[0] = this.ID;
        char tmp = (char) (this.QR ? 0x8000 : 0x0000); //set the QR bit
        tmp = (char) (tmp | (((char) (0x0F & this.Opcode))<<11)); //set the Opcode bits
        if (this.AA) tmp = (char) (tmp | 0x0400); // set AA if true
        if (this.TC) tmp = (char) (tmp | 0x0200); // set TC is
        if (this.RD) tmp = (char) (tmp | 0x0100);
        if (this.RA) tmp = (char) (tmp | 0x0080);
        tmp = (char) (tmp | (((char) (0x07 & this.Z))<<4));
        tmp = (char) (tmp | ((char) (0x0F & this.RCODE)));
        buffer[1] = tmp;
        buffer[2] = this.QDCOUNT;
        buffer[3] = this.ANCOUNT;
        buffer[4] = this.NSCOUNT;
        buffer[5] = this.ARCOUNT;
        return buffer;
    }

    public byte[] toByteArray() {
        char[] chars = this.toCharArray();
        byte[] bytes = new byte[chars.length << 1];
        for(int i=0; i<chars.length; i++){
            int bpos = i << 1;
            bytes[bpos] = (byte) ((chars[i]&0xFF00)>>8);
            bytes[bpos+1] = (byte) ((chars[i]&0x00FF));
        }
        return bytes;
    }
}

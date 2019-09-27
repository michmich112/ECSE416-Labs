package models;

public enum QtypeEnum {
    A((char) 0x0001),
    NS((char) 0x0002),
    MX((char) 0x000f);

    private char code;

    public char getCode(){
        return this.code;
    }

    private QtypeEnum(char code){
        this.code = code;
    }
}

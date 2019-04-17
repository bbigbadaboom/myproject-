class BadExpression extends Exception{
    private String msg;

    public BadExpression(String error, String expr, int charIndex){
        msg = error + " в \n" + expr + " в символе номер " + String.valueOf(charIndex);
    }
    public void print(){
        System.err.println(msg);
    }
}
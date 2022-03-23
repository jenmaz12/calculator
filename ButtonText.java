
public enum ButtonText
{
    M_PLUS("M+"), M_MINUS("M-"), MRC("MRC"), C("C"), SEVEN("7"), EIGHT("8"),
    NINE("9"), DIVIDE("\u00F7"), FOUR("4"), FIVE("5"), SIX("6"), MULTIPLY("x"),
    ONE("1"), TWO("2"), THREE("3"), MINUS("-"), ZERO("0"), DECIMAL("."),
    EQUALS("="), PLUS("+");
    
    public final String label;
    
    private ButtonText(String label) {
        this.label = label;
    }
    
}

public class JegyEllenorzo {
    private final String napiKod;

    public JegyEllenorzo(String kod) {
        this.napiKod = kod;
    }

    public boolean ellenoriz(String jegy) {
        return napiKod.equals(jegy);
    }

    public String getNapiKod() {
        return napiKod;
    }
}
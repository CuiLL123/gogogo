import jdk.nashorn.internal.objects.annotations.Getter;

public enum CountyEnum {
    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FORE(4,"赵"),FIVE(5,"魏"),SIX(6,"韩");


    private Integer retCode;
    private String retMessage;

    public Integer getRetCode() {
        return retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    CountyEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static CountyEnum forEach_countryEnum(int index) {
        CountyEnum[] countyEnums = CountyEnum.values();
        for(CountyEnum e :countyEnums) {
            if (index == e.getRetCode()) {
                return e;
            }
        }
        return null;
    }


}
